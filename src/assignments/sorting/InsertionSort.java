package assignments.sorting;

/**
 * Sorts an array using the insertion sort algorithm.
 */
public class InsertionSort extends SortingAlgorithm {

    /**
     * Constructs an InsertionSort sorting algorithm.
     */
    public InsertionSort() {}

    /**
     * Sort an array in-place using insertion sort.
     *
     * Post-condition: `array` is sorted in ascending order.
     *
     * @param array an array of integers
     */
    @Override
    public void sort(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            Integer value = array[i];
            int j = i - 1;

            while (j >= 0 && array[j].compareTo(value) > 0) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = value;
        }
    }

    /**
     * Swap two elements within an array.
     *
     * @param array the array to swap values in
     * @param i the first index to swap
     * @param j the second index to swap
     */
    private void swap(Integer[] array, int i, int j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Run validation tests.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SortingAlgorithm.validate(new InsertionSort());
        System.out.println("InsertionSort has passed all tests.");
    }
}