/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dto.TaskDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class PreparationTask extends BaseRepository implements Repository<TaskDTO>{

    @Override
    protected boolean validateData(Object entity) {
        if (entity == null) return false;
        TaskDTO task = (TaskDTO) entity;
        return task.getEventId() > 0 &&
               task.getNamaTugas() != null && !task.getNamaTugas().isEmpty() &&
               task.getDeadline() != null;
    }
    
    @Override
    protected boolean insertEntity(Object entity) {
        return insert((TaskDTO) entity);
    }
    
    @Override
    protected boolean updateEntity(Object entity) {
        return update((TaskDTO) entity);
    }
    
    @Override
    public List<TaskDTO> getAll() {
        List<TaskDTO> list = new ArrayList<>();
        String sql = "SELECT pt.*, v.nama as vendor_nama FROM preparation_task pt " +
                     "LEFT JOIN vendor v ON pt.vendor_id = v.id ORDER BY pt.id";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                TaskDTO dto = mapResultSetToDTO(rs);
                dto.setVendorNama(rs.getString("vendor_nama"));
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Error get all tasks: " + e.getMessage());
        }
        return list;
    }
    
    public List<TaskDTO> getByEventId(int eventId) {
        List<TaskDTO> list = new ArrayList<>();
        String sql = "SELECT pt.*, v.nama as vendor_nama FROM preparation_task pt " +
                     "LEFT JOIN vendor v ON pt.vendor_id = v.id WHERE pt.event_id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TaskDTO dto = mapResultSetToDTO(rs);
                dto.setVendorNama(rs.getString("vendor_nama"));
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Error get tasks by event: " + e.getMessage());
        }
        return list;
    }
    
    @Override
    public TaskDTO getById(int id) {
        String sql = "SELECT pt.*, v.nama as vendor_nama FROM preparation_task pt " +
                     "LEFT JOIN vendor v ON pt.vendor_id = v.id WHERE pt.id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                TaskDTO dto = mapResultSetToDTO(rs);
                dto.setVendorNama(rs.getString("vendor_nama"));
                return dto;
            }
        } catch (SQLException e) {
            System.err.println("Error get task by id: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public Boolean insert(TaskDTO task) {
        String sql = "INSERT INTO preparation_task(event_id, vendor_id, nama_tugas, deadline, status_pengerjaan) " +
                     "VALUES(?,?,?,?,?)";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, task.getEventId());
            if (task.getVendorId() != null && task.getVendorId() > 0) {
                stmt.setInt(2, task.getVendorId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setString(3, task.getNamaTugas());
            stmt.setDate(4, task.getDeadline());
            stmt.setString(5, task.getStatusPengerjaan() != null ? task.getStatusPengerjaan() : "belum_mulai");
            
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    task.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insert task: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean update(TaskDTO task) {
        String sql = "UPDATE preparation_task SET vendor_id=?, nama_tugas=?, deadline=?, status_pengerjaan=? WHERE id=?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            if (task.getVendorId() != null && task.getVendorId() > 0) {
                stmt.setInt(1, task.getVendorId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setString(2, task.getNamaTugas());
            stmt.setDate(3, task.getDeadline());
            stmt.setString(4, task.getStatusPengerjaan());
            stmt.setInt(5, task.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update task: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean deleteById(int id) {
        String sql = "DELETE FROM preparation_task WHERE id=?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete task: " + e.getMessage());
        }
        return false;
    }
    
    private TaskDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        Integer vendorId = rs.getObject("vendor_id") != null ? rs.getInt("vendor_id") : null;
        return new TaskDTO(
            rs.getInt("id"),
            rs.getInt("event_id"),
            vendorId,
            rs.getString("nama_tugas"),
            rs.getDate("deadline"),
            rs.getString("status_pengerjaan")
        );
    }
}
