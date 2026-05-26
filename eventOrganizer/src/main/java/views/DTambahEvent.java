package views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
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
        setSize(460, 480);
        setLocationRelativeTo(parent);
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(MainFrame.COL_SIDEBAR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel title = new JLabel("Tambah Event Baru");
        title.setFont(new Font("Georgia", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Isi semua informasi event dengan lengkap");
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

        txtNamaEvent    = MainFrame.createStyledTextField();
        txtNamaCustomer = MainFrame.createStyledTextField();
        txtNomorCust    = MainFrame.createStyledTextField();
        txtBudget       = MainFrame.createStyledTextField();
        txtTotalTamu    = MainFrame.createStyledTextField();

        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateSpinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.COL_BORDER, 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        addFormRow(formPanel, "Nama Event", txtNamaEvent);
        addFormRow(formPanel, "Nama Customer", txtNamaCustomer);
        addFormRow(formPanel, "Nomor Customer", txtNomorCust);
        addFormRowSpinner(formPanel, "Tanggal Event", dateSpinner);
        addFormRow(formPanel, "Budget Customer (Rp)", txtBudget);
        addFormRow(formPanel, "Total Tamu", txtTotalTamu);

        // ===== FOOTER BUTTONS =====
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 16));
        footerPanel.setBackground(new Color(248, 250, 252));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.COL_BORDER));

        JButton btnCancel = MainFrame.createOutlineButton("Batal");
        btnCancel.addActionListener(e -> dispose());

        JButton btnSave = MainFrame.createPrimaryButton("Simpan Event");
        btnSave.addActionListener(e -> saveEvent());

        footerPanel.add(btnCancel);
        footerPanel.add(btnSave);

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(new JScrollPane(formPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void addFormRow(JPanel panel, String label, JComponent field) {
        JLabel lbl = MainFrame.createFormLabel(label);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(field);
        panel.add(Box.createVerticalStrut(14));
    }

    private void addFormRowSpinner(JPanel panel, String label, JSpinner spinner) {
        JLabel lbl = MainFrame.createFormLabel(label);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        spinner.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(spinner);
        panel.add(Box.createVerticalStrut(14));
    }

    private void saveEvent() {
        if (txtNamaEvent.getText().isEmpty() || txtNamaCustomer.getText().isEmpty() ||
            txtNomorCust.getText().isEmpty() || txtBudget.getText().isEmpty() || txtTotalTamu.getText().isEmpty()) {
            showWarning("Semua field harus diisi!");
            return;
        }

        try {
            double budget = Double.parseDouble(txtBudget.getText());
            int totalTamu = Integer.parseInt(txtTotalTamu.getText());

            if (budget <= 0) { showWarning("Budget harus lebih dari 0!"); return; }
            if (totalTamu <= 0) { showWarning("Total tamu harus lebih dari 0!"); return; }

            EventDTO event = new EventDTO(
                0,
                txtNamaEvent.getText(),
                new Date(((java.util.Date) dateSpinner.getValue()).getTime()),
                txtNamaCustomer.getText(),
                txtNomorCust.getText(),
                budget,
                totalTamu,
                "Belum Selesai",
                0.0,
                "Belum Bayar"
            );

            eventController.saveEvent(event, this);

        } catch (NumberFormatException e) {
            showWarning("Budget dan Total Tamu harus berupa angka!");
        }
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Perhatian", JOptionPane.WARNING_MESSAGE);
    }
}