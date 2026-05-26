package views;

import controllers.EventController;
import controllers.EventVendorController;
import controllers.TaskController;
import controllers.VendorController;
import dto.EventDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(248, 250, 252));

        // ===== TOP HEADER BAR =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(MainFrame.COL_SIDEBAR);
        topBar.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JPanel topLeft = new JPanel(new BorderLayout(0, 4));
        topLeft.setOpaque(false);

        JLabel evtTitle = new JLabel(event.getNamaEvent());
        evtTitle.setFont(new Font("Georgia", Font.BOLD, 20));
        evtTitle.setForeground(Color.WHITE);

        topLeft.add(evtTitle, BorderLayout.NORTH);
        
        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        topRight.setOpaque(false);

        // PERUBAHAN: warna teks "Kembali" menjadi hitam
        JButton btnBack = MainFrame.createOutlineButton("Kembali");
        btnBack.setForeground(Color.BLACK);
        btnBack.addActionListener(e -> ((MainFrame) SwingUtilities.getWindowAncestor(this)).showMainPanel());

        JButton btnDelete = MainFrame.createDangerButton("Hapus Event");
        btnDelete.addActionListener(e -> deleteEvent());

        topRight.add(btnBack);
        topRight.add(btnDelete);

        topBar.add(topLeft, BorderLayout.WEST);
        topBar.add(topRight, BorderLayout.EAST);

        // ===== ROW 1: INFORMASI CUSTOMER =====
        JPanel customerCard = new JPanel(new BorderLayout(0, 0));
        customerCard.setBackground(Color.WHITE);
        customerCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(99, 102, 241)),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MainFrame.COL_BORDER),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
            )
        ));

        JLabel custSectionTitle = new JLabel("Informasi Customer");
        custSectionTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        custSectionTitle.setForeground(MainFrame.COL_SUBTEXT);
        custSectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // 4 kolom: label | value | label | value
        JPanel custGrid = new JPanel(new GridLayout(2, 4, 8, 6));
        custGrid.setOpaque(false);

        custGrid.add(makeSubLabel("Nama Customer"));
        custGrid.add(makeValueLabel(event.getNamaCust()));
        custGrid.add(makeSubLabel("Nomor Customer"));
        custGrid.add(makeValueLabel(event.getNomorCust()));

        custGrid.add(makeSubLabel("Tanggal Event"));
        custGrid.add(makeValueLabel(event.getTanggalEvent().toString()));
        custGrid.add(makeSubLabel("Total Tamu"));
        custGrid.add(makeValueLabel(event.getTotalTamu() + " orang"));

        customerCard.add(custSectionTitle, BorderLayout.NORTH);
        customerCard.add(custGrid, BorderLayout.CENTER);

        JPanel customerRow = new JPanel(new BorderLayout());
        customerRow.setOpaque(false);
        customerRow.setBorder(BorderFactory.createEmptyBorder(14, 20, 6, 20));
        customerRow.add(customerCard, BorderLayout.CENTER);

        // ===== ROW 2: BUDGET CARDS + STATUS CARDS =====
        double totalAkhir = event.getTotalAkhirPrice();
        double sisa = event.getBudgetCust() - totalAkhir;

        JPanel budgetRow = new JPanel(new GridLayout(1, 5, 12, 0));
        budgetRow.setOpaque(false);
        budgetRow.setBorder(BorderFactory.createEmptyBorder(6, 20, 14, 20));

        budgetRow.add(createInfoCard("Budget Customer",
            formatRupiah(event.getBudgetCust()), new Color(99, 102, 241), null));

        lblTotalAkhir = new JLabel(formatRupiah(totalAkhir));
        lblTotalAkhir.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTotalAkhir.setForeground(new Color(220, 38, 38));
        budgetRow.add(createLabelCard("Total Akhir Price", lblTotalAkhir, new Color(220, 38, 38)));

        lblSisa = new JLabel(formatRupiah(sisa));
        lblSisa.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblSisa.setForeground(sisa >= 0 ? new Color(22, 163, 74) : new Color(220, 38, 38));
        budgetRow.add(createLabelCard("Sisa Budget", lblSisa, sisa >= 0 ? new Color(22, 163, 74) : new Color(220, 38, 38)));

        cbEventStatus = new JComboBox<>(new String[]{"Belum Selesai", "Selesai"});
        cbEventStatus.setSelectedItem(event.getStatusAcara());
        cbEventStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cbEventStatus.setBackground(Color.WHITE);
        cbEventStatus.addActionListener(e -> updateEventStatus());
        budgetRow.add(createComboCard("Status Acara", cbEventStatus, new Color(234, 179, 8)));

        cbPaymentStatus = new JComboBox<>(new String[]{"Belum Bayar", "Lunas"});
        cbPaymentStatus.setSelectedItem(event.getPaymentStatus());
        cbPaymentStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cbPaymentStatus.setBackground(Color.WHITE);
        cbPaymentStatus.addActionListener(e -> updatePaymentStatus());
        budgetRow.add(createComboCard("Payment Status", cbPaymentStatus, new Color(234, 179, 8)));

        // ===== TOP SECTION =====
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);

        JPanel rowsWrapper = new JPanel(new GridLayout(2, 1, 0, 0));
        rowsWrapper.setOpaque(false);
        rowsWrapper.add(customerRow);   // PERUBAHAN: pakai customerRow (bukan custGrid langsung)
        rowsWrapper.add(budgetRow);

        topSection.add(topBar, BorderLayout.NORTH);
        topSection.add(rowsWrapper, BorderLayout.CENTER);

        // ===== VENDOR TABLE PANEL =====
        JPanel vendorCard = createSectionCard("Vendor yang Dipakai", new Color(155, 89, 182));

        String[] vendorColumns = {"ID", "Nama Vendor", "Kategori", "Harga Pakai", "Aksi"};
        vendorTableModel = new DefaultTableModel(vendorColumns, 0) {
            public boolean isCellEditable(int row, int column) { return column == 4; }
        };
        vendorTable = MainFrame.createStyledTable(vendorTableModel);
        vendorTable.getColumn("Aksi").setCellRenderer(new ButtonRenderer());
        vendorTable.getColumn("Aksi").setCellEditor(new ButtonEditor(new JCheckBox(), vendorTable, "vendor"));
        vendorTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = vendorTable.getSelectedRow();
                    int col = vendorTable.getSelectedColumn();
                    if (row != -1 && col != 4) {
                        int eventVendorId = (int) vendorTable.getValueAt(row, 0);
                        String hargaStr = vendorTable.getValueAt(row, 3).toString();
                        double currentHarga = Double.parseDouble(hargaStr.replace("Rp ", "").replace(".", ""));
                        showEditHargaDialog(eventVendorId, currentHarga);
                    }
                }
            }
        });

        JButton btnAddVendor = MainFrame.createPrimaryButton("+ Tambah Vendor");
        btnAddVendor.addActionListener(e -> showAddVendorDialog());

        JScrollPane vendorScroll = new JScrollPane(vendorTable);
        vendorScroll.setBorder(BorderFactory.createLineBorder(MainFrame.COL_BORDER));
        vendorScroll.getViewport().setBackground(Color.WHITE);

        JPanel vendorBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));
        vendorBtnPanel.setOpaque(false);
        vendorBtnPanel.add(btnAddVendor);

        vendorCard.add(vendorScroll, BorderLayout.CENTER);
        vendorCard.add(vendorBtnPanel, BorderLayout.SOUTH);

        // ===== TASK TABLE PANEL =====
        JPanel taskCard = createSectionCard("Tugas Persiapan  (double klik baris untuk edit status)", new Color(99, 102, 241));

        String[] taskColumns = {"ID", "Nama Tugas", "Vendor", "Deadline", "Status", "Aksi"};
        taskTableModel = new DefaultTableModel(taskColumns, 0) {
            public boolean isCellEditable(int row, int column) { return column == 5; }
        };
        taskTable = MainFrame.createStyledTable(taskTableModel);
        taskTable.getColumn("Aksi").setCellRenderer(new ButtonRenderer());
        taskTable.getColumn("Aksi").setCellEditor(new ButtonEditor(new JCheckBox(), taskTable, "task"));
        taskTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = taskTable.getSelectedRow();
                    int col = taskTable.getSelectedColumn();
                    if (row != -1 && col != 5) {
                        int taskId = (int) taskTable.getValueAt(row, 0);
                        String currentStatus = (String) taskTable.getValueAt(row, 4);
                        showEditStatusDialog(taskId, currentStatus);
                    }
                }
            }
        });

        JButton btnAddTask = MainFrame.createPrimaryButton("+ Tambah Tugas");
        btnAddTask.addActionListener(e -> showAddTaskDialog());

        JScrollPane taskScroll = new JScrollPane(taskTable);
        taskScroll.setBorder(BorderFactory.createLineBorder(MainFrame.COL_BORDER));
        taskScroll.getViewport().setBackground(Color.WHITE);

        JPanel taskBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));
        taskBtnPanel.setOpaque(false);
        taskBtnPanel.add(btnAddTask);

        taskCard.add(taskScroll, BorderLayout.CENTER);
        taskCard.add(taskBtnPanel, BorderLayout.SOUTH);

        // ===== SPLIT PANE =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vendorCard, taskCard);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);
        splitPane.setDividerSize(6);
        splitPane.setBackground(new Color(248, 250, 252));

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(new Color(248, 250, 252));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(12, 20, 16, 20));
        centerWrapper.add(splitPane, BorderLayout.CENTER);

        add(topSection, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }

    private JPanel createInfoCard(String label, String value, Color accent, Color valueFg) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, accent),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MainFrame.COL_BORDER),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
            )
        ));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(MainFrame.COL_SUBTEXT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.BOLD, 13));
        val.setForeground(valueFg != null ? valueFg : MainFrame.COL_TEXT);

        card.add(lbl, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);
        return card;
    }

    private JPanel createLabelCard(String label, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, accent),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MainFrame.COL_BORDER),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
            )
        ));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(MainFrame.COL_SUBTEXT);

        card.add(lbl, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createComboCard(String label, JComboBox<String> combo, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, accent),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MainFrame.COL_BORDER),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
            )
        ));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(MainFrame.COL_SUBTEXT);

        card.add(lbl, BorderLayout.NORTH);
        card.add(combo, BorderLayout.CENTER);
        return card;
    }

    private JPanel createSectionCard(String title, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(MainFrame.COL_BORDER));

        JPanel cardHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        cardHeader.setBackground(new Color(248, 250, 252));
        cardHeader.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, accent),
            BorderFactory.createMatteBorder(0, 0, 1, 0, MainFrame.COL_BORDER)
        ));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        titleLabel.setForeground(MainFrame.COL_TEXT);
        cardHeader.add(titleLabel);

        card.add(cardHeader, BorderLayout.NORTH);
        return card;
    }

    private void refreshStatusLabels() {
        EventDTO updatedEvent = eventController.getEventById(event.getId());
        if (updatedEvent != null) {
            double totalAkhir = updatedEvent.getTotalAkhirPrice();
            double sisa = event.getBudgetCust() - totalAkhir;
            lblTotalAkhir.setText(formatRupiah(totalAkhir));
            lblSisa.setText(formatRupiah(sisa));
            lblSisa.setForeground(sisa >= 0 ? new Color(22, 163, 74) : new Color(220, 38, 38));
        }
    }

    private void loadData() {
        eventVendorController.loadEventVendors(event.getId(), vendorTable);
        taskController.loadTasks(event.getId(), taskTable);
    }

    private void showAddVendorDialog() {
        vendorController.showVendorListDialog((JFrame) SwingUtilities.getWindowAncestor(this),
            vendor -> {
                double currentTotal = eventController.getEventById(event.getId()).getTotalAkhirPrice();
                double currentSisa = event.getBudgetCust() - currentTotal;

                JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
                panel.add(MainFrame.createFormLabel("Vendor:"));
                panel.add(new JLabel(vendor.getNama()));
                panel.add(MainFrame.createFormLabel("Harga Pakai (Rp):"));
                JTextField txtHarga = MainFrame.createStyledTextField();
                panel.add(txtHarga);
                panel.add(MainFrame.createFormLabel("Sisa Budget Saat Ini:"));
                panel.add(new JLabel(formatRupiah(currentSisa)));

                int result = JOptionPane.showConfirmDialog(this, panel, "Masukkan Harga Vendor",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        double harga = Double.parseDouble(txtHarga.getText());
                        if (harga <= 0) { JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0!"); return; }
                        if (harga < vendor.getMinPrice()) {
                            JOptionPane.showMessageDialog(this, "Harga terlalu rendah! Minimal " + formatRupiah(vendor.getMinPrice())); return;
                        }
                        if (harga > vendor.getMaxPrice()) {
                            JOptionPane.showMessageDialog(this, "Harga terlalu tinggi! Maksimal " + formatRupiah(vendor.getMaxPrice())); return;
                        }
                        double sisaSetelah = currentSisa - harga;
                        if (sisaSetelah < 0) {
                            int confirm = JOptionPane.showConfirmDialog(this,
                                "Peringatan: Sisa budget akan menjadi " + formatRupiah(sisaSetelah) + " (minus)!\n" +
                                "Apakah Anda tetap ingin menambahkan vendor ini?",
                                "Budget Tidak Cukup", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (confirm != JOptionPane.YES_OPTION) return;
                        }
                        eventVendorController.addVendorToEvent(event.getId(), vendor, harga, vendorTable, () -> refreshStatusLabels());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Harga tidak valid!");
                    }
                }
            });
    }

    private void showAddTaskDialog() {
        DTambahTugas dialog = new DTambahTugas(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            event.getId(), taskController, taskTable
        );
        dialog.setVisible(true);
    }

    private void showEditStatusDialog(int taskId, String currentStatus) {
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"belum selesai", "selesai"});
        cbStatus.setSelectedItem(currentStatus);

        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.add(MainFrame.createFormLabel("Pilih Status Baru:"));
        panel.add(cbStatus);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Status Tugas",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String newStatus = (String) cbStatus.getSelectedItem();
            taskController.updateTaskStatus(taskId, newStatus, event.getId(), taskTable);
        }
    }

    private void deleteVendorFromEvent(int eventVendorId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus vendor ini dari event?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            eventVendorController.removeVendorFromEvent(event.getId(), eventVendorId, vendorTable, () -> refreshStatusLabels());
        }
    }

    private void showEditHargaDialog(int eventVendorId, double currentHarga) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(MainFrame.createFormLabel("Harga Saat Ini:"));
        panel.add(new JLabel(formatRupiah(currentHarga)));
        panel.add(MainFrame.createFormLabel("Harga Baru (Rp):"));
        JTextField txtHargaBaru = MainFrame.createStyledTextField();
        panel.add(txtHargaBaru);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Harga Vendor",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double hargaBaru = Double.parseDouble(txtHargaBaru.getText());
                if (hargaBaru <= 0) { JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0!"); return; }
                eventVendorController.updateVendorHarga(event.getId(), eventVendorId, hargaBaru, vendorTable, () -> refreshStatusLabels());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Harga tidak valid!");
            }
        }
    }

    private void deleteTask(int taskId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus tugas ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
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

    // PERUBAHAN: makeSubLabel tetap sama (plain, subtext color)
    private JLabel makeSubLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(MainFrame.COL_SUBTEXT);
        return lbl;
    }

    // makeValueLabel tetap ada (masih dipakai di tempat lain jika perlu)
    private JLabel makeValueLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(MainFrame.COL_TEXT);
        return lbl;
    }

    private String formatRupiah(double amount) {
        return String.format("Rp %,.0f", amount).replace(",", ".");
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("SansSerif", Font.BOLD, 10));
            setBackground(new Color(254, 242, 242));
            setForeground(new Color(220, 38, 38));
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(254, 202, 202)),
                BorderFactory.createEmptyBorder(3, 10, 3, 10)
            ));
        }
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
            button.setFont(new Font("SansSerif", Font.BOLD, 10));
            button.setBackground(new Color(254, 242, 242));
            button.setForeground(new Color(220, 38, 38));
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(254, 202, 202)),
                BorderFactory.createEmptyBorder(3, 10, 3, 10)
            ));
            button.addActionListener(e -> fireEditingStopped());
        }

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

        public Object getCellEditorValue() { return "Hapus"; }
    }
}