package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TalmudBavliParser;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.util.Map;

/**
 * Created by USER on 25-Jan-17.
 */
public class TalmudBavliParserTest {
    private static final int NUM_OF_FIRST_MASECHET = 1;
    private static final int NUM_OF_LAST_MASECHET = 3;
    private static final int NUM_OF_MASACHTOT = NUM_OF_LAST_MASECHET - NUM_OF_FIRST_MASECHET +1;
    static SubjectsJson json[] = new SubjectsJson[NUM_OF_MASACHTOT + 1];

    @BeforeClass
    public static void setup() throws Exception {
        for (int masechetNum = NUM_OF_FIRST_MASECHET; masechetNum <= NUM_OF_LAST_MASECHET; masechetNum++) {
            System.out.println("Creating json for masechet number " + masechetNum);
            TalmudBavliParser parser = new TalmudBavliParser();
            BufferedReader reader = getText("/talmudbavli/talmudbavli-" + masechetNum + ".txt");
            parser.parse(reader, "json/talmudbavli/talmudbavli-" + masechetNum + ".json");
            json[masechetNum] = getJson("json/talmudbavli/talmudbavli-" + masechetNum + ".json");
        }
        System.out.println("");
    }

    @Test
    public void testNumOfAmudimPerMasechet() {
        System.out.print("testNumOfAmudimPerMasechet: ");
        for (int masechetNum = NUM_OF_FIRST_MASECHET; masechetNum <= NUM_OF_LAST_MASECHET; masechetNum++) {
            assertNotNull(json[masechetNum]);
            assertEquals(numOfObjectsForMasechet(masechetNum),json[masechetNum].subjects.size());
        }
        System.out.print("Success! :)\n");
    }

    @Test
    public void testSpecificObjects() {
        System.out.print("testSpecificObjects: ");
        Map<String, String> object;
        String text;
        //masechet 1 daf 2 amud 1
        object = json[1].getObject(0);
        assertEquals("jbr:bavli-1-2-1", object.get(URI));
        assertEquals("bavli-1", object.get(JBO_MASECHET));
        assertEquals("פרק ראשון - מאימתי", object.get(JBO_PEREK));
        assertEquals("ברכות ב א", object.get(RDFS_LABEL));
        assertEquals("1", object.get(JBO_POSITION));
        text = "מתני' מאימתי קורין את שמע בערבין. משעה שהכהנים נכנסים לאכול בתרומתן עד סוף האשמורה הראשונה דברי ר' אליעזר. וחכמים אומרים עד חצות. רבן גמליאל אומר עד שיעלה עמוד השחר. מעשה ובאו בניו מבית המשתה אמרו לו לא קרינו את שמע אמר להם אם לא עלה עמוד השחר חייבין אתם לקרות ולא זו בלבד אמרו אלא כל מה שאמרו חכמים עד חצות מצותן עד שיעלה עמוד השחר הקטר חלבים ואברים מצותן עד שיעלה עמוד השחר וכל הנאכלים ליום אחד מצותן עד שיעלה עמוד השחר א''כ למה אמרו חכמים עד חצות כדי להרחיק אדם מן העבירה: גמ' תנא היכא קאי דקתני מאימתי ותו מאי שנא דתני בערבית ברישא לתני דשחרית ברישא תנא אקרא קאי דכתיב {דברים ו-ז} בשכבך ובקומך והכי קתני זמן קריאת שמע דשכיבה אימת משעה שהכהנים נכנסין לאכול בתרומתן ואי בעית אימא יליף מברייתו של עולם דכתיב {בראשית א-ה} ויהי ערב ויהי בקר יום אחד אי הכי סיפא דקתני בשחר מברך שתים לפניה ואחת לאחריה ובערב מברך שתים לפניה ושתים לאחריה לתני דערבית ברישא. תנא פתח בערבית והדר תני בשחרית עד דקאי בשחרית פריש מילי דשחרית והדר פריש מילי דערבית: אמר מר משעה שהכהנים נכנסים לאכול בתרומתן. מכדי כהנים אימת קא אכלי תרומה משעת צאת הכוכבים לתני משעת צאת הכוכבים. מלתא אגב אורחיה קמשמע לן כהנים אימת קא אכלי בתרומה משעת צאת הכוכבים והא קמשמע לן דכפרה לא מעכבא כדתניא {ויקרא כב-ז} ובא השמש וטהר ביאת שמשו מעכבתו מלאכול בתרומה ואין כפרתו מעכבתו מלאכול בתרומה. וממאי דהאי ובא השמש ביאת השמש והאי וטהר טהר יומא";
        assertEquals(text, object.get(JBO_TEXT));
        //masechet 1 daf 2 amud 1 - perush rashi
        object = json[1].getObject(1);
        assertEquals("jbr:bavli-rashi-1-2-1", object.get(URI));
        assertEquals("bavli-1", object.get(JBO_MASECHET));
        assertEquals("פרק ראשון - מאימתי", object.get(JBO_PEREK));
        assertEquals("רשי ברכות ב א", object.get(RDFS_LABEL));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("jbr:bavli-1-2-1", object.get(JBO_MEFARESH));
        text = "רש\"י  מאימתי קורין את שמע בערבין. משעה שהכהנים נכנסים לאכול בתרומתן. כהנים שנטמאו וטבלו והעריב שמשן והגיע עתם לאכול בתרומה: עד סוף האשמורה הראשונה. שליש הלילה כדמפרש בגמ' (דף ג.) ומשם ואילך עבר זמן דלא מקרי תו זמן שכיבה ולא קרינן ביה בשכבך ומקמי הכי נמי לאו זמן שכיבה לפיכך הקורא קודם לכן לא יצא ידי חובתו. אם כן למה קורין אותה בבית הכנסת כדי לעמוד בתפלה מתוך דברי תורה והכי תניא בבריי' בברכות ירושלמי. ולפיכך חובה עלינו לקרותה משתחשך. ובקריאת פרשה ראשונה שאדם קורא על מטתו יצא: עד שיעלה עמוד השחר. שכל הלילה קרוי זמן שכיבה: הקטר חלבים ואברים. של קרבנות שנזרק דמן ביום: מצותן. להעלות כל הלילה ואינן נפסלים בלינה עד שיעלה עמוד השחר והן למטה מן המזבח דכתי' לא ילין לבקר (שמות לד): חלבים. של כל קרבנות: אברים. של עולה: וכל הנאכלים ליום אחד. כגון חטאת ואשם וכבשי עצרת ומנחות ותודה: מצותן. זמן אכילתן: עד שיעלה עמוד השחר. והוא מביאן להיות נותר דכתיב בתודה לא יניח ממנו עד בקר (ויקרא ז) וכלם מתודה ילמדו: אם כן למה אמרו חכמים עד חצות. בקריאת שמע ובאכילת קדשי': כדי להרחיק אדם מן העבירה. ואסרום באכילה קודם זמנן כדי שלא יבא לאכלן לאחר עמוד השחר ויתחייב כרת וכן בקריאת שמע לזרז את האדם שלא יאמר יש לי עוד שהות ובתוך כך יעלה עמוד השחר ועבר לו הזמן. והקטר חלבים דקתני הכא לא אמרו בו חכמים עד חצות כלל ולא נקט להו הכא אלא להודיע שכל דבר הנוהג בלילה כשר כל הלילה. והכי נמי תנן בפרק שני דמגילה (דף כ:) כל הלילה כשר לקצירת העומר ולהקטר חלבים ואברים: גמ' היכא קאי. מהיכא קא סליק דתנא ביה חובת קריאת שמע שהתחיל לשאול כאן זמן הקריאה: אקרא קאי. ושם למד חובת הקריאה: ואיבע''א. הא דתנא ערבין ברישא יליף מברייתו של עולם: והדר תנא בשחרית. מאימתי קורין את שמע בשחרית: משעת צאת הכוכבים. שהוא גמר ביאת השמש. כדיליף לקמן (עמוד ב): (רש\"י)";
        assertEquals(text, object.get(JBO_TEXT));
        //masechet 1 daf 2 amud 1 - perush tosafot
        object = json[1].getObject(2);
        assertEquals("jbr:bavli-tosafot-1-2-1", object.get(URI));
        assertEquals("bavli-1", object.get(JBO_MASECHET));
        assertEquals("פרק ראשון - מאימתי", object.get(JBO_PEREK));
        assertEquals("תוספות ברכות ב א", object.get(RDFS_LABEL));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("jbr:bavli-1-2-1", object.get(JBO_MEFARESH));
        text = "תוספות  מאימתי קורין וכו'. פי' רש''י ואנן היכי קרינן מבעוד יום ואין אנו ממתינין לצאת הכוכבים כדמפרש בגמ' על כן פירש רש''י שקריאת שמע שעל המטה עיקר והוא לאחר צאת הכוכבים. והכי איתא בירושלמי אם קרא קודם לכן לא יצא ואם כן למה אנו מתפללין קריאת שמע בבית הכנסת כדי לעמוד בתפלה מתוך דברי תורה. תימא לפירושו והלא אין העולם רגילין לקרות סמוך לשכיבה אלא פרשה ראשונה (לקמן דף ס:) ואם כן שלש פרשיות היה לו לקרות. ועוד קשה דצריך לברך בקריאת שמע שתים לפניה ושתים לאחריה בערבית. ועוד דאותה קריאת שמע סמוך למטה אינה אלא בשביל המזיקין כדאמר בסמוך (דף ה.) ואם תלמיד חכם הוא אינו צריך. ועוד קשה דא''כ פסקינן כרבי יהושע בן לוי דאמר תפלות באמצע תקנום פי' באמצע בין שני קריאת שמע בין קריאת שמע של שחרית ובין ק''ש של ערבית. ואנן קיי''ל כר' יוחנן דאמר לקמן (דף ד) איזהו בן העולם הבא זה הסומך גאולה של ערבית לתפלה. לכן פי' ר''ת דאדרבה קריאת שמע של בית הכנסת עיקר. ואם תאמר היאך אנו קורין כל כך מבעוד יום. ויש לומר דקיימא לן כרבי יהודה דאמר בפרק תפלת השחר (דף כו.) דזמן תפלת מנחה עד פלג המנחה דהיינו אחד עשר שעות פחות רביע ומיד כשיכלה זמן המנחה מתחיל זמן ערבית. ואם תאמר היאך אנו מתפללין תפלת מנחה סמוך לחשכה ואפילו לאחר פלג המנחה. יש לומר דקיימא לן כרבנן דאמרי זמן תפלת המנחה עד הערב ואמרינן לקמן (דף כז.) השתא דלא אתמר הלכתא לא כמר ולא כמר דעבד כמר עבד ודעבד כמר עבד. מכל מקום קשיא דהוי כתרי קולי דסתרן אהדדי שהרי מאיזה טעם אנו מתפללין ערבית מיד לאחר פלג המנחה משום דקיימא לן דשעת המנחה כלה כדברי רבי יהודה ומיד הוי זמן ערבית ובזמן התפלה עצמה לא קיימא לן כרבי יהודה אלא כרבנן. על כן אומר ר''י דודאי קריאת שמע של בית הכנסת עיקר ואנו שמתפללין ערבית מבעוד יום סבירא לן כהני תנאי דגמרא דאמרי משעה שקדש היום וגם משעה שבני אדם נכנסים להסב דהיינו סעודת ע''ש והיא היתה מבעוד יום ומאותה שעה הוי זמן תפלה. וגם ראיה (לקמן כז) דרב הוי מצלי של שבת בערב שבת ומסתמא גם היה קורא קריאת שמע. מכל אותן הראיות משמע דקריאת שמע של בית הכנסת היא עיקר. והא דקאמר בירושלמי למה היו קורין בבהכ''נ וכו' אומר ר''ת שהיו רגילין לקרות ק''ש קודם תפלתם כמו שאנו רגילין לומר אשרי תחלה ואותה ק''ש אינה אלא לעמוד בתפלה מתוך דברי תורה. ומכאן נראה מי שקורא ק''ש על מטתו שאין לברך וגם אינו צריך לקרות אלא פרשה ראשונה: ליתני דשחרית ברישא. כדאשכחן בתמיד דכתיב של בקר תחלה: אי הכי סיפא דקתני שחרית ברישא. אי אמרת בשלמא דסמיך אקרא דבשכבך א''כ אינו מקפיד קרא אלא אק''ש. אלא א''א דסמיך אקרא דברייתו של עולם א''כ קפיד אכל מילי א''כ סיפא דקתני וכו': מברך שתים לפניה וכו'. (ירושלמי) ושבע ברכות הוי כנגד שבע ביום הללתיך (תהילים קיט) ולא קא חשיב יראו עינינו דההיא ברכה תקנו רבנן כדי להמתין לחבריהם בבית הכנסת. ודוקא בבית הכנסת שלהם שהיו עומדים בשדה והם מסוכנים מן המזיקים אבל בבתי כנסיות שלנו אין צריכין להמתין לחבריהם אלא בלילה: והא קמ''ל דכפרה לא מעכבא. וא''ת הא תנינא חדא זימנא במס' נגעים (פי''ד) ומייתי לה בהערל (דף עד:) העריב שמשו אוכל בתרומה וי''ל דרגילות של משניות לאשמועינן בקוצר אף למה שמפורש כבר: (תוספות)";
        assertEquals(text, object.get(JBO_TEXT));

        //masechet 1 daf 64 amud 1
        object = json[1].getObject(json[1].subjects.size()-3);
        assertEquals("jbr:bavli-1-64-1", object.get(URI));
        assertEquals("bavli-1", object.get(JBO_MASECHET));
        assertEquals("פרק תשיעי - הרואה", object.get(JBO_PEREK));
        assertEquals("ברכות סד א", object.get(RDFS_LABEL));
        assertEquals("125", object.get(JBO_POSITION));
        text = "שנאמר {דברי הימים א כו-ה} פעלתי השמיני וכתיב {דברי הימים א כו-ה} כי ברכו אלהים {דברי הימים א כו-ח} כל אלה [מבני] (ל) עובד אדום המה ובניהם ואחיהם (אנשי) [איש] חיל בכח לעבודה ששים ושנים לעובד אדום אמר ר' אבין הלוי כל הדוחק את השעה שעה דוחקתו וכל הנדחה מפני השעה שעה נדחת מפניו מדרבה ורב יוסף. דרב יוסף סיני ורבה עוקר הרים אצטריכא להו שעתא שלחו להתם סיני ועוקר הרים איזה מהם קודם שלחו להו סיני קודם שהכל צריכין למרי חטיא אף על פי כן לא קבל עליו ר' יוסף דאמרי ליה כלדאי מלכת תרתין שנין מלך רבה עשרין ותרתין שנין מלך רב יוסף תרתין שנין ופלגא כל הנך שני דמלך רבה אפילו אומנא לביתיה לא קרא: ואמר רבי אבין הלוי מאי דכתיב {תהילים כ-ב} יענך ה' ביום צרה ישגבך שם אלהי יעקב אלהי יעקב ולא אלהי אברהם ויצחק מכאן לבעל הקורה שיכנס בעביה של קורה: ואמר רבי אבין הלוי כל הנהנה מסעודה שתלמיד חכם שרוי בתוכה כאילו נהנה מזיו שכינה שנאמר {שמות יח-יב} ויבא אהרן וכל זקני ישראל לאכל לחם עם חותן משה לפני האלהים וכי לפני אלהים אכלו והלא לפני משה אכלו אלא לומר לך כל הנהנה מסעודה שתלמיד חכם שרוי בתוכה כאילו נהנה מזיו שכינה: ואמר רבי אבין הלוי הנפטר מחברו אל יאמר לו לך בשלום אלא לך לשלום שהרי יתרו שאמר לו למשה {שמות ד-יח} לך לשלום עלה והצליח דוד שאמר לו לאבשלום {שמואל ב טו-ט} לך בשלום הלך ונתלה: ואמר רבי אבין הלוי הנפטר מן המת אל יאמר לו לך לשלום אלא לך בשלום שנאמר {בראשית טו-טו} ואתה תבא אל אבותיך בשלום: אמר רבי לוי בר חייא היוצא מבית הכנסת ונכנס לבית המדרש ועוסק בתורה זוכה ומקבל פני שכינה שנאמר {תהילים פד-ח} ילכו מחיל אל חיל יראה אל אלהים בציון. אמר רבי חייא בר אשי אמר רב תלמידי חכמים אין להם מנוחה לא בעולם הזה ולא בעולם הבא שנאמר ילכו מחיל אל חיל יראה אל אלהים בציון: אמר רבי אלעזר אמר רבי חנינא תלמידי חכמים מרבים שלום בעולם שנאמר {ישעיה נד-יג} וכל בניך למודי ה' ורב שלום בניך אל תקרי בניך אלא בוניך {תהילים קיט-קסה} שלום רב לאוהבי תורתך ואין למו מכשול {תהילים קכב-ז} יהי שלום בחילך שלוה בארמנותיך {תהילים קכב-ח} למען אחי ורעי אדברה נא שלום בך {תהילים קכב-ט} למען בית ה' אלהינו אבקשה טוב לך {תהילים כט-יא} ה' עוז לעמו יתן ה' יברך את עמו בשלום:";
        assertEquals(text, object.get(JBO_TEXT));
        //masechet 1 daf 64 amud 1 - perush rashi
        object = json[1].getObject(json[1].subjects.size()-2);
        assertEquals("jbr:bavli-rashi-1-64-1", object.get(URI));
        assertEquals("bavli-1", object.get(JBO_MASECHET));
        assertEquals("פרק תשיעי - הרואה", object.get(JBO_PEREK));
        assertEquals("רשי ברכות סד א", object.get(RDFS_LABEL));
        assertEquals("125", object.get(JBO_POSITION));
        assertEquals("jbr:bavli-1-64-1", object.get(JBO_MEFARESH));
        text = "רש\"י  שנאמר פעלתי השמיני. בני עובד אדום קא חשיב בדברי הימים וקא חשיב תמניא והיא תשיעית כל אחת ילדה ששה הם חמשים וארבע הוסיף עליהם שמונה בנים הראשונים הרי ששים ושנים לעובד אדום: הדוחק את השעה. כגון אבשלום שבקש למלוך בחזקה: אצטריכא להו שעתא. להיות אחד מהם ראש ישיבה. אצטריכא להו גרסי' הוצרכו להם חכמים: סיני. היו קורין לרב יוסף שהיה בקי בברייתות הרבה: עוקר הרים. לרבה בר נחמני שהיה מחודד יותר בפלפול: למרי חטיא. למי שקבץ תבואה למכור כלומר למי שקבץ שמועות: דאמרי ליה כלדאי. לרב יוסף: מלכת תרתין שנין. אמר אם אמלוך תחלה אמות לסוף שנתים ונדחה מפני השעה ולא אבה למלוך והשעה עמדה לו שלא הפסיד שנותיו בכך: אומנא לביתיה לא קרא. לא נהג כל אותן השנים שום צד שררה וכשהיה צריך להקיז דם היה הולך לבית הרופא ולא היה שולח לבא אליו: שיאחז בעובי הקורה. אם בא לטלטלה ממקום למקום לתתה בבנין כלומר יעקב שכל הבנים שלו וטרח בגידולם יבקש עליהם רחמים: הנפטר מן המת. כשהיו מוליכים ארונות ממקום למקום לקבורה היו מלוים אותו מעיר לעיר וחוזרים אלו ואחרים מלוים אותו משם והלאה והחוזרים קרי נפטרים נוטלין רשות להפטר ממנו: אין להם מנוחה. מישיבה לישיבה וממדרש למדרש: (רש\"י)";
        assertEquals(text, object.get(JBO_TEXT));
        //masechet 1 daf 64 amud 1 - perush tosafot
        object = json[1].getObject(json[1].subjects.size()-1);
        assertEquals("jbr:bavli-tosafot-1-64-1", object.get(URI));
        assertEquals("bavli-1", object.get(JBO_MASECHET));
        assertEquals("פרק תשיעי - הרואה", object.get(JBO_PEREK));
        assertEquals("תוספות ברכות סד א", object.get(RDFS_LABEL));
        assertEquals("125", object.get(JBO_POSITION));
        assertEquals("jbr:bavli-1-64-1", object.get(JBO_MEFARESH));
        text = "תוספות  אין פירוש לקטע זה (תוספות)";
        assertEquals(text, object.get(JBO_TEXT));

        //masechet 2 daf 157 amud 2
        object = json[2].getObject(json[2].subjects.size()-3);
        assertEquals("jbr:bavli-2-157-2", object.get(URI));
        assertEquals("bavli-2", object.get(JBO_MASECHET));
        assertEquals("פרק עשרים וארבע - מי שהחשיך", object.get(JBO_PEREK));
        assertEquals("שבת קנז ב", object.get(RDFS_LABEL));
        assertEquals("312", object.get(JBO_POSITION));
        text = "וגיגית סדוקה מונחת על גבן ופקקו את המאור בטפיח וקשרו את המקידה בגמי לידע אם יש שם בגיגית פותח טפח אם לאו: ומדבריהם למדנו שפוקקין ומודדין וקושרין בשבת: עולא איקלע לבי ריש גלותא חזייה לרבה בר רב הונא דיתיב באוונא דמיא וקא משח ליה אמר ליה אימר דאמרי רבנן מדידה דמצוה דלאו מצוה מי אמור אמר ליה מתעסק בעלמא אנא:";
        assertEquals(text, object.get(JBO_TEXT));
        //masechet 2 daf 157 amud 2 - perush rashi
        object = json[2].getObject(json[2].subjects.size()-2);
        assertEquals("jbr:bavli-rashi-2-157-2", object.get(URI));
        assertEquals("bavli-2", object.get(JBO_MASECHET));
        assertEquals("פרק עשרים וארבע - מי שהחשיך", object.get(JBO_PEREK));
        assertEquals("רשי שבת קנז ב", object.get(RDFS_LABEL));
        assertEquals("312", object.get(JBO_POSITION));
        assertEquals("jbr:bavli-2-157-2", object.get(JBO_MEFARESH));
        text = "רש\"י  וגיגית סדוקה מונחת על גביו. והמת מוטל בהילקטי תחת הגיגית [כנגד] הסדק ולפני מות המת פקקו בשבת המאור בטפיח שמא אין בסדק הגיגית פותח טפח ונמצא המת מוטל באהל שאין לו מקום לצאת דרך מעלה וחור שבין שני בתים מכניס את הטומאה לצד שני במלא אגרוף לפיכך סתמוהו בכלי חרס וגבו כנגד ההילקט וכלי חרס אינו מטמא מגבו וחוצץ: וקשרו מקידה. שהיא רחבה טפח: בגמי לידע. אם תכנס בסדק הגיגית ובמסכת אהלות (פ''י) שנינו (דאין) חילוק בארובה שבתוך הבית וטומאה מקצתה כנגד ארובה ומקצתה בתוך הבית בין יש בארובה פותח טפח לאין בארובה פותח טפח ואני לא יכולתי להבין בו ולהכי נקט גמי שראוי למאכל בהמה ולא מיבטל ליה להיות קשר של קיימא דמנתק כשיבש: באוונא. בגיגית מלאה מים: מתעסק. שלא לצורך אלא לאיעסוקי בעלמא: (רש\"י)";
        assertEquals(text, object.get(JBO_TEXT));
        //masechet 2 daf 157 amud 2 - perush tosafot
        object = json[2].getObject(json[2].subjects.size()-1);
        assertEquals("jbr:bavli-tosafot-2-157-2", object.get(URI));
        assertEquals("bavli-2", object.get(JBO_MASECHET));
        assertEquals("פרק עשרים וארבע - מי שהחשיך", object.get(JBO_PEREK));
        assertEquals("תוספות שבת קנז ב", object.get(RDFS_LABEL));
        assertEquals("312", object.get(JBO_POSITION));
        assertEquals("jbr:bavli-2-157-2", object.get(JBO_MEFARESH));
        text = "תוספות  באוונא דמיא. אמבטי של מים וכך פירש בערוך: מדידה דמצוה דלאו דמצוה מי אמור כו'. בפקיקה לא בעינן של מצוה כדפירש' לעיל בשילהי כל הכלים (דף קכו:) אלא דווקא במדידה בעינן של מצוה: (תוספות)";
        assertEquals(text, object.get(JBO_TEXT));




        System.out.print("Success! :)\n");
    }






    private int numOfObjectsForMasechet(int masechetNum) {
        final int objectsPerAmud = 3;
        int amudimPerMasechet[] = {0, (63-1)*2+1, (157-1)*2 , (104-1)*2+1, (121-1)*2};
        int skippedPerushinPerMasechet[] = {0, 1, 5, 0, 0};
        return amudimPerMasechet[masechetNum]*objectsPerAmud-skippedPerushinPerMasechet[masechetNum];
    }

    String text = "";

}
