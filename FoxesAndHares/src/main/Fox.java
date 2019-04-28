package main;

import engine.Viewport;

import java.awt.*;

public class Fox extends Animal{

    @Override
    protected void setup() {
        super.setup();
        color = Color.orange;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        int radius = 5;

        Dimension screenPos = Viewport.worldToScreenPoint(position).toDimension();
        g.fillOval(screenPos.width - radius, screenPos.height - radius, 2*radius, 2*radius);
    }
}