
package assignments.sorting;

/**
 * Sorts an array using Shell sort.
 * Works like insertion sort, but starts by comparing elements farther apart.
 * Sorts in place using O(1) extra space.
 *
 * @param <T> the type of values to sort
 */
public class ShellSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    /** Creates a ShellSort object. */
    public ShellSort() {}

    /**
     * Sorts the given array from smallest to largest.
     * Does nothing if the array is null or has fewer than two elements.
     * Elements in a non-null array must not be null.
     *
     * @param array the array to sort
     */
    @Override
    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                T temp = array[i];
                int j = i;

                while (j >= gap && array[j - gap].compareTo(temp) > 0) {
                    array[j] = array[j - gap];
                    j -= gap;
                }

                array[j] = temp;
            }
        }
    }
}
