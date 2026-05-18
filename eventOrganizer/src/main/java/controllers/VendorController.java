/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dto.VendorDTO;
import models.Vendor;
import views.DVendorList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author ACER
 */
public class VendorController {
    private final Vendor vendorModel;
    
    public VendorController() {
        this.vendorModel = new Vendor();
    }
    
    public void loadAllVendors(JTable table) {
        new Thread(() -> {
           List<VendorDTO> vendors = vendorModel.getAll();
            
            SwingUtilities.invokeLater(() -> {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                
                for (VendorDTO v : vendors) {
                    model.addRow(new Object[]{
                        v.getId(),
                        v.getNama(),
                        v.getKategori(),
                        v.getKontak(),
                        "Rp " + v.getMinPrice() + " - Rp " + v.getMaxPrice()
                    });
                } 
            });
        }).start();
    }
    
    public void showVendorListDialog(JFrame parent, VendorSelectCallback callback) {
        DVendorList dialog = new DVendorList (parent, this, callback);
        dialog.setVisible(true);
    }
    
    public List<VendorDTO> getAllVendors() {
        return vendorModel.getAll();
    }
    
    public VendorDTO getVendorById(int id) {
        return vendorModel.getById(id);
    }

    public boolean insert(VendorDTO newVendor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public interface VendorSelectCallback {
        void onVendorSelected(VendorDTO vendor);
    }
}
