package shapes;

public class Pyramid extends Shape {
    private double side;

    public Pyramid(double height, double side) {
        super(height);
        this.side = side;
    }

    @Override
    public double calcBaseArea() {
        return Math.pow(side, 2);
    }

    @Override
    public double calcVolume() {
        return (1.0 / 3.0) * calcBaseArea() * height;
    }
}