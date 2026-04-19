package com.campusevents.model;

import java.time.OffsetDateTime;

public class Reservation {
    private String id;
    private String userId;
    private String eventId;
    private String status;
    private String attendance;
    private OffsetDateTime reservedAt;
    private Event event;
    private User user;

    public Reservation() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAttendance() { return attendance; }
    public void setAttendance(String attendance) { this.attendance = attendance; }
    public OffsetDateTime getReservedAt() { return reservedAt; }
    public void setReservedAt(OffsetDateTime reservedAt) { this.reservedAt = reservedAt; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
