package org.example.view;

import org.example.model.entity.User;
import org.example.model.repository.UserRepository;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class RegisterView extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JTextField txtEmail = new JTextField(15); // Câmp nou pentru Assignment 2
    private JComboBox<String> cbRole = new JComboBox<>(new String[]{"VISITOR", "TEAM_MANAGER", "ADMIN"});

    // Câmpurile condiționate pentru rolul secundar (Team Manager)
    private JLabel lblTeam = new JLabel("Nume Echipă:");
    private JTextField txtManagedTeam = new JTextField(15);
    private JLabel lblCode = new JLabel("Cod Secret:");
    private JTextField txtSecurityCode = new JTextField(15);

    private JButton btnRegister = new JButton("Crează Cont");
    private UserRepository userRepository = new UserRepository();

    public RegisterView() {
        setTitle("F1 System - Inregistrare Utilizator Nou");
        setSize(450, 450);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLocationRelativeTo(null);

        // 1. Utilizator
        addComp(0, "Utilizator:", txtUser, gbc);
        // 2. Parolă
        addComp(1, "Parolă:", txtPass, gbc);
        // 3. Email (Adăugat special pentru serviciul de notificări)
        addComp(2, "Email:", txtEmail, gbc);
        // 4. Rol
        addComp(3, "Rol:", cbRole, gbc);

        // Setări inițiale pentru câmpurile de Manager (ascunse)
        lblTeam.setVisible(false);
        txtManagedTeam.setVisible(false);
        lblCode.setVisible(false);
        txtSecurityCode.setVisible(false);

        // Adăugare câmpuri Manager în layout
        gbc.gridwidth = 1;
        gbc.gridy = 4; gbc.gridx = 0; add(lblTeam, gbc);
        gbc.gridx = 1; add(txtManagedTeam, gbc);

        gbc.gridy = 5; gbc.gridx = 0; add(lblCode, gbc);
        gbc.gridx = 1; add(txtSecurityCode, gbc);

        // Buton Register
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        add(btnRegister, gbc);

        // Logică pentru afișare dinamică a rolului secundar [cite: 11, 15]
        cbRole.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                boolean isManager = cbRole.getSelectedItem().equals("TEAM_MANAGER");
                lblTeam.setVisible(isManager);
                txtManagedTeam.setVisible(isManager);
                lblCode.setVisible(isManager);
                txtSecurityCode.setVisible(isManager);

                revalidate();
                repaint();
                pack(); // Ajustează dimensiunea ferestrei automat
            }
        });

        btnRegister.addActionListener(e -> {
            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());
            String email = txtEmail.getText();
            String role = cbRole.getSelectedItem().toString();
            String team = txtManagedTeam.getText();
            String secretInput = txtSecurityCode.getText();

            // Validări de bază [cite: 27]
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Toate câmpurile standard sunt obligatorii!");
                return;
            }

            // Validare cod securitate pentru Manager
            if (role.equals("TEAM_MANAGER")) {
                if (team.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Introduceți numele echipei!");
                    return;
                }
                String correctCode = userRepository.getSecurityCodeForTeam(team);
                if (correctCode == null || !correctCode.equals(secretInput)) {
                    JOptionPane.showMessageDialog(this, "Cod secret incorect pentru echipa " + team);
                    return;
                }
            }

            // Creare obiect User cu noul câmp Email
            User newUser = new User(username, password, role, team.isEmpty() ? null : team, email);

            if (userRepository.save(newUser)) {
                JOptionPane.showMessageDialog(this, "Cont creat cu succes! Te poți loga acum.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Eroare: Numele de utilizator ar putea fi deja luat.");
            }
        });
    }

    private void addComp(int row, String label, JComponent comp, GridBagConstraints gbc) {
        gbc.gridy = row; gbc.gridx = 0; gbc.gridwidth = 1;
        add(new JLabel(label), gbc);
        gbc.gridx = 1;
        add(comp, gbc);
    }
}