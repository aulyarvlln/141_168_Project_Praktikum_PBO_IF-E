/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author ACER
 */

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import dto.VendorDTO;
import controllers.VendorController;
import controllers.TaskController;

public class DTambahTugas extends JDialog {
    private int eventId;
    private TaskController taskController;
    private JTextField txtNamaTugas;
    private JComboBox<String> cbVendor;
    private JSpinner dateSpinner;
    private JComboBox<String> cbStatus;
    private JTable taskTable;
    
    public DTambahTugas(JFrame parent, int eventId, TaskController taskController, JTable taskTable) {
        super(parent, "Tambah Tugas Persiapan", true);
        this.eventId = eventId;
        this.taskController = taskController;
        this.taskTable = taskTable;
        
        setSize(450, 300);
        setLocationRelativeTo(parent);
        initComponents();
        loadVendors();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Nama Tugas:"));
        txtNamaTugas = new JTextField();
        formPanel.add(txtNamaTugas);
        
        formPanel.add(new JLabel("Vendor (opsional):"));
        cbVendor = new JComboBox<>();
        cbVendor.addItem("-- Tidak ada vendor --");
        formPanel.add(cbVendor);
        
        formPanel.add(new JLabel("Deadline:"));
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        formPanel.add(dateSpinner);
        
        formPanel.add(new JLabel("Status Pengerjaan:"));
        cbStatus = new JComboBox<>(new String[]{"belum_mulai", "sedang_berjalan", "selesai", "terlambat"});
        formPanel.add(cbStatus);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnSave = new JButton("Simpan");
        btnSave.addActionListener(e -> saveTask());
        
        JButton btnCancel = new JButton("Batal");
        btnCancel.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadVendors() {
        new Thread(() -> {
            VendorController vendorController = new VendorController();
            List<VendorDTO> vendors = vendorController.getAllVendors();
            
            SwingUtilities.invokeLater(() -> {
                for (VendorDTO v : vendors) {
                    cbVendor.addItem(v.getId() + " - " + v.getNama() + " (" + v.getKategori() + ")");
                }
            });
        }).start();
    }
    
    private void saveTask() {
        String namaTugas = txtNamaTugas.getText().trim();
        if (namaTugas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tugas tidak boleh kosong!");
            return;
        }
        
        // Ambil vendor ID dari combo box
        Integer vendorId = null;
        if (cbVendor.getSelectedIndex() > 0) {
            String selected = (String) cbVendor.getSelectedItem();
            String[] parts = selected.split(" - ");
            vendorId = Integer.parseInt(parts[0]);
        }
        
        Date deadline = new Date(((java.util.Date) dateSpinner.getValue()).getTime());
        String status = (String) cbStatus.getSelectedItem();
        
        taskController.addTask(eventId, namaTugas, vendorId, deadline, status, taskTable);
        dispose();
    }
}
