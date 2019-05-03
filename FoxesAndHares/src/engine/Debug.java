package engine;

import extensions.Vector2d;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import javax.swing.text.View;
import java.awt.*;

public class Debug {
    private static SimulationPanel simPanel = SimulationPanel.getInstance();

    public static void drawLine(Vector2d start, Vector2d end, Color color, double duration) {
        Line line = new Line(Viewport.worldToScreenPoint(start), Viewport.worldToScreenPoint(end), color, duration);
        simPanel.addComponent(line);
    }

    public static void drawRay(Vector2d origin, Vector2d direction, Color color, double duration) {
        drawLine(origin, origin.plus(direction), color, duration);
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

            MonoBehaviour.addMonoBehaviour(new Timer());
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
                    destroy(this);
                }
            }
        }
    }

    public static void drawGrid(double size, Color color) {
        Grid grid = new Grid(size, color);
        simPanel.addComponent(grid);
    }

    private static class Grid implements GraphicComponent{
        Color color;
        double size;

        Grid(double size, Color color) {
            this.size = size;
            this.color = color;
        }

        @Override
        public void paintComponent(Graphics g) {
            Color tmp = g.getColor();
            g.setColor(color);
            for (double i = 0.0; i < Viewport.getWorldSpaceSize().x; i += size) { //vertical
                Dimension start = Viewport.worldToScreenPoint(new Vector2d(i, 0.0)).toDimension();
                Dimension end = Viewport.worldToScreenPoint(new Vector2d(i, Viewport.getWorldSpaceSize().y)).toDimension();
                g.drawLine(start.width, start.height, end.width, end.height);
            }
            for (double j = 0.0; j < Viewport.getWorldSpaceSize().y; j += size) {
                Dimension start = Viewport.worldToScreenPoint(new Vector2d(0.0, j)).toDimension();
                Dimension end = Viewport.worldToScreenPoint(new Vector2d(Viewport.getWorldSpaceSize().x, j)).toDimension();
                g.drawLine(start.width, start.height, end.width, end.height);
            }
            g.setColor(tmp);
        }
    }


    private static class Grid implements GraphicComponent{
        Color color;
        double size;

        Grid(double size, Color color) {
            this.size = size;
            this.color = color;
        }

        @Override
        public void paintComponent(Graphics g) {
            Color tmp = g.getColor();
            g.setColor(color);
            for (double i = 0.0; i < Viewport.getWorldSpaceSize().x; i += size) { //vertical
                Dimension start = Viewport.worldToScreenPoint(new Vector2d(i, 0.0)).toDimension();
                Dimension end = Viewport.worldToScreenPoint(new Vector2d(i, Viewport.getWorldSpaceSize().y)).toDimension();
                g.drawLine(start.width, start.height, end.width, end.height);
            }
            for (double j = 0.0; j < Viewport.getWorldSpaceSize().y; j += size) {
                Dimension start = Viewport.worldToScreenPoint(new Vector2d(0.0, j)).toDimension();
                Dimension end = Viewport.worldToScreenPoint(new Vector2d(Viewport.getWorldSpaceSize().x, j)).toDimension();
                g.drawLine(start.width, start.height, end.width, end.height);
            }
            g.setColor(tmp);
        }
    }
}
