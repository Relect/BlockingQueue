import java.io.Serializable;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new BlockingQueue(5);
        new Thread(new Producer(queue)).start();
        Thread.currentThread().sleep(1000);
        new Thread(new Consumer(queue)).start();
    }


    static class Producer implements Runnable {
        private final BlockingQueue queue;

        public Producer(BlockingQueue queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            System.out.println("[Producer] run");
            while (true) {
                try {
                    queue.enqueue(produce());
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private Integer produce() {
            Integer i = new Random().nextInt(100);
            System.out.println("[Producer] produce: " + i);
            return i;
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue queue;

        public Consumer(BlockingQueue queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            System.out.println("[Consumer] run");
            while (true) {
                try {
                    consume();
                    Thread.currentThread().sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void consume() throws InterruptedException {
            Integer i = (Integer) queue.dequeue();
            System.out.println("[Consumer] consumed: " + i);
        }

    }
}
