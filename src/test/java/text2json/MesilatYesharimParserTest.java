package text2json;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by omishali on 20/12/2016.
 */
public class MesilatYesharimParserTest {
    // TODO learn basics of JUnit

    @Test
    public void test1() throws IOException {
        MesilatYesharimParser parser = new MesilatYesharimParser();
        BufferedReader reader = TestUtils.getText("mesilatyesharim.txt");
        ParserOutput output = parser.parse(reader);

        // TODO test the content of ParserOutput using Assert.* methods
        String myStr = "helo";
        assertEquals("hello", myStr);

        // flush the output to json directory
        TestUtils.toJson(output);
    }
}
