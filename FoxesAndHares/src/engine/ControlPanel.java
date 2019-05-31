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
        SimulationManager simMng = SimulationManager.getInstance();

        startButton.addActionListener(e -> {
            if(!simMng.running) {
                simMng.animate();
                simMng.running = true;
                Time.timeScale = getTimeScaleFromSlider();
            }
            else if(simMng.paused) {
                resume();
            }
        });
        pauseButton.addActionListener(e -> {
            if (!simMng.running)
                return;

            if(simMng.paused)
                resume();
            else
                pause();
        });

        simulationSpeedSlider.addChangeListener(e -> {
            if (!simMng.paused)
                Time.timeScale = getTimeScaleFromSlider();
        });

        add(startButton);
        add(pauseButton);
        add(stopButton);
        add(Box.createRigidArea(new Dimension(60,1)));
        add(new JLabel("Simulation Speed"));
        add(simulationSpeedSlider);
    }

    private void resume() {
        SimulationManager.getInstance().paused = false;
        Time.timeScale = getTimeScaleFromSlider();
    }

    private void pause() {
        SimulationManager.getInstance().paused = true;
        Time.timeScale = 0.0;
    }

    private double getTimeScaleFromSlider() {
        int val = simulationSpeedSlider.getValue();
        double ts = val / (double) SimulationManager.initialSimSpeed;
        return ts;
    }
}