package assignments.datastructures;

import adt.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/// An extensible list backed by a chain of nodes.
///
/// The idea here is to wrap each datum in a larger structure, a *node*,
/// which also contains a pointer to the node containing the *next* element in the list.
/// This structure permits efficient insertion and deletion,
/// in the sense that it only requires rearranging pointers nearby where the change takes place.
///
/// However, this structure foregoes *random access*, i.e. easy access to arbitrary locations in the list.
/// In order to make any changes to a location in the middle of the list,
/// one must first traverse through the chain of nodes from the beginning of the list.
///
/// @param <T> the type of each element
public class LinkedList<T> implements List<T>, Iterable<T> {

    private Node head;
    private int size;

    /**
     * Initialize an empty linked list.
     */
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Compute the number of items in this list.
     * @return the number of items
     */
    public int length() {
        return this.size;
    }

    /**
     * Fetch an item from the list.
     * @param index the location of the item - a nonnegative integer less than the length of the list
     * @return the value stored at the given location
     */
    public T at(int index) {
        assert 0 <= index && index < this.size;

        Node current = this.head;

        for (int i = 0; i < index; i++) {
            current = current.link;
        }

        return current.data;
    }

    /**
     * Change an item in the list.
     * @param index the location of the item - a nonnegative integer less than the length of the list
     * @param value the new value to assign at the given location
     */
    public void set(int index, T value) {
        assert 0 <= index && index < this.size;

        Node current = this.head;

        for (int i = 0; i < index; i++) {
            current = current.link;
        }

        current.data = value;
    }

    /**
     * Check if the list contains a given value.
     * @param value the value to look for
     * @return true iff the collection contains value
     */
    public boolean contains(T value) {
        Node current = this.head;

        while (current != null) {
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
     * @param index the location of where to put the item - a nonnegative integer less than or equal to the length of the list
     * @param value the new value to put at the given location
     */
    public void insert(int index, T value) {
        assert 0 <= index && index <= this.size;

        if (index == 0) {
            this.head = new Node(value, this.head);
        } else {
            Node current = this.head;

            for (int i = 0; i < index - 1; i++) {
                current = current.link;
            }

            current.link = new Node(value, current.link);
        }

        this.size++;
    }

    /**
     * Remove an item from the list.
     * @param index the location to delete from - a nonnegative integer less than the length of the list
     * @return the value which was removed
     */
    public T delete(int index) {
        assert 0 <= index && index < this.size;

        T removedValue;

        if (index == 0) {
            removedValue = this.head.data;
            this.head = this.head.link;
        } else {
            Node current = this.head;

            for (int i = 0; i < index - 1; i++) {
                current = current.link;
            }

            removedValue = current.link.data;
            current.link = current.link.link;
        }

        this.size--;

        return removedValue;
    }

    /**
     * Return an iterator for this linked list.
     * @return an iterator over the elements in the list
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T value = current.data;
                current = current.link;

                return value;
            }
        };
    }

    /**
     * An encapsulation of a value with a pointer, allowing us to chain to another value.
     */
    private class Node {
        T data;
        Node link;

        /**
         * Initialize a node with no children.
         * @param data the data value
         * @param link the next node in the chain
         */
        Node(T data, Node link) {
            this.data = data;
            this.link = link;
        }
    }

    /**
     * Run validation tests.
     * @param args command-line args
     */
    public static void main(String[] args) {
        List.validate(new LinkedList<>());

        // Test iterator.
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 5; i++)
            list.insert(0, i);

        Iterator<Integer> iter = list.iterator();

        for (int i = 5; i > 0; i--)
            assert iter.next().equals(i - 1);

        assert !iter.hasNext();

        System.out.println("LinkedList passes all tests.");
    }
}