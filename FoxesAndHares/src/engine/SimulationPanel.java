package engine;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SimulationPanel extends JPanel {
    private static SimulationPanel instance = null;

    public static SimulationPanel getInstance() {
        if (instance == null)
            instance = new SimulationPanel();

        return instance;
    }

    private List<GraphicComponent> componentList = new ArrayList<>();

    public void addComponent(GraphicComponent component) {
        synchronized (componentList) {
            componentList.add(component);
        }
    }

    public void removeComponent(GraphicComponent component) {
        synchronized (componentList) {
            componentList.remove(component);
        }
    }

    public List<GraphicComponent> getComponentList() {
        synchronized (componentList) {
            return new ArrayList<GraphicComponent>(componentList);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintGraphicComponents(g);
    }

    private void paintGraphicComponents(Graphics g) {
        List<GraphicComponent> components = getComponentList();
        for(GraphicComponent comp : components) {
            comp.paintComponent(g);
        }
    }

    protected void prepare() {
        setOpaque(false);
        revalidate();
        repaint();
    }
}
