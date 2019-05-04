package main;

import engine.*;

import extensions.Vector2d;

import java.awt.*;
import java.util.List;

public class Animal extends AnimationAgent {
    Color color = Color.black;
    Vector2d direction = new Vector2d(1.0, 1.0);

    @Override
    protected void setup() {
        super.setup();

        generatePosition();
        //addBehaviour(new AnimalMovementController());
        addBehaviour(new VisionController());
    }

    private void generatePosition() {
        Vector2d mapSize = Viewport.getSize();
        double x = Math.random() * mapSize.x;
        double y = Math.random() * mapSize.y;
        position = new Vector2d(x, y);
    }

    public void paintComponent(Graphics g) {
        g.setColor(color);
        int radius = 5;

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2*radius, 2*radius);
    }

    abstract class AnimalMovementController extends MonoBehaviour {
        private double moveSpeed = 2.0;
        private double runSpeed = 4.0;
        boolean isIdle = true;
        double lengthOfIdleMovement = 6.0;
        double timeOfDirectionChange = 0.0;

        @Override
        public void action() {
            SetDirection();
            Move();
        }

        private void Move() {
            synchronized (this) {
                double speed = isIdle ? moveSpeed : runSpeed;
                Vector2d dir = direction.normalized();
                double deltaTime = Time.getDeltaTime();
                Vector2d move = dir.scaled(deltaTime).scaled(speed);
                position.add(move);
                List<Hare> hares = Utils.findAgentsOfType(Hare.class);
                if (hares.size() > 0) {
                    if (hares.get(0) == Animal.this) {
//                        System.out.println(hares.get(0).getName() + " -- " + position);
                    }
                }
            }
        }

        protected void SetDirection() {
            if(isIdle) {
                if(Time.getTime() - timeOfDirectionChange > lengthOfIdleMovement) {
                    timeOfDirectionChange = Time.getTime();
                    lengthOfIdleMovement = Math.random() * 6 + 6;
                    direction.setX((Math.random() * ( 100 - (-100) )) - 100);
                    direction.setY((Math.random() * ( 100 - (-100) )) - 100);
                }
            }
            else {
                Rush();
            }
        }

        protected abstract void Rush();

        protected void setIdle(boolean state, Animal target) {
            isIdle = state;
        }
    }

    class VisionController extends MonoBehaviour {
        double maxDist = 7;
        int fov = 90;
        VisionCone cone = new VisionCone();

        VisionController() {
            SimulationPanel.getInstance().addComponent(cone);
        }

        @Override
        public void action() {
            List<Animal> animals = Utils.findAgentsOfType(Animal.class);
            for(Animal animal : animals) {
                if (animal == Animal.this) {
                    // tutaj movementController.setIdle(true, null);
                    continue;
                }
                if (Vector2d.distance(position, animal.position) > maxDist) {
                    // tutaj movementController.setIdle(true, null);
                    continue;
                }
                double angle = Math.toDegrees(animal.position.minus(position).angle(direction));
                if (angle > fov/2) {
                    // tutaj movementController.setIdle(true, null);
                    continue;
                }
                //if(animal instanceof this.getClass().GETKURWAOUTERCLASS) {
                    // NO ZEBY SPRAWDZILO CZY TEN ANIMAL JEST TEGO SAMEGO CO TEN ANIMAL NO ZEBY FOX NIE GONIL FOXA
                //}

                Debug.drawLine(position, animal.position, Color.red, Time.getDeltaTime()); //An animal is seen
                // tutaj movementController.setIdle(false, animal);
            }
        }

        public class VisionCone implements GraphicComponent {

            @Override
            public void paintComponent(Graphics g) {
                double dirAngle = direction.angle(Vector2d.right());
                dirAngle = (int) Math.toDegrees(dirAngle);

                Vector2d screenSize = Viewport.worldToScreenPoint(new Vector2d(maxDist*2, maxDist*2));
                Dimension screenPos = Viewport.worldToScreenPoint(position).minus(new Vector2d(screenSize.x/2, screenSize.y/2)).toDimension();
                Color tmp = g.getColor();
                Color coneColor = new Color(0, 150, 255, 40);
                g.setColor(coneColor);
                g.fillArc(screenPos.width, screenPos.height, (int)screenSize.x, (int)screenSize.y, (int)-(dirAngle + fov/2), fov);
                g.setColor(tmp);
            }
        }
    }
}
