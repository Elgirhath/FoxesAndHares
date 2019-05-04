package main;

import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;

public class Hare extends Animal {
    private Fox predator;

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new AnimalMovementController());

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
            Run();
        }

        private void Run() {
            direction.setX(Hare.this.position.x - predator.position.x);
            direction.setY(Hare.this.position.y - predator.position.y);
        }

        @Override
        protected void setIdle(boolean state, Animal target) {
            super.setIdle(state, target);
            predator = (Fox)target;
        }
    }
}
