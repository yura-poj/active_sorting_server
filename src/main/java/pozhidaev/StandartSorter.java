package pozhidaev;

import java.util.Collections;
import java.util.List;

public class StandartSorter implements Runnable {
    private final List<String> list;
    private final int delayMillis;

    public StandartSorter(List<String> list, int delayMillis) {
        this.list = list;
        this.delayMillis = delayMillis;
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

            if (delayMillis > 0) {
                Thread.sleep(delayMillis);
            }
        }
    }
}