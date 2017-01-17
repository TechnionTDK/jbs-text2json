package text2json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ShmonaKvatzimParser;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by omishali on 17/01/2017.
 */
public class ShmonaKvatzimParserTest {
    static ParserOutput output;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new ShmonaKvatzimParser();
        BufferedReader reader = TestUtils.getText("shmonakvatzim/shmonakvatzim.txt");
        parser.parse(reader, "json/shmonakvatzim/shmonakvatzim.json");
        Gson gson = new Gson();
        output = gson.fromJson(new BufferedReader(new FileReader("json/shmonakvatzim/shmonakvatzim.json")), ParserOutput.class);
        //jsonReader = TestUtils.getJson("shmonakvatzim/shmonakvatzim.json");
    }

    @Test
    public void testTotalNumberOfElements() {
        assertNotNull(output);
        assertEquals(2822, output.subjects.size());
    }
}
