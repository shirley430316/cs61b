import javax.annotation.meta.Exclusive;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    int size;
    Node sentinel;
    Node last;

    public class Node {
        T elem;
        Node next;
        Node prev;
        public Node(T e, Node p, Node n) {
            elem = e;
            next = n;
            prev = p;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        size = 0;
        last = sentinel;
    }

    @Override
    public void addFirst(T x) {

        Node add = new Node(x,sentinel,sentinel.next);
        Node temp = sentinel.next;
        sentinel.next = add;
        if (temp != null)
            temp.prev = add;
        size++;

    }

    @Override
    public void addLast(T x) {

        Node add = new Node(x, last, null);

        last.next = add;
        last = add;

        size++;
    }

    @Override
    public List<T> toList() {

        List<T> l = new ArrayList<T>();
        Node ptr = sentinel;
        while (ptr.next != null) {
            l.add(ptr.next.elem);
            ptr = ptr.next;
        }
        return l;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) return true;
        else return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {

        if (sentinel.next != null) {
            size--;
            sentinel.next = sentinel.next.next;
            return sentinel.next.elem;
        }
        else {
            return null;
        }

    }

    @Override
    public T removeLast() {
        if (sentinel != last) {
            Node temp = last;
            last = last.prev;
            last.next = null;
            return last.elem;
        }
        else {
            return null;
        }
    }

    @Override
    public T get(int index) {
        Node ptr = sentinel;
        for (int i = 0; i < index; i++) {
            ptr = ptr.next;
        }
        return ptr.elem;
    }

    @Override
    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.elem;
        }
        else {
            return getRecursive(index - 1);
        }
    }
}
