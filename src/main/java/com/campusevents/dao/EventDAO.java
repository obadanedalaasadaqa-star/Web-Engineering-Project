package com.campusevents.dao;

import com.campusevents.model.Event;
import com.campusevents.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public List<Event> findAll() throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT e.*, u.name AS organizer_name, c.name AS category_name, " +
                     "COALESCE(AVG(r.stars), 0) AS avg_rating " +
                     "FROM events e " +
                     "LEFT JOIN users u ON e.organizer_id = u.id " +
                     "LEFT JOIN categories c ON e.category_id = c.id " +
                     "LEFT JOIN ratings r ON e.id = r.event_id " +
                     "GROUP BY e.id, u.name, c.name " +
                     "ORDER BY e.date_time ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public List<Event> findByOrganizer(String organizerId) throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT e.*, u.name AS organizer_name, c.name AS category_name, " +
                     "COALESCE(AVG(r.stars), 0) AS avg_rating " +
                     "FROM events e " +
                     "LEFT JOIN users u ON e.organizer_id = u.id " +
                     "LEFT JOIN categories c ON e.category_id = c.id " +
                     "LEFT JOIN ratings r ON e.id = r.event_id " +
                     "WHERE e.organizer_id = ? " +
                     "GROUP BY e.id, u.name, c.name " +
                     "ORDER BY e.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(organizerId));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Event findById(String id) throws SQLException {
        String sql = "SELECT e.*, u.name AS organizer_name, c.name AS category_name, " +
                     "COALESCE(AVG(r.stars), 0) AS avg_rating " +
                     "FROM events e " +
                     "LEFT JOIN users u ON e.organizer_id = u.id " +
                     "LEFT JOIN categories c ON e.category_id = c.id " +
                     "LEFT JOIN ratings r ON e.id = r.event_id " +
                     "WHERE e.id = ? " +
                     "GROUP BY e.id, u.name, c.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public void create(Event event) throws SQLException {
        String sql = "INSERT INTO events (title, description, organizer_id, category_id, type, " +
                     "date_time, location, capacity, seats_remaining, department) " +
                     "VALUES (?, ?, ?, ?, ?::event_type, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setObject(3, java.util.UUID.fromString(event.getOrganizerId()));
            if (event.getCategoryId() != null && !event.getCategoryId().isEmpty())
                ps.setObject(4, java.util.UUID.fromString(event.getCategoryId()));
            else
                ps.setNull(4, Types.OTHER);
            ps.setString(5, event.getType());
            ps.setTimestamp(6, Timestamp.from(event.getDateTime().toInstant()));
            ps.setString(7, event.getLocation());
            ps.setInt(8, event.getCapacity());
            ps.setInt(9, event.getSeatsRemaining());
            ps.setString(10, event.getDepartment());
            ps.executeUpdate();
        }
    }

    public void update(Event event) throws SQLException {
        String sql = "UPDATE events SET title=?, description=?, category_id=?, type=?::event_type, " +
                     "date_time=?, location=?, capacity=?, department=?, status=?::event_status WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            if (event.getCategoryId() != null && !event.getCategoryId().isEmpty())
                ps.setObject(3, java.util.UUID.fromString(event.getCategoryId()));
            else
                ps.setNull(3, Types.OTHER);
            ps.setString(4, event.getType());
            ps.setTimestamp(5, Timestamp.from(event.getDateTime().toInstant()));
            ps.setString(6, event.getLocation());
            ps.setInt(7, event.getCapacity());
            ps.setString(8, event.getDepartment());
            ps.setString(9, event.getStatus());
            ps.setObject(10, java.util.UUID.fromString(event.getId()));
            ps.executeUpdate();
        }
    }

    public void delete(String eventId) throws SQLException {
        String sql = "DELETE FROM events WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(eventId));
            ps.executeUpdate();
        }
    }

    public void expireOverdueEvents() throws SQLException {
        String sql = "UPDATE events SET status = 'expired'::event_status " +
                     "WHERE date_time < NOW() AND status IN ('open', 'closed')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    public void updateStatus(String eventId, String status) throws SQLException {
        String sql = "UPDATE events SET status = ?::event_status WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setObject(2, java.util.UUID.fromString(eventId));
            ps.executeUpdate();
        }
    }

    private Event mapRow(ResultSet rs) throws SQLException {
        Event e = new Event();
        e.setId(rs.getString("id"));
        e.setTitle(rs.getString("title"));
        e.setDescription(rs.getString("description"));
        e.setOrganizerId(rs.getString("organizer_id"));
        e.setOrganizerName(rs.getString("organizer_name"));
        e.setCategoryId(rs.getString("category_id"));
        e.setCategoryName(rs.getString("category_name"));
        e.setType(rs.getString("type"));
        Timestamp dt = rs.getTimestamp("date_time");
        if (dt != null) e.setDateTime(dt.toInstant().atOffset(java.time.ZoneOffset.UTC));
        e.setLocation(rs.getString("location"));
        e.setCapacity(rs.getInt("capacity"));
        e.setSeatsRemaining(rs.getInt("seats_remaining"));
        e.setStatus(rs.getString("status"));
        e.setImagePath(rs.getString("image_path"));
        e.setDepartment(rs.getString("department"));
        Timestamp cat = rs.getTimestamp("created_at");
        if (cat != null) e.setCreatedAt(cat.toInstant().atOffset(java.time.ZoneOffset.UTC));
        e.setAvgRating(rs.getDouble("avg_rating"));
        return e;
    }
}
