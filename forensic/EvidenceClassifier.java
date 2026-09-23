package forensic;

public class EvidenceClassifier {
    public String classify(String type) throws InvalidEvidenceException {
        if (type == null || type.trim().isEmpty() || "Unknown".equalsIgnoreCase(type)) {
            throw new InvalidEvidenceException("Unable to classify the selected file.");
        }

        switch (type) {
            case "Image":
                return "Visual Evidence";
            case "Document":
                return "Textual Evidence";
            case "Audio":
                return "Audio Evidence";
            case "Video":
                return "Multimedia Evidence";
            case "Executable":
                return "Program Artifact";
            case "Archive":
                return "Compressed Evidence";
            default:
                throw new InvalidEvidenceException("Unsupported evidence type: " + type);
        }
    }
}
