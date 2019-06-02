package engine;

import javax.swing.*;
import java.awt.*;

public class PlotPanel extends JPanel {

    private static PlotPanel instance = null;

    public static PlotPanel getInstance() {
        if (instance == null)
            instance = new PlotPanel();

        return instance;
    }

    private PlotPanel() {
        add(new JLabel("TU BEDZIE WYKRES"));
    }
}