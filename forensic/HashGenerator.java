package forensic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator extends ForensicTool {
    private String hashValue = "Unavailable";

    public HashGenerator(File file) {
        super(file);
    }

    @Override
    public String analyze() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    messageDigest.update(buffer, 0, bytesRead);
                }
            }
            hashValue = toHex(messageDigest.digest());
        } catch (IOException | NoSuchAlgorithmException exception) {
            hashValue = "Unavailable";
        }
        return "SHA-256: " + hashValue;
    }

    public String getHash() {
        return hashValue;
    }

    private String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte value : bytes) {
            builder.append(String.format("%02X", value));
        }
        return builder.toString();
    }
}
