import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class SortingStudentTest {

    private static final int TIMEOUT = 200;
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Alex
                index 1: Garrett
                index 2: Mahima
                index 3: Paige
                index 4: Brooke
                index 5: Sanjana
                index 6: Youn
                index 7: Nick
                index 8: Robert
                index 9: Destini
         */

        /*
            Sorted Names:
                index 0: Alex
                index 1: Brooke
                index 2: Destini
                index 3: Garrett
                index 4: Mahima
                index 5: Nick
                index 6: Paige
                index 7: Robert
                index 8: Sanjana
                index 9: Youn
         */

        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Garrett");
        tas[2] = new TeachingAssistant("Mahima");
        tas[3] = new TeachingAssistant("Paige");
        tas[4] = new TeachingAssistant("Brooke");
        tas[5] = new TeachingAssistant("Sanjana");
        tas[6] = new TeachingAssistant("Youn");
        tas[7] = new TeachingAssistant("Nick");
        tas[8] = new TeachingAssistant("Robert");
        tas[9] = new TeachingAssistant("Destini");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[4];
        tasByName[2] = tas[9];
        tasByName[3] = tas[1];
        tasByName[4] = tas[2];
        tasByName[5] = tas[7];
        tasByName[6] = tas[3];
        tasByName[7] = tas[8];
        tasByName[8] = tas[5];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 24 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testSelectionSort() {
        Sorting.selectionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 45 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 32 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 21 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Sorting.quickSort(tas, comp, new Random(234));
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 27 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[]{54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[]{-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    private static class TeachingAssistant {
        private String name;


        public TeachingAssistant(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof TeachingAssistant
                && ((TeachingAssistant) other).name.equals(this.name);
        }
    }


    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;


        public int getCount() {
            return count;
        }


        public void incrementCount() {
            count++;
        }
    }
}
