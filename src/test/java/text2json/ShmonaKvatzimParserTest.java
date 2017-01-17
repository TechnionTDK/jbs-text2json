package text2json;

import com.google.gson.stream.JsonReader;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ShmonaKvatzimParser;

import java.io.BufferedReader;

/**
 * Created by omishali on 17/01/2017.
 */
public class ShmonaKvatzimParserTest {
    private static JsonReader jsonReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new ShmonaKvatzimParser();
        BufferedReader reader = TestUtils.getText("shmonakvatzim/shmonakvatzim.txt");
        parser.parse(reader, "json/shmonakvatzim/shmonakvatzim.json");
        jsonReader = TestUtils.getJson("shmonakvatzim/shmonakvatzim.json");
    }

    @Test
    public void testTotalNumberOfElements() {
        assertNotNull(jsonReader);
    }
}
