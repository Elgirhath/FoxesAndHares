package engine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlotPanel extends JPanel {

    private static PlotPanel instance = null;

    public static PlotPanel getInstance() {
        if (instance == null)
            instance = new PlotPanel();

        return instance;
    }

    final int padding = 15;
    final int xLength = 100;
    final int yLength = 100;
    final int width = 400;
    final int height = 200;

    class Plot extends JPanel {

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.drawLine(padding, padding, padding, height- padding);
            g2.drawLine(padding, height- padding, width- padding, height- padding);
        }

        private Plot() {
            setSize(width, height);
        }
    }

    private PlotPanel() {
        setLayout(new GridLayout(0, 1));

        JLabel title = new JLabel("TU BEDZIE WYKRES", SwingConstants.CENTER);
        int titlePadding = (width - title.getWidth())/2;
        title.setBorder(new EmptyBorder(0,titlePadding,0,titlePadding));
        Plot plot = new Plot();
        plot.setBorder(new EmptyBorder(0,title.getWidth(),0,title.getWidth()));

        add(title);
        add(plot);
    }
}