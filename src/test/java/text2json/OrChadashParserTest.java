package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.OrChadashParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

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
        createOutputFolderIfNotExists("orchadash");
        parser.parse(reader, "json/orchadash/orchadash.json");
        json = getJson("json/orchadash/orchadash.json");
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
        assertEquals("jbr:orchadash-00", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("אור חדש - הקדמה א", object.get(RDFS_LABEL));
        assertEquals("jbr:book-orchadash", object.get(JBO_BOOK));


        object = json.getObject(2);
        assertEquals("jbr:orchadash-1", object.get(URI));
        assertEquals("אור חדש א", object.get(RDFS_LABEL));
        assertEquals("3", object.get(JBO_POSITION));
        assertEquals("jbr:book-orchadash", object.get(JBO_BOOK));


        object = json.getObject(5);
        assertEquals("jbr:orchadash-4", object.get(URI));
        assertEquals("אור חדש ד", object.get(RDFS_LABEL));
        assertEquals("6", object.get(JBO_POSITION));
        assertEquals("jbr:book-orchadash", object.get(JBO_BOOK));

        object = json.getObject(6);
        assertEquals("jbr:orchadash-5", object.get(URI));
        assertEquals("אור חדש ה", object.get(RDFS_LABEL));
        assertEquals("7", object.get(JBO_POSITION));
        assertEquals("jbr:book-orchadash", object.get(JBO_BOOK));

        object = json.getObject(9);
        assertEquals("jbr:orchadash-8", object.get(URI));
        assertEquals("אור חדש ח", object.get(RDFS_LABEL));
        assertEquals("10", object.get(JBO_POSITION));
        assertEquals("jbr:book-orchadash", object.get(JBO_BOOK));

        object = json.getObject(11);
        assertEquals("jbr:orchadash-10", object.get(URI));
        assertEquals("אור חדש י", object.get(RDFS_LABEL));
        assertEquals("12", object.get(JBO_POSITION));
        assertEquals("jbr:book-orchadash", object.get(JBO_BOOK));
    }
}
