package engine;

import main.Animal;
import main.Fox;
import main.Hare;

public class AnimationThread extends Thread {
    private static AnimationThread instance = new AnimationThread();

    public static AnimationThread getInstance() {
        return instance;
    }

    private MonoBehaviour[] allMonoBehaviours;

    AnimationThread() {
        super();
    }

    protected SimulationPanel panel = SimulationPanel.getInstance();

    boolean suspend = false;
    boolean stop = false;

    synchronized void wakeup(){
        suspend=false;
        notify();
    }
    void safeSuspend(){
        suspend=true;
    }

    public void run() {
        for (; !stop; ) {
            synchronized (this) {
                try {
                    if (suspend) {
                        System.out.println("suspending");
                        wait();
                    }
                } catch (InterruptedException e) {
                }
            }
            update();



            panel.setOpaque(false);
            panel.revalidate();
            panel.repaint();

        }
    }
    void update() {
        allMonoBehaviours = MonoBehaviour.getAll();
        ControlPanel.getInstance().updateBirthRates(); // NIE WIEM GDZIE TO DAC, MAM ZASTOJ MOZGU XD
        synchronized (allMonoBehaviours) {
            for (MonoBehaviour mb : allMonoBehaviours) {
                mb.action();
            }
        }

        AnimationAgent[] allAgents = AnimationAgent.getAgents();
        for (AnimationAgent agent : allAgents) {
            agent.runMonoBehaviours();
        }

        Time.update();
    }
}