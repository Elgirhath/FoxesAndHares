package engine;

import jade.core.behaviours.Behaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MonoBehaviour extends Behaviour {
    private static List<MonoBehaviour> all = Collections.synchronizedList(new ArrayList<>());

    public MonoBehaviour() {
        all.add(this);
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
            all.remove(mb);
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}