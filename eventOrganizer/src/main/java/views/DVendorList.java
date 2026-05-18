/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.VendorController;
import dto.VendorDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author ACER
 */
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
        
        setSize(750, 450);
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
        vendorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Double click to select
        vendorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && callback != null) {
                    selectVendor();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(vendorTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Vendor Tersedia"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        if (callback != null) {
            JButton btnSelect = new JButton("Pilih Vendor");
            btnSelect.setBackground(new Color(46, 204, 113));
            btnSelect.setForeground(Color.WHITE);
            btnSelect.setFocusPainted(false);
            btnSelect.addActionListener(e -> selectVendor());
            buttonPanel.add(btnSelect);
        }
        
        JButton btnAddVendor = new JButton("Tambah Vendor Baru");
        btnAddVendor.setBackground(new Color(52, 152, 219));
        btnAddVendor.setForeground(Color.WHITE);
        btnAddVendor.setFocusPainted(false);
        btnAddVendor.addActionListener(e -> showAddVendorDialog());
        buttonPanel.add(btnAddVendor);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadVendors());
        buttonPanel.add(btnRefresh);
        
        JButton btnCancel = new JButton("Tutup");
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnCancel);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Status bar
        JLabel statusBar = new JLabel(" ");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        add(statusBar, BorderLayout.SOUTH);
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
    
    private void showAddVendorDialog() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Fields
        JTextField txtNama = new JTextField(20);
        JTextField txtKategori = new JTextField(20);
        JTextField txtKontak = new JTextField(20);
        JTextField txtMinPrice = new JTextField(15);
        JTextField txtMaxPrice = new JTextField(15);
        
        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nama Vendor:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNama, gbc);
        
        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Kategori:"), gbc);
        gbc.gridx = 1;
        panel.add(txtKategori, gbc);
        
        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Kontak:"), gbc);
        gbc.gridx = 1;
        panel.add(txtKontak, gbc);
        
        // Row 4
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Min Price (Rp):"), gbc);
        gbc.gridx = 1;
        panel.add(txtMinPrice, gbc);
        
        // Row 5
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Max Price (Rp):"), gbc);
        gbc.gridx = 1;
        panel.add(txtMaxPrice, gbc);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Tambah Vendor Baru", 
                                                   JOptionPane.OK_CANCEL_OPTION, 
                                                   JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            // Validasi
            if (txtNama.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama vendor tidak boleh kosong!");
                return;
            }
            if (txtKategori.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kategori tidak boleh kosong!");
                return;
            }
            
            try {
                VendorDTO newVendor = new VendorDTO();
                newVendor.setNama(txtNama.getText().trim());
                newVendor.setKategori(txtKategori.getText().trim());
                newVendor.setKontak(txtKontak.getText().trim());
                newVendor.setMinPrice(Double.parseDouble(txtMinPrice.getText().trim()));
                newVendor.setMaxPrice(Double.parseDouble(txtMaxPrice.getText().trim()));
                
                boolean success = vendorController.insert(newVendor);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Vendor berhasil ditambahkan!");
                    loadVendors();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan vendor!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Harga harus berupa angka yang valid!");
            }
        }
    }
    
    private String formatRupiah(Double value) {
        if (value == null) return "Rp 0";
        return String.format("Rp %,d", value.longValue());
    }
}
