
package assignments.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sorts comparable values by distributing them into ordered buckets.
 *
 * <p>Sample values are used as bucket boundaries so this implementation
 * works with any Comparable type, not just numbers. Each bucket is sorted
 * with insertion sort, then the buckets are combined in ascending order.</p>
 *
 * @param <T> the comparable type being sorted
 */
public class BucketSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    /** Constructs a bucket-sort algorithm. */
    public BucketSort() {}

    /**
     * Sorts the given array in ascending order, modifying the same array.
     *
     * @param array the array to sort
     */
    @Override
    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int bucketCount = Math.max(1, (int) Math.sqrt(array.length));

        // Take sample values and sort them to define ordered bucket ranges.
        List<T> boundaries = new ArrayList<>();
        for (int i = 1; i < bucketCount; i++) {
            int sampleIndex = (int) ((long) i * array.length / bucketCount);
            boundaries.add(array[sampleIndex]);
        }
        boundaries.sort(null);

        // Create empty buckets.
        List<List<T>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Place each value in its corresponding bucket.
        for (T value : array) {
            int bucketIndex = findBucket(value, boundaries);
            buckets.get(bucketIndex).add(value);
        }

        // Sort each bucket, then copy its elements back into the input array.
        int outputIndex = 0;
        for (List<T> bucket : buckets) {
            insertionSort(bucket);
            for (T value : bucket) {
                array[outputIndex++] = value;
            }
        }
    }

    /** Returns the index of the bucket containing the given value. */
    private int findBucket(T value, List<T> boundaries) {
        int low = 0;
        int high = boundaries.size();

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (value.compareTo(boundaries.get(mid)) < 0) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /** Sorts a single bucket using insertion sort. */
    private void insertionSort(List<T> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            T value = bucket.get(i);
            int j = i - 1;
            while (j >= 0 && bucket.get(j).compareTo(value) > 0) {
                bucket.set(j + 1, bucket.get(j));
                j--;
            }
            bucket.set(j + 1, value);
        }
    }

    /**
     * Runs the course's standard validation and additional edge-case tests.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        SortingAlgorithm.validate(new BucketSort<Integer>());

        check(new Integer[] {});
        check(new Integer[] {42});
        check(new Integer[] {5, 4, 3, 2, 1});
        check(new Integer[] {-9, -1, 0, 8, -9, 8, 3});
        check(new Integer[] {7, 7, 7, 7, 7});
        check(new Integer[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE, -1});
        check(new String[] {"pear", "apple", "banana", "apple", "kiwi"});

        Integer[] many = new Integer[1000];
        for (int i = 0; i < many.length; i++) {
            many[i] = (i * 137) % 997 - 500;
        }
        check(many);

        System.out.println("BucketSort passes all tests.");
    }

    /** Checks in-place and copy-based sorting against Java's built-in sort. */
    private static <E extends Comparable<E>> void check(E[] input) {
        BucketSort<E> algorithm = new BucketSort<>();
        E[] expected = Arrays.copyOf(input, input.length);
        Arrays.sort(expected);

        E[] inPlace = Arrays.copyOf(input, input.length);
        algorithm.sort(inPlace);
        assert Arrays.equals(inPlace, expected) : "sort() failed";

        E[] original = Arrays.copyOf(input, input.length);
        E[] outOfPlace = algorithm.sorted(input);
        assert Arrays.equals(outOfPlace, expected) : "sorted() failed";
        assert Arrays.equals(input, original)
            : "sorted() changed the original input";
    }
}
