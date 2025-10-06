package pozhidaev;

import java.util.Iterator;

public class MySynchronizedList implements Iterable<String> {

    private Node head;

    public synchronized void addFirst(String value) {
        head = new Node(value, head);
    }


    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {
            Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public String next() {
                String val = current.value;
                current = current.next;
                return val;
            }
        };
    }
}
