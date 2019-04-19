import javax.vecmath.Vector2d;

public class Map {
    private static Map instance = null;

    private Map() {}

    public static Map getInstance() {
        if (instance == null)
            instance = new Map();

        return instance;
    }

    public Vector2d size;
}
