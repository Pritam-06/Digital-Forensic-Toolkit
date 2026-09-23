package threads;

import forensic.MetaDataExtractor;

public class MetaDataThread extends Thread {
    private final MetaDataExtractor metaDataExtractor;
    private String result;

    public MetaDataThread(MetaDataExtractor metaDataExtractor) {
        this.metaDataExtractor = metaDataExtractor;
    }

    @Override
    public void run() {
        result = metaDataExtractor.analyze();
    }

    public String getResult() {
        return result;
    }
}
