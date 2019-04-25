package engine;

public class Time {
    private static long startTime = System.nanoTime();
    private static double deltaTime = 0.0;

    private Time() {}

    public static double getDeltaTime() {
        return deltaTime;
    }

    protected static void setDeltaTime(double time) {
        deltaTime = time;
    }

    public static double getTime() {
        return (System.nanoTime() - startTime) * 10e-9;
    }
}