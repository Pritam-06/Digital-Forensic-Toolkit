package gui;

import database.ReportRecord;
import database.ReportRepository;
import forensic.EvidenceClassifier;
import forensic.FileAnalyzer;
import forensic.FileSignatureInspector;
import forensic.FileTypeDetector;
import forensic.HashGenerator;
import forensic.InvalidEvidenceException;
import forensic.MetaDataExtractor;
import forensic.ReportGenerator;
import forensic.RiskAssessor;
import threads.HashThread;
import threads.MetaDataThread;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.io.File;

public class MainGUI extends JFrame {
    private static final String APP_NAME = "TraceVault";
    private static final String APP_SUITE_NAME = "TraceVault Digital Forensics Suite";
    private JTextField filePathField;
    private JTextArea outputArea;
    private JLabel statusValueLabel;
    private JLabel engineValueLabel;
    private JButton browseButton;
    private JButton analyzeButton;
    private JButton saveToDbButton;

    private ReportRecord latestReportRecord;

    public MainGUI() {
        setTitle(APP_SUITE_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1100, 680));
        setMinimumSize(new Dimension(980, 620));
        setLocationRelativeTo(null);

        Color frameBackground = new Color(5, 12, 24);
        Color panelSurface = new Color(10, 22, 39);
        Color panelSurfaceAlt = new Color(14, 30, 52);
        Color outputBackground = new Color(1, 9, 18);
        Color cyan = new Color(77, 238, 234);
        Color green = new Color(132, 255, 163);
        Color orange = new Color(255, 176, 58);
        Color magenta = new Color(255, 90, 168);
        Color textPrimary = new Color(226, 247, 255);
        Color textSecondary = new Color(136, 193, 214);
        Color borderColor = new Color(32, 93, 122);

        UIManager.put("OptionPane.background", frameBackground);
        UIManager.put("Panel.background", frameBackground);

        GradientPanel mainPanel = new GradientPanel(new BorderLayout(18, 18));
        mainPanel.setGradientColors(new Color(3, 10, 20), new Color(8, 24, 43));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel bannerPanel = new JPanel(new BorderLayout(16, 0));
        bannerPanel.setOpaque(false);
        bannerPanel.add(createBannerPanel(panelSurface, cyan, green, textPrimary, textSecondary), BorderLayout.CENTER);
        bannerPanel.add(createStatusPanel(panelSurfaceAlt, borderColor, cyan, orange, textPrimary, textSecondary), BorderLayout.EAST);

        JPanel leftPanel = new JPanel(new BorderLayout(0, 14));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(320, 0));
        leftPanel.add(createControlPanel(panelSurface, borderColor, cyan, magenta, textPrimary, textSecondary), BorderLayout.NORTH);
        leftPanel.add(createInfoPanel(panelSurfaceAlt, borderColor, cyan, textPrimary, textSecondary), BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        outputArea.setBackground(outputBackground);
        outputArea.setForeground(green);
        outputArea.setCaretColor(cyan);
        outputArea.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        outputArea.setText(buildWelcomeMessage());

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        scrollPane.getViewport().setBackground(outputBackground);

        JPanel outputShell = new JPanel(new BorderLayout(0, 10));
        outputShell.setOpaque(false);
        outputShell.add(createSectionHeader("TRACE TERMINAL", "Basic evidence analysis and simple database save", cyan, textPrimary, textSecondary), BorderLayout.NORTH);
        outputShell.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(bannerPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(outputShell, BorderLayout.CENTER);
        setContentPane(mainPanel);

        browseButton.addActionListener(event -> browseForFile());
        analyzeButton.addActionListener(event -> analyzeSelectedFile());
        saveToDbButton.addActionListener(event -> saveToDatabase());
    }

    private JPanel createBannerPanel(Color panelSurface, Color cyan, Color green, Color textPrimary, Color textSecondary) {
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setBackground(panelSurface);
        banner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cyan, 1),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        JLabel tagLabel = new JLabel("DIGITAL EVIDENCE ANALYSIS PLATFORM");
        tagLabel.setForeground(cyan);
        tagLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel titleLabel = new JLabel(APP_NAME);
        titleLabel.setForeground(textPrimary);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));

        JLabel subTitleLabel = new JLabel("Select a file, inspect it as evidence, and optionally save the report to PostgreSQL.");
        subTitleLabel.setForeground(textSecondary);
        subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel signalLabel = new JLabel("SECURE MODE  |  THREAD MONITOR ACTIVE  |  EVIDENCE PIPELINE READY");
        signalLabel.setForeground(green);
        signalLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

        banner.add(tagLabel);
        banner.add(Box.createVerticalStrut(6));
        banner.add(titleLabel);
        banner.add(Box.createVerticalStrut(6));
        banner.add(subTitleLabel);
        banner.add(Box.createVerticalStrut(12));
        banner.add(signalLabel);
        return banner;
    }

    private JPanel createStatusPanel(Color panelSurfaceAlt, Color borderColor, Color cyan, Color orange, Color textPrimary, Color textSecondary) {
        JPanel statusPanel = new JPanel(new GridLayout(5, 1, 0, 8));
        statusPanel.setPreferredSize(new Dimension(260, 0));
        statusPanel.setBackground(panelSurfaceAlt);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel("OPERATION STATUS");
        titleLabel.setForeground(cyan);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel statusLabel = new JLabel("System State");
        statusLabel.setForeground(textSecondary);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        statusValueLabel = new JLabel("STANDBY");
        statusValueLabel.setForeground(orange);
        statusValueLabel.setFont(new Font("Consolas", Font.BOLD, 20));

        JLabel engineLabel = new JLabel("Analysis Engine");
        engineLabel.setForeground(textSecondary);
        engineLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        engineValueLabel = new JLabel("READY");
        engineValueLabel.setForeground(textPrimary);
        engineValueLabel.setFont(new Font("Consolas", Font.BOLD, 18));

        statusPanel.add(titleLabel);
        statusPanel.add(statusLabel);
        statusPanel.add(statusValueLabel);
        statusPanel.add(engineLabel);
        statusPanel.add(engineValueLabel);
        return statusPanel;
    }

    private JPanel createControlPanel(Color panelSurface, Color borderColor, Color cyan, Color magenta, Color textPrimary, Color textSecondary) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 12));
        wrapper.setBackground(panelSurface);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        wrapper.add(createSectionHeader("EVIDENCE INPUT", "Choose one file and analyze it", cyan, textPrimary, textSecondary), BorderLayout.NORTH);

        JPanel controls = new JPanel();
        controls.setOpaque(false);
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        JLabel pathLabel = new JLabel("Target File Path");
        pathLabel.setForeground(textSecondary);
        pathLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        filePathField = new JTextField();
        filePathField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        filePathField.setBackground(new Color(2, 14, 26));
        filePathField.setForeground(textPrimary);
        filePathField.setCaretColor(cyan);
        filePathField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cyan, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        filePathField.setFont(new Font("Consolas", Font.PLAIN, 14));

        browseButton = new JButton("BROWSE FILE");
        styleButton(browseButton, cyan, new Color(8, 58, 32));

        analyzeButton = new JButton("RUN ANALYSIS");
        styleButton(analyzeButton, cyan, new Color(0, 68, 78));

        saveToDbButton = new JButton("SAVE REPORT TO DB");
        styleButton(saveToDbButton, magenta, new Color(68, 18, 48));
        saveToDbButton.setEnabled(false);

        controls.add(pathLabel);
        controls.add(Box.createVerticalStrut(8));
        controls.add(filePathField);
        controls.add(Box.createVerticalStrut(14));
        controls.add(browseButton);
        controls.add(Box.createVerticalStrut(10));
        controls.add(analyzeButton);
        controls.add(Box.createVerticalStrut(10));
        controls.add(saveToDbButton);

        wrapper.add(controls, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createInfoPanel(Color panelSurfaceAlt, Color borderColor, Color cyan, Color textPrimary, Color textSecondary) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 12));
        wrapper.setBackground(panelSurfaceAlt);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        wrapper.add(createSectionHeader("TOOL FEATURES", "What the simplified project does", cyan, textPrimary, textSecondary), BorderLayout.NORTH);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(panelSurfaceAlt);
        infoArea.setForeground(textPrimary);
        infoArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        infoArea.setText(
                "1. Browse a file from the system\n"
                        + "2. Extract metadata and timestamps\n"
                        + "3. Generate SHA-256 hash\n"
                        + "4. Detect file type\n"
                        + "5. Classify evidence\n"
                        + "6. Show risk level\n"
                        + "7. Save report to PostgreSQL\n\n"
                        + "Best demo files: TXT, HTML, JSON, ZIP, EXE"
        );

        wrapper.add(infoArea, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createSectionHeader(String title, String subtitle, Color accent, Color textPrimary, Color textSecondary) {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(accent);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel subLabel = new JLabel(subtitle);
        subLabel.setForeground(textSecondary);
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        header.add(titleLabel);
        header.add(Box.createVerticalStrut(3));
        header.add(subLabel);
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

    private void browseForFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Evidence File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        File demoFolder = new File("DemoEvidence");
        if (demoFolder.exists()) {
            fileChooser.setCurrentDirectory(demoFolder);
        }

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            outputArea.setText("Selected file:\n" + fileChooser.getSelectedFile().getAbsolutePath() + "\n\nPress RUN ANALYSIS to start the forensic scan.");
            setStatus("TARGETED", "READY");
        }
    }

    private void analyzeSelectedFile() {
        try {
            String filePath = normalizePath(filePathField.getText());
            filePathField.setText(filePath);
            if (filePath.isEmpty()) {
                throw new IllegalArgumentException("Please select a file before analyzing.");
            }

            File file = new File(filePath);
            if (!file.exists()) {
                throw new IllegalArgumentException("The selected file does not exist.");
            }
            if (!file.isFile()) {
                throw new IllegalArgumentException("Please choose a file, not a folder.");
            }

            setStatus("SCANNING", "RUNNING");
            analyzeButton.setEnabled(false);
            browseButton.setEnabled(false);
            saveToDbButton.setEnabled(false);
            latestReportRecord = null;
            outputArea.setText(buildLoadingMessage(file));

            Thread thread = new Thread(() -> performAnalysis(file), "AnalysisCoordinator");
            thread.start();
        } catch (IllegalArgumentException exception) {
            setStatus("ALERT", "INPUT ERROR");
            showError(exception.getMessage());
        }
    }

    private void performAnalysis(File file) {
        try {
            FileAnalyzer analyzer = new FileAnalyzer(file.getName(), file.getAbsolutePath(), file.length());
            MetaDataExtractor metaDataExtractor = new MetaDataExtractor(file);
            HashGenerator hashGenerator = new HashGenerator(file);

            MetaDataThread metaDataThread = new MetaDataThread(metaDataExtractor);
            HashThread hashTask = new HashThread(hashGenerator);
            Thread hashWorker = new Thread(hashTask, "HashWorker");

            metaDataThread.start();
            hashWorker.start();
            metaDataThread.join();
            hashWorker.join();

            FileTypeDetector typeDetector = new FileTypeDetector();
            String fileType = typeDetector.detectType(file.getName());

            FileSignatureInspector signatureInspector = new FileSignatureInspector();
            String signatureType = signatureInspector.detectSignature(file);
            boolean signatureMismatch = signatureInspector.hasSignatureMismatch(fileType, signatureType);

            EvidenceClassifier classifier = new EvidenceClassifier();
            String category;
            try {
                category = classifier.classify(fileType);
            } catch (InvalidEvidenceException exception) {
                category = "Unclassified Evidence";
            }

            RiskAssessor riskAssessor = new RiskAssessor();
            String riskLevel = riskAssessor.assessRisk(fileType, metaDataExtractor.isHidden(), file.length(), signatureMismatch, metaDataExtractor.getExtension());
            String riskReason = riskAssessor.explainRisk(fileType, metaDataExtractor.isHidden(), file.length(), signatureMismatch, metaDataExtractor.getExtension());

            ReportGenerator reportGenerator = new ReportGenerator();
            String baseReport = reportGenerator.generateReport(
                    analyzer,
                    metaDataExtractor,
                    hashGenerator,
                    fileType,
                    signatureType,
                    signatureMismatch,
                    category,
                    riskLevel,
                    riskReason,
                    "Demo Case",
                    "Demo Investigator"
            );
            String report = buildInvestigationReport(baseReport);

            latestReportRecord = new ReportRecord(
                    file.getName(),
                    file.getAbsolutePath(),
                    file.length(),
                    metaDataExtractor.isReadable(),
                    metaDataExtractor.isWritable(),
                    hashGenerator.getHash(),
                    fileType,
                    category,
                    report
            );

            SwingUtilities.invokeLater(() -> {
                outputArea.setText(report);
                setStatus("SECURED", "COMPLETE");
                analyzeButton.setEnabled(true);
                browseButton.setEnabled(true);
                saveToDbButton.setEnabled(true);
            });
        } catch (Exception exception) {
            SwingUtilities.invokeLater(() -> {
                outputArea.setText("Error: " + exception.getMessage());
                setStatus("ALERT", "FAILED");
                analyzeButton.setEnabled(true);
                browseButton.setEnabled(true);
            });
        }
    }

    private void saveToDatabase() {
        if (latestReportRecord == null) {
            showError("Run an analysis first, then save the report to the database.");
            return;
        }

        setStatus("ARCHIVING", "DB WRITE");
        saveToDbButton.setEnabled(false);
        outputArea.append("\n\n[ DATABASE ]\nTrying to save report to PostgreSQL...");

        Thread dbThread = new Thread(() -> {
            try {
                ReportRepository repository = new ReportRepository();
                repository.saveReport(latestReportRecord);
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("\nDatabase Status: Report saved successfully.");
                    setStatus("ARCHIVED", "DB STORED");
                    saveToDbButton.setEnabled(true);
                });
            } catch (ClassNotFoundException exception) {
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("\nDatabase Error: PostgreSQL JDBC driver not found.");
                    outputArea.append("\nFix: Download the PostgreSQL JDBC jar and run the app with it in the classpath.");
                    setStatus("ALERT", "DB DRIVER");
                    saveToDbButton.setEnabled(true);
                });
            } catch (Exception exception) {
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("\nDatabase Error: Unable to connect or save report.");
                    outputArea.append("\nCause: " + exception.getMessage());
                    outputArea.append("\nFix checklist:");
                    outputArea.append("\n1. Start PostgreSQL");
                    outputArea.append("\n2. Create database 'digital_forensics'");
                    outputArea.append("\n3. Run postgres_setup.sql");
                    outputArea.append("\n4. Check username/password in DBConfig.java");
                    setStatus("ALERT", "DB ERROR");
                    saveToDbButton.setEnabled(true);
                });
            }
        }, "DatabaseWriter");
        dbThread.start();
    }

    private String buildLoadingMessage(File file) {
        return "[ FORENSIC ENGINE INITIALIZING ]\n"
                + "--------------------------------\n"
                + "Target File  : " + file.getName() + "\n"
                + "Path         : " + file.getAbsolutePath() + "\n"
                + "Stage 1      : Metadata extraction\n"
                + "Stage 2      : SHA-256 hashing\n"
                + "Stage 3      : File type detection\n"
                + "Stage 4      : Evidence classification\n"
                + "Stage 5      : Risk assessment\n\n"
                + "Please wait while analysis completes...";
    }

    private String buildWelcomeMessage() {
        return "[ " + APP_SUITE_NAME.toUpperCase() + " ]\n"
                + "====================================\n\n"
                + "How to use\n"
                + "1. Click BROWSE FILE\n"
                + "2. Select a file\n"
                + "3. Click RUN ANALYSIS\n"
                + "4. Optionally click SAVE REPORT TO DB\n\n"
                + "What the tool does\n"
                + "- Reads file metadata\n"
                + "- Generates SHA-256 hash\n"
                + "- Detects file type\n"
                + "- Classifies evidence\n"
                + "- Shows risk level\n"
                + "- Saves report to PostgreSQL\n\n"
                + "Best demo files: TXT, HTML, JSON, ZIP, EXE";
    }

    private String buildInvestigationReport(String baseReport) {
        return "[ INVESTIGATION REPORT ]\n"
                + "========================\n\n"
                + baseReport + "\n\n"
                + "Report Status: Ready for review and optional database storage.";
    }

    private void setStatus(String status, String engineState) {
        statusValueLabel.setText(status);
        engineValueLabel.setText(engineState);
    }

    private String normalizePath(String rawPath) {
        String cleanedPath = rawPath == null ? "" : rawPath.trim();
        if (cleanedPath.startsWith("\"") && cleanedPath.endsWith("\"") && cleanedPath.length() > 1) {
            cleanedPath = cleanedPath.substring(1, cleanedPath.length() - 1);
        }
        return cleanedPath;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminAuthGUI authGUI = new AdminAuthGUI();
            authGUI.setVisible(true);
        });
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
