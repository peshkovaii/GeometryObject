package geometry;

public interface GeometryObject {

    double area();

    void move(double dx, double dy);

    boolean intersects(GeometryObject other);

    GeometryObject union(GeometryObject other);

    String toString();
}