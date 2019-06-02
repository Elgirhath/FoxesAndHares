package engine;

import javax.swing.*;
import java.awt.*;
import main.Hare;
import main.Fox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private static ControlPanel instance = null;
    public static ControlPanel getInstance() {
        if (instance == null)
            instance = new ControlPanel();

        return instance;
    }

    //Buttons and sliders
    private JButton startButton = new JButton(new ImageIcon("assets/icons/play.png"));
    private JButton pauseButton = new JButton(new ImageIcon("assets/icons/pause.png"));
    private JButton stopButton = new JButton(new ImageIcon("assets/icons/stop.png"));
    private JSlider simulationSpeedSlider = new JSlider(SwingConstants.HORIZONTAL, SimulationManager.minSimSpeed, SimulationManager.maxSimSpeed, SimulationManager.initialSimSpeed);
    public JSlider foxBirthRateSlider = new JSlider(SwingConstants.HORIZONTAL, SimulationManager.minBirthRate, SimulationManager.maxBirthRate, (int)(Fox.birthRate*100));
    public JSlider hareBirthRateSlider = new JSlider(SwingConstants.HORIZONTAL, SimulationManager.minBirthRate, SimulationManager.maxBirthRate, (int)(Hare.birthRate*100));

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

        foxBirthRateSlider.addChangeListener(e -> {
            Fox.birthRate = (double)(foxBirthRateSlider.getValue())/100;
        });

        hareBirthRateSlider.addChangeListener(e -> {
            Fox.birthRate = (double)(hareBirthRateSlider.getValue())/100;
        });

        setLayout(new GridLayout(0, 1));
        JLabel title = new JLabel("Foxes and Hares", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 18));
        add(title);

        JPanel generalRow = new JPanel();
        generalRow.add(startButton);
        generalRow.add(pauseButton);
        generalRow.add(stopButton);
        generalRow.add(Box.createRigidArea(new Dimension(60,1)));
        generalRow.add(getComponentWithVerticalTitle(simulationSpeedSlider, "Simulation Speed"));

        JPanel birthRow = new JPanel();

        birthRow.add(getComponentWithVerticalTitle(hareBirthRateSlider,"Hare Birth Rate"));
        birthRow.add(getComponentWithVerticalTitle(foxBirthRateSlider,"Fox Birth Rate"));

        add(generalRow);
        add(birthRow);
    }

    private JPanel getComponentWithVerticalTitle (JComponent comp, String label) {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, 1));
        resultPanel.add(new JLabel(label, SwingConstants.CENTER));
        resultPanel.add(comp);
        return resultPanel;
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