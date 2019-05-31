package main;

import engine.*;

import extensions.Vector2d;
import jade.wrapper.StaleProxyException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

public abstract class Animal extends AnimationAgent {
    Color color = Color.black;
    Vector2d direction = new Vector2d((Math.random() * (100 - (-100))) - 100, (Math.random() * (100 - (-100))) - 100);
    boolean isIdle = true;
    boolean isFertile;
    double lastBirthTime = 0.0;
    AnimalMovementController movementController = new AnimalMovementController();
    VisionController visionController = new VisionController();
    boolean gender; //Male as TRUE, female as FALSE

    static double fertilenessFrequency = 8.0;

    @Override
    protected void setup() {
        super.setup();
        Object[] args = getArguments();
        if(args != null && args.length > 0) {
            position = new Vector2d((Vector2d) args[0]);
            isFertile = false;
            System.out.println("An animal has been born!");
        }
        else {
            generatePosition();
            isFertile = true;
        }
        //addBehaviour(new AnimalMovementController());
        addBehaviour(visionController);
    }

    protected abstract boolean setGender();
    protected abstract void breed();

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
    }

    protected List<Animal> getVisibleHares() {
        List<Animal> animals = visionController.getVisible();
        List<Animal> hares = animals.stream()
                .filter(s -> s instanceof Hare)
                .collect(Collectors.toList());
        return hares;
    }

    protected List<Animal> getVisibleFoxes() {
        List<Animal> animals = visionController.getVisible();
        List<Animal> foxes = animals.stream()
                .filter(s -> s instanceof Hare)
                .collect(Collectors.toList());
        return foxes;
    }

    public class AnimalMovementController extends MonoBehaviour {
        private double idleSoundRadius = 3.0;
        private double runSoundRadius = 4.0;

        double turnSpeed = 0.1;

        Vector2d idleDestination = null;
        double idleDestReachThreshold = 5.0;

        public double getSoundRadius() {
            return isIdle ? idleSoundRadius : runSoundRadius;
        }

        @Override
        public void action() {

        }

        private void setIdleDestination() {
            double mapScale = 0.7;
            Vector2d mapSize = Viewport.getSize();
            double x = new Random().nextDouble() * (mapSize.x * mapScale) + mapSize.x * (1 - mapScale) / 2;
            double y = new Random().nextDouble() * (mapSize.y * mapScale) + mapSize.y * (1 - mapScale) / 2;
            idleDestination = new Vector2d(x, y);
        }

        protected void setIdleDirection() {
            if (idleDestination == null || Vector2d.distance(position, idleDestination) < idleDestReachThreshold)
                setIdleDestination();

            Vector2d destDir = idleDestination.minus(position);
            double angle = direction.angle(destDir);

            double moveSpeed = Time.getDeltaTime() * 2.0;
            double radius = 10.0;
            double division = Math.pow(moveSpeed / radius, 2.0) / 2.0;
            double beta = Math.acos(1 - division);
            beta = beta > angle ? angle : beta;

            Vector2d perpendicular = direction.rotate(Math.toRadians(90));
            if (destDir.dot(perpendicular) < 0)
                beta = -beta;

            direction = direction.rotate(beta);
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
