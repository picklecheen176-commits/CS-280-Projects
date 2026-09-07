package assignments.sorting;

/**
 * Sorts an array using the selection sort algorithm.
 */
public class SelectionSort extends SortingAlgorithm {

    /**
     * Constructs a SelectionSort sorting algorithm.
     */
    public SelectionSort() {}

    /**
     * Sort an array in-place using selection sort.
     *
     * Post-condition: `array` is sorted in ascending order.
     *
     * @param array an array of integers
     */
    @Override
    public void sort(Integer[] array) {
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
        SortingAlgorithm.validate(new SelectionSort());
        System.out.println("SelectionSort has passed all tests.");
    }
}