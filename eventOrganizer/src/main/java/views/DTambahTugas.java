package views;

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
        setSize(460, 420);
        setLocationRelativeTo(parent);
        setBackground(Color.WHITE);
        initComponents();
        loadEventVendors();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(MainFrame.COL_SIDEBAR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel title = new JLabel("Tambah Tugas Persiapan");
        title.setFont(new Font("Georgia", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Isi detail tugas yang perlu dikerjakan");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setForeground(new Color(148, 163, 184));

        JPanel headerText = new JPanel(new BorderLayout(0, 4));
        headerText.setOpaque(false);
        headerText.add(title, BorderLayout.NORTH);
        headerText.add(subtitle, BorderLayout.SOUTH);
        headerPanel.add(headerText, BorderLayout.CENTER);

        // ===== FORM =====
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 10, 24));

        // Nama Tugas
        txtNamaTugas = MainFrame.createStyledTextField();
        addFormRow(formPanel, "Nama Tugas", txtNamaTugas);

        // Vendor ComboBox
        cbVendor = new JComboBox<>();
        cbVendor.addItem("-- Tidak ada vendor --");
        cbVendor.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbVendor.setBackground(new Color(248, 250, 252));
        cbVendor.setBorder(BorderFactory.createLineBorder(MainFrame.COL_BORDER, 1));
        addFormRowComponent(formPanel, "Vendor (dari event)", cbVendor);

        // Deadline Spinner
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateSpinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.COL_BORDER, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        addFormRowComponent(formPanel, "Deadline", dateSpinner);

        // Status ComboBox
        cbStatus = new JComboBox<>(new String[]{"Belum Selesai", "Selesai"});
        cbStatus.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbStatus.setBackground(new Color(248, 250, 252));
        cbStatus.setBorder(BorderFactory.createLineBorder(MainFrame.COL_BORDER, 1));
        addFormRowComponent(formPanel, "Status Pengerjaan", cbStatus);

        // ===== FOOTER =====
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 16));
        footerPanel.setBackground(new Color(248, 250, 252));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.COL_BORDER));

        JButton btnCancel = MainFrame.createOutlineButton("Batal");
        btnCancel.addActionListener(e -> dispose());

        JButton btnSave = MainFrame.createPrimaryButton("Simpan Tugas");
        btnSave.addActionListener(e -> saveTask());

        footerPanel.add(btnCancel);
        footerPanel.add(btnSave);

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(formPanel, BorderLayout.CENTER);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void addFormRow(JPanel panel, String label, JTextField field) {
        JLabel lbl = MainFrame.createFormLabel(label);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);
        panel.add(field);
        panel.add(Box.createVerticalStrut(14));
    }

    private void addFormRowComponent(JPanel panel, String label, JComponent comp) {
        JLabel lbl = MainFrame.createFormLabel(label);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);
        panel.add(comp);
        panel.add(Box.createVerticalStrut(14));
    }

    private void loadEventVendors() {
        new Thread(() -> {
            EventVendorController eventVendorController = new EventVendorController();
            eventVendors = eventVendorController.getEventVendors(eventId);

            SwingUtilities.invokeLater(() -> {
                if (eventVendors == null || eventVendors.isEmpty()) {
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
        String namaTugas = txtNamaTugas.getText().trim();
        if (namaTugas.isEmpty()) {
            showWarning("Nama tugas tidak boleh kosong!");
            return;
        }
        if (dateSpinner.getValue() == null) {
            showWarning("Deadline harus diisi!");
            return;
        }
        int selectedIndex = cbVendor.getSelectedIndex();
        if (selectedIndex <= 0 || eventVendors == null || eventVendors.isEmpty()) {
            showWarning("Pilih vendor terlebih dahulu!");
            return;
        }

        String status = (String) cbStatus.getSelectedItem();
        int vendorIndex = selectedIndex - 1;
        EventVendorDTO selectedVendor = eventVendors.get(vendorIndex);
        int vendorId = selectedVendor.getVendorId();
        Date deadline = new Date(((java.util.Date) dateSpinner.getValue()).getTime());

        taskController.addTask(eventId, namaTugas, vendorId, deadline, status, taskTable);
        dispose();
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Perhatian", JOptionPane.WARNING_MESSAGE);
    }
}