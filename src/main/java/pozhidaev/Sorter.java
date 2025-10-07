package pozhidaev;

public class Sorter implements Runnable {
    private final MySynchronizedList list;
    private final int delayMillis;

    public Sorter(MySynchronizedList list, int delayMillis) {
        this.list = list;
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        try {
            while (true) {
                bubbleSortStep();
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
        Node nextNode;
        for (Node node : list) {
            if (delayMillis > 0) {
                Thread.sleep(delayMillis);
            }

            nextNode = node.next;
            if (nextNode == null) {
                return;
            }

            if (node.value.compareTo(nextNode.value) > 0) {
                list.swap(node, nextNode);

                if (delayMillis > 0) {
                    Thread.sleep(delayMillis);
                }
            }
        }
    }
}