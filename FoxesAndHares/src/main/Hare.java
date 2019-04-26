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

        System.out.println("The hare has been created!");
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.green);

        Dimension screenPos = Viewport.worldToScreenPoint(position);
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }

    class HareMovementController extends Animal.AnimalMovementController {
        @Override
        protected void SetDirection() {
            if(Hare.this.isIdle) {
                // call a proper method
            }
            else {
                Run();
            }
        }

        private void Run() {
            Hare.this.direction.setX(Hare.this.position.getX() - chaser.position.getX());
            Hare.this.direction.setY(Hare.this.position.getY() - chaser.position.getY());
        }
    }
}
