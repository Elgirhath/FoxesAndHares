package main;

import engine.Viewport;

import java.awt.*;

public class Fox extends Animal{
    private Hare prey;

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new AnimalMovementController());

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

    class AnimalMovementController extends Animal.AnimalMovementController {
        @Override
        protected void Rush() {
            Chase();
        }

        private void Chase() {
            direction.setX(prey.position.x - Fox.this.position.x);
            direction.setY(prey.position.y - Fox.this.position.y);
        }

        @Override
        protected void setIdle(boolean state, Animal target) {
            super.setIdle(state, target);
            prey = (Hare)target;
        }
    }
}