package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.PesiktaRabbatiParser;
import text2json.parsers.PirkeiDeRabbiEliezerParser;

import java.io.BufferedReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class PirkeiDeRabbiEliezerParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Parser parser = new PirkeiDeRabbiEliezerParser();
        createOutputFolderIfNotExists("pirkeiderabbieliezer");
        BufferedReader reader = getText("pirkeiderabbieliezer/pirkeiderabbieliezer.txt");
        createOutputFolderIfNotExists("pirkeiderabbieliezer");
        parser.parse(reader, "json/pirkeiderabbieliezer/pirkeiderabbieliezer.json");
        json = getJson("json/pirkeiderabbieliezer/pirkeiderabbieliezer.json");
    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(54, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "pirkeiderabbieliezer-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"פרקי דרבי אליעזר פרק א");
        assertBookProperty(object,"pirkeiderabbieliezer");

        object = json.getObject(5);
        assertTextUriProperty(object, "pirkeiderabbieliezer-6");
        assertLabelProperty( object ,"פרקי דרבי אליעזר פרק ו");
        assertPositionProperty(object ,"6");
        assertBookProperty(object,"pirkeiderabbieliezer");

        object = json.getObject(13);
        assertTextUriProperty(object, "pirkeiderabbieliezer-14");
        assertLabelProperty( object ,"פרקי דרבי אליעזר פרק יד");
        assertPositionProperty(object ,"14");
        assertBookProperty(object,"pirkeiderabbieliezer");

        object = json.getObject(21);
        assertTextUriProperty(object, "pirkeiderabbieliezer-22");
        assertLabelProperty( object ,"פרקי דרבי אליעזר פרק כב");
        assertPositionProperty(object ,"22");
        assertBookProperty(object,"pirkeiderabbieliezer");

        object = json.getObject(32);
        assertTextUriProperty(object, "pirkeiderabbieliezer-33");
        assertLabelProperty( object ,"פרקי דרבי אליעזר פרק לג");
        assertPositionProperty(object ,"33");
        assertBookProperty(object,"pirkeiderabbieliezer");

        object = json.getObject(53);
        assertTextUriProperty(object, "pirkeiderabbieliezer-54");
        assertLabelProperty( object ,"פרקי דרבי אליעזר פרק נד");
        assertPositionProperty(object ,"54");
        assertBookProperty(object,"pirkeiderabbieliezer");
    }
}
