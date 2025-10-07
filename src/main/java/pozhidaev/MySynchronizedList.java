package pozhidaev;

import java.util.Iterator;

public class MySynchronizedList implements Iterable<Node> {

    private Node head;

    public synchronized Node getHead() {
        return head;
    }

    public synchronized void setHead(Node head) {
        this.head = head;
    }

    public synchronized void addFirst(String value) {
        Node new_node = new Node(value, head, null);
        head.prev = new_node;
        head = new_node;
    }

    public void swap(Node a, Node b) {
        Node a_prev = a.prev;
        Node b_next = b.next;

        if (a_prev != null) a_prev.lock.lock();
        a.lock.lock();
        b.lock.lock();
        if (b_next != null) b_next.lock.lock();

        if(a_prev != null) a_prev.next = b;
        if(b_next != null) b_next.prev = a;

        b.next = a;
        b.prev = a_prev;

        a.next = b_next;
        a.prev = b;

        if (b_next != null) b_next.lock.unlock();
        a.lock.unlock();
        b.lock.unlock();
        if (a_prev != null) a_prev.lock.unlock();
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
