package assignments.algorithms;

public class FibonacciRecursive {

    private static volatile long result;

    /**
     * Compute the nth Fibonacci number recursively.
     *
     * @param n the position in the Fibonacci sequence
     * @return the nth Fibonacci number
     */
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Run timing tests for recursive Fibonacci.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        // Warm up the JVM.
        for (int i = 0; i < 1000; i++) {
            result = fibonacci(20);
        }

        System.out.println("n,time,ln(time)");

        int[] sizes = {
            5,
            10,
            15,
            20,
            25,
            30,
            32,
            34,
            36,
            38,
            40
        };

        for (int n : sizes) {

            int trials;

            if (n <= 25) {
                trials = 100;
            } else if (n <= 35) {
                trials = 10;
            } else {
                trials = 3;
            }

            long totalTime = 0;

            for (int trial = 0; trial < trials; trial++) {

                long start = System.nanoTime();

                result = fibonacci(n);

                long end = System.nanoTime();

                totalTime += end - start;
            }

            double averageTime =
                totalTime / (double) trials;

            double logTime =
                Math.log(averageTime);

            System.out.println(
                n + "," +
                averageTime + "," +
                logTime
            );
        }
    }
}