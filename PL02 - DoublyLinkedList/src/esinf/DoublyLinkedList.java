package esinf;

import java.util.*;

public class DoublyLinkedList<E> implements Iterable<E>, Cloneable {

    // instance variables of the DoublyLinkedList
    private final Node<E> header;     // header sentinel

    private final Node<E> trailer;    // trailer sentinel

    private int size = 0;       // number of elements in the list

    private int modCount = 0;   // number of modifications to the list (adds or removes)

    /**
     * Creates both elements which act as sentinels
     */
    public DoublyLinkedList() {

        header = new Node<>(null, null, null);      // create header
        trailer = new Node<>(null, header, null);   // trailer is preceded by header
        header.setNext(trailer);                    // header is followed by trailer
    }

    /**
     * Returns the number of elements in the linked list
     *
     * @return the number of elements in the linked list
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks if the list is empty
     *
     * @return true if the list is empty, and false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns (but does not remove) the first element in the list
     *
     * @return the first element of the list
     */
    public E first() {

        if (isEmpty()) return null;

        return header.getNext().getElement();
    }

    /**
     * Returns (but does not remove) the last element in the list
     *
     * @return the last element of the list
     */
    public E last() {

        if (isEmpty()) return null;

        return trailer.getPrev().getElement();
    }

// public update methods

    /**
     * Adds an element e to the front of the list
     *
     * @param e element to be added to the front of the list
     */
    public void addFirst(E e) {

        // place just after the header
        Node<E> first = header.getNext();

        Node<E> newNode = new Node<>(e, header, first);

        header.setNext(newNode);

        if (first != null) {
            first.setPrev(newNode);
        }
        size++;
    }

    /**
     * Adds an element e to the end of the list
     *
     * @param e element to be added to the end of the list
     */
    public void addLast(E e) {
        // place just before the trailer
        Node<E> last = trailer.getPrev();

        Node<E> newNode = new Node<>(e, last, trailer);

        if (last != null) {
            last.setNext(newNode);
        }

        trailer.setPrev(newNode);

        size++;
    }

    /**
     * Removes and returns the first element of the list
     *
     * @return the first element of the list
     */
    public E removeFirst() {

        if (isEmpty()) return null;

        Node<E> first = header.getNext();

        E element = first.getElement();

        header.setNext(first.getNext());

        first.getNext().setPrev(header);

        size--;

        return element;
    }

    /**
     * Removes and returns the last element of the list
     *
     * @return the last element of the list
     */
    public E removeLast() {

        if (isEmpty()) return null;

        Node<E> last = trailer.getPrev();

        E element = last.getElement();

        trailer.setPrev(last.getPrev());

        last.getPrev().setNext(trailer);

        size--;

        return element;
    }

// private update methods

    /**
     * Adds an element e to the linked list between the two given nodes.
     */
    private void addBetween(E e, Node<E> predecessor, Node<E> successor) {

        Node<E> newNode = new Node<>(e, predecessor, successor);

        predecessor.setNext(newNode);
        successor.setPrev(newNode);

        size++;
    }

    /**
     * Removes a given node from the list and returns its content.
     */
    private E remove(Node<E> node) {

        Node<E> ancestor = node.getPrev();
        Node<E> precursor = node.getNext();

        ancestor.setNext(precursor);
        precursor.setPrev(ancestor);

        size--;

        return node.getElement();
    }

    // Overriden methods
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoublyLinkedList<?> that = (DoublyLinkedList<?>) o;

        if (size != that.size) return false;

        Node<?> walkA = this.header.getNext();
        Node<?> walkB = that.header.getNext();

        while (walkA != this.trailer) {
            if (!Objects.equals(walkA.getElement(), walkB.getElement())) return false;
            walkA = walkA.getNext();
            walkB = walkB.getNext();
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, trailer);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        try {

            DoublyLinkedList<E> copy = (DoublyLinkedList<E>) super.clone();
            copy.header.setNext(copy.trailer);
            copy.trailer.setPrev(copy.header);
            copy.size = 0;
            copy.modCount = 0;

            Node<E> walk = header.getNext();

            while (walk != trailer) {
                copy.addLast(walk.getElement());
                walk = walk.getNext();
            }

            copy.size = this.size; // Ensure the size is correctly set
            return copy;

        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    //---------------- nested DoublyLinkedListIterator class ----------------
    private class DoublyLinkedListIterator implements ListIterator<E> {

        private DoublyLinkedList.Node<E> nextNode, prevNode, lastReturnedNode; // node that will be returned using next and prev respectively

        private int nextIndex;  // Index of the next element

        private int expectedModCount;  // Expected number of modifications = modCount;

        public DoublyLinkedListIterator() {
            this.prevNode = header;
            this.nextNode = header.getNext();
            lastReturnedNode = null;
            nextIndex = 0;
            expectedModCount = modCount;
        }

        final void checkForComodification() {  // invalidate iterator on list modification outside the iterator
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            return nextNode != trailer;
        }

        @Override
        public E next() throws NoSuchElementException {

            checkForComodification();

            if (!hasNext()) throw new NoSuchElementException();

            lastReturnedNode = nextNode;
            prevNode = nextNode;
            nextNode = nextNode.getNext();

            nextIndex++;

            return lastReturnedNode.getElement();
        }

        @Override
        public boolean hasPrevious() {
            return prevNode != header;
        }

        @Override
        public E previous() throws NoSuchElementException {

            checkForComodification();

            if (!hasPrevious()) throw new NoSuchElementException();

            lastReturnedNode = prevNode;
            nextNode = prevNode;
            prevNode = prevNode.getPrev();

            nextIndex--;

            return lastReturnedNode.getElement();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() throws NoSuchElementException {

            checkForComodification();

            if (lastReturnedNode == null) throw new NoSuchElementException();

            if (lastReturnedNode == nextNode) {
                nextNode = nextNode.getNext();

            } else {
                prevNode = prevNode.getPrev();
                nextIndex--;
            }

            DoublyLinkedList.this.remove(lastReturnedNode);
            lastReturnedNode = null;
            expectedModCount = modCount;
        }

        @Override
        public void set(E e) throws NoSuchElementException {
            if (lastReturnedNode == null) throw new NoSuchElementException();
            checkForComodification();

            lastReturnedNode.setElement(e);
        }

        @Override
        public void add(E e) {
            checkForComodification();

            DoublyLinkedList.this.addBetween(e, prevNode, nextNode);

            prevNode = prevNode.getNext();
            lastReturnedNode = null;
            nextIndex++;
            expectedModCount = modCount;
        }

    }    //----------- end of inner DoublyLinkedListIterator class ----------

    //---------------- Iterable implementation ----------------
    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator();
    }

    public ListIterator<E> listIterator() {
        return new DoublyLinkedListIterator();
    }

    //---------------- nested Node class ----------------
    private static class Node<E> {

        private E element;      // reference to the element stored at this node
        private Node<E> prev;   // reference to the previous node in the list
        private Node<E> next;   // reference to the subsequent node in the list

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setElement(E element) { // Not on the original interface. Added due to list iterator implementation
            this.element = element;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    } //----------- end of nested Node class ----------

}