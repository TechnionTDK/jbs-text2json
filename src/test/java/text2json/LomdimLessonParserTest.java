package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.LomdimLessonParser;
import static text2json.parsers.LomdimLessonParser.*;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.TestUtils.getJson;
import static text2json.TestUtils.getText;

/**
 * Created by omishali on 17/01/2017.
 */
public class LomdimLessonParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new LomdimLessonParser();
        BufferedReader reader = getText("lomdim/lesson_test1.txt");
        parser.parse(reader, "json/lomdim/lesson_test1.json");
        json = getJson("json/lomdim/lesson_test1.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(6, json.subjects.size());
    }

    @Test
    public void testSpecificObjects() {
        // test first title object
        Map<String, String> o = json.getObject(0);
        assertEquals("title", o.get(KEY_TYPE));
        assertEquals("שיעור לדוגמא", o.get(KEY_CONTENT));

        // test second guidelines object
        o = json.getObject(1);
        assertEquals("guidelines", o.get(KEY_TYPE));
        //System.out.println((String)o.get("content")); We have a problem with types, this line throws exception.
        //assertEquals("הלכות ציצית להרמב\"ם", o.get("title"));

        // test third object
        o = json.getObject(2);
        assertEquals("materials", o.get(KEY_TYPE));

        // test multiple choice object
        o = json.getObject(4);
        assertEquals("multiple_choice", o.get(KEY_TYPE));
        assertEquals("2", o.get(KEY_NUM_OF_CORRECT));

        // test sort items object
        o = json.getObject(5);
        assertEquals("sort", o.get(KEY_TYPE));
    }
}
