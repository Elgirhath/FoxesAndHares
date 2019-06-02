package main;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static Map<Class, Data> map = new HashMap<>();
    private static Config config = new Config();

    public static Data createData(Class cls) {
        Data data = new Data();
        map.put(cls, data);
        return data;
    }

    public static Data getData(Class cls) {
        return map.get(cls);
    }

    public static Config getConfig() {
        return config;
    }

    public static class Data {
        public int count;
        public int maleCount;
        public int initializedCount;
    }

    public static class Config {
        public double maxHunger = 30.0;
        public double hungerPerMeal = 15.0;
        public double hungerLossPerSec = 0.1;
    }
}
