package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SeferHamitzvotParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;
import static text2json.JbsOntology.*;

/**
 * Created by omishali on 18/01/2017.
 */
public class SeferHamitzvotParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new SeferHamitzvotParser();
        BufferedReader reader = getText("seferhamitzvot/seferhamitzvot.txt");
        createOutputFolderIfNotExists("seferhamitzvot");
        parser.parse(reader, "json/seferhamitzvot/seferhamitzvot.json");
        json = getJson("json/seferhamitzvot/seferhamitzvot.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(613, json.subjects.size());
    }

    @Test
    public void testObjects() {
        // test first object
        Map<String, String> o = json.getObject(0);
        assertEquals(JBR_TEXT + "seferhamitzvot-3-1", o.get(URI));
        assertEquals("ספר המצוות עשה מצוה א.", o.get(RDFS_LABEL));
        assertTrue(o.get(JBO_TEXT).startsWith("היא הצווי אשר צונו בהאמנת"));
        assertBookProperty("seferhamitzvot", o.get(JBO_BOOK));

        // test last object
        o = json.getObject(612);
        assertEquals(JBR_TEXT + "seferhamitzvot-4-365", o.get(URI));
        assertEquals("ספר המצוות לא תעשה מצוה שסה:", o.get(RDFS_LABEL));
        assertTrue(o.get(JBO_TEXT).startsWith("הזהיר המלך מהרבות ממון מיוחד לעצמו"));
        assertBookProperty("seferhamitzvot", o.get(JBO_BOOK));
    }
}
