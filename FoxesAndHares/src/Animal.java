import jade.core.Agent;

import javax.vecmath.Vector2d;

public class Animal extends Agent {
    public Vector2d position;

    @Override
    protected void setup() {
        System.out.println("An animal has been created!\n");
    }
}
