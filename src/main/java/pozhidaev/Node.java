package pozhidaev;

import java.util.concurrent.locks.ReentrantLock;

public class Node {
    public String value;
    public Node next;
    public final ReentrantLock lock = new ReentrantLock();

    public Node(String value, Node next) {
        this.value = value;
        this.next = next;
    }
}