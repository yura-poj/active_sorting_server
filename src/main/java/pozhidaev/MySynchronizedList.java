package pozhidaev;

import java.util.Iterator;

public class MySynchronizedList implements Iterable<Node> {

    private volatile Node head;

    public synchronized Node getHead() {
        return head;
    }

    public synchronized void setHead(Node head) {
        this.head = head;
    }

    public synchronized void addFirst(String value) {
        if (head == null) {
            head = new Node(value, null, null);
            return;
        }
        Node new_node = new Node(value, head, null);
        head.prev = new_node;
        head = new_node;
    }

    public void swap(Node a, Node b) {
        if (a == null || b == null || a == b) return;

        if (b.next == a) { Node t = a; a = b; b = t; }

        if (a.next != b || b.prev != a) {
            throw new IllegalArgumentException("swap поддерживает только соседние узлы (a <-> b).");
        }

        Node a_prev = a.prev;
        Node b_next = b.next;

        if (a_prev != null) a_prev.lock.lock();
        a.lock.lock();
        b.lock.lock();
        if (b_next != null) b_next.lock.lock();

        try {
            if (a.prev != a_prev || a.next != b || b.prev != a || b.next != b_next) {
                return;
            }

            if (a_prev != null) {
                a_prev.next = b;
            } else {
                head = b;
            }
            if (b_next != null) {
                b_next.prev = a;
            }

            b.prev = a_prev;
            b.next = a;

            a.prev = b;
            a.next = b_next;
        } finally {
            if (b_next != null) b_next.lock.unlock();
            b.lock.unlock();
            a.lock.unlock();
            if (a_prev != null) a_prev.lock.unlock();
        }
    }

    @Override
    public Iterator<Node> iterator() {
        return new Iterator<Node>() {
            Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node next() {
                Node val = current;
                current = current.next;
                return val;
            }
        };
    }
}
