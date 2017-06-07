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
 * Created by omishali.
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
    public void testObject1Book4() {
        Map<String, String> object;
        object = subjectJsons[4].getObjectByText("הוקשה אל המפרשים מאחר שפני המנורה היינו הנר האמצעי");
        assertEquals("jbr:tanach-keliyekar-4-8-2", object.get(URI));

        // now we check that the object contains also text belonging to the last line.
        assertTrue(object.get(JBO_TEXT).contains("והוא ענין נכון וברור וזה סמיכות העלאת הנרות"));
    }

    @Test
    public void testObject2Book4() {
        Map<String, String> object;
        object = subjectJsons[4].getObjectByText("למה נסמכה פרשת המנורה לפרשת הנשיאים, לפי שכשראה אהרן חנוכת הנשיאים חלשה אז דעתו");
        assertEquals("jbr:tanach-rashi-4-8-2", object.get(URI));
    }

    @Test
    public void testFirstObjectBook4() {
        Map<String, String> object;
        object = subjectJsons[4].getObject(0);
        assertEquals("jbr:tanach-onkelos-4-1-1", object.get(URI));
        assertEquals("אונקלוס  וּמַלִיל יְיָ עִם משֶׁה בְּמַדְבְּרָא דְסִינַי בְּמַשְׁכַּן זִמְנָא בְּחַד לְיַרְחָא תִנְיָנָא בְּשַׁתָּא תִנְיֵתָא לְמִפַּקְהוֹן מֵאַרְעָא דְמִצְרַיִם לְמֵימָר:", object.get(JBO_TEXT));
    }

    @Test
    public void testSecondObjectBook4() {
        Map<String, String> object;
        object = subjectJsons[4].getObject(1);
        assertEquals("jbr:tanach-yonatan-4-1-1", object.get(URI));
        //assertEquals("אונקלוס  וּמַלִיל יְיָ עִם משֶׁה בְּמַדְבְּרָא דְסִינַי בְּמַשְׁכַּן זִמְנָא בְּחַד לְיַרְחָא תִנְיָנָא בְּשַׁתָּא תִנְיֵתָא לְמִפַּקְהוֹן מֵאַרְעָא דְמִצְרַיִם לְמֵימָר:", object.get(JBO_TEXT));
    }

    @Test
    public void testThirdObjectBook4() {
        Map<String, String> object;
        object = subjectJsons[4].getObject(2);
        assertEquals("jbr:tanach-rashi-4-1-1", object.get(URI));
        assertEquals("רש\"י  וידבר. במדבר סיני באחד לחדש. מתוך חיבתן לפניו מונה אותם כל שעה. (א) כשיצאו ממצרים מנאן, וכשנפלו בעגל מנאן לידע (ב) מנין הנותרים, כשבא להשרות שכינתו עליהם מנאם, באחד בניסן הוקם המשכן, ובאחד (ג) באייר מנאם:", object.get(JBO_TEXT));
    }

    @Test
    public void testSixthObjectBook4() {
        Map<String, String> object;
        object = subjectJsons[4].getObject(5);
        assertEquals("jbr:tanach-ramban-4-1-1", object.get(URI));
    }

    @Test
    public void testLastObjectBook4() {
        Map<String, String> object;
        object = subjectJsons[4].getObject(subjectJsons[4].subjects.size() -1);
        assertEquals("jbr:tanach-ibnezra-4-36-13", object.get(URI));
    }

    @Test
    public void testFirstObjectBook27() {
        Map<String, String> object;
        object = subjectJsons[27].getObject(0);
        assertEquals("jbr:tanach-rashi-27-1-1", object.get(URI));
    }

    @Test
    public void testSecondObjectBook27() {
        Map<String, String> object;
        object = subjectJsons[27].getObject(1);
        assertEquals("jbr:tanach-metzudatdavid-27-1-1", object.get(URI));
    }

    @Test
    public void testThirdObjectBook27() {
        Map<String, String> object;
        object = subjectJsons[27].getObject(2);
        assertEquals("jbr:tanach-metzudattzion-27-1-1", object.get(URI));
    }

    @Test
    public void testFourthObjectBook27() {
        Map<String, String> object;
        object = subjectJsons[27].getObject(3);
        assertEquals("jbr:tanach-malbiminyan-27-1-1", object.get(URI));
    }

    @Test
    public void testFourthObject27_2() {
        Map<String, String> object;
        object = subjectJsons[27].getObjectByText("שם לאום מציין תמיד את האומה מצד הדת");
        assertEquals("jbr:tanach-malbimmilot-27-2-1", object.get(URI));
    }

    @Test
    public void testGetMefareshId() {
        MefareshParser parser = new MefareshParser();
        assertEquals(11, parser.getMefareshId("מלבי\"ם - באור הענין  למה, אחר שבאר אבדן הרשע כי הוא כמוץ אשר תדפנו רוח ואין לו תקוה רק אם הוא שומר את הבר ונוטר את הצדיק, אומר. אם כן למה רגשו גוים למרוד במשיח ה' ובישראל עמו, הלא אך בזה יהיה להם קיום אם יעבדו את ה' ואת דוד מלכו. למה רגשו גוים, יש הבדל בין גוים ובין לאומים, גוי נקרא הקיבוץ מבלי השקף אם הוא בעל דת או לאו, ובשם לאום נקרא מצד שמתאחד תחת דת מיוחד, ומצייר שהקשר שהתקשרו נגדו היה אלהותיי וגם מדיניי, כמ''ש על ה' ועל משיחו, על ה', לפרוק עול הדת ושבע מצות שהכריחם דוד לקיימם, ועל משיחו, לפרוק עול מלכותו ועבודתו, והגוים רגשו ברגש ומרד למרוד במלכות בית דוד, והלאומים מצד ענין הדת הגו מחשבות נגד דת ומצות ה' : (מלבי\"ם באור הענין)\n"));
        assertEquals(7, parser.getMefareshId("כלי יקר  מבן עשרים שנה ומעלה וגו'. אם מספר זה היה בעבור השראת השכינה למה אמר כל יוצא צבא."));
    }
}