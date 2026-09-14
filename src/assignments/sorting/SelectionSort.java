package assignments.sorting;

/**
 * Sorts an array using the selection sort algorithm.
 */
public class SelectionSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    /**
     * Constructs a SelectionSort sorting algorithm.
     */
    public SelectionSort() {}

    /**
     * Sort an array in-place using selection sort.
     *
     * Post-condition: `array` is sorted in ascending order.
     *
     * @param array an array of comparable elements
     */
    @Override
    public void sort(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            swap(array, i, minIndex);
        }
    }

    /**
     * Swap two elements within an array.
     *
     * @param array the array to swap values in
     * @param i the first index to swap
     * @param j the second index to swap
     */
    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Run validation tests.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SortingAlgorithm.validate(new SelectionSort<Integer>());
        System.out.println("SelectionSort has passed all tests.");
    }
}