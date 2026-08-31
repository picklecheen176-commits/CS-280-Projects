package assignments.datastructures;

import adt.OrderedPair;

/// An ordered pair representing a key-value pair.
/// 
/// The operationally signficant member of the KeyValuePair is the *key*.
/// The *value* is just an extra bit of information attached to the key.
/// Once set, the key cannot be changed. The value can be changed at any time.
/// Two key-value pairs are considered *equal* so long as they have the same key.
/// 
/// Most data structures we learn about in this course may hold an arbitrary number of items.
/// This data structure holds exactly two. 
/// This class serves the following purposes:
/// 1. In the first Monday, we will use this class to practice running Java and submitting work with git.
/// 2. In the first Wednesday, we will use this class as a model to understand how variables and objects are represented in memory.
/// 3. In the first weekend, we will learn how to adapt this class to use "generics".
/// 4. Later on, we will use this class to implement a *Map*, a data structure similar to Python's `dict`.
public class KeyValuePair<K, V> implements OrderedPair<K, V> {
    private K key;
    private V value;

    /**
     * Initialize a key-value pair.
     * @param key the key (immutable)
     * @param value the value (may be changed later)
     */
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Extract the key from the key-value pair.
     * @return the key
     */
    public K first() {
        return this.key;
    }

    /**
     * Extract the value from the key-value pair.
     * @return the value
     */
    public V second() {
        return this.value;
    }

    /**
     * Construct a new ordered pair with the order of items reversed.
     * 
     * The ordered pair interface requires a method to transform an ordered pair (x,y) to its reverse, (y,x).
     * But there's a problem - for a KeyValuePair, x and y are two intrinsically different kinds of objects.
     * You can't just make a KeyValuePair with x and y reversed; that would be a - let's say - a ValueKeyPair!
     * 
     * Note that the ordered pair interface does not require us to return an ordered pair of the same type.
     * Any object implementing the `OrderedPair` interface will do.
     * So one solution would be to go through the effort of defining a `ValueKeyPair` class from scratch,
     *  and for this `reversed` method to return an object of that class.
     * 
     * But this isn't a great solution, because a `ValueKeyPair` is not useful for anything other than this single method.
     * Instead, we can use an *anonymous class*,
     *  defining a minimal custom behavior for a so-called ValueKeyPair from within the `reversed` method itself.
     * 
     * This design pattern is common whenever you need to implement a method which specifies an abstract return type.
     * We will see it again when we learn about iterators.
     * 
     * @return a new ordered pair
     */
    public OrderedPair<V, K> reversed() {
        return new OrderedPair<V, K>() {
            V v = KeyValuePair.this.value;
            K k = KeyValuePair.this.key;
            public V first() { return this.v; }
            public K second() { return this.k; }
            public OrderedPair<K, V> reversed() { return new KeyValuePair<>(this.k, this.v); }
        };
    }

    /**
     * Replace the value in the key-value pair.
     * @param value the new value
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Represent the key-value pair with a string.
     * @return "{key} => {pair}"
     */
    public String toString() {
        return this.key.toString()+" => "+this.value.toString();
    }

    /**
     * Check if the key-value pair is equal to another object.
     * 
     * In order to be equal, the other object must itself be a key-value pair with an equal key.
     * Counter-intuitively, the value is *not* considered to be relevant when establishing equality.
     * 
     * @return true iff o is a key-value pair and its key equals that of this key-value pair
     */
    public boolean equals(Object o) {
        return (o instanceof KeyValuePair<?, ?>) && this.key.equals(((KeyValuePair<?, ?>) o).key);
    }

    /**
     * Compute a hash code for the key-value pair.
     * 
     * A hash code is an integer "fingerprint" of an object.
     * It need not be any integer in particular; in fact, we will learn later that random integers are often better.
     * However, in order to act as a "fingerprint",
     *  we require that two objects which are considered *equal* must have the same hash code.
     * Because equality of key-value pairs depends only on the key, so too must the hash code.
     * 
     * Rather than worry about the details of exactly what integer to compute,
     *  we can simply use the algorithm already in place for the key.
     * 
     * @return the hash code of the key
     */
    public int hashCode() {
        return this.key.hashCode();
    }
    
    /**
     * Run validation tests.
     * @param args command-line args
     */
    public static void main(String[] args) {
        OrderedPair.validate(new KeyValuePair<String, Integer>("", 0));

        // Create a specific key-value pair to test with.
        KeyValuePair<String, Integer> pair =
    new KeyValuePair<>("disciples", 12);

        // Test that we can mutate the value.
        assert pair.second().equals(12);
        pair.setValue(11);
        assert pair.second().equals(11);
        
        // Test that the return values for toString and hashCode match documentation.
        assert pair.toString().equals("disciples => 11");
        assert pair.hashCode() == "disciples".hashCode();

        // Test that equality is based on key but not on value.
        assert pair.equals(new KeyValuePair<>("disciples", 12));
        assert !pair.equals(new KeyValuePair<>("apostles", 11));

        System.out.println("KeyValuePair passes all tests.");
    }
    
}
