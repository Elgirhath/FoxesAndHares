package engine;

import extensions.Vector2d;
import java.awt.*;

public class Debug {
    public static void drawLine(Vector2d start, Vector2d end, Color color, double duration) {
        new Line(start, end, color, duration);
    }

    private static class Line extends GraphicComponent {
        double timeLeft;
        Vector2d start;
        Vector2d end;
        Color color;

        Line(Vector2d start, Vector2d end, Color color, double duration) {
            super();

            timeLeft = duration;
            this.start = start;
            this.end = end;
            this.color = color;
        }

        @Override
        public void paint(Graphics g) {
            Dimension startDim = start.toDimension();
            Dimension endDim = end.toDimension();
            Color tmp = g.getColor();
            g.setColor(color);
            g.drawLine(startDim.width, startDim.height, endDim.width, endDim.height);
            g.setColor(tmp);

            timeLeft -= Time.getDeltaTime();
            if (timeLeft < 0)
                enabled = false;
        }
    }
}
