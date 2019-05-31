package main;

import engine.Debug;
import engine.MonoBehaviour;
import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Hare extends Animal {
    HareMovement movementController = new HareMovement();

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(movementController);
        color = Color.green;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        int radius = 5;

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2*radius, 2*radius);
    }


    public class HareMovement extends Animal.AnimalMovementController {
        private Hare target = Hare.this;

        private double moveSpeed = 2.0;
        private double runSpeed = 4.0;

        @Override
        public void action() {
            List<Animal> visible = getVisibleFoxes();
            if (visible.size() == 0) {
                isIdle = true;
            } else {
                direction = getOptimalRunDirection();
                isIdle = false;
            }
            move();
        }

        private void idle() {}

        private void move() {
            synchronized (this) {
                double speed = target.isIdle ? moveSpeed : runSpeed;
                Vector2d dir = direction.normalized();
                double deltaTime = Time.getDeltaTime();
                Vector2d move = dir.scaled(deltaTime).scaled(speed);
                position.add(move);
            }
        }

        private Vector2d getOptimalRunDirection() {
            List<Animal> animals = visionController.getVisible();
            List<Animal> foxes = animals.stream()
                    .filter(s -> s instanceof Fox)
                    .collect(Collectors.toList());

            Vector2d foxesMassCenter = Vector2d.zero();
            for (Animal fox : foxes) {
                foxesMassCenter.add(fox.position.scaled(1.0/foxes.size()));
                Debug.drawRay(fox.position, Vector2d.up().scaled(3), Color.MAGENTA, 3.0);
            }

            return target.position.minus(foxesMassCenter).normalized();
        }

        private List<Animal> getVisibleFoxes() {
            List<Animal> animals = visionController.getVisible();
            List<Animal> foxes = animals.stream()
                    .filter(s -> s instanceof Fox)
                    .collect(Collectors.toList());
            return foxes;
        }
    }

}
