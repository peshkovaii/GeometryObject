package geometry;

public class GeometryUtils {

    //векторное произведение - cross product
    //(Cx - Ax) * (By - Ay) - (Cy - Ay) * (Bx - Ax)

    private static double crossProduct(Point a, Point b, Point c) {
        return (c.getX() - a.getX()) * (b.getY() - a.getY()) - (c.getY() - a.getY()) * (b.getX() - a.getX());
    }

    public static boolean doLineIntersect(Line l1, Line l2) {
        Point p1 = l1.getStart();
        Point p2 = l1.getEnd();
        Point p3 = l2.getStart();
        Point p4 = l2.getEnd();

        double d1 = crossProduct(p3, p4, p1);
        double d2 = crossProduct(p3, p4, p2);
        double d3 = crossProduct(p1, p2, p3);
        double d4 = crossProduct(p1, p2, p4);

        if ((d1 < 0 && d2 > 0) || (d1 > 0 && d2 < 0) || (d3 < 0 && d4 > 0) || (d3 > 0 && d4 < 0)) {
            return true;
        }

        return false;
    }

    public static boolean doRectanglesIntersect(Rectangle r1, Rectangle r2) {
        return r1.getTopLeft().getX() < r2.getTopLeft().getX() + r2.getWidth() &&
                r1.getTopLeft().getX() + r1.getWidth() > r2.getTopLeft().getX() &&
                r1.getTopLeft().getY() < r2.getTopLeft().getY() + r2.getHeight() &&
                r1.getTopLeft().getY() + r1.getHeight() > r2.getTopLeft().getY();
    }

    public static boolean doesLineIntersectRectangle(Line line, Rectangle rect) {
        Line top = new Line(rect.getTopLeft(), rect.getTopRight());
        Line bottom = new Line(rect.getBottomLeft(), rect.getBottomRight());
        Line left = new Line(rect.getTopLeft(), rect.getBottomLeft());
        Line right = new Line(rect.getTopRight(), rect.getBottomRight());

        return doLineIntersect(line, top) ||
                doLineIntersect(line, bottom) ||
                doLineIntersect(line, left) ||
                doLineIntersect(line, right);
    }

    public static Rectangle unionRectangles(Rectangle r1, Rectangle r2) {
        double minX = Math.min(r1.getTopLeft().getX(), r2.getTopLeft().getX());
        double minY = Math.min(r1.getTopLeft().getY(), r2.getTopLeft().getY());
        double maxX = Math.max(r1.getTopLeft().getX() + r1.getWidth(),
                r2.getTopLeft().getX() + r2.getWidth());
        double maxY = Math.max(r1.getTopLeft().getY() + r1.getHeight(),
                r2.getTopLeft().getY() + r2.getHeight());

        double newWidth = maxX - minX;
        double newHeight = maxY - minY;

        return new Rectangle(new Point(minX, minY), newWidth, newHeight);
    }

    public static GeometryObject unionLines(Line l1, Line l2) {
        if (!doLineIntersect(l1, l2)) {
            return null;
        }

        Point[] points = {
                l1.getStart(), l1.getEnd(), l2.getStart(), l2.getEnd()
        };

        double minX = points[0].getX();
        double maxX = points[0].getX();
        double minY = points[0].getY();
        double maxY = points[0].getY();

        for (Point p : points) {
            minX = Math.min(minX, p.getX());
            maxX = Math.max(maxX, p.getX());
            minY = Math.min(minY, p.getY());
            maxY = Math.max(maxY, p.getY());
        }

        return new Line(new Point(minX, minY), new Point(maxX, maxY));
    }
}
