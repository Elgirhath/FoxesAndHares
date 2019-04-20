import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.List;

public class SimulationManager extends Agent {
    private List<AgentController> agents = new ArrayList<>();

    @Override
    protected void setup() {
        System.out.println("Simulation Manager has been created!\n");
        start();
    }

    void start() {
        //Create all agents and manage the simulation;
        Map.getInstance().size = new Vector2d(1000f, 1000f);

        ContainerController container = getContainerController();
        try {
            AgentController ac = container.createNewAgent("Fox1", "Fox", null);
            ac.start();
            agents.add(ac);
            AgentController hc = container.createNewAgent("Hare1", "Hare", null);
            hc.start();
            agents.add(hc);
        } catch (Exception e) {}
    }
}
