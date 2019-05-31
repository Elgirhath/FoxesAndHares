package main;

import engine.*;

import extensions.Vector2d;
import jade.wrapper.StaleProxyException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends AnimationAgent {
    Color color = Color.black;
    Vector2d direction = new Vector2d((Math.random() * (100 - (-100))) - 100, (Math.random() * (100 - (-100))) - 100);
    boolean isIdle = true;
    AnimalMovementController movementController = new AnimalMovementController();
    VisionController visionController = new VisionController();

    @Override
    protected void setup() {
        super.setup();

        generatePosition();
        //addBehaviour(new AnimalMovementController());
        addBehaviour(visionController);
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
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2 * radius, 2 * radius);
    }

    protected void Die() {
        agents.remove(this);
        System.out.println(this + " died.");
        SimulationPanel.getInstance().removeComponent(this);
        SimulationPanel.getInstance().removeComponent(visionController.visionCone);
        //getContainerController().kill(); - nie dziala to xD
    }

    public class AnimalMovementController extends MonoBehaviour {
        private double idleSoundRadius = 3.0;
        private double runSoundRadius = 4.0;

        public double getSoundRadius() {
            return isIdle ? idleSoundRadius : runSoundRadius;
        }

        @Override
        public void action() {

        }
    }

    class VisionController extends MonoBehaviour {
        double maxDist = 7;
        int fov = 90;

        VisionCone visionCone;

        VisionController() {
            visionCone = new VisionCone();
            SimulationPanel.getInstance().addComponent(visionCone);
        }

        @Override
        public void action() {
        }

        public List<Animal> getVisible() {
            List<Animal> animalsVisible = new ArrayList<>();

            List<Animal> animals = Utils.findAgentsOfType(Animal.class);
            for (Animal animal : animals) {
                if (!isVisible(animal) || animal == Animal.this)
                    continue;

                Debug.drawLine(position, animal.position, Color.red, Time.getDeltaTime()); //An animal is seen

                animalsVisible.add(animal);
            }

            return animalsVisible;
        }

        private boolean isVisible(Animal animal) {
            if (animal.movementController != null) {
                if (Vector2d.distance(position, animal.position) < animal.movementController.getSoundRadius())
                    return true;
            }

            if (Vector2d.distance(position, animal.position) > maxDist) {
                return false;
            }
            double angle = Math.toDegrees(animal.position.minus(position).angle(direction));
            if (angle > fov / 2) {
                return false;
            }

            return true;
        }

        public class VisionCone implements GraphicComponent {

            @Override
            public void paintComponent(Graphics g) {
                double dirAngle = direction.angle(Vector2d.right());
                dirAngle = (int) Math.toDegrees(dirAngle);
                if (direction.y < 0) {
                    dirAngle = -dirAngle;
                }


                Vector2d screenSize = Viewport.worldToScreenPoint(new Vector2d(maxDist * 2, maxDist * 2));
                Dimension screenPos = Viewport.worldToScreenPoint(position).minus(new Vector2d(screenSize.x / 2, screenSize.y / 2)).toDimension();
                Color tmp = g.getColor();
                Color coneColor = new Color(0, 150, 255, 40);
                g.setColor(coneColor);
                g.fillArc(screenPos.width, screenPos.height, (int) screenSize.x, (int) screenSize.y, (int) -(dirAngle + fov / 2), fov);

                if (Animal.this.movementController != null) {
                    int soundRadius = (int) Animal.this.movementController.getSoundRadius();
                    Vector2d soundScreenSize = Viewport.worldToScreenPoint(new Vector2d(soundRadius * 2, soundRadius * 2));
                    Dimension screenSoundPos = Viewport.worldToScreenPoint(position).minus(new Vector2d(soundScreenSize.x / 2, soundScreenSize.y / 2)).toDimension();
                    g.fillOval(screenSoundPos.width, screenSoundPos.height, (int) soundScreenSize.x, (int) soundScreenSize.y);
                }
                g.setColor(tmp);
            }
        }
    }
}
