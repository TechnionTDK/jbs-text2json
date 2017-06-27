package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.new_parsers.DerashotMaharalParser;

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
public class DerashotMaharalParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new DerashotMaharalParser();
        BufferedReader reader = getText("derashotmaharal/derashotmaharal.txt");
        parser.parse(reader, "json/derashotmaharal/derashotmaharal.json");
        json = getJson("json/derashotmaharal/derashotmaharal.json");
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
        assertEquals(5, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:derashotmaharal-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("דרשות מהר\"ל - הקדמה לדרוש על התורה", object.get(RDFS_LABEL));

        object = json.getObject(1);
        assertEquals("jbr:derashotmaharal-1", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש על התורה", object.get(RDFS_LABEL));
        assertEquals("2", object.get(JBO_POSITION));

        object = json.getObject(2);
        assertEquals("jbr:derashotmaharal-2", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש על המצות", object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));

        object = json.getObject(3);
        assertEquals("jbr:derashotmaharal-3", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש לשבת תשובה", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(4);
        assertEquals("jbr:derashotmaharal-4", object.get(URI));
        assertEquals("דרשות מהר\"ל - דרוש לשבת הגדול", object.get(RDFS_LABEL));
        assertEquals("5", object.get(JBO_POSITION));

    }
}
