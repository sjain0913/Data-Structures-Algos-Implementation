import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Saumya Jain
 * @version 1.0
 * @userid sjain395
 * @GTID 903407158
 *
 * Collaborators: Collaborated with Gursimran Singh, Colton Hazelton, Jiale Zheng
 *
 * Resources: Youtube Tutorials
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered data"
                    + " cannot be null!");
        }
        for (T t: data) {
            if (t == null) {
                throw new IllegalArgumentException("The entered data"
                        + " cannot be null!");
            }
            add(t);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null!");
        }
        root = addHelper(data, root);
    }

    /**
     * Helper method to add a node to the AVL
     *
     * @param data the data to be added to the tree
     * @param root the root to compare data to
     * @return A node representing the recursive state of the new tree
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> root) {
        if (root == null) {
            root = new AVLNode<>(data);
            root.setHeight(1);
            root.setBalanceFactor(0);
            size++;
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(addHelper(data, root.getLeft()));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(addHelper(data, root.getRight()));
        }
        updateH(root);
        updateBF(root);
        root = rebalance(root);
        return root;
    }

    /**
     * Helper method to update balance factor after every change in AVL tree
     * @param root the root to be updated
     */
    private void updateBF(AVLNode<T> root) {
        if (root.getLeft() != null && root.getRight() != null) {
            root.setBalanceFactor(root.getLeft().getHeight()
                    - root.getRight().getHeight());
        } else if (root.getLeft() != null) {
            root.setBalanceFactor(1 + root.getLeft().getHeight());
        } else if (root.getRight() != null) {
            root.setBalanceFactor(-1 - root.getRight().getHeight());
        } else {
            root.setBalanceFactor(0);
        }
    }

    /**
     * Helper method to update height of a node
     * @param root the root height to be updated
     */
    private void updateH(AVLNode<T> root) {
        if (root == null) {
            return;
        }
        int heightLeft = -1;
        int heightRight = -1;
        if (root.getLeft() != null) {
            heightLeft = root.getLeft().getHeight();
        }
        if (root.getRight() != null) {
            heightRight = root.getRight().getHeight();
        }
        root.setHeight(1 + Math.max(heightLeft, heightRight));
    }

    /**
     * Helper method to rebalance the AVL
     * @param root the root to be rebalanced
     * @return new root after rebalancing
     */
    private AVLNode<T> rebalance(AVLNode<T> root) {
        if (root.getBalanceFactor() == 2) {
            if (root.getLeft().getBalanceFactor() == 1
                    || root.getLeft().getBalanceFactor() == 0) {
                root = rotateRight(root);
            } else if (root.getLeft().getBalanceFactor() == -1) {
                root = rotateLeftRight(root);
            }
        }
        if (root.getBalanceFactor() == -2) {
            if (root.getRight().getBalanceFactor() == -1
                    || root.getRight().getBalanceFactor() == 0) {
                root = rotateLeft(root);
            } else if (root.getRight().getBalanceFactor() == 1) {
                root = rotateRightLeft(root);
            }
        }
        return root;
    }

    /**
     * Helper method for rotating clockwise around node
     * @param root root to be rotated right
     * @return new parent after the rotation
     */
    private AVLNode<T> rotateRight(AVLNode<T> root) {
        AVLNode<T> newParent = root.getLeft();
        root.setLeft(newParent.getRight());
        newParent.setRight(root);
        updateH(root);
        updateBF(root);
        if (newParent.getLeft() != null) {
            updateBF(newParent.getLeft());
            updateH(newParent.getLeft());
        }
        updateH(newParent);
        updateBF(newParent);
        return newParent;
    }

    /**
     * Helper method for rotating counter-clockwise around node
     * @param root root to be rotated left
     * @return new parent after the rotation
     */
    private AVLNode<T> rotateLeft(AVLNode<T> root) {
        AVLNode<T> newParent = root.getRight();
        root.setRight(newParent.getLeft());
        newParent.setLeft(root);
        updateH(root);
        updateBF(root);
        if (newParent.getRight() != null) {
            updateH(newParent.getRight());
            updateBF(newParent.getRight());
        }
        updateH(newParent);
        updateBF(newParent);
        return newParent;
    }

    /**
     * Helper method for rotating left and right for a node
     * @param root root to be rotated right
     * @return new parent node after the rotation
     */
    private AVLNode<T> rotateLeftRight(AVLNode<T> root) {
        root.setLeft(rotateLeft(root.getLeft())); //rotate the child
        return rotateRight(root);
    }

    /**
     * Helper method for rotating right and left for a node
     * @param root root to be rotated right
     * @return new parent node after the rotation
     */
    private AVLNode<T> rotateRightLeft(AVLNode<T> root) {
        root.setRight(rotateRight(root.getRight()));
        return rotateLeft(root);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null!");
        }
        AVLNode<T> temp = new AVLNode<T>(null);
        root = removeHelper(root, data, temp);
        if (temp.getData() == null) {
            throw new NoSuchElementException("The data is not in the AVL!");
        }
        size--;
        return temp.getData();
    }

    /**
     *
     * @param node the current node to be recursed from
     * @param data the data to be removed
     * @param dummy the dummy node
     * @return the node to be pointed toward
     */
    private AVLNode<T> removeHelper(AVLNode<T> node, T data, AVLNode<T> dummy) {
        if (node == null) {
            return null;
        }
        if (node != null && data.equals(node.getData())) {
            dummy.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if ((node.getLeft() != null || node.getRight() != null)
                    && !(node.getLeft() != null && node.getRight() != null)) {
                if (node.getLeft() != null) {
                    updateH(node);
                    updateBF(node);
                    node = rotationCondition(node);
                    return node.getLeft();
                } else {
                    updateH(node);
                    updateBF(node);
                    node = rotationCondition(node);
                    return node.getRight();
                }
            } else {
                AVLNode<T> temp = new AVLNode<T>(null);
                node.setRight(successorHelper(node.getRight(), temp));
                node.setData(temp.getData());
            }
        }
        if (node != null && data.compareTo(node.getData()) > 0) {
            node.setRight(this.removeHelper(node.getRight(), data, dummy));
            updateH(node);
            updateBF(node);
            node = rotationCondition(node);
            return node;
        } else if (node != null && data.compareTo(node.getData()) < 0) {
            node.setLeft(this.removeHelper(node.getLeft(), data, dummy));
            updateH(node);
            updateBF(node);
            node = rotationCondition(node);
            return node;
        }
        updateH(node);
        updateBF(node);
        node = rotationCondition(node);
        return node;
    }

    /**
     *
     * @param node the node from which recursion takes place
     * @param temp the temporary node which stores the data in a given node
     * @return the predecessor node
     */
    private AVLNode<T> successorHelper(AVLNode<T> node, AVLNode<T> temp) {
        if (node.getLeft() == null) {
            temp.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(successorHelper(node.getLeft(), temp));
            updateH(node);
            updateBF(node);
            node = rotationCondition(node);
        }
        return node;
    }

    /**
     * Helper method to check what rotations to perform
     * @param current Current node at which check is being performed
     * @return Node to be pointed at
     */
    private AVLNode<T> rotationCondition(AVLNode<T> current) {
        if (Math.abs(current.getBalanceFactor()) > 1) {
            if (current.getBalanceFactor() > 0) {
                if (current.getLeft().getBalanceFactor() < 0) {
                    current.setLeft(rotateLeft(current.getLeft()));
                    return rotateRight(current);
                } else {
                    return rotateRight(current);
                }
            } else if (current.getBalanceFactor() < 0) {
                if (current.getRight().getBalanceFactor() > 0) {
                    current.setRight(rotateRight(current.getRight()));
                    return rotateLeft(current);
                } else {
                    return rotateLeft(current);
                }
            }
        }
        return current;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
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
            throw new IllegalArgumentException("The data cannot be null!");
        }
        return getHelper(data, root);
    }

    /**
     * Helper method to find data in an AVL
     *
     * @param data the data being searched for
     * @param root the node being compared to data
     * @return the data of the node that matches data
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private T getHelper(T data, AVLNode<T> root) {
        if (root == null) {
            throw new java.util.NoSuchElementException("Element does not"
                    + " exist in the tree.");
        }
        if (data.compareTo(root.getData()) < 0) {
            return getHelper(data, root.getLeft());
        } else if (data.compareTo(root.getData()) > 0) {
            return getHelper(data, root.getRight());
        }
        return root.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null!");
        }
        return containsHelper(data, root);
    }

    /**
     * Helper method to check if given data is in the AVL tree
     *
     * @param data the data being checked
     * @param root the current node being compared to data
     * @return boolean of whether data is in the AVL
     */
    private boolean containsHelper(T data, AVLNode<T> root) {
        if (root == null) {
            return false;
        }
        if (data.compareTo(root.getData()) < 0) {
            return containsHelper(data, root.getLeft());
        } else if (data.compareTo(root.getData()) > 0) {
            return containsHelper(data, root.getRight());
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data"
                    + " cannot be null!");
        }
        AVLNode<T> temp = new AVLNode<>(null);
        return predecessorHelper(root, data, temp);
    }

    /**
     * Helper method for predecessor data
     * @param current the current node
     * @param data data to be searched
     * @param newNode created node which contains data
     * @return data for predecessor
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private T predecessorHelper(AVLNode<T> current, T data,
                                AVLNode<T> newNode) {
        if (current == null) {
            throw new java.util.NoSuchElementException("The data is not"
                    + " in the AVL tree!");
        } else if (data.compareTo(current.getData()) < 0) {
            return predecessorHelper(current.getLeft(), data, newNode);
        } else if (data.compareTo(current.getData()) > 0) {
            newNode.setData(current.getData());
            return predecessorHelper(current.getRight(), data, newNode);
        } else {
            if (current.getLeft() != null) {
                return predecessorHelperTwo(current.getLeft(), newNode);
            } else if (current.getLeft() == null && newNode.getData() != null) {
                return newNode.getData();
            } else {
                return null;
            }
        }
    }

    /**
     * Helper method for predecessor method
     * @param current the current node
     * @param newNode created node that contains predecessor data
     * @return data for predecessor
     */
    private T predecessorHelperTwo(AVLNode<T> current, AVLNode<T> newNode) {
        if (current.getRight() == null) {
            newNode.setData(current.getData());
            return newNode.getData();
        } else {
            return predecessorHelperTwo(current.getRight(), newNode);
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \      \
     *        10  15    40    85
     *           /
     *          13
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > this.size()) {
            throw new java.lang.IllegalArgumentException("Your argument"
                    + " is invalid!");
        }
        List<T> toReturn = new ArrayList<>();
        if (k == 0) {
            return toReturn;
        } else {
            toReturn = inOrder(root, toReturn, k);
            return toReturn;
        }
    }

    /**
     * Helper method to get inorder traversal
     * @param current current node
     * @param temp list to hold traversed data
     * @param counter keeps track
     * @return inorder traversal of AVL
     */
    private List<T> inOrder(AVLNode current, List<T> temp, int counter) {
        if (current == null) {
            return null;
        }
        if (temp.size() == counter) {
            return null;
        } else {
            inOrder(current.getLeft(), temp, counter);
            if (temp.size() != counter) {
                temp.add((T) current.getData());
            }
            inOrder(current.getRight(), temp, counter);
        }
        return temp;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
