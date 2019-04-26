package main;

import engine.*;

import extensions.Vector2d;
import java.awt.*;
import java.util.List;

public class Animal extends AnimationAgent {
    public Vector2d position = new Vector2d(0.0, 0.0);
    Color color = Color.black;

    @Override
    protected void setup() {
        super.setup();

        generatePosition();
        addBehaviour(new AnimalMovementController());
        addBehaviour(new VisionController());
    }

    private void generatePosition() {
        Vector2d mapSize = Viewport.size;
        double x = Math.random() * mapSize.x;
        double y = Math.random() * mapSize.y;
        position = new Vector2d(x, y);
    }

    public void paint(Graphics g) {
        g.setColor(color);

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }

    class AnimalMovementController extends MonoBehaviour {
        private double moveSpeed = 3.0;
        private double runSpeed = 6.0;

        Vector2d direction = new Vector2d(1.0, 1.0);
        boolean isIdle = true;

        @Override
        public void action() {
            SetDirection();
            Move();
        }

        private void Move() {
            synchronized (this) {
                double speed = isIdle ? moveSpeed : runSpeed;
                Vector2d dir = direction.normalized();
                double deltaTime = Time.getDeltaTime();
                Vector2d move = dir.scaled(deltaTime).scaled(speed);
                position.add(move);
                List<Hare> hares = Utils.findAgentsOfType(Hare.class);
                if (hares.size() > 0) {
                    if (hares.get(0) == Animal.this) {
                        System.out.println(position);
                    }
                }
            }
        }

        protected void SetDirection() {
            //...
        }
    }

    class VisionController extends MonoBehaviour {
        double maxDist = 100;

        @Override
        public void action() {
            List<Hare> hares = Utils.findAgentsOfType(Hare.class);
            for(Hare hare : hares) {
                if (hare == Animal.this) {
                    continue;
                }
                if (Vector2d.distance(position, hare.position) < maxDist) {
                    Debug.drawLine(Viewport.worldToScreenPoint(position), Viewport.worldToScreenPoint(hare.position), Color.red, 0.1);
                }
            }
        }
    }
}
