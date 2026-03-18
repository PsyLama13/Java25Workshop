package exercises.e01;

public class Triangle extends Shape {
    private final double base, height;
    public Triangle(double base, double height) { this.base = base; this.height = height; }
    public double getBase()   { return base; }
    public double getHeight() { return height; }
    @Override public double area() { return 0.5 * base * height; }
}
