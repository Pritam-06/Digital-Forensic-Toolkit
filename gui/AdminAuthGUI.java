package gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.io.IOException;

public class AdminAuthGUI extends JFrame {
    private static final String LOGIN_CARD = "LOGIN";
    private static final String SIGNUP_CARD = "SIGNUP";

    private final AdminAuthService authService = new AdminAuthService();
    private final CardLayout formLayout = new CardLayout();
    private final JPanel formPanel = new JPanel(formLayout);

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JTextField signupUsernameField;
    private JPasswordField signupPasswordField;
    private JPasswordField signupConfirmPasswordField;
    private JLabel modeValueLabel;
    private JLabel alertValueLabel;

    public AdminAuthGUI() {
        setTitle("TraceVault Admin Access");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(980, 620));
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);

        Color frameBackground = new Color(5, 12, 24);
        Color panelSurface = new Color(10, 22, 39);
        Color panelSurfaceAlt = new Color(14, 30, 52);
        Color cyan = new Color(77, 238, 234);
        Color green = new Color(132, 255, 163);
        Color orange = new Color(255, 176, 58);
        Color magenta = new Color(255, 90, 168);
        Color textPrimary = new Color(226, 247, 255);
        Color textSecondary = new Color(136, 193, 214);
        Color borderColor = new Color(32, 93, 122);

        UIManager.put("OptionPane.background", frameBackground);
        UIManager.put("Panel.background", frameBackground);

        GradientPanel rootPanel = new GradientPanel(new BorderLayout(20, 20));
        rootPanel.setGradientColors(new Color(3, 10, 20), new Color(8, 24, 43));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel topShell = new JPanel(new BorderLayout(16, 0));
        topShell.setOpaque(false);
        topShell.add(createHeroPanel(panelSurface, cyan, green, textPrimary, textSecondary), BorderLayout.CENTER);
        topShell.add(createStatusPanel(panelSurfaceAlt, borderColor, cyan, orange, textPrimary, textSecondary), BorderLayout.EAST);

        JPanel centerShell = new JPanel(new GridLayout(1, 2, 18, 0));
        centerShell.setOpaque(false);
        centerShell.add(createInfoPanel(panelSurfaceAlt, borderColor, cyan, textPrimary, textSecondary));
        centerShell.add(createAccessPanel(panelSurface, borderColor, cyan, magenta, green, textPrimary, textSecondary));

        rootPanel.add(topShell, BorderLayout.NORTH);
        rootPanel.add(centerShell, BorderLayout.CENTER);
        setContentPane(rootPanel);

        showLoginCard();
    }

    private JPanel createHeroPanel(Color panelSurface, Color cyan, Color green, Color textPrimary, Color textSecondary) {
        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setBackground(panelSurface);
        heroPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cyan, 1),
                BorderFactory.createEmptyBorder(20, 22, 20, 22)
        ));

        JLabel tagLabel = new JLabel("ADMIN AUTHENTICATION PORTAL");
        tagLabel.setForeground(cyan);
        tagLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel titleLabel = new JLabel("TraceVault Access Control");
        titleLabel.setForeground(textPrimary);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));

        JLabel subtitleLabel = new JLabel("Create an admin account or sign in before opening the forensic dashboard.");
        subtitleLabel.setForeground(textSecondary);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel signalLabel = new JLabel("LOCAL AUTH STORAGE  |  ADMIN MODE  |  DASHBOARD LOCKED UNTIL LOGIN");
        signalLabel.setForeground(green);
        signalLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

        heroPanel.add(tagLabel);
        heroPanel.add(Box.createVerticalStrut(6));
        heroPanel.add(titleLabel);
        heroPanel.add(Box.createVerticalStrut(6));
        heroPanel.add(subtitleLabel);
        heroPanel.add(Box.createVerticalStrut(12));
        heroPanel.add(signalLabel);
        return heroPanel;
    }

    private JPanel createStatusPanel(Color panelSurfaceAlt, Color borderColor, Color cyan, Color orange, Color textPrimary, Color textSecondary) {
        JPanel statusPanel = new JPanel(new GridLayout(5, 1, 0, 8));
        statusPanel.setPreferredSize(new Dimension(250, 0));
        statusPanel.setBackground(panelSurfaceAlt);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel("ACCESS STATUS");
        titleLabel.setForeground(cyan);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel modeLabel = new JLabel("Portal Mode");
        modeLabel.setForeground(textSecondary);
        modeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        modeValueLabel = new JLabel("LOGIN");
        modeValueLabel.setForeground(orange);
        modeValueLabel.setFont(new Font("Consolas", Font.BOLD, 20));

        JLabel alertLabel = new JLabel("Security State");
        alertLabel.setForeground(textSecondary);
        alertLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        alertValueLabel = new JLabel("LOCKED");
        alertValueLabel.setForeground(textPrimary);
        alertValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));

        statusPanel.add(titleLabel);
        statusPanel.add(modeLabel);
        statusPanel.add(modeValueLabel);
        statusPanel.add(alertLabel);
        statusPanel.add(alertValueLabel);
        return statusPanel;
    }

    private JPanel createInfoPanel(Color panelSurfaceAlt, Color borderColor, Color cyan, Color textPrimary, Color textSecondary) {
        JPanel infoPanel = new JPanel(new BorderLayout(0, 12));
        infoPanel.setBackground(panelSurfaceAlt);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        infoPanel.add(createSectionHeader("AUTH WORKFLOW", "Quick demo flow for mam", cyan, textPrimary, textSecondary), BorderLayout.NORTH);

        JLabel stepsLabel = new JLabel("<html><div style='width:320px'>"
                + "<b>1.</b> Open the app and show the admin login page.<br><br>"
                + "<b>2.</b> If no account exists, click Sign Up and create one.<br><br>"
                + "<b>3.</b> Sign in with the new admin account.<br><br>"
                + "<b>4.</b> The forensic dashboard opens after successful login.<br><br>"
                + "<b>5.</b> Credentials are stored locally in a project file for demo use."
                + "</div></html>");
        stepsLabel.setForeground(textPrimary);
        stepsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        infoPanel.add(stepsLabel, BorderLayout.CENTER);
        return infoPanel;
    }

    private JPanel createAccessPanel(Color panelSurface, Color borderColor, Color cyan, Color magenta, Color green, Color textPrimary, Color textSecondary) {
        JPanel accessPanel = new JPanel(new BorderLayout(0, 14));
        accessPanel.setBackground(panelSurface);
        accessPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        accessPanel.add(createSectionHeader("ADMIN ACCESS", "Login or create a new admin", cyan, textPrimary, textSecondary), BorderLayout.NORTH);

        formPanel.setOpaque(false);
        formPanel.add(createLoginCard(cyan, green, textPrimary, textSecondary), LOGIN_CARD);
        formPanel.add(createSignupCard(cyan, magenta, textPrimary, textSecondary), SIGNUP_CARD);

        accessPanel.add(formPanel, BorderLayout.CENTER);
        return accessPanel;
    }

    private JPanel createLoginCard(Color cyan, Color green, Color textPrimary, Color textSecondary) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        loginUsernameField = createTextField(cyan, textPrimary);
        loginPasswordField = createPasswordField(cyan, textPrimary);

        JButton loginButton = new JButton("LOGIN AS ADMIN");
        styleButton(loginButton, cyan, new Color(0, 68, 78));
        loginButton.addActionListener(event -> handleLogin());

        JButton switchButton = new JButton("CREATE NEW ADMIN");
        styleButton(switchButton, green, new Color(8, 58, 32));
        switchButton.addActionListener(event -> showSignupCard());

        panel.add(createFieldLabel("Admin Username", textSecondary));
        panel.add(Box.createVerticalStrut(8));
        panel.add(loginUsernameField);
        panel.add(Box.createVerticalStrut(14));
        panel.add(createFieldLabel("Password", textSecondary));
        panel.add(Box.createVerticalStrut(8));
        panel.add(loginPasswordField);
        panel.add(Box.createVerticalStrut(18));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(switchButton);
        return panel;
    }

    private JPanel createSignupCard(Color cyan, Color magenta, Color textPrimary, Color textSecondary) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        signupUsernameField = createTextField(cyan, textPrimary);
        signupPasswordField = createPasswordField(cyan, textPrimary);
        signupConfirmPasswordField = createPasswordField(cyan, textPrimary);

        JButton signupButton = new JButton("SIGN UP ADMIN");
        styleButton(signupButton, magenta, new Color(68, 18, 48));
        signupButton.addActionListener(event -> handleSignup());

        JButton switchButton = new JButton("BACK TO LOGIN");
        styleButton(switchButton, cyan, new Color(0, 68, 78));
        switchButton.addActionListener(event -> showLoginCard());

        panel.add(createFieldLabel("New Admin Username", textSecondary));
        panel.add(Box.createVerticalStrut(8));
        panel.add(signupUsernameField);
        panel.add(Box.createVerticalStrut(14));
        panel.add(createFieldLabel("Password", textSecondary));
        panel.add(Box.createVerticalStrut(8));
        panel.add(signupPasswordField);
        panel.add(Box.createVerticalStrut(14));
        panel.add(createFieldLabel("Confirm Password", textSecondary));
        panel.add(Box.createVerticalStrut(8));
        panel.add(signupConfirmPasswordField);
        panel.add(Box.createVerticalStrut(18));
        panel.add(signupButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(switchButton);
        return panel;
    }

    private JLabel createFieldLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField(Color accent, Color textPrimary) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setBackground(new Color(2, 14, 26));
        field.setForeground(textPrimary);
        field.setCaretColor(accent);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setFont(new Font("Consolas", Font.PLAIN, 14));
        field.setAlignmentX(LEFT_ALIGNMENT);
        return field;
    }

    private JPasswordField createPasswordField(Color accent, Color textPrimary) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setBackground(new Color(2, 14, 26));
        field.setForeground(textPrimary);
        field.setCaretColor(accent);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setFont(new Font("Consolas", Font.PLAIN, 14));
        field.setAlignmentX(LEFT_ALIGNMENT);
        return field;
    }

    private JPanel createSectionHeader(String title, String subtitle, Color accent, Color textPrimary, Color textSecondary) {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(accent);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setForeground(textSecondary);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        header.add(titleLabel);
        header.add(Box.createVerticalStrut(3));
        header.add(subtitleLabel);
        return header;
    }

    private void styleButton(JButton button, Color foreground, Color background) {
        button.setFocusPainted(false);
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(foreground, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
    }

    private void handleLogin() {
        try {
            authService.login(loginUsernameField.getText(), loginPasswordField.getPassword());
            alertValueLabel.setText("VERIFIED");
            openDashboard();
        } catch (IllegalArgumentException | IOException exception) {
            alertValueLabel.setText("LOCKED");
            showError(exception.getMessage());
        }
    }

    private void handleSignup() {
        try {
            authService.signup(
                    signupUsernameField.getText(),
                    signupPasswordField.getPassword(),
                    signupConfirmPasswordField.getPassword()
            );
            JOptionPane.showMessageDialog(this, "Admin account created successfully. Please log in.", "Signup Complete", JOptionPane.INFORMATION_MESSAGE);
            loginUsernameField.setText(signupUsernameField.getText().trim());
            clearSignupFields();
            showLoginCard();
        } catch (IllegalArgumentException | IOException exception) {
            alertValueLabel.setText("LOCKED");
            showError(exception.getMessage());
        }
    }

    private void showLoginCard() {
        formLayout.show(formPanel, LOGIN_CARD);
        modeValueLabel.setText("LOGIN");
        alertValueLabel.setText("LOCKED");
    }

    private void showSignupCard() {
        formLayout.show(formPanel, SIGNUP_CARD);
        modeValueLabel.setText("SIGNUP");
        alertValueLabel.setText("PENDING");
    }

    private void clearSignupFields() {
        signupUsernameField.setText("");
        signupPasswordField.setText("");
        signupConfirmPasswordField.setText("");
    }

    private void openDashboard() {
        SwingUtilities.invokeLater(() -> {
            dispose();
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Admin Access Error", JOptionPane.ERROR_MESSAGE);
    }

    private static class GradientPanel extends JPanel {
        private Color colorTop = new Color(6, 14, 24);
        private Color colorBottom = new Color(10, 28, 48);

        GradientPanel(BorderLayout layout) {
            super(layout);
            setOpaque(false);
        }

        void setGradientColors(Color top, Color bottom) {
            this.colorTop = top;
            this.colorBottom = bottom;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2d = (Graphics2D) graphics.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(new GradientPaint(0, 0, colorTop, 0, getHeight(), colorBottom));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(new Color(20, 83, 114, 80));
            g2d.setStroke(new BasicStroke(1.2f));
            for (int y = 0; y < getHeight(); y += 40) {
                g2d.drawLine(0, y, getWidth(), y);
            }
            g2d.dispose();
        }
    }
}
