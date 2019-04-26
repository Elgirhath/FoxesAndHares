package engine;

public class Time {
    private static long startTime = System.currentTimeMillis();
    private static double deltaTime = 0.0;
    private static double lastFrameTime = 0.0;

    private Time() {}

    public static double getDeltaTime() {
        return deltaTime;
    }

    public static double getTime() {
        long milisDiff = System.currentTimeMillis() - startTime;
        double time = milisDiff * 10e-3;
        return time;
    }

    static void update() {
        double time = getTime();
        deltaTime = time - lastFrameTime;
        lastFrameTime = time;
    }
}