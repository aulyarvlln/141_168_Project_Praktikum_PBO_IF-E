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
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JPanel contentPanel;
    private EventController eventController;
    private JLabel lblStatus;
    
    public MainFrame() {
        eventController = new EventController();
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
        setLayout(new BorderLayout());
        
        // Menu Bar
        createMenuBar();
        
        // Tool Bar
        createToolBar();
        
        // Content Panel (CardLayout untuk navigasi)
        contentPanel = new JPanel(new CardLayout());
        
        // Main Panel
        JPanel mainPanel = createMainPanel();
        contentPanel.add(mainPanel, "main");
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Status Bar
        createStatusBar();
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu File
        JMenu menuFile = new JMenu("File");
        JMenuItem menuRefresh = new JMenuItem("Refresh");
        menuRefresh.addActionListener(e -> loadEvents());
        JMenuItem menuExit = new JMenuItem("Keluar");
        menuExit.addActionListener(e -> System.exit(0));
        menuFile.add(menuRefresh);
        menuFile.addSeparator();
        menuFile.add(menuExit);
        
        // Menu Master Data
        JMenu menuMaster = new JMenu("Master Data");
        JMenuItem menuVendor = new JMenuItem("Daftar Vendor");
        menuVendor.addActionListener(e -> showVendorList());
        menuMaster.add(menuVendor);
        
        // Menu Help
        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuAbout = new JMenuItem("About");
        menuAbout.addActionListener(e -> showAbout());
        menuHelp.add(menuAbout);
        
        menuBar.add(menuFile);
        menuBar.add(menuMaster);
        menuBar.add(menuHelp);
        
        setJMenuBar(menuBar);
    }
    
    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton btnAdd = new JButton("Tambah Event");
        btnAdd.setIcon(createImageIcon("➕"));
        btnAdd.addActionListener(e -> eventController.showAddEventDialog());
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setIcon(createImageIcon("🔄"));
        btnRefresh.addActionListener(e -> loadEvents());
        
        JButton btnVendor = new JButton("Master Vendor");
        btnVendor.setIcon(createImageIcon("📋"));
        btnVendor.addActionListener(e -> showVendorList());
        
        toolBar.add(btnAdd);
        toolBar.addSeparator();
        toolBar.add(btnRefresh);
        toolBar.addSeparator();
        toolBar.add(btnVendor);
        
        add(toolBar, BorderLayout.NORTH);
    }
    
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("DAFTAR EVENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(41, 128, 185));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Nama Event", "Customer", "Tanggal Event", "Status", "Total Akhir"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventTable = new JTable(tableModel);
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventTable.setRowHeight(30);
        eventTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        eventTable.getTableHeader().setBackground(new Color(52, 73, 94));
        eventTable.getTableHeader().setForeground(Color.WHITE);
        
        // Double click listener untuk detail event
        eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Event List"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton btnAdd = new JButton("+ Tambah Event Baru");
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdd.addActionListener(e -> eventController.showAddEventDialog());
        
        JButton btnDetail = new JButton("Detail Event");
        btnDetail.setBackground(new Color(52, 152, 219));
        btnDetail.setForeground(Color.WHITE);
        btnDetail.setFocusPainted(false);
        btnDetail.addActionListener(e -> openSelectedEventDetail());
        
        JButton btnDelete = new JButton("Hapus Event");
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteSelectedEvent());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDetail);
        buttonPanel.add(btnDelete);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        return mainPanel;
    }
    
    private void createStatusBar() {
        lblStatus = new JLabel(" Ready");
        lblStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        add(lblStatus, BorderLayout.SOUTH);
    }
    
    private void loadEvents() {
        lblStatus.setText(" Loading data event...");
        eventController.loadAllEvents(eventTable, null);
        lblStatus.setText(" Ready - " + tableModel.getRowCount() + " event(s) loaded");
    }
    
    private void openSelectedEventDetail() {
        int row = eventTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih event terlebih dahulu!", 
                                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int eventId = (int) eventTable.getValueAt(row, 0);
        eventController.showEventDetail(eventId);
    }
    
    private void deleteSelectedEvent() {
        int row = eventTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih event yang akan dihapus!", 
                                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int eventId = (int) eventTable.getValueAt(row, 0);
        String eventName = (String) eventTable.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin menghapus event:\n" + eventName + "\n\nSEMUA DATA TERKAIT (vendor & tugas) AKAN IKUT TERHAPUS!",
            "Konfirmasi Hapus Event", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            eventController.deleteEvent(eventId);
        }
    }
    
    private void showVendorList() {
        VendorController vendorController = new VendorController();
        VendorListDialog dialog = new VendorListDialog(this, vendorController, null);
        dialog.setVisible(true);
    }
    
    private void showAbout() {
        String message = "Event Organizer System\nVersion 1.0\n\n" +
                        "Aplikasi Manajemen Event\n" +
                        "Fitur:\n" +
                        "- Kelola Event\n" +
                        "- Kelola Vendor\n" +
                        "- Kelola Tugas Persiapan\n" +
                        "- Multi-threading Support\n\n" +
                        "© 2024 Event Organizer Team";
        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private ImageIcon createImageIcon(String text) {
        // Simple text-based icon for demo
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        return new ImageIcon(label.getGraphicsConfiguration().createCompatibleImage(20, 20));
    }
    
    public void showDetailPanel(JPanel detailPanel) {
        contentPanel.add(detailPanel, "detail");
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "detail");
    }
    
    public void showMainPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "main");
        loadEvents();
    }
    
    public JTable getEventTable() { 
        return eventTable; 
    }
    
    public DefaultListModel<String> getEventListModel() { 
        return null; // Tidak digunakan, pakai table model saja
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
        loadEvents();
    }
}
