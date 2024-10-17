package utilities;

import shapes.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    // Reads shapes from a resource file in the source package
    public static Shape[] readShapesFromFile(String fileName) throws IOException {
        List<Shape> shapeList = new ArrayList<>();
        
        // Load file from classpath using getResourceAsStream
        InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("File not found: " + fileName);
        }
        
        // Read the file and parse shape data
        try (Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNextLine()) {
                int numShapes = Integer.parseInt(scanner.nextLine().trim());
                for (int i = 0; i < numShapes; i++) {
                    if (scanner.hasNextLine()) {
                        String[] shapeData = scanner.nextLine().trim().split(" ");
                        try {
                            shapeList.add(createShape(shapeData));
                        } catch (Exception e) {
                            System.out.println("Error creating shape: " + e.getMessage());
                        }
                    }
                }
            }
        }

        return shapeList.toArray(new Shape[0]);
    }

    // Creates a Shape object based on the provided shape data
    private static Shape createShape(String[] shapeData) {
        if (shapeData.length < 3) {
            throw new IllegalArgumentException("Insufficient shape data.");
        }

        String shapeType = shapeData[0];
        double height = Double.parseDouble(shapeData[1]);
        double value = Double.parseDouble(shapeData[2]);

        switch (shapeType) {
            case "Cylinder":
                return new Cylinder(height, value);
                
            case "Cone":
                return new Cone(height, value);
                
            case "Pyramid":
                return new Pyramid(height, value);
            
            case "SquarePrism":
                return new SquarePrism(height, value);
            
            case "TriangularPrism":
                return new TriangularPrism(height, value);
            
            case "PentagonalPrism":
                return new PentagonalPrism(height, value);
            
            case "OctagonalPrism":
                return new OctagonalPrism(height, value);
                
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }
}
