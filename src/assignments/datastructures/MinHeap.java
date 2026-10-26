
package assignments.datastructures;

import adt.PriorityQueue;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

/**
 * A minimum-priority queue implemented as a binary min-heap stored in a
 * {@link Vector}. The smallest element is always stored at index zero.
 *
 * <p>For a node at index {@code i}, its parent is at
 * {@code (i - 1) / 2}, and its children are at
 * {@code 2 * i + 1} and {@code 2 * i + 2}. Every parent is less
 * than or equal to its children according to the elements'
 * {@link Comparable#compareTo(Object) natural ordering}.</p>
 *
 * <p>Enqueue and dequeue take O(log N) amortized time because only one
 * root-to-leaf path is traversed; peek and isEmpty take O(1) time.
 * The Vector must support O(1) indexed access and replacement and
 * amortized O(1) insertion/deletion at its end. Duplicate values are
 * allowed. Null values are not allowed.</p>
 *
 * @param <T> the comparable element type
 */
public class MinHeap<T extends Comparable<T>> implements PriorityQueue<T> {

    /** Internal array representation of the complete binary tree. */
    private final Vector<T> heap;

    /**
     * Constructs an empty minimum-priority queue.
     */
    public MinHeap() {
        heap = new Vector<>();
    }

    /**
     * Reports whether this priority queue contains no elements.
     *
     * @return true when empty, otherwise false
     */
    @Override
    public boolean isEmpty() {
        return heap.length() == 0;
    }

    /**
     * Inserts a new value and restores the min-heap property by
     * moving it upward until its parent is no greater than it.
     * Duplicates are permitted.
     *
     * <p>Amortized time complexity: O(log N).</p>
     *
     * @param value the non-null element to add
     * @throws NullPointerException if value is null
     */
    @Override
    public void enqueue(T value) {
        Objects.requireNonNull(value, "value");

        int index = heap.length();
        heap.insert(index, value);

        // Bubble the new value upward.
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.at(parent).compareTo(heap.at(index)) <= 0) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    /**
     * Removes and returns the minimum value at the root.
     * The last element moves into the root and is moved downward
     * until the min-heap property is restored.
     *
     * <p>Amortized time complexity: O(log N).</p>
     *
     * @return the smallest element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                "Cannot dequeue from an empty MinHeap"
            );
        }

        T minimum = heap.at(0);
        int lastIndex = heap.length() - 1;

        // Remove the last element.
        T lastValue = heap.delete(lastIndex);

        if (!isEmpty()) {
            heap.set(0, lastValue);
            siftDown(0);
        }

        return minimum;
    }

    /**
     * Returns the smallest element without removing it.
     *
     * <p>Time complexity: O(1).</p>
     *
     * @return the smallest element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                "Cannot peek into an empty MinHeap"
            );
        }

        return heap.at(0);
    }

    /**
     * Restores heap ordering by moving a value toward
     * its smaller child.
     *
     * @param index the index to begin moving downward
     */
    private void siftDown(int index) {
        int size = heap.length();

        while (true) {
            int left = 2 * index + 1;
            int right = left + 1;

            if (left >= size) {
                return;
            }

            // Find the smaller child.
            int smallerChild = left;

            if (right < size
                    && heap.at(right).compareTo(heap.at(left)) < 0) {
                smallerChild = right;
            }

            // Stop if heap ordering is correct.
            if (heap.at(index).compareTo(heap.at(smallerChild)) <= 0) {
                return;
            }

            swap(index, smallerChild);
            index = smallerChild;
        }
    }

    /**
     * Exchanges two elements in the Vector.
     *
     * @param first index of the first element
     * @param second index of the second element
     */
    private void swap(int first, int second) {
        T temporary = heap.at(first);

        heap.set(first, heap.at(second));
        heap.set(second, temporary);
    }

    /**
     * Runs the course PriorityQueue interface validation and tests
     * empty queues, insertion, removal, ordering, duplicate values,
     * negative numbers, and a large sequence of operations.
     *
     * <p>Run with the -ea flag to enable assertions.</p>
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {

        // Validate the PriorityQueue interface.
        PriorityQueue.validate(new MinHeap<Integer>());

        MinHeap<Integer> queue = new MinHeap<>();

        // Test empty queue.
        assert queue.isEmpty();

        // Test enqueue.
        queue.enqueue(10);
        queue.enqueue(4);
        queue.enqueue(4);
        queue.enqueue(-2);
        queue.enqueue(7);

        assert !queue.isEmpty();

        // Test peek.
        assert queue.peek().equals(-2);

        // Test dequeue and correct priority order.
        assert queue.dequeue().equals(-2);
        assert queue.dequeue().equals(4);
        assert queue.dequeue().equals(4);
        assert queue.dequeue().equals(7);
        assert queue.dequeue().equals(10);

        assert queue.isEmpty();

        // Test single-element queue.
        queue.enqueue(12);

        assert queue.peek().equals(12);
        assert queue.dequeue().equals(12);
        assert queue.isEmpty();

        // Test inserting after becoming empty.
        queue.enqueue(1);
        assert queue.dequeue().equals(1);

        // Test dequeue on empty heap.
        boolean caughtEmptyDequeue = false;

        try {
            queue.dequeue();
        } catch (NoSuchElementException expected) {
            caughtEmptyDequeue = true;
        }

        assert caughtEmptyDequeue;

        // Test peek on empty heap.
        boolean caughtEmptyPeek = false;

        try {
            queue.peek();
        } catch (NoSuchElementException expected) {
            caughtEmptyPeek = true;
        }

        assert caughtEmptyPeek;

        // Test inserting null.
        boolean caughtNull = false;

        try {
            queue.enqueue(null);
        } catch (NullPointerException expected) {
            caughtNull = true;
        }

        assert caughtNull;

        // Compare with Java's built-in PriorityQueue for testing.
        // The MinHeap itself still uses Vector for storage.
        java.util.PriorityQueue<Integer> reference =
            new java.util.PriorityQueue<>();

        Random random = new Random(2026);

        for (int i = 0; i < 10000; i++) {

            if (reference.isEmpty() || random.nextBoolean()) {

                int value = random.nextInt(1001) - 500;

                queue.enqueue(value);
                reference.add(value);

            } else {

                assert queue.peek().equals(reference.peek());
                assert queue.dequeue().equals(reference.remove());
            }

            assert queue.isEmpty() == reference.isEmpty();

            if (!reference.isEmpty()) {
                assert queue.peek().equals(reference.peek());
            }
        }

        // Remove all remaining elements.
        while (!reference.isEmpty()) {
            assert queue.dequeue().equals(reference.remove());
        }

        assert queue.isEmpty();

        // Test generic support using Strings.
        MinHeap<String> words = new MinHeap<>();

        words.enqueue("pear");
        words.enqueue("apple");
        words.enqueue("banana");

        assert words.dequeue().equals("apple");
        assert words.dequeue().equals("banana");
        assert words.dequeue().equals("pear");

        assert words.isEmpty();

        System.out.println("MinHeap has passed all tests.");
    }
}
