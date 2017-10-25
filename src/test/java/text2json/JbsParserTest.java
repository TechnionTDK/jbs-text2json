package text2json;

import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.*;
/**
 * Created by omishali on 25/10/2017.
 */
public class JbsParserTest {
    private JbsParser parser;

    @Before
    public void before() {
        parser = new JbsParser() {
            @Override
            protected void registerMatchers() {

            }

            @Override
            protected void onLineMatch(String type, Line line) throws IOException {

            }

            @Override
            protected String getUri() {
                return null;
            }
        };
    }
    @Test
    public void testGetHeb() {
        assertEquals("תריג", parser.numberToHebrew(613));
        assertEquals("ב", parser.numberToHebrew(2));
        assertEquals("טו", parser.numberToHebrew(15));
        assertEquals("טז", parser.numberToHebrew(16));
        assertEquals("יח", parser.numberToHebrew(18));
        assertEquals("סב", parser.numberToHebrew(62));
        assertEquals("קסא", parser.numberToHebrew(161));

        assertEquals("תק", parser.numberToHebrew(500));
        assertEquals("תר", parser.numberToHebrew(600));
        assertEquals("תשגכ", parser.numberToHebrew(723));
        assertEquals("תתצא", parser.numberToHebrew(891));
        assertEquals("תתק", parser.numberToHebrew(900));
        assertEquals("תתתנ", parser.numberToHebrew(1250));
    }
}
