package engine;

import main.Hare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Time {
    private static long startTimeMillis = System.currentTimeMillis();
    private static double deltaTime = 0.0;
    private static double lastFrameTime = 0.0;
    public static double timeScale = 1.0;

    static List<Timer> timers = Collections.synchronizedList(new ArrayList<>());

    static Timer[] getTimers() {
        Timer[] timersArr = new Timer[timers.size()];
        timers.toArray(timersArr);
        return timersArr;
    }

    private Time() {}

    public synchronized static double getDeltaTime() {
        return deltaTime;
    }

    public static double getTime() {
        long milisDiff = System.currentTimeMillis() - startTimeMillis;
        double time = milisDiff * 1e-3;
        return time;
    }

    static synchronized void update() {
        double time = getTime();
        if (lastFrameTime < 1e-6) {
            lastFrameTime = time;
        }
        deltaTime = time - lastFrameTime;
        deltaTime *= timeScale;
        lastFrameTime = time;

        for (Timer timer : getTimers()) {
            timer.update();
        }
    }
}