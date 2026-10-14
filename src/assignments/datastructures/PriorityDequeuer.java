package assignments.datastructures;

import adt.PriorityQueue;
import java.util.NoSuchElementException;

/**
 * A priority queue implementation with an O(1) dequeue operation.
 *
 * <p>Elements are maintained in ascending order so that the smallest
 * element, which has the highest priority, is always stored at the
 * beginning of the linked list. Enqueue may require O(n) time, while
 * dequeue and peek run in O(1) time.</p>
 *
 * @param <T> the type of element stored in the priority queue
 */
public class PriorityDequeuer<T extends Comparable<T>>
        implements PriorityQueue<T> {

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head;

    /**
     * Constructs an empty PriorityDequeuer.
     */
    public PriorityDequeuer() {
        head = null;
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
     * Adds an element to the priority queue in priority order.
     *
     * <p>Smaller values have higher priority. This operation runs
     * in O(n) time in the worst case because the correct insertion
     * location must be found.</p>
     *
     * @param item the element to add
     */
    @Override
    public void enqueue(T item) {
        Node newNode = new Node(item);

        if (head == null || item.compareTo(head.data) < 0) {
            newNode.next = head;
            head = newNode;
            return;
        }

        Node current = head;

        while (current.next != null
                && current.next.data.compareTo(item) <= 0) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;
    }

    /**
     * Removes and returns the highest-priority element.
     *
     * <p>Because the queue is kept sorted, the highest-priority
     * element is always at the front. Therefore this operation
     * runs in O(1) time.</p>
     *
     * @return the highest-priority element
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty.");
        }

        T value = head.data;
        head = head.next;

        return value;
    }

    /**
     * Returns the highest-priority element without removing it.
     *
     * <p>This operation runs in O(1) time because the
     * highest-priority element is always at the front.</p>
     *
     * @return the highest-priority element
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty.");
        }

        return head.data;
    }

    /**
     * Validates the PriorityDequeuer implementation.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        PriorityQueue.validate(new PriorityDequeuer<Integer>());
    }
}