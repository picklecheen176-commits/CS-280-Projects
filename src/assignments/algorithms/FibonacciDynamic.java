package assignments.algorithms;

public class FibonacciDynamic {

    /**
     * Compute the nth Fibonacci number using dynamic programming.
     */
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        long previous = 0;
        long current = 1;

        for (int i = 2; i <= n; i++) {
            long next = previous + current;
            previous = current;
            current = next;
        }

        return current;
    }

    public static void main(String[] args) {

        // Warm up the JVM before collecting timing data.
        for (int i = 0; i < 10000; i++) {
            fibonacci(1000);
        }

        System.out.println("n,time");

        int[] sizes = {
            1000,
            5000,
            10000,
            50000,
            100000,
            500000,
            1000000,
            5000000,
            10000000
        };

        for (int n : sizes) {

            int trials = 10;
            long totalTime = 0;

            for (int trial = 0; trial < trials; trial++) {

                long start = System.nanoTime();

                fibonacci(n);

                long end = System.nanoTime();

                totalTime += end - start;
            }

            double averageTime = totalTime / (double) trials;

            System.out.println(n + "," + averageTime);
        }
    }
}