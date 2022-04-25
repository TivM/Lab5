package com.lab.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.lab.exceptions.InvalidDateFormatException;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import static com.lab.utils.DateConverter.parseZonedDateTime;

public class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {
    @Override
    public ZonedDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        try{
            return parseZonedDateTime(json.getAsJsonPrimitive().getAsString());
        }
        catch (InvalidDateFormatException e){
            throw new JsonParseException("");
        }
    }
}
