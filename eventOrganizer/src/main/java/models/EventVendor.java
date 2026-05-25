/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

import dto.EventVendorDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventVendor extends BaseRepository implements Repository<EventVendorDTO> {

    public List<EventVendorDTO> getByEventId(int eventId) {
        List<EventVendorDTO> list = new ArrayList<>();

        try {
            String sql = "SELECT ev.*, v.nama as vendor_nama, v.kategori as vendor_kategori " +
                "FROM event_vendor ev " +
                "JOIN vendor v ON ev.vendor_id = v.id WHERE ev.event_id = ?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, eventId);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EventVendorDTO eventvendor = new EventVendorDTO(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("vendor_id"),
                    rs.getDouble("harga_pakai")
                );
                eventvendor.setVendorNama(rs.getString("vendor_nama"));
                eventvendor.setVendorKategori(rs.getString("vendor_kategori"));
                list.add(eventvendor);
            } 
        } catch (SQLException e) {
            System.err.println("Error get event_vendor by event: " + e.getMessage());
        }
        return list;
    }
    
    @Override
    public List<EventVendorDTO> getAll() {
        List<EventVendorDTO> list = new ArrayList<>();

        try {
            String sql = "SELECT ev.*, v.nama as vendor_nama, v.kategori as vendor_kategori " +
                "FROM event_vendor ev " +
                "JOIN vendor v ON ev.vendor_id = v.id ORDER BY ev.id";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EventVendorDTO eventvendor = new EventVendorDTO(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("vendor_id"),
                    rs.getDouble("harga_pakai")
                );
                eventvendor.setVendorNama(rs.getString("vendor_nama"));
                eventvendor.setVendorKategori(rs.getString("vendor_kategori"));
                list.add(eventvendor);
            }
        } catch (SQLException e) {
            System.err.println("Error get all event_vendor: " + e.getMessage());
        }
        return list;
    }

    @Override
    public EventVendorDTO getById(int id) {
        try {
            String sql = "SELECT ev.*, v.nama as vendor_nama, v.kategori as vendor_kategori " +
                "FROM event_vendor ev " +
                "JOIN vendor v ON ev.vendor_id = v.id WHERE ev.id = ?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                EventVendorDTO eventvendor = new EventVendorDTO(
                    rs.getInt("id"),
                    rs.getInt("event_id"),
                    rs.getInt("vendor_id"),
                    rs.getDouble("harga_pakai")
                );
                eventvendor.setVendorNama(rs.getString("vendor_nama"));
                eventvendor.setVendorKategori(rs.getString("vendor_kategori"));
                return eventvendor;
            }
        } catch (SQLException e) {
            System.err.println("Error get event_vendor by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean insert(EventVendorDTO ev) {
        try {
            String sql = "INSERT INTO event_vendor(event_id, vendor_id, harga_pakai) VALUES(?,?,?)";
        
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, ev.getEventId());
            stmt.setInt(2, ev.getVendorId());
            stmt.setDouble(3, ev.getHargaPakai());

            int affectedRow = stmt.executeUpdate();
            
            if (affectedRow == 0) {
                System.out.println("Data Gagal ditambahkan");
                return false;
            } else {   
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insert event_vendor: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean update(EventVendorDTO ev) {
        try {
            String sql = "UPDATE event_vendor SET harga_pakai=? WHERE id=?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setDouble(1, ev.getHargaPakai());
            stmt.setInt(2, ev.getId());
            
            int affectedRow = stmt.executeUpdate();
            
            if (affectedRow == 0) {
                System.out.println("Data Gagal diupdate");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error update event_vendor: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean deleteById(int id) {
        try {
            String sql = "DELETE FROM event_vendor WHERE id=?";
        
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
            System.err.println("Error delete event_vendor: " + e.getMessage());
        }
        return false;
    }
}