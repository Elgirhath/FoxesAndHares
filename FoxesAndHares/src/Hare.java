import javax.vecmath.Vector2d;
import java.awt.*;

public class Hare extends Animal {
    @Override
    protected void setup() {
        super.setup();
        System.out.println("The hare has been created!");
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.green);

        Vector2d screenPos = Camera.worldToScreenPoint(position);
        g.fillOval((int) screenPos.x, (int) screenPos.y, 10, 10);
    }
}
