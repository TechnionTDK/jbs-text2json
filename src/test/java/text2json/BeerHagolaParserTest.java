package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.new_parsers.BeerHagolaParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.getJson;
import static text2json.TestUtils.getText;

/**
 * Created by Assaf on 08/06/2017.
 */
public class BeerHagolaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new BeerHagolaParser();
        BufferedReader reader = getText("beerhagola/beerhagola.txt");
        parser.parse(reader, "json/beerhagola/beerhagola.json");
        json = getJson("json/beerhagola/beerhagola.json");
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
        assertEquals(8, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:beerhagola-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("באר הגולה - הקדמה", object.get(RDFS_LABEL));


        object = json.getObject(6);
        assertEquals("jbr:beerhagola-6", object.get(URI));
        assertEquals("באר הגולה - באר ו", object.get(RDFS_LABEL));
        assertEquals("7", object.get(JBO_POSITION));

        object = json.getObject(2);
        assertEquals("jbr:beerhagola-2", object.get(URI));
        assertEquals("באר הגולה - באר ב", object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));
    }
}
