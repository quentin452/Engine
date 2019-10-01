package nullengine.client.gui.shape;

import nullengine.client.gui.rendering.ComponentRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon extends Shape {

    private final List<Path.Point> points = new ArrayList<>();

    public Polygon() {

    }

    public List<Path.Point> getPoints() {
        return points;
    }

    public static Polygon fromPath(Path path) {
        return new Polygon();
    }

    @Override
    public float prefWidth() {
        return 0;
    }

    @Override
    public float prefHeight() {
        return 0;
    }

    @Override
    protected ComponentRenderer createDefaultRenderer() {
        return null;
    }
}
