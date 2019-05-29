package engine;

public class Time {
    private static long startTimeMillis = System.currentTimeMillis();
    private static double deltaTime = 0.0;
    private static double lastFrameTime = 0.0;

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
        double tmp = lastFrameTime;
        deltaTime = time - lastFrameTime;
        lastFrameTime = time;
        //Po wznowieniu symulacji (po pauzie) zwierzeta "skacza" tak jakby pauza byla tylko w wyswietlaniiu a symulacja dalej leciala.
        // Czy mozliwe ze to przez to ze po wznowieniu po prostu deltaTime jest ogromny i stad ten skok?
        //Wolalbym sie upewnic zanim cos tu pozmieniam
    }
}