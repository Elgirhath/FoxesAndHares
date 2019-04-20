import jade.core.Agent;

import javax.swing.text.View;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animal extends Agent {
    public static List<Animal> animals = new ArrayList<>();
    public Vector2d position;

    @Override
    protected void setup() {
        generatePosition();
        animals.add(this);

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
