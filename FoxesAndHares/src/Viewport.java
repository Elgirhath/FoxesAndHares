import javax.vecmath.Vector2d;
import java.awt.*;

public class Viewport {
    public static Vector2d size = new Vector2d(1000.0, 1000.0);

    public static Dimension worldToScreenPoint(Vector2d point) {
        SimulationPanel panel = SimulationPanel.getInstance();
        double x = (point.x / size.x) * panel.getSize().width;
        double y = (point.y / size.y) * panel.getSize().height;
        Dimension screenPoint = new Dimension((int) x, (int) y);
        return screenPoint;
    }
}
