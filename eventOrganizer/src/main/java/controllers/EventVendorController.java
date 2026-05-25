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
                        formatRupiah(ev.getHargaPakai()),
                        "Hapus"
                    });
                }
            });
        }).start();
    }

    public void addVendorToEvent(int eventId, VendorDTO vendor, double hargaPakai, 
                                  JTable table, Runnable onTotalUpdated) {
        new Thread(() -> {
            EventVendorDTO ev = new EventVendorDTO(0, eventId, vendor.getId(), hargaPakai);
            
            boolean success = eventVendorModel.insert(ev);

            SwingUtilities.invokeLater(() -> {
                if (success) {
                    updateTotalAndRefresh(eventId, table, onTotalUpdated);
                    JOptionPane.showMessageDialog(null, "Vendor berhasil ditambahkan!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menambahkan vendor!");
                }
            });
        }).start();
    }
    
    public void updateVendorHarga(int eventId, int eventVendorId, double hargaBaru,
                               JTable table, Runnable onTotalUpdated) {
        new Thread(() -> {
            EventVendorDTO ev = eventVendorModel.getById(eventVendorId);
            if (ev != null) {
                ev.setHargaPakai(hargaBaru);
                boolean success = eventVendorModel.update(ev);

                if (success) {
                    eventModel.updateTotalAkhirPrice(eventId);

                    SwingUtilities.invokeLater(() -> {
                        loadEventVendors(eventId, table);
                        if (onTotalUpdated != null) {
                            onTotalUpdated.run();
                        }
                        JOptionPane.showMessageDialog(null, "Harga vendor berhasil diupdate!");
                    });
                } else {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null, "Gagal mengupdate harga!"));
                }
            }
        }).start();
    }

    public void removeVendorFromEvent(int eventId, int eventVendorId, 
                                       JTable table, Runnable onTotalUpdated) {
        new Thread(() -> {
            boolean success = eventVendorModel.deleteById(eventVendorId);

            SwingUtilities.invokeLater(() -> {
                if (success) {
                    // ✅ Sama, pakai method dari Event model
                    updateTotalAndRefresh(eventId, table, onTotalUpdated);
                    JOptionPane.showMessageDialog(null, "Vendor berhasil dihapus!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus vendor!");
                }
            });
        }).start();
    }

    // Ambil total terbaru dari DB
    public double getTotalAkhir(int eventId) {
        return eventModel.getTotalAkhirPrice(eventId);
    }

    private void updateTotalAndRefresh(int eventId, JTable vendorTable, Runnable onTotalUpdated) {
        eventModel.updateTotalAkhirPrice(eventId); // tugasnya model, bukan controller

        SwingUtilities.invokeLater(() -> {
            loadEventVendors(eventId, vendorTable);
            // Panggil callback agar label di panel ikut terupdate
            if (onTotalUpdated != null) {
                onTotalUpdated.run();
            }
        });
    }
    
    private String formatRupiah(double value) {
        return String.format("Rp %,.0f", value).replace(",", ".");
    }
}