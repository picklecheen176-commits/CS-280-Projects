
package assignments.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * Sorts an array using bucket sort.
 *
 * @param <T> the type of values being sorted
 */
public class BucketSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    /** Creates a BucketSort. */
    public BucketSort() {}

    /** Sorts the array from smallest to largest. */
    @Override
    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int count = (int) Math.sqrt(array.length);

        // Choose bucket boundaries.
        List<T> boundaries = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            boundaries.add(array[i * array.length / count]);
        }
        boundaries.sort(null);

        // Create buckets.
        List<List<T>> buckets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            buckets.add(new ArrayList<>());
        }

        // Put values into buckets.
        for (T value : array) {
            int index = 0;

            while (index < boundaries.size()
                    && value.compareTo(boundaries.get(index)) >= 0) {
                index++;
            }

            buckets.get(index).add(value);
        }

        // Sort buckets and copy values back.
        int index = 0;
        for (List<T> bucket : buckets) {
            bucket.sort(null);

            for (T value : bucket) {
                array[index++] = value;
            }
        }
    }

    /** Tests the sorting algorithm. */
    public static void main(String[] args) {
        SortingAlgorithm.validate(new BucketSort<Integer>());
        System.out.println("BucketSort passes all tests.");
    }
}
