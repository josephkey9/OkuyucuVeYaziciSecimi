package uygulama;

public class Writer extends Thread {
    private final SharedData sharedData;
    private final String writerName;

    public Writer(SharedData sharedData, String name) {
        this.sharedData = sharedData;
        this.writerName = name;
    }

    @Override
    public void run() {
        try {
            sharedData.startWrite(writerName);
            // Simulate writing
            Thread.sleep(1500);
            sharedData.endWrite(writerName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

