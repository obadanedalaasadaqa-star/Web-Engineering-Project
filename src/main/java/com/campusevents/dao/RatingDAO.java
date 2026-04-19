package com.campusevents.dao;

import com.campusevents.model.Rating;
import com.campusevents.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO {

    public List<Rating> findByEvent(String eventId) throws SQLException {
        List<Rating> list = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS user_name FROM ratings r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.event_id = ? ORDER BY r.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(eventId));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public boolean hasRated(String userId, String eventId) throws SQLException {
        String sql = "SELECT 1 FROM ratings WHERE user_id=? AND event_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(userId));
            ps.setObject(2, java.util.UUID.fromString(eventId));
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public void create(Rating rating) throws SQLException {
        String sql = "INSERT INTO ratings (user_id, event_id, stars, review) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(rating.getUserId()));
            ps.setObject(2, java.util.UUID.fromString(rating.getEventId()));
            ps.setInt(3, rating.getStars());
            ps.setString(4, rating.getReview());
            ps.executeUpdate();
        }
    }

    private Rating mapRow(ResultSet rs) throws SQLException {
        Rating r = new Rating();
        r.setId(rs.getString("id"));
        r.setUserId(rs.getString("user_id"));
        r.setEventId(rs.getString("event_id"));
        r.setStars(rs.getInt("stars"));
        r.setReview(rs.getString("review"));
        r.setUserName(rs.getString("user_name"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) r.setCreatedAt(ts.toInstant().atOffset(java.time.ZoneOffset.UTC));
        return r;
    }
}
