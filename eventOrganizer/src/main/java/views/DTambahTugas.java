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
import dto.EventVendorDTO;
import controllers.EventVendorController;
import controllers.TaskController;

public class DTambahTugas extends JDialog {
    private int eventId;
    private TaskController taskController;
    private JTextField txtNamaTugas;
    private JComboBox<String> cbVendor;
    private JSpinner dateSpinner;
    private JComboBox<String> cbStatus;
    private JTable taskTable;
    private List<EventVendorDTO> eventVendors;

    public DTambahTugas(JFrame parent, int eventId, TaskController taskController, JTable taskTable) {
        super(parent, "Tambah Tugas Persiapan", true);
        this.eventId = eventId;
        this.taskController = taskController;
        this.taskTable = taskTable;

        setSize(450, 320);
        setLocationRelativeTo(parent);
        initComponents();
        loadEventVendors();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nama Tugas:"));
        txtNamaTugas = new JTextField();
        formPanel.add(txtNamaTugas);

        formPanel.add(new JLabel("Vendor (dari event):"));
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
        cbStatus = new JComboBox<>(new String[]{"belum selesai", "selesai"});
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

    // Ambil vendor dari event ini saja, bukan semua vendor
    private void loadEventVendors() {
        new Thread(() -> {
            EventVendorController eventVendorController = new EventVendorController();
            eventVendors = eventVendorController.getEventVendors(eventId);

            SwingUtilities.invokeLater(() -> {
                if (eventVendors == null || eventVendors.isEmpty()) {
                    // Tidak ada vendor di event ini, disable combo
                    cbVendor.setEnabled(false);
                    cbVendor.removeAllItems();
                    cbVendor.addItem("-- Belum ada vendor di event ini --");
                } else {
                    for (EventVendorDTO ev : eventVendors) {
                        cbVendor.addItem(ev.getId() + " - " + ev.getVendorNama());
                    }
                }
            });
        }).start();
    }

    private void saveTask() {
        // Validasi nama tugas
        String namaTugas = txtNamaTugas.getText().trim();
        if (namaTugas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tugas tidak boleh kosong!");
            return;
        }

        // Validasi deadline tidak boleh null
        if (dateSpinner.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Deadline harus diisi!");
            return;
        }

        // Validasi status
        String status = (String) cbStatus.getSelectedItem();
        if (status == null || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Status harus dipilih!");
            return;
        }

        // Ambil vendor ID dari event vendor yang dipilih
        Integer vendorId = null;
        if (cbVendor.isEnabled() && cbVendor.getSelectedIndex() > 0) {
            int selectedIndex = cbVendor.getSelectedIndex() - 1; 
            if (eventVendors != null && selectedIndex >= 0 && selectedIndex < eventVendors.size()) {
                vendorId = eventVendors.get(selectedIndex).getVendorId();
            }
        }

        Date deadline = new Date(((java.util.Date) dateSpinner.getValue()).getTime());

        taskController.addTask(eventId, namaTugas, vendorId, deadline, status, taskTable);
        dispose();
    }
}