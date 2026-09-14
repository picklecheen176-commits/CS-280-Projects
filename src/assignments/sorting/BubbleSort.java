package assignments.sorting;

/**
 * Swap adjacent elements over and over until the whole array is sorted.
 */
public class BubbleSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    /**
     * Constructs a BubbleSort sorting algorithm.
     */
    public BubbleSort() {}

    /**
     * Sort an array in-place using bubble sort.
     *
     * Post-condition: `array` is sorted in ascending order.
     *
     * @param array an array of comparable elements
     */
    @Override
    public void sort(T[] array) {
        for (int k = array.length; k >= 2; k--) {
            for (int i = 0; i < k - 1; i++) {
                if (array[i].compareTo(array[i + 1]) > 0) {
                    swap(array, i, i + 1);
                }
            }
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
        SortingAlgorithm.validate(new BubbleSort<Integer>());
        System.out.println("BubbleSort has passed all tests.");

        int N = 10;
        Integer[] array = new Integer[N];

        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(N * Math.random());
        }

        SortingAlgorithm<Integer> sorter = new BubbleSort<Integer>();
        long start = System.nanoTime();
        sorter.sort(array);
        long end = System.nanoTime();
        double duration = (end - start) / (1e9);

        System.out.println("Array size: " + N);
        System.out.println("Total duration: " + duration);
    }
}