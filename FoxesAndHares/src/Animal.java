import engine.AnimationAgent;
import engine.Viewport;

import extensions.Vector2d;
import java.awt.*;

public class Animal extends AnimationAgent {
    public Vector2d position;

    @Override
    protected void setup() {
        super.setup();

        generatePosition();

        System.out.println("An animal has been created at position: " + position);
    }

    private void generatePosition() {
        Vector2d mapSize = Viewport.size;
        double x = Math.random() * mapSize.x;
        double y = Math.random() * mapSize.y;
        position = new Vector2d(x, y);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);

        Dimension screenPos = Viewport.worldToScreenPoint(position);
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }
}
