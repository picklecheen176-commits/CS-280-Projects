
package assignments.datastructures;

import adt.PriorityQueue;
import adt.Tree;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

/**
 * A complete binary min-heap stored in the course's {@link Vector} class.
 * Every parent is less than or equal to its children according to natural
 * ordering. Duplicate values are allowed, but {@code null} values are not.
 *
 * <p>Priority-queue operations {@code enqueue} and
 * {@link #dequeue()} take O(log N) amortized time; {@link #peek()},
 * {@link #isEmpty()}, {@link #length()}, {@link #degree()}, and
 * {@link #height()} take O(1) time. Each traversal takes O(N) time and
 * returns an iterator over a snapshot of the tree. The heap's shape is
 * a complete binary tree, but unlike a binary search tree its inorder
 * traversal is <em>not</em> necessarily sorted.</p>
 *
 * <p>All operations and traversals are iterative; no recursion is used.</p>
 *
 * @param <T> the type of elements, ordered by {@code compareTo}
 */
public class MinHeap<T extends Comparable<T>>
        implements PriorityQueue<T>, Tree<T> {

    /** Level-order storage: parent i has children 2i+1 and 2i+2. */
    private final Vector<T> heap;

    /** Creates an empty min-heap. */
    public MinHeap() {
        heap = new Vector<>();
    }

    /**
     * Checks whether this heap has no elements in O(1) time.
     *
     * @return {@code true} when empty; otherwise {@code false}
     */
    @Override
    public boolean isEmpty() {
        return heap.length() == 0;
    }

    /**
     * Inserts a value, then moves it upward until heap order is restored.
     * Runs in O(log N) amortized time with a dynamically resized Vector.
     *
     * @param value the non-null value to insert; duplicates are permitted
     * @throws NullPointerException if {@code value} is null
     */
    @Override
    public void enqueue(T value) {
        Objects.requireNonNull(value, "value");
        int index = heap.length();
        heap.insert(index, value); // Append, so no elements are shifted.

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
     * Removes and returns the smallest value. Replaces the root with the
     * final Vector element and iteratively moves it downward as needed.
     * Runs in O(log N) amortized time.
     *
     * @return the minimum value
     * @throws NoSuchElementException if the heap is empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty heap");
        }

        T minimum = heap.at(0);
        T last = heap.delete(heap.length() - 1); // O(1) at the end.

        if (!isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }

        return minimum;
    }

    /**
     * Retrieves the smallest value without removing it, in O(1) time.
     *
     * @return the minimum value
     * @throws NoSuchElementException if the heap is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into an empty heap");
        }
        return heap.at(0);
    }

    /**
     * Counts the elements in the tree in O(1) time.
     *
     * @return the number of elements, including duplicates
     */
    @Override
    public int length() {
        return heap.length();
    }

    /**
     * Returns the maximum number of children of any node in O(1) time.
     * A complete binary tree has degree 0 for at most one node, degree 1
     * for exactly two nodes, and degree 2 for at least three nodes.
     *
     * @return 0, 1, or 2, depending on the number of elements
     */
    @Override
    public int degree() {
        int count = length();
        return count >= 3 ? 2 : count == 2 ? 1 : 0;
    }

    /**
     * Computes height in number of node levels in O(1) time.
     * An empty heap has height 0; a heap with one node has height 1.
     *
     * @return the number of levels in the complete binary tree
     */
    @Override
    public int height() {
        int count = length();
        return count == 0 ? 0
                : Integer.SIZE - Integer.numberOfLeadingZeros(count);
    }

    /**
     * Visits root, left subtree, then right subtree using an explicit stack.
     * Runs in O(N) time and returns a snapshot iterator.
     *
     * @return an iterator in preorder (root-left-right)
     */
    @Override
    public Iterator<T> preorder() {
        List<T> values = new ArrayList<>(length());

        if (isEmpty()) {
            return values.iterator();
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(0);

        while (!stack.isEmpty()) {
            int index = stack.pop();
            values.add(heap.at(index));

            int right = rightChild(index);
            int left = leftChild(index);

            if (right != -1) {
                stack.push(right);
            }

            if (left != -1) {
                stack.push(left);
            }
        }

        return values.iterator();
    }

    /**
     * Visits left subtree, root, then right subtree using an explicit stack.
     * Does not necessarily produce sorted values in a min-heap.
     * Runs in O(N) time and returns a snapshot iterator.
     *
     * @return an iterator in inorder (left-root-right)
     */
    @Override
    public Iterator<T> inorder() {
        List<T> values = new ArrayList<>(length());
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int index = isEmpty() ? -1 : 0;

        while (index != -1 || !stack.isEmpty()) {
            while (index != -1) {
                stack.push(index);
                index = leftChild(index);
            }

            index = stack.pop();
            values.add(heap.at(index));
            index = rightChild(index);
        }

        return values.iterator();
    }

    /**
     * Visits left subtree, right subtree, then root using an explicit stack
     * and the last completed subtree's index. No recursion is used.
     * Runs in O(N) time and returns a snapshot iterator.
     *
     * @return an iterator in postorder (left-right-root)
     */
    @Override
    public Iterator<T> postorder() {
        List<T> values = new ArrayList<>(length());
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        int index = isEmpty() ? -1 : 0;
        int lastVisited = -1;

        while (index != -1 || !stack.isEmpty()) {
            if (index != -1) {
                stack.push(index);
                index = leftChild(index);
            } else {
                int parent = stack.peek();
                int right = rightChild(parent);

                if (right != -1 && lastVisited != right) {
                    index = right;
                } else {
                    values.add(heap.at(parent));
                    lastVisited = stack.pop();
                }
            }
        }

        return values.iterator();
    }

    /**
     * Visits nodes level by level, left to right. This is the same order as
     * the underlying Vector and needs no extra traversal stack or queue.
     * Runs in O(N) time and returns a snapshot iterator.
     *
     * @return an iterator in level order (breadth-first order)
     */
    @Override
    public Iterator<T> levelorder() {
        List<T> values = new ArrayList<>(length());

        for (int i = 0; i < length(); i++) {
            values.add(heap.at(i));
        }

        return values.iterator();
    }

    /**
     * Returns a level-order snapshot iterator as the default tree iteration.
     * Unlike a search tree, a heap has no inherently sorted traversal order.
     *
     * @return an iterator over the elements in level order
     */
    @Override
    public Iterator<T> iterator() {
        return levelorder();
    }

    /** Returns a valid left-child index, or -1 when there is none. */
    private int leftChild(int index) {
        long child = 2L * index + 1;
        return child < length() ? (int) child : -1;
    }

    /** Returns a valid right-child index, or -1 when there is none. */
    private int rightChild(int index) {
        long child = 2L * index + 2;
        return child < length() ? (int) child : -1;
    }

    /** Moves the value at the given index downward until ordered. */
    private void siftDown(int index) {
        while (true) {
            int left = leftChild(index);

            if (left == -1) {
                return;
            }

            int smallerChild = left;
            int right = rightChild(index);

            if (right != -1
                    && heap.at(right).compareTo(heap.at(left)) < 0) {
                smallerChild = right;
            }

            if (heap.at(index).compareTo(heap.at(smallerChild)) <= 0) {
                return;
            }

            swap(index, smallerChild);
            index = smallerChild;
        }
    }

    /** Exchanges two Vector entries in constant time. */
    private void swap(int first, int second) {
        T value = heap.at(first);
        heap.set(first, heap.at(second));
        heap.set(second, value);
    }

    /** Checks the heap invariant directly for tests, without recursion. */
    private void verifyHeapProperty() {
        for (int i = 1; i < length(); i++) {
            check(
                heap.at((i - 1) / 2).compareTo(heap.at(i)) <= 0,
                "Parent is greater than child at index " + i
            );
        }
    }

    /** Fails a test with a useful message, even when assertions are disabled. */
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    /** Compares a traversal against its expected sequence of elements. */
    private static <E> void expect(Iterator<E> actual, List<E> expected) {
        for (E value : expected) {
            check(actual.hasNext(), "Traversal stopped early");

            check(
                Objects.equals(actual.next(), value),
                "Unexpected traversal value"
            );
        }

        check(!actual.hasNext(), "Traversal contained extra values");
    }

    /**
     * Validates both course interfaces and tests the heap invariant,
     * every traversal, height/degree edge cases, duplicates, empty-queue
     * exceptions, and many random operations against a reference queue.
     * Run using {@code -ea} for the course's assertion-based validators.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {

        // Validate both course interfaces.
        PriorityQueue.validate(new MinHeap<Integer>());
        Tree.validate(new MinHeap<Integer>());

        MinHeap<Integer> heap = new MinHeap<>();

        // Test empty heap.
        check(heap.isEmpty(), "New heap should be empty");

        check(
            heap.length() == 0
                && heap.height() == 0
                && heap.degree() == 0,
            "Incorrect empty-tree shape"
        );

        expect(heap.preorder(), List.of());
        expect(heap.inorder(), List.of());
        expect(heap.postorder(), List.of());
        expect(heap.levelorder(), List.of());
        expect(heap.iterator(), List.of());

        // Insert elements for predictable tree traversal.
        for (int value : new int[] {1, 3, 2, 7, 9, 5, 4}) {
            heap.enqueue(value);
        }

        check(heap.peek() == 1, "Incorrect minimum");

        check(
            heap.length() == 7
                && heap.height() == 3
                && heap.degree() == 2,
            "Incorrect tree shape"
        );

        heap.verifyHeapProperty();

        // Test every tree traversal.
        expect(
            heap.preorder(),
            List.of(1, 3, 7, 9, 2, 5, 4)
        );

        expect(
            heap.inorder(),
            List.of(7, 3, 9, 1, 5, 2, 4)
        );

        expect(
            heap.postorder(),
            List.of(7, 9, 3, 5, 4, 2, 1)
        );

        expect(
            heap.levelorder(),
            List.of(1, 3, 2, 7, 9, 5, 4)
        );

        expect(
            heap.iterator(),
            List.of(1, 3, 2, 7, 9, 5, 4)
        );

        // Verify snapshot behavior.
        Iterator<Integer> snapshot = heap.levelorder();

        heap.enqueue(-1);

        expect(
            snapshot,
            List.of(1, 3, 2, 7, 9, 5, 4)
        );

        check(heap.dequeue() == -1, "Failed to remove a new minimum");
        heap.verifyHeapProperty();

        // Check shape for small heaps.
        MinHeap<Integer> small = new MinHeap<>();

        small.enqueue(8);

        check(
            small.degree() == 0 && small.height() == 1,
            "Single-node shape"
        );

        small.enqueue(4);

        check(
            small.degree() == 1 && small.height() == 2,
            "Two-node shape"
        );

        check(
            small.dequeue() == 4 && small.dequeue() == 8,
            "Two-node ordering"
        );

        check(small.isEmpty(), "Heap should become empty");

        // Test empty dequeue exception.
        boolean emptyDequeueThrows = false;

        try {
            small.dequeue();
        } catch (NoSuchElementException expected) {
            emptyDequeueThrows = true;
        }

        check(emptyDequeueThrows, "Empty dequeue must throw");

        // Test empty peek exception.
        boolean emptyPeekThrows = false;

        try {
            small.peek();
        } catch (NoSuchElementException expected) {
            emptyPeekThrows = true;
        }

        check(emptyPeekThrows, "Empty peek must throw");

        // Test null insertion exception.
        boolean nullThrows = false;

        try {
            small.enqueue(null);
        } catch (NullPointerException expected) {
            nullThrows = true;
        }

        check(nullThrows, "Enqueueing null must throw");

        // Compare with Java's PriorityQueue for correctness testing.
        // The actual MinHeap remains backed by Vector.
        MinHeap<Integer> tested = new MinHeap<>();

        java.util.PriorityQueue<Integer> reference =
            new java.util.PriorityQueue<>();

        Random random = new Random(2026);

        for (int i = 0; i < 20_000; i++) {

            if (reference.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt(2001) - 1000;

                tested.enqueue(value);
                reference.add(value);

            } else {
                int observed = tested.dequeue();
                int expected = reference.remove();

                check(
                    observed == expected,
                    "Random dequeue differs from reference"
                );
            }

            check(
                tested.isEmpty() == reference.isEmpty(),
                "Empty status differs"
            );

            check(
                tested.length() == reference.size(),
                "Length differs"
            );

            if (!reference.isEmpty()) {
                check(
                    tested.peek().equals(reference.peek()),
                    "Peek differs"
                );
            }

            if (i % 100 == 0) {
                tested.verifyHeapProperty();
            }
        }

        // Drain the remaining elements.
        while (!reference.isEmpty()) {
            check(
                tested.dequeue().equals(reference.remove()),
                "Drain differs"
            );
        }

        check(tested.isEmpty(), "Random test did not drain");

        // Test Comparable with strings and duplicates.
        MinHeap<String> words = new MinHeap<>();

        for (String word : new String[] {
            "pear", "apple", "banana", "apple"
        }) {
            words.enqueue(word);
        }

        check(words.dequeue().equals("apple"), "String ordering 1");
        check(words.dequeue().equals("apple"), "String duplicates");
        check(words.dequeue().equals("banana"), "String ordering 2");
        check(words.dequeue().equals("pear"), "String ordering 3");
        check(words.isEmpty(), "String heap must be empty");

        System.out.println(
            "MinHeap passed all PriorityQueue, Tree, and iterative traversal tests."
        );
    }
}
