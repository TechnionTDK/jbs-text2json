package text2json;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by omishali on 23/05/2017.
 */
public class TestLine {
    @Test
    public void testBeginsWith() {
        Line line = new Line("ספר בראשית");
        assertTrue(line.beginsWith("ספר"));
    }
}
