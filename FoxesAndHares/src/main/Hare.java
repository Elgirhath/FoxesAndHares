package main;

import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;

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
        private double shiftFrequency = Math.random() * 1.5 + 1.5;
        private double timeOfShift = 0.0;
        private boolean shifted = false;


        @Override
        protected void Rush() {
            if(predator != null)
                Run();
        }

        private void Run() {
            if (shifted) {
                if(Time.getTime() - timeOfShift < shiftFrequency + 2.0)
                    return;
                shiftFrequency = Math.random() * 1.5 + 1.5;
                timeOfShift = 0.0;
                shifted = false;
            }
            direction.setX(Hare.this.position.x - predator.position.x);
            direction.setY(Hare.this.position.y - predator.position.y);
            if(timeOfShift == 0.0) {
                timeOfShift = Time.getTime();
            }
            else if(Time.getTime() - timeOfShift > shiftFrequency) {
                System.out.println("XD");
                double rotationDegree = Math.random() * 65 + 45;
                if(Math.random() > 0.5)
                    rotationDegree *= -1;
                direction = direction.rotate(rotationDegree);
                shifted = true;
            }
        }

        @Override
        protected void SetIdle() {
            if(isChased) {
                isIdle = false;
                return;
            }
            if(visionController.GetVisible().isEmpty())
                isIdle = true;
            else
                SetPredator();
        }

        private void SetPredator() {
            for(Animal animal : visionController.GetVisible()) {
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
