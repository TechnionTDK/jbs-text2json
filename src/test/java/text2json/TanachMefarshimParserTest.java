package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TanachMefarshimParser;

import java.io.BufferedReader;
import java.util.Map;
import static text2json.JbsOntology.*;
import static org.junit.Assert.*;
import static text2json.TestUtils.*;

/**
 * Created by omishali.
 */
public class TanachMefarshimParserTest {
    private static final int NUM_OF_LAST_BOOK = 39;
    private static final int NUM_OF_FIRST_BOOK = 1;
    private static final int NUM_OF_BOOKS = NUM_OF_LAST_BOOK - NUM_OF_FIRST_BOOK + 1;
    static SubjectsJson[] subjectJsons = new SubjectsJson[NUM_OF_BOOKS + 1];

    @BeforeClass
    public static void setup() throws Exception {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            TanachMefarshimParser parser = new TanachMefarshimParser();
            BufferedReader reader = getText("/tanach/tanach-" + bookNum + ".txt");
            createOutputFolderIfNotExists("tanachMefarshim");
            parser.parse(reader, "json/tanachMefarshim/tanachMefarshim-" + bookNum + ".json");
            subjectJsons[bookNum] = getJson("json/tanachMefarshim/tanachMefarshim-" + bookNum + ".json");
        }
    }

    @Test
    public void testSpecificObjects() {
        Map<String, String> object;
        object = subjectJsons[4].getObjectByText("הוקשה אל המפרשים מאחר שפני המנורה היינו הנר האמצעי");
        assertEquals(JBR_TEXT + "tanach-keliyekar-4-8-2", object.get(URI));
        assertBookProperty( object,"keliyekar");
        // now we check that the object contains also text belonging to the last line.
        assertTrue(object.get(JBO_TEXT).contains("והוא ענין נכון וברור וזה סמיכות העלאת הנרות"));

        object = subjectJsons[4].getObjectByText("למה נסמכה פרשת המנורה לפרשת הנשיאים, לפי שכשראה אהרן חנוכת הנשיאים חלשה אז דעתו");
        assertEquals(JBR_TEXT + "tanach-rashi-4-8-2", object.get(URI));
        assertBookProperty( object,"rashi");

        object = subjectJsons[4].getObject(0);
        assertEquals(JBR_TEXT + "tanach-onkelos-4-1-1", object.get(URI));
        assertEquals("אונקלוס  וּמַלִיל יְיָ עִם משֶׁה בְּמַדְבְּרָא דְסִינַי בְּמַשְׁכַּן זִמְנָא בְּחַד לְיַרְחָא תִנְיָנָא בְּשַׁתָּא תִנְיֵתָא לְמִפַּקְהוֹן מֵאַרְעָא דְמִצְרַיִם לְמֵימָר:", object.get(JBO_TEXT));
        assertBookProperty( object,"onkelos");

        object = subjectJsons[4].getObject(1);
        assertEquals(JBR_TEXT + "tanach-yonatan-4-1-1", object.get(URI));
        assertBookProperty( object,"yonatan");

        object = subjectJsons[4].getObject(2);
        assertEquals(JBR_TEXT + "tanach-rashi-4-1-1", object.get(URI));
        assertEquals("רש\"י  וידבר. במדבר סיני באחד לחדש. מתוך חיבתן לפניו מונה אותם כל שעה. (א) כשיצאו ממצרים מנאן, וכשנפלו בעגל מנאן לידע (ב) מנין הנותרים, כשבא להשרות שכינתו עליהם מנאם, באחד בניסן הוקם המשכן, ובאחד (ג) באייר מנאם:", object.get(JBO_TEXT));

        object = subjectJsons[4].getObject(5);
        assertEquals(JBR_TEXT + "tanach-ramban-4-1-1", object.get(URI));
        assertBookProperty( object,"ramban");

        object = subjectJsons[4].getObject(subjectJsons[4].subjects.size() -1);
        assertEquals(JBR_TEXT + "tanach-ibnezra-4-36-13", object.get(URI));
        assertBookProperty( object,"ibnezra");

        object = subjectJsons[27].getObject(0);
        assertEquals(JBR_TEXT + "tanach-rashi-27-1-1", object.get(URI));

        object = subjectJsons[27].getObject(1);
        assertEquals(JBR_TEXT + "tanach-metzudatdavid-27-1-1", object.get(URI));
        assertBookProperty( object,"metzudatdavid");

        object = subjectJsons[27].getObject(2);
        assertEquals(JBR_TEXT + "tanach-metzudattzion-27-1-1", object.get(URI));
        assertBookProperty( object,"metzudattzion");

        object = subjectJsons[27].getObject(3);
        assertEquals(JBR_TEXT + "tanach-malbiminyan-27-1-1", object.get(URI));
        assertBookProperty( object,"malbim");

        object = subjectJsons[27].getObjectByText("שם לאום מציין תמיד את האומה מצד הדת");
        assertEquals(JBR_TEXT + "tanach-malbimmilot-27-2-1", object.get(URI));
        assertBookProperty( object,"malbim");

        object = subjectJsons[11].getObject(JBR_TEXT + "tanach-malbim-11-17-29");
        assertTrue(object.get(JBO_TEXT).contains("באופן שאנשי בבל עשו את סוכות"));
        assertBookProperty( object,"malbim");

        object = subjectJsons[11].getObject(JBR_TEXT + "tanach-malbim-11-17-32");
        assertTrue(object.get(JBO_TEXT).contains("וכן היו עושים הכהנים להם בבית הבמות"));
        assertBookProperty( object,"malbim");

        object = subjectJsons[11].getObject(JBR_TEXT + "tanach-malbim-11-17-33");
        assertTrue(object.get(JBO_TEXT).contains("כמשפט הגוים רצה לומר ונדמו בזה"));
        assertBookProperty( object,"malbim");

        object = subjectJsons[9].getObject(JBR_TEXT + "tanach-malbim-9-6-20");
        assertTrue(object.get(JBO_TEXT).contains("גם זה מגלה צדקתו שאחר שהשפיע לכל העם פנה אל ביתו"));
        assertBookProperty( object,"malbim");
    }

    @Test
    public void testGetMefareshId() {
        TanachMefarshimParser parser = new TanachMefarshimParser();
        assertEquals(11, parser.getMefareshId("מלבי\"ם - באור הענין  למה, אחר שבאר אבדן הרשע כי הוא כמוץ אשר תדפנו רוח ואין לו תקוה רק אם הוא שומר את הבר ונוטר את הצדיק, אומר. אם כן למה רגשו גוים למרוד במשיח ה' ובישראל עמו, הלא אך בזה יהיה להם קיום אם יעבדו את ה' ואת דוד מלכו. למה רגשו גוים, יש הבדל בין גוים ובין לאומים, גוי נקרא הקיבוץ מבלי השקף אם הוא בעל דת או לאו, ובשם לאום נקרא מצד שמתאחד תחת דת מיוחד, ומצייר שהקשר שהתקשרו נגדו היה אלהותיי וגם מדיניי, כמ''ש על ה' ועל משיחו, על ה', לפרוק עול הדת ושבע מצות שהכריחם דוד לקיימם, ועל משיחו, לפרוק עול מלכותו ועבודתו, והגוים רגשו ברגש ומרד למרוד במלכות בית דוד, והלאומים מצד ענין הדת הגו מחשבות נגד דת ומצות ה' : (מלבי\"ם באור הענין)\n"));
        assertEquals(7, parser.getMefareshId("כלי יקר  מבן עשרים שנה ומעלה וגו'. אם מספר זה היה בעבור השראת השכינה למה אמר כל יוצא צבא."));
    }
}