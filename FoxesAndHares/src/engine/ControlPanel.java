package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private static ControlPanel instance = null;
    public static ControlPanel getInstance() {
        if (instance == null)
            instance = new ControlPanel();

        return instance;
    }

    private JButton startButton = new JButton(new ImageIcon("assets/icons/play.png"));
    private JButton pauseButton = new JButton(new ImageIcon("assets/icons/pause.png"));
    private JButton stopButton = new JButton(new ImageIcon("assets/icons/stop.png"));
    private JSlider simulationSpeedSlider = new JSlider(SwingConstants.HORIZONTAL, SimulationManager.minSimSpeed, SimulationManager.maxSimSpeed, SimulationManager.initialSimSpeed);

    private ControlPanel() {
        startButton.addActionListener(e -> {
            if(!SimulationManager.getInstance().running) {
                SimulationManager.getInstance().animate();
                SimulationManager.getInstance().running = true;
                Time.timeScale = getTimeScaleFromSlider();
            }
            else if(Time.timeScale == 0.0) {
                Time.timeScale = getTimeScaleFromSlider();
            }
        });
        pauseButton.addActionListener(e -> {
            if(Time.timeScale == 0.0)
                Time.timeScale = getTimeScaleFromSlider();
            else
                Time.timeScale = 0.0;
        });

        simulationSpeedSlider.addChangeListener(e -> {
            if (Time.timeScale != 0.0)
                Time.timeScale = getTimeScaleFromSlider();
        });

        add(startButton);
        add(pauseButton);
        add(stopButton);
        add(Box.createRigidArea(new Dimension(60,1)));
        add(new JLabel("Simulation Speed"));
        add(simulationSpeedSlider);
    }

    private double getTimeScaleFromSlider() {
        int val = simulationSpeedSlider.getValue();
        double ts = val / (double) SimulationManager.initialSimSpeed;
        return ts;
    }
}