public class printer<T> extends LinkedListDeque61B<T> {

    public void printDeque() {

        Node ptr = sentinel;

        while (ptr.next != null) {
            System.out.print(ptr.next.elem + " ");
            ptr = ptr.next;
        }
    }
}