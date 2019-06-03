package main;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static Map<Class, Data> dataMap = new HashMap<>();
    private static Map<Class, Config> configMap = new HashMap<>();
    private static GlobalConfig globalConfig = new GlobalConfig();

    static {
        Config foxInitConfig = getConfig(Fox.class);
        foxInitConfig.breedRate = 0.2;

        Config hareInitConfig = getConfig(Hare.class);
        hareInitConfig.breedRate = 0.85;
    }

    private static Data createData(Class cls) {
        Data data = new Data();
        dataMap.put(cls, data);
        return data;
    }

    public static Data getData(Class cls) {
        if (dataMap.containsKey(cls))
            return dataMap.get(cls);
        else
            return createData(cls);
    }

    private static Config createConfig(Class cls) {
        Config config = new Config();
        configMap.put(cls, config);
        return config;
    }
    public static Config getConfig(Class cls) {
        if (configMap.containsKey(cls))
            return configMap.get(cls);
        else
            return createConfig(cls);
    }

    public static GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public static class Data {
        public int count;
        public int maleCount;
        public int initializedCount;
    }

    public static class Config {
        public double breedRate = 0.4;
        public double lifespan = 13.0;
        public double minBreedAge = 2.0;
    }

    public static class GlobalConfig {
        public static double maxHunger = 30.0;
        public static double hungerPerMeal = 23.0;
        public static double hungerLossPerSec = 0.4;
        public static double minBreedHungerPct = 0.5;
        public static double maxHuntHungerPct = 0.9;

        public static int maxSimSpeed = 100;
        public static int minSimSpeed = 1;
        public static int maxBirthRate = 100;
        public static int minBirthRate = 1;
        public static int initialSimSpeed = 10;

        public static double genderMaxPercentage = 0.65;
        public static double yearDuration = 60.0;

        public static double fertilenessFrequency = 20.0;
        public static double tryBreedFrequency = 3.0;
    }
}
