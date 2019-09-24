import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
/**

/**
 * Your implementation of a BST.
 *
 * @author Saumya Jain
 * @version 1.0
 * @userid sjain395
 * @GTID 903407158
 *
 * Collaborators: Collaboration with Gabby Germanson & Ritesh Malpani
 *
 * Resources: Textbook
 */

public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data cannot"
                    + "be null.");
        }
        for (T t: data) {
            if (data == null) {
                throw new IllegalArgumentException("The entered data cannot"
                        + "be null.");
            }
            add(t);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data cannot"
                    + "be null.");
        }
        if (size == 0) {
            root = new BSTNode<T>(data);
            size++;
        }
        root = addHelper(root, data);
    }

    /**
     * Recursive helper method to traverse tree and add new node
     * @throws IllegalArgumentException if data is null
     * @param current current node that the method will traverse
     * @param data the data to search for
     * @return current node in tree, including newly created node
     * if data does not already exist in the tree
     *
     */
    private BSTNode<T> addHelper(BSTNode<T> current, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data"
                    + "cannot be null.");
        }
        if (current == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(current.getLeft(), data));
            return current;
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight((addHelper(current.getRight(), data)));
            return current;
        } else {
            return current;
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data"
                    + "cannot be null.");
        }
        BSTNode<T> temp = new BSTNode<T>(null);
        root = removeHelper(root, data, temp);
        return temp.getData();
    }

    /**
     * Recursive helper method to traverse tree and remove node
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException if data not found in tree
     * @param current current node that the method will traverse
     * @param data data to search for
     * @param temp stores data from removed node, if found
     * @return current node, may be the original curr's predecessor
     *
     */
    private BSTNode<T> removeHelper(BSTNode<T> current, T data,
                                    BSTNode<T> temp) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data"
                    + " cannot be null.");
        }
        if (current == null) {
            throw new NoSuchElementException("Your entered data"
                    + " could not be found.");
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeHelper(current.getLeft(), data, temp));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight((removeHelper(current.getRight(), data, temp)));
        } else {
            size--;
            temp.setData(current.getData());
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            } else if (current.getLeft() == null
                    && current.getRight() != null) {
                return current.getRight();
            } else if (current.getLeft() != null
                    && current.getRight() == null) {
                return current.getLeft();
            } else {
                BSTNode<T> predecessor = new BSTNode<T>(null);
                current.setLeft(findRemoveMax(current.getLeft(), predecessor));
                current.setData(predecessor.getData());
            }
        }
        return current;
    }

    /**
     * Helper method to find and remove predecessor of node with 2 children
     * @param current current node that the method will traverse
     * @param predecessor temp node to store predecessor data
     * @return removes & returns data of largest child
     */
    private BSTNode<T> findRemoveMax(BSTNode<T> current,
                                     BSTNode<T> predecessor) {
        if (current.getRight() == null) {
            predecessor.setData(current.getData());
            return current.getLeft();
        } else {
            current.setRight(findRemoveMax(current.getRight(), predecessor));
        }
        return current;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data"
                    + "cannot be null.");
        }
        if (size == 0) {
            throw new NoSuchElementException("Your entered data could"
                    + "not be found.");
        }
        BSTNode<T> temp = getHelper(root, data);
        return temp.getData();
    }

    /**
     * Helper method for finding and getting the node that contains data
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException if data not found in tree
     * @param current the current node the method will be traversing
     * @param data the data to search for in the tree
     * @return the node containing the desired data
     */
    private BSTNode<T> getHelper(BSTNode<T> current, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data"
                    + "cannot be null.");
        }
        if (current == null) {
            throw new java.util.NoSuchElementException("Your entered data"
                    + "could not be found.");
        } else if (data.compareTo(current.getData()) < 0) {
            return getHelper(current.getLeft(), data);
        } else if (data.compareTo(current.getData()) > 0) {
            return getHelper(current.getRight(), data);
        } else {
            return current;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data"
                    + "cannot be null.");
        }
        return (containsHelper(root, data) != null);
    }

    /**
     * Helper method for finding and getting the node that contains data
     * @param current the current node the method will be traversing
     * @param data the data to search for in the tree
     * @return the desired data, or null if not found
     */
    private T containsHelper(BSTNode<T> current, T data) {
        if (current == null) {
            return null;
        } else if (data.compareTo(current.getData()) < 0) {
            return containsHelper(current.getLeft(), data);
        } else if (data.compareTo(current.getData()) > 0) {
            return containsHelper(current.getRight(), data);
        } else {
            return current.getData();
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> outcome = new ArrayList<>();
        preorderHelper(root, outcome);
        return outcome;
    }

    /**
     * Helper method for preorder traversal
     * @param current current node that the method will be traverse
     * @param outcome list that elements are stored in
     */
    private void preorderHelper(BSTNode<T> current, ArrayList<T> outcome) {
        if (current != null) {
            outcome.add(current.getData());
            preorderHelper(current.getLeft(), outcome);
            preorderHelper(current.getRight(), outcome);
        }
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> outcome = new ArrayList<>();
        inorderHelper(root, outcome);
        return outcome;
    }

    /**
     * Helper method for inorder traversal
     * @param current current node that the method will be traverse
     * @param outcome list that elements are stored in
     */
    private void inorderHelper(BSTNode<T> current, ArrayList<T> outcome) {
        if (current != null) {
            inorderHelper(current.getLeft(), outcome);
            outcome.add(current.getData());
            inorderHelper(current.getRight(), outcome);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> outcome = new ArrayList<>();
        postorderHelper(root, outcome);
        return outcome;
    }

    /**
     * Helper method for postorder traversal
     * @param current current node that the method will be traverse
     * @param outcome list that elements are stored in
     */
    private void postorderHelper(BSTNode<T> current, ArrayList<T> outcome) {
        if (current != null) {
            postorderHelper(current.getLeft(), outcome);
            postorderHelper(current.getRight(), outcome);
            outcome.add(current.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        BSTNode<T> temp;
        List<T> list = new ArrayList<T>();
        Queue<BSTNode<T>> tempQueue = new LinkedList<BSTNode<T>>();
        if (root != null) {
            tempQueue.add(root);
        }
        while (!tempQueue.isEmpty()) {
            temp = tempQueue.remove();
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                tempQueue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                tempQueue.add(temp.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method to calculate tree height
     * @param current current node that the method will traverse
     * @return height of tree root, -1 if empty true
     */
    private int heightHelper(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return (Math.max(heightHelper(current.getLeft()) + 1,
                    heightHelper(current.getRight()) + 1));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }


    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     **
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *                 50
     *             /        \
     *           25         75
     *         /    \
     *        12    37
     *       /  \    \
     *     10   15   40
     *         /
     *       13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("The entered data"
                    + "cannot be null.");
        } else if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("The entered data does"
                    + "not exist.");
        } else {
            LinkedList<T> tempList = new LinkedList<T>();
            if (data1.equals(data2)) {
                tempList.add(data1);
            } else {
                BSTNode<T> ca = deepestAncestor(root, data1, data2);
                preDeepestAncestor(tempList, ca, data1);
                postDeepestAncestor(tempList, ca, data2);
            }
            return tempList;
        }
    }

    /**
     * Returns the Deepest Ancestor of the two data elements.
     *
     * @param current current node working with
     * @param data1 first data
     * @param data2 second data
     * @return the deepest ancestor of data1 and data2
     */
    private BSTNode<T> deepestAncestor(BSTNode<T> current, T data1, T data2) {
        if (current == null) {
            return null;
        } else if (current.getData().compareTo(data1) > 0
                && current.getData().compareTo(data2) > 0) {
            return deepestAncestor(current.getLeft(), data1, data2);
        } else if (current.getData().compareTo(data1) < 0
                && current.getData().compareTo(data2) < 0) {
            return deepestAncestor(current.getRight(), data1, data2);
        }
        return current;
    }

    /**
     * Helper method for findPathBetween that adds everything
     * less than Deepest Ancestor to list
     *
     * @param list The list to add nodes in path to
     * @param current current node
     * @param data1 start point for path
     */
    private void preDeepestAncestor(LinkedList<T> list, BSTNode<T> current,
                                    T data1) {
        if (current.getData().equals(data1)) {
            if (!(list.contains(data1))) {
                list.addFirst(data1);
            }
        } else if (current.getData().compareTo(data1) < 0) {
            if (!(list.contains(current.getData()))) {
                list.addFirst(current.getData());
            }
            preDeepestAncestor(list, current.getRight(), data1);
        } else if (current.getData().compareTo(data1) > 0) {
            if (!(list.contains(current.getData()))) {
                list.addFirst(current.getData());
            }
            preDeepestAncestor(list, current.getLeft(), data1);
        }
    }

    /**
     * Helper method for findPathBetween that adds everything greater
     * than Deepest Ancestor to list
     *
     * @param list the list to add nodes in path to
     * @param current current node
     * @param data2 end point of path
     */
    private void postDeepestAncestor(LinkedList<T> list, BSTNode<T> current,
                                     T data2) {
        if (current.getData().equals(data2)) {
            if (!(list.contains(data2))) {
                list.add(data2);
            }
        } else if (current.getData().compareTo(data2) < 0) {
            if (!(list.contains(current.getData()))) {
                list.add(current.getData());
            }
            postDeepestAncestor(list, current.getRight(), data2);
        } else if (current.getData().compareTo(data2) > 0) {
            if (!(list.contains(current.getData()))) {
                list.add(current.getData());
            }
            postDeepestAncestor(list, current.getLeft(), data2);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
