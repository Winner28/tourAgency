package com.epam.lab.ultimatewebservice.db.connpool;


import lombok.experimental.Delegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


@Component
public class ConnectionPool {
    private static BlockingQueue<Connection> connectionQueue;
    private static BlockingQueue<Connection> givenAwayConQueue;
    private static ConnectionPool connectionPool;
    private static Logger errorLog;

    private static DataSource dataSource;

    private static int poolSize = 8;



    @Autowired
    private ConnectionPool(DataSource dataSsource) {
        dataSource = dataSsource;
        initPoolData();

    }

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

    public static ConnectionPool getInstance(DataSource dataSource) {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(dataSource);
        }
        return connectionPool;
    }

    public Connection getConnection() throws InterruptedException {
       /* Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            errorLog.error("Connection pool exception: can't get connection");
            throw e;
        }
        return connection;*/
        try {
            return new PooledConnection(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
            /*if (connection.isClosed()) {
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
            }*/
        }
    }
}
