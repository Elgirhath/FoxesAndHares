package engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static synchronized <T extends AnimationAgent> List<T> findAgentsOfType(Class<T> type, boolean includeDerived) {
        List<T> list = new ArrayList<>();

        for (AnimationAgent agent : AnimationAgent.getAgents()) {
            if (includeDerived) {
                if (type.isInstance(agent)) {
                    list.add((T) agent);
                }
            }
            else {
                if (agent.getClass() == type) {
                    list.add((T) agent);
                }
            }
        }
        return list;
    }

    public static synchronized <T extends AnimationAgent> List<T> findAgentsOfType(Class<T> type) {
        return findAgentsOfType(type, true);
    }

    public static Color lerp(Color a, Color b, double t) {
        int rc = lerp(a.getRed(), b.getRed(), t);
        int gc = lerp(a.getGreen(), b.getGreen(), t);
        int bc = lerp(a.getBlue(), b.getBlue(), t);
        return clampColor(rc, gc, bc);
    }

    public static int lerp(int a, int b, double t) {
        double res = lerp((double) a, (double) b, t);
        return (int)res;
    }

    public static double lerp(double a, double b, double t) {
        double x = clamp(t, 0.0, 1.0);
        double res = a * (1.0 - x) + b * x;
        return res;
    }

    public static Color clampColor(int r, int g, int b) {
        int rc = clamp(r, 0, 255);
        int gc = clamp(g, 0, 255);
        int bc = clamp(b, 0, 255);
        return new Color(rc, gc, bc);
    }

    public static int clamp(int number, int min, int max) {
        int res = number < min ? min : number;
        res = res > max ? max : res;
        return res;
    }

    public static double clamp(double number, double min, double max) {
        double res = number < min ? min : number;
        res = res > max ? max : res;
        return res;
    }
}
