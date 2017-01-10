package text2json;

import com.google.gson.stream.JsonWriter;
import groovy.lang.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 24-Dec-16.
 */
public class JsonObject {
    List<Tuple> tuples = new ArrayList<Tuple>();
    List<JsonObject> inArrayObjects = new ArrayList<JsonObject>();
    String arrayKey = null;
    JsonObject currentArrayObject = null;

    public void addObject(String key, String value) {
        tuples.add(new Tuple(key, value));
    }

    public void append(String key, String added_value) {
        for(Tuple tuple : tuples) {
            if (tuple.getKey() == key) {
                tuple.appendToValue(added_value);
                return;
            }
        }
        tuples.add(new Tuple(key, added_value));
    }


    public void writeObject(JsonWriter jsonWriter) throws IOException {
        if(tuples.isEmpty() && inArrayObjects.isEmpty()) return;
        jsonWriter.beginObject();
        for(Tuple tuple : tuples){
            jsonWriter.name(tuple.getKey());
            jsonWriter.value(tuple.getValue());
        }
        if(arrayKey != null){
            jsonWriter.name(arrayKey);
            jsonWriter.beginArray();
            for(JsonObject jsonObject : inArrayObjects){

                jsonWriter.beginObject();
                for(Tuple tuple : jsonObject.tuples){
                    jsonWriter.name(tuple.getKey());
                    jsonWriter.value(tuple.getValue());
                }
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }

    public void addArray(String arrayKey) {
        this.arrayKey = arrayKey;
    }

    public void addObjectToArray() {
        currentArrayObject = new JsonObject();
        inArrayObjects.add(currentArrayObject);
    }

    public void addToArrayObject(String key, String value) {
        currentArrayObject.addObject(key, value);
    }

    private class Tuple {
        private String key;
        private String value;

        Tuple(String key, String value){
            this.key = key;
            this.value = value;
        }

        public String getKey(){return this.key;}
        public String getValue(){return this.value;}
        protected void setValue(String value){this.value = value;}
        protected void appendToValue(String added_value){this.value += added_value;}
    }
}
