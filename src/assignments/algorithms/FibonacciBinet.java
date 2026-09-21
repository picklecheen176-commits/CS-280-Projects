package assignments.algorithms;

public class FibonacciBinet {

    private static volatile long result;

    public static long fibonacci(int n) {
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;
        double psi = (1 - sqrt5) / 2;

        return Math.round(
            (Math.pow(phi, n) - Math.pow(psi, n)) / sqrt5
        );
    }

    public static void main(String[] args) {

        // Warm up the JVM.
        for (int i = 0; i < 100000; i++) {
            result = fibonacci(40);
        }

        System.out.println("n,time");

        int[] sizes = {
            5,
            10,
            20,
            30,
            40,
            50,
            60,
            70
        };

        int trials = 1000000;

        for (int n : sizes) {

            long start = System.nanoTime();

            for (int trial = 0; trial < trials; trial++) {
                result = fibonacci(n);
            }

            long end = System.nanoTime();

            double averageTime =
                (end - start) / (double) trials;

            System.out.println(n + "," + averageTime);
        }
    }
}