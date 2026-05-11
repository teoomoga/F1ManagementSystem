package org.example.view;

import org.example.controller.DriverController;
import org.example.model.entity.Driver;
import org.example.model.repository.UserRepository;
import org.example.model.service.export.CsvExportStrategy;
import org.example.model.service.export.ExportService;
import org.example.model.service.export.JsonExportStrategy;
import org.example.model.service.export.XmlExportStrategy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainView extends JFrame {
    private DriverController controller = new DriverController();
    private UserRepository userRepository = new UserRepository();
    // Design Pattern: Strategy - Serviciul de export
    private ExportService exportService = new ExportService();

    private String role;
    private String managedTeam;
    private String currentUserEmail;

    private DefaultTableModel modelManage;
    private JTable tableManage;
    private JTextField txtName = new JTextField(10);
    private JTextField txtTeamAdmin = new JTextField(10);
    private JTextField txtNationality = new JTextField(10);
    private JTextField txtPoints = new JTextField(5);
    private JButton btnAdd = new JButton("Adaugă");
    private JButton btnUpdate = new JButton("Update");
    private JButton btnDelete = new JButton("Șterge");
    private JButton btnTransfer = new JButton("Transfer");

    private DefaultTableModel modelAll;
    private JTable tableAll;
    private JTextField txtSearch = new JTextField(15);
    private JButton btnSearch = new JButton("Caută Echipă");
    private JButton btnRanking = new JButton("Show Standings");

    public MainView(String role, String managedTeam, String currentUserEmail) {
        this.role = role;
        this.managedTeam = managedTeam;
        this.currentUserEmail = currentUserEmail;

        setTitle("F1 Racing - " + role + " | Logged as: " + currentUserEmail);
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        // Cerința 1.1: Administrare bazată pe roluri [cite: 8, 10, 11]
        if (!role.equals("VISITOR")) {
            tabbedPane.addTab("Consolă Gestiune", createManagementTab());
        }
        tabbedPane.addTab("Clasament & Explorare", createExplorerTab());

        add(tabbedPane, BorderLayout.CENTER);

        JPanel south = new JPanel();
        JButton btnLogout = new JButton("Log Out");
        btnLogout.addActionListener(e -> { this.dispose(); new LoginView().setVisible(true); });
        south.add(btnLogout);
        add(south, BorderLayout.SOUTH);

        configPermisiuni();
        refreshData();
    }

    private JPanel createManagementTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();

        inputPanel.add(new JLabel("Nume:")); inputPanel.add(txtName);
        if (role.equals("ADMIN")) {
            inputPanel.add(new JLabel("Echipă:")); inputPanel.add(txtTeamAdmin);
        }
        inputPanel.add(new JLabel("Naț.:")); inputPanel.add(txtNationality);
        inputPanel.add(new JLabel("Pct:")); inputPanel.add(txtPoints);

        inputPanel.add(btnAdd); inputPanel.add(btnUpdate);
        inputPanel.add(btnDelete); inputPanel.add(btnTransfer);

        panel.add(inputPanel, BorderLayout.NORTH);

        modelManage = new DefaultTableModel(new String[]{"ID", "Nume", "Echipă", "Naț.", "Pct"}, 0);
        tableManage = new JTable(modelManage);
        panel.add(new JScrollPane(tableManage), BorderLayout.CENTER);

        // Eveniment de creare/update - va declanșa Observer [cite: 29, 30, 31]
        btnAdd.addActionListener(e -> {
            String team = role.equals("ADMIN") ? txtTeamAdmin.getText() : managedTeam;
            controller.handleUpdateDriver(0, txtName.getText(), team, Integer.parseInt(txtPoints.getText()), txtNationality.getText(), currentUserEmail);
            refreshData();
        });

        btnUpdate.addActionListener(e -> {
            int row = tableManage.getSelectedRow();
            if (row != -1) {
                int id = (int) modelManage.getValueAt(row, 0);
                String team = role.equals("ADMIN") ? txtTeamAdmin.getText() : managedTeam;
                controller.handleUpdateDriver(id, txtName.getText(), team, Integer.parseInt(txtPoints.getText()), txtNationality.getText(), currentUserEmail);
                refreshData();
            }
        });

        btnDelete.addActionListener(e -> {
            int row = tableManage.getSelectedRow();
            if (row != -1) {
                controller.handleDeleteDriver((int) modelManage.getValueAt(row, 0), currentUserEmail);
                refreshData();
            }
        });

        btnTransfer.addActionListener(e -> {
            int row = tableManage.getSelectedRow();
            if (row != -1) {
                String newTeam = JOptionPane.showInputDialog("Noua echipă:");
                if (newTeam != null) {
                    controller.handleTransfer((int) modelManage.getValueAt(row, 0), newTeam, currentUserEmail);
                    refreshData();
                }
            }
        });

        tableManage.getSelectionModel().addListSelectionListener(e -> {
            int row = tableManage.getSelectedRow();
            if (row != -1) {
                txtName.setText(modelManage.getValueAt(row, 1).toString());
                if(role.equals("ADMIN")) txtTeamAdmin.setText(modelManage.getValueAt(row, 2).toString());
                txtNationality.setText(modelManage.getValueAt(row, 3).toString());
                txtPoints.setText(modelManage.getValueAt(row, 4).toString());
            }
        });

        return panel;
    }

    private JPanel createExplorerTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Caută Echipă:")); topPanel.add(txtSearch);
        topPanel.add(btnSearch); topPanel.add(btnRanking);
        panel.add(topPanel, BorderLayout.NORTH);

        modelAll = new DefaultTableModel(new String[]{"ID", "Pilot", "Echipă", "Pct", "Naționalitate"}, 0);
        tableAll = new JTable(modelAll);
        panel.add(new JScrollPane(tableAll), BorderLayout.CENTER);

        // --- COD PENTRU EXPORT (Cerința 1.3 - Assignment 2) --- [cite: 36, 37]
        JPanel exportPanel = new JPanel();
        JButton btnJson = new JButton("Export JSON");
        JButton btnXml = new JButton("Export XML");
        JButton btnCsv = new JButton("Export CSV");

        exportPanel.add(new JLabel("Descarcă Clasament:"));
        exportPanel.add(btnJson);
        exportPanel.add(btnXml);
        exportPanel.add(btnCsv);
        panel.add(exportPanel, BorderLayout.SOUTH);

        // Utilizare Pattern Strategy pentru Export
        btnJson.addActionListener(e -> {
            exportService.setStrategy(new JsonExportStrategy());
            exportService.executeExport(controller.handleGetStandings(), "f1_standings");
            JOptionPane.showMessageDialog(this, "Export JSON finalizat!");
        });

        btnXml.addActionListener(e -> {
            exportService.setStrategy(new XmlExportStrategy());
            exportService.executeExport(controller.handleGetStandings(), "f1_standings");
            JOptionPane.showMessageDialog(this, "Export XML finalizat!");
        });

        btnCsv.addActionListener(e -> {
            exportService.setStrategy(new CsvExportStrategy());
            exportService.executeExport(controller.handleGetStandings(), "f1_standings");
            JOptionPane.showMessageDialog(this, "Export CSV finalizat!");
        });

        btnRanking.addActionListener(e -> {
            modelAll.setRowCount(0);
            List<Driver> sorted = controller.handleGetStandings();
            for (Driver d : sorted) modelAll.addRow(new Object[]{d.getId(), d.getName(), d.getTeam(), d.getPoints(), d.getNationality()});
        });

        btnSearch.addActionListener(e -> {
            modelAll.setRowCount(0);
            List<Driver> ds = controller.handleSearchTeam(txtSearch.getText());
            for (Driver d : ds) modelAll.addRow(new Object[]{d.getId(), d.getName(), d.getTeam(), d.getPoints(), d.getNationality()});
        });

        return panel;
    }

    private void configPermisiuni() {
        if (role.equals("TEAM_MANAGER")) {
            btnDelete.setVisible(false);
            btnTransfer.setVisible(false);
        }
    }

    private void refreshData() {
        if (modelManage != null) {
            modelManage.setRowCount(0);
            List<Driver> all = controller.getDriversForTable();
            for (Driver d : all) {
                if (role.equals("ADMIN") || d.getTeam().equalsIgnoreCase(managedTeam)) {
                    modelManage.addRow(new Object[]{d.getId(), d.getName(), d.getTeam(), d.getNationality(), d.getPoints()});
                }
            }
        }
        modelAll.setRowCount(0);
        List<Driver> all = controller.getDriversForTable();
        for (Driver d : all) {
            modelAll.addRow(new Object[]{d.getId(), d.getName(), d.getTeam(), d.getPoints(), d.getNationality()});
        }
    }

    public void start() { setVisible(true); }
}