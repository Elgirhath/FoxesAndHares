package engine;

import jade.core.behaviours.Behaviour;
import main.Hare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MonoBehaviour extends Behaviour {
    private static List<MonoBehaviour> all = Collections.synchronizedList(new ArrayList<>());

    static void addMonoBehaviour(MonoBehaviour mb) {
        synchronized (all) {
            all.add(mb);
        }
    }

    static MonoBehaviour[] getAll() {
        synchronized (all) {
            MonoBehaviour arr[] = new MonoBehaviour[all.size()];
            all.toArray(arr);
            return arr;
        }
    }

    static void destroy(MonoBehaviour mb) {
        synchronized (all) {
            try {
                all.remove(mb);
            }
            catch(Exception e) {}
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}