
package assignments.datastructures;

import adt.Tree;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * An unbalanced binary search tree that stores distinct, comparable values.
 * Each node's left subtree contains smaller values, and its right subtree
 * contains larger values. Values that compare as equal are not duplicated.
 *
 * <p>The tree uses the natural ordering defined by {@link Comparable}.
 * Search, insertion, and removal take O(h) time, where h is the tree's height:
 * typically O(log n) for a reasonably shaped tree but O(n) in the worst case.
 * Traversal iterators contain a snapshot of the values when requested.
 * Null values are not supported.</p>
 *
 * @param <T> the type of comparable elements stored in the tree
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Tree<T> {

    /** A node with one stored value and up to two child nodes. */
    private static final class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;

        Node(E value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size;

    /** Constructs an empty binary search tree. */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * Checks whether a value is present without modifying the tree.
     *
     * @param value the non-null value to find
     * @return true if the value exists in the tree; otherwise false
     * @throws NullPointerException if value is null
     */
    public boolean contains(T value) {
        Objects.requireNonNull(value, "value");
        Node<T> current = root;

        while (current != null) {
            int comparison = value.compareTo(current.value);

            if (comparison == 0) {
                return true;
            }

            current = comparison < 0 ? current.left : current.right;
        }

        return false;
    }

    /**
     * Adds a value in sorted binary-search-tree order.
     * Duplicate values are not added.
     *
     * @param value the non-null value to insert
     * @return true if inserted; false if already present
     * @throws NullPointerException if value is null
     */
    public boolean add(T value) {
        Objects.requireNonNull(value, "value");

        if (root == null) {
            root = new Node<>(value);
            size++;
            return true;
        }

        Node<T> current = root;

        while (true) {
            int comparison = value.compareTo(current.value);

            if (comparison == 0) {
                return false;
            }

            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node<>(value);
                    break;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node<>(value);
                    break;
                }
                current = current.right;
            }
        }

        size++;
        return true;
    }

    /**
     * Removes a value while preserving binary search tree ordering.
     * Handles nodes with zero, one, or two children.
     *
     * @param value the non-null value to remove
     * @return true if removed; false if absent
     * @throws NullPointerException if value is null
     */
    public boolean remove(T value) {
        Objects.requireNonNull(value, "value");

        boolean[] removed = {false};
        root = removeNode(root, value, removed);

        if (removed[0]) {
            size--;
        }

        return removed[0];
    }

    /** Deletes one matching node and returns the new subtree root. */
    private Node<T> removeNode(Node<T> node, T value, boolean[] removed) {
        if (node == null) {
            return null;
        }

        int comparison = value.compareTo(node.value);

        if (comparison < 0) {
            node.left = removeNode(node.left, value, removed);
        } else if (comparison > 0) {
            node.right = removeNode(node.right, value, removed);
        } else {
            removed[0] = true;

            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            // Replace with the smallest value in the right subtree.
            Node<T> successor = node.right;

            while (successor.left != null) {
                successor = successor.left;
            }

            node.value = successor.value;
            node.right = deleteMinimum(node.right);
        }

        return node;
    }

    /** Removes the smallest node from a non-empty subtree. */
    private Node<T> deleteMinimum(Node<T> node) {
        if (node.left == null) {
            return node.right;
        }

        node.left = deleteMinimum(node.left);
        return node;
    }

    /**
     * Returns the number of values currently stored.
     *
     * @return the number of nodes in the tree
     */
    @Override
    public int length() {
        return size;
    }

    /**
     * Returns the maximum number of children held by any node.
     *
     * @return 0, 1, or 2 depending on the tree's structure
     */
    @Override
    public int degree() {
        return degree(root);
    }

    private int degree(Node<T> node) {
        if (node == null) {
            return 0;
        }

        int children = (node.left == null ? 0 : 1)
                     + (node.right == null ? 0 : 1);

        return Math.max(
            children,
            Math.max(degree(node.left), degree(node.right))
        );
    }

    /**
     * Computes the longest root-to-leaf path measured in node levels.
     *
     * @return 0 if empty, otherwise the number of levels
     */
    @Override
    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Traverses the tree in root-left-right order.
     *
     * @return an iterator in preorder
     */
    @Override
    public Iterator<T> preorder() {
        List<T> values = new ArrayList<>();
        collectPreorder(root, values);
        return values.iterator();
    }

    private void collectPreorder(Node<T> node, List<T> values) {
        if (node == null) {
            return;
        }

        values.add(node.value);
        collectPreorder(node.left, values);
        collectPreorder(node.right, values);
    }

    /**
     * Traverses the tree in left-root-right order.
     *
     * @return an iterator in sorted order
     */
    @Override
    public Iterator<T> inorder() {
        List<T> values = new ArrayList<>();
        collectInorder(root, values);
        return values.iterator();
    }

    private void collectInorder(Node<T> node, List<T> values) {
        if (node == null) {
            return;
        }

        collectInorder(node.left, values);
        values.add(node.value);
        collectInorder(node.right, values);
    }

    /**
     * Traverses the tree in left-right-root order.
     *
     * @return an iterator in postorder
     */
    @Override
    public Iterator<T> postorder() {
        List<T> values = new ArrayList<>();
        collectPostorder(root, values);
        return values.iterator();
    }

    private void collectPostorder(Node<T> node, List<T> values) {
        if (node == null) {
            return;
        }

        collectPostorder(node.left, values);
        collectPostorder(node.right, values);
        values.add(node.value);
    }

    /**
     * Traverses the tree breadth-first, one level at a time.
     *
     * @return an iterator in level order
     */
    @Override
    public Iterator<T> levelorder() {
        List<T> values = new ArrayList<>();

        if (root == null) {
            return values.iterator();
        }

        ArrayDeque<Node<T>> queue = new ArrayDeque<>();
        queue.addLast(root);

        while (!queue.isEmpty()) {
            Node<T> node = queue.removeFirst();
            values.add(node.value);

            if (node.left != null) {
                queue.addLast(node.left);
            }

            if (node.right != null) {
                queue.addLast(node.right);
            }
        }

        return values.iterator();
    }

    /**
     * Provides the default iterator using inorder traversal.
     *
     * @return an iterator of sorted values
     */
    @Override
    public Iterator<T> iterator() {
        return inorder();
    }

    /**
     * Runs interface validation and tests searching, inserting,
     * removing, and traversing values.
     *
     * Run with -ea to enable assertions.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {

        // Validate the Tree interface.
        Tree.validate(new BinarySearchTree<Integer>());

        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        // Test an empty tree.
        assert !tree.contains(8);
        assert !tree.remove(8);
        assert tree.length() == 0;

        // Test adding elements.
        int[] toAdd = {8, 3, 10, 1, 6, 14, 4, 7, 13};

        for (int value : toAdd) {
            assert tree.add(value);
            assert tree.contains(value);
        }

        assert tree.length() == 9;
        assert tree.degree() == 2;
        assert tree.height() == 4;

        // Test duplicate insertion.
        assert !tree.add(6);
        assert tree.length() == 9;

        // Test contains.
        assert !tree.contains(99);

        // Test traversals.
        assert traversalEquals(
            tree.inorder(),
            List.of(1, 3, 4, 6, 7, 8, 10, 13, 14)
        );

        assert traversalEquals(
            tree.preorder(),
            List.of(8, 3, 1, 6, 4, 7, 10, 14, 13)
        );

        assert traversalEquals(
            tree.postorder(),
            List.of(1, 4, 7, 6, 3, 13, 14, 10, 8)
        );

        assert traversalEquals(
            tree.levelorder(),
            List.of(8, 3, 10, 1, 6, 14, 4, 7, 13)
        );

        // Test removing a node with two children.
        assert tree.remove(3);
        assert !tree.contains(3);
        assert tree.length() == 8;

        // Test removing a leaf.
        assert tree.remove(1);
        assert !tree.contains(1);
        assert tree.length() == 7;

        // Test removing a node with one child.
        assert tree.remove(14);
        assert tree.contains(13);
        assert tree.length() == 6;

        // Test removing the root.
        assert tree.remove(8);
        assert !tree.contains(8);
        assert tree.length() == 5;

        // Test removing an element that does not exist.
        assert !tree.remove(8);

        // Confirm remaining values are sorted.
        assert traversalEquals(
            tree.inorder(),
            List.of(4, 6, 7, 10, 13)
        );

        assert traversalEquals(
            tree.iterator(),
            List.of(4, 6, 7, 10, 13)
        );

        // Remove all remaining elements.
        for (int value : new int[]{4, 6, 7, 10, 13}) {
            assert tree.remove(value);
        }

        assert tree.length() == 0;
        assert tree.height() == 0;
        assert tree.degree() == 0;
        assert !tree.iterator().hasNext();

        // Test the generic implementation using Strings.
        BinarySearchTree<String> words = new BinarySearchTree<>();

        assert words.add("pear");
        assert words.add("apple");

        assert traversalEquals(
            words.iterator(),
            List.of("apple", "pear")
        );

        System.out.println(
            "BinarySearchTree passed all contains, add, remove, and traversal tests."
        );
    }

    /**
     * Compares iterator values against an expected list.
     *
     * @param iterator the iterator being tested
     * @param expected the expected sequence of values
     * @param <E> the element type
     * @return true if the sequences are identical
     */
    private static <E> boolean traversalEquals(
        Iterator<E> iterator,
        List<E> expected
    ) {
        for (E value : expected) {
            if (!iterator.hasNext()
                || !Objects.equals(iterator.next(), value)) {
                return false;
            }
        }

        return !iterator.hasNext();
    }
}
