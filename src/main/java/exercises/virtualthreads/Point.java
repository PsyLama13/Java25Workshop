package ch.irix.jdk25test.devmeeting.virtualthreads;

public record Point (double x, double y) {

    public Point(Point to) {
        this(to.x(), to.y());
    }

}
