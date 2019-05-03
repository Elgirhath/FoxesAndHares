package main;

import engine.*;

import extensions.Vector2d;
import graphics.VisionCone;

import java.awt.*;
import java.util.List;

public class Animal extends AnimationAgent {
    Color color = Color.black;
    Vector2d direction = new Vector2d(1.0, 1.0);

    @Override
    protected void setup() {
        super.setup();

        generatePosition();
        addBehaviour(new AnimalMovementController());
        addBehaviour(new VisionController());
    }

    private void generatePosition() {
        Vector2d mapSize = Viewport.getSize();
        double x = Math.random() * mapSize.x;
        double y = Math.random() * mapSize.y;
        position = new Vector2d(x, y);
    }

    public void paintComponent(Graphics g) {
        g.setColor(color);
        int radius = 5;

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2*radius, 2*radius);
    }

    class AnimalMovementController extends MonoBehaviour {
        private double moveSpeed = 50.0;
        private double runSpeed = 100.0;
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
//                        System.out.println(hares.get(0).getName() + " -- " + position);
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
        VisionCone cone = new VisionCone(position, direction, maxDist, 90);

        VisionController() {
            SimulationPanel.getInstance().addComponent(cone);
        }

        @Override
        public void action() {
            List<Hare> hares = Utils.findAgentsOfType(Hare.class);
            cone.position = position;
            cone.direction = direction;
            for(Hare hare : hares) {
                if (hare == Animal.this) {
                    continue;
                }
                if (Vector2d.distance(position, hare.position) < maxDist) {
                    Debug.drawLine(position, hare.position, Color.red, Time.getDeltaTime());
                }
            }
        }
    }
}
