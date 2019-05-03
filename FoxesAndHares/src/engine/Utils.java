package engine;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static <T extends AnimationAgent> List<T> findAgentsOfType(Class<T> type, boolean includeDerived) {
        List<T> list = new ArrayList<>();

        for (AnimationAgent agent : AnimationAgent.getAgents()) {
            if (includeDerived) {
                if (type.isInstance(agent)) {
                    list.add((T) agent);
                }
            }
            else {
                if (agent.getClass() == type) {
                    list.add((T) agent);
                }
            }
        }
        return list;
    }

    public static <T extends AnimationAgent> List<T> findAgentsOfType(Class<T> type) {
        return findAgentsOfType(type, true);
    }
}
