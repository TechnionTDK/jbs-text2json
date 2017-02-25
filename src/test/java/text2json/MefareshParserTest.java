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
            //======== SEFER ==============
            assertEquals(jsonReader.nextName(), "jbo:sefer");
            testSefer(jsonReader, bookNum);
            //======== TITLES ==============
            assertEquals(jsonReader.nextName(), "titles");
            testTitels(jsonReader);
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
}