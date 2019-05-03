package engine;

import extensions.Vector2d;

public class Viewport {
    public static double horizontalSize = 100.0;

    public static Vector2d worldToScreenPoint(Vector2d point) {
        SimulationPanel panel = SimulationPanel.getInstance();
        Vector2d panelSize = new Vector2d(panel.getSize());
        Vector2d vec = point.divide(getSize()).multiply(panelSize);
        return vec;
    }

    public static Vector2d screenToWorldPoint(Vector2d point) {
        SimulationPanel panel = SimulationPanel.getInstance();
        Vector2d panelSize = new Vector2d(panel.getSize());
        Vector2d vec = point.multiply(getSize()).divide(panelSize);
        return vec;
    }

    public static Vector2d getScreenSpaceSize() {
        return worldToScreenPoint(getSize());
    }

    public static double getVerticalSize() {
        SimulationPanel panel = SimulationPanel.getInstance();
        Vector2d panelSize = new Vector2d(panel.getSize());
        double aspectRatio = panelSize.getAspectRatio();
        return horizontalSize / aspectRatio;
    }

    public static Vector2d getSize() {
        return new Vector2d(horizontalSize, getVerticalSize());
    }
}