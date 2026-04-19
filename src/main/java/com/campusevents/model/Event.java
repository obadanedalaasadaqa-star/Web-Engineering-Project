package com.campusevents.model;

import java.time.OffsetDateTime;
import java.util.Date;

public class Event {
    private String id;
    private String title;
    private String description;
    private String organizerId;
    private String organizerName;
    private String categoryId;
    private String categoryName;
    private String type;
    private OffsetDateTime dateTime;
    private String location;
    private int capacity;
    private int seatsRemaining;
    private String status;
    private String imagePath;
    private String department;
    private OffsetDateTime createdAt;
    private double avgRating;

    public Event() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOrganizerId() { return organizerId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }
    public String getOrganizerName() { return organizerName; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public OffsetDateTime getDateTime() { return dateTime; }
    public void setDateTime(OffsetDateTime dateTime) { this.dateTime = dateTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getSeatsRemaining() { return seatsRemaining; }
    public void setSeatsRemaining(int seatsRemaining) { this.seatsRemaining = seatsRemaining; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }
    public Date getDateTimeAsDate() {
        if (dateTime == null) return null;
        return Date.from(dateTime.toInstant());
    }
    public Date getCreatedAtAsDate() {
        if (createdAt == null) return null;
        return Date.from(createdAt.toInstant());
    }
}
