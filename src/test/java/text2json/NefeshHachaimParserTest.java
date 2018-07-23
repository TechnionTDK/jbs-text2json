package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.NefeshHachaimParser;

import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.JBO_TEXT;
import static text2json.TestUtils.*;

/**
 * Created by orel on 22/07/18.
 */
public class NefeshHachaimParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;
    private static String bookName = "nefeshhachaim";

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new NefeshHachaimParser() , "nefeshhachaim");
        packageJson = getJson("json/" + bookName + "/" + bookName + "-packages.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(96, json.subjects.size()); //total number of chapters
        assertEquals(5, packageJson.subjects.size()); //total number of gates
    }


    private void testObject(Map<String, String> o, int gateNum, int perekNum, int position, String startsWith, String endsWith) {
        String UriNum = "-" + gateNum + "-" + perekNum;
        String positionStr = Integer.toString(position);
        assertBookProperty(o,bookName);
        assertTextUriProperty( o , bookName + UriNum);
        assertPositionProperty(o, positionStr);
        assertTrue(o.get(JBO_TEXT).startsWith(startsWith));
        assertTrue(o.get(JBO_TEXT).endsWith(endsWith));
        String str1 = "נפש החיים ";
        String label = str1 + JbsUtils.numberToHebrew(gateNum) + " " + JbsUtils.numberToHebrew(perekNum);
        assertLabelProperty( o ,label);
    }

    private void testPackageObject(Map<String, String> o, int gateNum) {
        String UriNum = "-" + gateNum;
        String positionNum = Integer.toString(gateNum);
        assertBookProperty(o,bookName);
        //this next line in fact gets the correct uri, but compares a jbr:text to a jbr:section. seems like packageJson isn't meant to be tested
//        assertTextUriProperty( o , bookName + UriNum);
        assertPositionProperty(o, positionNum);
        String str1 = "נפש החיים ";
        String label = str1 + JbsUtils.numberToHebrew(gateNum);
        assertLabelProperty( o ,label);
    }

    @Test
    public void testSpecificObjects() {
        Map<String, String> o;
        String startsWith, endsWith;

        // test first object
        o = json.getObject(0);
        startsWith = "כתיב ויברא אלקים את האדם בצלמו בצלם אלקים ברא אותו וכן כתיב כי בצלם אלקים עשה את האדם.";
        endsWith = "כ''ה לפי הפשטנים הראשונים ז''ל וכן עד''ז הוא ענין מלת צלם. כי המה דומים במשמעם בצד מה:";
        testObject(o, 1, 1, 1, startsWith, endsWith);

        o = json.getObject(1);
        startsWith = "אמנם להבין ענין אומרו בצלם אלקים דיקא. ולא שם אחר. כי שם אלקים ידוע פירושו שהוא מורה שהוא ית''ש בעל הכחות כולם כמ''ש בטור א''ח סימן ה'.";
        endsWith = "שהוא הבעל כח האמיתי של כולם שכולם מקבלים כחם ממנו ית''ש ז''ש ויפלו על פניהם ויאמרו ה' הוא האלהים:";
        testObject(o, 1, 2,  2, startsWith, endsWith);

        o = json.getObject(5);
        startsWith = "אמנם עדיין הענין צריך ביאור. (כי הוא ז''ל דיבר בקדשו דרך קצרה כדרכו בכל כתבי קדשו בנסתרות. כמ''ש בעצמו בהקדמתו שם שהוא מגלה טפח ומכסה אלפים אמה).";
        endsWith = "כביכול הקב''ה ובית דינו נמנו על כל אבר ואבר משלך ומעמידך על תיקונך. וא''ת שתי רשויות הן והלא כבר נאמר הוא עשך ויכוננך. והוא מבואר:";
        testObject(o, 1, 6,  6, startsWith, endsWith);

        o = json.getObject(27);
        startsWith = "וכמו שענין חבור וקיום נשמת האדם בגופו. הוא ע''י אכילה ושתיה. ובלתם תפרד ותסתלק מהגוף. כן חיבור עצמותו ית' אל העולמות שהן סוד האדם הגדול. כדי להעמידם ולקיימם ולא תגעל נפשו אותם.";
        endsWith = "אלימה הובאו דבריו בזה בס' שומר אמונים ע''ש. ובתולעת יעקב ובריש ס' דרך אמונה שלו. וזהו ענין מאמרם ז''ל ישראל מפרנסין לאביהם שבשמים:";
        testObject(o, 2, 6, 28, startsWith, endsWith);

        //the last object (should be object index 95)
        o = json.getObject(json.subjects.size()-1);
        startsWith = "ומעת חורבן בית קדשנו וגלו הבנים מעל שלחן אביהם. שכינת כבודו יתב' אזלא ומתרכא כביכול ולא תרגיע.";
        endsWith = "בנל''ך בא''י למדני חקיך: גל עיני ואביטה נפלאות מתורתך:";
        testObject(o, 5, 34, 96, startsWith, endsWith);

        //now we'll check all package objects
        for(int i = 0; i < 5; i++) {
            o = packageJson.getObject(i);
            testPackageObject(o, i+1);
        }

    }
}