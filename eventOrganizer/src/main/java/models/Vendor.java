/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dto.VendorDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class Vendor extends BaseRepository implements Repository<VendorDTO>{
    @Override
    protected boolean validateData(Object entity) {
         if (entity == null) 
            return false;
        VendorDTO vendor = (VendorDTO) entity;
        return vendor.getNama() != null && !vendor.getNama().isEmpty() &&
            vendor.getKategori() != null && !vendor.getKategori().isEmpty();
    }

    @Override
    protected boolean insertEntity(Object entity) {
        return insert((VendorDTO) entity);
    }

    @Override
    protected boolean updateEntity(Object entity) {
        return update((VendorDTO) entity);
    }

    @Override
    public List<VendorDTO> getAll() {
        List<VendorDTO> vendors = new ArrayList<>();
        String sql = "SELECT * FROM vendor ORDER BY id";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                vendors.add(mapResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error get all vendors: " + e.getMessage());
        }
        return vendors;
    }

    @Override
    public VendorDTO getById(int id) {
        String sql = "SELECT * FROM vendor WHERE id = ?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToDTO(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error get vendor by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean insert(VendorDTO vendor) {
        String sql = "INSERT INTO vendor(nama, kategori, kontak, min_price, max_price) VALUES(?,?,?,?,?)";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vendor.getNama());
            stmt.setString(2, vendor.getKategori());
            stmt.setString(3, vendor.getKontak());
            stmt.setDouble(4, vendor.getMinPrice());
            stmt.setDouble(5, vendor.getMaxPrice());
            
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    vendor.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insert vendor: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean update(VendorDTO vendor) {
        String sql = "UPDATE vendor SET nama=?, kategori=?, kontak=?, min_price=?, max_price=? WHERE id=?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, vendor.getNama());
            stmt.setString(2, vendor.getKategori());
            stmt.setString(3, vendor.getKontak());
            stmt.setDouble(4, vendor.getMinPrice());
            stmt.setDouble(5, vendor.getMaxPrice());
            stmt.setInt(6, vendor.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update vendor: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean deleteById(int id) {
        String sql = "DELETE FROM vendor WHERE id=?";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete vendor: " + e.getMessage());
        }
        return false;
    }
    
    private VendorDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new VendorDTO(
            rs.getInt("id"),
            rs.getString("nama"),
            rs.getString("kategori"),
            rs.getString("kontak"),
            rs.getDouble("min_price"),
            rs.getDouble("max_price")
        );
    }
}
