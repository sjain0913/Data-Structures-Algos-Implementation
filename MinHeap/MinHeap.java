import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
 *
 * @author Saumya Jain
 * @version 1.0
 * @userid sjain395
 * @GTID 903407158
 *
 * Collaboration: Gursimran Singh
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data cannot be empty");
        } else {
            backingArray = (T[]) new Comparable[(data.size() * 2) + 1];
            size = 0;
            for (int i = 1; i <= data.size(); i++) {
                if (data.get(i - 1) == null) {
                    throw new java.lang.IllegalArgumentException(
                            "The element in data cannot be empty");
                } else {
                    backingArray[i] = data.get(i - 1);
                    size++;
                }
            }
        }
        for (int k = (size / 2); k >= 1; k--) {
            downHeap(k);
        }

    }
    /**
     * This returns the left child
     * @param n parent
     * @return the left child
     */
    private T getLeft(int n) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException(
                    "The element index cannot be below zero");
        } else {
            int left = n * 2;
            if (left > size) {
                return null;
            } else {
                return backingArray[left];
            }
        }
    }


    /**
     * returns the right child
     * @param n parent
     * @return the right child
     */
    private T getRight(int n) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException(
                    "The element index cannot be below zero");
        } else {
            int right = n * 2 + 1;
            if (right > size) {
                return null;
            } else {
                return backingArray[right];
            }
        }
    }

    /**
     * Returns the parent
     * @param n child
     * @return the parent
     */
    private T getParent(int n) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException(""
                    + "The element index cannot be below zero");
        } else {
            int parent = n / 2;
            if (parent < 1) {
                return null;
            } else {
                return backingArray[parent];
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data cannot be null");
        } else {
            if (size >= backingArray.length - 1) {
                T[] newElements =
                        (T[]) new Comparable[backingArray.length * 2];
                for (int i = 1; i < backingArray.length; i++) {
                    newElements[i] = backingArray[i];
                }
                backingArray = newElements;
            }
            if (size == 0) {
                backingArray[1] = data;
                size++;
            } else {
                size++;
                backingArray[size] = data;
                upHeap(size);
            }
        }
    }
    /**
     * UpHeap the recursive function
     * @param n performs upHeap on set index
     */
    private void upHeap(int n) {
        T temp;
        if (n > 1) {
            if (getParent(n).compareTo(backingArray[n]) > 0) {
                temp = getParent(n);
                backingArray[n / 2] = backingArray[n];
                backingArray[n] = temp;
                upHeap(n / 2);
            }
        }

    }
    /**
     * Used to see if index is leaf.
     *
     * @param i the index to check
     * @return true or false (if leaf)
     */
    private boolean isLeaf(int i) {
        if (isEmpty() || i > size / 2 && i <= size) {
            return true;
        }
        return false;
    }

    /**
     * DownHeap recursive function
     * @param n index to recurse through
     */
    private void downHeap(int n) {
        if (isLeaf(n)) {
            return;
        }
        T curr = this.backingArray[n];
        T leftChild = this.backingArray[2 * n];
        T rightChild = this.backingArray[2 * n + 1];

        if (curr != null) {
            if ((leftChild != null && curr.compareTo(leftChild) > 0)
                    || (rightChild != null && curr.compareTo(rightChild) > 0)) {

                if (rightChild != null && rightChild.compareTo(leftChild) < 0) {
                    swap(n, 2 * n + 1);
                    downHeap(2 * n + 1);
                } else if (leftChild != null) {
                    swap(n, 2 * n);
                    downHeap(2 * n);
                }
            }
        }
    }
    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("The heap cannot be empty.");
        }
        T toReturn = backingArray[1];
        swap(1, size);
        backingArray[size] = null;
        size--;
        downHeap(1);
        return toReturn;

    }
    /**
     * Swapping elements of backingarray
     * @param a first element
     * @param b second element
     */
    private void swap(int a, int b) {
        T temp;
        temp = backingArray[a];
        backingArray[a] = backingArray[b];
        backingArray[b] = temp;
    }
    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("The heap cannot be empty");
        } else {
            return backingArray[1];
        }

    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
