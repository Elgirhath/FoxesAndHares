import javax.vecmath.Vector2d;
import java.awt.*;

public class Camera {
    private static Camera instance = null;

    public static Camera getInstance() {
        if (instance == null)
            instance = new Camera();

        return instance;
    }

    public static Vector2d getResolution() {
        Dimension dim = SimulationPanel.getInstance().getSize();
        return new Vector2d(dim.width, dim.height);
    }

    public static Vector2d worldToScreenPoint(Vector2d point) {
        double x = (point.x / Scene.getInstance().size.x) * getResolution().x;
        double y = (point.y / Scene.getInstance().size.y) * getResolution().y;
        Vector2d screenPoint = new Vector2d(x, y);
        System.out.println(screenPoint);
        return screenPoint;
    }
}
