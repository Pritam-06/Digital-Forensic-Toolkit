package forensic;

public class FileTypeDetector {
    public String detectType(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "Unknown";
        }

        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
                return "Image";
            case "txt":
            case "html":
            case "htm":
            case "css":
            case "js":
            case "xml":
            case "json":
            case "csv":
            case "log":
            case "pdf":
            case "doc":
            case "docx":
                return "Document";
            case "mp3":
            case "wav":
                return "Audio";
            case "mp4":
            case "avi":
            case "mkv":
                return "Video";
            case "exe":
            case "dll":
            case "bat":
                return "Executable";
            case "zip":
            case "rar":
            case "7z":
                return "Archive";
            default:
                return "Unknown";
        }
    }
}
