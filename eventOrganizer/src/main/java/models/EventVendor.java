/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ACER
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.EventVendorDTO;

public class EventVendor extends BaseRepository implements Repository<EventVendorDTO> {
    
    @Override
    protected boolean validateData(Object entity) {
        if (entity == null) return false;
        EventVendorDTO ev = (EventVendorDTO) entity;
        return ev.getEventId() > 0 &&
               ev.getVendorId() > 0 &&
               ev.getHargaPakai() > 0;
    }

    @Override
    protected boolean insertEntity(Object entity) {
        return insert((EventVendorDTO) entity);
    }

    @Override
    protected boolean updateEntity(Object entity) {
        return update((EventVendorDTO) entity);
    }

    @Override
    public List<EventVendorDTO> getAll() {
        List<EventVendorDTO> list = new ArrayList<>();
        String sql = "SELECT ev.*, v.nama as vendor_nama, v.kategori as vendor_kategori " +
                     "FROM event_vendor ev " +
                     "JOIN vendor v ON ev.vendor_id = v.id ORDER BY ev.id";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EventVendorDTO dto = mapResultSetToDTO(rs);
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Error get all event_vendor: " + e.getMessage());
        }
        return list;
    }

    public List<EventVendorDTO> getByEventId(int eventId) {
        List<EventVendorDTO> list = new ArrayList<>();
        String sql = "SELECT ev.*, v.nama as vendor_nama, v.kategori as vendor_kategori " +
                     "FROM event_vendor ev " +
                     "JOIN vendor v ON ev.vendor_id = v.id WHERE ev.event_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EventVendorDTO dto = mapResultSetToDTO(rs);
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Error get event_vendor by event: " + e.getMessage());
        }
        return list;
    }

    @Override
    public EventVendorDTO getById(int id) {
        String sql = "SELECT ev.*, v.nama as vendor_nama, v.kategori as vendor_kategori " +
                     "FROM event_vendor ev " +
                     "JOIN vendor v ON ev.vendor_id = v.id WHERE ev.id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToDTO(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error get event_vendor by id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean insert(EventVendorDTO ev) {
        String sql = "INSERT INTO event_vendor(event_id, vendor_id, harga_pakai) VALUES(?,?,?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ev.getEventId());
            stmt.setInt(2, ev.getVendorId());
            stmt.setDouble(3, ev.getHargaPakai());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    ev.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insert event_vendor: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean update(EventVendorDTO ev) {
        String sql = "UPDATE event_vendor SET harga_pakai=? WHERE id=?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setDouble(1, ev.getHargaPakai());
            stmt.setInt(2, ev.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update event_vendor: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean deleteById(int id) {
        String sql = "DELETE FROM event_vendor WHERE id=?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete event_vendor: " + e.getMessage());
        }
        return false;
    }

    private EventVendorDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        EventVendorDTO dto = new EventVendorDTO(
            rs.getInt("id"),
            rs.getInt("event_id"),
            rs.getInt("vendor_id"),
            rs.getDouble("harga_pakai")
        );
        dto.setVendorNama(rs.getString("vendor_nama"));
        dto.setVendorKategori(rs.getString("vendor_kategori")); // tambah ini
        return dto;
    }
}
