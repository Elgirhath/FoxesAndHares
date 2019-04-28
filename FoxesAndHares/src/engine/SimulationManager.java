package engine;

import extensions.Vector2d;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.awt.*;

public class SimulationManager extends Agent {
    public static ContainerController animalContainer = null;

    @Override
    protected void setup() { //core setup
        if (animalContainer == null)
            animalContainer = Runtime.instance().createAgentContainer(new ProfileImpl());
        GUI.getInstance();
        System.out.println("Simulation Manager has been created!\n");

        start();

        AnimationThread thread = AnimationThread.getInstance();
        thread.start(); //start an animation after animals were created

        new SimulationController();
    }

    void start() { //user setup
        int foxNumber = 8;
        int hareNumber = 20;

        for (int i = 0; i < foxNumber; i++) {
            createAnimal("Fox_" + i, "main.Fox");
        }
        for (int i = 0; i < hareNumber; i++) {
            createAnimal("Hare_" + i, "main.Hare");
        }
        Debug.drawLine(new Vector2d(10, 10), new Vector2d(300, 300), Color.black, 1.3);
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

    private class SimulationController extends MonoBehaviour {
        boolean enabled = true;

        @Override
        public void action() {
            if (!enabled)
                return;
            Vector2d start = new Vector2d(10, 10);
            Vector2d end = new Vector2d(200, 200);
            Debug.drawLine(start, end, Color.black, 0.1);
            enabled = false;
        }
    }
}
