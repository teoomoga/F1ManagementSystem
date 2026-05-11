package org.example.view;

import org.example.model.repository.UserRepository;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");
    private JButton btnCreateAccount = new JButton("Create Account");

    public LoginView() {
        setTitle("F1 System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        add(new JLabel("Utilizator:"), gbc);
        gbc.gridx = 1; add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Parolă:"), gbc);
        gbc.gridx = 1; add(txtPass, gbc);

        gbc.gridy = 2;

        gbc.gridx = 0; gbc.gridwidth = 1;
        add(btnLogin, gbc);

        gbc.gridx = 1;
        add(btnCreateAccount, gbc);

        btnLogin.addActionListener(e -> {
            UserRepository userRepo = new UserRepository();
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            String[] userData = userRepo.authenticate(user, pass);

            if (userData != null) {
                String role = userData[0];
                String team = userData[1];
                String realEmail = userData[2];

                new MainView(role, team, realEmail).start();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Utilizator sau parolă incorectă!");
            }
        });

        btnCreateAccount.addActionListener(e -> {
            new RegisterView().setVisible(true);
        });
    }
}