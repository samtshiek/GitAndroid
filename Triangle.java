package HW1;

public class Triangle implements IShape{
    private int side1;
    private int side2;
    private int side3;

    public Triangle(){

    }
    public Triangle(int side1, int side2, int side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    public int getSide1() {
        return side1;
    }

    public void setSide1(int side1) {
        this.side1 = side1;
    }

    public int getSide2() {
        return side2;
    }

    public void setSide2(int side2) {
        this.side2 = side2;
    }

    public int getSide3() {
        return side3;
    }

    public void setSide3(int side3) {
        this.side3 = side3;
    }

    public String getArea() {
        double s = (side1 + side2 + side3)/2; // Perimeter/2
        double Area = Math.sqrt(s*(s-side1)*(s-side2)*(s-side3));
        return "Area: " + Area;
    }

    public String getPerimeter() {
        return "Perimeter: " + (side1 + side2 + side3);
    }

}
