package shapes;

public abstract class Shape implements Comparable<Shape> {
    protected double height; // Common attribute: height

    // Constructor
    public Shape(double height) {
        this.height = height;
    }

    // Getter for height
    public double getHeight() {
        return height;
    }

    // Abstract methods for calculating base area and volume
    public abstract double calcBaseArea();
    public abstract double calcVolume();

    // Implement Comparable to compare shapes by height in descending order
    @Override
    public int compareTo(Shape other) {
        return Double.compare(other.height, this.height);
    }

    // Method to format the shape based on the comparison type
    public String formatShape(String compareType) {
        switch (compareType) {
            case "h":
                return "The polygon " + this.getClass().getSimpleName() + " has a Height of: " + getHeight();
            case "a":
                return "The polygon " + this.getClass().getSimpleName() + " has a Base Area of: " + calcBaseArea();
            case "v":
                return "The polygon " + this.getClass().getSimpleName() + " has a Volume of: " + calcVolume();
            default:
                return toString(); // Fallback to generic toString for unsupported compareType
        }
    }

    // Generic toString method as a fallback
    @Override
    public String toString() {
        return "The polygon " + this.getClass().getSimpleName() + " has a Volume of: " + calcVolume();
    }
}
