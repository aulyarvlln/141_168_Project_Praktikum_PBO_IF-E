/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author ACER
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import dto.EventVendorDTO;
import dto.VendorDTO;
import models.EventVendor;
import models.Event;

public class EventVendorController {
    private final EventVendor eventVendorModel;
    private final Event eventModel;

    public EventVendorController() {
        this.eventVendorModel = new EventVendor();
        this.eventModel = new Event();
    }

    public List<EventVendorDTO> getEventVendors(int eventId) {
        return eventVendorModel.getByEventId(eventId);
    }

    public void loadEventVendors(int eventId, JTable table) {
        new Thread(() -> {
            List<EventVendorDTO> vendors = eventVendorModel.getByEventId(eventId);

            SwingUtilities.invokeLater(() -> {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                for (EventVendorDTO ev : vendors) {
                    model.addRow(new Object[]{
                        ev.getId(),
                        ev.getVendorNama(),
                        ev.getVendorKategori(),
                        String.format("Rp %,.0f", ev.getHargaPakai()).replace(",", "."),
                        "Hapus"
                    });
                }
            });
        }).start();
    }

    public void addVendorToEvent(int eventId, VendorDTO vendor, double hargaPakai, 
                                  JTable table, Runnable onTotalUpdated) {
        new Thread(() -> {
            EventVendorDTO ev = new EventVendorDTO();
            ev.setEventId(eventId);
            ev.setVendorId(vendor.getId());
            ev.setHargaPakai(hargaPakai);

            boolean success = eventVendorModel.insert(ev);

            if (success) {
                updateTotalAndRefresh(eventId, table, onTotalUpdated);
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null, "Vendor berhasil ditambahkan!"));
            } else {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null, "Gagal menambahkan vendor!"));
            }
        }).start();
    }

    public void removeVendorFromEvent(int eventId, int eventVendorId, 
                                       JTable table, Runnable onTotalUpdated) {
        new Thread(() -> {
            boolean success = eventVendorModel.deleteById(eventVendorId);

            if (success) {
                updateTotalAndRefresh(eventId, table, onTotalUpdated);
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null, "Vendor berhasil dihapus!"));
            } else {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null, "Gagal menghapus vendor!"));
            }
        }).start();
    }

    // Ambil total terbaru dari DB
    public double getTotalAkhir(int eventId) {
        String sql = "SELECT COALESCE(SUM(harga_pakai), 0) as total FROM event_vendor WHERE event_id = ?";
        try (java.sql.PreparedStatement stmt =
                     eventVendorModel.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error get total: " + e.getMessage());
        }
        return 0.0;
    }

    private void updateTotalAndRefresh(int eventId, JTable vendorTable, Runnable onTotalUpdated) {
        String sql = "UPDATE event e SET e.total_akhir_price = " +
                     "(SELECT COALESCE(SUM(harga_pakai), 0) FROM event_vendor WHERE event_id = ?) " +
                     "WHERE e.id = ?";
        try (java.sql.PreparedStatement stmt =
                     eventVendorModel.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.setInt(2, eventId);
            stmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error update total price: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            loadEventVendors(eventId, vendorTable);
            // Panggil callback agar label di panel ikut terupdate
            if (onTotalUpdated != null) {
                onTotalUpdated.run();
            }
        });
    }
}