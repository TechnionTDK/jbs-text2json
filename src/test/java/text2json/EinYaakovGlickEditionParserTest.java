package text2json;

import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.EinYaakovGlickEditionParser;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static text2json.TestUtils.*;

/**
 * Created by Assaf on 08/06/2017.
 */
public class EinYaakovGlickEditionParserTest {
    static SubjectsJson json;
    static SubjectsJson packageJson;

    @BeforeClass
    public static void beforeClass() throws Exception {
        json = setupParser(new EinYaakovGlickEditionParser() , "einyaakovglickedition");

    }

    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(252, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        assertTextUriProperty(object, "einyaakovglickedition-1-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"עין יעקב (מאת שמואל צבי גליק) ברכות פרק א");
        assertBookProperty(object,"einyaakovglickedition");

        object = json.getObject(42);
        assertTextUriProperty(object, "einyaakovglickedition-4-4");
        assertLabelProperty( object ,"עין יעקב (מאת שמואל צבי גליק) פסחים פרק ד");
        assertPositionProperty(object ,"43");
        assertBookProperty(object,"einyaakovglickedition");

        object = json.getObject(98);
        assertTextUriProperty(object, "einyaakovglickedition-13-16");
        assertLabelProperty( object ,"עין יעקב (מאת שמואל צבי גליק) יבמות פרק טז");
        assertPositionProperty(object ,"99");
        assertBookProperty(object,"einyaakovglickedition");

        object = json.getObject(150);
        assertTextUriProperty(object, "einyaakovglickedition-19-1");
        assertLabelProperty( object ,"עין יעקב (מאת שמואל צבי גליק) קידושין פרק א");
        assertPositionProperty(object ,"151");
        assertBookProperty(object,"einyaakovglickedition");

        object = json.getObject(201);
        assertTextUriProperty(object, "einyaakovglickedition-25-5");
        assertLabelProperty( object ,"עין יעקב (מאת שמואל צבי גליק) שבועות פרק ה");
        assertPositionProperty(object ,"202");
        assertBookProperty(object,"einyaakovglickedition");

        object = json.getObject(251);
        assertTextUriProperty(object, "einyaakovglickedition-31-7");
        assertLabelProperty( object ,"עין יעקב (מאת שמואל צבי גליק) חולין פרק ז");
        assertPositionProperty(object ,"252");
        assertBookProperty(object,"einyaakovglickedition");
    }
}
