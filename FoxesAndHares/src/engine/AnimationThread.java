package engine;

public class AnimationThread extends Thread {
    private static AnimationThread instance = new AnimationThread();

    public static AnimationThread getInstance() {
        return instance;
    }

    private MonoBehaviour[] allMonoBehaviours;

    AnimationThread() {
        super();
    }

    protected SimulationPanel simPanel = SimulationPanel.getInstance();
    protected PlotPanel plotPanel = PlotPanel.getInstance();

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



            simPanel.setOpaque(false);
            simPanel.revalidate();
            simPanel.repaint();
            plotPanel.setOpaque(false);
            plotPanel.revalidate();
            plotPanel.repaint();

        }
    }
    void update() {
        Time.update();

        if (SimulationManager.getInstance().paused)
            return;

        allMonoBehaviours = MonoBehaviour.getAll();
        synchronized (allMonoBehaviours) {
            for (MonoBehaviour mb : allMonoBehaviours) {
                mb.action();
            }
        }

        AnimationAgent[] allAgents = AnimationAgent.getAgents();
        for (AnimationAgent agent : allAgents) {
            agent.runMonoBehaviours();
        }
    }
}