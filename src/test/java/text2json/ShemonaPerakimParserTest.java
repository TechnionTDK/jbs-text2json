package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ShemonaPerakimParser;

import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.JBO_TEXT;
import static text2json.TestUtils.*;

/**
 * Created by orel on 22/07/18.
 */
public class ShemonaPerakimParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new ShemonaPerakimParser() , "shemonaperakim");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(9, json.subjects.size());  //hakdama + 8 chapters
    }

    @Test
    public void testSpecificObjects() {
        // test first object
        Map<String, String> o = json.getObject(0);
        assertTextUriProperty( o , "shemonaperakim-0");
        assertPositionProperty(o, "0");
        String text = "הנה בארנו בפתיחת זה החבור מה היתה הסבה בשום המחבר זאת המסכתא בזה הסדר, וזכרנו גם כן רוב התועלת בזאת המסכתא, ויעדנו פעמים רבות במה שקדם מזה החבור לדבר בזאת המסכתא בענינים מועילים, ולהאריך בה קצת אריכות מפני שהיא אף על פי שתראה מבוארת קלה להבין לעשות מה שבכללה אינו קל על כל בני אדם, ואין כל עניניה גם כן מובנים מבלי פירוש ארוך עם היותו מביאה אל שלמות גדול והצלחה אמתית, ומפני זה ראיתי להרחיב בה המאמר, וכבר אמרו ז\"ל האי מאן דבעי למהוי חסידא לקיים מילי דאבות, ואין אצלנו מעלה גדולה מחסידות אלא הנבואה, והיא המביאה אליה כמו שאמרו חסידות מביאה לידי רוח הקודש, הנה התבאר מדבריהם שעשות מוסרי זאת המסכתא מביא לידי נבואה, והנני עתיד לבאר אמיתת הדבר ההוא מפני שהיא כוללת על חלק גדול מהמדות. וראיתי להקדים קודם שאתחיל בפירוש הלכה הלכה פרקים מועילים ידע מהם האדם הקדמות, ויהיו לו גם כן כמפתח במה שאני עתיד לפרש, ודע שהדברים שאומרם באלו הפרקים, ובמה שיבא מן הפירוש אינם ענינים בדיתים אני מעצמי, ולא פירושים שחדשתים, ואמנם הם ענינים לקטתים מדברי החכמים במדרשות ובתלמוד וזולתו מחבוריהם, ומדברי הפילוסופים גם כן הקדומים והחדשים, ומחבורים הרבה מבני אדם, ושמע האמת ממי שאמרו, ואפשר שאביא פעמים מאמר אחד כלו מספר מפורסם בלשונו, ואין בכל זה רוע, ואיני מתפאר במה שאמרו מי שקדם, שאני כבר התודעתי בזה, ואף על פי שלא אזכור אמר פלוני אמר פלוני שזה אריכות אין תועלת בו, ואפשר שהיה מביא שם האיש ההוא לחשוב מי שאין חיך לו שהדבר ההוא נפסד, ובתוכו רע, שלא יבינהו, ומפני זה ראיתי שלא לזכור האומר, שכוונתי התועלת לקורא ולבאר לו הענינים הגנוזים בזאת המסכתא, ואני מתחיל עתה לזכור הפרקים אשר ראיתי להקדים הנה לפי כוונתי, והם שמונה פרקים:";
        assertEquals(text, o.get(JBO_TEXT));
        assertLabelProperty( o ,"שמונה פרקים לרמב\"ם הקדמה");
        assertBookProperty(o,"shemonaperakim");

        // test chapter 1
        o = json.getObject(1);
        assertBookProperty(o,"shemonaperakim");
        assertTextUriProperty( o , "shemonaperakim-1");
        assertPositionProperty(o, "1");
        assertLabelProperty( o ,"שמונה פרקים לרמב\"ם פרק 1");
        text = "דע שנפש האדם נפש אחת ויש לה פעולות רבות חלוקות, יקראו קצת הפעולות הם נפשות";
        assertTrue(o.get(JBO_TEXT).startsWith(text));
        text = "והדעות כמה הם ואיך יגיעו אין זה מקומו ולא יצטרך במה שנרצהו לדבר על המדות, והוא יותר ראוי בספר הנבואה אשר זכרנו ובכאן אפסוק בזה הפרק ואתחיל באחר:";
        assertTrue(o.get(JBO_TEXT).endsWith(text));


        // test last object
        o = json.getObject(json.subjects.size()-1);
        assertBookProperty(o,"shemonaperakim");
        assertTextUriProperty( o , "shemonaperakim-8");
        assertPositionProperty(o, "8");
        assertLabelProperty( o ,"שמונה פרקים לרמב\"ם פרק 8");
        text = "אי אפשר שיולד האדם מתחלת ענינו בטבע בעל מעלה ולא בעל חסרון";
        assertTrue(o.get(JBO_TEXT).startsWith(text));
        text = "וכבר הגיעה עת ואפסוק הדברים הנה, ואתחיל בפרוש זאת המסכתא אשר הקדמנו לה אליו אילו הפרקים:";
        assertTrue(o.get(JBO_TEXT).endsWith(text));

    }
}