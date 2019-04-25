package engine;

import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import main.Main;

public class SimulationManager extends Agent {
    public static ContainerController animalContainer = null;

    @Override
    protected void setup() {
        System.out.println("Simulation Manager has been created!\n");
        start();
    }

    void start() {
        if (animalContainer == null)
            animalContainer = Runtime.instance().createAgentContainer(new ProfileImpl());
        GUI.getInstance();

        AnimationThread thread = AnimationThread.getInstance();
        thread.start();

        Main.main();
    }
}
