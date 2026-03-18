package exercises.e01;

public class Rectangle extends Shape {
    private final double width, height;
    public Rectangle(double width, double height) { this.width = width; this.height = height; }
    public double getWidth()  { return width; }
    public double getHeight() { return height; }
    @Override public double area() { return width * height; }
}
