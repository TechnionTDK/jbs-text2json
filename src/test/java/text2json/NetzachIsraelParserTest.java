package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.NetzachIsraelParser;

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
public class NetzachIsraelParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new NetzachIsraelParser();
        BufferedReader reader = getText("netzachisrael/netzachisrael.txt");
        parser.parse(reader, "json/netzachisrael/netzachisrael.json");
        json = getJson("json/netzachisrael/netzachisrael.json");
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
        assertEquals(64, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:netzachisrael-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("נצח ישראל - פרק א ", object.get(RDFS_LABEL));


        object = json.getObject(3);
        assertEquals("jbr:netzachisrael-4", object.get(URI));
        assertEquals("נצח ישראל - פרק ד ", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(23);
        assertEquals("jbr:netzachisrael-24", object.get(URI));
        assertEquals("נצח ישראל - פרק כד ", object.get(RDFS_LABEL));
        assertEquals("24", object.get(JBO_POSITION));

        object = json.getObject(32);
        assertEquals("jbr:netzachisrael-33", object.get(URI));
        assertEquals("נצח ישראל - פרק לג ", object.get(RDFS_LABEL));
        assertEquals("33", object.get(JBO_POSITION));

        object = json.getObject(57);
        assertEquals("jbr:netzachisrael-58", object.get(URI));
        assertEquals("נצח ישראל - פרק נח ", object.get(RDFS_LABEL));
        assertEquals("58", object.get(JBO_POSITION));

        object = json.getObject(63);
        assertEquals("jbr:netzachisrael-64", object.get(URI));
        assertEquals("נצח ישראל - פרק סד ", object.get(RDFS_LABEL));
        assertEquals("64", object.get(JBO_POSITION));
    }
}
