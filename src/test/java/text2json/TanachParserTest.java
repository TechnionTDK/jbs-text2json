package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.JbsUtils;
import text2json.SubjectsJson;
import text2json.parsers.TanachParser;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.JBO_TEXT;
import static text2json.JbsOntology.JBO_TEXT_NIKUD;
import static text2json.JbsOntology.JBO_WITHIN;
import static text2json.TestUtils.*;
import static text2json.TestUtils.assertLabelProperty;
import static text2json.TestUtils.assertPositionProperty;

/**
 * Created by orel on 25/07/18.
 */
public class TanachParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new TanachParser() , "tanach");
        packageJson = getJson("json/tanach/tanach-packages.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(23206, json.subjects.size()); //total number of psukim
        assertEquals(54 + 929, packageJson.subjects.size()); //total number of parashot and perakim
    }


    private void testObject(Map<String, String> o, int bookNum,int perekNum, int pasukNum,
                            int position, String bookName, String pasukNikudString) {
        String UriNum = "-" + bookNum + "-" + perekNum + "-" + pasukNum;
        String positionStr = Integer.toString(position);
        String bible = "tanach";
        assertBookProperty(o, bible + "-" + bookName);
        assertTextUriProperty( o , bible + UriNum);
        assertPositionProperty(o, positionStr);
        String pasukString = JbsUtils.removeNikkud(pasukNikudString);
        assertTrue(o.get(JBO_TEXT).equals(pasukString));
        assertTrue(o.get(JBO_TEXT_NIKUD).equals(pasukNikudString));
        String hebBookName = JbsUtils.SEFARIM_TANACH_HE[bookNum-1] + " ";
        String label = hebBookName + JbsUtils.numberToHebrew(perekNum) + " " + JbsUtils.numberToHebrew(pasukNum);
        assertLabelProperty( o ,label);
    }

    private void testPackageObject(Map<String, String> o, String label, String bookName,int position) {
        String positionStr = Integer.toString(position);
        assertBookProperty(o, "tanach-" + bookName);
        assertPositionProperty(o, positionStr);
        assertLabelProperty( o ,label);
    }


    @Test
    public void testSpecificObjects() {
        Map<String, String> o;
        String pasukStr;

        // test first object
        o = json.getObject(0);
        pasukStr = "בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ׃";
        testObject(o, 1, 1, 1, 1,
                "bereshit", pasukStr);

        o = json.getObject(1556);
        pasukStr = "וַתַּהַר הָאִשָּׁה וַתֵּלֶד בֵּן וַתֵּרֶא אֹתוֹ כִּי־טוֹב הוּא וַתִּצְפְּנֵהוּ שְׁלֹשָׁה יְרָחִים׃";
        testObject(o, 2, 2, 2, 1557,
                "shemot", pasukStr);

        o = json.getObject(10295);
        pasukStr = "וְאָמַרְתָּ אֵלָיו הִשָּׁמֵר וְהַשְׁקֵט אַל־תִּירָא וּלְבָבְךָ אַל־יֵרַךְ מִשְּׁנֵי זַנְבוֹת הָאוּדִים הָעֲשֵׁנִים הָאֵלֶּה בָּחֳרִי־אַף רְצִין וַאֲרָם וּבֶן־רְמַלְיָהוּ׃";
        testObject(o, 12, 7, 4, 10296,
                "yeshaaya", pasukStr);

        o = json.getObject(18615);
        pasukStr = "וַיֹּאמֶר אֵלֶיהָ כְּדַבֵּר אַחַת הַנְּבָלוֹת תְּדַבֵּרִי גַּם אֶת־הַטּוֹב נְקַבֵּל מֵאֵת הָאֱלֹהִים וְאֶת־הָרָע לֹא נְקַבֵּל בְּכָל־זֹאת לֹא־חָטָא אִיּוֹב בִּשְׂפָתָיו׃";
        testObject(o, 29, 2, 10, 18616,
                "iyov", pasukStr);
        Object s = json.getComplexObject(18615).get(JBO_WITHIN);
        ArrayList<String> sections = (ArrayList<String>) s;
        assertEquals(sections.get(0), "jbr:section-tanach-29-2");
        assertEquals(sections.size(), 1);


        o = json.getObject(1086);
        pasukStr = "וְיִשְׂרָאֵל אָהַב אֶת־יוֹסֵף מִכָּל־בָּנָיו כִּי־בֶן־זְקֻנִים הוּא לוֹ וְעָשָׂה לוֹ כְּתֹנֶת פַּסִּים׃";
        testObject(o, 1, 37, 3, 1087,
                "bereshit", pasukStr);
        s = json.getComplexObject(1086).get(JBO_WITHIN);
        sections = (ArrayList<String>) s;
        assertEquals(sections.get(0), "jbr:section-tanach-1-37");
        assertEquals(sections.get(1), "jbr:section-tanach-parasha-9");
        assertEquals(sections.size(), 2);

        //the last object (should be object index 23205)
        o = json.getObject(json.subjects.size()-1);
        pasukStr = "כֹּה־אָמַר כּוֹרֶשׁ מֶלֶךְ פָּרַס כָּל־מַמְלְכוֹת הָאָרֶץ נָתַן לִי יְהוָה אֱלֹהֵי הַשָּׁמַיִם וְהוּא־פָקַד עָלַי לִבְנוֹת־לוֹ בַיִת בִּירוּשָׁלִַם אֲשֶׁר בִּיהוּדָה מִי־בָכֶם מִכָּל־עַמּוֹ יְהוָה אֱלֹהָיו עִמּוֹ וְיָעַל׃";
        testObject(o, 39, 36, 23, 23206,
                "divreyhayamimB", pasukStr);

        //testing package objects
        o = packageJson.getObject("jbr:section-tanach-parasha-1");
        testPackageObject(o, "פרשת בראשית", "bereshit", 1);

        o = packageJson.getObject("jbr:section-tanach-1-1");
        testPackageObject(o, "בראשית פרק א", "bereshit", 1);

        o = packageJson.getObject("jbr:section-tanach-1-10");
        testPackageObject(o, "בראשית פרק י", "bereshit", 10);

        o = packageJson.getObject("jbr:section-tanach-13-50");
        testPackageObject(o, "ירמיה פרק נ", "yirmiya", 450);

        o = packageJson.getObject("jbr:section-tanach-parasha-32");
        testPackageObject(o, "פרשת בהר", "vayikra", 32);

    }
}