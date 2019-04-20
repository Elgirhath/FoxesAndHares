package extensions;

import java.awt.*;

public class Vector2d extends javax.vecmath.Vector2d {
    public Vector2d() {
        super();
    }

    public Vector2d(double x, double y) {
        super(x, y);
    }

    public Vector2d(Dimension dim) {
        x = (double) dim.width;
        y = (double) dim.height;
    }

    public Vector2d(javax.vecmath.Vector2d v) {
        super(v);
    }

    public Vector2d(javax.vecmath.Vector2f v) {
        super(v);
    }

    public Vector2d(double[] v) {
        super(v);
    }

    public Vector2d multiply(Vector2d vec) {
        return new Vector2d(x * vec.x, y * vec.y);
    }

    public Vector2d divide(Vector2d vec) {
        return new Vector2d(x / vec.x, y / vec.y);
    }

    public Dimension toDimension() {
        return new Dimension((int) x, (int) y);
    }
}
