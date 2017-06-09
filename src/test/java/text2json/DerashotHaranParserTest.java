package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerashotHaranParser;
import java.io.BufferedReader;

import static org.junit.Assert.*;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 05/05/2017.
 */
    public class DerashotHaranParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DerashotHaranParser();
        BufferedReader reader = getText("derashotharan/derashotharan.txt");
        parser.parse(reader, "json/derashotharan/derashotharan.json");
        json = getJson("json/derashotharan/derashotharan.json");
    }

    @Test
    //test the correctness with sampling a few values
    public void test() {
        System.out.println("bla bla");
        System.out.println();
    }


    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(12, json.subjects.size());
    }
}