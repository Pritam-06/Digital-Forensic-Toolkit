package forensic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileSignatureInspector {
    public String detectSignature(File file) {
        byte[] header = new byte[8];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int bytesRead = inputStream.read(header);
            if (bytesRead < 4) {
                return "Unknown";
            }

            if (matches(header, new int[]{0x25, 0x50, 0x44, 0x46})) {
                return "Document";
            }
            if (matches(header, new int[]{0x89, 0x50, 0x4E, 0x47})) {
                return "Image";
            }
            if (matches(header, new int[]{0xFF, 0xD8, 0xFF})) {
                return "Image";
            }
            if (matches(header, new int[]{0x50, 0x4B, 0x03, 0x04})) {
                return "Archive";
            }
            if (matches(header, new int[]{0x4D, 0x5A})) {
                return "Executable";
            }
            if (matches(header, new int[]{0x49, 0x44, 0x33})) {
                return "Audio";
            }
        } catch (IOException exception) {
            return "Unknown";
        }
        return "Unknown";
    }

    public boolean hasSignatureMismatch(String detectedType, String signatureType) {
        if ("Unknown".equals(signatureType) || "Unknown".equals(detectedType)) {
            return false;
        }
        return !detectedType.equalsIgnoreCase(signatureType);
    }

    private boolean matches(byte[] header, int[] expected) {
        if (header.length < expected.length) {
            return false;
        }
        for (int index = 0; index < expected.length; index++) {
            if ((header[index] & 0xFF) != expected[index]) {
                return false;
            }
        }
        return true;
    }
}
