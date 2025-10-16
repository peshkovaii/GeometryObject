
public abstract class GeometryObject {
    public abstract double area();

    public abstract void move(double dx, double dy);

    public abstract boolean intersects(GeometryObject other);

    public abstract GeometryObject union(GeometryObject other);

    @Override
    public abstract String toString();
}
