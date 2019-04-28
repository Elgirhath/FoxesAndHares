package engine;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    private static SimulationPanel instance = null;

    public static SimulationPanel getInstance() {
        if (instance == null)
            instance = new SimulationPanel();

        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("LOL");

//        for (AnimationAgent agent : AnimationAgent.getAgents()) {
//            agent.paint(g);
//        }

//        paintGraphicComponents(g);
    }

    private void paintGraphicComponents(Graphics g) {
        System.out.println("ABC");
        GraphicComponent[] components = GraphicComponent.getComponentList();
        System.out.println(components.length);
        for(GraphicComponent comp : components) {
            if (comp.enabled)
                comp.paint(g);
        }
    }
}
