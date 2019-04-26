package engine;

import jade.core.Agent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AnimationAgent extends Agent {
    private static List<AnimationAgent> agents = Collections.synchronizedList(new ArrayList<>());

    public static AnimationAgent[] getAgents() {
        synchronized (agents) {
            AnimationAgent arr[] = new AnimationAgent[agents.size()];
            agents.toArray(arr);
            return arr;
        }
    }

    @Override
    protected void setup() {
        super.setup();

        synchronized (agents) {
            agents.add(this);
        }
    }

    public abstract void paint(Graphics g);
}