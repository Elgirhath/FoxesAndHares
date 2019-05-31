package main;

import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;

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

    private void Eat() {
        prey.Die();
        prey = null;
        isIdle = true;
    }

    class AnimalMovementController extends Animal.AnimalMovementController {

        @Override
        public void action() {
            super.action();
            if(prey != null) {
                Vector2d distanceVector = new Vector2d(position.x - prey.position.x, position.y - prey.position.y);
                if(distanceVector.length() < maxEatDistance) {
                    Eat();
                }
            }
        }
    }
}