package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ShemonaKevatzimParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 17/01/2017.
 */
public class ShemonaKvatzimParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new ShemonaKevatzimParser();
        createOutputFolderIfNotExists("shmonakvatzim");
        BufferedReader reader = getText("shmonakvatzim/shmonakvatzim.txt");
        createOutputFolderIfNotExists("shmonakvatzim");
        parser.parse(reader, "json/shmonakvatzim/shmonakvatzim.json");
        json = getJson("json/shmonakvatzim/shmonakvatzim.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(2822, json.subjects.size());
    }

    @Test
    public void testSpecificObjects() {
        // test first object
        //Map<String, String> o = (Map<String, String>) json.subjects.get(0);
        Map<String, String> o = json.getObject(0);
        assertEquals(JBR_TEXT + "shemonakevatzim-1-1", o.get(URI));
        String text = "חיים אנו עם הציורים הרוחניים שבהשאיפה של נשמת האומה. בכל מקום שהניצוצות של אור הללו גנוזים הם שם, הננו מקושרים בקשר של נשמה של חיים, של כל הוייתנו, אליו. בין שיהיה מקום זה מקום ממשי, מדת ארץ, בין שיהיו מעשים שאלו הציורים כמוסים ביסודם, בין שיהיו מחשבות ורעיונות מאיזה מין שיהיו.";
        assertEquals(text, o.get(JBO_TEXT));
        assertEquals("שמונה קבצים 1 1", o.get(RDFS_LABEL));
        assertBookProperty("shemonakevatzim", o.get(JBO_BOOK));

        // test last object
        o = json.getObject(json.subjects.size()-1);
        assertEquals(JBR_TEXT + "shemonakevatzim-8-260", o.get(URI));
        text = "ואם הנשמה בחביונה היא חדורה צמאון אלהי, וחשיפות היושר הטהור היא עריגתה הקבועה, מי הוא זה יכול לעצור בפניה, ולהטות אותה מאורח שטפה הרענן.";
        assertEquals(text, o.get(JBO_TEXT));

        // test last object of kovetz 1
        o = json.getObject(902);
        assertEquals(JBR_TEXT + "shemonakevatzim-1-903", o.get(URI));
        assertTrue(o.get(JBO_TEXT).startsWith("בינה הוא האידיאל העליון"));

        // test last object of kovetz 2
        o = json.getObject(1263);
        assertEquals(JBR_TEXT + "shemonakevatzim-2-361", o.get(URI));
        assertTrue(o.get(JBO_TEXT).startsWith("הנשמה ההולכת ומאירה"));


        // test last object of kovetz 3
        // note: kovetz 3 is missing saif chet
        o = json.getObject(1630);
        assertEquals(JBR_TEXT + "shemonakevatzim-3-367", o.get(URI));
        assertTrue(o.get(JBO_TEXT).startsWith("המחשבות של הצדיקים"));
    }
}
