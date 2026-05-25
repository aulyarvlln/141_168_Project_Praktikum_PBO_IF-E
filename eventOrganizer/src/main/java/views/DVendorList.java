/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author ACER
 */

import controllers.VendorController;
import dto.VendorDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DVendorList extends JDialog{
    private JTable vendorTable;
    private DefaultTableModel tableModel;
    private VendorController vendorController;
    private VendorController.VendorSelectCallback callback;
    
    public DVendorList(JFrame parent, VendorController vendorController, 
                            VendorController.VendorSelectCallback callback) {
        super(parent, "Daftar Vendor", true);
        this.vendorController = vendorController;
        this.callback = callback;
        
        setSize(800, 500);
        setLocationRelativeTo(parent);
        initComponents();
        loadVendors();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("DAFTAR VENDOR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Nama Vendor", "Kategori", "Kontak", "Min Price", "Max Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { 
                return false; 
            }
        };
        vendorTable = new JTable(tableModel);
        vendorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vendorTable.setRowHeight(25);
        vendorTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(vendorTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Vendor Tersedia"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Tombol Pilih Vendor
        if (callback != null) {
            JButton btnSelect = new JButton("Pilih Vendor");
            btnSelect.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSelect.addActionListener(e -> selectVendor());
            buttonPanel.add(btnSelect);
        }
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadVendors());
        buttonPanel.add(btnRefresh);
        
        JButton btnCancel = new JButton("Tutup");
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnCancel);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadVendors() {
        new Thread(() -> {
            List<VendorDTO> vendors = vendorController.getAllVendors();
            
            SwingUtilities.invokeLater(() -> {
                tableModel.setRowCount(0);
                for (VendorDTO v : vendors) {
                    tableModel.addRow(new Object[]{
                        v.getId(),
                        v.getNama(),
                        v.getKategori(),
                        v.getKontak(),
                        formatRupiah(v.getMinPrice()),
                        formatRupiah(v.getMaxPrice())
                    });
                }
            });
        }).start();
    }
    
    private void selectVendor() {
        int row = vendorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih vendor terlebih dahulu!", 
                                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int vendorId = (int) vendorTable.getValueAt(row, 0);
        VendorDTO vendor = vendorController.getVendorById(vendorId);
        
        if (callback != null && vendor != null) {
            callback.onVendorSelected(vendor);
            dispose();
        }
    }
    
    private String formatRupiah(Double value) {
        if (value == null) return "Rp 0";
        return String.format("Rp %,d", value.longValue());
    }
}