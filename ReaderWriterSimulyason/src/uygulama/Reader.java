
package uygulama;

public class Reader extends Thread {
    private final SharedData sharedData;
    private final String readerName;

    public Reader(SharedData sharedData, String name) {
        this.sharedData = sharedData;
        this.readerName = name;
    }

    @Override
    public void run() {
        try {
            sharedData.startRead(readerName);
            // Simulate reading
            Thread.sleep(1000);
            sharedData.endRead(readerName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

