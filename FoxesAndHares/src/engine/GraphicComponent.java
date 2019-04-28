package engine;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public abstract class GraphicComponent extends Component{
    public boolean enabled = true;
    public abstract void paint(Graphics g);

    private static List<GraphicComponent> componentList = Collections.synchronizedList(new ArrayList<>());

    public static GraphicComponent[] getComponentList() {
        synchronized (componentList) {
            GraphicComponent[] arr = new GraphicComponent[componentList.size()];
            componentList.toArray(arr);
            return arr;
        }
    }

    GraphicComponent() {
        synchronized (componentList) {
            componentList.add(this);
        }
    }
}