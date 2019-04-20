import jade.core.Agent;
import jade.wrapper.AgentController;

public class SimulationManager extends Agent {
    private int foxNumber = 8;
    private int hareNumber = 20;

    @Override
    protected void setup() {
        System.out.println("Simulation Manager has been created!\n");
        start();
    }

    void start() {
        //Create all agents and manage the simulation;
        createAnimals();

        GUI.getInstance();
    }

    void createAnimals() {
        for (int i = 0; i < foxNumber; i++) {
            createAnimal("Fox", "Fox_" + i);
        }
        for (int i = 0; i < hareNumber; i++) {
            createAnimal("Hare", "Hare_" + i);
        }
    }

    void createAnimal(String className, String name) {
        try {
            AgentController ac = getContainerController().createNewAgent(name, className, null);
            ac.start();
        } catch (Exception e) {}
    }
}
