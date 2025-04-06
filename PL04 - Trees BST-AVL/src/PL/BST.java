package PL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BST<E extends Comparable<E>> implements BSTInterface<E> {

    /**
     * Nested static class for a binary search tree node.
     */

    protected static class Node<E> {
        private E element;          // an element stored at this node
        private Node<E> left;       // a reference to the left child (if any)
        private Node<E> right;      // a reference to the right child (if any)

        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param e          the element to be stored
         * @param leftChild  reference to a left child node
         * @param rightChild reference to a right child node
         */
        public Node(E e, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            left = leftChild;
            right = rightChild;
        }

        // accessor methods
        public E getElement() {
            return element;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        // update methods
        public void setElement(E e) {
            element = e;
        }

        public void setLeft(Node<E> leftChild) {
            left = leftChild;
        }

        public void setRight(Node<E> rightChild) {
            right = rightChild;
        }
    }

    //----------- end of nested Node class -----------

    protected Node<E> root = null;     // root of the tree


    /* Constructs an empty binary search tree. */
    public BST(Node<E> root) {
        this.root = root;
    }

    public BST() {
        this.root = null;
    }

    /*
     * @return root Node of the tree (or null if tree is empty)
     */
    protected Node<E> root() {

        if (root == null) return null;

        return root;
    }

    /*
     * Verifies if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the Node containing a specific Element, or null otherwise.
     *
     * @param element the element to find
     * @return the Node that contains the Element, or null otherwise
     * <p>
     * This method despite not being essential is very useful.
     * It is written here in order to be used by this class and its
     * subclasses avoiding recoding.
     * So its access level is protected
     */
    protected Node<E> find(Node<E> node, E element) {

        if (node == null || element == null) return null;

        if (element.compareTo(node.getElement()) < 0) {

            return find(node.getLeft(), element);

        } else if (element.compareTo(node.getElement()) > 0) {

            return find(node.getRight(), element);

        } else {
            return node;
        }
    }

    /*
     * Inserts an element in the tree.
     */
    public void insert(E element) {
        root = insert(element, root);
    }

    private Node<E> insert(E element, Node<E> node) {

        if (node == null) {
            return new Node<>(element, null, null);
        }

        if (element.compareTo(node.getElement()) < 0) {
            node.setLeft(insert(element, node.getLeft()));

        } else if (element.compareTo(node.getElement()) > 0) {
            node.setRight(insert(element, node.getRight()));
        }
        return node;
    }

    /**
     * Removes an element from the tree maintaining its consistency as a Binary Search Tree.
     */
    public void remove(E element) {
        root = remove(element, root());
    }

    private Node<E> remove(E element, Node<E> node) {

        if (node == null) {
            return null;    //throw new IllegalArgumentException("Element does not exist");
        }

        if (element.compareTo(node.getElement()) < 0) {

            node.setLeft(remove(element, node.getLeft()));

        } else if (element.compareTo(node.getElement()) > 0) {

            node.setRight(remove(element, node.getRight()));

        } else {
            // Node to be removed found
            if (node.getLeft() == null && node.getRight() == null) {
                return null; // Node is a leaf
            }
            if (node.getLeft() == null) {
                return node.getRight(); // Node has only right child
            }
            if (node.getRight() == null) {
                return node.getLeft(); // Node has only left child
            }
            // Node has two children
            E min = smallestElement(node.getRight());
            node.setElement(min);
            node.setRight(remove(min, node.getRight()));
        }
        return node;
    }

    /*
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    public int size() {
        return size(root);
    }

    private int size(Node<E> node) {

        if (node == null) return 0;

        return size(node.getLeft()) + size(node.getRight()) + 1;
    }

    /*
     * Returns the height of the tree
     * @return height
     */
    public int height() {
        return height(root);
    }

    /*
     * Returns the height of the subtree rooted at Node node.
     * @param node A valid Node within the tree
     * @return height
     */
    protected int height(Node<E> node) {

        if (node == null) return -1;

        int heightLeft = height(node.getLeft());
        int heightRight = height(node.getRight());

        return Math.max(heightLeft, heightRight) + 1;
    }

    /**
     * Returns the smallest element within the tree.
     *
     * @return the smallest element within the tree
     */
    public E smallestElement() {
        return smallestElement(root);
    }

    protected E smallestElement(Node<E> node) {

        if (node == null) return null;

        if (node.getLeft() == null) {

            return node.getElement();

        } else {
            return smallestElement(node.getLeft());
        }
    }

    /*
     * Returns an iterable collection of elements of the tree, reported in in-order.
     * @return iterable collection of the tree's elements reported in in-order
     */
    public Iterable<E> inOrder() {

        List<E> snapshot = new ArrayList<>();

        if (root != null) {
            inOrderSubtree(root, snapshot);   // fill the snapshot recursively
        }
        return snapshot;
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an in-order traversal
     *
     * @param node     Node serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void inOrderSubtree(Node<E> node, List<E> snapshot) {

        if (node == null) return;

        inOrderSubtree(node.getLeft(), snapshot);
        snapshot.add(node.getElement());
        inOrderSubtree(node.getRight(), snapshot);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in pre-order.
     *
     * @return iterable collection of the tree's elements reported in pre-order
     */
    public Iterable<E> preOrder() {

        List<E> snapshot = new ArrayList<>();

        if (root != null) {
            preOrderSubtree(root, snapshot);
        }
        return snapshot;
    }

    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an pre-order traversal
     *
     * @param node     Node serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void preOrderSubtree(Node<E> node, List<E> snapshot) {

        if (node == null) return;

        snapshot.add(node.getElement());
        preOrderSubtree(node.getLeft(), snapshot);
        preOrderSubtree(node.getRight(), snapshot);
    }

    /**
     * Returns an iterable collection of elements of the tree, reported in post-order.
     *
     * @return iterable collection of the tree's elements reported in post-order
     */
    public Iterable<E> posOrder() {

        List<E> snapshot = new ArrayList<>();

        if (root != null) {
            posOrderSubtree(root, snapshot);
        }
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Node node to the given
     * snapshot using an post-order traversal
     *
     * @param node     Node serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void posOrderSubtree(Node<E> node, List<E> snapshot) {

        if (node == null) return;

        posOrderSubtree(node.getLeft(), snapshot);
        posOrderSubtree(node.getRight(), snapshot);
        snapshot.add(node.getElement());
    }

    /*
     * Returns a map with a list of nodes by each tree level.
     * @return a map with a list of nodes by each tree level
     */
    public Map<Integer, List<E>> nodesByLevel() {

        Map<Integer, List<E>> result = new HashMap<>();

        if (root != null) {
            processBstByLevel(root, result, 0);
        }
        return result;
    }

    private void processBstByLevel(Node<E> node, Map<Integer, List<E>> result, int level) {

        if (node == null) return;

        if (result.get(level) == null) {
            List<E> list = new ArrayList<>();
            list.add(node.getElement());
            result.put(level, list);
        } else {
            result.get(level).add(node.getElement());
        }

        processBstByLevel(node.getLeft(), result, level + 1);
        processBstByLevel(node.getRight(), result, level + 1);
    }

//=====================================================================================================================

    /**
     * Returns a string representation of the tree.
     * Draw the tree horizontally
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRec(root, 0, sb);
        return sb.toString();
    }

    private void toStringRec(Node<E> root, int level, StringBuilder sb) {

        if (root == null) return;

        toStringRec(root.getRight(), level + 1, sb);

        if (level != 0) {
            for (int i = 0; i < level - 1; i++)
                sb.append("|\t");
            sb.append("|-------" + root.getElement() + "\n");
        } else
            sb.append(root.getElement() + "\n");

        toStringRec(root.getLeft(), level + 1, sb);
    }

} //----------- end of BST class -----------




  

  