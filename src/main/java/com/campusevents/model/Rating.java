package com.campusevents.model;

import java.time.OffsetDateTime;

public class Rating {
    private String id;
    private String userId;
    private String eventId;
    private int stars;
    private String review;
    private OffsetDateTime createdAt;
    private String userName;

    public Rating() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
