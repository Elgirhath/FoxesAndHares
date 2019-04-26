package engine;

import extensions.Vector2d;
import java.awt.*;

public class Viewport {
    public static Vector2d size = new Vector2d(1600.0, 900.0);

    public static Vector2d worldToScreenPoint(Vector2d point) {
        SimulationPanel panel = SimulationPanel.getInstance();
        Vector2d panelSize = new Vector2d(panel.getSize());
        Vector2d vec = point.divide(size).multiply(panelSize);
        return vec;
    }
}