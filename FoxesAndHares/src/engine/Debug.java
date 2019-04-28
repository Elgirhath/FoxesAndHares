package engine;

import extensions.Vector2d;

import java.awt.*;

public class Debug {
    private static SimulationPanel simPanel = SimulationPanel.getInstance();

    public static void drawLine(Vector2d start, Vector2d end, Color color, double duration) {
        Line line = new Line(start, end, color, duration);
        simPanel.addComponent(line);
    }

    private static class Line implements GraphicComponent {
        double timeLeft;
        Vector2d start;
        Vector2d end;
        Color color;

        Line(Vector2d start, Vector2d end, Color color, double duration) {
            timeLeft = duration;
            this.start = start;
            this.end = end;
            this.color = color;

            new Timer();
        }

        @Override
        public void paintComponent(Graphics g) {
            Dimension startDim = start.toDimension();
            Dimension endDim = end.toDimension();
            Color tmp = g.getColor();
            g.setColor(color);
            g.drawLine(startDim.width, startDim.height, endDim.width, endDim.height);
            g.setColor(tmp);
        }

        private class Timer extends MonoBehaviour {
            @Override
            public void action() {
                Line.this.timeLeft -= Time.getDeltaTime();
                if (timeLeft < 0) {
                    Debug.simPanel.removeComponent(Line.this);
                }
            }
        }
    }
}
