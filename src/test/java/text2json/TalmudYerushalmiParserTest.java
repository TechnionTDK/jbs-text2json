package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TalmudYerushalmiParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by USER on 25-Jan-17.
 */
public class TalmudYerushalmiParserTest {
    private static final int NUM_OF_FIRST_MASECHET = 1;
    private static final int NUM_OF_LAST_MASECHET = 39;
    static SubjectsJson json[] = new SubjectsJson[NUM_OF_LAST_MASECHET + 1];
    static SubjectsJson packageJson[] = new SubjectsJson[NUM_OF_LAST_MASECHET + 1];


    @BeforeClass
    public static void setup() throws Exception {
        for (int masechetNum = NUM_OF_FIRST_MASECHET; masechetNum <= NUM_OF_LAST_MASECHET; masechetNum++) {
            TalmudYerushalmiParser parser = new TalmudYerushalmiParser();
            BufferedReader reader = getText("talmudyerushalmi/talmudyerushalmi-" + masechetNum + ".txt");
            createOutputFolderIfNotExists("talmudyerushalmi");
            parser.parse(reader, "json/talmudyerushalmi/talmudyerushalmi-" + masechetNum + ".json");
            json[masechetNum] = getJson("json/talmudyerushalmi/talmudyerushalmi-" + masechetNum + ".json");
            packageJson[masechetNum] = getJson("json/talmudyerushalmi/talmudyerushalmi-" + masechetNum + "-packages.json");
        }
    }

    @Test
    public void testNumOfAmudimPerMasechet() {
        for (int masechetNum = NUM_OF_FIRST_MASECHET; masechetNum <= NUM_OF_LAST_MASECHET; masechetNum++) {
            assertNotNull(json[masechetNum]);
//            System.out.println(masechetNum + ": "+json[masechetNum].subjects.size());
//            System.out.print(","+json[masechetNum].subjects.size());
            assertEquals(numOfObjectsForMasechet(masechetNum),json[masechetNum].subjects.size());
        }
    }


    @Test
    public void testSpecificObjects() {
        Map<String, String> object;

        //masechet 1
        object = json[1].getObject(0);
        assertTextUriProperty(object, "yerushalmi-1-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי ברכות א א");
        assertBookProperty(object,"yerushalmi");

        object = json[1].getObject(87);
        assertTextUriProperty(object, "yerushalmi-1-44-2");
        assertLabelProperty( object ,"תלמוד ירושלמי ברכות מד ב");
        assertPositionProperty(object ,"88");
        assertBookProperty(object,"yerushalmi");

        object = json[1].getObject(134);
        assertTextUriProperty(object, "yerushalmi-1-68-1");
        assertLabelProperty( object ,"תלמוד ירושלמי ברכות סח א");
        assertPositionProperty(object ,"135");
        assertBookProperty(object,"yerushalmi");

        //masechet 2
        object = json[2].getObject(0);
        assertTextUriProperty(object, "yerushalmi-2-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי פאה א א");
        assertBookProperty(object,"yerushalmi");

        object = json[2].getObject(39);
        assertTextUriProperty(object, "yerushalmi-2-20-2");
        assertLabelProperty( object ,"תלמוד ירושלמי פאה כ ב");
        assertPositionProperty(object ,"40");
        assertBookProperty(object,"yerushalmi");

        object = json[2].getObject(73);
        assertTextUriProperty(object, "yerushalmi-2-37-2");
        assertLabelProperty( object ,"תלמוד ירושלמי פאה לז ב");
        assertPositionProperty(object ,"74");
        assertBookProperty(object,"yerushalmi");

        //masechet 3
        object = json[3].getObject(0);
        assertTextUriProperty(object, "yerushalmi-3-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי דמאי א א");
        assertBookProperty(object,"yerushalmi");

        object = json[3].getObject(30);
        assertTextUriProperty(object, "yerushalmi-3-16-1");
        assertLabelProperty( object ,"תלמוד ירושלמי דמאי טז א");
        assertPositionProperty(object ,"31");
        assertBookProperty(object,"yerushalmi");

        object = json[3].getObject(66);
        assertTextUriProperty(object, "yerushalmi-3-34-1");
        assertLabelProperty( object ,"תלמוד ירושלמי דמאי לד א");
        assertPositionProperty(object ,"67");
        assertBookProperty(object,"yerushalmi");

        //masechet 4
        object = json[4].getObject(0);
        assertTextUriProperty(object, "yerushalmi-4-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי כלאים א א");
        assertBookProperty(object,"yerushalmi");

        object = json[4].getObject(41);
        assertTextUriProperty(object, "yerushalmi-4-21-2");
        assertLabelProperty( object ,"תלמוד ירושלמי כלאים כא ב");
        assertPositionProperty(object ,"42");
        assertBookProperty(object,"yerushalmi");

        object = json[4].getObject(87);
        assertTextUriProperty(object, "yerushalmi-4-44-2");
        assertLabelProperty( object ,"תלמוד ירושלמי כלאים מד ב");
        assertPositionProperty(object ,"88");
        assertBookProperty(object,"yerushalmi");

        //masechet 5
        object = json[5].getObject(0);
        assertTextUriProperty(object, "yerushalmi-5-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי שביעית א א");
        assertBookProperty(object,"yerushalmi");

        object = json[5].getObject(29);
        assertTextUriProperty(object, "yerushalmi-5-15-2");
        assertLabelProperty( object ,"תלמוד ירושלמי שביעית טו ב");
        assertPositionProperty(object ,"30");
        assertBookProperty(object,"yerushalmi");

        object = json[5].getObject(60);
        assertTextUriProperty(object, "yerushalmi-5-31-1");
        assertLabelProperty( object ,"תלמוד ירושלמי שביעית לא א");
        assertPositionProperty(object ,"61");
        assertBookProperty(object,"yerushalmi");

        //masechet 6
        object = json[6].getObject(0);
        assertTextUriProperty(object, "yerushalmi-6-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי תרומות א א");
        assertBookProperty(object,"yerushalmi");

        object = json[6].getObject(59);
        assertTextUriProperty(object, "yerushalmi-6-30-2");
        assertLabelProperty( object ,"תלמוד ירושלמי תרומות ל ב");
        assertPositionProperty(object ,"60");
        assertBookProperty(object,"yerushalmi");

        object = json[6].getObject(116);
        assertTextUriProperty(object, "yerushalmi-6-59-1");
        assertLabelProperty( object ,"תלמוד ירושלמי תרומות נט א");
        assertPositionProperty(object ,"117");
        assertBookProperty(object,"yerushalmi");

        //masechet 7
        object = json[7].getObject(0);
        assertTextUriProperty(object, "yerushalmi-7-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי מעשרות א א");
        assertBookProperty(object,"yerushalmi");

        object = json[7].getObject(26);
        assertTextUriProperty(object, "yerushalmi-7-14-1");
        assertLabelProperty( object ,"תלמוד ירושלמי מעשרות יד א");
        assertPositionProperty(object ,"27");
        assertBookProperty(object,"yerushalmi");

        object = json[7].getObject(51);
        assertTextUriProperty(object, "yerushalmi-7-26-2");
        assertLabelProperty( object ,"תלמוד ירושלמי מעשרות כו ב");
        assertPositionProperty(object ,"52");
        assertBookProperty(object,"yerushalmi");

        //masechet 8
        object = json[8].getObject(0);
        assertTextUriProperty(object, "yerushalmi-8-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי מעשר שני א א");
        assertBookProperty(object,"yerushalmi");

        object = json[8].getObject(33);
        assertTextUriProperty(object, "yerushalmi-8-17-2");
        assertLabelProperty( object ,"תלמוד ירושלמי מעשר שני יז ב");
        assertPositionProperty(object ,"34");
        assertBookProperty(object,"yerushalmi");

        object = json[8].getObject(65);
        assertTextUriProperty(object, "yerushalmi-8-33-2");
        assertLabelProperty( object ,"תלמוד ירושלמי מעשר שני לג ב");
        assertPositionProperty(object ,"66");
        assertBookProperty(object,"yerushalmi");

        //masechet 9
        object = json[9].getObject(0);
        assertTextUriProperty(object, "yerushalmi-9-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי חלה א א");
        assertBookProperty(object,"yerushalmi");

        object = json[9].getObject(24);
        assertTextUriProperty(object, "yerushalmi-9-13-1");
        assertLabelProperty( object ,"תלמוד ירושלמי חלה יג א");
        assertPositionProperty(object ,"25");
        assertBookProperty(object,"yerushalmi");

        object = json[9].getObject(55);
        assertTextUriProperty(object, "yerushalmi-9-28-2");
        assertLabelProperty( object ,"תלמוד ירושלמי חלה כח ב");
        assertPositionProperty(object ,"56");
        assertBookProperty(object,"yerushalmi");

        //masechet 10
        object = json[10].getObject(0);
        assertTextUriProperty(object, "yerushalmi-10-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי ערלה א א");
        assertBookProperty(object,"yerushalmi");

        object = json[10].getObject(19);
        assertTextUriProperty(object, "yerushalmi-10-10-2");
        assertLabelProperty( object ,"תלמוד ירושלמי ערלה י ב");
        assertPositionProperty(object ,"20");
        assertBookProperty(object,"yerushalmi");

        object = json[10].getObject(39);
        assertTextUriProperty(object, "yerushalmi-10-20-2");
        assertLabelProperty( object ,"תלמוד ירושלמי ערלה כ ב");
        assertPositionProperty(object ,"40");
        assertBookProperty(object,"yerushalmi");

        //masechet 11
        object = json[11].getObject(0);
        assertTextUriProperty(object, "yerushalmi-11-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי בכורים א א");
        assertBookProperty(object,"yerushalmi");

        object = json[11].getObject(11);
        assertTextUriProperty(object, "yerushalmi-11-6-2");
        assertLabelProperty( object ,"תלמוד ירושלמי בכורים ו ב");
        assertPositionProperty(object ,"12");
        assertBookProperty(object,"yerushalmi");

        object = json[11].getObject(24);
        assertTextUriProperty(object, "yerushalmi-11-13-1");
        assertLabelProperty( object ,"תלמוד ירושלמי בכורים יג א");
        assertPositionProperty(object ,"25");
        assertBookProperty(object,"yerushalmi");

        //masechet 12
        object = json[12].getObject(0);
        assertTextUriProperty(object, "yerushalmi-12-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי שבת א א");
        assertBookProperty(object,"yerushalmi");

        object = json[12].getObject(100);
        assertTextUriProperty(object, "yerushalmi-12-51-1");
        assertLabelProperty( object ,"תלמוד ירושלמי שבת נא א");
        assertPositionProperty(object ,"101");
        assertBookProperty(object,"yerushalmi");

        object = json[12].getObject(183);
        assertTextUriProperty(object, "yerushalmi-12-92-2");
        assertLabelProperty( object ,"תלמוד ירושלמי שבת צב ב");
        assertPositionProperty(object ,"184");
        assertBookProperty(object,"yerushalmi");

        //masechet 13
        object = json[13].getObject(0);
        assertTextUriProperty(object, "yerushalmi-13-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי עירובין א א");
        assertBookProperty(object,"yerushalmi");

        object = json[13].getObject(84);
        assertTextUriProperty(object, "yerushalmi-13-43-1");
        assertLabelProperty( object ,"תלמוד ירושלמי עירובין מג א");
        assertPositionProperty(object ,"85");
        assertBookProperty(object,"yerushalmi");

        object = json[13].getObject(129);
        assertTextUriProperty(object, "yerushalmi-13-65-2");
        assertLabelProperty( object ,"תלמוד ירושלמי עירובין סה ב");
        assertPositionProperty(object ,"130");
        assertBookProperty(object,"yerushalmi");

        //masechet 14
        object = json[14].getObject(0);
        assertTextUriProperty(object, "yerushalmi-14-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי פסחים א א");
        assertBookProperty(object,"yerushalmi");

        object = json[14].getObject(80);
        assertTextUriProperty(object, "yerushalmi-14-41-1");
        assertLabelProperty( object ,"תלמוד ירושלמי פסחים מא א");
        assertPositionProperty(object ,"81");
        assertBookProperty(object,"yerushalmi");

        object = json[14].getObject(141);
        assertTextUriProperty(object, "yerushalmi-14-71-2");
        assertLabelProperty( object ,"תלמוד ירושלמי פסחים עא ב");
        assertPositionProperty(object ,"142");
        assertBookProperty(object,"yerushalmi");

        //masechet 20
        object = json[20].getObject(0);
        assertTextUriProperty(object, "yerushalmi-20-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי תענית א א");
        assertBookProperty(object,"yerushalmi");

        object = json[20].getObject(30);
        assertTextUriProperty(object, "yerushalmi-20-16-1");
        assertLabelProperty( object ,"תלמוד ירושלמי תענית טז א");
        assertPositionProperty(object ,"31");
        assertBookProperty(object,"yerushalmi");

        object = json[20].getObject(51);
        assertTextUriProperty(object, "yerushalmi-20-26-2");
        assertLabelProperty( object ,"תלמוד ירושלמי תענית כו ב");
        assertPositionProperty(object ,"52");
        assertBookProperty(object,"yerushalmi");

        //masechet 26
        object = json[26].getObject(0);
        assertTextUriProperty(object, "yerushalmi-26-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי נדרים א א");
        assertBookProperty(object,"yerushalmi");

        object = json[26].getObject(45);
        assertTextUriProperty(object, "yerushalmi-26-23-2");
        assertLabelProperty( object ,"תלמוד ירושלמי נדרים כג ב");
        assertPositionProperty(object ,"46");
        assertBookProperty(object,"yerushalmi");

        object = json[26].getObject(78);
        assertTextUriProperty(object, "yerushalmi-26-40-1");
        assertLabelProperty( object ,"תלמוד ירושלמי נדרים מ א");
        assertPositionProperty(object ,"79");
        assertBookProperty(object,"yerushalmi");

        //masechet 30
        object = json[30].getObject(0);
        assertTextUriProperty(object, "yerushalmi-30-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי קידושין א א");
        assertBookProperty(object,"yerushalmi");

        object = json[30].getObject(50);
        assertTextUriProperty(object, "yerushalmi-30-26-1");
        assertLabelProperty( object ,"תלמוד ירושלמי קידושין כו א");
        assertPositionProperty(object ,"51");
        assertBookProperty(object,"yerushalmi");

        object = json[30].getObject(95);
        assertTextUriProperty(object, "yerushalmi-30-48-2");
        assertLabelProperty( object ,"תלמוד ירושלמי קידושין מח ב");
        assertPositionProperty(object ,"96");
        assertBookProperty(object,"yerushalmi");

        //masechet 33
        object = json[33].getObject(0);
        assertTextUriProperty(object, "yerushalmi-33-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי בבא בתרא א א");
        assertBookProperty(object,"yerushalmi");

        object = json[33].getObject(42);
        assertTextUriProperty(object, "yerushalmi-33-22-1");
        assertLabelProperty( object ,"תלמוד ירושלמי בבא בתרא כב א");
        assertPositionProperty(object ,"43");
        assertBookProperty(object,"yerushalmi");

        object = json[33].getObject(66);
        assertTextUriProperty(object, "yerushalmi-33-34-1");
        assertLabelProperty( object ,"תלמוד ירושלמי בבא בתרא לד א");
        assertPositionProperty(object ,"67");
        assertBookProperty(object,"yerushalmi");

        //masechet 35
        object = json[35].getObject(0);
        assertTextUriProperty(object, "yerushalmi-35-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי מכות א א");
        assertBookProperty(object,"yerushalmi");

        object = json[35].getObject(9);
        assertTextUriProperty(object, "yerushalmi-35-5-2");
        assertLabelProperty( object ,"תלמוד ירושלמי מכות ה ב");
        assertPositionProperty(object ,"10");
        assertBookProperty(object,"yerushalmi");

        object = json[35].getObject(17);
        assertTextUriProperty(object, "yerushalmi-35-9-2");
        assertLabelProperty( object ,"תלמוד ירושלמי מכות ט ב");
        assertPositionProperty(object ,"18");
        assertBookProperty(object,"yerushalmi");

        //masechet 39
        object = json[39].getObject(0);
        assertTextUriProperty(object, "yerushalmi-39-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תלמוד ירושלמי נידה א א");
        assertBookProperty(object,"yerushalmi");

        object = json[39].getObject(13);
        assertTextUriProperty(object, "yerushalmi-39-7-2");
        assertLabelProperty( object ,"תלמוד ירושלמי נידה ז ב");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"yerushalmi");

        object = json[39].getObject(24);
        assertTextUriProperty(object, "yerushalmi-39-13-1");
        assertLabelProperty( object ,"תלמוד ירושלמי נידה יג א");
        assertPositionProperty(object ,"25");
        assertBookProperty(object,"yerushalmi");

    }

      private int numOfObjectsForMasechet(int masechetNum) {
        int objectsPerMasechet[] = {-1, 135,74,67,88,61,117,52,66,56,40,25,184,130,142,66,84,52,44,43,52,67,38,44,169,143,79,94,93,108,96,88,73,67,114,18,88,74,38,25};
        return objectsPerMasechet[masechetNum];
    }
}
