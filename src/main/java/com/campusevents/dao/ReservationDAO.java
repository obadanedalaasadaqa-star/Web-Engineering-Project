package com.campusevents.dao;

import com.campusevents.model.Event;
import com.campusevents.model.Reservation;
import com.campusevents.model.User;
import com.campusevents.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public List<Reservation> findByUser(String userId) throws SQLException {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT r.*, " +
                     "e.title, e.date_time, e.location, e.type, e.status AS event_status, e.seats_remaining " +
                     "FROM reservations r " +
                     "JOIN events e ON r.event_id = e.id " +
                     "WHERE r.user_id = ? AND r.status = 'reserved' " +
                     "ORDER BY r.reserved_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(userId));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reservation res = mapRow(rs);
                    Event ev = new Event();
                    ev.setId(rs.getString("event_id"));
                    ev.setTitle(rs.getString("title"));
                    Timestamp dt = rs.getTimestamp("date_time");
                    if (dt != null) ev.setDateTime(dt.toInstant().atOffset(java.time.ZoneOffset.UTC));
                    ev.setLocation(rs.getString("location"));
                    ev.setType(rs.getString("type"));
                    ev.setStatus(rs.getString("event_status"));
                    ev.setSeatsRemaining(rs.getInt("seats_remaining"));
                    res.setEvent(ev);
                    list.add(res);
                }
            }
        }
        return list;
    }

    public List<Reservation> findByEvent(String eventId) throws SQLException {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS user_name, u.email AS user_email, u.uni_number AS user_uni_number " +
                     "FROM reservations r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.event_id = ? AND r.status = 'reserved' " +
                     "ORDER BY r.reserved_at ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(eventId));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reservation res = mapRow(rs);
                    User u = new User();
                    u.setId(rs.getString("user_id"));
                    u.setName(rs.getString("user_name"));
                    u.setEmail(rs.getString("user_email"));
                    u.setUniNumber(rs.getString("user_uni_number"));
                    res.setUser(u);
                    list.add(res);
                }
            }
        }
        return list;
    }

    public boolean hasReservation(String userId, String eventId) throws SQLException {
        String sql = "SELECT 1 FROM reservations WHERE user_id=? AND event_id=? AND status='reserved'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(userId));
            ps.setObject(2, java.util.UUID.fromString(eventId));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void reserve(String userId, String eventId) throws SQLException {
        String sql = "SELECT reserve_ticket(?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(userId));
            ps.setObject(2, java.util.UUID.fromString(eventId));
            ps.executeQuery();
        }
    }

    public void cancel(String userId, String eventId) throws SQLException {
        String sql = "SELECT cancel_reservation(?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(userId));
            ps.setObject(2, java.util.UUID.fromString(eventId));
            ps.executeQuery();
        }
    }

    public void updateAttendance(String reservationId, String attendance) throws SQLException {
        String sql = "UPDATE reservations SET attendance = ?::attendance_status WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, attendance);
            ps.setObject(2, java.util.UUID.fromString(reservationId));
            ps.executeUpdate();
        }
    }

    private Reservation mapRow(ResultSet rs) throws SQLException {
        Reservation res = new Reservation();
        res.setId(rs.getString("id"));
        res.setUserId(rs.getString("user_id"));
        res.setEventId(rs.getString("event_id"));
        res.setStatus(rs.getString("status"));
        res.setAttendance(rs.getString("attendance"));
        Timestamp ts = rs.getTimestamp("reserved_at");
        if (ts != null) res.setReservedAt(ts.toInstant().atOffset(java.time.ZoneOffset.UTC));
        return res;
    }
}
