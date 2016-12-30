package text2json;

import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Represents the output of a parser
 * Created by omishali on 20/12/2016.
 */
public class JsonFile {

    protected JsonWriter jsonWriter;

    protected JsonFile(String filename){
        try{
            jsonWriter = new JsonWriter(new FileWriter(filename));
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

    public void write(JsonObject jsonObject) throws IOException {
        jsonObject.writeObject(jsonWriter);
    }

}
