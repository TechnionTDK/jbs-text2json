package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.SifreiBamidbarParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class SifreiBamidbarParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {

        json = setupParser(new SifreiBamidbarParser() , "sifreibamidbar");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(160, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "sifreibamidbar-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"ספרי במדבר א");
        assertBookProperty(object,"sifreibamidbar");

        object = json.getObject(22);
        assertTextUriProperty(object, "sifreibamidbar-23");
        assertLabelProperty( object ,"ספרי במדבר כג");
        assertPositionProperty(object ,"23");
        assertBookProperty(object,"sifreibamidbar");

        object = json.getObject(44);
        assertTextUriProperty(object, "sifreibamidbar-45");
        assertLabelProperty( object ,"ספרי במדבר מה");
        assertPositionProperty(object ,"45");
        assertBookProperty(object,"sifreibamidbar");

        object = json.getObject(88);
        assertTextUriProperty(object, "sifreibamidbar-89");
        assertLabelProperty( object ,"ספרי במדבר פט");
        assertPositionProperty(object ,"89");
        assertBookProperty(object,"sifreibamidbar");

        object = json.getObject(111);
        assertTextUriProperty(object, "sifreibamidbar-112");
        assertLabelProperty( object ,"ספרי במדבר קיב");
        assertPositionProperty(object ,"112");
        assertBookProperty(object,"sifreibamidbar");

        object = json.getObject(159);
        assertTextUriProperty(object, "sifreibamidbar-160");
        assertLabelProperty( object ,"ספרי במדבר קס");
        assertPositionProperty(object ,"160");
        assertBookProperty(object,"sifreibamidbar");
    }
}
