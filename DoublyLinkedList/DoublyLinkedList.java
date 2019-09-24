/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Saumya Jain
 * @version 1.0
 * @userid sjain395
 * @GTID 903407158
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, data is null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Error, index is negative"
                    + "or larger than size");
        }
        DoublyLinkedListNode<T> tempnode = new DoublyLinkedListNode(data);
        if (index == 0) {
            if (head == null) {
                head = tempnode;
                tail = tempnode;
            } else {
                tempnode.setNext(head);
                tempnode.setPrevious(null);
                head.setPrevious(tempnode);
                head = tempnode;
            }
        } else if (index == size) {
            if (tail == null) {
                head = tempnode;
                tail = tempnode;
            } else {
                tempnode.setNext(null);
                tempnode.setPrevious(tail);
                tail.setNext(tempnode);
                tail = tempnode;
            }
        } else {
            DoublyLinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            tempnode.setNext(current);
            tempnode.setPrevious(current.getPrevious());
            (current.getPrevious()).setNext(tempnode);
            current.setPrevious(tempnode);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        DoublyLinkedListNode<T> tempnode = new DoublyLinkedListNode(data);
        if (head == null) {
            head = tempnode;
            tail = tempnode;
        } else {
            tempnode.setNext(head);
            tempnode.setPrevious(null);
            head.setPrevious(tempnode);
            head = tempnode;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        DoublyLinkedListNode<T> tempnode = new DoublyLinkedListNode(data);
        if (tail == null) {
            head = tempnode;
            tail = tempnode;
        } else {
            tempnode.setNext(null);
            tempnode.setPrevious(tail);
            tail.setNext(tempnode);
            tail = tempnode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error, index is negative"
                    + "or larger than size");
        }
        if (index == 0) {
            DoublyLinkedListNode<T> current = head;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head.getNext().setPrevious(null);
                head = head.getNext();
            }
            size--;
            return current.getData();
        } else if (index == size - 1) {
            DoublyLinkedListNode<T> current = tail;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                tail.getPrevious().setNext(null);
                tail = tail.getPrevious();
            }
            size--;
            return current.getData();
        } else {
            DoublyLinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            current.getNext().setPrevious(current.getPrevious());
            current.getPrevious().setNext(current.getNext());
            size--;
            return current.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The list is empty!");
        }
        DoublyLinkedListNode<T> current = head;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head.getNext().setPrevious(null);
            head = head.getNext();
        }
        size--;
        return current.getData();
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The list is empty!");
        }
        DoublyLinkedListNode<T> current = tail;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }
        size--;
        return current.getData();
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error, index is negative"
                    + "or larger than size");
        }
        DoublyLinkedListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data provided "
                    + "is null.");
        } else if (size == 0) {
            throw new java.util.NoSuchElementException("The data is not found");
        } else {
            T returnData = head.getData();
            int counter = 1;
            DoublyLinkedListNode<T> curr = tail;
            while (counter < size && !(curr.getData().equals(data))) {
                curr = curr.getPrevious();
                counter++;
            }
            if (curr.getData().equals(data)) {
                if (curr == head) {
                    removeFromFront();
                    return returnData;
                } else if (curr == tail) {
                    removeFromBack();
                    return tail.getData();
                } else {
                    curr.getPrevious().setNext(curr.getNext());
                    curr.getNext().setPrevious(curr.getPrevious());
                    returnData = curr.getData();
                    curr.setPrevious(null);
                    curr.setNext(null);
                    size--;
                    return returnData;
                }
            } else {
                throw new java.util.NoSuchElementException("The data is "
                        + "not found");
            }
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        DoublyLinkedListNode<T> current = head;
        Object[] backingArray = (Object[]) new Object[size];
        for (int i = 0; i < size; i++) {
            backingArray[i] = current.getData();
            current = current.getNext();
        }
        return backingArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
