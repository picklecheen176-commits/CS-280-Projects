package assignments.datastructures;

import adt.PriorityQueue;
import java.util.NoSuchElementException;

/**
 * A priority queue implementation with an O(1) enqueue operation.
 *
 * <p>Elements are inserted at the end of a linked list in constant time.
 * The smallest element has the highest priority. Because the elements are
 * not kept sorted, peek and dequeue search through the list to locate the
 * highest-priority element.</p>
 *
 * @param <T> the type of element stored in the priority queue
 */
public class PriorityEnqueuer<T extends Comparable<T>>
        implements PriorityQueue<T> {

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;

    /**
     * Constructs an empty PriorityEnqueuer.
     */
    public PriorityEnqueuer() {
        head = null;
        tail = null;
    }

    /**
     * Determines whether the priority queue is empty.
     *
     * @return true if the queue contains no elements; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Adds an element to the priority queue.
     *
     * <p>This operation runs in O(1) time because the new element
     * is appended directly to the end of the linked list.</p>
     *
     * @param item the element to add
     */
    @Override
    public void enqueue(T item) {
        Node newNode = new Node(item);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    /**
     * Removes and returns the highest-priority element.
     *
     * <p>The smallest element is considered the highest priority.
     * This operation runs in O(n) time because the list must be
     * searched for the smallest element.</p>
     *
     * @return the highest-priority element
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty.");
        }

        Node min = head;
        Node minPrevious = null;

        Node previous = null;
        Node current = head;

        while (current != null) {
            if (current.data.compareTo(min.data) < 0) {
                min = current;
                minPrevious = previous;
            }

            previous = current;
            current = current.next;
        }

        if (minPrevious == null) {
            head = head.next;
        } else {
            minPrevious.next = min.next;
        }

        if (min == tail) {
            tail = minPrevious;
        }

        if (head == null) {
            tail = null;
        }

        return min.data;
    }

    /**
     * Returns the highest-priority element without removing it.
     *
     * <p>The smallest element is considered the highest priority.
     * This operation runs in O(n) time.</p>
     *
     * @return the highest-priority element
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty.");
        }

        Node min = head;
        Node current = head.next;

        while (current != null) {
            if (current.data.compareTo(min.data) < 0) {
                min = current;
            }

            current = current.next;
        }

        return min.data;
    }

    /**
     * Validates the PriorityEnqueuer implementation.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        PriorityQueue.validate(new PriorityEnqueuer<Integer>());
    }
}