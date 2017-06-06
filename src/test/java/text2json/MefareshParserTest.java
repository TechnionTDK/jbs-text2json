package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MefareshParser;

import java.io.BufferedReader;
import java.util.Map;
import static text2json.JbsOntology.*;
import static org.junit.Assert.*;
import static text2json.TestUtils.getJson;
import static text2json.TestUtils.getText;

/**
 * Created by shilonoa on 1/18/2017.
 */
public class MefareshParserTest {
    private static final int NUM_OF_LAST_BOOK = 39;
    private static final int NUM_OF_FIRST_BOOK = 1;
    private static final int NUM_OF_BOOKS = NUM_OF_LAST_BOOK - NUM_OF_FIRST_BOOK + 1;
    static SubjectsJson[] subjectJsons = new SubjectsJson[NUM_OF_BOOKS + 1];

    @BeforeClass
    public static void setup() throws Exception {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            MefareshParser parser = new MefareshParser();
            BufferedReader reader = getText("/tanach/tanach-" + bookNum + ".txt");
            parser.parse(reader, "json/tanach/tanachMefarshim-" + bookNum + ".json");
            subjectJsons[bookNum] = getJson("json/tanach/tanachMefarshim-" + bookNum + ".json");
        }
    }

    @Test
    public void testObject1() {
        Map<String, String> object;
        object = subjectJsons[4].getObjectByText("הוקשה אל המפרשים מאחר שפני המנורה היינו הנר האמצעי");
        assertEquals("jbr:tanach-keliyekar-4-8-2", object.get(URI));

        // now we check that the object contains also text belonging to the last line.
        assertTrue(object.get(JBO_TEXT).contains("והוא ענין נכון וברור וזה סמיכות העלאת הנרות"));
    }

    @Test
    public void testObject2() {
        Map<String, String> object;
        object = subjectJsons[4].getObjectByText("למה נסמכה פרשת המנורה לפרשת הנשיאים, לפי שכשראה אהרן חנוכת הנשיאים חלשה אז דעתו");
        assertEquals("jbr:tanach-rashi-4-8-2", object.get(URI));
    }

    @Test
    public void testLastObject() {
        Map<String, String> object;
        object = subjectJsons[4].getObject(subjectJsons[4].subjects.size() -1);
        assertEquals("jbr:tanach-ibnezra-4-36-13", object.get(URI));
    }
}