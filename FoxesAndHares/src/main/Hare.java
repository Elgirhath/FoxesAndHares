package main;

import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.Math;

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
        private double pathFindThreshold = 4.0;

        List<Animal> visibleFoxes = new ArrayList<>();

        private double moveSpeed = 2.0;
        private double runSpeed = 4.0;

        @Override
        public void action() {
            visibleFoxes = getVisibleFoxes();
            setDirection();
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

        private void setDirection() {
            if (visibleFoxes.size() == 0) {
                direction = getIdleDirection();
                isIdle = true;
            } else {
                direction = getOptimalRunDirection();
                isIdle = false;
            }
        }

        private Vector2d getIdleDirection() {
            return getOptimalRunDirection();
        }

        private Vector2d getOptimalRunDirection() {
            double epsilon = 1.0; //degrees
            double bestNorm = 0.0;
            Vector2d bestDir = null;

            double startAngle = 360 - (Math.toDegrees(Math.atan2(direction.y, direction.x)) - 90);

            for (double phi = 0.0; phi < 360.0; phi += epsilon) {
                double phiRad = Math.toRadians(phi + startAngle % 360.0);
                Vector2d dir = new Vector2d(Math.sin(phiRad), Math.cos(phiRad));
                Vector2d move = dir.scaled(pathFindThreshold);
                Vector2d pos = position.plus(move);

                if (!Viewport.isInView(pos)) {
                    continue;
                }

                double norm = getDistanceNorm(pos);
                if (bestDir == null || norm > bestNorm) {
                    bestNorm = norm;
                    bestDir = dir;
                }
            }

            return bestDir;
        }

        private double getDistanceNorm(Vector2d pos) {
            double sum = 0;
            for (Animal fox : visibleFoxes) {
                sum += Vector2d.distance(fox.position, pos);
            }
            return sum;
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
