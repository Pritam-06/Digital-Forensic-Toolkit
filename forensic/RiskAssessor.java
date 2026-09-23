package forensic;

public class RiskAssessor {
    public String assessRisk(String fileType, boolean hidden, long fileSize, boolean signatureMismatch, String extension) {
        if (signatureMismatch || "exe".equalsIgnoreCase(extension) || "bat".equalsIgnoreCase(extension) || "dll".equalsIgnoreCase(extension)) {
            return "High";
        }
        if (hidden || fileSize > 10 * 1024 * 1024 || "Archive".equalsIgnoreCase(fileType)) {
            return "Medium";
        }
        return "Low";
    }

    public String explainRisk(String fileType, boolean hidden, long fileSize, boolean signatureMismatch, String extension) {
        if (signatureMismatch) {
            return "File extension and file signature do not match.";
        }
        if ("exe".equalsIgnoreCase(extension) || "bat".equalsIgnoreCase(extension) || "dll".equalsIgnoreCase(extension)) {
            return "Executable artifact requires careful review.";
        }
        if (hidden) {
            return "Hidden file detected on the system.";
        }
        if (fileSize > 10 * 1024 * 1024) {
            return "Large file size may require deeper investigation.";
        }
        if ("Archive".equalsIgnoreCase(fileType)) {
            return "Compressed artifacts may contain multiple embedded files.";
        }
        return "No immediate risk indicators detected in the preliminary scan.";
    }
}
