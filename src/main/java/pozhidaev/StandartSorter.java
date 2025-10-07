package pozhidaev;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StandartSorter implements Runnable {
    private final List<String> list;
    private final int delayMillis;
    private AtomicInteger swapNumbers;

    public StandartSorter(List<String> list, int delayMillis,  AtomicInteger swapNumbers) {
        this.list = list;
        this.delayMillis = delayMillis;
        this.swapNumbers = swapNumbers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (list) {
                    bubbleSortStep();
                }
                if (delayMillis > 0) {
                    Thread.sleep(delayMillis);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Sorter thread interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private void bubbleSortStep() throws InterruptedException {
        for (int i = 0; i < list.size() - 1; i++) {
            String current = list.get(i);
            String next = list.get(i + 1);

            if (current.compareTo(next) > 0) {
                Collections.swap(list, i, i + 1);
            }
            swapNumbers.incrementAndGet();

            if (delayMillis > 0) {
                Thread.sleep(delayMillis);
            }
        }
    }
}