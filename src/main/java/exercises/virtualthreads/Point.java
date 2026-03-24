package exercises.virtualthreads;

public record Point (double x, double y) {

    public Point(Point to) {
        this(to.x(), to.y());
    }

}
