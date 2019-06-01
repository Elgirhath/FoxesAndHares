package main;

import engine.SimulationManager;
import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Fox extends Animal{
    private double maxEatDistance = 0.1;
    private Hare prey;
    AnimalMovementController movementController = new AnimalMovementController();

    private static int foxes = 0;
    private static int maleFoxes = 0;
    private final static int lifespan = 14;
    private final static int minBreedAge = 2;

    @Override
    protected void setup() {
        super.setup();
        this.gender = setGender();
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

    protected boolean setGender() {
        int tmp;
        boolean gender = new Random().nextBoolean();
        if(gender)
            tmp = Fox.maleFoxes;
        else
            tmp = Fox.foxes - Fox.maleFoxes;
        if((tmp + 1)/(Fox.foxes + 1) > SimulationManager.genderMaxPercentage)
            gender = !gender;
        if(gender)
            ++Fox.maleFoxes;
        ++Fox.foxes;
        return gender;
    }

    protected void breed() {
        List<Animal> nearFoxes = getVisibleFoxes();
        for(Animal fox : nearFoxes) {
            if(this.gender == fox.gender)
                continue;
            if(!fox.isIdle)
                continue;
            if(Math.random() <= (float)SimulationManager.foxBirthRate/100) {
                double currentTime = Time.getTime();
                Animal mother;
                if(this.gender)
                    mother = fox;
                else
                    mother = this;
                if(!this.isFertile || !fox.isFertile || currentTime - mother.lastBirthTime < Animal.fertilenessFrequency)
                    continue;
                mother.lastBirthTime = Time.getTime();
                SimulationManager.getInstance().createAnimal("Fox_" + Fox.foxes, Fox.class.getName(), mother.position);
                break;
            }
        }
    }

    protected void getOlder() {
        double time = Time.getTime();
        if(time - lastBirthday >= SimulationManager.yearDuration) {
            ++age;
            System.out.println("Fox age: " + age);
            switch(age) {
                case Fox.minBreedAge:
                    isFertile = true;
                    break;
                case Fox.lifespan:
                    Die();
                    break;
            }
            lastBirthday = time;
        }
    }

    private void eatPrey() {
        prey.Die();
        prey = null;
        isIdle = true;
    }

    private void setPrey(Hare hare) {
        prey = hare;
        if (prey != null) {
            isIdle = false;
        }
    }

    class AnimalMovementController extends Animal.AnimalMovementController {

        private double moveSpeed = 2.0;
        private double runSpeed = 4.0;

        @Override
        public void action() {
            getOlder();
            findPrey();
            setDirection();
            move();

            if (prey != null && Vector2d.distance(prey.position, position) < maxEatDistance) {
                eatPrey();
            }
            else if(isIdle)  { //dodalem warunek w ten sposob, zeby nie rozmnazaly sie gdy kogos gonia albo jedza
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
            if(prey != null) {
                Vector2d distanceVector = prey.position.minus(position);
                direction = distanceVector;
            }
            else {
                setIdleDirection();
            }
        }

        private void findPrey() {
            List<Animal> hares = getVisibleHares();

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
}