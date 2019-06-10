package engine;

import main.DataBase;
import main.Fox;
import main.Hare;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlotPanel extends JPanel {

    private static final Color sharedColor = new Color(153,102,0);

    private static PlotPanel instance = null;

    public static PlotPanel getInstance() {
        if (instance == null)
            instance = new PlotPanel();

        return instance;
    }

    Plot plot;

    private int xLength = 300;
    int yLength = 90;
    private int width;
    private int height;

    private int xPadding = 20;
    private int yPadding = 10;
    private double xAxis;
    private double yAxis;
    private PlotPanel() {
        width = DataBase.GlobalConfig.simWidth/3;
        height = DataBase.GlobalConfig.simHeight*2/9;
        setPreferredSize(new Dimension(width, height));
        int initFox = DataBase.getConfig(Fox.class).initPopulation;
        int initHare = DataBase.getConfig(Hare.class).initPopulation;
        if(initFox > initHare)
            yLength = (int)(initFox * 1.5);
        else
            yLength = (int)(initHare * 1.5);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Population/time plot");
        //xPadding = title.getPreferredSize().width/4;
        //int titlePadding = (width - xPadding)/3;
        //title.setBorder(new EmptyBorder(0,titlePadding,0,titlePadding));
        plot = new Plot();
        plot.setAlignmentY(SwingConstants.BOTTOM);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(plot);
        xLength = width - 2*xPadding;
        xAxis = (width - 2* xPadding)/xLength;
        yAxis = (height - 2* yPadding)/yLength;
    }

    void changeYAxis(int y) {
        yLength = y;
        yAxis = (height - 2* yPadding)/yLength;
    }

    class Plot extends JPanel {

        List <Integer> foxPlot = new ArrayList<>();
        List <Integer> harePlot = new ArrayList<>();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.drawLine(xPadding, yPadding, xPadding, height- yPadding);
            g2.drawLine(xPadding, height - yPadding, width- xPadding, height- yPadding);
            if(!SimulationManager.getInstance().running)
                return;
            if(!SimulationManager.getInstance().paused)
                updatePlot();

            int foxes;
            int hares;
            for(int i = 0; i < foxPlot.size(); ++i) {
                foxes = foxPlot.get(i);
                hares = harePlot.get(i);
                if(foxes == hares) {
                    drawPoint(i, foxes, g2, sharedColor);
                    continue;
                }
                drawPoint(i, foxPlot.get(i), g2, Color.orange);
                drawPoint(i, harePlot.get(i), g2, Color.green);
            }
        }

        private void drawPoint(int xx, int yy, Graphics2D g2, Color color) {
            int x0 = xPadding;
            int y0 = height- yPadding;
            g2.setPaint(color);
            int x = x0 + (int)(xAxis * (xx+1));
            int y = y0 - (int)(yAxis * yy);
            g2.fillOval(x-2, y-2, 4, 4);
        }

        private void updatePlot() {
            foxPlot.add(DataBase.getData(Fox.class).count);
            harePlot.add(DataBase.getData(Hare.class).count);
            if(foxPlot.size() >= xLength){
                foxPlot.remove(0);
                harePlot.remove(0);
            }
        }
    }
}