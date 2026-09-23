package database;

public class ReportRecord {
    private final String fileName;
    private final String filePath;
    private final long fileSize;
    private final boolean readable;
    private final boolean writable;
    private final String hashValue;
    private final String fileType;
    private final String category;
    private final String reportText;

    public ReportRecord(
            String fileName,
            String filePath,
            long fileSize,
            boolean readable,
            boolean writable,
            String hashValue,
            String fileType,
            String category,
            String reportText) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.readable = readable;
        this.writable = writable;
        this.hashValue = hashValue;
        this.fileType = fileType;
        this.category = category;
        this.reportText = reportText;
    }

    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public long getFileSize() { return fileSize; }
    public boolean isReadable() { return readable; }
    public boolean isWritable() { return writable; }
    public String getHashValue() { return hashValue; }
    public String getFileType() { return fileType; }
    public String getCategory() { return category; }
    public String getReportText() { return reportText; }
}
