package test.java.com.yandex.kanban.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import test.java.com.yandex.kanban.API.DurationTypeAdapter;
import test.java.com.yandex.kanban.API.LocalDateTimeTypeAdapter;

import java.time.Duration;
import java.time.LocalDateTime;

public class GsonUtil {
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }
}