
package assignments.datastructures;

import adt.Set;
import java.util.Iterator;
import java.util.Objects;

/**
 * A set that stores unique values using a Vector.
 *
 * <p>Duplicate values are not allowed. Values are stored
 * in insertion order. Adding, removing, and searching
 * take O(n) time. Getting the length takes O(1) time.</p>
 *
 * @param <T> the type of value stored
 */
public class VectorSet<T> implements Set<T> {

    // Store our values in a Vector.
    private final Vector<T> values;

    /**
     * Creates an empty set.
     */
    public VectorSet() {
        values = new Vector<>();
    }

    /**
     * Adds a value if it does not already exist.
     *
     * @param value the value to add
     * @return true if added, false if already present
     */
    @Override
    public boolean add(T value) {

        // Do not allow duplicates.
        if (contains(value)) {
            return false;
        }

        // Add the value to the end.
        values.insert(values.length(), value);
        return true;
    }

    /**
     * Removes a value from the set.
     *
     * @param value the value to remove
     * @return true if removed, false if not found
     */
    @Override
    public boolean remove(T value) {

        // Look through the Vector.
        for (int i = 0; i < values.length(); i++) {

            // Find the matching value.
            if (Objects.equals(values.at(i), value)) {
                values.delete(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether a value exists in the set.
     *
     * @param value the value to find
     * @return true if present, false otherwise
     */
    @Override
    public boolean contains(T value) {

        // Check each value.
        for (int i = 0; i < values.length(); i++) {
            if (Objects.equals(values.at(i), value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the number of unique values.
     *
     * @return the number of values in the set
     */
    @Override
    public int length() {
        return values.length();
    }

    /**
     * Iterates over values in insertion order.
     *
     * @return an iterator over the Vector
     */
    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    /**
     * Runs interface validation and additional tests.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {

        // Run the required interface validation.
        Set.validate(new VectorSet<Integer>());

        // Create a set.
        VectorSet<Integer> numbers = new VectorSet<>();

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

        // Test iteration.
        Iterator<Integer> it = numbers.iterator();
        assert it.hasNext() && it.next() == 10;
        assert it.hasNext() && it.next() == 5;
        assert it.hasNext() && it.next() == 20;
        assert !it.hasNext();

        // Test removing values.
        assert numbers.remove(5);
        assert !numbers.remove(5);
        assert !numbers.contains(5);
        assert numbers.length() == 2;

        // Test with Strings.
        VectorSet<String> words = new VectorSet<>();
        assert words.add("apple");
        assert words.add("banana");
        assert !words.add("apple");
        assert words.length() == 2;

        System.out.println("VectorSet passes additional tests.");
    }
}
