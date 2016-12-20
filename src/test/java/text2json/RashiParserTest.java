package text2json;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by omishali on 20/12/2016.
 */
public class RashiParserTest {
    // TODO learn basics of JUnit

    @Test
    public void test1() throws IOException {
        MefareshParser parser = new MefareshParser("rashi");
        BufferedReader reader = null;
        ParserOutput output = parser.parse(reader);

        // TODO test the content of ParserOutput using Assert.* methods
        String myStr = "helo";
        assertEquals("hello", myStr);
    }
}
