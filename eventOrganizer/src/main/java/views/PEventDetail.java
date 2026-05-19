/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.EventController;
import controllers.EventVendorController;
import controllers.TaskController;
import controllers.VendorController;
import dto.EventDTO;
import dto.TaskDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;

public class PEventDetail extends JPanel {
    private EventDTO event;
    private EventController eventController;
    private EventVendorController eventVendorController;
    private TaskController taskController;
    private VendorController vendorController;

    private JTable vendorTable;
    private JTable taskTable;
    private DefaultTableModel vendorTableModel;
    private DefaultTableModel taskTableModel;
    private JLabel lblTotalAkhir;
    private JComboBox<String> cbPaymentStatus;
    private JComboBox<String> cbEventStatus;
    private JLabel lblBudget;
    private JLabel lblSisa;

    public PEventDetail(EventDTO event, EventController eventController) {
        this.event = event;
        this.eventController = eventController;
        this.eventVendorController = new EventVendorController();
        this.taskController = new TaskController();
        this.vendorController = new VendorController();

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // ========== TOP PANEL - EVENT INFO ==========
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Informasi Event",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Poppins", Font.BOLD, 14)
        ));
        infoPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(new JLabel("Nama Event:"), gbc);
        gbc.gridx = 1;
        JLabel lblNamaEvent = new JLabel(event.getNamaEvent());
        lblNamaEvent.setFont(new Font("Poppins", Font.BOLD, 12));
        infoPanel.add(lblNamaEvent, gbc);

        gbc.gridx = 2;
        infoPanel.add(new JLabel("Customer:"), gbc);
        gbc.gridx = 3;
        infoPanel.add(new JLabel(event.getNamaCust()), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(new JLabel("Nomor Customer:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(event.getNomorCust()), gbc);

        gbc.gridx = 2;
        infoPanel.add(new JLabel("Tanggal Event:"), gbc);
        gbc.gridx = 3;
        infoPanel.add(new JLabel(event.getTanggalEvent().toString()), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        infoPanel.add(new JLabel("Total Tamu:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(event.getTotalTamu() + " orang"), gbc);

        gbc.gridx = 2;
        infoPanel.add(new JLabel("Budget Customer:"), gbc);
        gbc.gridx = 3;
        lblBudget = new JLabel(formatRupiah(event.getBudgetCust()));
        lblBudget.setFont(new Font("Poppins", Font.BOLD, 12));
        lblBudget.setForeground(new Color(46, 204, 113));
        infoPanel.add(lblBudget, gbc);

        // ========== STATUS PANEL ==========
        JPanel statusPanel = new JPanel(new GridLayout(1, 4, 15, 10));
        statusPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(241, 196, 15), 2),
            "Status Keuangan & Acara",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Poppins", Font.BOLD, 14)
        ));
        statusPanel.setBackground(Color.WHITE);

        // Total Akhir
        JPanel totalPanel = new JPanel(new GridLayout(2, 1));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.add(new JLabel("Total Akhir Price:"));
        double totalAkhir = event.getTotalAkhirPrice();
        lblTotalAkhir = new JLabel(formatRupiah(totalAkhir));
        lblTotalAkhir.setFont(new Font("Poppins", Font.BOLD, 14));
        lblTotalAkhir.setForeground(new Color(231, 76, 60));
        totalPanel.add(lblTotalAkhir);
        statusPanel.add(totalPanel);

        // Sisa Budget
        JPanel sisaPanel = new JPanel(new GridLayout(2, 1));
        sisaPanel.setBackground(Color.WHITE);
        sisaPanel.add(new JLabel("Sisa Budget:"));
        double sisa = event.getBudgetCust() - totalAkhir;
        lblSisa = new JLabel(formatRupiah(sisa));
        lblSisa.setFont(new Font("Poppins", Font.BOLD, 14));
        lblSisa.setForeground(sisa >= 0 ? new Color(46, 204, 113) : new Color(231, 76, 60));
        sisaPanel.add(lblSisa);
        statusPanel.add(sisaPanel);

        // Status Acara
        JPanel statusAcaraPanel = new JPanel(new GridLayout(2, 1));
        statusAcaraPanel.setBackground(Color.WHITE);
        statusAcaraPanel.add(new JLabel("Status Acara:"));
        cbEventStatus = new JComboBox<>(new String[]{"belum selesai", "selesai"});
        cbEventStatus.setSelectedItem(event.getStatusAcara());
        cbEventStatus.addActionListener(e -> updateEventStatus());
        statusAcaraPanel.add(cbEventStatus);
        statusPanel.add(statusAcaraPanel);

        // Payment Status
        JPanel paymentPanel = new JPanel(new GridLayout(2, 1));
        paymentPanel.setBackground(Color.WHITE);
        paymentPanel.add(new JLabel("Payment Status:"));
        cbPaymentStatus = new JComboBox<>(new String[]{"belum_bayar", "lunas"});
        cbPaymentStatus.setSelectedItem(event.getPaymentStatus());
        cbPaymentStatus.addActionListener(e -> updatePaymentStatus());
        paymentPanel.add(cbPaymentStatus);
        statusPanel.add(paymentPanel);

        // ========== VENDOR SECTION ==========
        JPanel vendorPanel = new JPanel(new BorderLayout(5, 5));
        vendorPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
            "Vendor yang Dipakai",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Poppins", Font.BOLD, 14)
        ));
        vendorPanel.setBackground(Color.WHITE);

        String[] vendorColumns = {"ID", "Nama Vendor", "Kategori", "Harga Pakai", "Aksi"};
        vendorTableModel = new DefaultTableModel(vendorColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        vendorTable = new JTable(vendorTableModel);
        vendorTable.setRowHeight(30);
        vendorTable.getColumn("Aksi").setCellRenderer(new ButtonRenderer());
        vendorTable.getColumn("Aksi").setCellEditor(new ButtonEditor(new JCheckBox(), vendorTable, "vendor"));

        JButton btnAddVendor = new JButton("Tambah Vendor ke Event");
        btnAddVendor.setFocusPainted(false);
        btnAddVendor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddVendor.addActionListener(e -> showAddVendorDialog());

        JPanel vendorButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vendorButtonPanel.add(btnAddVendor);
        vendorPanel.add(new JScrollPane(vendorTable), BorderLayout.CENTER);
        vendorPanel.add(vendorButtonPanel, BorderLayout.SOUTH);

        // ========== TASK SECTION ==========
        JPanel taskPanel = new JPanel(new BorderLayout(5, 5));
        taskPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Tugas Persiapan (double klik untuk edit status)",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Poppins", Font.BOLD, 14)
        ));
        taskPanel.setBackground(Color.WHITE);

        String[] taskColumns = {"ID", "Nama Tugas", "Vendor", "Deadline", "Status", "Aksi"};
        taskTableModel = new DefaultTableModel(taskColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        taskTable = new JTable(taskTableModel);
        taskTable.setRowHeight(30);
        taskTable.getColumn("Aksi").setCellRenderer(new ButtonRenderer());
        taskTable.getColumn("Aksi").setCellEditor(new ButtonEditor(new JCheckBox(), taskTable, "task"));

        // Double klik baris tugas untuk edit status
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = taskTable.getSelectedRow();
                    int col = taskTable.getSelectedColumn();
                    // Pastikan bukan klik di kolom Aksi
                    if (row != -1 && col != 5) {
                        int taskId = (int) taskTable.getValueAt(row, 0);
                        String currentStatus = (String) taskTable.getValueAt(row, 4);
                        showEditStatusDialog(taskId, currentStatus);
                    }
                }
            }
        });

        JButton btnAddTask = new JButton("Tambah Tugas Persiapan");
        btnAddTask.setFocusPainted(false);
        btnAddTask.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddTask.addActionListener(e -> showAddTaskDialog());

        JPanel taskButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskButtonPanel.add(btnAddTask);
        taskPanel.add(new JScrollPane(taskTable), BorderLayout.CENTER);
        taskPanel.add(taskButtonPanel, BorderLayout.SOUTH);

        // ========== SPLIT PANE ==========
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vendorPanel, taskPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);

        // ========== BOTTOM BUTTON PANEL ==========
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(new Color(236, 240, 241));

        JButton btnBack = new JButton("Kembali ke Daftar Event");
        btnBack.setFont(new Font("Poppins", Font.BOLD, 12));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> ((MainFrame) SwingUtilities.getWindowAncestor(this)).showMainPanel());

        JButton btnDeleteEvent = new JButton("Hapus Event Ini");
        btnDeleteEvent.setFont(new Font("Poppins", Font.BOLD, 12));
        btnDeleteEvent.setFocusPainted(false);
        btnDeleteEvent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeleteEvent.addActionListener(e -> deleteEvent());

        bottomPanel.add(btnBack);
        bottomPanel.add(btnDeleteEvent);

        // ========== ASSEMBLE ALL ==========
        JPanel topContainer = new JPanel(new BorderLayout(10, 10));
        topContainer.add(infoPanel, BorderLayout.NORTH);
        topContainer.add(statusPanel, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Update label totalAkhir dan sisa budget di panel tanpa reload seluruh panel
    private void refreshStatusLabels() {
        double totalAkhir = eventVendorController.getTotalAkhir(event.getId());
        event.setTotalAkhirPrice(totalAkhir);
        double sisa = event.getBudgetCust() - totalAkhir;

        lblTotalAkhir.setText(formatRupiah(totalAkhir));
        lblSisa.setText(formatRupiah(sisa));
        lblSisa.setForeground(sisa >= 0 ? new Color(46, 204, 113) : new Color(231, 76, 60));
    }

    private void loadData() {
        eventVendorController.loadEventVendors(event.getId(), vendorTable);
        taskController.loadTasks(event.getId(), taskTable);
    }

    private void showAddVendorDialog() {
        vendorController.showVendorListDialog((JFrame) SwingUtilities.getWindowAncestor(this),
            vendor -> {
                JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
                panel.add(new JLabel("Vendor:"));
                panel.add(new JLabel(vendor.getNama()));
                panel.add(new JLabel("Harga Pakai (Rp):"));
                JTextField txtHarga = new JTextField();
                panel.add(txtHarga);

                int result = JOptionPane.showConfirmDialog(this, panel, "Masukkan Harga Vendor",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        double harga = Double.parseDouble(txtHarga.getText());
                        if (harga <= 0) {
                            JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0!");
                            return;
                        }
                        // Kirim callback refreshStatusLabels agar label ikut update
                        eventVendorController.addVendorToEvent(
                            event.getId(), vendor, harga, vendorTable,
                            () -> refreshStatusLabels()
                        );
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Harga tidak valid!");
                    }
                }
            });
    }

    private void showAddTaskDialog() {
        DTambahTugas dialog = new DTambahTugas(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            event.getId(),
            taskController,
            taskTable
        );
        dialog.setVisible(true);
    }

    // Popup edit status tugas (double klik)
    private void showEditStatusDialog(int taskId, String currentStatus) {
        JComboBox<String> cbStatus = new JComboBox<>(
            new String[]{"belum selesai", "selesai"}
        );
        cbStatus.setSelectedItem(currentStatus);

        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.add(new JLabel("Pilih Status Baru:"));
        panel.add(cbStatus);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Edit Status Tugas",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String newStatus = (String) cbStatus.getSelectedItem();
            taskController.updateTaskStatus(taskId, newStatus, event.getId(), taskTable);
        }
    }

    private void deleteVendorFromEvent(int eventVendorId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus vendor ini dari event?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Kirim callback refreshStatusLabels agar label ikut update
            eventVendorController.removeVendorFromEvent(
                event.getId(), eventVendorId, vendorTable,
                () -> refreshStatusLabels()
            );
        }
    }

    private void deleteTask(int taskId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus tugas ini?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            taskController.deleteTask(taskId, event.getId(), taskTable);
        }
    }

    private void updateEventStatus() {
        String newStatus = (String) cbEventStatus.getSelectedItem();
        event.setStatusAcara(newStatus);
        eventController.updateEvent(event);
    }

    private void updatePaymentStatus() {
        String newStatus = (String) cbPaymentStatus.getSelectedItem();
        event.setPaymentStatus(newStatus);
        eventController.updateEvent(event);
    }

    private void deleteEvent() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin hapus event \"" + event.getNamaEvent() + "\"?\n\nSemua vendor dan tugas akan ikut terhapus!",
            "Konfirmasi Hapus Event", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            eventController.deleteEvent(event.getId());
            ((MainFrame) SwingUtilities.getWindowAncestor(this)).showMainPanel();
        }
    }

    private String formatRupiah(double amount) {
        return String.format("Rp %,.0f", amount).replace(",", ".");
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Poppins", Font.BOLD, 10));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Hapus");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String type;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table, String type) {
            super(checkBox);
            this.table = table;
            this.type = type;
            button = new JButton("Hapus");
            button.setOpaque(true);
            button.setFont(new Font("Poppins", Font.BOLD, 10));
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (type.equals("vendor")) {
                int eventVendorId = (int) table.getValueAt(row, 0);
                button.addActionListener(e -> deleteVendorFromEvent(eventVendorId));
            } else if (type.equals("task")) {
                int taskId = (int) table.getValueAt(row, 0);
                button.addActionListener(e -> deleteTask(taskId));
            }
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Hapus";
        }
    }
}