package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DerashotHaranParser;
import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.JBO_POSITION;
import static text2json.JbsOntology.RDFS_LABEL;
import static text2json.JbsOntology.URI;
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


    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:derashotharan-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("דרשות הר\"ן א", object.get(RDFS_LABEL));


        object = json.getObject(4);
        assertEquals("jbr:derashotharan-5", object.get(URI));
        assertEquals("דרשות הר\"ן ה", object.get(RDFS_LABEL));
        assertEquals("5", object.get(JBO_POSITION));

        object = json.getObject(8);
        assertEquals("jbr:derashotharan-9", object.get(URI));
        assertEquals("דרשות הר\"ן ט", object.get(RDFS_LABEL));
        assertEquals("9", object.get(JBO_POSITION));

        object = json.getObject(11);
        assertEquals("jbr:derashotharan-12", object.get(URI));
        assertEquals("דרשות הר\"ן יב", object.get(RDFS_LABEL));
        assertEquals("12", object.get(JBO_POSITION));

    }
}