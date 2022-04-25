package com.lab.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.lab.exceptions.InvalidDateFormatException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static com.lab.utils.DateConverter.parseLocalDateTime;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        try{
            return parseLocalDateTime(json.getAsJsonPrimitive().getAsString());
        }
        catch (InvalidDateFormatException e){
            throw new JsonParseException("");
        }
    }
}
