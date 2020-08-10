import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Barrier mechanism demo
 * Servicemen working on car simultaneously and
 * wait at the barrier when all services are finished
 */


public class BarrierDemo {

    private static CyclicBarrier barrier;

    static class Serviceman implements Runnable {

        private final int num; // service id

        public Serviceman(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println("Service " + num + "start");
            try {
                Thread.sleep(num * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Service " + num + "done");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int serviceNum = 2;
        barrier = new CyclicBarrier(serviceNum,
                () -> System.out.println("All services done, free to ride!"));
        for (int i = 1; i <= serviceNum; i++) {
            Thread thread = new Thread(new Serviceman(i));
            thread.start();
        }
    }
}
