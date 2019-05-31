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
    private static SimulationManager instance = null;
    public static SimulationManager getInstance() {
        if (instance == null)
            instance = new SimulationManager();

        return instance;
    }

    private static ContainerController animalContainer = null;

    boolean running = false;
    boolean paused = false;

    static int maxSimSpeed = 100;
    static int minSimSpeed = 1;
    static int initialSimSpeed = 10;
    public static float genderMaxPercentage = 0.65f;
    static int maxFoxBirthRate = 100;
    static int minFoxBirthRate = 1;
    public static int foxBirthRate = 50;
    static int maxHareBirthRate = 100;
    static int minHareBirthRate = 1;
    public static int hareBirthRate = 70;

    @Override
    protected void setup() { //core setup
        if (animalContainer == null)
            animalContainer = Runtime.instance().createAgentContainer(new ProfileImpl());
        GUI.getInstance();
        System.out.println("Simulation Manager has been created!\n");
        start();
        SimulationPanel.getInstance().prepare();
    }

    void start() { //user setup
        int foxNumber = 9;
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

    protected void animate() {
        AnimationThread thread = AnimationThread.getInstance();
        thread.start(); //start an animation after animals were created
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

    public void createAnimal(String name, String className, Vector2d position) {
        try {
            ContainerController container = animalContainer;
            AgentController ac = container.createNewAgent(name, className, new Vector2d[] {position});
            ac.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
