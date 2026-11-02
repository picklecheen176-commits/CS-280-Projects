
package assignments.sorting;

/**
 * Sorts Integer values using least-significant-digit (LSD) radix sort.
 *
 * <p>Processes four bytes per integer, starting with the least significant.
 * Each pass uses stable counting sort with 256 possible byte values.
 * Flipping the sign bit ensures negative integers sort before positive ones.</p>
 *
 * <p>Time: O(N) for fixed-width Java integers. Extra space: O(N).
 * This algorithm is stable and does not use comparisons or recursion.</p>
 */
public class RadixSort extends SortingAlgorithm<Integer> {

    /** Creates a radix sort algorithm for integers. */
    public RadixSort() {}

    /**
     * Sorts the array in ascending numerical order.
     * Does nothing for a null array or an array with fewer than two elements.
     *
     * @param array the array of non-null Integer values to sort
     * @throws NullPointerException if an array element is null
     */
    @Override
    public void sort(Integer[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        Integer[] buffer = new Integer[array.length];

        // An int has four bytes: sort one byte per pass.
        for (int shift = 0; shift < 32; shift += 8) {
            int[] count = new int[257];

            // Count how many values have each byte.
            for (int value : array) {
                int digit = ((value ^ Integer.MIN_VALUE) >>> shift) & 255;
                count[digit + 1]++;
            }

            // Find the starting position for each byte.
            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }

            // Place values into the buffer in a stable order.
            for (int value : array) {
                int digit = ((value ^ Integer.MIN_VALUE) >>> shift) & 255;
                buffer[count[digit]++] = value;
            }

            System.arraycopy(buffer, 0, array, 0, array.length);
        }
    }
}
