package uygulama;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedData {
    private int activeReaders = 0;
    private int activeWriters = 0;
    private int waitingReaders = 0;
    private int waitingWriters = 0;

    private final Lock lock = new ReentrantLock();
    private final Condition okToRead = lock.newCondition();
    private final Condition okToWrite = lock.newCondition();

    
    public void startRead(String readerName) throws InterruptedException {
        lock.lock();
        try {
            waitingReaders++;
            while (activeWriters > 0 || waitingWriters > 0) { // Mesa-style: while
                okToRead.await();
            }
            waitingReaders--;
            activeReaders++;
            System.out.println(readerName + " okuyor. [AR=" + activeReaders + "]");
        } finally {
            lock.unlock();
        }
    }

    
    public void endRead(String readerName) {
        lock.lock();
        try {
            activeReaders--;
            System.out.println(readerName + " okudu. [AR=" + activeReaders + "]");
            if (activeReaders == 0 && waitingWriters > 0) {
                okToWrite.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    
    public void startWrite(String writerName) throws InterruptedException {
        lock.lock();
        try {
            waitingWriters++;
            while (activeReaders > 0 || activeWriters > 0) {
                okToWrite.await();
            }
            waitingWriters--;
            activeWriters++;
            System.out.println(writerName + " yazıyor. [AW=" + activeWriters + "]");
        } finally {
            lock.unlock();
        }
    }

   
    public void endWrite(String writerName) {
        lock.lock();
        try {
            activeWriters--;
            System.out.println(writerName + " yazdi. [AW=" + activeWriters + "]");
            if (waitingWriters > 0) {
                okToWrite.signal(); // writers-first
            } else if (waitingReaders > 0) {
                okToRead.signalAll(); // broadcast to all readers
            }
        } finally {
            lock.unlock();
        }
    }
}
