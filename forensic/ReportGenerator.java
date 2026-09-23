package forensic;

public class ReportGenerator {
    public String generateReport(
            FileAnalyzer analyzer,
            MetaDataExtractor metaDataExtractor,
            HashGenerator hashGenerator,
            String fileType,
            String signatureType,
            boolean signatureMismatch,
            String category,
            String riskLevel,
            String riskReason,
            String caseId,
            String investigatorName) {

        return "Case ID: " + caseId + System.lineSeparator()
                + "Investigator: " + investigatorName + System.lineSeparator()
                + analyzer.basicInfo() + System.lineSeparator()
                + "Readable/Writable: " + metaDataExtractor.isReadable() + "/" + metaDataExtractor.isWritable() + System.lineSeparator()
                + "Hidden: " + metaDataExtractor.isHidden() + System.lineSeparator()
                + "Extension: " + metaDataExtractor.getExtension() + System.lineSeparator()
                + "Created Time: " + metaDataExtractor.getCreatedTime() + System.lineSeparator()
                + "Modified Time: " + metaDataExtractor.getModifiedTime() + System.lineSeparator()
                + "Accessed Time: " + metaDataExtractor.getAccessedTime() + System.lineSeparator()
                + "SHA-256: " + hashGenerator.getHash() + System.lineSeparator()
                + "File Type: " + fileType + System.lineSeparator()
                + "Signature Type: " + signatureType + System.lineSeparator()
                + "Signature Mismatch: " + signatureMismatch + System.lineSeparator()
                + "Category: " + category + System.lineSeparator()
                + "Risk Level: " + riskLevel + System.lineSeparator()
                + "Risk Reason: " + riskReason;
    }
}
