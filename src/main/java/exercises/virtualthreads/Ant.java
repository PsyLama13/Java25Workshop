package ch.irix.jdk25test.devmeeting.virtualthreads;

import java.awt.*;

public class Ant implements Runnable {

    public final Rectangle bounds;
    public Point from;
    public Point to;
    public final int rgbColor;
    public final int pauseInMillis;
    public final int movementLength;

    public Ant(Point position, Rectangle bounds) {
        this.from = position;
        this.to = position;
        this.bounds = bounds;
        rgbColor = (int) (Math.random() * 0xFFFFFF);
        pauseInMillis = (int) (Math.random() * 10) + 25;
        movementLength = (int) (Math.random() * 10) + 1;
    }

    @Override
    public void run() {

        while (true) {
            pause();
            moveRandom();
        }
    }

    public void moveRandom() {
        from = new Point(to);

        double dx = from.x() - (movementLength / 2d) + (Math.random() * (movementLength));
        double dy = from.y() - (movementLength / 2d) + (Math.random() * (movementLength));

        if (dx < bounds.x) {
            dx = bounds.x;
        }
        if (dx > bounds.x + bounds.width) {
            dx = bounds.x + bounds.width;
        }
        if (dy < bounds.y) {
            dy = bounds.y;
        }
        if (dy > bounds.y + bounds.height) {
            dy = bounds.y + bounds.height;
        }

        to = new Point(dx, dy);
    }

    public void pause(){
        try {
            Thread.sleep(pauseInMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
