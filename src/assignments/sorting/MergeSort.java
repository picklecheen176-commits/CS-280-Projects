package assignments.sorting;

import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    @Override
    public void sort(T[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        T[] temp = Arrays.copyOf(array, array.length);
        mergeSort(array, temp, 0, array.length - 1);
    }

    private void mergeSort(T[] array, T[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(array, temp, left, middle);
        mergeSort(array, temp, middle + 1, right);

        merge(array, temp, left, middle, right);
    }

    private void merge(T[] array, T[] temp, int left, int middle, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (temp[i].compareTo(temp[j]) <= 0) {
                array[k++] = temp[i++];
            } else {
                array[k++] = temp[j++];
            }
        }

        while (i <= middle) {
            array[k++] = temp[i++];
        }

        while (j <= right) {
            array[k++] = temp[j++];
        }
    }

    public static void main(String[] args) {
        SortingAlgorithm.validate(new MergeSort<Integer>());
    }
}
