package shapes;

public abstract class Shape implements Comparable<Shape> {
    protected double height;  // Common attribute: height

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

    // Implement Comparable to compare shapes by height
    @Override
    public int compareTo(Shape other) {
        // Compare in descending order by Height
        return Double.compare(other.height, this.height);
    }

    // Override toString for easy printing in the desired format
    @Override
    public String toString() {
        return "The polygon." + this.getClass().getSimpleName() + " has a Volume of: " + calcVolume();
    }
}
