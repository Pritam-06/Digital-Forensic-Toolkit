package forensic;

public class FileAnalyzer {
    private String fileName;
    private String filePath;
    private long fileSize;

    public FileAnalyzer() {
        this("Unknown", "Unknown", 0L);
    }

    public FileAnalyzer(String fileName, String filePath, long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public String basicInfo() {
        return "File Name: " + fileName + System.lineSeparator()
                + "File Path: " + filePath + System.lineSeparator()
                + "File Size: " + fileSize + " bytes";
    }
}
