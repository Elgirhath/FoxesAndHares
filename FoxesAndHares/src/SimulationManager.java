import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import javax.vecmath.Vector2d;

public class SimulationManager extends Agent {
    private int foxNumber = 8;
    private int currentFoxes;
    private int hareNumber = 20;
    private int currentHares;

    @Override
    protected void setup() {
        System.out.println("Simulation Manager has been created!\n");
        start();
    }

    void start() {
        //Create all agents and manage the simulation;
        Scene.getInstance().size = new Vector2d(1000.0, 1000.0);
        createAnimals();

        GUI.getInstance();
    }

    void createAnimals() {
        for (currentFoxes = 0; currentFoxes < foxNumber; currentFoxes++) {
            createAnimal("Fox", "Fox_" + currentFoxes);
        }
        for (currentHares = 0; currentHares < hareNumber; currentHares++) {
            createAnimal("Hare", "Hare_" + currentHares);
        }
    }

    void createAnimal(String className, String name) {
        try {
            AgentController ac = getContainerController().createNewAgent(name, className, null);
            ac.start();
        } catch (Exception e) {}
    }
}
