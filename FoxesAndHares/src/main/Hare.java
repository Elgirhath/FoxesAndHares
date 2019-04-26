package main;

import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;

public class Hare extends Animal {
    private Fox chaser;

    @Override
    protected void setup() {
        super.setup();

        chaser = null;
        color = Color.green;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);

        Dimension screenPos = Viewport.worldToScreenPoint(position);
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }

    class HareMovementController extends Animal.AnimalMovementController {
        @Override
        protected void SetDirection() {
            if(isIdle) {
                // call a proper method
            }
            else {
                Run();
            }
        }

        private void Run() {
            direction.setX(Hare.this.position.getX() - chaser.position.getX());
            direction.setY(Hare.this.position.getY() - chaser.position.getY());
        }
    }
}
