/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

import dto.EventDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Event extends BaseRepository implements Repository<EventDTO> {
    
    @Override
    protected boolean validateData(Object entity) {
        if (entity == null) return false;
        EventDTO event = (EventDTO) entity;
        return event.getNamaEvent() != null && !event.getNamaEvent().isEmpty() &&
               event.getTanggalEvent() != null &&
               event.getNamaCust()!= null && !event.getNamaCust().isEmpty() &&
               event.getBudgetCust() > 0 &&
               event.getTotalTamu() > 0;
    }
    
    @Override
    protected boolean insertEntity(Object entity) {
        return insert((EventDTO) entity);
    }
    
    @Override
    protected boolean updateEntity(Object entity) {
        return update((EventDTO) entity);
    }
    
    @Override
    public List<EventDTO> getAll() {
        List<EventDTO> events = new ArrayList<>();
        String sql = "SELECT * FROM event ORDER BY id DESC";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                events.add(mapResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error get all events: " + e.getMessage());
        }
        return events;
    }
    
    @Override
    public EventDTO getById(int id) {
        String sql = "SELECT * FROM event WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToDTO(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error get event by id: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public Boolean insert(EventDTO event) {
        String sql = "INSERT INTO event(nama_event, tanggal_event, nama_cust, nomor_cust, " +
                     "budget_cust, total_tamu, status_acara, total_akhir_price, payment_status) " +
                     "VALUES(?,?,?,?,?,?,?,?,?)";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, event.getNamaEvent());
            stmt.setDate(2, event.getTanggalEvent());
            stmt.setString(3, event.getNamaCust());
            stmt.setString(4, event.getNomorCust());
            stmt.setDouble(5, event.getBudgetCust());
            stmt.setInt(6, event.getTotalTamu());
            stmt.setString(7, event.getStatusAcara() != null ? event.getStatusAcara() : "rencana");
            stmt.setDouble(8, event.getTotalAkhirPrice());
            stmt.setString(9, event.getPaymentStatus() != null ? event.getPaymentStatus() : "belum_bayar");
            
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    event.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insert event: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean update(EventDTO event) {
        String sql = "UPDATE event SET nama_event=?, tanggal_event=?, nama_cust=?, " +
                     "nomor_cust=?, budget_cust=?, total_tamu=?, status_acara=?, " +
                     "total_akhir_price=?, payment_status=? WHERE id=?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, event.getNamaEvent());
            stmt.setDate(2, event.getTanggalEvent());
            stmt.setString(3, event.getNamaCust());
            stmt.setString(4, event.getNomorCust());
            stmt.setDouble(5, event.getBudgetCust());
            stmt.setInt(6, event.getTotalTamu());
            stmt.setString(7, event.getStatusAcara());
            stmt.setDouble(8, event.getTotalAkhirPrice());
            stmt.setString(9, event.getPaymentStatus());
            stmt.setInt(10, event.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update event: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean deleteById(int id) {
        String sql = "DELETE FROM event WHERE id=?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete event: " + e.getMessage());
        }
        return false;
    }
    
    // Method khusus untuk update total akhir price (multithreading akan pakai ini)
    public void updateTotalAkhirPrice(int eventId) {
        new Thread(() -> {
            String sql = "UPDATE event e SET e.total_akhir_price = " +
                         "(SELECT COALESCE(SUM(harga_pakai), 0) FROM event_vendor WHERE event_id = ?) " +
                         "WHERE e.id = ?";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setInt(1, eventId);
                stmt.setInt(2, eventId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error update total price: " + e.getMessage());
            }
        }).start();
    }
    
    private EventDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new EventDTO(
            rs.getInt("id"),
            rs.getString("nama_event"),
            rs.getDate("tanggal_event"),
            rs.getString("nama_cust"),
            rs.getString("nomor_cust"),
            rs.getDouble("budget_cust"),
            rs.getInt("total_tamu"),
            rs.getString("status_acara"),
            rs.getDouble("total_akhir_price"),
            rs.getString("payment_status")
        );
    }
}