package main;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static Map<Class, Data> map = new HashMap<>();

    public static Data createData(Class cls) {
        Data data = new Data();
        map.put(cls, data);
        return data;
    }

    public static Data getData(Class cls) {
        return map.get(cls);
    }

    public static class Data {
        public int count;
        public int maleCount;
        public int initializedCount;
    }
}
