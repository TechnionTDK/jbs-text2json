package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.TannaDebeiEliyahuRabbahParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class TannaDebeiEliyahuRabbahParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new TannaDebeiEliyahuRabbahParser() , "tannadebeieliyahurabbah");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(31, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"תנא דבי אליהו רבה א");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(5);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-6");
        assertLabelProperty( object ,"תנא דבי אליהו רבה ו");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(13);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-14");
        assertLabelProperty( object ,"תנא דבי אליהו רבה יד");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(21);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-22");
        assertLabelProperty( object ,"תנא דבי אליהו רבה כב");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"tannadebeieliyahurabbah");

        object = json.getObject(30);
        assertTextUriProperty(object, "tannadebeieliyahurabbah-31");
        assertLabelProperty( object ,"תנא דבי אליהו רבה לא");
        assertPositionProperty(object ,"31");
        assertBookProperty(object,"tannadebeieliyahurabbah");

    }
}
