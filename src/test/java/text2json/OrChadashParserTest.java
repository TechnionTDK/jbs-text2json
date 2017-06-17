package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OrChadashParser;

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
public class OrChadashParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new OrChadashParser();
        BufferedReader reader = getText("orchadash/orchadash.txt");
        parser.parse(reader, "json/orchadash/orchadash.json");
        json = getJson("json/orchadash/orchadash.json");
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
        assertEquals(4, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals("jbr:orchadash-0", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("אור חדש - הקדמה א", object.get(RDFS_LABEL));


        object = json.getObject(3);
        assertEquals("jbr:orchadash-2", object.get(URI));
        assertEquals("אור חדש - פרק ב ", object.get(RDFS_LABEL));
        assertEquals("4", object.get(JBO_POSITION));

        object = json.getObject(2);
        assertEquals("jbr:orchadash-1", object.get(URI));
        assertEquals("אור חדש - פרק א ", object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));
    }
}