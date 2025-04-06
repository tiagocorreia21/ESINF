package PL;

import java.lang.reflect.Array;
import java.util.*;

public class TREE<E extends Comparable<E>> extends BST<E> {

    /*
     * @param element A valid element within the tree
     * @return the path to a given element in the tree
     */
    public List<E> path(E elem) {

        List<E> pathList = new ArrayList<>();

        if (elem == null || root == null) return null;

        if (findPath(elem, root, pathList)) {
            return pathList;
        }
        return null;
    }

    private boolean findPath(E elem, Node<E> node, List<E> pathList) {

        if (node == null) return false;

        pathList.add(node.getElement());

        if (elem.compareTo(node.getElement()) == 0) {
            return true;

        } else if (elem.compareTo(node.getElement()) < 0) {

            if (findPath(elem, node.getLeft(), pathList)) return true;

        } else {

            if (findPath(elem, node.getRight(), pathList)) return true;
        }

        pathList.remove(pathList.size() - 1); // Remove the node if not in path
        return false;
    }

    /*
     * @return the set of the leaf node elements of the tree
     */
    public Set<E> leafs() {

        Set<E> leafs = new HashSet<>();

        if (root == null) return null;

        findLeafs(root, leafs);

        return leafs;
    }

    private void findLeafs(Node<E> node, Set<E> leafs) {

        if (root == null) return;

        if (node.getLeft() == null && node.getRight() == null) {
            leafs.add(node.getElement());
            return;
        }

        if (node.getLeft() != null) {
            findLeafs(node.getLeft(), leafs);
        }

        if (node.getRight() != null) {
            findLeafs(node.getRight(), leafs);
        }
    }

    /*
     * @return an array with the minimum and the maximum values of the tree
     */
    public E[] range() {

        if (root == null) return null;

        E min = minimum(root).getElement();

        E max = maximum(root).getElement();

        E[] rangeArray = (E[]) Array.newInstance(min.getClass(), 2);

        rangeArray[0] = min;
        rangeArray[1] = max;

        return rangeArray;
    }

    private Node<E> minimum(Node<E> node) {

        if (node.getLeft() != null) {
            return minimum(node.getLeft());
        }
        return node;
    }

    private Node<E> maximum(Node<E> node) {

        if (node.getRight() != null) {
            return maximum(node.getRight());
        }
        return node;
    }

    /*
     *  @return the set of elements belonging to the diameter of the BST
     */
    public Set<E> diameter() {

        Set<E> list = new TreeSet<>();

        diameterElements(root, list);

        return list;
    }

    private Set<E> diameterElements(Node<E> node, Set<E> list) {

        if (node == null) return new TreeSet<>();

        Set<E> left = diameterElements(node.getLeft(), list);
        Set<E> right = diameterElements(node.getRight(), list);

        int currentDiameter = 1 + left.size() + right.size();

        if (list.size() < currentDiameter) {

            list.clear();
            list.addAll(left);
            list.add(node.getElement());
            list.addAll(right);
        }

        if (left.size() >= right.size()) {

            left.add(node.getElement());
            return left;

        } else {

            right.add(node.getElement());
            return right;
        }
    }

    /*
     *  @return the previous element of the tree for a given element
     */
    public E findPredecessor(E element) {

        Node<E> predessesor = findPredessessor(root(), null, element);

        if (element == null) return null;

        return predessesor.getElement();
    }

    private Node<E> findPredessessor(Node<E> node, Node<E> pred, E key) {

        if (root == null) return null;

        if (node == null) return null;

        if (node.getElement().equals(key)) {

            if (node.getLeft() != null) {

                return maximum(node.getLeft());
            }

        } else if (key.compareTo(node.getElement()) < 0) {

            return findPredessessor(node.getLeft(), pred, key);

        } else {

            pred = node;
            return findPredessessor(node.getRight(), pred, key);
        }
        return pred;
    }

    /*
     * – verify if the current and tree BST are identical.
     */
    public boolean identical(BST<E> tree) {

        return identical(this.root, tree.root());
    }

    private boolean identical(Node<E> node1, Node<E> node2) {

        if (node1 == null && node2 == null) return true;

        if (node1 == null || node2 == null) return false;

        return node1.getElement().equals(node2.getElement()) &&
                identical(node1.getLeft(), node2.getLeft()) &&
                identical(node1.getRight(), node2.getRight());

    }

    /*
     * – remove all elements in the current BST that are outside the range [low, high]
     */
    public void truncate(E low, E high) {

        root = truncate(root, low, high);
    }

    private Node<E> truncate(Node<E> node, E low, E high) {

        if (node == null) return node;

        node.setLeft(truncate(node.getLeft(), low, high));
        node.setRight(truncate(node.getRight(), low, high));

        if (node.getElement().compareTo(low) < 0) {

            node = node.getRight();

        } else if (node.getElement().compareTo(high) < 0) {

            node = node.getLeft();
        }

        return node;
    }

    /*
     *– return true if BST<E> tree is a sub tree of the BST<E>.
     */
    public boolean isSubTree(BST<E> tree) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public E minimumSubtree(Set<E> elems) {

        Set<E> remaining = new TreeSet<>();

        remaining.addAll(elems);

        Node<E> node = lowestCommonAncestorSet(root(), elems, remaining);

        if (node == null) {

            for (E element : remaining) {

                if (find(root(), element) == null) {
                    return null;
                }
            }
            return node.getElement();
        }

        return null;
    }

    private Node<E> lowestCommonAncestorSet(Node<E> node, Set<E> elems, Set<E> remaining) {

        if (node == null) return null;

        if (elems.contains(node.getElement())) {
            remaining.remove(node.getElement());
            return node;
        }

        Node<E> left_lca = lowestCommonAncestorSet(node.getLeft(), elems, remaining);
        Node<E> right_lca = lowestCommonAncestorSet(node.getRight(), elems, remaining);

        if (left_lca != null && right_lca != null) {
            return node;
        }

        return (left_lca != null) ? left_lca : right_lca;
    }
}
