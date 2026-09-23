package forensic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MetaDataExtractor extends ForensicTool {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private long fileSize;
    private boolean readable;
    private boolean writable;
    private boolean hidden;
    private String extension = "none";
    private String createdTime = "Unavailable";
    private String modifiedTime = "Unavailable";
    private String accessedTime = "Unavailable";

    public MetaDataExtractor(File file) {
        super(file);
    }

    @Override
    public String analyze() {
        fileSize = file.length();
        readable = file.canRead();
        writable = file.canWrite();
        extension = extractExtension(file.getName());

        try {
            hidden = Files.isHidden(file.toPath());
            BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            createdTime = FORMATTER.format(attributes.creationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            modifiedTime = FORMATTER.format(attributes.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            accessedTime = FORMATTER.format(attributes.lastAccessTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } catch (IOException exception) {
            hidden = file.isHidden();
        }

        return "File Size: " + fileSize + " bytes" + System.lineSeparator()
                + "Readable/Writable: " + readable + "/" + writable + System.lineSeparator()
                + "Hidden: " + hidden + System.lineSeparator()
                + "Extension: " + extension + System.lineSeparator()
                + "Created: " + createdTime + System.lineSeparator()
                + "Modified: " + modifiedTime + System.lineSeparator()
                + "Accessed: " + accessedTime;
    }

    private String extractExtension(String name) {
        int index = name.lastIndexOf('.');
        return index >= 0 && index < name.length() - 1 ? name.substring(index + 1).toLowerCase() : "none";
    }

    public long getFileSize() { return fileSize; }
    public boolean isReadable() { return readable; }
    public boolean isWritable() { return writable; }
    public boolean isHidden() { return hidden; }
    public String getExtension() { return extension; }
    public String getCreatedTime() { return createdTime; }
    public String getModifiedTime() { return modifiedTime; }
    public String getAccessedTime() { return accessedTime; }
}
