package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import main.DataBase;
import main.Hare;
import main.Fox;

import static java.lang.Double.NaN;

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
        int minValue;
        int maxValue;

        Slider(int direction, int min, int max, int initValue) {
            minValue = min;
            maxValue = max;
            slider = new JSlider(direction, minValue, maxValue, initValue);
            textField = new JTextField(Integer.toString(initValue), 3);
            textField.setHorizontalAlignment(JTextField.RIGHT);

            //addLabels(slider, minValue, maxValue);
        }

        private void addLabels(JSlider slider, int minValue, int maxValue) {

            Hashtable labels = new Hashtable();
            labels.put(minValue, new JLabel(Integer.toString(minValue)));
            if(maxValue % 2 == 0) {
                labels.put(maxValue/2, new JLabel(Integer.toString(maxValue/2)));
            }
            labels.put(maxValue, new JLabel(Integer.toString(maxValue)));

            slider.setLabelTable(labels);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(maxValue/2-1);
            slider.setPaintTicks(true);
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
        setSliderListener(foxBirthRateSlider, Fox.class);
        setSliderListener(hareBirthRateSlider, Hare.class);

        setLayout(new GridLayout(0, 1));
        add(getComponentWithHorizontalTitle(hareBirthRateSlider.getPanel(),"Hare Birth Rate"));
        add(getComponentWithHorizontalTitle(foxBirthRateSlider.getPanel(),"Fox Birth Rate"));
    }

    private void setSliderListener(Slider slider, Class cls) {
        slider.slider.addChangeListener(e -> {
            int sliderValue = slider.slider.getValue();
            DataBase.getConfig(cls).breedRate = (double)(sliderValue)/100;
            slider.textField.setText(Integer.toString(sliderValue));
        });

        slider.textField.addActionListener(
                e -> {
                    if(Integer.parseInt(slider.textField.getText()) != NaN){
                        int value = Integer.parseInt(slider.textField.getText());
                        if(value > slider.maxValue)
                            value = slider.maxValue;
                        if(value < slider.minValue)
                            value = slider.minValue;
                        System.out.println(value);
                        DataBase.getConfig(cls).breedRate = (double)(value)/100;
                        slider.slider.setValue(value);
                    }
                }
        );

    }
}