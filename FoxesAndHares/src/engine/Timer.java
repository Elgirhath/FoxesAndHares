package engine;

public class Timer {
    double value;
    Runnable func;

    public Timer(double secs, Runnable runnable) {
        value = secs;
        func = runnable;
        synchronized (Time.timers) {
            Time.timers.add(this);
        }
    }

    void update() {
        value -= Time.getDeltaTime();
        if (value <= 0.0) {
            func.run();
            delete();
        }
    }

    void delete() {
        synchronized (Time.timers) {
            Time.timers.remove(this);
        }
    }
}