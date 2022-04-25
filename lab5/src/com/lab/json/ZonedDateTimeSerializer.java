package com.lab.json;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import com.google.gson.*;
import static com.lab.utils.DateConverter.*;

public class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {
    @Override
    public JsonElement serialize(ZonedDateTime zonedDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(zonedDateTimeToString(zonedDateTime));
    }
}


