package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MefareshParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;
import static text2json.TestUtils.assertLabelProperty;

/**
 * Created by orel on 01/08/18.
 */
public class MefareshParserTest {
    private static SubjectsJson rambanJson, abarbanelJson, adereteliyahuJson, alshichJson, chizkuniJson,
    aviezerJson, baalhaturimJson, bartenuraJson, bekhorshorJson, chomatanakhJson, daatzkenimJson,
    guraryehJson, haktavvehakabalahJson, haamekdavarJson;

    private static final int rambanSize = 1738, abarbanelSize = 477;

    @BeforeClass
    public static void beforeClass() throws Exception {
        rambanJson = setupParser(new MefareshParser() , "tanachmefarshim", "ramban");
        abarbanelJson = setupParser(new MefareshParser() , "tanachmefarshim", "abarbanel");
        adereteliyahuJson = setupParser(new MefareshParser() , "tanachmefarshim", "adereteliyahu");
        alshichJson = setupParser(new MefareshParser() , "tanachmefarshim", "alshich");
        chizkuniJson = setupParser(new MefareshParser() , "tanachmefarshim", "chizkuni");
        aviezerJson = setupParser(new MefareshParser() , "tanachmefarshim", "aviezer");
        baalhaturimJson = setupParser(new MefareshParser() , "tanachmefarshim", "baalhaturim");
        bartenuraJson = setupParser(new MefareshParser() , "tanachmefarshim", "bartenura");
        bekhorshorJson = setupParser(new MefareshParser() , "tanachmefarshim", "bekhorshor");
        chomatanakhJson = setupParser(new MefareshParser() , "tanachmefarshim", "chomatanakh");
        daatzkenimJson = setupParser(new MefareshParser() , "tanachmefarshim", "daatzkenim");
        guraryehJson = setupParser(new MefareshParser() , "tanachmefarshim", "guraryeh");
        haktavvehakabalahJson = setupParser(new MefareshParser() , "tanachmefarshim", "haktavvehakabalah");
        haamekdavarJson = setupParser(new MefareshParser() , "tanachmefarshim", "haamekdavar");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(rambanJson);
        assertEquals(rambanSize, rambanJson.subjects.size());
        assertNotNull(abarbanelJson);
        assertEquals(abarbanelSize, abarbanelJson.subjects.size());
        assertNotNull(adereteliyahuJson);
        assertNotNull(alshichJson);
        assertNotNull(chizkuniJson);
    }

    private void testObject(Map<String, String> o, int bookNum, int perekNum, int pasukNum, String interprets,
                            String mefareshName, String mefareshHebrewName,String startsWith, String endsWith) {
        assertBookProperty(o, mefareshName);

        String startsNoNikud = JbsUtils.removeNikkud(startsWith);
        String endsNoNikud = JbsUtils.removeNikkud(endsWith);
        assertTrue(o.get(JBO_TEXT).startsWith(startsNoNikud));
        assertTrue(o.get(JBO_TEXT).endsWith(endsNoNikud));
        if(o.get(JBO_TEXT_NIKUD) != null) {
            assertTrue(o.get(JBO_TEXT_NIKUD).startsWith(startsWith));
            assertTrue(o.get(JBO_TEXT_NIKUD).endsWith(endsWith));
        }

        String hebBookName = JbsUtils.SEFARIM_TANACH_HE[bookNum-1] + " ";
        String label;
        if(pasukNum > 0) label = mefareshHebrewName + " " + hebBookName +
                JbsUtils.numberToHebrew(perekNum) + " " + JbsUtils.numberToHebrew(pasukNum);
        else label = mefareshHebrewName + " " + hebBookName + "הקדמה";
        assertLabelProperty( o ,label);

        assertEquals(o.get(JBO_NAME), mefareshHebrewName);

        if(pasukNum > 0)
            assertEquals(o.get(JBO_INTERPRETS), JBR_TEXT + interprets);
    }

    @Test
    public void testRambanObjects() {
        Map<String, String> o;
        String startsWith, endsWith;
        String mefareshName = "ramban", mefareshHebrewName = "רמב\"ן";

        o = rambanJson.getObject("jbr:text-ramban-tanach-1-0");
        startsWith = "בשם האל הגדול והנורא. אתחיל לכתוב חידושים בפירוש התורה. באימה ביראה ברתת בזיע במורא.";
        endsWith = "צדק עדותיך לעולם. הבינני ואחיה (תהלים קיט קמד).";
        testObject(o, 1, 1, 0, "",
                mefareshName, mefareshHebrewName, startsWith, endsWith);

        o = rambanJson.getObject("jbr:text-ramban-tanach-1-1-1");
        startsWith = "בְּרֵאשִׁית בָּרָא אֱלֹהִים אמר רבי יצחק: לא היה צריך להתחיל התורה אלא מ\"הַחֹדֶשׁ הַזֶּה לָכֶם\" (שמות יב ב), שהיא מצווה ראשונה שנצטוו בה ישראל. ומה טעם פתח בבראשית?";
        endsWith = "לומר \"אֱלֹהִים בָּרָא בְּרֵאשִׁית\", תדע כי על דרך האמת – הכתוב יגיד בתחתונים וירמוז בעליונים. ומילת בְּרֵאשִׁית תרמוז בחכמה, שהיא ראשית הראשים, כאשר הזכרתי. ולכך תרגמו בתרגום ירושלמי \"בחכמתא\", והמילה מוכתרת בכתר בי\"ת.";
        testObject(o, 1, 1, 1, "tanach-1-1-1",
                mefareshName, mefareshHebrewName, startsWith, endsWith);

        o = rambanJson.getObject("jbr:text-ramban-tanach-2-25-9");
        startsWith = "וכן תעשו לדורות אם יאבד אחד מן הכלים או כשתעשו כלי בית עולמים כגון שולחנות ";
        endsWith = "משה כן עשו (להלן לט לב), כי מפני שהיא צואה אמר וכן תעשו:";
        testObject(o, 2, 25, 9, "tanach-2-25-9",
                mefareshName, mefareshHebrewName, startsWith, endsWith);

        o = rambanJson.getObject("jbr:text-ramban-tanach-5-22-8");
        startsWith = "ועשית מעקה מצות המעקה מחודשת או מבוארת מלא תעמוד על דם רעך (ויקרא יט טז)";
        endsWith = "תבואה מתקדשת משתשריש וענבים משיעשו כפול הלבן";
        testObject(o, 5, 22, 8, "tanach-5-22-8",
                mefareshName, mefareshHebrewName, startsWith, endsWith);
    }

    @Test
    public void testAbarbanelObjects() {
        Map<String, String> o;
        String startsWith, endsWith;
        String mefareshName = "abarbanel";
        String mefareshHebrewName = "אברבנאל";

        o = abarbanelJson.getObject("jbr:text-abarbanel-tanach-1-1-1");
        startsWith = "ידבר ומסדר הבריאה טעם סמיכות מלות בראשית למלת ברא";
        endsWith = "הוא השפלו' והיותם למטה מהגלגלים. והב' שהם מרכזם שעליהם יסובו. והג' שהם דחוקים בתוך הגלגל רוצצים ומתפוצצים זה בזה:";
        testObject(o, 1, 1, 1, "tanach-1-1-1",
                mefareshName, mefareshHebrewName, startsWith, endsWith);

        o = abarbanelJson.getObject("jbr:text-abarbanel-tanach-2-40-1");
        startsWith = "וידבר ה' אל משה לאמר ביום החדש הראשון וגו' עד ויכס הענן את אהל מועד וכבוד ה' מלא את המשכן.";
        endsWith = "ולמה נאמר בכל אחד מהם כאשר צוה ה' אל משה ההקמה הזאת. ולא אם כן בזה דבר כפול ולא מותר. והותרה השאלה הי':";
        testObject(o, 2, 40, 1, "tanach-2-40-1",
                mefareshName, mefareshHebrewName, startsWith, endsWith);

        o = abarbanelJson.getObject("jbr:text-abarbanel-tanach-5-34-11");
        startsWith = "לכל האותות והמופתים אשר שלחו ה' לעשות בארץ מצרים וגו'. עד סוף הפרשה קיימו וקבלו חכמי המחברים הקדמה אחת";
        endsWith = "לחדש שבט שנת רנ\"ו שמים כי עשר ה' מהאלף הששי כפי חשבוננו האמתי מיצירת העולם והתהלה לאל המרומם ע\"כ ברכה ותהלה:";
        testObject(o, 5, 34, 11, "tanach-5-34-11",
                mefareshName, mefareshHebrewName, startsWith, endsWith);
    }

    @Test
    public void testPositions() {
        //the position of the elements should fit their index in the json
        Map<String, String> o;
        int[] positions = {1, 20, 59, 100, 89, 789, 1000};
        for (int position : positions) {
            o = rambanJson.getObject(position - 1);
            assertPositionProperty(o, Integer.toString(position));
            if(position < abarbanelSize) {
                o = abarbanelJson.getObject(position - 1);
                assertPositionProperty(o, Integer.toString(position));
            }
        }
    }
}