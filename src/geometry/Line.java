package geometry;

public class Line extends GeometryObject {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public double length() {
        return start.distanceTo(end);
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public void move(double dx, double dy) {
        start.setX(start.getX() + dx);
        start.setY(start.getY() + dy);
        end.setX(end.getX() + dx);
        end.setY(end.getY() + dy);

    }

    @Override
    public String toString() {
        return "LineSegment{start=" + start + ", end=" + end + "}";
    }

    @Override
    public boolean intersects(GeometryObject other) {
        if (other instanceof Line) {
            Line otherSegment = (Line) other;
            return GeometryUtils.doLineIntersect(this, otherSegment);
        } else if (other instanceof Rectangle) {
            Rectangle rect = (Rectangle) other;
            return GeometryUtils.doesLineIntersectRectangle(this, rect);
        }
        return false;
    }

    @Override
    public GeometryObject union(GeometryObject other) {
        if (other instanceof Line) {
            return GeometryUtils.unionLines(this, (Line) other);
        }
        return null;
    }
}
