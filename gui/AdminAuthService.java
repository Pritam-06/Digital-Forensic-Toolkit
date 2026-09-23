package gui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AdminAuthService {
    private static final Path CREDENTIALS_FILE = Path.of("gui", "admin_credentials.txt");

    public boolean signup(String username, char[] password, char[] confirmPassword) throws IOException {
        String normalizedUsername = normalizeUsername(username);
        String normalizedPassword = normalizePassword(password);
        String normalizedConfirmPassword = normalizePassword(confirmPassword);

        validateSignup(normalizedUsername, normalizedPassword, normalizedConfirmPassword);
        ensureStorageExists();

        List<String> records = Files.exists(CREDENTIALS_FILE)
                ? Files.readAllLines(CREDENTIALS_FILE, StandardCharsets.UTF_8)
                : new ArrayList<>();

        for (String record : records) {
            String[] parts = record.split(":", 2);
            if (parts.length == 2 && parts[0].equalsIgnoreCase(normalizedUsername)) {
                throw new IllegalArgumentException("Admin username already exists.");
            }
        }

        String hash = hashPassword(normalizedPassword);
        Files.writeString(
                CREDENTIALS_FILE,
                normalizedUsername + ":" + hash + System.lineSeparator(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
        return true;
    }

    public boolean login(String username, char[] password) throws IOException {
        String normalizedUsername = normalizeUsername(username);
        String normalizedPassword = normalizePassword(password);

        if (normalizedUsername.isEmpty() || normalizedPassword.isEmpty()) {
            throw new IllegalArgumentException("Enter both username and password.");
        }
        if (!Files.exists(CREDENTIALS_FILE)) {
            throw new IllegalArgumentException("No admin account found. Please sign up first.");
        }

        String hash = hashPassword(normalizedPassword);
        List<String> records = Files.readAllLines(CREDENTIALS_FILE, StandardCharsets.UTF_8);
        for (String record : records) {
            String[] parts = record.split(":", 2);
            if (parts.length == 2
                    && parts[0].equalsIgnoreCase(normalizedUsername)
                    && parts[1].equals(hash)) {
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid admin username or password.");
    }

    private void validateSignup(String username, String password, String confirmPassword) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Admin username is required.");
        }
        if (!username.matches("[A-Za-z0-9_]{4,20}")) {
            throw new IllegalArgumentException("Username must be 4-20 characters using letters, numbers, or underscore.");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password and confirm password do not match.");
        }
    }

    private void ensureStorageExists() throws IOException {
        Path parent = CREDENTIALS_FILE.getParent();
        if (parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
        }
    }

    private String normalizeUsername(String username) {
        return username == null ? "" : username.trim();
    }

    private String normalizePassword(char[] password) {
        return password == null ? "" : new String(password).trim();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte value : hashedBytes) {
                builder.append(String.format("%02x", value));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm unavailable.", exception);
        }
    }
}
