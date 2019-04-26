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

        for (AnimationAgent agent : AnimationAgent.getAgents()) {
            agent.paint(g);
        }
    }
}
