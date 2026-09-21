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

        // Test data structures.
        KeyValuePair.main(args);
        Vector.main(args);
        LinkedList.main(args);
        CircularLinkedList.main(args);

        // Test Fibonacci algorithms.
        testFibonacciAlgorithms();

        return true;
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