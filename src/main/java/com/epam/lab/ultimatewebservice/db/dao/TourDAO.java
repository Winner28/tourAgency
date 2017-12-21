package com.epam.lab.ultimatewebservice.db.dao;


import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class TourDAO {

    private final static String ADD_TOUR =
            "INSERT INTO tours (hot, price, duration, agent_id, active, tour_types_id) VALUES(?,?,?,?,?,?)";
    private final static String DELETE_TOUR_BY_ID =
            "DELETE FROM tours WHERE id=?";
    private final static String GET_ALL_TOURS =
            "SELECT id, hot, price, duration, agent_id, active, tour_types_id FROM tours";
    private final static String GET_TOUR_BY_ID =
            "SELECT id, hot, price, duration, agent_id, active, tour_types_id FROM tours WHERE id=?";

    private final static String ID = "id";
    private final static String HOT= "hot";
    private final static String PRICE = "price";
    private final static String DURATION  = "duration";
    private final static String AGENT_ID  = "agent_id";
    private final static String ACTIVE  = "active";
    private final static String TOUR_TYPES_ID  = "tour_types_id";

    private JdbcDAO jdbcDAO;

    @Autowired
    public TourDAO(ConnectionPool connectionPool) {
        jdbcDAO = () -> {
            try {
                return connectionPool.getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    public Optional<Tour> addTour(Tour tour) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatementFlagged(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                return !rs.next() ? null : new Tour()
                        .setId(rs.getInt(1))
                        .setHot(tour.isHot())
                        .setPrice(tour.getPrice())
                        .setDuration(tour.getDuration())
                        .setAgentId(tour.getAgentId())
                        .setActive(tour.isActive())
                        .setTourTypesId(tour.getTourTypesId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, ADD_TOUR, tour.isHot(), tour.getPrice(), tour.getDuration(), tour.getAgentId(), tour.isActive(), tour.getTourTypesId()));
    }

    public Optional<Tour> updateUser(Tour updatedTour) {
        Optional<Tour> optionalTour = getTourById(updatedTour.getId());
        if (!optionalTour.isPresent()) {

            return Optional.empty();
        }
        Tour toUpdate = optionalTour.get();
        Map<String, String> tourMap = getFieldsToUpdate(updatedTour, toUpdate);
        if (tourMap.size() == 0) {
            return Optional.ofNullable(toUpdate);
        }
        StringBuilder SQL = new StringBuilder("UPDATE users SET ");
        for (Iterator<Map.Entry<String, String>> iterator = tourMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> param = iterator.next();
            SQL.append(param.getKey()).append("=?");
            if (iterator.hasNext()) {
                SQL.append(", ");
            } else {
                SQL.append(" ");
            }
        }
        SQL.append(" WHERE id=?");
        System.out.println("AND SQL STRING: " + SQL);
        tourMap.put("id", String.valueOf(updatedTour.getId()));
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return Optional.of(toUpdate);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, SQL.toString(), tourMap.values().toArray());

    }

    public int deleteTourById(int id) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }, DELETE_TOUR_BY_ID, id);

    }

    public List<Tour> getAllTours() {
        List<Tour> tourList = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    tourList.add(new Tour()
                            .setId(rs.getInt(ID))
                            .setHot(rs.getBoolean(HOT))
                            .setPrice(rs.getInt(PRICE))
                            .setDuration(rs.getInt(DURATION))
                            .setAgentId(rs.getInt(AGENT_ID))
                            .setActive(rs.getBoolean(ACTIVE))
                            .setTourTypesId(rs.getInt(TOUR_TYPES_ID)));
                }
            }catch (SQLException e) {
                throw new RuntimeException("Got an exception");
            }
        }, GET_ALL_TOURS);

        return tourList;
    }


    public Optional<Tour> getTourById(int id) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Tour()
                            .setId(id)
                            .setHot(resultSet.getBoolean(HOT))
                            .setPrice(resultSet.getInt(PRICE))
                            .setDuration(resultSet.getInt(DURATION))
                            .setAgentId(resultSet.getInt(AGENT_ID))
                            .setActive(resultSet.getBoolean(ACTIVE))
                            .setTourTypesId(resultSet.getInt(TOUR_TYPES_ID));
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, GET_TOUR_BY_ID, id));
    }



    private Map<String, String> getFieldsToUpdate(Tour updatedTour, Tour toUpdate) {
        Map<String, String> tourMap = new LinkedHashMap<>();

        if (updatedTour.isHot() != toUpdate.isHot()) {
            tourMap.put(HOT, String.valueOf(updatedTour.isHot()));
            toUpdate.setHot(updatedTour.isHot());
        }

        if (updatedTour.getDuration() != 0 &&
                !(toUpdate.getDuration() == updatedTour.getDuration())) {
            tourMap.put(DURATION, String.valueOf(updatedTour.getDuration()));
            toUpdate.setDuration(updatedTour.getDuration());
        }

        if (updatedTour.getPrice() != 0 &&
                !(toUpdate.getPrice() == updatedTour.getPrice())) {
            tourMap.put(PRICE, String.valueOf(updatedTour.getPrice()));
            toUpdate.setPrice(updatedTour.getPrice());
        }

        if (updatedTour.getAgentId() != 0 &&
                !(toUpdate.getAgentId() == updatedTour.getAgentId())) {
            tourMap.put(AGENT_ID, String.valueOf(updatedTour.getAgentId()));
            toUpdate.setAgentId(updatedTour.getAgentId());
        }

        if (updatedTour.isActive() != toUpdate.isActive()) {
            tourMap.put(ACTIVE, String.valueOf(updatedTour.isActive()));
            toUpdate.setActive(updatedTour.isActive());
        }

        if (updatedTour.getTourTypesId() != 0 &&
                !(toUpdate.getTourTypesId() == updatedTour.getTourTypesId())) {
            tourMap.put(TOUR_TYPES_ID, String.valueOf(updatedTour.getTourTypesId()));
            toUpdate.setTourTypesId(updatedTour.getTourTypesId());
        }


        return tourMap;
    }

}

