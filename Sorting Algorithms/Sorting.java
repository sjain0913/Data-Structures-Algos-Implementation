import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Saumya Jain
 * @version 1.0
 * @userid sjain395
 * @GTID 903407158
 *
 * Collaborators: Collaboration with Gursimran Singh and Ritesh Malpani
 *
 * Resources: Stack Overflow, Youtube
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, the array"
                    + " cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, the comparator"
                    + " cannot be null!");
        }
        int minIndex;
        for (int i = 0; i < arr.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, the array"
                    + " cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, the comparator"
                    + " cannot be null!");
        }
        T temp;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    j = -1;
                }
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, the array"
                    + " cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, the comparator"
                    + " cannot be null!");
        }
        boolean swap = true;
        int start = 0;
        int end = arr.length - 1;
        int endT = 0;
        while (swap && start < end) {
            swap = false;
            endT = 1;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i + 1], arr[i]) < 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swap = true;
                    endT = 1;
                } else {
                    endT++;
                }
            }
            end = end - endT;
            if (swap) {
                swap = false;
                endT = 1;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swap = true;
                        endT = 1;
                    } else {
                        endT++;
                    }
                }
            }
            start = start + endT;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, the array"
                    + " cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, the comparator"
                    + " cannot be null!");
        }
        if (arr.length > 1) {
            int middleIndex = (int) (arr.length / 2);
            int leftSize = (int) (arr.length / 2);
            int rightSize = arr.length - leftSize;
            T[] leftArr = (T[]) new Object[leftSize];
            T[] rightArr = (T[]) new Object[rightSize];
            for (int i = 0; i < leftSize; i++) {
                leftArr[i] = arr[i];
            }
            for (int i = leftSize; i < arr.length; i++) {
                rightArr[i - leftSize] = arr[i];
            }
            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);
            int leftIndex = 0;
            int rightIndex = 0;
            int currentIndex = 0;
            while (leftIndex < middleIndex
                    && rightIndex < arr.length - middleIndex) {
                if (comparator.compare(leftArr[leftIndex],
                        rightArr[rightIndex]) <= 0) {
                    arr[currentIndex] = leftArr[leftIndex];
                    leftIndex++;
                } else {
                    arr[currentIndex] = rightArr[rightIndex];
                    rightIndex++;
                }
                currentIndex++;
            }
            while (leftIndex < middleIndex) {
                arr[currentIndex] = leftArr[leftIndex];
                leftIndex++;
                currentIndex++;
            }
            while (rightIndex < arr.length - middleIndex) {
                arr[currentIndex] = rightArr[rightIndex];
                rightIndex++;
                currentIndex++;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, the array"
                    + " cannot be null!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error, the comparator"
                    + " cannot be null!");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Error, the random object"
                    + " cannot be null!");
        }
        qsHelper(arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * Helper method for quick sort
     *
     * @param arr array to be sorted
     * @param first the starting index
     * @param last the ending index
     * @param rand the Random object used to select pivots
     * @param comparator the Comparator used to compare the data in array arr
     * @param <T> data type to sort
     */
    private static <T> void qsHelper(T[] arr, int first, int last,
                                     Random rand, Comparator<T> comparator) {
        if (last > first) {
            int partitionIndex = partition(arr, first, last,
                    rand.nextInt(last - first) + first, comparator);
            qsHelper(arr, first, partitionIndex - 1, rand, comparator);
            qsHelper(arr, partitionIndex + 1, last, rand, comparator);
        }
    }

    /**
     * Partitions the array for quick sort
     *
     * @param arr the array to be sorted
     * @param first starting index
     * @param last ending index
     * @param pIndex pivot index
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     * @return new pivot index
     */
    private static <T> int partition(T[] arr, int first, int last,
                                     int pIndex, Comparator<T> comparator) {
        T pivotData = arr[pIndex];
        swap(arr, pIndex, last);
        int ind = first;
        for (int i = first; i < last; i++) {
            if (comparator.compare(arr[i], pivotData) <= 0) {
                swap(arr, i, ind);
                ind++;
            }
        }
        swap(arr, last, ind);
        return ind;
    }

    /**
     * Swap two elements in an array
     *
     * @param arr the array that contains items to swap
     * @param a first item
     * @param b second item
     * @param <T>  data type to sort
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    /**
         * Implement LSD (least significant digit) radix sort.
         *
         * Make sure you code the algorithm as you have been taught it in class.
         * There are several versions of this algorithm and you may not get full
         * credit if you do not implement the one we have taught you!
         *
         * Remember you CANNOT convert the ints to strings at any point in your
         * code! Doing so may result in a 0 for the implementation.
         *
         * It should be:
         * out-of-place
         * stable
         * not adaptive
         *
         * Have a worst case running time of:
         * O(kn)
         *
         * And a best case running time of:
         * O(kn)
         *
         * You are allowed to make an initial O(n) passthrough of the array to
         * determine the number of iterations you need.
         *
         * At no point should you find yourself needing a way to exponentiate a
         * number; any such method would be non-O(1). Think about how how you
         * get each power of BASE naturally and efficiently as the algorithm
         * progresses through each digit.
         *
         * Refer to the PDF for more information on LSD Radix Sort.
         *
         * You may use ArrayList or LinkedList if you wish, but it may only be
         * used inside radix sort and any radix sort helpers. Do NOT use these
         * classes with other sorts. However, be sure the List implementation
         * choose allows for stability while being as efficient as possible.
         *
         * Do NOT use anything from the Math class except Math.abs().
         *
         * @param arr the array to be sorted
         * @throws IllegalArgumentException if the array is null
         */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Error, the array"
                    + " cannot be null!");
        }
        int maxNumber = arr[0];
        int maxLength = 1;
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > maxNumber) {
                maxNumber = Math.abs(arr[i]);
            }
        }
        while ((maxNumber) >= 10) {
            maxLength++;
            maxNumber = maxNumber / 10;
        }
        List<Integer>[] myList = new ArrayList[19];
        for (int i = 0; i < 19; i++) {
            myList[i] = new ArrayList<Integer>();
        }
        int divnumber = 1;
        for (int i = 0; i < maxLength; i++) {
            for (Integer num: arr) {
                myList[((num / divnumber) % 10) + 9].add(num);
            }
            int index = 0;
            for (int k = 0; k < myList.length; k++) {
                for (Integer xx: myList[k]) {
                    arr[index++] = xx;
                }
                myList[k].clear();
            }
            divnumber = divnumber * 10;
        }
    }
}
