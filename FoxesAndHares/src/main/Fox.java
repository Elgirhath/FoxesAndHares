package main;

import engine.Viewport;

import java.awt.*;

public class Fox extends Animal{
    @Override
    protected void setup() {
        super.setup();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.orange);

        Dimension screenPos = Viewport.worldToScreenPoint(position);
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }
}