package engine;

import extensions.Vector2d;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import main.Animal;
import main.DataBase;
import main.Fox;
import main.Hare;

import java.awt.*;

public class SimulationManager extends Agent {
    private static SimulationManager instance = null;
    public static synchronized SimulationManager getInstance() {
        if (instance == null)
            instance = new SimulationManager();

        return instance;
    }

    private static ContainerController animalContainer = null;

    boolean running = false;
    boolean paused = false;

    static int maxSimSpeed = 100;
    static int minSimSpeed = 1;
    static int maxBirthRate = 100;
    static int minBirthRate = 1;
    static int initialSimSpeed = 10;
    public static double genderMaxPercentage = 0.65;
    public static double yearDuration = 60.0;

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
        int foxNumber = 3;
        int hareNumber = 10;

        for (int i = 0; i < foxNumber; i++) {
            createAnimal("Fox_" + i, Fox.class);
        }
        for (int i = 0; i < hareNumber; i++) {
            createAnimal("Hare_" + i, Hare.class);
        }
        Debug.drawLine(new Vector2d(10, 10), new Vector2d(10, 10), Color.black, 1.3);
        Debug.drawGrid(5.0, new Color(0,0,0,30));
    }

    protected void animate() {
        AnimationThread thread = AnimationThread.getInstance();
        thread.start(); //start an animation after animals were created
    }

    public static synchronized void createAnimal(String name, Class cls) {
        try {
            ContainerController container = animalContainer;
            AgentController ac = container.createNewAgent(name, cls.getName(), null);
            ac.start();
            ++DataBase.getData(cls).initializedCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static synchronized void createAnimal(String name, Class cls, Vector2d position) {
        try {
            ContainerController container = animalContainer;
            AgentController ac = container.createNewAgent(name, cls.getName(), new Vector2d[]{position});
            ac.start();
            ++DataBase.getData(cls).initializedCount;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
