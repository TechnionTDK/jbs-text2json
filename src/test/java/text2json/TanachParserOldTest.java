package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TanachParserOld;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;


/**
 * Created by USER on 10-Jan-17.
 */
public class TanachParserOldTest {
    private static final int NUM_OF_LAST_BOOK = 39;
    private static final int NUM_OF_FIRST_BOOK = 1;
    private static final int NUM_OF_BOOKS = NUM_OF_LAST_BOOK - NUM_OF_FIRST_BOOK + 1;
    static SubjectsJson[] json = new SubjectsJson[NUM_OF_BOOKS + 1];
    static SubjectsJson[] packagesJson = new SubjectsJson[NUM_OF_BOOKS + 1];

    @BeforeClass
    public static void setup() throws Exception {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            TanachParserOld parser = new TanachParserOld();
            BufferedReader reader = getText("tanach-old/tanach-" + bookNum + ".txt");
            createOutputFolderIfNotExists("tanach-old");
            parser.parse(reader, "json/tanach-old/tanach-" + bookNum + ".json");
            json[bookNum] = getJson("json/tanach-old/tanach-" + bookNum + ".json");
            packagesJson[bookNum] = getJson("json/tanach-old/tanach-" + bookNum + "-packages.json");
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
    public void testTotalNumberOfObjectsInPackage() {
        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            assertNotNull(packagesJson[bookNum]);
            assertEquals(numOfObjectsInPackage(bookNum), packagesJson[bookNum].subjects.size());
        }
    }


    @Test
    public void testSpecificObjects() {
        Map<String, Object> object;
        //1-1-1
        object = json[1].getComplexObject(0);
        assertEquals(JBR_TEXT + "tanach-1-1-1", object.get(URI));
        String textNikud = "בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ";
        String text = stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("בראשית א א", object.get(RDFS_LABEL));
        assertEquals("1", object.get(JBO_POSITION));
        String book = (String) object.get(JBO_BOOK);
        assertEquals(book, JBR_BOOK_TANACH + "bereshit");

        //2-1-1
        object = json[2].getComplexObject(0);
        assertEquals(JBR_TEXT + "tanach-2-1-1", object.get(URI));
        assertEquals("1534", object.get(JBO_POSITION));
        book = (String) object.get(JBO_BOOK);
        assertEquals(book, JBR_BOOK_TANACH + "shemot");

        //3-1-1
        object = json[3].getComplexObject(0);
        assertEquals(JBR_TEXT + "tanach-3-1-1", object.get(URI));
        assertEquals("2744", object.get(JBO_POSITION));
        book = (String) object.get(JBO_BOOK);
        assertEquals(book, JBR_BOOK_TANACH + "vayikra");

        //1-50-26
        object = json[1].getComplexObject(json[1].subjects.size()-1);
        assertEquals(JBR_TEXT + "tanach-1-50-26", object.get(URI));
        textNikud = "וַיָּמָת יוֹסֵף בֶּן מֵאָה וָעֶשֶׂר שָׁנִים וַיַּחַנְטוּ אֹתוֹ וַיִּישֶׂם בָּאָרוֹן בְּמִצְרָיִם";
        text = TestUtils.stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("בראשית נ כו", object.get(RDFS_LABEL));

        //5-4-10
        object = json[5].getComplexObject(121);
        assertEquals(JBR_TEXT + "tanach-5-4-10", object.get(URI));
        textNikud = "יוֹם אֲשֶׁר עָמַדְתָּ לִפְנֵי יְהֹוָה אֱלֹהֶיךָ בְּחֹרֵב בֶּאֱמֹר יְהֹוָה אֵלַי הַקְהֶל לִי אֶת הָעָם וְאַשְׁמִעֵם אֶת דְּבָרָי אֲשֶׁר יִלְמְדוּן לְיִרְאָה אֹתִי כָּל הַיָּמִים אֲשֶׁר הֵם חַיִּים עַל הָאֲדָמָה וְאֶת בְּנֵיהֶם יְלַמֵּדוּן";
        text = stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("דברים ד י", object.get(RDFS_LABEL));
        book = (String) object.get(JBO_BOOK);
        assertEquals(book, JBR_BOOK_TANACH + "devarim");

        //6-2-17
        object = json[6].getComplexObject(34);
        assertEquals(JBR_TEXT + "tanach-6-2-17", object.get(URI));
        textNikud = "וַיֹּאמְרוּ אֵלֶיהָ הָאֲנָשִׁים נְקִיִּם אֲנַחְנוּ מִשְּׁבֻעָתֵךְ הַזֶּה אֲשֶׁר הִשְׁבַּעְתָּנוּ";
        text = stripVowels(textNikud);
        assertEquals(text, object.get(JBO_TEXT));
        assertEquals(textNikud, object.get(JBO_TEXT_NIKUD));
        assertEquals("יהושע ב יז", object.get(RDFS_LABEL));
        book = (String) object.get(JBO_BOOK);
        assertEquals(book, JBR_BOOK_TANACH + "yehoshua");
    }

    @Test
    public void testPackagesTriples() {
        // test the number of packages objects in sefer Bereshit
        int expected = 12 + 50; // parashot + perakim, sefer is not added
        assertEquals(expected, packagesJson[1].subjects.size()); // note that element in position 0 is null

        // test the number of packages objects in sefer Devarim
        expected = 11 + 34; // sefer + parashot + perakim
        assertEquals(expected, packagesJson[5].subjects.size());

        // test the number of packages objects in sefer Divrey Hayamim B
        expected = 0 + 36; // sefer + parashot + perakim
        assertEquals(expected, packagesJson[39].subjects.size());
    }

    private int numOfPsukimInBook(int bookNum) {
        int psukimForBook[] = {0, 1533, 1210, 859, 1288, 956, 658, 618, 811, 695, 817,
                719, 1291, 1364, 1273, 197, 73, 146, 21, 48, 105, 47, 56, 53, 38, 211,
                55, 2527, 915, 1070, 117, 85, 154, 222, 167, 357, 280, 405, 943, 822};
        return psukimForBook[bookNum];
    }

    private int numOfObjectsInPackage(int bookNum) {
        // note that the main "sefer" object is removed from packages
        int prakimForBook[] = {0, 50, 40, 27, 36, 34, 24, 21, 31, 24, 22,
                25, 66, 52, 48, 14, 4, 9, 1, 4, 7, 3, 3, 3, 2, 14,
                3, 150, 31, 42, 8, 4, 5, 12, 10, 12, 10, 13, 29, 36};
        int parashotForBook[] = {0, 12, 11, 10, 10, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        return parashotForBook[bookNum] + prakimForBook[bookNum];
    }
}
