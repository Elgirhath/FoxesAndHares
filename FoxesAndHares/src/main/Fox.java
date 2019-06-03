package main;

import engine.MonoBehaviour;
import engine.Time;
import engine.Utils;
import extensions.Vector2d;

import java.awt.*;
import java.util.List;

public class Fox extends Animal{
    private double maxEatDistance = 0.1;
    private Hare prey;
    AnimalMovementController movementController = new AnimalMovementController();
    HungerController hungerController = new HungerController();

    public static DataBase.Data data = DataBase.createData(Fox.class);
    public static DataBase.Config config = DataBase.createConfig(Fox.class);
    public static DataBase.GlobalConfig globalConfig = DataBase.getGlobalConfig();

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(movementController);
        addBehaviour(hungerController);

        prey = null;
        graphic.color = Color.orange;
    }

    private void eatPrey() {
        prey.die();
        prey = null;
        isIdle = true;
        hungerController.hunger += globalConfig.hungerPerMeal;
    }

    private void setPrey(Hare hare) {
        prey = hare;
        isIdle = prey == null;
    }

    class AnimalMovementController extends Animal.AnimalMovementController {

        private double moveSpeed = 2.0;
        private double runSpeed = 4.4;
        double turnRadius = 2.0;

        @Override
        public double getTurnRadius() {
            return turnRadius;
        }

        @Override
        public void action() {
            getOlder();
            findPrey();
            setDirection();
            move();

            if (prey != null && Vector2d.distance(prey.position, position) < maxEatDistance) {
                eatPrey();
            }
            else if(isIdle && hungerController.getRatio() > 0.5)  { //dodalem warunek w ten sposob, zeby nie rozmnazaly sie gdy kogos gonia albo jedza
                breed();
            }
        }

        protected void move() {
            synchronized (this) {
                double speed = isIdle ? moveSpeed : runSpeed;
                Vector2d dir = direction.normalized();
                double deltaTime = Time.getDeltaTime();
                Vector2d move = dir.scaled(deltaTime).scaled(speed);
                position.add(move);
            }
        }

        private void setDirection() {
            if(!isIdle) {
                Vector2d destDir = prey.position.minus(position);
                turn(destDir);
            }
            else {
                setIdleDirection();
            }
        }

        private void findPrey() {
            List<Animal> hares = getVisibleOfType(Hare.class);

            if (hungerController.getRatio() > 0.9) {
                setPrey(null);
                return;
            }

            double minDist = Double.POSITIVE_INFINITY;
            Animal closest = null;
            for (Animal hare : hares) {
                double distance = Vector2d.distance(hare.position, position);
                if (closest == null || distance < minDist) {
                    minDist = distance;
                    closest = hare;
                }
            }

            setPrey((Hare) closest);
        }
    }

    class HungerController extends MonoBehaviour {
        public double hunger = globalConfig.maxHunger;
        private Color hungryColor = Color.red;
        private Color fullColor = Color.orange;

        @Override
        public void action() {
            hunger -= globalConfig.hungerLossPerSec * Time.getDeltaTime();
            double ratio = hunger / globalConfig.maxHunger;
            graphic.color = Utils.lerp(hungryColor, fullColor, ratio);
            if (hunger <= 0.0)
                die();
        }

        public double getRatio() {
            return hunger / globalConfig.maxHunger;
        }
    }
}