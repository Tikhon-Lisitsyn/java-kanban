package test.java.com.yandex.kanban.API;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationTypeAdapter extends TypeAdapter<Duration> {
    @Override
    public void write(JsonWriter out, Duration value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toSeconds());
        }
    }

    @Override
    public Duration read(JsonReader in) throws IOException {
        return Duration.ofSeconds(in.nextLong());
    }
}