package assignments.datastructures;

import adt.List;

/// An extensible list backed by an array buffer.
///
/// The idea here is to store your data in an array larger than it has to be.
/// This gives you room to add more items to the end, without having to reallocate memory every time.
/// You simply need to keep track of which parts of the array are currently in use.
///
/// Eventually, the array buffer *will* run out of space.
/// Then you need to allocate an even larger buffer, and copy the present buffer to the new one.
/// This is a very expensive operation, so you want to make sure it occurs very infrequently.
///
/// @param <T> the type of each element
public class Vector<T> implements List<T> {
    /** The initial amount of buffer space in a newly-created vector. */
    public static final int INITIAL_BUFFER_SIZE = 10;

    private T[] array;
    private int size;

    /**
     * Initialize an empty vector.
     */
    @SuppressWarnings("unchecked")
    public Vector() {
        // Generic types (i.e. `T`) don't technically exist at runtime, so you have to allocate arrays generically and then cast them.
        // This is normally bad practice and generates a warning, hence the @SuppressWarnings tag before the method.
        this.array = (T[])(new Object[INITIAL_BUFFER_SIZE]);
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

        return this.array[index];
    }

    /**
     * Change an item in the list.
     * @param index the location of the item - a nonnegative integer less than the length of the list
     * @param value the new value to assign at the given location
     */
    public void set(int index, T value) {
        assert 0 <= index && index < this.size;

        this.array[index] = value;
    }

    /**
     * Check if the list contains a given value.
     * @param value the value to look for
     * @return true iff the collection contains value
     */
    public boolean contains(T value) {
        for (int i = 0; i < this.size; i++) {
            if (this.array[i].equals(value)) {
                return true;
            }
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

        if (this.size == this.array.length) {
            resize(this.array.length * 2);
        }

        for (int i = this.size; i > index; i--) {
            this.array[i] = this.array[i - 1];
        }

        this.array[index] = value;
        this.size++;
    }

    /**
     * Remove an item from the list.
     * @param index the location to delete from - a nonnegative integer less than the length of the list
     * @return the value which was removed
     */
    public T delete(int index) {
        assert 0 <= index && index < this.size;

        T value = this.array[index];

        for (int i = index; i < this.size - 1; i++) {
            this.array[i] = this.array[i + 1];
        }

        this.array[this.size - 1] = null;
        this.size--;

        return value;
    }

    /**
     * Resize the internal buffer array.
     *
     * This method involves copying from the current buffer to a newly allocated one.
     *
     * @param newSize the new size of the internal buffer array
     */
    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        T[] newArray = (T[])(new Object[newSize]);

        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.array[i];
        }

        this.array = newArray;
    }

    /**
     * Run validation tests.
     * @param args command-line args
     */
    public static void main(String[] args) {
        List.validate(new Vector<>());
        System.out.println("Vector passes all tests.");
    }
}