public class Rectangle extends GeometryObject {
    private Point topLeft;
    private double width, height;

    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Point getBottomLeft() {
        return new Point(topLeft.getX(), topLeft.getY() + height);
    }

    public Point getTopRight() {
        return new Point(topLeft.getX() + width, topLeft.getY());
    }

    public Point getBottomRight() {
        return new Point(topLeft.getX() + width, topLeft.getY() + height);
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public void move(double dx, double dy) {
        topLeft.setX(topLeft.getX() + dx);
        topLeft.setY(topLeft.getY() + dy);
    }

    @Override
    public String toString() {
        return String.format("Rectangle[topLeft=%s, width=%.1f, height=%.1f]", topLeft, width, height);
    }

    @Override
    public boolean intersects(GeometryObject other) {
        if (other instanceof Rectangle) {
            Rectangle otherRect = (Rectangle) other;
            return GeometryUtils.doRectanglesIntersect(this, otherRect);
        } else if (other instanceof Line) {
            Line line = (Line) other;
            return GeometryUtils.doesLineIntersectRectangle(line, this);
        }
        return false;
    }

    @Override
    public GeometryObject union(GeometryObject other) {
        if (other instanceof Rectangle) {
            return GeometryUtils.unionRectangles(this, (Rectangle) other);
        }
        return null;
    }
}