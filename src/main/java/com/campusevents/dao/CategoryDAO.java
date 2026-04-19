package com.campusevents.dao;

import com.campusevents.model.Category;
import com.campusevents.model.Department;
import com.campusevents.model.Faculty;
import com.campusevents.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> findAllCategories() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, name FROM categories ORDER BY name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Category(rs.getString("id"), rs.getString("name")));
        }
        return list;
    }

    public void createCategory(String name) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public void deleteCategory(String id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(id));
            ps.executeUpdate();
        }
    }

    public List<Faculty> findAllFaculties() throws SQLException {
        List<Faculty> list = new ArrayList<>();
        String sql = "SELECT id, name FROM faculties ORDER BY name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Faculty(rs.getString("id"), rs.getString("name")));
        }
        return list;
    }

    public List<Department> findDepartmentsByFaculty(String facultyId) throws SQLException {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT id, name, faculty_id FROM departments WHERE faculty_id = ? ORDER BY name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, java.util.UUID.fromString(facultyId));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Department d = new Department();
                    d.setId(rs.getString("id"));
                    d.setName(rs.getString("name"));
                    d.setFacultyId(rs.getString("faculty_id"));
                    list.add(d);
                }
            }
        }
        return list;
    }
}
