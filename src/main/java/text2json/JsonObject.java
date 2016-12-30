package text2json;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 24-Dec-16.
 */
public class JsonObject {
    List<Tuple> tuples = new ArrayList<Tuple>();

    public void addObject(String key, String value) {
        tuples.add(new Tuple(key, value));
    }

    public void append(String key, String added_value) {
        for(Tuple tuple : tuples){
            if(tuple.getKey() == key){
                tuple.appendToValue(added_value);
                break;
            }
        }
    }

    public void writeObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        for(Tuple tuple : tuples){
            jsonWriter.name(tuple.getKey());
            jsonWriter.value(tuple.getValue());
        }
        jsonWriter.endObject();
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
