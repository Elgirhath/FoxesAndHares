package engine;

import extensions.Vector2d;
import jade.core.Agent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AnimationAgent extends Agent implements GraphicComponent {
    private static List<AnimationAgent> agents = Collections.synchronizedList(new ArrayList<>());
    public Vector2d position = new Vector2d(0.0, 0.0);

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

        SimulationPanel.getInstance().addComponent(this);
    }

    private List<GraphicComponent> components = Collections.synchronizedList(new ArrayList<>());

    public void addGraphicComponent(GraphicComponent component) {
        components.add(component);
    }

    public void paintComponent(Graphics g) {
        for (GraphicComponent component : components) {
            component.paintComponent(g);
        }
    }
}