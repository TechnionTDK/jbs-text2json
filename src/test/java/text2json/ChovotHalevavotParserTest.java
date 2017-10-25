package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.ChovotHalevavotParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class ChovotHalevavotParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new ChovotHalevavotParser();
        createOutputFolderIfNotExists("chovothalevavot");
        BufferedReader reader = getText("chovothalevavot/chovothalevavot.txt");
        createOutputFolderIfNotExists("chovothalevavot");
        parser.parse(reader, "json/chovothalevavot/chovothalevavot.json");
        json = getJson("json/chovothalevavot/chovothalevavot.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(90, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "chovothalevavot-0-0");
        assertPositionProperty(object ,"1");
        assertBookProperty( object,"chovothalevavot");
        assertLabelProperty( object ,"חובות הלבבות הקדמת המחבר");

        object = json.getObject(5);
        assertTextUriProperty(object, "chovothalevavot-1-4");
        assertLabelProperty( object ,"חובות הלבבות שער ייחוד ד");
        assertPositionProperty(object ,"6");


        object = json.getObject(13);
        assertTextUriProperty(object, "chovothalevavot-2-1");
        assertLabelProperty( object ,"חובות הלבבות שער הבחינה א");
        assertPositionProperty(object ,"14");

        object = json.getObject(34);
        assertTextUriProperty(object, "chovothalevavot-4-4");
        assertLabelProperty( object ,"חובות הלבבות שער הביטחון ד");
        assertPositionProperty(object ,"35");

        object = json.getObject(54);
        assertTextUriProperty(object, "chovothalevavot-6-9");
        assertLabelProperty( object ,"חובות הלבבות שער הכניעה ט");
        assertPositionProperty(object ,"55");

        object = json.getObject(71);
        assertTextUriProperty(object, "chovothalevavot-8-4");
        assertLabelProperty( object ,"חובות הלבבות שער חשבון הנפש ד");
        assertPositionProperty(object ,"72");

        object = json.getObject(84);
        assertTextUriProperty(object, "chovothalevavot-10-2");
        assertLabelProperty( object ,"חובות הלבבות שער אהבת ה' ב");
        assertPositionProperty(object ,"85");

        object = json.getObject(89);
        assertTextUriProperty(object, "chovothalevavot-10-7");
        assertLabelProperty( object ,"חובות הלבבות שער אהבת ה' ז");
        assertPositionProperty(object ,"90");
    }
}
