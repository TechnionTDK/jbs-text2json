package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.HaemunotVehadeotParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class HaemunotVehadeotParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new HaemunotVehadeotParser();
        createOutputFolderIfNotExists("haemunotvehadeot");
        BufferedReader reader = getText("haemunotvehadeot/haemunotvehadeot.txt");
        createOutputFolderIfNotExists("haemunotvehadeot");
        parser.parse(reader, "json/haemunotvehadeot/haemunotvehadeot.json");
        json = getJson("json/haemunotvehadeot/haemunotvehadeot.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(110, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "haemunotvehadeot-0-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"האמונות והדעות הקדמה א");
        assertBookProperty(object,"haemunotvehadeot");

        object = json.getObject(5);
        assertTextUriProperty(object, "haemunotvehadeot-0-6");
        assertLabelProperty( object ,"האמונות והדעות הקדמה ו");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"haemunotvehadeot");

        object = json.getObject(13);
        assertTextUriProperty(object, "haemunotvehadeot-1-5");
        assertLabelProperty( object ,"האמונות והדעות חדוש ה");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"haemunotvehadeot");

        object = json.getObject(34);
        assertTextUriProperty(object, "haemunotvehadeot-3-6");
        assertLabelProperty( object ,"האמונות והדעות צווי ואזהרה ו");
        assertPositionProperty(object ,"35");

        object = json.getObject(53);
        assertTextUriProperty(object, "haemunotvehadeot-5-8");
        assertLabelProperty( object ,"האמונות והדעות זכיות וחובות ח");
        assertPositionProperty(object ,"54");

        object = json.getObject(81);
        assertTextUriProperty(object, "haemunotvehadeot-9-3");
        assertLabelProperty( object ,"האמונות והדעות בגמול ועונש ג");
        assertPositionProperty(object ,"82");
        assertBookProperty(object,"haemunotvehadeot");

        object = json.getObject(102);
        assertTextUriProperty(object, "haemunotvehadeot-10-12");
        assertLabelProperty( object ,"האמונות והדעות הנהגת האדם יב");
        assertPositionProperty(object ,"103");
        assertBookProperty(object,"haemunotvehadeot");

        object = json.getObject(109);
        assertTextUriProperty(object, "haemunotvehadeot-10-19");
        assertLabelProperty( object ,"האמונות והדעות הנהגת האדם יט");
        assertPositionProperty(object ,"110");
    }
}
