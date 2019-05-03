package graphics;

import engine.GraphicComponent;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;

public class VisionCone implements GraphicComponent {
    public Vector2d position;
    public Vector2d direction;
    double size;
    int fov;

    public VisionCone(Vector2d position, Vector2d direction, double size, int fov) {
        this.position = position;
        this.direction = direction;
        this.size = size*2;
        this.fov = fov;
    }

    @Override
    public void paintComponent(Graphics g) {
        double dirAngle = direction.angle(Vector2d.right());
        dirAngle = (int) Math.toDegrees(dirAngle);

        Dimension screenSize = Viewport.worldToScreenPoint(new Vector2d(size, size)).toDimension();
        Dimension screenPos = Viewport.worldToScreenPoint(position).minus(new Vector2d(screenSize.width/2, screenSize.height/2)).toDimension();
        Color tmp = g.getColor();
        Color coneColor = new Color(0, 150, 255, 70);
        g.setColor(coneColor);
        g.fillArc(screenPos.width, screenPos.height, screenSize.width, screenSize.height, (int)-(dirAngle + fov/2), fov);
        g.setColor(tmp);
    }
}
