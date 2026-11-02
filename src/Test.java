
import assignments.datastructures.*;
import assignments.sorting.*;
import assignments.algorithms.*;

public class Test {
    public static void main(String[] args) {
        if (!checkAssertions()) {
            System.err.println("""
            ERROR: Some tests rely on assertions. Please run java with the -ea option, e.g.

                java -cp lib/* -ea src/Test.java
            """);
            System.exit(1);
        }

        assert runTests(args);

        System.out.println("All tests have passed without error.");
        System.exit(0);
    }

    private static boolean checkAssertions() {
        try {
            assert false;
        } catch (AssertionError e) {
            return true;
        }

        return false;
    }

    private static boolean runTests(String[] args) {

        // Test sorting algorithms.
        BubbleSort.main(args);
        SelectionSort.main(args);
        InsertionSort.main(args);
        MergeSort.main(args);
        QuickSort.main(args);
        BucketSort.main(args);
        TreeSort.main(args);
        testShellSort();
        testRadixSort();

        // Test data structures.
        KeyValuePair.main(args);
        Vector.main(args);
        LinkedList.main(args);
        CircularLinkedList.main(args);
        PriorityEnqueuer.main(args);
        PriorityDequeuer.main(args);
        BinarySearchTree.main(args);
        MinHeap.main(args);

        // Test Fibonacci algorithms.
        testFibonacciAlgorithms();

        return true;
    }

    private static void testShellSort() {

        ShellSort<Integer> sort = new ShellSort<>();

        // Test unsorted numbers.
        Integer[] numbers = {9, 5, 3, 7, 1};
        sort.sort(numbers);

        assert java.util.Arrays.equals(
            numbers, new Integer[]{1, 3, 5, 7, 9}
        );

        // Test reverse order.
        Integer[] reverse = {5, 4, 3, 2, 1};
        sort.sort(reverse);

        assert java.util.Arrays.equals(
            reverse, new Integer[]{1, 2, 3, 4, 5}
        );

        // Test duplicates and negative numbers.
        Integer[] duplicates = {3, -1, 3, 0, -5};
        sort.sort(duplicates);

        assert java.util.Arrays.equals(
            duplicates, new Integer[]{-5, -1, 0, 3, 3}
        );

        // Test empty array.
        Integer[] empty = {};
        sort.sort(empty);
        assert empty.length == 0;

        // Test strings.
        String[] words = {"cat", "apple", "dog"};
        new ShellSort<String>().sort(words);

        assert java.util.Arrays.equals(
            words, new String[]{"apple", "cat", "dog"}
        );

        System.out.println("ShellSort passes all tests.");
    }


    private static void testRadixSort() {

        RadixSort sort = new RadixSort();

        // Test normal numbers.
        Integer[] numbers = {9, 5, 3, 7, 1};
        sort.sort(numbers);

        assert java.util.Arrays.equals(
            numbers, new Integer[]{1, 3, 5, 7, 9}
        );

        // Test reverse order.
        Integer[] reverse = {5, 4, 3, 2, 1};
        sort.sort(reverse);

        assert java.util.Arrays.equals(
            reverse, new Integer[]{1, 2, 3, 4, 5}
        );

        // Test negative numbers and duplicates.
        Integer[] negatives = {3, -1, 3, 0, -5};
        sort.sort(negatives);

        assert java.util.Arrays.equals(
            negatives, new Integer[]{-5, -1, 0, 3, 3}
        );

        // Test integer limits.
        Integer[] limits = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        sort.sort(limits);

        assert java.util.Arrays.equals(
            limits, new Integer[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}
        );

        // Test empty array.
        Integer[] empty = {};
        sort.sort(empty);
        assert empty.length == 0;

        System.out.println("RadixSort passes all tests.");
    }


    private static void testFibonacciAlgorithms() {

        // Dynamic Programming.
        assert FibonacciDynamic.fibonacci(0) == 0;
        assert FibonacciDynamic.fibonacci(1) == 1;
        assert FibonacciDynamic.fibonacci(10) == 55;
        assert FibonacciDynamic.fibonacci(20) == 6765;

        System.out.println(
            "FibonacciDynamic passes all tests."
        );

        // Binet's Formula.
        assert FibonacciBinet.fibonacci(0) == 0;
        assert FibonacciBinet.fibonacci(1) == 1;
        assert FibonacciBinet.fibonacci(10) == 55;
        assert FibonacciBinet.fibonacci(20) == 6765;

        System.out.println(
            "FibonacciBinet passes all tests."
        );

        // Recursive Fibonacci.
        assert FibonacciRecursive.fibonacci(0) == 0;
        assert FibonacciRecursive.fibonacci(1) == 1;
        assert FibonacciRecursive.fibonacci(10) == 55;
        assert FibonacciRecursive.fibonacci(20) == 6765;

        System.out.println(
            "FibonacciRecursive passes all tests."
        );
    }
}
