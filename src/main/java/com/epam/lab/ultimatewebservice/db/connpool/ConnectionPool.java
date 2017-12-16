package com.epam.lab.ultimatewebservice.db.connpool;

import ch.qos.logback.classic.Level;
import com.sun.javafx.util.Logging;
import lombok.experimental.Delegate;
import org.apache.commons.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ConnectionPool {
    private static BlockingQueue<Connection> connectionQueue;
    private static BlockingQueue<Connection> givenAwayConQueue;
    private static ConnectionPool connectionPool;
    private static org.slf4j.Logger errorLog;
    @Autowired
    private static DataSource dataSource;

    private static String driverName;
    private static String url;
    private static String user;
    private static String pswd;
    private static int poolSize;

    private ConnectionPool() { }

    private void initPoolData() {
        errorLog = LoggerFactory.getLogger("CP.errorLogger");
        givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        try {
            for (int i = 0; i < poolSize; i++) {
                PooledConnection pooledConnection = new PooledConnection(dataSource.getConnection());
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Connection pool exception: can't get connection from driver manager!");
        }
    }

    private void dispose(){
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException ex) {
            errorLog.error("Connections in connection pool haven't been closed");
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue)
            throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    public static ConnectionPool getInstance() {
        if (connectionPool != null) {
            return connectionPool;
        } else {
            connectionPool = new ConnectionPool();
            return connectionPool;
        }
    }

    public Connection getConnection() throws InterruptedException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException("Connection pool exception: can't get connection");
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet resultSet) {
        closeConnection(con, st);

        try {
            resultSet.close();
        } catch (SQLException e) {
            errorLog.error("ResultSet didn't close");
        }
    }

    public void closeConnection(Connection con, Statement st) {
        closeConnection(con);

        try {
            con.close();
        } catch (SQLException e) {
            errorLog.error("Connection didn't return in pool");
        }
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            errorLog.error("Connection didn't return in pool");
        }
    }


    class PooledConnection implements Connection {
        @Delegate(excludes = AutoCloseable.class)
        Connection connection;

        public PooledConnection(Connection conn) throws SQLException {
            connection = conn;
            connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection.");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConQueue.remove(this)) {
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }
            if (!connectionQueue.offer(this)) {
                throw new SQLException("Error returning connection in the pool.");
            }
        }
    }
}
