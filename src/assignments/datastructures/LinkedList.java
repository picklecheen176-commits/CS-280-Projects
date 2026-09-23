package assignments.datastructures;

import adt.List;
import adt.Stack;
import java.util.Iterator;
import java.util.NoSuchElementException;

/// An extensible list backed by a chain of nodes.
///
/// The idea here is to wrap each datum in a larger structure, a *node*,
/// which also contains a pointer to the node containing the *next* element in the list.
///
/// @param <T> the type of each element
public class LinkedList<T> implements List<T>, Stack<T>, Iterable<T> {

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

        Node current = this.head;

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

        Node current = this.head;

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
     *
     * @param index the location of where to put the item
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
     *
     * @param index the location to delete from
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
     * Check whether the stack is empty.
     *
     * @return true if the stack contains no elements
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Push a value onto the top of the stack.
     *
     * @param value the value to push
     */
    @Override
    public void push(T value) {
        insert(0, value);
    }

    /**
     * Remove and return the top value from the stack.
     *
     * @return the value removed from the top
     */
    @Override
    public T pop() {
        assert !isEmpty();

        return delete(0);
    }

    /**
     * Return the top value without removing it.
     *
     * @return the value on top of the stack
     */
    @Override
    public T peek() {
        assert !isEmpty();

        return at(0);
    }

    /**
     * Return an iterator for this linked list.
     *
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
     * A node containing data and a link to the next node.
     */
    private class Node {

        T data;
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
     * Run validation tests.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        List.validate(new LinkedList<>());
        Stack.validate(new LinkedList<>());

        // Test iterator.
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            list.insert(0, i);
        }

        Iterator<Integer> iter = list.iterator();

        for (int i = 5; i > 0; i--) {
            assert iter.next().equals(i - 1);
        }

        assert !iter.hasNext();

        System.out.println("LinkedList passes all tests.");
    }
}