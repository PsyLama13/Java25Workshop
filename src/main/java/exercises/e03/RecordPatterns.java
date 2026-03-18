package exercises.e03;

public class RecordPatterns {

    public static String describePoint(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            int x = p.x();
            int y = p.y();
            if (x == 0 && y == 0) { return "Ursprung"; }
            else if (y == 0)       { return "Auf der X-Achse bei x=" + x; }
            else if (x == 0)       { return "Auf der Y-Achse bei y=" + y; }
            else                   { return "Punkt bei (" + x + ", " + y + ")"; }
        }
        return "Kein Punkt";
    }

    public static double lineLength(Object obj) {
        if (obj instanceof Line) {
            Line line = (Line) obj;
            Point start = line.start();
            Point end   = line.end();
            int dx = end.x() - start.x();
            int dy = end.y() - start.y();
            return Math.sqrt(dx * dx + dy * dy);
        }
        return -1;
    }

    public static String describeColoredPoint(Object obj) {
        if (obj instanceof ColoredPoint) {
            ColoredPoint cp = (ColoredPoint) obj;
            Point p  = cp.point();
            String color = cp.color();
            int x = p.x();
            int y = p.y();
            return color + " Punkt bei (" + x + ", " + y + ")";
        }
        return "Kein farbiger Punkt";
    }

    public static String classifyBoundingBox(BoundingBox box) {
        Point tl = box.topLeft();
        Point br = box.bottomRight();

        if (tl.x() == br.x() && tl.y() == br.y()) {
            return "Punkt bei (" + tl.x() + ", " + tl.y() + ")";
        } else if (tl.y() == br.y()) {
            return "Horizontale Linie von x=" + tl.x() + " bis x=" + br.x();
        } else if (tl.x() == br.x()) {
            return "Vertikale Linie von y=" + tl.y() + " bis y=" + br.y();
        } else {
            return "Rechteck " + (br.x() - tl.x()) + "x" + (br.y() - tl.y());
        }
    }
}
