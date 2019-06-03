package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            slider = new JSlider(direction, minValue, maxValue, initValue);
            textField = new JTextField(Integer.toString(initValue), 3);
            textField.setHorizontalAlignment(JTextField.RIGHT);
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
        foxBirthRateSlider.slider.addChangeListener(e -> {
            int sliderValue = foxBirthRateSlider.slider.getValue();
            DataBase.getConfig(Fox.class).breedRate = (double)(sliderValue)/100;
            foxBirthRateSlider.textField.setText(Integer.toString(sliderValue));
        });

        hareBirthRateSlider.slider.addChangeListener(e -> {
            int sliderValue = hareBirthRateSlider.slider.getValue();
            DataBase.getConfig(Hare.class).breedRate = (double)(sliderValue)/100;
            hareBirthRateSlider.textField.setText(Integer.toString(sliderValue));
        });

        foxBirthRateSlider.textField.addActionListener(
                e -> {
                    int value = Integer.valueOf(foxBirthRateSlider.textField.getText());
                    DataBase.getConfig(Fox.class).breedRate = (double)(value)/100;
                    foxBirthRateSlider.slider.setValue(value);
                }
        );

        hareBirthRateSlider.textField.addActionListener(
                e -> {
                    int value = Integer.valueOf(hareBirthRateSlider.textField.getText());
                    DataBase.getConfig(Hare.class).breedRate = (double)(value)/100;
                    hareBirthRateSlider.slider.setValue(value);
                }
        );


        setLayout(new GridLayout(0, 1));
        add(getComponentWithVerticalTitle(hareBirthRateSlider.getPanel(),"Hare Birth Rate"));
        add(getComponentWithVerticalTitle(foxBirthRateSlider.getPanel(),"Fox Birth Rate"));
    }
}