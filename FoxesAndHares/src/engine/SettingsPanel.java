package engine;

import javax.swing.*;
import java.awt.*;

import main.DataBase;
import main.Hare;
import main.Fox;

public class SettingsPanel extends UIPanel {
    private static SettingsPanel instance = null;
    public static SettingsPanel getInstance() {
        if (instance == null)
            instance = new SettingsPanel();

        return instance;
    }

    class Slider {
        JSlider slider;
        JTextField textField;

        Slider(int direction, int minValue, int maxValue, int initValue) {
            slider = new JSlider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minBirthRate, DataBase.GlobalConfig.maxBirthRate, (int)(Fox.config.breedRate*100));
            textField = new JTextField(Integer.toString(initValue));
        }

        JPanel getPanel() {
            JPanel resultPanel = new JPanel();
            resultPanel.add(slider);
            resultPanel.add(textField);
            return resultPanel;
        }
    }


    private Slider foxBirthRateSlider = new Slider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minBirthRate, DataBase.GlobalConfig.maxBirthRate, (int)(Fox.config.breedRate*100));
    private Slider hareBirthRateSlider = new Slider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minBirthRate, DataBase.GlobalConfig.maxBirthRate, (int)(Hare.config.breedRate*100));

    private SettingsPanel() {
        SimulationManager simMng = SimulationManager.getInstance();

        foxBirthRateSlider.slider.addChangeListener(e -> {
            DataBase.getConfig(Fox.class).breedRate = (double)(foxBirthRateSlider.slider.getValue())/100;
        });

        hareBirthRateSlider.slider.addChangeListener(e -> {
            DataBase.getConfig(Hare.class).breedRate = (double)(hareBirthRateSlider.slider.getValue())/100;
        });

        setLayout(new GridLayout(0, 1));
        add(getComponentWithVerticalTitle(hareBirthRateSlider.getPanel(),"Hare Birth Rate"));
        add(getComponentWithVerticalTitle(foxBirthRateSlider.getPanel(),"Fox Birth Rate"));
    }
}