package engine;

import javax.swing.*;
import java.awt.*;
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

    class Slider extends JComponent {
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

        Slider(int direction, int min, int max, int initValue, String tfInitValue) {
            minValue = min;
            maxValue = max;
            slider = new JSlider(direction, minValue, maxValue, initValue);
            textField = new JTextField(tfInitValue, 3);
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

    JTextField hareInit = new JTextField(Integer.toString(DataBase.getConfig(Hare.class).initPopulation), 3);
    JTextField foxInit = new JTextField(Integer.toString(DataBase.getConfig(Fox.class).initPopulation), 3);

    private Slider foxBirthRateSlider = new Slider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minBirthRate, DataBase.GlobalConfig.maxBirthRate, (int)(Fox.config.breedRate*100));
    private Slider hareBirthRateSlider = new Slider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minBirthRate, DataBase.GlobalConfig.maxBirthRate, (int)(Hare.config.breedRate*100));

    private Slider maxHungerSlider = new Slider(SwingConstants.HORIZONTAL, 10, 50, (int)DataBase.GlobalConfig.maxHunger, Double.toString(DataBase.GlobalConfig.maxHunger));
    private Slider hungerPerMealSlider = new Slider(SwingConstants.HORIZONTAL, 10, 50, (int)DataBase.GlobalConfig.hungerPerMeal, Double.toString(DataBase.GlobalConfig.hungerPerMeal));
    private Slider hungerLossPerSecSlider = new Slider(SwingConstants.HORIZONTAL, 1, 10, (int)(DataBase.GlobalConfig.hungerLossPerSec*10), Double.toString(DataBase.GlobalConfig.hungerLossPerSec));
    private Slider minBreedHungerPctSlider = new Slider(SwingConstants.HORIZONTAL, 1, 10, (int)(DataBase.GlobalConfig.minBreedHungerPct*10), Double.toString(DataBase.GlobalConfig.minBreedHungerPct));
    private Slider maxHuntHungerPctSlider = new Slider(SwingConstants.HORIZONTAL, 1, 10, (int)(DataBase.GlobalConfig.maxHuntHungerPct*10), Double.toString(DataBase.GlobalConfig.maxHuntHungerPct));
    private Slider genderMaxPercentageSlider = new Slider(SwingConstants.HORIZONTAL, 1, 100, (int)(DataBase.GlobalConfig.genderMaxPercentage*100), Double.toString(DataBase.GlobalConfig.genderMaxPercentage));
    private Slider yearDurationSlider = new Slider(SwingConstants.HORIZONTAL, 30, 80, (int)DataBase.GlobalConfig.yearDuration, Double.toString(DataBase.GlobalConfig.yearDuration));
    private Slider fertilenessFrequencySlider = new Slider(SwingConstants.HORIZONTAL, 10, 50, (int)DataBase.GlobalConfig.fertilenessFrequency, Double.toString(DataBase.GlobalConfig.fertilenessFrequency));
    private Slider tryBreedFrequencySlider = new Slider(SwingConstants.HORIZONTAL, 1, 8, (int)DataBase.GlobalConfig.tryBreedFrequency, Double.toString(DataBase.GlobalConfig.tryBreedFrequency));

    private SettingsPanel() {

        setLayout(new GridLayout(0,1));
        setPreferredSize(new Dimension(DataBase.GlobalConfig.simWidth/3, DataBase.GlobalConfig.simHeight/3-50));
        setBreedRateSliderListener(foxBirthRateSlider, Fox.class);
        setBreedRateSliderListener(hareBirthRateSlider, Hare.class);
        setInitValueListener(hareInit, Hare.class);
        setInitValueListener(foxInit, Fox.class);
        setGlobalConfigListeners();

        foxInit.setHorizontalAlignment(JTextField.RIGHT);
        hareInit.setHorizontalAlignment(JTextField.RIGHT);

        //JPanel initPanel = new JPanel();

        //JPanel initHarePanel = new JPanel();
        //initHarePanel.setLayout(new BoxLayout(initHarePanel, BoxLayout.Y_AXIS));
        //initHarePanel.setLayout(new GridLayout(0, 1));
        //initHarePanel.add(hareInit);
        //hareInit.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(getComponentWithHorizontalTitle(hareInit, "Hare Initial Population"));
        add(getComponentWithHorizontalTitle(foxInit, "Fox Initial Population"));
        add(getComponentWithHorizontalTitle(hareBirthRateSlider.getPanel(),"Hare Birth Rate",
                "Determines the possibility of reproducing when two hares meet"));

        //JPanel initFoxPanel = new JPanel();
        //initFoxPanel.setLayout(new GridLayout(0, 1));
        add(getComponentWithHorizontalTitle(foxBirthRateSlider.getPanel(),"Fox Birth Rate",
                "Determines the possibility of reproducing when two foxes meet"));

        //initPanel.add(initHarePanel);
        //initPanel.add(initFoxPanel);

        //JPanel slidersPanel = new JPanel();
        //slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.Y_AXIS));
        add(getComponentWithHorizontalTitle(maxHungerSlider.getPanel(),"Max hunger",
                "Sets the total amount of hunger points"));

        add(getComponentWithHorizontalTitle(hungerPerMealSlider.getPanel(),"Hun/meal",
                "Determines how many hunger points are restored when a hare is eaten"));

        add(getComponentWithHorizontalTitle(hungerLossPerSecSlider.getPanel(),"Hunger loss",
                "Determines how many hunger points drop every second"));

        add(getComponentWithHorizontalTitle(minBreedHungerPctSlider.getPanel(),"Min BreedHunger Percentage",
                "Determines at what level of fullness foxes don't reproduce and focus on hunting"));

        add(getComponentWithHorizontalTitle(maxHuntHungerPctSlider.getPanel(),"Max HuntHunger Percentage",
                "Sets the level of hunger, at which foxes don't hunt."));

        add(getComponentWithHorizontalTitle(genderMaxPercentageSlider.getPanel(),"Gender Max Percentage",
                "Sets how many animals can be of the same gender at initialization"));

        add(getComponentWithHorizontalTitle(yearDurationSlider.getPanel(),"Year Duration",
                "Defines year duration in seconds (on default time scale). It influences the speed of aging of animals," +
                        "\n which then influences the time at which animals became mature and die"));

        add(getComponentWithHorizontalTitle(fertilenessFrequencySlider.getPanel(),"Fertileness frequency",
                "Determines how much time animals wait after reproducing (in seconds, in default time scale)"));

        add(getComponentWithHorizontalTitle(tryBreedFrequencySlider.getPanel(),"Breeding Attempts Frequency",
                "Determines how many seconds animals wait every time they try to reproduce"));

        //add(initPanel);
        //add(slidersPanel);
    }

    private void setBreedRateSliderListener(Slider slider, Class cls) {
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
                        DataBase.getConfig(cls).breedRate = (double)(value)/100;
                        slider.slider.setValue(value);
                    }
                }
        );

    }

    private void setInitValueListener(JTextField tf, Class cls) {
        tf.addActionListener(
                e -> {
                    if(Integer.parseInt(tf.getText()) != NaN){
                        int value = Integer.parseInt(tf.getText());
                        DataBase.getConfig(cls).initPopulation = value;
                        if(value * 1.5 > PlotPanel.getInstance().yLength)
                            PlotPanel.getInstance().changeYAxis((int)(value*1.5));
                    }
                }
        );
    }

    private void setGlobalConfigListeners() {
        maxHungerSlider.slider.addChangeListener(e -> {
            double sliderValue = (double)maxHungerSlider.slider.getValue();
            DataBase.GlobalConfig.maxHunger = sliderValue;
            maxHungerSlider.textField.setText(Double.toString(sliderValue));
        });
        maxHungerSlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(maxHungerSlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(maxHungerSlider.textField.getText());
                        if(value > maxHungerSlider.maxValue)
                            value = maxHungerSlider.maxValue;
                        if(value < maxHungerSlider.minValue)
                            value = maxHungerSlider.minValue;
                        DataBase.GlobalConfig.maxHunger = value;
                        maxHungerSlider.slider.setValue((int)value);
                    }
                }
        );

        hungerPerMealSlider.slider.addChangeListener(e -> {
            double sliderValue = (double)hungerPerMealSlider.slider.getValue();
            DataBase.GlobalConfig.hungerPerMeal = sliderValue;
            hungerPerMealSlider.textField.setText(Double.toString(sliderValue));
        });
        hungerPerMealSlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(hungerPerMealSlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(hungerPerMealSlider.textField.getText());
                        if(value > hungerPerMealSlider.maxValue)
                            value = hungerPerMealSlider.maxValue;
                        if(value < hungerPerMealSlider.minValue)
                            value = hungerPerMealSlider.minValue;
                        DataBase.GlobalConfig.hungerPerMeal = value;
                        hungerPerMealSlider.slider.setValue((int)value);
                    }
                }
        );

        yearDurationSlider.slider.addChangeListener(e -> {
            double sliderValue = (double)yearDurationSlider.slider.getValue();
            DataBase.GlobalConfig.yearDuration = sliderValue;
            yearDurationSlider.textField.setText(Double.toString(sliderValue));
        });

        yearDurationSlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(yearDurationSlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(yearDurationSlider.textField.getText());
                        if(value > yearDurationSlider.maxValue)
                            value = yearDurationSlider.maxValue;
                        if(value < yearDurationSlider.minValue)
                            value = yearDurationSlider.minValue;
                        DataBase.GlobalConfig.yearDuration = value;
                        yearDurationSlider.slider.setValue((int)value);
                    }
                }
        );

        fertilenessFrequencySlider.slider.addChangeListener(e -> {
            double sliderValue = (double)fertilenessFrequencySlider.slider.getValue();
            DataBase.GlobalConfig.fertilenessFrequency = sliderValue;
            fertilenessFrequencySlider.textField.setText(Double.toString(sliderValue));
        });
        fertilenessFrequencySlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(fertilenessFrequencySlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(fertilenessFrequencySlider.textField.getText());
                        if(value > fertilenessFrequencySlider.maxValue)
                            value = fertilenessFrequencySlider.maxValue;
                        if(value < fertilenessFrequencySlider.minValue)
                            value = fertilenessFrequencySlider.minValue;
                        DataBase.GlobalConfig.fertilenessFrequency = value;
                        fertilenessFrequencySlider.slider.setValue((int)value);
                    }
                }
        );

        tryBreedFrequencySlider.slider.addChangeListener(e -> {
            double sliderValue = (double)tryBreedFrequencySlider.slider.getValue();
            DataBase.GlobalConfig.tryBreedFrequency = sliderValue;
            tryBreedFrequencySlider.textField.setText(Double.toString(sliderValue));
        });
        tryBreedFrequencySlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(tryBreedFrequencySlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(tryBreedFrequencySlider.textField.getText());
                        if(value > tryBreedFrequencySlider.maxValue)
                            value = tryBreedFrequencySlider.maxValue;
                        if(value < tryBreedFrequencySlider.minValue)
                            value = tryBreedFrequencySlider.minValue;
                        DataBase.GlobalConfig.tryBreedFrequency = value;
                        tryBreedFrequencySlider.slider.setValue((int)value);
                    }
                }
        );

        hungerLossPerSecSlider.slider.addChangeListener(e -> {
            double sliderValue = (double)hungerLossPerSecSlider.slider.getValue();
            DataBase.GlobalConfig.hungerLossPerSec = sliderValue/10;
            hungerLossPerSecSlider.textField.setText(Double.toString(sliderValue/10));
        });
        hungerLossPerSecSlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(hungerLossPerSecSlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(hungerLossPerSecSlider.textField.getText());
                        if(value > (double)hungerLossPerSecSlider.maxValue/10)
                            value = (double)hungerLossPerSecSlider.maxValue/10;
                        if(value < (double)hungerLossPerSecSlider.minValue/10)
                            value = (double)hungerLossPerSecSlider.minValue/10;
                        DataBase.GlobalConfig.hungerLossPerSec = value;
                        hungerLossPerSecSlider.slider.setValue((int)(value*10));
                    }
                }
        );
        minBreedHungerPctSlider.slider.addChangeListener(e -> {
            double sliderValue = (double)minBreedHungerPctSlider.slider.getValue();
            DataBase.GlobalConfig.minBreedHungerPct = sliderValue/10;
            minBreedHungerPctSlider.textField.setText(Double.toString(sliderValue/10));
        });
        minBreedHungerPctSlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(minBreedHungerPctSlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(minBreedHungerPctSlider.textField.getText());
                        if(value > (double)minBreedHungerPctSlider.maxValue/10)
                            value = (double)minBreedHungerPctSlider.maxValue/10;
                        if(value < (double)minBreedHungerPctSlider.minValue/10)
                            value = (double)minBreedHungerPctSlider.minValue/10;
                        DataBase.GlobalConfig.minBreedHungerPct = value;
                        minBreedHungerPctSlider.slider.setValue((int)(value*10));
                    }
                }
        );
        maxHuntHungerPctSlider.slider.addChangeListener(e -> {
            double sliderValue = (double)maxHuntHungerPctSlider.slider.getValue();
            DataBase.GlobalConfig.maxHuntHungerPct = sliderValue/10;
            maxHuntHungerPctSlider.textField.setText(Double.toString(sliderValue/10));
        });
        maxHuntHungerPctSlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(maxHuntHungerPctSlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(maxHuntHungerPctSlider.textField.getText());
                        if(value > (double)maxHuntHungerPctSlider.maxValue/10)
                            value = (double)maxHuntHungerPctSlider.maxValue/10;
                        if(value < (double)maxHuntHungerPctSlider.minValue/10)
                            value = (double)maxHuntHungerPctSlider.minValue/10;
                        DataBase.GlobalConfig.maxHuntHungerPct = value;
                        maxHuntHungerPctSlider.slider.setValue((int)(value*10));
                    }
                }
        );
        genderMaxPercentageSlider.slider.addChangeListener(e -> {
            double sliderValue = (double)genderMaxPercentageSlider.slider.getValue();
            DataBase.GlobalConfig.genderMaxPercentage = sliderValue/100;
            genderMaxPercentageSlider.textField.setText(Double.toString(sliderValue/100));
        });
        genderMaxPercentageSlider.textField.addActionListener(
                e -> {
                    if(Double.parseDouble(genderMaxPercentageSlider.textField.getText()) != NaN){
                        double value = Double.parseDouble(genderMaxPercentageSlider.textField.getText());
                        if(value > (double)genderMaxPercentageSlider.maxValue/100)
                            value = (double)genderMaxPercentageSlider.maxValue/100;
                        if(value < (double)genderMaxPercentageSlider.minValue/100)
                            value = (double)genderMaxPercentageSlider.minValue/100;
                        DataBase.GlobalConfig.genderMaxPercentage = value;
                        genderMaxPercentageSlider.slider.setValue((int)(value*100));
                    }
                }
        );
    }
}