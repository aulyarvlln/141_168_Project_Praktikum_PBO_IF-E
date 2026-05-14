/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ACER
 */

import database.DatabaseConnection;
import models.PreparationTask;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection;
    
    public TaskDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public boolean create(PreparationTask task) {
        String sql = "INSERT INTO preparation_tasks "
                + "(event_id, vendor_id, task_name, deadline, status, notes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, task.eventID);
            stmt.setInt(2, task.vendorID);
            stmt.setString(3, task.taskName);
            stmt.setTimestamp(4, Timestamp.valueOf(task.deadline));
            stmt.setString(5, task.status);
            stmt.setString(6, task.notes);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error insert task: " + e.getMessage());
            return false;
        }
    }
    
    public List<PreparationTask> getAll() {
        List<PreparationTask> list = new ArrayList<>();
        String sql = "SELECT t.*, v.name as vendor_name, "
                + "e.event_name FROM preparation_tasks "
                + "t LEFT JOIN vendors v ON t.vendor_id = v.id LEFT JOIN events e ON t.event_id = e.id ORDER BY t.deadline";
        
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PreparationTask task = new PreparationTask();
                task.ID = rs.getInt("id");
                task.eventID = rs.getInt("event_id");
                task.vendorID = rs.getInt("vendor_id");
                task.taskName = rs.getString("task_name");
                task.deadline = rs.getTimestamp("deadline").toLocalDateTime();
                task.status = rs.getString("status");
                task.notes = rs.getString("notes");
                task.vendorName = rs.getString("vendor_name");
                task.eventName = rs.getString("event_name");
                list.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error get all tasks: " + e.getMessage());
        }
        
        return list;
    }
    
    public PreparationTask getById(int id) {
        String sql = "SELECT t.*, v.name as vendor_name, "
                + "e.event_name FROM preparation_tasks t LEFT JOIN vendors v ON t.vendor_id = v.id "
                + "LEFT JOIN events e ON t.event_id = e.id WHERE t.id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                PreparationTask task = new PreparationTask();
                task.ID = rs.getInt("id");
                task.eventID = rs.getInt("event_id");
                task.vendorID = rs.getInt("vendor_id");
                task.taskName = rs.getString("task_name");
                task.deadline = rs.getTimestamp("deadline").toLocalDateTime();
                task.status = rs.getString("status");
                task.notes = rs.getString("notes");
                task.vendorName = rs.getString("vendor_name");
                task.eventName = rs.getString("event_name");
                return task;
            }
        } catch (SQLException e) {
            System.err.println("Error get task by id: " + e.getMessage());
        }
        return null;
    }
    
    public List<PreparationTask> getByEventId(int eventId) {
        List<PreparationTask> list = new ArrayList<>();
        String sql = "SELECT t.*, v.name as vendor_name "
                + "FROM preparation_tasks t LEFT JOIN vendors v ON t.vendor_id = v.id "
                + "WHERE t.event_id = ? ORDER BY t.deadline";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PreparationTask task = new PreparationTask();
                task.ID = rs.getInt("id");
                task.eventID = rs.getInt("event_id");
                task.vendorID = rs.getInt("vendor_id");
                task.taskName = rs.getString("task_name");
                task.deadline = rs.getTimestamp("deadline").toLocalDateTime();
                task.status = rs.getString("status");
                task.notes = rs.getString("notes");
                task.vendorName = rs.getString("vendor_name");
                list.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error get tasks by event_id: " + e.getMessage());
        }
        return list;
    }
    
    public boolean update(PreparationTask task) {
        String sql = "UPDATE preparation_tasks SET event_id = ?, "
                + "vendor_id = ?, task_name = ?, deadline = ?, status = ?, notes = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, task.eventID);
            stmt.setInt(2, task.vendorID);
            stmt.setString(3, task.taskName);
            stmt.setTimestamp(4, Timestamp.valueOf(task.deadline));
            stmt.setString(5, task.status);
            stmt.setString(6, task.notes);
            stmt.setInt(7, task.ID);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error update task: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE preparation_tasks SET status = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error update task status: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM preparation_tasks WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error delete task: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteByEventId(int eventId) {
        String sql = "DELETE FROM preparation_tasks WHERE event_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error delete tasks by event_id: " + e.getMessage());
            return false;
        }
    }
    
//    READ - Mengambil tugas yang deadline-nya mendekat (untuk notifikasi)
    public List<PreparationTask> getUpcomingTasks(int hours) {
        List<PreparationTask> list = new ArrayList<>();
        String sql = "SELECT t.*, v.name as vendor_name, e.event_name FROM preparation_tasks t LEFT JOIN vendors v ON t.vendor_id = v.id LEFT JOIN events e ON t.event_id = e.id WHERE t.deadline BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? HOUR) AND t.status != 'Selesai' ORDER BY t.deadline";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, hours);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PreparationTask task = new PreparationTask();
                task.ID = rs.getInt("id");
                task.eventID = rs.getInt("event_id");
                task.vendorID = rs.getInt("vendor_id");
                task.taskName = rs.getString("task_name");
                task.deadline = rs.getTimestamp("deadline").toLocalDateTime();
                task.status = rs.getString("status");
                task.notes = rs.getString("notes");
                task.vendorName = rs.getString("vendor_name");
                task.eventName = rs.getString("event_name");
                list.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error get upcoming tasks: " + e.getMessage());
        }
        return list;
    }
}
