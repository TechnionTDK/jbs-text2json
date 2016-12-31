package text2json;

import com.google.gson.stream.JsonReader;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by omishali on 20/12/2016.
 */
public class MesilatYesharimParserTest {
    // TODO learn basics of JUnit

    @Test
    //test the building of the parser
    public void test1() throws IOException {
        MesilatYesharimParser parser = new MesilatYesharimParser();
        BufferedReader reader = TestUtils.getBufferedReader("/../../text/mesilatyesharim.txt");
        //BufferedReader reader = TestUtils.getBufferedReader("C:/Users/USER/Documents/GitHub/jbs-text2json/text/mesilatyesharim.txt");
        JsonFile output = parser.parse(reader);
    }

    @Test
    //test the correctness with sampling a few values
    public void test2() throws IOException {
        JsonReader jsonReader = new JsonReader(TestUtils.getFileReader("./parser.mesilatYesharim.json"));
        jsonReader.beginObject();
        assertEquals(jsonReader.nextName(), "subjects");
        jsonReader.beginArray();
        //jsonReader.beginObject();
        int perekNum = 0;
        while (jsonReader.hasNext()){
            jsonReader.beginObject();
            assertEquals(jsonReader.nextName(), "uri");
            assertEquals(jsonReader.nextString(), "mesilatyesharim-" + perekNum);
            assertEquals(jsonReader.nextName(), "title");
            testPerekTitle(jsonReader, perekNum);
            assertEquals(jsonReader.nextName(), "sefer");
            assertEquals(jsonReader.nextString(), "mesilatyesharim");
            assertEquals(jsonReader.nextName(), "text");
            jsonReader.nextString();

            jsonReader.endObject();
            perekNum++;
        }
        jsonReader.endArray();
        jsonReader.endObject();

        assertEquals(perekNum, 28);
    }

    /**
     * sampled titles tests
     * @param jsonReader
     * @param perekNum
     * @throws IOException
     */
    private void testPerekTitle(JsonReader jsonReader, int perekNum) throws IOException {
        if(perekNum == 0)
            assertEquals(jsonReader.nextString(), "הקדמת הרב המחבר זצ\"ל");
        else if (perekNum == 6)
            assertEquals(jsonReader.nextString(), "בביאור מדת הזריזות");
        else if (perekNum == 24)
            assertEquals(jsonReader.nextString(), "בביאור יראת החטא");
        else if (perekNum == 27)
            assertEquals(jsonReader.nextString(), "חתימה");
        else
            jsonReader.nextString();
    }
}
