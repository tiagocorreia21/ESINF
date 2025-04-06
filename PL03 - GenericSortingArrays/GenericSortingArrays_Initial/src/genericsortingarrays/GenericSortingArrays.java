package genericsortingarrays;

public class GenericSortingArrays {

    /**
     * Swaps two vector positions                  O(1)
     */
    public static <E> void swap(E[] v, int i, int j) {

        E temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }

    //  printArray
    public static <E> void printArray(E[] v) {
        for (E element : v) {
            System.out.println(", " + element);
        }
    }

    /**
     * Selection Sort Algorithm
     */
    public static <E extends Comparable<E>> void selectionSort(E[] v) {

        for (int i = 0; i < v.length - 1; i++) {

            int min = i;

            for (int j = i + 1; j < v.length; j++) {

                if (v[j].compareTo(v[min]) < 0) {
                    min = j;
                }
            }

            if (min != i) {
                swap(v, i, min);
            }
        }
    }

    /**
     * Bubble Sort Algorithm
     *
     * @param v
     */
    public static <E extends Comparable<E>> void bubbleSort(E[] v) {

        for (int i = 0; i < v.length; i++) {

            for (int j = 0; j < v.length - 1; j++) {

                if (v[j].compareTo(v[j + 1]) > 0) {
                    swap(v, j, j + 1);
                }
            }
        }
    }

    /**
     * insertionSort Algorithm
     */
    public static <E extends Comparable<E>> void insertionSort(E[] v) {

        for (int i = 1; i < v.length; i++) {

            E temp = v[i];
            int j = i - 1;

            while (j >= 0 && temp.compareTo(v[j]) < 0) {
                v[j + 1] = v[j];
                j--;
            }

            v[j + 1] = temp;
        }
    }

    /**
     * Mergesort Algorithm
     */
    private static <E extends Comparable<E>> void merge(E[] S1, E[] S2, E[] S) {

        int i = 0, j = 0;

        while (i + j < S.length) {

            if (j == S2.length || (i < S1.length && S1[i].compareTo(S2[j]) < 0)) {
                S[i + j] = S1[i++];
            } else {
                S[i + j] = S2[j++];
            }
        }
    }

    public static <E extends Comparable<E>> void mergeSort(E[] S) {

        int n = S.length;

        if (n < 2) {
            return;
        }

        int mid = n / 2;

        E[] S1 = (E[]) new Comparable[mid];
        E[] S2 = (E[]) new Comparable[n - mid];

        for (int i = 0; i < mid; i++) {
            S1[i] = S[i];
        }

        for (int i = mid; i < n; i++) {
            S2[i - mid] = S[i];
        }

        mergeSort(S1);
        mergeSort(S2);
        merge(S1, S2, S);
    }

    /**
     * Quicksort Algorithm
     */
    public static <E extends Comparable<E>> void quickSort(E v[]) {

        quickSort(v, 0, v.length - 1);
    }

    private static <E extends Comparable<E>> void quickSort(E v[], int left, int right) {

        if (left >= right) {
            return;
        }

        E pivot = v[(left + right) / 2];
        int i = left;
        int j = right;

        while (i <= j) {

            while (v[i].compareTo(pivot) < 0) {
                i++;
            }

            while (v[j].compareTo(pivot) > 0) {
                j--;
            }

            if (i <= j) {
                swap(v, i, j);
                i++;
                j--;
            }
        }

        quickSort(v, left, j);
        quickSort(v, i, right);
    }
}
