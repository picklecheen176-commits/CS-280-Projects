package assignments.datastructures;

import adt.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An extensible circular linked list.
 *
 * <p>Each element is stored inside a node. Unlike a regular linked list,
 * the final node links back to the first node rather than linking to null.
 * The list maintains a reference to the tail node. The head of the list
 * can therefore be found at {@code tail.link}.</p>
 *
 * @param <T> the type of each element
 */
public class CircularLinkedList<T> implements List<T>, Iterable<T> {

    /** The final node in the circular linked list. */
    private Node tail;

    /** The number of elements currently stored in the list. */
    private int size;

    /**
     * Initialize an empty circular linked list.
     */
    public CircularLinkedList() {
        this.tail = null;
        this.size = 0;
    }

    /**
     * Compute the number of items in this list.
     *
     * @return the number of items
     */
    public int length() {
        return this.size;
    }

    /**
     * Fetch an item from the list.
     *
     * @param index the location of the item
     * @return the value stored at the given location
     */
    public T at(int index) {
        assert 0 <= index && index < this.size;

        Node current = this.tail.link;

        for (int i = 0; i < index; i++) {
            current = current.link;
        }

        return current.data;
    }

    /**
     * Change an item in the list.
     *
     * @param index the location of the item
     * @param value the new value to assign at the given location
     */
    public void set(int index, T value) {
        assert 0 <= index && index < this.size;

        Node current = this.tail.link;

        for (int i = 0; i < index; i++) {
            current = current.link;
        }

        current.data = value;
    }

    /**
     * Check if the list contains a given value.
     *
     * @param value the value to look for
     * @return true iff the collection contains value
     */
    public boolean contains(T value) {
        if (this.size == 0) {
            return false;
        }

        Node current = this.tail.link;

        for (int i = 0; i < this.size; i++) {
            if (value == null) {
                if (current.data == null) {
                    return true;
                }
            } else if (value.equals(current.data)) {
                return true;
            }

            current = current.link;
        }

        return false;
    }

    /**
     * Insert an item into the list.
     *
     * @param index the location where the item should be inserted
     * @param value the new value to insert
     */
    public void insert(int index, T value) {
        assert 0 <= index && index <= this.size;

        Node newNode = new Node(value, null);

        if (this.size == 0) {
            newNode.link = newNode;
            this.tail = newNode;
        } else if (index == 0) {
            newNode.link = this.tail.link;
            this.tail.link = newNode;
        } else if (index == this.size) {
            newNode.link = this.tail.link;
            this.tail.link = newNode;
            this.tail = newNode;
        } else {
            Node current = this.tail.link;

            for (int i = 0; i < index - 1; i++) {
                current = current.link;
            }

            newNode.link = current.link;
            current.link = newNode;
        }

        this.size++;
    }

    /**
     * Remove an item from the list.
     *
     * @param index the location to delete from
     * @return the value which was removed
     */
    public T delete(int index) {
        assert 0 <= index && index < this.size;

        T removedValue;

        if (this.size == 1) {
            removedValue = this.tail.data;
            this.tail = null;
        } else if (index == 0) {
            Node head = this.tail.link;
            removedValue = head.data;
            this.tail.link = head.link;
        } else {
            Node current = this.tail.link;

            for (int i = 0; i < index - 1; i++) {
                current = current.link;
            }

            Node removedNode = current.link;
            removedValue = removedNode.data;
            current.link = removedNode.link;

            if (removedNode == this.tail) {
                this.tail = current;
            }
        }

        this.size--;

        return removedValue;
    }

    /**
     * Create an iterator that moves through every item in the list once.
     *
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node current =
                tail == null ? null : tail.link;

            private int remaining = size;

            /**
             * Check whether another item remains in the iterator.
             *
             * @return true if another item remains
             */
            @Override
            public boolean hasNext() {
                return remaining > 0;
            }

            /**
             * Return the next item in the iterator.
             *
             * @return the next value
             * @throws NoSuchElementException if no elements remain
             */
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T value = current.data;
                current = current.link;
                remaining--;

                return value;
            }
        };
    }

    /**
     * A node containing data and a link to the next node.
     */
    private class Node {

        /** The value stored in this node. */
        T data;

        /** The next node in the circular chain. */
        Node link;

        /**
         * Initialize a node.
         *
         * @param data the value stored in the node
         * @param link the next node in the chain
         */
        Node(T data, Node link) {
            this.data = data;
            this.link = link;
        }
    }

    /**
     * Run validation and iterator tests.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        List.validate(new CircularLinkedList<>());

        // Test iterator.
        CircularLinkedList<Integer> list =
            new CircularLinkedList<>();

        for (int i = 0; i < 5; i++) {
            list.insert(0, i);
        }

        Iterator<Integer> iter = list.iterator();

        for (int i = 5; i > 0; i--) {
            assert iter.next().equals(i - 1);
        }

        assert !iter.hasNext();

        System.out.println(
            "CircularLinkedList passes all tests."
        );
    }
}