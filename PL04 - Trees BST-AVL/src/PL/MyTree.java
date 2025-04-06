package PL;

public class MyTree extends BST<Integer> {

    public boolean isSymmetric() {
        return isSymmetric2(this.root().getLeft(), this.root().getRight());
    }

    private boolean isSymmetric2(Node<Integer> left, Node<Integer> right) {

        if (left == null && right == null) return true;
        if (left == null || right == null) return false;

        return (right.getElement().equals(-1 * left.getElement()))
                && isSymmetric2(left.getRight(), right.getLeft())
                && isSymmetric2(right.getLeft(), right.getRight());
    }
}
