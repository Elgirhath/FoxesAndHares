package main;

import engine.*;

import extensions.Vector2d;
import java.awt.*;

public class Animal extends AnimationAgent {
    public Vector2d position;
    protected Vector2d direction = new Vector2d(1.0, 1.0);
    public boolean isIdle;

    @Override
    protected void setup() {
        super.setup();

        generatePosition();
        isIdle = true;
        addBehaviour(new AnimalMovementController());

        System.out.println("An animal has been created at position: " + position);
    }

    private void generatePosition() {
        Vector2d mapSize = Viewport.size;
        double x = Math.random() * mapSize.x;
        double y = Math.random() * mapSize.y;
        position = new Vector2d(x, y);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);

        Dimension screenPos = Viewport.worldToScreenPoint(position);
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }

    class AnimalMovementController extends MonoBehaviour {
        private double moveSpeed = 3.0;
        private double runSpeed = 6.0;

        @Override
        public void action() {
            SetDirection();
            Move();
        }

        private void Move() {
            double speed = isIdle ? moveSpeed : runSpeed;
            Vector2d dir = Animal.this.direction.normalized();
            Animal.this.position.add(dir.scaled(Time.getDeltaTime()).scaled(speed));
        }

        protected void SetDirection() {
            //...
        }
    }
}
