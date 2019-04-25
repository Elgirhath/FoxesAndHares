package engine;

import jade.core.behaviours.Behaviour;

import java.util.ArrayList;
import java.util.List;

public abstract class MonoBehaviour extends Behaviour {
    private static List<MonoBehaviour> all = new ArrayList<>();

    public MonoBehaviour() {
        all.add(this);
    }

    public static MonoBehaviour[] getAll() {
        MonoBehaviour arr[] = new MonoBehaviour[all.size()];
        all.toArray(arr);
        return arr;
    }

    @Override
    public boolean done() {
        return false;
    }
}