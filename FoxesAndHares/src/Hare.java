import engine.Viewport;

import java.awt.*;

public class Hare extends Animal {
    @Override
    protected void setup() {
        super.setup();
        System.out.println("The hare has been created!");
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.green);

        Dimension screenPos = Viewport.worldToScreenPoint(position);
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }
}
