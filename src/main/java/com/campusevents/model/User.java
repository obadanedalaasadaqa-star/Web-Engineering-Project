package com.campusevents.model;

import java.time.OffsetDateTime;

public class User {
    private String id;
    private String name;
    private String email;
    private String passwordHash;
    private String role;
    private String faculty;
    private String department;
    private String major;
    private int admissionYear;
    private String status;
    private OffsetDateTime createdAt;

    public User() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public int getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(int admissionYear) { this.admissionYear = admissionYear; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
