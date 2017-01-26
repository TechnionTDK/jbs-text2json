package text2json;

import com.google.gson.stream.JsonWriter;
//import groovy.lang.Tuple;
//import org.codehaus.groovy.runtime.ReverseListIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by USER on 24-Dec-16.
 */
public class JsonObject {
    private static final String OPEN_ARRAY = "open_new_array";
    private static final String CLOSE_ARRAY = "close_current_array";
    private static final String OPEN_OBJECT = "open_new_object";
    private static final String CLOSE_OBJECT = "close_current_object";

    List<Tuple> tuples = new ArrayList<Tuple>();

    public void addObject(String key, String value) {
        tuples.add(new Tuple(key, value));
    }

    public void addObject(String key, int value) {
        addObject(key, String.valueOf(value));
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
            if(tuplekey == OPEN_ARRAY || tuplekey == CLOSE_ARRAY || tuplekey == OPEN_OBJECT || tuplekey == CLOSE_OBJECT){
                break;
            }
        }
        //if here, no such key in current object. create new one.
        tuples.add(new Tuple(key, added_value));
    }

    public void writeObject(JsonWriter jsonWriter) throws IOException {
        if(tuples.isEmpty()) return;
        jsonWriter.beginObject();
        for(Tuple tuple : tuples){
            if(tuple.isOpenArray()){
                writeOpenArray(jsonWriter, tuple);
            }
            else if(tuple.isCloseArray()){
                writeCloseArray(jsonWriter);
            }
            else if(tuple.isOpenObject()){
                writeOpenObject(jsonWriter, tuple);
            }
            else if(tuple.isCloseObject()){
                writeCloseObject(jsonWriter);
            }
            else {
                jsonWriter.name(tuple.getKey());
                jsonWriter.value(tuple.getValue());
            }
        }
        jsonWriter.endObject();
    }

    private void writeOpenObject(JsonWriter jsonWriter, Tuple tuple) throws IOException {
        if(tuple.getValue() != null){
            jsonWriter.name(tuple.getValue());
        }
        jsonWriter.beginObject();
    }
    private void writeCloseObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.endObject();
    }
    private void writeOpenArray(JsonWriter jsonWriter, Tuple tuple) throws IOException {
        if(tuple.getValue() != null) {
            jsonWriter.name(tuple.getValue());
        }
        jsonWriter.beginArray();
    }
    private void writeCloseArray(JsonWriter jsonWriter) throws IOException {
        jsonWriter.endArray();
    }

    /**
     * open inner object with key. i.e {super_key:{sub_key:value}}
     * @param objectKey
     */
    public void openObject(String objectKey){
        addObject(OPEN_OBJECT, objectKey);
    }
    /**
     * open inner object without key
     */
    public void openObject(){
        addObject(OPEN_OBJECT, null);
    }
    public void closeObject(){
        addObject(CLOSE_OBJECT, null);
    }
    /**
     * open array with a key before it i.e {super_key:[{key1:val1), {key2:val 2}]}
     * @param arrayKey
     */
    public void openArray(String arrayKey) {
        addObject(OPEN_ARRAY, arrayKey);
    }
    /**
     * open array without a key
     */
    public void openArray() {
        addObject(OPEN_ARRAY, null);
    }
    public void closeArray() {
        addObject(CLOSE_ARRAY, null);
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

        public boolean isOpenArray(){
            return getKey() == OPEN_ARRAY;
        }

        public boolean isCloseArray() {
            return getKey() == CLOSE_ARRAY;
        }

        public boolean isOpenObject() {
            return getKey() == OPEN_OBJECT;
        }

        public boolean isCloseObject() {
            return getKey() == CLOSE_OBJECT;
        }
    }
}
