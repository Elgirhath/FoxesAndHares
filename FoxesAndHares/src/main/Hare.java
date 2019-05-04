package main;

import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;

public class Hare extends Animal {
    private Fox predator;
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
            direction.setX(Hare.this.position.x - predator.position.x);
            direction.setY(Hare.this.position.y - predator.position.y);
        }

        @Override
        protected void SetIdle() {
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
    }
}
