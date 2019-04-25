import engine.MonoBehaviour;
import engine.Time;
import engine.Viewport;
import extensions.Vector2d;

import java.awt.*;

public class Fox extends Animal{
    @Override
    protected void setup() {
        super.setup();
        System.out.println("The fox has been created!");
        addBehaviour(new FoxIdle());
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.orange);

        Dimension screenPos = Viewport.worldToScreenPoint(position);
        g.fillOval(screenPos.width, screenPos.height, 10, 10);
    }

    class FoxIdle extends MonoBehaviour {

        @Override
        public void action() {
            Vector2d move = new Vector2d(3, 3);
            move.scale(Time.getDeltaTime());
            position.add(move);
        }

        @Override
        public boolean done() {
            return false;
        }
    }
}