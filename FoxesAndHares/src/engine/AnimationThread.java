package engine;

public class AnimationThread extends Thread {
    private static AnimationThread instance = new AnimationThread();

    public static AnimationThread getInstance() {
        return instance;
    }

    private MonoBehaviour[] allMonoBehaviours;

    AnimationThread() {
        super();

        allMonoBehaviours = MonoBehaviour.getAll();
    }

    private SimulationPanel panel = SimulationPanel.getInstance();

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
        synchronized (allMonoBehaviours) {
            for (MonoBehaviour mb : allMonoBehaviours) {
                mb.action();
            }
        }

        Time.update();
    }
}