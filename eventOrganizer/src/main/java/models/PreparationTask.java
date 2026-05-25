/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

import dto.TaskDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreparationTask extends BaseRepository implements Repository<TaskDTO>{

    public List<TaskDTO> getByEventId(int eventId) {
        List<TaskDTO> list = new ArrayList<>();

        try {
            String sql = "SELECT pt.*, v.nama as vendor_nama FROM preparation_task pt " +
                "LEFT JOIN vendor v ON pt.vendor_id = v.id " +
                "WHERE pt.event_id = ? " +
                "ORDER BY pt.deadline ASC";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, eventId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TaskDTO task = new TaskDTO(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("vendor_id"),
                    rs.getString("nama_tugas"),
                    rs.getDate("deadline"),
                    rs.getString("status_pengerjaan")
                );
                task.setVendorNama(rs.getString("vendor_nama"));
                list.add(task);
            } 
        } catch (SQLException e) {
            System.err.println("Error get tasks by event: " + e.getMessage());
        }
        return list;
    }
    
    @Override
    public List<TaskDTO> getAll() {
        List<TaskDTO> list = new ArrayList<>();
        
        try {
            String sql = "SELECT pt.*, v.nama as vendor_nama FROM preparation_task pt " +
                "LEFT JOIN vendor v ON pt.vendor_id = v.id ORDER BY pt.id";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
        
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TaskDTO task  = new TaskDTO( 
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("vendor_id"),
                    rs.getString("nama_tugas"),
                    rs.getDate("deadline"),
                    rs.getString("status_pengerjaan")
                );
                task.setVendorNama(rs.getString("vendor_nama"));
                list.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Error get all tasks: " + e.getMessage());
        }
        return list;
    }
    
    @Override
    public TaskDTO getById(int id) {
        try {
            String sql = "SELECT pt.*, v.nama as vendor_nama FROM preparation_task pt " +
                "LEFT JOIN vendor v ON pt.vendor_id = v.id WHERE pt.id = ?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                TaskDTO task= new TaskDTO(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("vendor_id"),
                    rs.getString("nama_tugas"),
                    rs.getDate("deadline"),
                    rs.getString("status_pengerjaan")
                );
                task.setVendorNama(rs.getString("vendor_nama"));
                return task;
            }
        } catch (SQLException e) {
            System.err.println("Error get task by id: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public Boolean insert(TaskDTO task) {
        try {
            String sql = "INSERT INTO preparation_task(event_id, vendor_id, nama_tugas, deadline, status_pengerjaan) " +
                "VALUES(?,?,?,?,?)";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, task.getEventId());
            stmt.setInt(2, task.getVendorId());
            stmt.setString(3, task.getNamaTugas());
            stmt.setDate(4, task.getDeadline());
            stmt.setString(5, task.getStatusPengerjaan() != null ? task.getStatusPengerjaan() : "belum mulai");
            
            int affectedRow = stmt.executeUpdate();
            
            if (affectedRow == 0) {
                System.out.println("Data Gagal ditambahkan");
                return false;
            } else {   
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insert task: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean update(TaskDTO task) {        
        try {
            String sql = "UPDATE preparation_task SET status_pengerjaan=? WHERE id=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setString(1, task.getStatusPengerjaan());
            stmt.setInt(2, task.getId());
            
            int affectedRow = stmt.executeUpdate();
            
            if (affectedRow == 0) {
                System.out.println("Data Gagal diupdate");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error update task: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean deleteById(int id) {
        try {
            String sql = "DELETE FROM preparation_task WHERE id=?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            int affectedRow = stmt.executeUpdate();
            
            if (affectedRow == 0) {
                System.out.println("Data Gagal dihapus");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error delete task: " + e.getMessage());
        }
        return false;
    }
}