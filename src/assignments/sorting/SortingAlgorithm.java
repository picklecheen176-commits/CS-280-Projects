package assignments.sorting;

import java.util.Arrays;

/// Superclass to ensure all sorting algorithms operate in a consistent manner.
/// 
/// We use "interfaces" for data structures, but we use this "abstract class" for sorting algorithms.
/// The chief differences are that a class can "implement" any number of interfaces but only "extend" one class,
///  and that abstract classes are allowed to implement default behavior.
/// We take advantage of this fact to implement two versions of every sort:
///  an "in-place" version called `sort` which modifies the input array, and
///  an "out-of-place" version called `sorted` which constructs a new array.
/// The subclasses you create will only need to override the "in-place" version.
/// 
/// To create a class implementing your own sorting algorithm,
/// 1. Create a new class file in your "`sorting`" package. E.g. `BubbleSort.java`.
/// 2. Extend this class in your class definition. E.g. "`class BubbleSort extends SortingAlgorithm`"
/// 3. Implement the "`sort`" method.
/// 4. Write a main function in your class file which, at minimum, calls the "`validate`" method. E.g. `SortingAlgorithm.validate(new BubbleSort())`)
/// 
/// @param <T> the type of object to be sorted. Must be ordinal.
public abstract class SortingAlgorithm<T extends Comparable<T>> {
    /**
     * Sort an array in-place.
     * 
     * Post-condition: `array` is sorted in ascending order.
     * 
     * @param array the array to sort
     */
    public abstract void sort(T[] array);

    /**
     * Construct a sorted version of an array.
     * 
     * @param array the array to sort
     * @return the sorted array
     */
    public T[] sorted(T[] array) {
        T[] copiedArray = Arrays.copyOf(array, array.length);
        sort(copiedArray);
        return copiedArray;
    }

    /**
     * Both this class and all sorting algorithms you implement do not use instance variables.
     * Therefore, you can construct them with an argument-less "default constructor" that need not be explicitly defined.
     * However, the `javadoc` command will complain if you attempt to build documentation for a class whose constructor is not documented,
     *  and that includes any class relying on its default constructor.
     * 
     * Therefore, we include a dummy constructor, with this javadoc.
     * You should include a similar dummy constructor in your sorting algorithms,
     *  though the javadoc need not be so long-winded.
     */
    public SortingAlgorithm() {}

    /**
     * Test a sorting algorithm with a standard test,
     *  including potentially tricky features like negative numbers and duplicates.
     * @param algorithm a freshly-initialized SortingAlgorithm object
     */
    public static void validate(SortingAlgorithm<Integer> algorithm) {
        // Construct a sample array of numbers, including negatives and duplicates.
        Integer[] random_array = {-7, 4, 3, 5, -9, 1, -6, 7, -5, -9, -1, 1, 7, 7, 6};
        Integer[] sorted_array = {-9, -9, -7, -6, -5, -1, 1, 1, 3, 4, 5, 6, 7, 7, 7};

        // Copy sample array twice, to use for sort() and for sorted().
        Integer[] tosort_array = Arrays.copyOf(random_array, random_array.length);
        Integer[] copied_array = Arrays.copyOf(random_array, random_array.length);

        // Test in-place sorting.
        algorithm.sort(tosort_array);
        assert Arrays.equals(sorted_array, tosort_array) : String.format(
            """

            %s.sort() produced incorrect array

            Original: %s
            Expected: %s
            Encountered: %s

            """,
            algorithm.getClass().getSimpleName(),
            Arrays.toString(random_array),
            Arrays.toString(sorted_array),
            Arrays.toString(tosort_array)
        );

        // Test sorted() method.
        Integer[] tested_array = algorithm.sorted(copied_array);
        assert Arrays.equals(random_array, copied_array) : String.format(
            """

            %s.sorted() modified input array

            Original: %s
            Encountered: %s

            """,
            algorithm.getClass().getSimpleName(),
            Arrays.toString(random_array),
            Arrays.toString(copied_array)
        );
        assert Arrays.equals(sorted_array, tested_array) : String.format(
            """

            %s.sorted() produced incorrect array

            Original: %s
            Expected: %s
            Encountered: %s

            """,
            algorithm.getClass().getSimpleName(),
            Arrays.toString(random_array),
            Arrays.toString(sorted_array),
            Arrays.toString(tested_array)
        );

        System.out.println(String.format(
            "* %s passes sorting validation.",
            algorithm.getClass().getSimpleName()
        ));
    }
}
