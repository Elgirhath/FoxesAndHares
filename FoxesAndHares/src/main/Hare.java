package main;

import engine.AnimationAgent;
import engine.Debug;
import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Hare extends Animal {
    private Fox predator;
    protected boolean isChased = false;
    protected double detectionDistance = 3.5; // Could be changed depending on hare's age or state
    AnimalMovementController movementController = new AnimalMovementController();

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(movementController);

        predator = null;
        color = Color.green;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        int radius = 5;

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2*radius, 2*radius);
    }

    class AnimalMovementController extends Animal.AnimalMovementController {
        @Override
        protected void Rush() {
            if(predator != null)
                Run();
        }

        private void Run() {
            direction = getOptimalRunDirection();
            Debug.drawRay(position, direction, Color.MAGENTA, Time.getDeltaTime());
        }

        private Vector2d getOptimalRunDirection() {
            List<Animal> animals = visionController.getVisible();
            List<Animal> foxes = animals.stream()
                    .filter(s -> s instanceof Fox)
                    .collect(Collectors.toList());

            Vector2d foxesMassCenter = Vector2d.zero();
            for (Animal fox : foxes) {
                foxesMassCenter.add(fox.position.scaled(1.0/foxes.size()));
            }

            return position.minus(foxesMassCenter).normalized();
        }

        @Override
        protected void SetIdle() {
            if(isChased) {
                isIdle = false;
                return;
            }
            if(visionController.getVisible().isEmpty())
                isIdle = true;
            else
                SetPredator();
        }

        private void SetPredator() {
            for(Animal animal : visionController.getVisible()) {
                if(animal instanceof Fox) {
                    predator = (Fox)animal;
                    isIdle = false;
                    return;
                }
            }
            isIdle = true;
        }

        protected void SetPredator(Fox predator) {
            Hare.this.predator = predator;
        }
    }
}
