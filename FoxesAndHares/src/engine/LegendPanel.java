package engine;

import javax.swing.*;

public class LegendPanel extends JPanel {

    private static LegendPanel instance = null;

    public static LegendPanel getInstance() {
        if (instance == null)
            instance = new LegendPanel();

        return instance;
    }


}