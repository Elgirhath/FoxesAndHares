package engine;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static <T extends AnimationAgent> List<T> findAgentsOfType(Class<T> type) {
        List<T> list = new ArrayList<>();

        for (AnimationAgent agent : AnimationAgent.getAgents()) {
            if (agent.getClass() == type) {
                list.add((T) agent);
            }
        }
        return list;
    }
}
