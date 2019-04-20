import javax.vecmath.Vector2d;

public class Scene {
    private static Scene instance = null;

    private Scene() {}

    public static Scene getInstance() {
        if (instance == null)
            instance = new Scene();

        return instance;
    }

    public Vector2d size;
}
