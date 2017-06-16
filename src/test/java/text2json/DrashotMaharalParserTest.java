package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.DrashotMaharalParser;

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
public class DrashotMaharalParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DrashotMaharalParser();
        BufferedReader reader = getText("drashotmaharal/drashotmaharal.txt");
        parser.parse(reader, "json/drashotmaharal/drashotmaharal.json");
        json = getJson("json/drashotmaharal/drashotmaharal.json");
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
        assertEquals(6, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:drashotmaharal-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("דרשות מהר\"ל - הקדמה ", object.get(RDFS_LABEL));


        object = json.getObject(2);
        assertEquals("jbr:drashotmaharal-2", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש על התורה", object.get(RDFS_LABEL));
        assertEquals("2", object.get(JBO_POSITION));

        object = json.getObject(3);
        assertEquals("jbr:drashotmaharal-3", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש על המצות", object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));

        object = json.getObject(5);
        assertEquals("jbr:drashotmaharal-5", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש לשבת הגדול", object.get(RDFS_LABEL));
        assertEquals("5", object.get(JBO_POSITION));

    }
}
