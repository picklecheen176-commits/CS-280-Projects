
package assignments.datastructures;

import adt.Set;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A HashSet that uses linked lists for buckets.
 * Each bucket can hold at most 5 items.
 *
 * @param <T> the type of item stored
 */
public class HashSet<T> implements Set<T> {

    private ArrayList<LinkedList<T>> buckets;
    private int size = 0;

    /** Creates an empty HashSet with 11 buckets. */
    public HashSet() {
        makeTable(11);
    }

    // Creates empty buckets.
    private void makeTable(int count) {
        buckets = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    // Finds the bucket for an item.
    private int index(T item) {
        return Math.floorMod(
            Objects.hashCode(item), buckets.size()
        );
    }

    /**
     * Adds an item if it is not already present.
     *
     * @param item the item to add
     * @return true if added, false if already present
     */
    @Override
    public boolean add(T item) {
        if (contains(item)) {
            return false;
        }

        LinkedList<T> bucket = buckets.get(index(item));
        bucket.add(item);
        size++;

        // Rehash if the bucket has 6 items.
        if (bucket.size() > 5) {
            rehash();
        }

        return true;
    }

    /**
     * Removes an item.
     *
     * @param item the item to remove
     * @return true if removed, false if not found
     */
    @Override
    public boolean remove(T item) {
        if (buckets.get(index(item)).remove(item)) {
            size--;
            return true;
        }

        return false;
    }

    /**
     * Checks whether an item is in the set.
     *
     * @param item the item to find
     * @return true if the item exists
     */
    @Override
    public boolean contains(T item) {
        return buckets.get(index(item)).contains(item);
    }

    /**
     * Returns the number of items in the set.
     *
     * @return the number of items
     */
    @Override
    public int length() {
        return size;
    }

    /**
     * Returns an iterator over the stored items.
     *
     * @return an iterator over a copy of the items
     */
    @Override
    public Iterator<T> iterator() {
        ArrayList<T> items = new ArrayList<>();

        for (LinkedList<T> bucket : buckets) {
            items.addAll(bucket);
        }

        return items.iterator();
    }

    // Makes a larger table and moves all items.
    private void rehash() {
        ArrayList<T> items = new ArrayList<>();

        // Save all the old items.
        for (LinkedList<T> bucket : buckets) {
            items.addAll(bucket);
        }

        int newSize = buckets.size() * 2 + 1;

        while (true) {
            makeTable(newSize);
            boolean tooFull = false;

            // Put the items into the new buckets.
            for (T item : items) {
                LinkedList<T> bucket = buckets.get(index(item));
                bucket.add(item);

                if (bucket.size() > 5) {
                    tooFull = true;
                }
            }

            // Stop when every bucket has at most 5 items.
            if (!tooFull) {
                return;
            }

            newSize = newSize * 2 + 1;
        }
    }

    /**
     * Tests the HashSet, including rehashing.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Set.validate(new HashSet<Integer>());

        HashSet<Integer> set = new HashSet<>();

        // These 6 numbers start in the same bucket.
        for (int i = 0; i < 6; i++) {
            set.add(i * 11);
        }

        // Make sure the table grew.
        assert set.buckets.size() > 11;

        // Make sure all 6 numbers are still there.
        for (int i = 0; i < 6; i++) {
            assert set.contains(i * 11);
        }

        assert set.length() == 6;

        System.out.println("HashSet passes rehash tests.");
    }
}
