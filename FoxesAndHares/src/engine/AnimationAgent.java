package engine;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AnimationAgent extends Agent {
    public static List<AnimationAgent> agents = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();
        agents.add(this);
    }

    public abstract void paint(Graphics g);
}