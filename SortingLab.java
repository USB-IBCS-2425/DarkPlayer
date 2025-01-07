import java.util.*;

class SortingLab {

    private ArrayList<Integer> a;
    private int n;

    // Constructor
    public SortingLab(int num) {
        this.n = num;
        this.a = new ArrayList<>();
        resetList();
    }

    // Reset the list with random integers
    public void resetList() {
        a.clear();
        for (int i = 0; i < n; i++) {
            int newNum = 1 + (int) (Math.random() * n);
            a.add(newNum);
        }
    }

    // BubbleSort
    public void BubbleSort() {
        while (true) {
            boolean swapped = false;
            for (int i = 0; i < a.size() - 1; i++) {
                if (a.get(i) > a.get(i + 1)) {
                    // Swap
                    int temp = a.get(i);
                    a.set(i, a.get(i + 1));
                    a.set(i + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    // SelectionSort
    public void SelectionSort() {
        for (int i = 0; i < a.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.size(); j++) {
                if (a.get(j) < a.get(minIndex)) {
                    minIndex = j;
                }
            }
            // Swap
            int temp = a.get(i);
            a.set(i, a.get(minIndex));
            a.set(minIndex, temp);
        }
    }

    // InsertionSort
    public void InsertionSort() {
        for (int i = 1; i < a.size(); i++) {
            int elem = a.get(i);
            int j = i - 1;
            while (j >= 0 && a.get(j) > elem) {
                a.set(j + 1, a.get(j));
                j--;
            }
            a.set(j + 1, elem);
        }
    }

    // QuickSort Helper
    private int QuickSplit(int start, int stop) {
        int pivot = a.get(stop);
        int pos = start;

        for (int i = start; i < stop; i++) {
            if (a.get(i) <= pivot) {
                Collections.swap(a, pos, i);
                pos++;
            }
        }
        Collections.swap(a, pos, stop);
        return pos;
    }

    // QuickSort
    public void QuickSort(int start, int stop) {
        if (start < stop) {
            int pivot = QuickSplit(start, stop);
            QuickSort(start, pivot - 1);
            QuickSort(pivot + 1, stop);
        }
    }

    // MergeSort (Placeholder)
    // MergeSort Helper Method
private void Merge(int start, int middle, int stop) {
    int i = start;         // Index for the left subarray
    int j = middle + 1;    // Index for the right subarray
    int k = 0;             // Index for the temporary array
    int[] tempArr = new int[stop - start + 1]; // Temporary array to hold sorted elements

    // Merge the two subarrays into tempArr
    while (i <= middle && j <= stop) {
        if (a.get(i) <= a.get(j)) {
            tempArr[k++] = a.get(i++);
        } else {
            tempArr[k++] = a.get(j++);
        }
    }

    // Copy remaining elements from the left subarray
    while (i <= middle) {
        tempArr[k++] = a.get(i++);
    }

    // Copy remaining elements from the right subarray
    while (j <= stop) {
        tempArr[k++] = a.get(j++);
    }

    // Copy sorted elements back to the original ArrayList
    for (int l = 0; l < tempArr.length; l++) {
        a.set(start + l, tempArr[l]);
    }
}

// MergeSort Method
private void MergeSort(int start, int stop) {
    if (start < stop) {
        int middle = (start + stop) / 2;

        // Recursively sort the left and right subarrays
        MergeSort(start, middle);
        MergeSort(middle + 1, stop);

        // Merge the sorted subarrays
        Merge(start, middle, stop);
    }
}

// Public MergeSort Wrapper Method
public void MergeSort() {
    MergeSort(0, a.size() - 1);
}


    // Main
    public static void main(String[] args) {
        int[] ns = {2500, 5000, 10000, 20000, 40000, 80000, 160000, 320000};

        for (int size : ns) {
            System.out.println("Testing with n = " + size);
            SortingLab lab = new SortingLab(size);

            double totalTime = 0;
            for (int c = 0; c < 10; c++) {
                lab.resetList(); // Reset the list for each run

                // Measure execution time
                long start = System.nanoTime();
                lab.MergeSort(); // Change to test other algorithms
                long end = System.nanoTime();

                totalTime += (end - start) / 1_000_000_000.0; // Time in seconds
            }

            double averageTime = totalTime / 10.0;
            System.out.println("Average time: " + averageTime + " seconds\n");
        }
    }
}
