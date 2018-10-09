package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ShemonaKevatzimParser;

import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.JBO_POSITION;
import static text2json.JbsOntology.JBO_TEXT;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 17/01/2017.
 */
public class ShemonaKvatzimParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new ShemonaKevatzimParser() , "shmonakvatzim");

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
        assertTextUriProperty( o , "shemonakevatzim-1-1");
        String text = "חיים אנו עם הציורים הרוחניים שבהשאיפה של נשמת האומה. בכל מקום שהניצוצות של אור הללו גנוזים הם שם, הננו מקושרים בקשר של נשמה של חיים, של כל הוייתנו, אליו. בין שיהיה מקום זה מקום ממשי, מדת ארץ, בין שיהיו מעשים שאלו הציורים כמוסים ביסודם, בין שיהיו מחשבות ורעיונות מאיזה מין שיהיו.";
        assertEquals(text, o.get(JBO_TEXT));
        assertEquals("1", o.get(JBO_POSITION));
        assertLabelProperty( o ,"שמונה קבצים א א");
        assertBookProperty(o,"shemonakevatzim");

        // test last object
        o = json.getObject(json.subjects.size()-1);
        assertTextUriProperty( o , "shemonakevatzim-8-260");
        text = "ואם הנשמה בחביונה היא חדורה צמאון אלהי, וחשיפות היושר הטהור היא עריגתה הקבועה, מי הוא זה יכול לעצור בפניה, ולהטות אותה מאורח שטפה הרענן.";
        assertEquals(text, o.get(JBO_TEXT));

        // test last object of kovetz 1
        o = json.getObject(902);
        assertTextUriProperty( o , "shemonakevatzim-1-903");
        assertTrue(o.get(JBO_TEXT).startsWith("בינה הוא האידיאל העליון"));

        // test last object of kovetz 2
        o = json.getObject(1263);
        assertTextUriProperty( o , "shemonakevatzim-2-361");
        assertTrue(o.get(JBO_TEXT).startsWith("הנשמה ההולכת ומאירה"));


        // test last object of kovetz 3
        // note: kovetz 3 is missing saif chet
        o = json.getObject(1630);
        assertTextUriProperty( o , "shemonakevatzim-3-367");
        assertTrue(o.get(JBO_TEXT).startsWith("המחשבות של הצדיקים"));
    }
}
