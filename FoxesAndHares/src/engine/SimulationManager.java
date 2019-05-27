package engine;

import extensions.Vector2d;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import main.Fox;
import main.Hare;

import java.awt.*;

public class SimulationManager extends Agent {
    private static ContainerController animalContainer = null;

    @Override
    protected void setup() { //core setup
        if (animalContainer == null)
            animalContainer = Runtime.instance().createAgentContainer(new ProfileImpl());
        GUI.getInstance();
        System.out.println("Simulation Manager has been created!\n");

        start();

        AnimationThread thread = AnimationThread.getInstance();
        thread.start(); //start an animation after animals were created
    }

    void start() { //user setup
        int foxNumber = 30;
        int hareNumber = 7;

        for (int i = 0; i < foxNumber; i++) {
            createAnimal("Fox_" + i, Fox.class.getName());
        }
        for (int i = 0; i < hareNumber; i++) {
            createAnimal("Hare_" + i, Hare.class.getName());
        }
        Debug.drawLine(new Vector2d(10, 10), new Vector2d(10, 10), Color.black, 1.3);
        Debug.drawGrid(5.0, new Color(0,0,0,30));
    }

    public void createAnimal(String name, String className) {
        try {
            ContainerController container = animalContainer;
            AgentController ac = container.createNewAgent(name, className, null);
            ac.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
