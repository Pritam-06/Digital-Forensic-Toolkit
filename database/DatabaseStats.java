package database;

public class DatabaseStats {
    private final int totalReports;
    private final int documentCount;
    private final int imageCount;
    private final int executableCount;
    private final int archiveCount;
    private final int highRiskCount;

    public DatabaseStats(int totalReports, int documentCount, int imageCount, int executableCount, int archiveCount, int highRiskCount) {
        this.totalReports = totalReports;
        this.documentCount = documentCount;
        this.imageCount = imageCount;
        this.executableCount = executableCount;
        this.archiveCount = archiveCount;
        this.highRiskCount = highRiskCount;
    }

    public int getTotalReports() { return totalReports; }
    public int getDocumentCount() { return documentCount; }
    public int getImageCount() { return imageCount; }
    public int getExecutableCount() { return executableCount; }
    public int getArchiveCount() { return archiveCount; }
    public int getHighRiskCount() { return highRiskCount; }
}
