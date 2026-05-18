/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.EventController;
import controllers.VendorController;
import dto.EventDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author ACER
 */
public class MainFrame extends JFrame {
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JPanel contentPanel;
    private EventController eventController;
    private VendorController vendorController;
    
    public MainFrame() {
        eventController = new EventController();
        vendorController = new VendorController();
        eventController.setMainFrame(this);
        
        setTitle("Event Organizer System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        loadEvents();
        
        setVisible(true);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // ========== MENU BAR ==========
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuFile = new JMenu("File");
        JMenuItem menuExit = new JMenuItem("Keluar");
        menuExit.addActionListener(e -> System.exit(0));
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        
        JMenu menuMaster = new JMenu("Master Data");
        JMenuItem menuVendor = new JMenuItem("Daftar Vendor");
        menuVendor.addActionListener(e -> showVendorList());
        menuMaster.add(menuVendor);
        menuBar.add(menuMaster);
        
        JMenu menuHelp = new JMenu("Bantuan");
        JMenuItem menuAbout = new JMenuItem("Tentang Aplikasi");
        menuAbout.addActionListener(e -> showAbout());
        menuHelp.add(menuAbout);
        menuBar.add(menuHelp);
        
        setJMenuBar(menuBar);
        
        // ========== MAIN PANEL ==========
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Daftar Event", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        titleLabel.setForeground(new Color(41, 128, 185));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Table Panel
        String[] columns = {"ID", "Nama Event", "Customer", "Tanggal Event", "Status Acara", "Total Akhir"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventTable = new JTable(tableModel);
        eventTable.setRowHeight(30);
        eventTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
        eventTable.getTableHeader().setBackground(new Color(52, 152, 219));
        eventTable.getTableHeader().setForeground(Color.WHITE);
        eventTable.setSelectionBackground(new Color(173, 216, 230));
        eventTable.setSelectionForeground(Color.BLACK);
        
        // Double click listener untuk detail event
        eventTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = eventTable.getSelectedRow();
                    if (row != -1) {
                        int eventId = (int) eventTable.getValueAt(row, 0);
                        eventController.showEventDetail(eventId);
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Event"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnAdd = new JButton("+ Tambah Event Baru");
        btnAdd.setFont(new Font("Poppins", Font.BOLD, 12));
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> eventController.showAddEventDialog());
        
        JButton btnRefresh = new JButton("⟳ Refresh");
        btnRefresh.setFont(new Font("Poppins", Font.BOLD, 12));
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadEvents());
        
        JButton btnVendorList = new JButton("📋 Daftar Vendor");
        btnVendorList.setFont(new Font("Poppins", Font.BOLD, 12));
        btnVendorList.setBackground(new Color(155, 89, 182));
        btnVendorList.setForeground(Color.WHITE);
        btnVendorList.setFocusPainted(false);
        btnVendorList.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVendorList.addActionListener(e -> showVendorList());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnVendorList);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // ========== CONTENT PANEL WITH CARD LAYOUT ==========
        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(mainPanel, "main");
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Status Bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(new Color(52, 73, 94));
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));
        JLabel statusLabel = new JLabel("  Siap | Double klik event untuk melihat detail");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Poppins", Font.PLAIN, 11));
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private void loadEvents() {
        eventController.loadAllEvents(eventTable, null);
    }
    
    public void showDetailPanel(JPanel detailPanel) {
        contentPanel.add(detailPanel, "detail");
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "detail");
        revalidate();
        repaint();
    }
    
    public void showMainPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "main");
        loadEvents();
        revalidate();
        repaint();
    }
    
    private void showVendorList() {
        VendorListDialog dialog = new VendorListDialog(this, vendorController, null);
        dialog.setVisible(true);
    }
    
    private void showAbout() {
        String message = "Event Organizer System\n\n" +
                         "Versi: 1.0\n" +
                         "Fitur:\n" +
                         "• Kelola Event\n" +
                         "• Kelola Vendor\n" +
                         "• Assign Vendor ke Event\n" +
                         "• Kelola Tugas Persiapan\n" +
                         "• Auto-calculate Total Harga\n\n" +
                         "© 2024 - All Rights Reserved";
        JOptionPane.showMessageDialog(this, message, "Tentang Aplikasi", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public JTable getEventTable() { return eventTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
