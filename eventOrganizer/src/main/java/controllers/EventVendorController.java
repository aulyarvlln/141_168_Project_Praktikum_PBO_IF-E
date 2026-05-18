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
    
    // MULTITHREADING: Load event vendors di thread terpisah
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
                        "Rp " + ev.getHargaPakai()
                    });
                }
            });
        }).start();
    }
    
    public void addVendorToEvent(int eventId, VendorDTO vendor, double hargaPakai, JTable table) {
        // MULTITHREADING: Add vendor di thread terpisah
        new Thread(() -> {
            EventVendorDTO ev = new EventVendorDTO();
            ev.setEventId(eventId);
            ev.setVendorId(vendor.getId());
            ev.setHargaPakai(hargaPakai);
            
            boolean success = eventVendorModel.insert(ev);
            
            if (success) {
                // Update total akhir price event
                eventModel.updateTotalAkhirPrice(eventId);
            }
            
            SwingUtilities.invokeLater(() -> {
                if (success) {
                    loadEventVendors(eventId, table);
                    JOptionPane.showMessageDialog(null, "Vendor berhasil ditambahkan ke event!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menambahkan vendor!");
                }
            });
        }).start();
    }
    
    public void removeVendorFromEvent(int eventId, int eventVendorId, JTable table) {
        new Thread(() -> {
            boolean success = eventVendorModel.deleteById(eventVendorId);
            
            if (success) {
                eventModel.updateTotalAkhirPrice(eventId);
            }
            
            SwingUtilities.invokeLater(() -> {
                if (success) {
                    loadEventVendors(eventId, table);
                    JOptionPane.showMessageDialog(null, "Vendor berhasil dihapus dari event!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus vendor!");
                }
            });
        }).start();
    }
}
