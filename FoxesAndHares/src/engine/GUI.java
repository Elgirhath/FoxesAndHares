package engine;

import main.DataBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel mainPanel = SimulationPanel.getInstance();
    private JPanel settingsPanel = SettingsPanel.getInstance();
    private JPanel controlPanel = ControlPanel.getInstance();
    private JPanel plotPanel = PlotPanel.getInstance();

    private GUI() {
        super("Title");
        buildGUI();
        //setContentPane(mainPanel);
        Dimension camRes = SimulationPanel.getInstance().getSize();
        setSize(DataBase.GlobalConfig.simWidth, DataBase.GlobalConfig.simHeight);

        setVisible(true);
    }

    private static GUI instance = null;

    public static GUI getInstance() {
        if (instance == null)
            instance = new GUI();

        return instance;
    }

    private void buildGUI() {
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.PAGE_START);
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.add(settingsPanel);
        //JPanel space = new JPanel();
        //space.setPreferredSize(new Dimension(70, 70));
        //east.add(space);
        east.add(plotPanel);

        plotPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(east, BorderLayout.EAST);



    }
}