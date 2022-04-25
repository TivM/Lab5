package com.lab.json;

import com.google.gson.*;
import com.lab.data.Worker;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.TreeSet;

import static com.lab.io.OutputManager.printErr;

public class CollectionDeserializer implements JsonDeserializer<TreeSet<Worker>> {
    private HashSet<Long> uniqueIds;

    /**
     * constructor
     * @param uniqueIds set of ids. useful for generating new id
     */
    public CollectionDeserializer(HashSet<Long> uniqueIds){
        this.uniqueIds = uniqueIds;
    }

    @Override
    public TreeSet<Worker> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        TreeSet<Worker> collection = new TreeSet<>();
        JsonArray workers = json.getAsJsonArray();
        int damagedElements = 0;

        for (JsonElement jsonWorker: workers){
            Worker worker = null;
            try{
                if(jsonWorker.getAsJsonObject().entrySet().isEmpty()){
                    printErr("empty worker found");
                    throw new JsonParseException("empty worker");
                }
                if(!jsonWorker.getAsJsonObject().has("id")) {
                    printErr("found worker without id");
                    throw new JsonParseException("no id");
                }
                worker = (Worker) context.deserialize(jsonWorker, Worker.class);

                Long id = worker.getId();

                if(uniqueIds.contains(id)) {
                    printErr("database already contains worker with id #" + Long.toString(id));
                    throw new JsonParseException("id isn't unique");
                }
                if(!worker.validate()) {
                    printErr("worker #"+Long.toString(id) + " doesnt match specific conditions");
                    throw new JsonParseException("invalid worker data");
                }
                uniqueIds.add(id);
                collection.add(worker);
            } catch (JsonParseException e){
                damagedElements += 1;
            }
        }
        if(collection.size()==0){
            if(damagedElements == 0) printErr("database is empty");
            else  printErr("all elements in database are damaged");
            throw new JsonParseException("no data");
        }
        if (damagedElements != 0) printErr(Integer.toString(damagedElements) + " elements in database are damaged");
        return collection;
    }
}

