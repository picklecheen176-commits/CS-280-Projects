
package assignments.sorting;

import assignments.datastructures.BinarySearchTree;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Sorts comparable values using a binary search tree.
 *
 * <p>Values are inserted into a binary search tree, then
 * copied back in ascending order through an inorder traversal.
 * Original indices distinguish duplicate values.</p>
 *
 * <p>Average time: O(n log n).
 * Worst-case time: O(n^2) for an unbalanced tree.
 * Extra space: O(n).</p>
 *
 * @param <T> the comparable element type
 */
public class TreeSort<T extends Comparable<T>>
        extends SortingAlgorithm<T> {

    /** Constructs a TreeSort algorithm. */
    public TreeSort() {}

    /**
     * Sorts an array in ascending order.
     *
     * @param array the array to sort in place
     */
    @Override
    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        BinarySearchTree<IndexedValue<T>> tree =
                new BinarySearchTree<>();

        // Insert each value with its original index.
        for (int i = 0; i < array.length; i++) {
            tree.add(new IndexedValue<>(array[i], i));
        }

        // Inorder traversal produces ascending values.
        Iterator<IndexedValue<T>> sorted = tree.inorder();

        for (int i = 0; i < array.length; i++) {
            array[i] = sorted.next().value;
        }
    }

    /**
     * Stores a value and its original position.
     * This allows the tree to preserve duplicates.
     *
     * @param <E> the element type
     */
    private static final class IndexedValue<E extends Comparable<E>>
            implements Comparable<IndexedValue<E>> {

        private final E value;
        private final int originalIndex;

        private IndexedValue(E value, int originalIndex) {
            this.value = value;
            this.originalIndex = originalIndex;
        }

        @Override
        public int compareTo(IndexedValue<E> other) {
            int comparison = value.compareTo(other.value);

            if (comparison != 0) {
                return comparison;
            }

            return Integer.compare(
                originalIndex, other.originalIndex
            );
        }
    }

    /**
     * Runs sorting validation and additional tests.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        SortingAlgorithm.validate(new TreeSort<Integer>());

        check(new Integer[] {});
        check(new Integer[] {42});
        check(new Integer[] {5, 4, 3, 2, 1});
        check(new Integer[] {-9, -1, 0, 8, -9, 8, 3});
        check(new Integer[] {7, 7, 7, 7, 7});
        check(new Integer[] {
            Integer.MIN_VALUE, 0, Integer.MAX_VALUE
        });
        check(new String[] {
            "pear", "apple", "banana", "apple", "kiwi"
        });

        Integer[] many = new Integer[1000];
        for (int i = 0; i < many.length; i++) {
            many[i] = (i * 137) % 997 - 500;
        }
        check(many);

        System.out.println("TreeSort passes all tests.");
    }

    /** Compares sorting results with Java's built-in sort. */
    private static <E extends Comparable<E>> void check(E[] input) {
        TreeSort<E> algorithm = new TreeSort<>();

        E[] expected = Arrays.copyOf(input, input.length);
        Arrays.sort(expected);

        E[] inPlace = Arrays.copyOf(input, input.length);
        algorithm.sort(inPlace);

        assert Arrays.equals(inPlace, expected)
            : "sort() failed";

        E[] original = Arrays.copyOf(input, input.length);
        E[] outOfPlace = algorithm.sorted(input);

        assert Arrays.equals(outOfPlace, expected)
            : "sorted() failed";

        assert Arrays.equals(input, original)
            : "sorted() changed the input";
    }
}
