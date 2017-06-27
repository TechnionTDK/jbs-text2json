package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidbarShurParser;

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
public class MidbarShurParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new MidbarShurParser();
        BufferedReader reader = getText("midbarshur/midbarshur.txt");
        parser.parse(reader, "json/midbarshur/midbarshur.json");
        json = getJson("json/midbarshur/midbarshur.json");
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
        assertEquals(38, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:midbarshur-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("מדבר שור - דרוש א", object.get(RDFS_LABEL));


        object = json.getObject(3);
        assertEquals("jbr:midbarshur-4", object.get(URI));
        assertEquals("מדבר שור - דרוש ד", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(18);
        assertEquals("jbr:midbarshur-19", object.get(URI));
        assertEquals("מדבר שור - דרוש יט", object.get(RDFS_LABEL));
        assertEquals("19", object.get(JBO_POSITION));

        object = json.getObject(19);
        assertEquals("jbr:midbarshur-20", object.get(URI));
        assertEquals("מדבר שור - דרוש כ", object.get(RDFS_LABEL));
        assertEquals("20", object.get(JBO_POSITION));

        object = json.getObject(28);
        assertEquals("jbr:midbarshur-29", object.get(URI));
        assertEquals("מדבר שור - דרוש כט", object.get(RDFS_LABEL));
        assertEquals("29", object.get(JBO_POSITION));

        object = json.getObject(37);
        assertEquals("jbr:midbarshur-38", object.get(URI));
        assertEquals("מדבר שור - דרוש לח", object.get(RDFS_LABEL));
        assertEquals("38", object.get(JBO_POSITION));
    }
}
