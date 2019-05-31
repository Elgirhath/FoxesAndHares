package main;

import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Fox extends Animal{
    private double maxEatDistance = 0.1;
    private Hare prey;
    AnimalMovementController movementController = new AnimalMovementController();

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(movementController);

        prey = null;
        color = Color.orange;
    }


    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        int radius = 5;

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2*radius, 2*radius);
    }

    protected boolean setGender() {
        return true;
    }

    private void eatPrey() {
        prey.Die();
        prey = null;
        isIdle = true;
    }

    private void setPrey(Hare hare) {
        prey = hare;
        if (prey != null) {
            isIdle = false;
        }
    }

    private List<Animal> getVisibleHares() {
        List<Animal> animals = visionController.getVisible();
        List<Animal> hares = animals.stream()
                .filter(s -> s instanceof Hare)
                .collect(Collectors.toList());
        return hares;
    }

    class AnimalMovementController extends Animal.AnimalMovementController {
        double moveSpeed = 2.0;
        double runSpeed = 4.0;

        private void move() {
            synchronized (this) {
                double speed = isIdle ? moveSpeed : runSpeed;
                Vector2d dir = direction.normalized();
                double deltaTime = Time.getDeltaTime();
                Vector2d move = dir.scaled(deltaTime).scaled(speed);
                position.add(move);
            }
        }

        @Override
        public void action() {
            findPrey();
            setDirection();
            move();

            if (prey != null && Vector2d.distance(prey.position, position) < maxEatDistance) {
                eatPrey();
            }
        }

        private void setDirection() {
            if(prey != null) {
                Vector2d distanceVector = prey.position.minus(position);
                direction = distanceVector;
            }
            else {
                setIdleDirection();
            }
        }

        private void findPrey() {
            List<Animal> hares = getVisibleHares();

            double minDist = Double.POSITIVE_INFINITY;
            Animal closest = null;
            for (Animal hare : hares) {
                double distance = Vector2d.distance(hare.position, position);
                if (closest == null || distance < minDist) {
                    minDist = distance;
                    closest = hare;
                }
            }

            setPrey((Hare) closest);
        }
    }
}