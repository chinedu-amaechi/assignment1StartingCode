package utilities;

import shapes.Shape;
import java.util.Comparator;

public class Sorter {

    // Bubble Sort
    public static void bubbleSort(Shape[] shapes, Comparator<Shape> comparator) {
        int n = shapes.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(shapes[j], shapes[j + 1]) > 0) {
                    Shape temp = shapes[j];
                    shapes[j] = shapes[j + 1];
                    shapes[j + 1] = temp;
                }
            }
        }
    }

    // Insertion Sort
    public static void insertionSort(Shape[] shapes, Comparator<Shape> comparator) {
        for (int i = 1; i < shapes.length; i++) {
            Shape key = shapes[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(shapes[j], key) > 0) {
                shapes[j + 1] = shapes[j];
                j--;
            }
            shapes[j + 1] = key;
        }
    }

    // Selection Sort
    public static void selectionSort(Shape[] shapes, Comparator<Shape> comparator) {
        for (int i = 0; i < shapes.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < shapes.length; j++) {
                if (comparator.compare(shapes[j], shapes[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            Shape temp = shapes[minIndex];
            shapes[minIndex] = shapes[i];
            shapes[i] = temp;
        }
    }

    // Quick Sort
    public static void quickSort(Shape[] shapes, Comparator<Shape> comparator) {
        quickSort(shapes, 0, shapes.length - 1, comparator);
    }

    private static void quickSort(Shape[] shapes, int low, int high, Comparator<Shape> comparator) {
        if (low < high) {
            int pi = partition(shapes, low, high, comparator);
            quickSort(shapes, low, pi - 1, comparator);
            quickSort(shapes, pi + 1, high, comparator);
        }
    }

    private static int partition(Shape[] shapes, int low, int high, Comparator<Shape> comparator) {
        Shape pivot = shapes[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(shapes[j], pivot) <= 0) {
                i++;
                Shape temp = shapes[i];
                shapes[i] = shapes[j];
                shapes[j] = temp;
            }
        }
        Shape temp = shapes[i + 1];
        shapes[i + 1] = shapes[high];
        shapes[high] = temp;
        return i + 1;
    }

    // Merge Sort
    public static void mergeSort(Shape[] shapes, Comparator<Shape> comparator)
    {
        if (shapes.length > 1) {
            Shape[] left = new Shape[shapes.length / 2];
            Shape[] right = new Shape[shapes.length - shapes.length / 2];

            System.arraycopy(shapes, 0, left, 0, left.length);
            System.arraycopy(shapes, left.length, right, 0, right.length);

            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(shapes, left, right, comparator);
        }
    }

    private static void merge(Shape[] shapes, Shape[] left, Shape[] right, Comparator<Shape> comparator)
    {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                shapes[k++] = left[i++];
            } else {
                shapes[k++] = right[j++];
            }
        }
        while (i < left.length) {
            shapes[k++] = left[i++];
        }
        while (j < right.length) {
            shapes[k++] = right[j++];
        }
    }

    // Heap Sort (newly added)
    public static void heapSort(Shape[] shapes, Comparator<Shape> comparator) {
        int n = shapes.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(shapes, n, i, comparator);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            Shape temp = shapes[0];
            shapes[0] = shapes[i];
            shapes[i] = temp;

            // Call max heapify on the reduced heap
            heapify(shapes, i, 0, comparator);
        }
    }

    private static void heapify(Shape[] shapes, int n, int i, Comparator<Shape> comparator) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n && comparator.compare(shapes[left], shapes[largest]) > 0) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && comparator.compare(shapes[right], shapes[largest]) > 0) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            Shape swap = shapes[i];
            shapes[i] = shapes[largest];
            shapes[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(shapes, n, largest, comparator);
        }
    }
}
