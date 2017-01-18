package text2json;

import com.google.gson.stream.JsonReader;
import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MesilatYesharimParser;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by omishali on 20/12/2016.
 */
public class MesilatYesharimParserTest {
    @BeforeClass
    public static void setup() throws IOException {
        MesilatYesharimParser parser = new MesilatYesharimParser();
        BufferedReader reader = TestUtils.getText("mesilatyesharim.txt");
        parser.parse(reader, "json/mesilatyesharim.json");
    }

    @Test
    //test the correctness with sampling a few values
    public void test() throws IOException {
        JsonReader jsonReader = null;//TestUtils.getJson("mesilatyesharim.json");
        //JsonReader jsonReader = new JsonReader(TestUtils.getFileReader("./parser.mesilatYesharim.json"));
        jsonReader.beginObject();
        assertEquals(jsonReader.nextName(), "subjects");
        jsonReader.beginArray();
        //jsonReader.beginObject();
        int perekNum = 0;
        while (jsonReader.hasNext()){
            jsonReader.beginObject();
            assertEquals(jsonReader.nextName(), "uri");
            assertEquals(jsonReader.nextString(), "mesilatyesharim-" + perekNum);
            assertEquals(jsonReader.nextName(), "title");
            testPerekTitle(jsonReader, perekNum);
            assertEquals(jsonReader.nextName(), "sefer");
            assertEquals(jsonReader.nextString(), "mesilatyesharim");
            assertEquals(jsonReader.nextName(), "text");
            testPerekText(jsonReader, perekNum);
            jsonReader.endObject();
            perekNum++;
        }
        jsonReader.endArray();
        jsonReader.endObject();

        assertEquals(perekNum, 28);
    }

    /***
     * sampled texts tests
     * @param jsonReader
     * @param perekNum
     */
    private void testPerekText(JsonReader jsonReader, int perekNum) throws IOException {
        String text = jsonReader.nextString();
        if(perekNum == 0){
            assertTrue(text.startsWith("אמר המחבר: החיבור הזה לא חברתיו ללמד לבני האדם את אשר לא ידעו, אלא להזכירם את הידוע להם כבר ומפורסם אצלם פירסום גדול."));
            assertTrue(text.contains("שמירת כל המצות: כמשמעו, דהינו: שמירת כל המצות כלן בכל דקדוקיהן ותנאיהן."));
            assertTrue(text.endsWith("הורני ה' דרכך אהלך באמתך יחד לבבי ליראה שמך(תהלים פו, יא), אמן כן יהי רצון. "));
        }
        else if(perekNum == 4){
            assertTrue(text.startsWith("הנה מה שמביא את האדם על דרך כלל אל הזהירות, הוא לימוד התורה. והוא מה שאמר רבי פינחס בתחלת הברייתא, תורה מביאה לידי זהירות (עבודה זרה כ, ב)."));
            assertTrue(text.contains("עוד אמרו (יבמות קכא, ב; בבא קמא נ, א) וסביביו נשערה מאד (תהילים נ, ג), מלמד שהקדוש ברוך הוא מדקדק עם חסידיו כחוט השערה."));
            assertTrue(text.endsWith("הן כל אלה ההשקפות שישקיף עליהן האדם ויקנה בם מדת הזהירות ודאי, אם בעל נפש הוא. "));
        }
        else if(perekNum == 20){
            assertTrue(text.startsWith("מה שצריך לבאר עתה הוא משקל החסידות הזה, והוא ענין עקרי מאד מאד,"));
            assertTrue(text.contains("אתה דובר על ישמעאל (ירמיה מ), ומה גרם? גרם שמת הוא ונפזרו ישראל וכבה גחלתם הנשארה,"));
            assertTrue(text.endsWith("וזה לנו לעינים לראות אי זה דרך ישכון אור באמת ובאמונה לעשות הישר בעיני ה'. "));
        }
        else if(perekNum == 27){
            assertTrue(text.startsWith("אמר המחבר הפעם אודה את ה' אשירה ואזמרה, אשר עד הנה עזרוני רחמיו להוציא לאור ספרי זה מסלת ישרים אשר להתלמד בו חברתיו, ו"));
            assertTrue(text.contains("עקב בן כבוד רבי אברהם בשן נטרה רחמנא ופרקה, אשר נכנס בעביה של קורה לזכותני בדבר הזה"));
            assertTrue(text.endsWith("משה בן כבוד רבי יעקב חי לוצאטי ס\"ט."));
        }
    }

    /**
     * sampled titles tests
     * @param jsonReader
     * @param perekNum
     * @throws IOException
     */
    private void testPerekTitle(JsonReader jsonReader, int perekNum) throws IOException {
        if(perekNum == 0)
            assertEquals(jsonReader.nextString(), "הקדמת הרב המחבר זצ\"ל");
        else if (perekNum == 6)
            assertEquals(jsonReader.nextString(), "בביאור מדת הזריזות");
        else if (perekNum == 24)
            assertEquals(jsonReader.nextString(), "בביאור יראת החטא");
        else if (perekNum == 27)
            assertEquals(jsonReader.nextString(), "חתימה");
        else
            jsonReader.nextString();
    }
}
