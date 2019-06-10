package engine;

import main.DataBase;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends UIPanel {
    private static ControlPanel instance = null;

    public static ControlPanel getInstance() {
        if (instance == null)
            instance = new ControlPanel();

        return instance;
    }

    //Buttons and sliders
    private JButton startButton = new JButton(new ImageIcon("assets/icons/play.png"));
    private JButton pauseButton = new JButton(new ImageIcon("assets/icons/pause.png"));
    //private JButton stopButton = new JButton(new ImageIcon("assets/icons/stop.png"));
    private JSlider simulationSpeedSlider = new JSlider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minSimSpeed, DataBase.GlobalConfig.maxSimSpeed, DataBase.GlobalConfig.initialSimSpeed);

    private ControlPanel() {
        SimulationManager simMng = SimulationManager.getInstance();
        SettingsPanel setPnl = SettingsPanel.getInstance();

        startButton.addActionListener(e -> {
            if(!simMng.running) {
                simMng.start();
                SimulationPanel.getInstance().prepare();
                simMng.animate();
                simMng.running = true;
                Time.timeScale = getTimeScaleFromSlider();

                setPnl.foxInit.setEnabled(false);
                setPnl.hareInit.setEnabled(false);
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

        //stopButton.addActionListener(e -> {
          //  System.exit(0);
        //});

        simulationSpeedSlider.addChangeListener(e -> {
            if (!simMng.paused)
                Time.timeScale = getTimeScaleFromSlider();
        });

        setLayout(new GridLayout(0, 1));

        addTitle("Foxes and Hares");

        JPanel buttonRow = new JPanel();
        buttonRow.add(startButton);
        buttonRow.add(pauseButton);
        //buttonRow.add(stopButton);
        buttonRow.add(Box.createRigidArea(new Dimension(60,1)));
        buttonRow.add(getComponentWithVerticalTitle(simulationSpeedSlider, "Simulation Speed"));

        add(buttonRow);
    }

    private void resume() {
        SimulationManager.getInstance().paused = false;
        Time.timeScale = getTimeScaleFromSlider();
    }

    private void pause() {
        SimulationManager.getInstance().paused = true;
        Time.timeScale = 0.0;
    }

    private void addTitle(final String text) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 18));
        add(title);
    }

    private double getTimeScaleFromSlider() {
        int val = simulationSpeedSlider.getValue();
        double ts = val / (double) DataBase.GlobalConfig.initialSimSpeed;
        return ts;
    }
}