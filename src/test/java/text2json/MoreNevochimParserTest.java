package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MoreNevochimParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class MoreNevochimParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {

        json = setupParser(new MoreNevochimParser() , "morenevochim");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(183, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertTextUriProperty(object, "morenevochim-0-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מורה נבוכים הקדמות המתרגמים פתיחת אבן תיבון");
        assertBookProperty(object,"morenevochim");

        object = json.getObject(15);
        assertTextUriProperty(object, "morenevochim-1-13");
        assertLabelProperty( object ,"מורה נבוכים א יג");
        assertPositionProperty(object ,"16");
        assertBookProperty(object,"morenevochim");

        object = json.getObject(33);
        assertTextUriProperty(object, "morenevochim-1-31");
        assertLabelProperty( object ,"מורה נבוכים א לא");
        assertPositionProperty(object ,"34");
        assertBookProperty(object,"morenevochim");

        object = json.getObject(54);
        assertTextUriProperty(object, "morenevochim-1-52");
        assertLabelProperty( object ,"מורה נבוכים א נב");
        assertPositionProperty(object ,"55");
        assertBookProperty(object,"morenevochim");

        object = json.getObject(74);
        assertTextUriProperty(object, "morenevochim-1-72");
        assertLabelProperty( object ,"מורה נבוכים א עב");
        assertPositionProperty(object ,"75");

        object = json.getObject(91);
        assertTextUriProperty(object, "morenevochim-2-12");
        assertLabelProperty( object ,"מורה נבוכים ב יב");
        assertPositionProperty(object ,"92");

        object = json.getObject(114);
        assertTextUriProperty(object, "morenevochim-2-35");
        assertLabelProperty( object ,"מורה נבוכים ב לה");
        assertPositionProperty(object ,"115");
        assertBookProperty(object,"morenevochim");

        object = json.getObject(146);
        assertTextUriProperty(object, "morenevochim-3-18");
        assertLabelProperty( object ,"מורה נבוכים ג יח");
        assertPositionProperty(object ,"147");

        object = json.getObject(160);
        assertTextUriProperty(object, "morenevochim-3-32");
        assertLabelProperty( object ,"מורה נבוכים ג לב");
        assertPositionProperty(object ,"161");

        object = json.getObject(182);
        assertTextUriProperty(object, "morenevochim-3-54");
        assertLabelProperty( object ,"מורה נבוכים ג נד");
        assertPositionProperty(object ,"183");
        assertBookProperty(object,"morenevochim");
    }
}
