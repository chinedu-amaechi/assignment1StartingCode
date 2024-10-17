
package shapes;

public abstract class Prism extends Shape {
    protected double side;  // Side length for prisms

    // Constructor
    public Prism(double height, double side) {
        super(height);
        this.side = side;
    }

    // Getter for side
    public double getSide() {
        return side;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
