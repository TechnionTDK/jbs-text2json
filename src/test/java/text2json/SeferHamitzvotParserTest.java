package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SeferHamitzvotParser;

import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.JBO_TEXT;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 18/01/2017.
 */
public class SeferHamitzvotParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new SeferHamitzvotParser() , "seferhamitzvot");

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
        assertTextUriProperty(o, "seferhamitzvot-3-1");
        assertLabelProperty( o ,"ספר המצוות עשה מצוה א.");
        assertTrue(o.get(JBO_TEXT).startsWith("היא הצווי אשר צונו בהאמנת"));
        assertBookProperty(o,"seferhamitzvot");

        // test last object
        o = json.getObject(612);
        assertTextUriProperty(o, "seferhamitzvot-4-365");
        assertLabelProperty( o ,"ספר המצוות לא תעשה מצוה שסה:");
        assertTrue(o.get(JBO_TEXT).startsWith("הזהיר המלך מהרבות ממון מיוחד לעצמו"));
        assertBookProperty(o,"seferhamitzvot");
    }
}
