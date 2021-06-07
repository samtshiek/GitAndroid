package HW1;

public class Rectangle implements IShape {

    private int length;
    private int breadth;

    public Rectangle(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    public String getArea() {
        return "Area: " + (length * breadth) + ", Length: " + length + ", Breadth: " + breadth;
    }
}
