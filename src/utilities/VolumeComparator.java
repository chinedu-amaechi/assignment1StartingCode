package utilities;

import shapes.Shape;
import java.util.Comparator;

public class VolumeComparator implements Comparator<Shape> {

    @Override
    public int compare(Shape s1, Shape s2) {
        // Compare s2 to s1 for descending order
        return Double.compare(s2.calcVolume(), s1.calcVolume());
    }
}