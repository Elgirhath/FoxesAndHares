package main;

import engine.SimulationManager;
import engine.Time;
import engine.Timer;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Hare extends Animal {
    HareMovement movementController = new HareMovement();

    private static int maleHares = 0;

    private final static double birthRate = 0.7;
    private final static int lifespan = 13; //years
    private final static int minBreedAge = 2; //years

    @Override
    protected void setup() {
        super.setup();
        setGender();
        addBehaviour(movementController);
        color = Color.green;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        int radius = 5;

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2*radius, 2*radius);
    }


    @Override
    protected double getBirthRate() {
        return birthRate;
    }

    @Override
    protected void registerGender() {
        if (gender == Gender.MALE)
            ++maleHares;
    }

    @Override
    protected int getLifeSpan() {
        return lifespan;
    }

    @Override
    protected int getMinBreedAge() {
        return minBreedAge;
    }

    @Override
    protected int getMaleCount() {
        return maleHares;
    }


    public class HareMovement extends Animal.AnimalMovementController {
        private double pathFindThreshold = 4.0;

        List<Animal> visibleFoxes = new ArrayList<>();

        private double moveSpeed = 2.0;
        private double runSpeed = 4.0;

        boolean isSafe = true;

        double turnRadius = 0.5;

        @Override
        public double getTurnRadius() {
            return turnRadius;
        }

        @Override
        public void action() {
            getOlder();
            visibleFoxes = getVisibleFoxes();
            setDirection();
            move();

            System.out.println(getAgeInYears());

            if(isIdle) {
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
            if (visibleFoxes.size() == 0) {
                if (!isSafe) {
                    new Timer(2.0, () -> isIdle = true);
                    isSafe = true;
                    setIdleDestination(position.plus(direction.normalized().scaled(20.0)));
                }
                setIdleDirection();
            } else {
                Vector2d destDir = getOptimalRunDirection();
                turn(destDir);
                isIdle = false;
                isSafe = false;
            }
        }

        private Vector2d getOptimalRunDirection() {
            double epsilon = 1.0; //degrees
            double bestNorm = 0.0;
            Vector2d bestDir = null;

            double startAngle = 360 - (Math.toDegrees(Math.atan2(direction.y, direction.x)) - 90);

            for (double phi = 0.0; phi < 360.0; phi += epsilon) {
                double phiRad = Math.toRadians(phi + startAngle % 360.0);
                Vector2d dir = new Vector2d(Math.sin(phiRad), Math.cos(phiRad));
                Vector2d move = dir.scaled(pathFindThreshold);
                Vector2d pos = position.plus(move);

                if (!Viewport.isInView(pos)) {
                    continue;
                }

                double norm = getDistanceNorm(pos);
                if (bestDir == null || norm > bestNorm) {
                    bestNorm = norm;
                    bestDir = dir;
                }
            }

            return bestDir;
        }

        private double getDistanceNorm(Vector2d pos) {
            double sum = 0;
            for (Animal fox : visibleFoxes) {
                sum += Vector2d.distance(fox.position, pos);
            }
            return sum;
        }
    }

}
