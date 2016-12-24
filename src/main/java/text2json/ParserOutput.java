package text2json;

import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Represents the output of a parser
 * Created by omishali on 20/12/2016.
 */
public class ParserOutput {

    protected JsonWriter jsonWriter;

    protected ParserOutput(String fileName){
        try{
            jsonWriter = new JsonWriter(new FileWriter(fileName));
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createMainObject() throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("subjects");
        jsonWriter.beginArray();
    }

    public void closeMainObject() throws IOException {
        jsonWriter.endArray();
        jsonWriter.endObject();
        jsonWriter.close();
    }

    public void addJsonSubject(List<JsonObject> subject) throws IOException {
        jsonWriter.beginObject();
        for(JsonObject object : subject){
            jsonWriter.name(object.name);
            jsonWriter.value(object.value);
        }
        jsonWriter.endObject();
    }
}
