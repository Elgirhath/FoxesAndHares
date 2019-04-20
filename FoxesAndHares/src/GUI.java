import javax.swing.*;
import javax.vecmath.Vector2d;

public class GUI extends JFrame {
    private JPanel mainPanel = SimulationPanel.getInstance();

    private GUI() {
        super("Title");
        buildGUI();

        Vector2d camRes = Camera.getResolution();
        setSize(800, 600);

        setVisible(true);
    }

    private static GUI instance = null;

    public static GUI getInstance() {
        if (instance == null)
            instance = new GUI();

        return instance;
    }

    private void buildGUI() {
        add(mainPanel);
    }
}