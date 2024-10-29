package appDomain;

import shapes.*;
import utilities.*;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class AppDriver {

    public static void main(String[] args) {
        String fileName = null;
        String compareType = null;
        String sortTypeAlgorithm = null;
        
        String[] myArgs = new String[3];

        String fileInput;
        String compareInput;
        String sortInput;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the file name:");
            fileInput = scanner.nextLine();

            System.out.println("Enter the compare type:");
            compareInput = scanner.nextLine();

            System.out.println("Enter the sort type:");
            sortInput = scanner.nextLine();
        } while (fileInput.isBlank() || compareInput.isBlank() || sortInput.isBlank());
        myArgs[0] = "-f" + fileInput.toLowerCase();
        myArgs[1] = "-t" + compareInput.toLowerCase();
        myArgs[2] = "-s" + sortInput.toLowerCase();

        
        // Parse the command-line arguments
        for (int i = 0; i < myArgs.length; i++) {
            if (myArgs[i].length() < 2) {
                System.out.println("Unknown argument: " + myArgs[i]);
                return;
            }

            String arg = myArgs[i].toLowerCase();
            switch (arg.charAt(1)) {
                case 'f':
                    if (myArgs[i].length() > 2) {
                        fileName = myArgs[i].substring(2);
                    } else if (i + 1 < myArgs.length) {
                        fileName = myArgs[i + 1];
                        i++;
                    }
                    break;
                case 't':
                    compareType = myArgs[i].substring(2).toLowerCase();
                    break;
                case 's':
                    sortTypeAlgorithm = myArgs[i].substring(2).toLowerCase();
                    break;
                default:
                    System.out.println("Unknown argument: " + myArgs[i]);
                    return;
            }
        }

        // Validate that required arguments are provided
        if (fileName == null || compareType == null || sortTypeAlgorithm == null) {
            System.out.println("Invalid input! You must provide the following:");
            System.out.println("- File with -f (e.g., -fshapes1.txt or -f shapes1.txt)");
            System.out.println("- Compare type with -t (v for volume, a for area, h for height)");
            System.out.println("- Sorting algorithm with -s (b for bubble, i for insertion, q for quick, m for merge, s for selection, h for heap sort)");
            return;
        }

        // Print the absolute file path
        try {
            File file = new File(fileName);
            System.out.println("Absolute path of file: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error resolving file path: " + e.getMessage());
            return;
        }

        // Print sorting information based on arguments
        printSortingInfo(compareType, sortTypeAlgorithm);

        // Attempt to read file and sort shapes
        try {
            Shape[] shapes = FileReader.readShapesFromFile(fileName);
            if (shapes.length == 0) {
                System.out.println("No shapes found in the file.");
                return;
            }

            Comparator<Shape> comparator = getComparator(compareType);
            if (comparator == null) {
                System.out.println("Invalid compare type: " + compareType);
                return;
            }

            long startTime = System.currentTimeMillis();
            switch (sortTypeAlgorithm) {
                case "b":
                    Sorter.bubbleSort(shapes, comparator);
                    break;
                case "i":
                    Sorter.insertionSort(shapes, comparator);
                    break;
                case "q":
                    Sorter.quickSort(shapes, comparator);
                    break;
                case "m":
                    Sorter.mergeSort(shapes, comparator);
                    break;
                case "s":
                    Sorter.selectionSort(shapes, comparator);
                    break;
                case "h":
                    Sorter.heapSort(shapes, comparator);
                    break;
                default:
                    System.out.println("Unknown sorting algorithm: " + sortTypeAlgorithm);
                    return;
            }
            long endTime = System.currentTimeMillis();

            printResults(shapes, compareType);
            System.out.println(); // Blank Space
            System.out.println("Run time was: " + (endTime - startTime) + " milliseconds");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to print sorting information based on the given arguments
    private static void printSortingInfo(String compareType, String sortTypeAlgorithm) {
        String compareMethod;
        switch (compareType) {
            case "v":
                compareMethod = "Volume";
                break;
            case "a":
                compareMethod = "Base Area";
                break;
            case "h":
                compareMethod = "Height";
                break;
            default:
                compareMethod = "Unknown";
                break;
        }

        String sortingMethod;
        switch (sortTypeAlgorithm) {
            case "b":
                sortingMethod = "Bubble Sort";
                break;
            case "i":
                sortingMethod = "Insertion Sort";
                break;
            case "q":
                sortingMethod = "Quick Sort";
                break;
            case "m":
                sortingMethod = "Merge Sort";
                break;
            case "s":
                sortingMethod = "Selection Sort";
                break;
            case "h":
                sortingMethod = "Heap Sort";
                break;
            default:
                sortingMethod = "Unknown";
                break;
        }

        System.out.println("Compare method: " + compareMethod);
        System.out.println("Sorting method: " + sortingMethod);
        System.out.println(); // Blank Space
    }

    // Method to get the appropriate comparator based on compare type
    private static Comparator<Shape> getComparator(String compareType) {
        switch (compareType) {
            case "v":
                return new VolumeComparator(); // Sort by volume (Descending order)
            case "a":
                return new BaseAreaComparator(); // Sort by base area (Descending order)
            case "h":
                return new Comparator<Shape>() {
                    @Override
                    public int compare(Shape shape1, Shape shape2) {
                        return Double.compare(shape2.getHeight(), shape1.getHeight()); // Sort by height
                    }
                };
            default:
                return null; // Default to null for invalid compare type
        }
    }

    // Method to print the sorted shapes: first, every 1000th, and last shape
    private static void printResults(Shape[] shapes, String compareType) {
        if (shapes.length == 0) {
            System.out.println("No shapes to print.");
            return;
        }

        System.out.println("First element is: " + shapes[0].formatShape(compareType));

        for (int i = 999; i < shapes.length; i += 1000) {
            System.out.println((i + 1) + "-th element is: " + shapes[i].formatShape(compareType));
        }

        System.out.println("Last element is: " + shapes[shapes.length - 1].formatShape(compareType));
    }
}
