package threads;

import forensic.HashGenerator;

public class HashThread implements Runnable {
    private final HashGenerator hashGenerator;
    private String result;

    public HashThread(HashGenerator hashGenerator) {
        this.hashGenerator = hashGenerator;
    }

    @Override
    public void run() {
        result = hashGenerator.analyze();
    }

    public String getResult() {
        return result;
    }
}
