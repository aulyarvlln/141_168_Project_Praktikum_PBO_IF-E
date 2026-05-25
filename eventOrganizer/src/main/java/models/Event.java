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
    public List<EventDTO> getAll() {
        List<EventDTO> events = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM event ORDER BY id DESC";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
        
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EventDTO event = new EventDTO(
                    rs.getInt("id"),
                    rs.getString("nama_event"),
                    rs.getDate("tanggal_event"),rs.getString("nama_cust"),
                    rs.getString("nomor_cust"),
                    rs.getDouble("budget_cust"),
                    rs.getInt("total_tamu"),
                    rs.getString("status_acara"),
                    rs.getDouble("total_akhir_price"),
                    rs.getString("payment_status")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error get all events: " + e.getMessage());
        }
        return events;
    }
    
    @Override
    public EventDTO getById(int id) {        
        try {
            String sql = "SELECT * FROM event WHERE id = ?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                EventDTO event = new EventDTO(
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
                return event;
            }
        } catch (SQLException e) {
            System.err.println("Error get event by id: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public Boolean insert(EventDTO event) {        
        try {
            String sql = "INSERT INTO event(nama_event, tanggal_event, nama_cust, nomor_cust, " + 
                "budget_cust, total_tamu, status_acara, total_akhir_price, payment_status) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setString(1, event.getNamaEvent());
            stmt.setDate(2, event.getTanggalEvent());
            stmt.setString(3, event.getNamaCust());
            stmt.setString(4, event.getNomorCust());
            stmt.setDouble(5, event.getBudgetCust());
            stmt.setInt(6, event.getTotalTamu());
            stmt.setString(7, event.getStatusAcara() != null ? event.getStatusAcara() : "belum selesai");
            stmt.setDouble(8, event.getTotalAkhirPrice());
            stmt.setString(9, event.getPaymentStatus() != null ? event.getPaymentStatus() : "belum bayar");
            
            int affectedRow = stmt.executeUpdate();
            
            if (affectedRow == 0) {
                System.out.println("Data Gagal ditambahkan");
                return false;
            } else {   
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insert event: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean update(EventDTO event) {
        try {
            String sql = "UPDATE event SET status_acara=?, payment_status=? WHERE id=?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setString(1, event.getStatusAcara());
            stmt.setString(2, event.getPaymentStatus());
            stmt.setInt(3, event.getId());
            
            int affectedRow = stmt.executeUpdate();
            
            if (affectedRow == 0) {
                System.out.println("Data Gagal diupdate");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error update event: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public Boolean deleteById(int id) {
        try {
            String sql = "DELETE FROM event WHERE id=?";
        
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
            System.err.println("Error delete event: " + e.getMessage());
        }
        return false;
    }
    
    // Method khusus untuk update total akhir price (multithreading akan pakai ini)
    public void updateTotalAkhirPrice(int eventId) {
        new Thread(() -> {
            try {
                String sql = "UPDATE event e SET e.total_akhir_price = " +
                    "(SELECT COALESCE(SUM(harga_pakai), 0) FROM event_vendor WHERE event_id = ?) " +
                    "WHERE e.id = ?";
                
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                
                stmt.setInt(1, eventId);
                stmt.setInt(2, eventId);
                stmt.executeUpdate();
                
                System.out.println("Total harga event " + eventId + " berhasil diupdate");
            } catch (SQLException e) {
                System.err.println("Error update total price: " + e.getMessage());
            }
        }).start();
    }

    public double getTotalAkhirPrice(int eventId) {
        try {
            String sql = "SELECT COALESCE(SUM(harga_pakai), 0) as total " +
                     "FROM event_vendor WHERE event_id = ?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, eventId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error get total akhir price: " + e.getMessage());
        }
        return 0.0;
    }
}