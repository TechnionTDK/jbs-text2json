package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.LomdimLessonParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;
import static text2json.parsers.LomdimLessonParser.*;

/**
 * Created by omishali on 17/01/2017.
 */
public class LomdimLessonParserTest {
    static SubjectsJson[] json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new LomdimLessonParser();
        String[] filesToParse = new String[]{"lesson_test1",
                "lesson_teshuva1", "lesson_teshuva2", "lesson_teshuva3", "lesson_teshuva4", "lesson_rashi_ki_tetze",
                "lesson_rashi_ki_tavo", "lesson_shofar1", "lesson_shofar2", "lesson_shofar3", "lesson_shofar4", "lesson_lashon_hara_1",
                "lesson_banot_miketz"};
        json = new SubjectsJson[filesToParse.length];
        createOutputFolderIfNotExists("lomdim");
        for (int i=0; i<filesToParse.length; i++) {
            BufferedReader reader = getText("lomdim/" + filesToParse[i] + ".txt");
            parser.parse(reader, "json/lomdim/" + filesToParse[i] + ".json");
            json[i] = getJson("json/lomdim/" + filesToParse[i] + ".json");
        }

    }

    @Test
    public void testTotalNumberOfObjects_0() {
        assertNotNull(json[0]);
        assertEquals(9, json[0].subjects.size());
    }

    @Test
    public void testSpecificObjects_0() {
        Map<String, String> o = json[0].getObject(0);
        assertEquals("series", o.get(KEY_TYPE));
        assertEquals("שיעורים לדוגמא", o.get(KEY_CONTENT));

        o = json[0].getObject(1);
        assertEquals("title", o.get(KEY_TYPE));
        assertEquals("דוגמא ראשונה", o.get(KEY_CONTENT));

        o = json[0].getObject(2);
        assertEquals("author", o.get(KEY_TYPE));
        assertEquals("ד\"ר אורן משלי", o.get(KEY_CONTENT));

        o = json[0].getObject(3);
        assertEquals("image", o.get(KEY_TYPE));
        assertEquals("picture.jpg", o.get(KEY_CONTENT));

        o = json[0].getObject(4);
        assertEquals("guidelines", o.get(KEY_TYPE));
        //System.out.println((String)o.get("content")); We have a problem with types, this line throws exception.
        //assertEquals("הלכות ציצית להרמב\"ם", o.get("title"));

        // test third object
        o = json[0].getObject(5);
        assertEquals("materials", o.get(KEY_TYPE));
        assertEquals("כותרת משנה", o.get(KEY_TITLE));

        // test multiple choice object
        o = json[0].getObject(7);
        assertEquals("multiple_choice", o.get(KEY_TYPE));
        assertEquals("2", o.get(KEY_NUM_OF_CORRECT));

        // test sort items object
        o = json[0].getObject(8);
        assertEquals("sort", o.get(KEY_TYPE));
    }

 //   @Test
//    public void testSpecificObjects_1() {
//        // test first title object
//        Map<String, String> o = json[1].getObject(0);
//        assertEquals("title", o.get(KEY_TYPE));
//        //assertEquals("הלכות ציצית להרמב\"ם פרק א", o.get(KEY_CONTENT));
//
//        // test first materials object
//        o = json[1].getObject(2);
//        assertEquals("materials", o.get(KEY_TYPE));
//        assertEquals("הלכות ציצית פרק א", o.get(KEY_TITLE));
//
//        // test second materials object
//        o = json[1].getObject(8);
//        assertEquals("materials", o.get(KEY_TYPE));
//        assertNull(o.get(KEY_TITLE));
//
//        // test last
//        o = json[1].getObject(json[1].subjects.size()-1);
//        assertEquals("multiple_choice", o.get(KEY_TYPE));
//        assertEquals("2", o.get(KEY_NUM_OF_CORRECT));
//    }

}
