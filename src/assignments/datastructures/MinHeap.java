
package assignments.datastructures;

import adt.PriorityQueue;
import adt.Tree;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A min-heap stored in a Vector.
 * The smallest value is always at the root.
 * No recursion is used.
 *
 * @param <T> the type of values stored
 */
public class MinHeap<T extends Comparable<T>>
        implements PriorityQueue<T>, Tree<T> {

    private final Vector<T> heap = new Vector<>();

    /** Returns true if the heap is empty. */
    @Override
    public boolean isEmpty() {
        return heap.length() == 0;
    }

    /** Returns the number of values. */
    @Override
    public int length() {
        return heap.length();
    }

    /** Adds a value in O(log N) time. */
    @Override
    public void enqueue(T value) {
        if (value == null) {
            throw new NullPointerException();
        }

        int i = length();
        heap.insert(i, value);

        while (i > 0) {
            int parent = (i - 1) / 2;

            if (heap.at(parent).compareTo(heap.at(i)) <= 0) {
                break;
            }

            swap(i, parent);
            i = parent;
        }
    }

    /** Removes and returns the smallest value in O(log N) time. */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T smallest = heap.at(0);
        T last = heap.delete(length() - 1);

        if (isEmpty()) {
            return smallest;
        }

        heap.set(0, last);
        int i = 0;

        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smaller = left;

            if (left >= length()) {
                break;
            }

            if (right < length() &&
                heap.at(right).compareTo(heap.at(left)) < 0) {
                smaller = right;
            }

            if (heap.at(i).compareTo(heap.at(smaller)) <= 0) {
                break;
            }

            swap(i, smaller);
            i = smaller;
        }

        return smallest;
    }

    /** Returns the smallest value without removing it. */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return heap.at(0);
    }

    /** Returns the maximum number of children of a node. */
    @Override
    public int degree() {
        if (length() >= 3) return 2;
        if (length() == 2) return 1;
        return 0;
    }

    /** Returns the height in number of levels. */
    @Override
    public int height() {
        int n = length();
        int height = 0;

        while (n > 0) {
            height++;
            n /= 2;
        }

        return height;
    }

    /** Visits root, left, then right. */
    @Override
    public Iterator<T> preorder() {
        ArrayList<T> result = new ArrayList<>();
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        if (!isEmpty()) stack.push(0);

        while (!stack.isEmpty()) {
            int i = stack.pop();
            result.add(heap.at(i));

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (right < length()) stack.push(right);
            if (left < length()) stack.push(left);
        }

        return result.iterator();
    }

    /** Visits left, root, then right. */
    @Override
    public Iterator<T> inorder() {
        ArrayList<T> result = new ArrayList<>();
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int i = 0;

        while (i < length() || !stack.isEmpty()) {
            while (i < length()) {
                stack.push(i);
                i = 2 * i + 1;
            }

            i = stack.pop();
            result.add(heap.at(i));
            i = 2 * i + 2;
        }

        return result.iterator();
    }

    /** Visits left, right, then root. */
    @Override
    public Iterator<T> postorder() {
        ArrayList<T> result = new ArrayList<>();
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        ArrayDeque<Integer> reverse = new ArrayDeque<>();

        if (!isEmpty()) stack.push(0);

        while (!stack.isEmpty()) {
            int i = stack.pop();
            reverse.push(i);

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < length()) stack.push(left);
            if (right < length()) stack.push(right);
        }

        while (!reverse.isEmpty()) {
            result.add(heap.at(reverse.pop()));
        }

        return result.iterator();
    }

    /** Visits nodes level by level. */
    @Override
    public Iterator<T> levelorder() {
        ArrayList<T> result = new ArrayList<>();

        for (int i = 0; i < length(); i++) {
            result.add(heap.at(i));
        }

        return result.iterator();
    }

    /** Uses level-order traversal by default. */
    @Override
    public Iterator<T> iterator() {
        return levelorder();
    }

    /** Swaps two values in the heap. */
    private void swap(int a, int b) {
        T temp = heap.at(a);
        heap.set(a, heap.at(b));
        heap.set(b, temp);
    }

    /** Runs interface validation and a simple ordering test. */
    public static void main(String[] args) {
        PriorityQueue.validate(new MinHeap<Integer>());
        Tree.validate(new MinHeap<Integer>());

        MinHeap<Integer> heap = new MinHeap<>();

        heap.enqueue(5);
        heap.enqueue(2);
        heap.enqueue(8);
        heap.enqueue(1);
        heap.enqueue(3);

        for (int expected = 1; expected <= 3; expected++) {
            if (heap.dequeue() != expected) {
                throw new AssertionError("Wrong order");
            }
        }

        if (heap.dequeue() != 5 || heap.dequeue() != 8) {
            throw new AssertionError("Wrong order");
        }

        System.out.println("MinHeap passes all tests.");
    }
}
