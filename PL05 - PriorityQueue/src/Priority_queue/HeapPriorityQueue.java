package Priority_queue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
    /**
     * primary collection of priority queue entries
     */
    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

    /**
     * Creates an empty priority queue based on the natural ordering of its keys.
     */
    public HeapPriorityQueue() {
        super();
    }

    /**
     * Creates an empty priority queue using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the priority queue
     */
    public HeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Creates a priority queue initialized with the respective
     * key-value pairs.  The two arrays given will be paired
     * element-by-element. They are presumed to have the same
     * length. (If not, entries will be created only up to the length of
     * the shorter of the arrays)
     *
     * @param keys   an array of the initial keys for the priority queue
     * @param values an array of the initial values for the priority queue
     */
    public HeapPriorityQueue(K[] keys, V[] values) {
        super();

        int n = Math.min(keys.length, values.length);

        for (int i = 0; i < n; i++) {

            heap.add(new PQEntry<>(keys[i], values[i]));
        }
        System.out.println(this.toString());
        buildHeap();
    }

    // protected utilities
    protected int parent(int j) {
        return (j - 1) / 2;
    }     // truncating division

    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    /**
     * Exchanges the entries at indices i and j of the array list.
     */
    protected void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Moves the entry at index j higher, if necessary, to restore the heap property.
     */
    protected void percolateUp(int j) {

        while (j > 0) {

            int p = parent(j);

            if (compare(heap.get(j), heap.get(p)) >= 0) {
                break;
            }

            swap(j, p);
            j = p;
        }
    }

    /**
     * Moves the entry at index j lower, if necessary, to restore the heap property.
     */
    protected void percolateDown(int j) {

        while (hasLeft(j)) {

            int leftIndex = left(j);
            int smallChildIndex = leftIndex;

            if (hasRight(j)) {

                int rightIndex = right(j);

                if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0) {

                    smallChildIndex = rightIndex;
                }
            }
            if (compare(heap.get(smallChildIndex), heap.get(j)) > 0) {
                break;
            }
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }

    /**
     * Performs a batch bottom-up construction of the heap in O(n) time.
     */
    protected void buildHeap() {

        int startIndex = parent(size() - 1);

        for (int j = startIndex; j >= 0; j--) {
            System.out.println(heap.get(j).getValue());
            System.out.println("####################");
            percolateDown(j);
            System.out.println(this.toString());
        }
    }

    // public methods

    /**
     * Returns the number of items in the priority queue.
     *
     * @return number of items
     */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Returns (but does not remove) an entry with minimal key.
     *
     * @return entry having a minimal key (or null if empty)
     */
    @Override
    public Entry<K, V> min() {
        return heap.getFirst();
    }

    /**
     * Inserts a key-value pair and return the entry created.
     *
     * @param key   the key of the new entry
     * @param value the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {

        checkKey(key);

        Entry<K, V> entry = new PQEntry<>(key, value);

        heap.add(entry);

        percolateUp(heap.size() - 1);

        return entry;
    }

    /**
     * Removes and returns an entry with minimal key.
     *
     * @return the removed entry (or null if empty)
     */
    @Override
    public Entry<K, V> removeMin() {

        if (heap.isEmpty()) return null;

        Entry<K, V> min = heap.getFirst();

        swap(0, heap.size() - 1);

        heap.removeLast();

        percolateDown(0);

        return min;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (Entry<K, V> entry : heap) {
            sb.append(entry.getKey()).append(" ");
        }

        return sb.toString();
    }

    @Override
    public HeapPriorityQueue<K, V> clone() {

        HeapPriorityQueue<K, V> other = new HeapPriorityQueue<>();

        other.heap.addAll(heap);

        return other;
    }

}
