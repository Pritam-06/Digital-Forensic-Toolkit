package forensic;

public class AnalysisResult {
    private final String caseId;
    private final String investigatorName;
    private final String fileName;
    private final String filePath;
    private final long fileSize;
    private final boolean readable;
    private final boolean writable;
    private final boolean hidden;
    private final String extension;
    private final String createdTime;
    private final String modifiedTime;
    private final String accessedTime;
    private final String hashValue;
    private final String fileType;
    private final String signatureType;
    private final boolean signatureMismatch;
    private final String category;
    private final String riskLevel;
    private final String riskReason;
    private final String reportText;

    public AnalysisResult(
            String caseId,
            String investigatorName,
            String fileName,
            String filePath,
            long fileSize,
            boolean readable,
            boolean writable,
            boolean hidden,
            String extension,
            String createdTime,
            String modifiedTime,
            String accessedTime,
            String hashValue,
            String fileType,
            String signatureType,
            boolean signatureMismatch,
            String category,
            String riskLevel,
            String riskReason,
            String reportText) {
        this.caseId = caseId;
        this.investigatorName = investigatorName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.readable = readable;
        this.writable = writable;
        this.hidden = hidden;
        this.extension = extension;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.accessedTime = accessedTime;
        this.hashValue = hashValue;
        this.fileType = fileType;
        this.signatureType = signatureType;
        this.signatureMismatch = signatureMismatch;
        this.category = category;
        this.riskLevel = riskLevel;
        this.riskReason = riskReason;
        this.reportText = reportText;
    }

    public String getCaseId() { return caseId; }
    public String getInvestigatorName() { return investigatorName; }
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public long getFileSize() { return fileSize; }
    public boolean isReadable() { return readable; }
    public boolean isWritable() { return writable; }
    public boolean isHidden() { return hidden; }
    public String getExtension() { return extension; }
    public String getCreatedTime() { return createdTime; }
    public String getModifiedTime() { return modifiedTime; }
    public String getAccessedTime() { return accessedTime; }
    public String getHashValue() { return hashValue; }
    public String getFileType() { return fileType; }
    public String getSignatureType() { return signatureType; }
    public boolean isSignatureMismatch() { return signatureMismatch; }
    public String getCategory() { return category; }
    public String getRiskLevel() { return riskLevel; }
    public String getRiskReason() { return riskReason; }
    public String getReportText() { return reportText; }
}
