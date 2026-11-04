
package assignments.datastructures;

import adt.Set;
import java.util.Iterator;

/**
 * A set that stores unique values in a binary search tree.
 *
 * <p>Values are compared using their natural ordering. Adding
 * a value already in the set does nothing. Iterating visits
 * values in sorted order. Null values are not supported.</p>
 *
 * <p>Adding, removing, and searching take O(h) time, where h
 * is the tree's height. An unbalanced tree may have height O(n).</p>
 *
 * @param <T> the type of value, which must be comparable
 */
public class TreeSet<T extends Comparable<? super T>>
        implements Set<T> {

    // The binary search tree stores our values.
    private final BinarySearchTree<T> tree;

    /**
     * Creates an empty set.
     */
    public TreeSet() {
        tree = new BinarySearchTree<>();
    }

    /**
     * Adds a value if it is not already in the set.
     *
     * @param value the value to add
     * @return true if added, false if already present
     * @throws NullPointerException if value is null
     */
    @Override
    public boolean add(T value) {
        return tree.add(value);
    }

    /**
     * Removes a value from the set.
     *
     * @param value the value to remove
     * @return true if removed, false if not found
     * @throws NullPointerException if value is null
     */
    @Override
    public boolean remove(T value) {
        return tree.remove(value);
    }

    /**
     * Checks if the set contains a value.
     *
     * @param value the value to find
     * @return true if found, false otherwise
     * @throws NullPointerException if value is null
     */
    @Override
    public boolean contains(T value) {
        return tree.contains(value);
    }

    /**
     * Returns the number of values in the set.
     *
     * @return the number of unique values
     */
    @Override
    public int length() {
        return tree.length();
    }

    /**
     * Returns an iterator over the values in sorted order.
     *
     * @return an iterator over a snapshot of the values
     */
    @Override
    public Iterator<T> iterator() {
        return tree.iterator();
    }

    /**
     * Tests the TreeSet class using assertions.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Run the instructor's Set interface validation.
        Set.validate(new TreeSet<Integer>());

        // Create a set for additional tests.
        TreeSet<Integer> numbers = new TreeSet<>();

        // Test adding values.
        assert numbers.length() == 0;
        assert numbers.add(10);
        assert numbers.add(5);
        assert numbers.add(20);

        // Test duplicate values.
        assert !numbers.add(10);
        assert numbers.length() == 3;

        // Test searching.
        assert numbers.contains(5);
        assert !numbers.contains(99);

        // Test sorted iteration.
        Iterator<Integer> it = numbers.iterator();
        assert it.hasNext() && it.next() == 5;
        assert it.hasNext() && it.next() == 10;
        assert it.hasNext() && it.next() == 20;
        assert !it.hasNext();

        // Test removing values.
        assert numbers.remove(10);
        assert !numbers.remove(10);
        assert numbers.length() == 2;
        assert !numbers.contains(10);

        // Test with strings.
        TreeSet<String> words = new TreeSet<>();
        assert words.add("apple");
        assert words.add("banana");
        assert !words.add("apple");
        assert words.length() == 2;

        System.out.println("TreeSet passes additional tests.");
    }
}
