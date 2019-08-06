package me.nosmakos.killshot.itemnbt;

import com.google.gson.Gson;

public class GsonWrapper {
    private static final Gson gson = new Gson();

    public GsonWrapper() {
    }

    public static String getString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T deserializeJson(String json, Class<T> type) {
        try {
            if(json == null) {
                return null;
            } else {
                T obj = gson.fromJson(json, type);
                return type.cast(obj);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
