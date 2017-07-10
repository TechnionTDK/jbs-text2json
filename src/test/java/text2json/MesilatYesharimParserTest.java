package text2json;

import com.google.gson.stream.JsonReader;
import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MesilatYesharimParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.*;

/**
 * Created by omishali on 20/12/2016.
 */
public class MesilatYesharimParserTest {
    static SubjectsJson json;

    @BeforeClass
    public static void setup() throws Exception {
        MesilatYesharimParser parser = new MesilatYesharimParser();
        BufferedReader reader = getText("mesilatyesharim/mesilatyesharim.txt");
        createOutputFolderIfNotExists("mesilatyesharim");
        parser.parse(reader, "json/mesilatyesharim/mesilatyesharim.json");
        json = getJson("json/mesilatyesharim/mesilatyesharim.json");
    }
    @Test
    public void testTotalNumberOfObjects() {
        assertNotNull(json);
        assertEquals(28, json.subjects.size());
    }

    @Test
    public void testSpecificPackageObjects() {
        Map<String, String> object;


        object = json.getObject(0);
        assertEquals(JBR_TEXT + "mesilatyesharim-0", object.get(URI));
        assertEquals("0", object.get(JBO_POSITION));
        assertEquals("הקדמת הרב המחבר זצ\"ל", object.get(RDFS_LABEL));
        assertBookProperty("mesilatyesharim", object.get(JBO_BOOK));

        object = json.getObject(1);
        assertEquals(JBR_TEXT + "mesilatyesharim-1", object.get(URI));
        assertEquals("1", object.get(JBO_POSITION));
        assertEquals("בביאור כלל חובת האדם בעולמו", object.get(RDFS_LABEL));
        assertBookProperty("mesilatyesharim", object.get(JBO_BOOK));

        object = json.getObject(27);
        assertEquals(JBR_TEXT + "mesilatyesharim-27", object.get(URI));
        assertEquals("27", object.get(JBO_POSITION));
        assertEquals("חתימה", object.get(RDFS_LABEL));
        assertBookProperty("mesilatyesharim", object.get(JBO_BOOK));
    }
}
