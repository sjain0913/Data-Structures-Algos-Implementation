public class LinkedList<T> implements SimpleList<T> {
    private Node<T> head;
    private int size;

    public boolean isEmpty() {
        if (size== 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }
}
