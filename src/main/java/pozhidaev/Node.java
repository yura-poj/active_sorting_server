package pozhidaev;

import java.util.concurrent.locks.ReentrantLock;

public class Node {
    public String value;
    public Node next;
    public Node prev;
    public final ReentrantLock lock = new ReentrantLock();

    public Node(String value, Node next, Node prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}