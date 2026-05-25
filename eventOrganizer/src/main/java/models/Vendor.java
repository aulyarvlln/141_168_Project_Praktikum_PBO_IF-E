/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */

import dto.VendorDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vendor extends BaseRepository {
    
    public List<VendorDTO> getAll() {
        List<VendorDTO> vendors = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM vendor ORDER BY id";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                VendorDTO vendor = new VendorDTO(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("kategori"),
                    rs.getString("kontak"),
                    rs.getDouble("min_price"),
                    rs.getDouble("max_price")
                );
                vendors.add(vendor);
            }
        } catch (SQLException e) {
            System.err.println("Error get all vendors: " + e.getMessage());
        }
        return vendors;
    }
    
    public List<VendorDTO> getByKategori(String kategori) {
        List<VendorDTO> vendors = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM vendor WHERE kategori = ? ORDER BY nama";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setString(1, kategori);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                VendorDTO vendor = new VendorDTO(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("kategori"),
                    rs.getString("kontak"),
                    rs.getDouble("min_price"),
                    rs.getDouble("max_price")
                );
                vendors.add(vendor);
            }
        } catch (SQLException e) {
            System.err.println("Error get vendors by kategori: " + e.getMessage());
        }
        return vendors;
    }
    
    public VendorDTO getById(int id) {
        try {
            String sql = "SELECT * FROM vendor WHERE id = ?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new VendorDTO(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("kategori"),
                    rs.getString("kontak"),
                    rs.getDouble("min_price"),
                    rs.getDouble("max_price")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error get vendor by id: " + e.getMessage());
        }
        return null;
    }
}