package forensic;

import java.io.File;

public abstract class ForensicTool {
    protected File file;

    public ForensicTool(File file) {
        this.file = file;
    }

    public abstract String analyze();

    public String showFileDetails() {
        return "File Name: " + file.getName() + System.lineSeparator()
                + "File Path: " + file.getAbsolutePath() + System.lineSeparator()
                + "File Size: " + file.length() + " bytes";
    }
}
