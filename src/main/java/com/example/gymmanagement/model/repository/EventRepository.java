package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Event;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EventRepository {
    private final JDBCConnect jdbcConnect = new JDBCConnect();

    public void addEvent(Event event) {
        String query = "INSERT INTO events (event_name, start_date, end_date, discount_percent, description, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        executeEventQuery(query, event);
    }

    public void updateEvent(Event event) {
        String query = "UPDATE events " +
                "SET event_name = ?, start_date = ?, end_date = ?, discount_percent = ?, description = ?, status = ? " +
                "WHERE event_id = ?";
        executeEventQuery(query, event);
    }

    public void deleteEvent(int eventId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM events WHERE event_id = ?")) {
            statement.setInt(1, eventId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Event getEventById(int eventId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE event_id = ?")) {
            statement.setInt(1, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> getAllEvents() {
        List<Event> eventsList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE status = 1");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                eventsList.add(fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsList;
    }

    public Event fromResultSet(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setEvent_id(resultSet.getInt("event_id"));
        event.setEvent_name(resultSet.getString("event_name"));
        event.setStart_date(resultSet.getString("start_date"));
        event.setEnd_date(resultSet.getString("end_date"));
        event.setDiscount_percent(resultSet.getBigDecimal("discount_percent"));
        event.setDescription(resultSet.getString("description"));
        event.setMember_id(resultSet.getInt("member_id"));
        event.setStatus(resultSet.getInt("status"));
        return event;
    }

    public void executeEventQuery(String query, Event event) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, event.getEvent_name());
            statement.setString(2, event.getStart_date());
            statement.setString(3, event.getEnd_date());
            statement.setBigDecimal(4, event.getDiscount_percent());
            statement.setString(5, event.getDescription());
//            statement.setInt(6, event.getMember_id());
            statement.setInt(6, event.getStatus());
//            statement.setInt(8, event.getEvent_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteEventByStatus(int eventId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE events SET status = 3 WHERE event_id = ?")) {
            statement.setInt(1, eventId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
