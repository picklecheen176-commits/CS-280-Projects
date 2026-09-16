package assignments.algorithms;
import assignments.sorting.InsertionSort;
public class ERADemo {
    private static Integer[] randomArray(int N)
    {
        Integer[] array = new Integer[N];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = (int)(Math.random() * N);
        }
        return array;
    }

    public static void main(String[] args){
        InsertionSort<Integer> sorter = new InsertionSort<>();
        for (int N = 10; N < 100; N += 10) {
            Integer[] array = randomArray(N);
            long start = System.nanoTime();
            sorter.sort(array);
            long end = System.nanoTime();
            double duration = (end-start) / 1e9;
            System.out.println(N + "\t" + duration); 
        }



    }


}
//java -cp lib/* src/assignments/algorithms/ERADemo.java >> dat/insertiondata.csv
//mkdir dat