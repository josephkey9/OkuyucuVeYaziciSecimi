package uygulama;

public class Main {
    public static void main(String[] args) {
        SharedData data = new SharedData();

        // Okuyucular ve yazıcılar oluşturuluyor
        Thread r1 = new Reader(data, "Reader-1");
        Thread r2 = new Reader(data, "Reader-2");
        Thread w1 = new Writer(data, "Writer-1");
        Thread r3 = new Reader(data, "Reader-3");
        Thread w2 = new Writer(data, "Writer-2");

        // Thread'leri başlat
        r1.start();
        r2.start();
        w1.start();
        r3.start();
        w2.start();
    }
}
