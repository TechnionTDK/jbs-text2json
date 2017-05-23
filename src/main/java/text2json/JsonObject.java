package text2json;

import com.google.gson.stream.JsonWriter;
//import groovy.lang.Tuple;
//import org.codehaus.groovy.runtime.ReverseListIterator;

import java.io.IOException;
import java.util.*;

/**
 * Created by USER on 24-Dec-16.
 */
public class JsonObject {

    private List<Tuple> tuples = new ArrayList<Tuple>();
    private Map<String, List<String>> arrays = new HashMap<>();

    public void add(String key, String value) {
        tuples.add(new Tuple(key, value));
    }

    public void add(String key, int value) {
        add(key, String.valueOf(value));
    }

    /**
     * append value to the tuple-object with a given key.
     * if no such key exists in the current level object, creates a new tuple-object with the given key and value.
     * a value can be appended only if from current position in object going backwards there's no beginning/end of
     * another object or array
     * @param key
     * @param added_value
     */
    public void append(String key, String added_value) {
        ListIterator<Tuple> reverse_iterator = tuples.listIterator(tuples.size());
        while(reverse_iterator.hasPrevious()){
            Tuple tuple = reverse_iterator.previous();
            String tuplekey = tuple.getKey();
            if(tuplekey == key) {
                tuple.appendToValue(added_value);
                return;
            }
        }
        //if here, no such key in current object. create new one.
        tuples.add(new Tuple(key, added_value));
    }

    public boolean hasKey(String key) {
        for (Tuple t : tuples)
            if (t.getKey().equals(key))
                return true;

        return false;
    }

    public void writeObject(JsonWriter jsonWriter) throws IOException {
        if(tuples.isEmpty()) return;
        jsonWriter.beginObject();
        writeArrays(jsonWriter);
        for(Tuple tuple : tuples){
            jsonWriter.name(tuple.getKey());
            jsonWriter.value(tuple.getValue());
        }
        jsonWriter.endObject();
    }

    private void writeArrays(JsonWriter writer) throws IOException {
        for (String key : arrays.keySet()) {
            writer.name(key);
            writer.beginArray();
            for (String value : arrays.get(key))
                writer.value(value);
            writer.endArray();
        }
    }

    public void addToArray(String key, String value) {
        if (arrays.containsKey(key))
            arrays.get(key).add(value);
        else {
            List<String> list = new ArrayList<String>();
            list.add(value);
            arrays.put(key, list);
        }
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
        protected void appendToValue(String added_value){this.value = this.value+ "\n" +added_value;}

    }
}
