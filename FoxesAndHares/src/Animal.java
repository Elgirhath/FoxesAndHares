import jade.core.Agent;

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
        Vector2d mapSize = Scene.getInstance().size;
        double x = Math.random() * mapSize.x;
        double y = Math.random() * mapSize.y;
        position = new Vector2d(x, y);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);

        Vector2d screenPos = Camera.worldToScreenPoint(position);
        g.fillOval((int) screenPos.x, (int) screenPos.y, 10, 10);
    }
}
