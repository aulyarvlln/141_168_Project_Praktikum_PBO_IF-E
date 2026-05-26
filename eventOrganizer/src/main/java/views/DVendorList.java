package views;

import controllers.VendorController;
import dto.VendorDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class DVendorList extends JDialog {
    private JTable vendorTable;
    private DefaultTableModel tableModel;
    private VendorController vendorController;
    private VendorController.VendorSelectCallback callback;

    public DVendorList(JFrame parent, VendorController vendorController,
                       VendorController.VendorSelectCallback callback) {
        super(parent, "Daftar Vendor", true);
        this.vendorController = vendorController;
        this.callback = callback;
        setSize(860, 540);
        setLocationRelativeTo(parent);
        setBackground(Color.WHITE);
        initComponents();
        loadVendors();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(MainFrame.COL_SIDEBAR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel title = new JLabel("Daftar Vendor");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JPanel headerText = new JPanel(new BorderLayout(0, 4));
        headerText.setOpaque(false);
        headerText.add(title, BorderLayout.NORTH);
        headerPanel.add(headerText, BorderLayout.CENTER);

        // ===== TABLE =====
        String[] columns = {"ID", "Nama Vendor", "Kategori", "Kontak", "Min Price", "Max Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        vendorTable = MainFrame.createStyledTable(tableModel);

        // PERUBAHAN: atur auto resize dan lebar kolom agar kontak menyesuaikan sisa ruang
        vendorTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel colModel = vendorTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(40);   // ID
        colModel.getColumn(1).setPreferredWidth(160);  // Nama Vendor
        colModel.getColumn(2).setPreferredWidth(110);  // Kategori
        colModel.getColumn(3).setPreferredWidth(220);  // Kontak (paling lebar)
        colModel.getColumn(4).setPreferredWidth(110);  // Min Price
        colModel.getColumn(5).setPreferredWidth(110);  // Max Price

        JScrollPane scrollPane = new JScrollPane(vendorTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(MainFrame.COL_BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(20, 24, 8, 24));
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        // ===== FOOTER =====
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 16));
        footerPanel.setBackground(new Color(248, 250, 252));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.COL_BORDER));

        if (callback != null) {
            JButton btnSelect = MainFrame.createPrimaryButton("Pilih Vendor");
            btnSelect.addActionListener(e -> selectVendor());
            footerPanel.add(btnSelect);
        }

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(tableWrapper, BorderLayout.CENTER);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
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
            JOptionPane.showMessageDialog(this, "Pilih vendor terlebih dahulu!", "Perhatian", JOptionPane.WARNING_MESSAGE);
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