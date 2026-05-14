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
import models.Vendor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendorDAO {
    private Connection connection;
    
    public VendorDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    public boolean create(Vendor vendor) {
        String sql = "INSERT INTO vendors "
                + "(name, category, min_price, max_price, unit_type, contact, rating, notes) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getCategory());
            stmt.setDouble(3, vendor.getMinPrice());
            stmt.setDouble(4, vendor.getMaxPrice());
            stmt.setString(5, vendor.getUnitType());
            stmt.setString(6, vendor.getContact());
            stmt.setDouble(7, vendor.getRating());
            stmt.setString(8, vendor.getNotes());
            
            int affectedRow = stmt.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e){
            System.err.println("Error insert vendor: " + e.getMessage());
            return false;
//            seharusnya buat exception ga sih?
        }
    }
    
    public List<Vendor> getAll() {
        List<Vendor> list = new ArrayList<>();
        String sql = "SELECT * FROM vendors ORDER BY id";
        
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setId(rs.getInt("id"));
                vendor.setName(rs.getString("name"));
                vendor.setCategory(rs.getString("category"));
                vendor.setMinPrice(rs.getDouble("min_price"));
                vendor.setMaxPrice(rs.getDouble("max_price"));
                vendor.setUnitType(rs.getString("unit_type"));
                vendor.setContact(rs.getString("contact"));
                vendor.setRating(rs.getDouble("rating"));
                vendor.setNotes(rs.getString("notes"));
                list.add(vendor);
            }
        } catch (SQLException e) {
            System.err.println("Error get all vendors: " + e.getMessage());
        }
        return list;
    }
    
    public Vendor getById(int id) {
        String sql = "SELECT * FROM vendors WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setId(rs.getInt("id"));
                vendor.setName(rs.getString("name"));
                vendor.setCategory(rs.getString("category"));
                vendor.setMinPrice(rs.getDouble("min_price"));
                vendor.setMaxPrice(rs.getDouble("max_price"));
                vendor.setUnitType(rs.getString("unit_type"));
                vendor.setContact(rs.getString("contact"));
                vendor.setRating(rs.getDouble("rating"));
                vendor.setNotes(rs.getString("notes"));
                return vendor;       
            }
        } catch (SQLException e) {
            System.err.println("Error get vendor by id: " + e.getMessage());
        }
        return null;
    }
    
    public boolean update(Vendor vendor) {
        String sql = "UPDATE vendors SET name = ?, category = ?, min_price = ?, "
                + "max_price = ?, unit_type = ?, contact = ?, rating = ?, "
                + "notes = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getCategory());
            stmt.setDouble(3, vendor.getMinPrice());
            stmt.setDouble(4, vendor.getMaxPrice());
            stmt.setString(5, vendor.getUnitType());
            stmt.setString(6, vendor.getContact());
            stmt.setDouble(7, vendor.getRating());
            stmt.setString(8, vendor.getNotes());
            stmt.setInt(9, vendor.getId());
            
            int affectedRow = stmt.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e) {
            System.err.println("Error update vendor: " + e.getMessage());
            return false;
//            seharusnya dari exception juga
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM vendors WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRow = stmt.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e) {
            System.err.println("Error delete vendor: " + e.getMessage());
            return false;
//            seharusnya dari exception juga
        }
    }
    
    
//     Mencari vendor berdasarkan kategori
//    public List<Vendor> getByCategory(String category) {
//        List<Vendor> list = new ArrayList<>();
//        String sql = "SELECT * FROM vendors WHERE category = ? ORDER BY name";
//        
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, category);
//            ResultSet rs = pstmt.executeQuery();
//            
//            while (rs.next()) {
//                Vendor vendor = new Vendor();
//                vendor.setId(rs.getInt("id"));
//                vendor.setName(rs.getString("name"));
//                vendor.setCategory(rs.getString("category"));
//                vendor.setMinPrice(rs.getDouble("min_price"));
//                vendor.setMaxPrice(rs.getDouble("max_price"));
//                vendor.setUnitType(rs.getString("unit_type"));
//                vendor.setContact(rs.getString("contact"));
//                vendor.setRating(rs.getDouble("rating"));
//                vendor.setNotes(rs.getString("notes"));
//                list.add(vendor);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error get vendors by category: " + e.getMessage());
//        }
//        return list;
//    }

//    Mencari vendor berdasarkan nama (search)
//    public List<Vendor> searchByName(String keyword) {
//        List<Vendor> list = new ArrayList<>();
//        String sql = "SELECT * FROM vendors WHERE name LIKE ? ORDER BY name";
//        
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, "%" + keyword + "%");
//            ResultSet rs = pstmt.executeQuery();
//            
//            while (rs.next()) {
//                Vendor vendor = new Vendor();
//                vendor.setId(rs.getInt("id"));
//                vendor.setName(rs.getString("name"));
//                vendor.setCategory(rs.getString("category"));
//                vendor.setMinPrice(rs.getDouble("min_price"));
//                vendor.setMaxPrice(rs.getDouble("max_price"));
//                vendor.setUnitType(rs.getString("unit_type"));
//                vendor.setContact(rs.getString("contact"));
//                vendor.setRating(rs.getDouble("rating"));
//                vendor.setNotes(rs.getString("notes"));
//                list.add(vendor);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error search vendors: " + e.getMessage());
//        }
//        return list;
//    }
}
