package text2json;

import com.google.gson.stream.JsonReader;
import com.sun.deploy.util.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MefareshParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by shilonoa on 1/18/2017.
 */
public class MefareshParserTest {
    private static final int NUM_OF_BOOKS = 39;
    private static final int NUM_OF_FIRST_BOOK = 1;
    int booknum = 0;
    int pasukNum = 0;
    String mefaresh = null;

    @BeforeClass
    public static void setup() throws IOException {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_BOOKS; bookNum++) {
            System.out.println("Creating mefarshim json for book number " + bookNum);
            MefareshParser parser = new MefareshParser();
            BufferedReader reader = TestUtils.getText("/tanach/tanach-" + bookNum + ".txt");
            parser.parse(reader, "json/tanachMefarshim-" + bookNum + ".json");
        }
        return;
    }

    @Test
    public void test() throws IOException {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_BOOKS; bookNum++) {
            System.out.println("Testing book number " + bookNum);
            booknum = bookNum;
            testTanachMefarshim(bookNum);
            System.out.println("SUCCESS :)");
        }
    }

    private void testTanachMefarshim(int bookNum) throws IOException {
        JsonReader jsonReader = new JsonReader(TestUtils.getJsonFileReader("tanachMefarshim-" + bookNum + ".json"));
        jsonReader.beginObject();

        assertEquals(jsonReader.nextName(), "subjects");
        jsonReader.beginArray();
        int perekNum = 1;
        pasukNum = 1;
        int positionInSefer = 0;
        int positionInParasha = 0;
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            //======== URI ==============
            assertEquals(jsonReader.nextName(), "uri");
            boolean adv_perek = testUri(jsonReader, bookNum, perekNum);
            if (adv_perek == true) {
                perekNum++;
                pasukNum = 1;
            }
            //======== JBO_TEXT ==============
            assertEquals(jsonReader.nextName(), "jbo:text");
            testText(jsonReader, bookNum, perekNum);
            //======== LABLE ==============
            assertEquals(jsonReader.nextName(), "rdfs:label");
            assertNotNull(jsonReader.nextString());
            //======== NAME ==============
            assertEquals(jsonReader.nextName(), "jbo:name");
            assertNotNull(jsonReader.nextString());
            //======== SEFER ==============
            assertEquals(jsonReader.nextName(), "jbo:sefer");
            testSefer(jsonReader, bookNum);
            //======== TITLES ==============
            //assertEquals(jsonReader.nextName(), "titles");
            //testTitels(jsonReader);
            //end of titles block
            //======== PARASHA ==============
            if (bookNum <= 5) {
                assertEquals(jsonReader.nextName(), "jbo:parasha");
                jsonReader.nextString();
            }
            //======== PEREK ==============
            assertEquals(jsonReader.nextName(), "jbo:perek");
            jsonReader.nextString();
            //======== INTERPRETS ==============
            assertEquals(jsonReader.nextName(), "jbo:interprets");
            jsonReader.nextString();
            //======== POSITIONINPEREK ==============
            assertEquals(jsonReader.nextName(), "jbo:positionInPerek");
            jsonReader.nextString();
            //======== POSITIONINPARASH ==============
            if (bookNum <= 5) {
                assertEquals(jsonReader.nextName(), "jbo:positionInParasha");
                jsonReader.nextString();
            }
            jsonReader.endObject();
        }
    }

    private boolean testUri(JsonReader jsonReader, int bookNum, int perekNum) throws IOException {
        boolean retval = false;
        Line uriLine = new Line(jsonReader.nextString());
        //System.out.println(uriLine.getLine());
        String uriMefaresh = uriLine.extract("tanach-", "-");
        mefaresh = uriMefaresh;
        int uriBook = Integer.valueOf(uriLine.extract("tanach-" + uriMefaresh + "-", "-"));
        int uriPerek = Integer.valueOf(uriLine.extract("tanach-" + uriMefaresh + "-" + uriBook + "-", "-"));
        int uriPasuk = Integer.valueOf(uriLine.extract("tanach-" + uriMefaresh + "-" + uriBook + "-" + uriPerek + "-", " "));
        assertEquals(bookNum, uriBook);
        if (Integer.valueOf(uriPerek) != perekNum) {
            perekNum++;
            pasukNum = 1;
            retval = true;
        }
        if (pasukNum != uriPasuk) {
            pasukNum = uriPasuk;
        }
        assertEquals(perekNum, uriPerek);

        return retval;
    }

    private void testSefer(JsonReader jsonReader, int bookNum) throws IOException {
        Line uriLabel = new Line(jsonReader.nextString());
        int uribookNum = Integer.valueOf(uriLabel.extract("-", " "));
        assertEquals(uribookNum, bookNum);
    }

    private void testText(JsonReader jsonReader, int bookNum, int perekNum) throws IOException {
        switch (bookNum) {
            case 7:
                testBook7Text(jsonReader, perekNum);
                break;
            case 15:
                testBook15Text(jsonReader, perekNum);
                break;
            case 38:
                testBook38Text(jsonReader, perekNum);
                break;
            default:
                jsonReader.nextString();
                break;
        }
    }

    private void testTitels(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        jsonReader.beginObject();
        assertEquals(jsonReader.nextName(), "title");
        if (((booknum >= 8) && (booknum <= 11)) || (booknum == 30)) {
            assertEquals(TestUtils.countMtches(jsonReader.nextString(), " "), 3);
        } else if (booknum >= 38) {
            assertEquals(TestUtils.countMtches(jsonReader.nextString(), " "), 4);
        } else {
            assertEquals(TestUtils.countMtches(jsonReader.nextString(), " "), 2);
        }
        jsonReader.endObject();
        jsonReader.beginObject();
        assertEquals(jsonReader.nextName(), "title");
        if (((booknum >= 8) && (booknum <= 11)) || (booknum == 30)) {
            assertEquals(TestUtils.countMtches(jsonReader.nextString(), " "), 5);
        } else if (booknum >= 38) {
            assertEquals(TestUtils.countMtches(jsonReader.nextString(), " "), 6);
        } else {
            assertEquals(TestUtils.countMtches(jsonReader.nextString(), " "), 4);
        }
        jsonReader.endObject();
        jsonReader.endArray();
    }

    private void testBook7Text(JsonReader jsonReader, int perekNum) throws IOException {
        String text = jsonReader.nextString();
        switch (perekNum) {
            case 1:
                switch (pasukNum) {
                    case 1:
                        switch (mefaresh) {
                            case "Rashi":
                                assertEquals(text, "רש\"י  מי יעלה לנו אל הכנעני. על מקומות שהפלנו בגורל ועדיין לא נכבשו");
                                break;
                            case "metzudatdavid":
                                assertEquals(text, "מצודת דוד  בה'. באורים ותומים : מי יעלה לנו. עם כי כל אחד נלחם בעבור חלקו, אמר 'לנו', כי כאשר יעלה מי מהם בהכנעני ויגבר עליו, יביא המורך בלבם והתועלת לכולם : בתחלה. לקחת חלקו אשר נפל בגורלו, ועדיין לא כבש");
                                break;
                            case "ralbag":
                                assertEquals(text, "רלב\"ג  ספר שאחרי מות יהושע שאלו בני ישראל בה' ר\"ל באורים ותומים מי מהשבטים יעלה בתחלה להלחם בכנעני הנשאר בארצות השבטים וראוי שנדע שאין הרצון בזה שלא היו נשאלין באורים ותומים בימי יהושע כי התורה העידה כי בימי יהושע ג\"כ ישאלו במשפט האורים, ואולי הרצון בזה כי אחרי מות יהושע הוצרכו לשאול מי יעלה בתחלה כי במלחמה הראשונה שורש גדול לשאר המלחמות וזה שאם ינוצחו ישראל במלחמה הראשונה יאמרו הנשאר מהגוים ההם סר צלם מעליהם ויתחזקו להלחם בהם ואם ינצחו אותם יפיל הענין מורך לב בגוים ההם וינצחום ישראל בקלות, ולזה בחר השם שילחם תחלה מי שהוא ראוי יותר לנצח והוא שבט יהודה, וסבב יהודה שעלה אתו שמעון להלחם כאשר נשארו בגבולו מהגוים ההם");
                                break;
                            default:
                                break;
                        }
                        break;
                    case 34:
                        switch (mefaresh) {
                            case "metzudatdavid":
                                assertEquals(text, "מצודת דוד  ההרה. דחקום להשאר בהר, כי לא הניחום לרדת להעמק");
                                break;
                            case "metzudattzion":
                                assertEquals(text, "מצודת ציון  וילחצו. ענין דחק, כמו (במדבר כב כה) ותלחץ אל הקיר : לא נתנו. לא הניחו, כמו (בראשית לא ז) ולא נתנו אלהים");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }

    private void testBook15Text(JsonReader jsonReader, int perekNum) throws IOException {
        String text = jsonReader.nextString();
        switch (perekNum) {
            case 1:
                switch (pasukNum) {
                    case 4:
                        switch (mefaresh) {
                            case "Rashi":
                                assertEquals(text, "רש\"י  קרא שמו יזרעאל. קרי שומהון מבדריא, התנבא עליהן שיגלו ויהיו זרועין בארצות: את דמי יזרעאל. כתרגומו את דמי בית אחאב שהרג יהוא ביזרעאל על שעבדו את הבעל והלכו הוא ובניו אחרי כן ועבדו עבודת עכו''ם לכך אני חושב עליהם דמי בית אחאב כדם נקי: על בית יהוא. ירבעם בן יואש מבני יהוא היה (מלכים ב טו) וזכריה בנו נהרג");
                                break;
                            case "metzudatdavid":
                                assertEquals(text, "מצודת דוד  כי עוד מעט. עוד זמן מועט ר''ל בזמן קרוב אזכור דמי אחאב שהרגו יהוא ביזרעאל הדם ההוא אזכור על בית יהוא לשלם גמול ועל זכריה בן ירבעם יאמר שהיה מבית יהוא ונהרג ע''י שלום בן יבש כמ''ש במ''ב ועם כי יהוא הרג לאחאב בדבר ה' על שעבד לבעל עכ''ז הואיל וגם יהוא חזר ועבד לבעל נחשב לו דם אחאב כדם נקי : והשבתי. ואח''ז אשבית ממלכות ישראל מכל וכל כי מעט מן הזמן עמדה מלכות ישראל אח''ז");
                                break;
                            case "metzudattzion":
                                assertEquals(text, "מצודת ציון  ופקדתי. ענין זכרון כמו וה' פקד (ראשית כ''א) : והשבתי. ענין בטול כמו שבת נוגש (ישעיה י''ד)");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 8:
                switch (pasukNum) {
                    case 9:
                        switch (mefaresh) {
                            case "Rashi":
                                assertEquals(text, "רש\"י  פרא בודד לו. נעשו כפרא שהולך בודד לעצמו שואף רוח ממקום למקום לנוד: התנו אהבים. י''ת לשון תנים כמו ערוד זה שהולך במדברות שאפו רוח כתנים לקנות להם אוהבים נואפי' זרים התנו אידגרוניו''ט בלע''ז ויש לפרש לשון אתנן");
                                break;
                            case "metzudatdavid":
                                assertEquals(text, "מצודת דוד  עלו אשור. לבקש מהם עזרה : פרא. המה כפרא ההולך בדד ויחידי באות נפש מבלי עצה ומבלי מנהיג : התנו אהבים. נתנו למלך אשור אתנן דרך אהבה למען יעזור לו");
                                break;
                            case "metzudattzion":
                                assertEquals(text, "מצודת ציון  פרא. הוא חמור הבר : בודד. מל' בדד ויחידי כמו איכה ישבה בדד (איכה א) : התנו. מל' אתנן זונה ואמר בל' בזיון");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void testBook38Text(JsonReader jsonReader, int perekNum) throws IOException {
        String text = jsonReader.nextString();
        switch (perekNum) {
            case 1:
                switch (pasukNum) {
                    case 12:
                        switch (mefaresh) {
                            case "Rashi":
                                assertEquals(text, "רש\"י  פתרוסים. פתרוסי ים כסלוחים כסלוחי ים פלשתים פלשתי ים כפתורים כפתורי ים לפיכך כתוב בשני יודי''ן לדרוש וכן כולם");
                                break;
                            case "metzudattzion":
                                assertEquals(text, "מצודת דוד  אשר יצאו משם פלשתים. ר''ל מבני כסלוחים באה האומה של פלשתים : ואת כפתורים. חוזר הוא על מצרים שהוא ילד את כפתורים");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 10:
                switch (pasukNum) {
                    case 13:
                        switch (mefaresh) {
                            case "Rashi":
                                assertEquals(text, "רש\"י  וימת שאול במעלו אשר מעל. שתי מעילות מעל בשאלת אוב אחת ששאל אחרי שמואל ואחת שדרש ובאגדה דשמואל שנינו חמש מעילות מעל : על דבר ה' אשר לא שמר. מה שצוהו שמואל כדכתיב שבעת ימים תוחל (שמואל א') ועל אשר מעל במלחמת עמלק");
                                break;
                            case "metzudatdavid":
                                assertEquals(text, "מצודת דוד  במעלו. בעבור מעלו וכו' וחוזר ומפרש על דבר ה' אשר לא שמר למחות את עמלק מכל וכל וגם אשר חטא לשאול באוב לדרוש בו עתידות");
                                break;
                            case "ralbag":
                                assertEquals(text, "רלב\"ג  על דבר ה' אשר לא שמר. ר''ל במה שלא הוחיל על שמואל שבע' ימים עד בואו אליו כי מפני זה אמר לו שמואל כי ממלכתו לא תקום. ובמה שלא עשה את דבד ה' בעמלק כי מפני זה אמר לו שמואל יען מאסת את דבר ה' וימאסך ממלך : וגם לשאול באוב לדרוש. ר''ל במה שבא שאול אל אשה בעלת אוב ואמר לה שתקסם לו באוב. והנה לאלו הסבות כלם המיתהו ה' יתברך וסבב שתהיה המלוכה לדוד בן ישי ולא זכר מלכות איש בשת בן שאול כי לא היה ממלכי יהודה אך זכר שאול להעיר על דבר צמיחת מלכות בית דוד איך היתה. כי לולא חטאי שאול לא היתה צומחת עדיין ואף על פי שהיה ראוי לפי מה שנתבאר מברכת יעקב שתהיה המלכות בסוף לשבט יהודה. ולזה אמר שמואל לשאול כי עתה הכין ה' את ממלכתך אל ישראל עד עולם ועתה ממלכתך לא תקום (שמואל א' י''ג י''ג). והנה הרצון באמרו עד עולם עד זמן ארוך כטעם אמרו ועבדו לעולם. הנה מה שראינו לבארו בזה החלק שהגבלנו באורו בזה המקום וברבו בארנו לפי המחשבה הגוברת לבד לעמקו ובפרט בענין התיחש הבנים לאביהם כי לפעמים השמיט דורות רבים בתולדות ולפעמים השמיט הדור האחד והשמיט התיחש הנמשך לו להשענו שיובן מדבריו זה ובמה שהוא התאר לא נוכל לאמת איך היה אם לא שעמדנו עליו ממקום אחר וראינו גם כן שאין בזה שום נזק איך שיובן בכמו אלו היוחסים וזה מבואר מאד. והנה התועלות המגיעים מזה הספור הם אלו. התועלת הראשון הוא ליסד אמונת החדוש כמו שזכרנו בבאורנו לדברי תורה ולזה זכר הסתעפות הדורות. מאדם הראשון עד הזמן ההוא. התועלת השני הוא להודיע כי ה' יתברך יענוש החוטא על חטאו וישלם טוב לצדיק כצדקתו. ולזה ספר שכבר היה ער בכור יהודה רע בעיני ה' וימיתהו. ולזה גם כן זכר בעכן שמעל מעל בחרם מפני שלא נתקיימו לו תולדות עם שבזה תועלת מפני פרסום החטאים שיהיה האדם יותר נשמר מלחטא כדי שלא יקנה לו בשת ושם רע לדורי דורות. ולזה גם כן זכר כי יואש ושרף בעלו למואב. ולזה גם כן זכר כי יעבץ היה נכבד מאחיו ולזה שמע ה' יתברך בקולו במה ששאל ממנו. ואחשוב כי יעבץ היה מהשופטים והוא אבצן כי האל''ף והעי''ן הם ממוצא אחד והם מתחלפים. והנה היה מחור בן חצרון שהיה (לעיל ד' ד') אבי בית לחם. וכן מצאנו בבועז שהיה נראה מדבריו היותו מהשופטים והוא מבית לחם וידמה ששמו האמיתי היה בועז. אך אמו קראתהו יעבץ כי ילדתו בעצב. ולזה גם כן זכר כי הבכורה היתה לראובן ולפי שחלל יצועי אביו בשכבו את בלהה פילגש אביו נתנה בכורתו ליוסף. ולזה גם כן זכר כי מפני שמעלו באלהי אבותיהם ראובן וגם וחצי שבט המנשי וזנו אחרי אלהי עמי הארץ אשר השמיד אלהים מפניהם העיר ה' רוח מלכי אשור והגלה אותם ויביאם לחלח ולחבור והרא ונהר גוזן. ולזה גם כן ספר כי יהודה הגלו לבבל במעלם. ולזה גם כן זכר שכבר מת שאול במעלו אשר מעל בה' ולא נשאר מזרעו כי אם בן אחד ליהונתן והסב ה' המלוכה לדוד אשר היה כלבבו בכל מעשיו רק בדבר אוריה החתי. ולזה גם כן ספר הצלחת דוד ברבוי הבנים. התועלת השלישי הוא להודיע כי הממשלה היתה לדוד מלידה ומבטן ומהריון כי אבותיו היו נשיאים. ר''ל כי נחשון בן עמינדב היה נשיא בני יהודה בימי משה והדור השני לנחשון היה בועז מן השופטי' את ישראל והרביעי לבועז היה דוד. התועלת הרביעי הוא להודיע שהוא ראוי שיקים האדם זכר לאחיו כשמת בלא בנים. ולזה ספר כי אף על פי שלא יבם שלה בן יהודה תמר אשת ער אחיו שמת בלא בנים. הנה קרא שם הבן הראשון שיולד לו ער להשאיר זכר לאחיו. ולזה אמר בני שלה בן יהודה ער וגו' (ד' כ''א). התועלת החמישי הוא להודיע איך הפליג ה' יתברך להשגיח בישראל להשיבם לאמונתו לפי מה שאפשר. וזה כי עם הפליגם לחטוא שמע זעקת בני ראובן וגד וחצי שבט מנשה בעת אשר זעקו אליו במלחמה ויתן עם רב מאד בידם עד שהיה נפש אדם מהשבי מאה אלף (ה' כ''א) כי זה היה ממה שהיה ראוי שיישירם לעבוד את ה' לבדו והם לא לקחו מוסר ובמעט זמן אחר זה גלו מארצם. התועלת הששי הוא לפרסם כי רוב הכהנים הגדולים היו מפנחס ולזה נתן לו ה' יתברך ברית כהנת עולם ופרסם עם זה כי עשרה דורות מזרעו פרנסו כל זמן בית ראשון אשר היו ממלכי יהודה יותר מחמשה עשר דורות וממלכי ישראל היו מעת רחבעם עד עת חזקיה כמו שמונה עשר דורות. ולזה זכר כי עזריה כהן בבית אשר בנה שלמה ויהוצדק שהיה הדור העשירי אחריו הלך בהגלות ה' את יהודה וירושלים ביד נבוכדנצר. התועלת השביעי הוא להודיע כי כשיהיו שלשה יחד הנה ראוי שיהיה הגדול באמצע והקטן מהם בשמאלו והגדול מהקטן בימינו כי הימין יותר נכבד מהשמאל והאמצע הוא היותר נכבד מהם. וזה מבואר בדברים הטבעים כי הלב הוא באמצע והוא היותר נכבד עם שבזה תועלת שהוא קרוב להם יחד להשפיע להם מהודו ולהדריכם ולהנהיגם והם קרובים לו יחד לשמשו. ולזה ספר כי הימן המשורר אשר מבני הקהתי שהם היותר נכבדים היה באמצע ומימינו היה המשורר אשר מבני גרשון שהיה אחריו בגדולה ומשמאלו היה המשורר אשר מבני מררי שהיה למטה מכלם. התועלת השמיני הוא להודיע כי מעבודת הלוים היה לשרת בשם ה' בשיר כי זה נרמז בתורה ולא נתבאר בה באור רחב. אך בזה הספר ובספר עזרא נתבאר זה תכלית הבאור וזה משלמות התקיעה בחצוצרות שהיו תוקעים הכהנים ונתבאר פה כי עבודת הלוים היתה לשמור משמרת שיהיו שוערים לפתוח הדלתות ביום ולסגרם בלילה ולשמור כל הלילה בארבע רוחות ביהמ''ק ולא ישכבו בלילה השומרים והיו שומרים מבואות הבית שלא יכנסו שם רק הראוים והיו גם כן שומרים כל כלי העבודה וכלי הקדשים וכל כלי הקדש וזאת היא השמירה שרמז אליה בפרשת בהר סיני. התועלת התשיעי הוא להודיע כי בשכר עבודתם נתנו לכהנים וללוים עריהם. ולזה סמך בזה המקום לעבודת הלוים והכהנים זכרון הערים שנתנו ישראל לכהנים וללוים. התועלת העשירי הוא להודיע איך השגיח ה' יתברך בשבט בנימין להרבותם אחרי כלות השבט ההוא בימי פילגש בגבעה שלא נשארו מהם כי אם שש מאות איש מבלי נשים וטף ולא ארכו הימים שהיו מספרם בימי דוד קרוב לששים אלף. התועלת האחד עשר הוא להודיע כי אין ראוי לעשוק לאנשים ואף כי יד העושקים תקיפה. ולזה ספר מה שקרה מהרע לבני אפרים ברדתם לקחת את מקנה אנשי גת מפני היותם בוטחים על עוצם גדולת יוסף שהיה שליט על הארץ וספר כי כבר הרגום אנשי גת. התועלת השנים עשר הוא להודיע שיש שם אות וסימן מעת לקיחת האשה בטוב ההצלחה או בהפכה. ולזה אמרו ז''ל (חולין צ''ה ע''ב) בית תינוק ואשה אף על פי שאין נחש יש סימן וזה דומה קצת למה שאמר נחשתי ויברכני ה' בגללך (בראשית ל' כ''ז). ויברך ה' את בית המצרי בגלל יוסף (שם ל''ט ה'). ולזה זכר כי אפרים בא אל אשתו סביב הריגת בניו וילדה לו בן וקרא שמו בריעה כי ברעה היתה בביתו. התועלת השלשה עשר הוא להודיע כי ה' יתברך ישגיח בנרדפים. ולזה זכר שקצת בני אהוד הוציאו אחיהם מגבע והגלו אותם אל מנחת וקצת הגולי' הצליחו מאד בבנים (לעיל ח') ובנו עירו' והצליחו במלחמה להבריח את יושבי גת. התועלת הארבעה עשר הוא להודיע כי ה' יתברך לא יקפח שכר כל בריה ובריה. ולזה ספר כי אף על פי שבחטאי שאול חיוב הכליון לבניו כדי שתסוב המלוכה לדוד הנה השגיח ה' יתברך ביהונתן לטוב לבבו עם האלהים ועם אנשי' והשגיח במפיבשת בנו להשאיר ממנו זרע עד שמזרעו היה אחד והוא אולם שהרבה בנים ובני בנים מאה וחמשים. התועלת החמשה עשר הוא מה שלמדנו מפני מה שסמך לגלות בבל וכרון השבי' לירושלים והשתדלות' בעבודת בית האלהים בשלום שבפנים שלא היתה הכונה בהגלותם כי אם להיישירם לעבודת ה' יתברך כי רוב השומן הביאם אל הבעיטה. ולזה תמצא כי בתחלת בית שני נעתק ענין יהודה ובנימין אל הטוב בתכלית מה שאפשר וזה מבואר מאד בספר עזרא. התועלת הששה עשר הוא להודיע שהוא ראוי לגמול חסד לאשר גמלוהו היו חיים או מתים. ולזה ספר כי אחרי מות שאול כששמעו אנשי יבש גלעד את אשר עשו הפלשתים לשאול קמו כל איש חיל ונשאו את גופת שאול ואת גופות בניו ויביאום יבשה ויקברו את עצמותיהם תחת האלה ונצטערו צער גדול על מיתתו עד שכבר הפליגו בזה וצמו שבעת ימים");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}