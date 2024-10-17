package appDomain;

import shapes.*;
import utilities.*;
import java.util.*;
import java.io.IOException;

public class AppDriver {

    public static void main(String[] args) {
        String fileName = null;
        String compareType = null;
        String sortTypeAlgorithm = null;

        // Parse the command-line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].length() < 2) {
                System.out.println("Unknown argument: " + args[i]);
                return;
            }

            // Convert the entire argument to lowercase for case-insensitive handling
            String arg = args[i].toLowerCase();

            switch (arg.charAt(1)) {
                case 'f': 
                    // Handle file name with or without spaces after the '-f' flag
                    if (args[i].length() > 2) {
                        fileName = args[i].substring(2); // e.g., -fshapes1.txt
                    } else if (i + 1 < args.length) {
                        fileName = args[i + 1]; // e.g., -f shapes1.txt
                        i++; // Skip the next argument since it's the file name
                    }
                    break;
                case 't': compareType = args[i].substring(2).toLowerCase(); break; // Sorting type
                case 's': sortTypeAlgorithm = args[i].substring(2).toLowerCase(); break; // Sorting algorithm
                default: 
                    System.out.println("Unknown argument: " + args[i]); 
                    return;
            }
        }

        // Validate that the required arguments are provided
        if (fileName == null || compareType == null || sortTypeAlgorithm == null) {
            System.out.println("Invalid input! You must provide the following:");
            System.out.println("- File with -f (e.g., -fshapes1.txt or -f shapes1.txt)");
            System.out.println("- Compare type with -t (v for volume, a for area, h for height)");
            System.out.println("- Sorting algorithm with -s (b for bubble, i for insertion, q for quick, m for merge, s for selection, h for heap sort)");
            return;
        }

        // Attempt to read the file and sort the shapes
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

            printResults(shapes);
            System.out.println("Run time was: " + (endTime - startTime) + " milliseconds");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to get the appropriate comparator based on compare type
    private static Comparator<Shape> getComparator(String compareType) {
        switch (compareType) {
            case "v": return new VolumeComparator(); // Sort by volume (Descending order)
            case "a": return new BaseAreaComparator(); // Sort by base area (Descending order)
            case "h": return Comparator.comparingDouble(Shape::getHeight).reversed(); // Sort by height
            default: return null; // Default to null for invalid compare type
        }
    }

    // Method to print the sorted shapes: first, every 1000th, and last shape
    private static void printResults(Shape[] shapes) {
        if (shapes.length == 0) {
            System.out.println("No shapes to print.");
            return;
        }

        // Print first element
        System.out.println("First element is: " + shapes[0]);

        // Print every 1000th element
        for (int i = 999; i < shapes.length; i += 1000) {
            System.out.println((i + 1) + "-th element is: " + shapes[i]);
        }

        // Print last element
        System.out.println("Last element is: " + shapes[shapes.length - 1]);
    }
}
