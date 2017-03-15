package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TanachParser;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.util.Map;


/**
 * Created by USER on 10-Jan-17.
 */
public class TanachParserTest {
    private static final int NUM_OF_LAST_BOOK = 39;
    private static final int NUM_OF_FIRST_BOOK = 1;
    private static final int NUM_OF_BOOKS = NUM_OF_LAST_BOOK - NUM_OF_FIRST_BOOK + 1;
    static SubjectsJson[] json = new SubjectsJson[NUM_OF_BOOKS + 1];
    static SubjectsJson[] packagesJson = new SubjectsJson[NUM_OF_BOOKS + 1];


    @BeforeClass
    public static void setup() throws Exception {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            TanachParser parser = new TanachParser();
            BufferedReader reader = getText("/tanach/tanach-" + bookNum + ".txt");
            parser.parse(reader, "json/tanach/tanach-" + bookNum + ".json");
            json[bookNum] = getJson("json/tanach/tanach-" + bookNum + ".json");
            packagesJson[bookNum] = getJson("json/tanach/tanach-" + bookNum + "-packages.json");

        }
    }

    @Test
    public void testTotalNumberOfObjects() {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            assertNotNull(json[bookNum]);
            assertEquals(numOfPsukimInBook(bookNum), json[bookNum].subjects.size());
        }
    }

    @Test
    public void testSpecificObjects() {
        Map<String, String> object;
        //1-1-1
        object = json[1].getObject(0);
        assertEquals("jbr:tanach-1-1-1", object.get(URI));
        String textNikud = "בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ";
        String text = stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("בראשית א א", object.get(RDFS_LABEL));
        assertEquals("jbr:tanach-1", object.get(JBO_SEFER));
        assertEquals("1", object.get(JBO_POSITION_IN_PARASHA));
        assertTitlesArray(object, "[{title=בראשית א א}, {title=בראשית פרק א פסוק א}]");

        //1-50-26
        object = json[1].getObject(json[1].subjects.size()-1);
        assertEquals("jbr:tanach-1-50-26", object.get(URI));
        textNikud = "וַיָּמָת יוֹסֵף בֶּן מֵאָה וָעֶשֶׂר שָׁנִים וַיַּחַנְטוּ אֹתוֹ וַיִּישֶׂם בָּאָרוֹן בְּמִצְרָיִם";
        text = TestUtils.stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("בראשית נ כו", object.get(RDFS_LABEL));
        assertEquals("jbr:tanach-1", object.get(JBO_SEFER));
        assertEquals("85", object.get(JBO_POSITION_IN_PARASHA));
        assertTitlesArray(object, "[{title=בראשית נ כו}, {title=בראשית פרק נ פסוק כו}]");

        //5-4-10
        object = json[5].getObject(121);
        assertEquals("jbr:tanach-5-4-10", object.get(URI));
        textNikud = "יוֹם אֲשֶׁר עָמַדְתָּ לִפְנֵי יְהֹוָה אֱלֹהֶיךָ בְּחֹרֵב בֶּאֱמֹר יְהֹוָה אֵלַי הַקְהֶל לִי אֶת הָעָם וְאַשְׁמִעֵם אֶת דְּבָרָי אֲשֶׁר יִלְמְדוּן לְיִרְאָה אֹתִי כָּל הַיָּמִים אֲשֶׁר הֵם חַיִּים עַל הָאֲדָמָה וְאֶת בְּנֵיהֶם יְלַמֵּדוּן";
        text = stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("דברים ד י", object.get(RDFS_LABEL));
        assertEquals("jbr:tanach-5", object.get(JBO_SEFER));
        assertEquals("17", object.get(JBO_POSITION_IN_PARASHA));
        assertTitlesArray(object, "[{title=דברים ד י}, {title=דברים פרק ד פסוק י}]");

        //6-2-17
        object = json[6].getObject(34);
        assertEquals("jbr:tanach-6-2-17", object.get(URI));
        textNikud = "וַיֹּאמְרוּ אֵלֶיהָ הָאֲנָשִׁים נְקִיִּם אֲנַחְנוּ מִשְּׁבֻעָתֵךְ הַזֶּה אֲשֶׁר הִשְׁבַּעְתָּנוּ";
        text = stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("יהושע ב יז", object.get(RDFS_LABEL));
        assertEquals("jbr:tanach-6", object.get(JBO_SEFER));
        assertNull(object.get(JBO_POSITION_IN_PARASHA));
        assertTitlesArray(object, "[{title=יהושע ב יז}, {title=יהושע פרק ב פסוק יז}]");
    }

    @Test
    public void testPackagesTriples() {
        // test the number of packages objects in sefer Bereshit
        int expected = 1 + 12 + 50; // sefer + parashot + perakim
        assertEquals(expected, packagesJson[1].subjects.size()); // note that element in position 0 is null

        // test the number of packages objects in sefer Devarim
        expected = 1 + 11 + 34; // sefer + parashot + perakim
        assertEquals(expected, packagesJson[5].subjects.size());

        // test the number of packages objects in sefer Divrey Hayamim B
        expected = 1 + 0 + 36; // sefer + parashot + perakim
        assertEquals(expected, packagesJson[39].subjects.size());
    }



    private void assertTitlesArray(Map<String, String> object, String expected_titles) {
        String object_str = object.toString();
        int begin_idx = object_str.indexOf("[{");
        int end_idx = object_str.indexOf("]}")+1;
        String actual_titles = object_str.substring(begin_idx, end_idx);
        assertEquals(expected_titles, actual_titles);
    }


    private int numOfPsukimInBook(int bookNum) {
        int psukimForBook[] = {0, 1533, 1210, 859, 1288, 956, 658, 618, 811, 695, 817,
                719, 1291, 1364, 1273, 197, 73, 146, 21, 48, 105, 47, 56, 53, 38, 211,
                55, 2527, 915, 1070, 117, 85, 154, 222, 167, 357, 280, 405, 943, 822};
        return psukimForBook[bookNum];
    }
}
