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

    //Sliders
    private JSlider foxBirthRateSlider = new JSlider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minBirthRate, DataBase.GlobalConfig.maxBirthRate, (int)(Fox.config.breedRate*100));
    private JSlider hareBirthRateSlider = new JSlider(SwingConstants.HORIZONTAL, DataBase.GlobalConfig.minBirthRate, DataBase.GlobalConfig.maxBirthRate, (int)(Hare.config.breedRate*100));

    private SettingsPanel() {
        SimulationManager simMng = SimulationManager.getInstance();

        foxBirthRateSlider.addChangeListener(e -> {
            DataBase.getConfig(Fox.class).breedRate = (double)(foxBirthRateSlider.getValue())/100;
        });

        hareBirthRateSlider.addChangeListener(e -> {
            DataBase.getConfig(Hare.class).breedRate = (double)(hareBirthRateSlider.getValue())/100;
        });

        setLayout(new GridLayout(0, 1));
        add(getComponentWithVerticalTitle(hareBirthRateSlider,"Hare Birth Rate"));
        add(getComponentWithVerticalTitle(foxBirthRateSlider,"Fox Birth Rate"));
    }
}