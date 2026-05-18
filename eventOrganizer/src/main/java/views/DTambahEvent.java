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
import dto.EventDTO;
import controllers.EventController;

public class DTambahEvent extends JDialog {
    private JTextField txtNamaEvent, txtNamaCustomer, txtNomorCust, txtBudget, txtTotalTamu;
    private JSpinner dateSpinner;
    private EventController eventController;
    
    public DTambahEvent(JFrame parent, EventController eventController) {
        super(parent, "Tambah Event Baru", true);
        this.eventController = eventController;
        
        setSize(400, 350);
        setLocationRelativeTo(parent);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Nama Event:"));
        txtNamaEvent = new JTextField();
        formPanel.add(txtNamaEvent);
        
        formPanel.add(new JLabel("Nama Customer:"));
        txtNamaCustomer = new JTextField();
        formPanel.add(txtNamaCustomer);
        
        formPanel.add(new JLabel("Nomor Customer:"));
        txtNomorCust = new JTextField();
        formPanel.add(txtNomorCust);
        
        formPanel.add(new JLabel("Tanggal Event:"));
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        formPanel.add(dateSpinner);
        
        formPanel.add(new JLabel("Budget Customer:"));
        txtBudget = new JTextField();
        formPanel.add(txtBudget);
        
        formPanel.add(new JLabel("Total Tamu:"));
        txtTotalTamu = new JTextField();
        formPanel.add(txtTotalTamu);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnSave = new JButton("Simpan");
        btnSave.addActionListener(e -> saveEvent());
        
        JButton btnCancel = new JButton("Batal");
        btnCancel.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void saveEvent() {
        if (txtNamaEvent.getText().isEmpty() || txtNamaCustomer.getText().isEmpty() ||
            txtNomorCust.getText().isEmpty() || txtBudget.getText().isEmpty() || txtTotalTamu.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }
        
        EventDTO event = new EventDTO();
        event.setNamaEvent(txtNamaEvent.getText());
        event.setNamaCust(txtNamaCustomer.getText());
        event.setNomorCust(txtNomorCust.getText());
        event.setTanggalEvent(new Date(((java.util.Date) dateSpinner.getValue()).getTime()));
        event.setBudgetCust(Double.parseDouble(txtBudget.getText()));
        event.setTotalTamu(Integer.parseInt(txtTotalTamu.getText()));
        event.setStatusAcara("belum selesai");
        event.setPaymentStatus("belum_bayar");
        
        eventController.saveEvent(event, this);
    }
}
