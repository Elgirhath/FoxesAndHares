package engine;

import extensions.Vector2d;
import java.awt.*;

public class Debug {
    public static void drawLine(Vector2d start, Vector2d end, Color color, double duration) {
        Line line = new Line(start, end, color, duration);
        SimulationPanel.getInstance().add(line);
    }

    private static class Line extends Component {
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
            System.out.println("XDDD");
            g.drawOval(0, 0, 100, 100);
            Dimension startDim = start.toDimension();
            Dimension endDim = end.toDimension();
            Color tmp = g.getColor();
            g.setColor(color);
            g.drawLine(startDim.width, startDim.height, endDim.width, endDim.height);
            g.setColor(tmp);
            System.out.println("AAAAAA");

//            timeLeft -= Time.getDeltaTime();
//            if (timeLeft < 0)
//                enabled = false;
        }
    }
}
