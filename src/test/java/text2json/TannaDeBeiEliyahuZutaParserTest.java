package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TannaDeBeiEliyahuZutaParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TannaDeBeiEliyahuZutaParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new TannaDeBeiEliyahuZutaParser() , "tannadebeieliyahuzuta");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(22, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא - סדר אליהו זוטא א");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(4);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-5");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא - סדר אליהו זוטא ה");
        assertPositionProperty(object ,"5");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(7);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-8");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא - סדר אליהו זוטא ח");
        assertPositionProperty(object ,"8");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(9);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-10");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא - סדר אליהו זוטא י");
        assertPositionProperty(object ,"10");
        assertBookProperty(object,"tannadebeieliyahuzuta");

        object = json.getObject(11);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-1-12");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא - סדר אליהו זוטא יב");
        assertPositionProperty(object ,"12");
        assertBookProperty(object,"tannadebeieliyahuzuta");


        object = json.getObject(15);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-2-0");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא נספחים - הקדמה");
        assertPositionProperty(object ,"16");
        assertBookProperty(object,"tannadebeieliyahuzuta");


        object = json.getObject(18);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-2-4");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא נספחים - פרקי דר' אליעזר");
        assertPositionProperty(object ,"19");
        assertBookProperty(object,"tannadebeieliyahuzuta");


        object = json.getObject(21);
        assertTextUriProperty(object, "tannadebeieliyahuzuta-3-3");
        assertLabelProperty( object ,"תנא דבי אליהו זוטא - הירידות ג'");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"tannadebeieliyahuzuta");


    }
}
