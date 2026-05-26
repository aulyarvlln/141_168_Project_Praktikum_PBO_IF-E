package views;

import controllers.EventController;
import controllers.VendorController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MainFrame extends JFrame {
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JPanel contentPanel;
    private EventController eventController;
    private VendorController vendorController;

    // Palette
    static final Color COL_BG        = Color.WHITE;
    static final Color COL_SIDEBAR    = new Color(15, 23, 42);
    static final Color COL_ACCENT     = new Color(99, 102, 241);
    static final Color COL_ACCENT2    = new Color(236, 72, 153);
    static final Color COL_TEXT       = new Color(15, 23, 42);
    static final Color COL_SUBTEXT    = new Color(100, 116, 139);
    static final Color COL_BORDER     = new Color(226, 232, 240);
    static final Color COL_ROW_EVEN   = new Color(248, 250, 252);
    static final Color COL_ROW_ODD    = Color.WHITE;
    static final Color COL_HOVER      = new Color(238, 242, 255);

    public MainFrame() {
        eventController = new EventController();
        vendorController = new VendorController();
        eventController.setMainFrame(this);

        setTitle("Event Organizer");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(COL_BG);

        initComponents();
        loadEvents();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(COL_SIDEBAR);
        sidebar.setLayout(new BorderLayout());

        JPanel brandPanel = new JPanel();
        brandPanel.setBackground(COL_SIDEBAR);
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        JLabel brandIcon = new JLabel("HA");
        brandIcon.setFont(new Font("Georgia", Font.BOLD, 32));
        brandIcon.setForeground(COL_ACCENT);
        brandIcon.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brandName = new JLabel("Event Organizer");
        brandName.setFont(new Font("SansSerif", Font.BOLD, 14));
        brandName.setForeground(Color.WHITE);
        brandName.setAlignmentX(Component.LEFT_ALIGNMENT);

        brandPanel.add(brandIcon);
        brandPanel.add(Box.createVerticalStrut(6));
        brandPanel.add(brandName);
        brandPanel.add(Box.createVerticalStrut(30));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(30, 41, 59));
        sep.setMaximumSize(new Dimension(180, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        brandPanel.add(sep);

        sidebar.add(brandPanel, BorderLayout.NORTH);

        JPanel navPanel = new JPanel();
        navPanel.setBackground(COL_SIDEBAR);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel navLabel = new JLabel();
        navLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        navLabel.setForeground(new Color(71, 85, 105));
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 8, 0));
        navPanel.add(navLabel);

        navPanel.add(createNavItem("Daftar Event", true));
        navPanel.add(Box.createVerticalStrut(4));
        JPanel vendorNav = createNavItem("Daftar Vendor", false);
        vendorNav.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showVendorList(); }
        });
        navPanel.add(vendorNav);

        sidebar.add(navPanel, BorderLayout.CENTER);

        JPanel sidebarBottom = new JPanel();
        sidebarBottom.setBackground(new Color(10, 15, 30));
        sidebarBottom.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        sidebarBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel verLabel = new JLabel("v1.0.0");
        verLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        verLabel.setForeground(new Color(71, 85, 105));
        sidebarBottom.add(verLabel);
        sidebar.add(sidebarBottom, BorderLayout.SOUTH);

        // ===== MAIN CONTENT =====
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COL_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, COL_BORDER),
            BorderFactory.createEmptyBorder(16, 28, 16, 28)
        ));

        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerLeft.setOpaque(false);
        JLabel pageTitle = new JLabel("Daftar Event");
        pageTitle.setFont(new Font("Georgia", Font.BOLD, 22));
        pageTitle.setForeground(COL_TEXT);
        headerLeft.add(pageTitle);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        headerRight.setOpaque(false);
        JButton btnRefresh = createOutlineButton("Refresh");
        btnRefresh.addActionListener(e -> loadEvents());
        JButton btnAdd = createPrimaryButton("+ Tambah Event");
        btnAdd.addActionListener(e -> eventController.showAddEventDialog());
        headerRight.add(btnRefresh);
        headerRight.add(btnAdd);

        header.add(headerLeft, BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);

        // Table area
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(new Color(248, 250, 252));
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        String[] columns = {"ID", "Nama Event", "Customer", "Tanggal Event", "Status Acara", "Total Akhir"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        eventTable = createStyledTable(tableModel);
        eventTable.addMouseListener(new MouseAdapter() {
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
        scrollPane.setBorder(BorderFactory.createLineBorder(COL_BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);

        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COL_BORDER),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JPanel cardHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        cardHeader.setBackground(Color.WHITE);
        cardHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COL_BORDER));
        JLabel tblTitle = new JLabel("Semua Event");
        tblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        tblTitle.setForeground(COL_TEXT);
        JLabel tblHint = new JLabel("  Double klik baris untuk melihat detail");
        tblHint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        tblHint.setForeground(COL_SUBTEXT);
        cardHeader.add(tblTitle);
        cardHeader.add(tblHint);

        cardPanel.add(cardHeader, BorderLayout.NORTH);
        cardPanel.add(scrollPane, BorderLayout.CENTER);
        tableWrapper.add(cardPanel, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(tableWrapper, BorderLayout.CENTER);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(mainPanel, "main");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createNavItem(String text, boolean active) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        item.setBackground(active ? new Color(30, 41, 59) : COL_SIDEBAR);
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        item.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", active ? Font.BOLD : Font.PLAIN, 13));
        label.setForeground(active ? Color.WHITE : new Color(148, 163, 184));
        item.add(label);

        if (!active) {
            item.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    item.setBackground(new Color(30, 41, 59));
                    label.setForeground(Color.WHITE);
                }
                public void mouseExited(MouseEvent e) {
                    item.setBackground(COL_SIDEBAR);
                    label.setForeground(new Color(148, 163, 184));
                }
            });
        }
        return item;
    }

    static JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setForeground(COL_TEXT);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(COL_HOVER);
        table.setSelectionForeground(COL_TEXT);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(COL_SUBTEXT);
        header.setFont(new Font("SansSerif", Font.BOLD, 11));
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COL_BORDER));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        // Alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                if (!sel) {
                    setBackground(row % 2 == 0 ? COL_ROW_EVEN : COL_ROW_ODD);
                    setForeground(COL_TEXT);
                }
                return this;
            }
        });
        return table;
    }

    static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setText(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(COL_ACCENT);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(79, 70, 229)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(COL_ACCENT); }
        });
        return btn;
    }

    static JButton createOutlineButton(String text) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.setColor(COL_BORDER);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setForeground(COL_TEXT);
        btn.setBackground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(248, 250, 252)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }

    static JButton createDangerButton(String text) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(220, 38, 38));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(185, 28, 28)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(220, 38, 38)); }
        });
        return btn;
    }

    static JTextField createStyledTextField() {
        JTextField tf = new JTextField() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setForeground(COL_TEXT);
        tf.setBackground(new Color(248, 250, 252));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COL_BORDER, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        tf.setOpaque(false);
        return tf;
    }

    static JLabel createFormLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(new Color(51, 65, 85));
        return lbl;
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
        DVendorList dialog = new DVendorList(this, vendorController, null);
        dialog.setVisible(true);
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