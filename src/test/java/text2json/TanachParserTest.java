package text2json;

import com.google.gson.stream.JsonReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by USER on 10-Jan-17.
 */
public class TanachParserTest {
    private static final int NUM_OF_BOOKS = 39;
    private static final int NUM_OF_FIRST_BOOK = 1;


    @BeforeClass
    public static void setup() throws IOException {
//        int bookNum = 7;
//        TanachParser parser = new TanachParser(bookNum);
//        BufferedReader reader = TestUtils.getText("/tanach/tanach-" + bookNum + ".txt");
//        parser.parse(reader);

        for(int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_BOOKS; bookNum++){
            System.out.println("Creating json for book number " + bookNum);
            TanachParser parser = new TanachParser(bookNum);
            BufferedReader reader = TestUtils.getText("/tanach/tanach-" + bookNum + ".txt");
            parser.parse(reader);
        }
    }

    @Test
    public void test() throws IOException {
//        int bookNum = 7;
//        testTanach(bookNum);
        for(int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_BOOKS; bookNum++){
            System.out.println("Testing book number " + bookNum);
            testTanach(bookNum);
            System.out.println("SUCCESS :)");
        }
    }


    private void testTanach(int bookNum) throws IOException {
        JsonReader jsonReader = TestUtils.getJson("tanach-" + bookNum + ".json");
        jsonReader.beginObject();
        assertEquals(jsonReader.nextName(), "subjects");
        jsonReader.beginArray();
        int perekNum = 1;
        int pasukNum = 0;
        int positionInSefer  = 0;
        while (jsonReader.hasNext()) {
            positionInSefer++;
            pasukNum++;

            jsonReader.beginObject();
            //======== URI ==============
            assertEquals(jsonReader.nextName(), "uri");
            boolean adv_perek = testUri(jsonReader, bookNum, perekNum, pasukNum);
            if(adv_perek == true){

                //System.out.println("num of psukim in perek " + perekNum + " is " + (pasukNum-1));
                //System.out.println("    positin in sefer = " + positionInSefer);
                testNumOfPsukimInPerek(bookNum, perekNum, pasukNum);

                perekNum++;
                pasukNum = 1;
            }
            //System.out.println("    adv_perek = " + adv_perek);
            //System.out.println("perekNum = " + perekNum + "; pasukNum = " + pasukNum + "; positionInSefer = " + positionInSefer);
            //======== TEXT ==============
            assertEquals(jsonReader.nextName(), "jbo:text");
            testText(jsonReader, bookNum, perekNum, pasukNum);
            //======== TITLES ==============
            assertEquals(jsonReader.nextName(), "titles");
            jsonReader.beginArray();
            //TODO: add here sample title test instead of this next block
            jsonReader.beginObject();
            assertEquals(jsonReader.nextName(), "title");
            jsonReader.nextString();
            jsonReader.endObject();
            jsonReader.beginObject();
            assertEquals(jsonReader.nextName(), "title");
            jsonReader.nextString();
            jsonReader.endObject();
            //end of titles block
            jsonReader.endArray();
            jsonReader.endObject();
        }
        //System.out.println("num of psukim in perek " + perekNum + " is " + pasukNum);

        assertEquals(numOfPsukimInBook(bookNum), positionInSefer);
    }

    private void testNumOfPsukimInPerek(int bookNum, int perekNum, int pasukNum) {
        int numOfPsukim = pasukNum -1;
        switch (bookNum){
            case 6:
                switch (perekNum){
                    case 1:
                        assertEquals(18, numOfPsukim);
                        break;
                    case 2:
                        assertEquals(24, numOfPsukim);
                        break;
                    case 3:
                        assertEquals(17, numOfPsukim);
                        break;
                    case 4:
                        assertEquals(24, numOfPsukim);
                        break;
                    case 5:
                        assertEquals(15, numOfPsukim);
                        break;
                    case 6:
                        assertEquals(27, numOfPsukim);
                        break;
                    case 7:
                        assertEquals(26, numOfPsukim);
                        break;
                    case 8:
                        assertEquals(35, numOfPsukim);
                        break;
                    case 9:
                        assertEquals(27, numOfPsukim);
                        break;
                    case 10:
                        assertEquals(43, numOfPsukim);
                        break;
                    case 11:
                        assertEquals(23, numOfPsukim);
                        break;
                    case 12:
                        assertEquals(24, numOfPsukim);
                        break;
                    case 13:
                        assertEquals(33, numOfPsukim);
                        break;
                    case 14:
                        assertEquals(15, numOfPsukim);
                        break;
                    case 15:
                        assertEquals(63, numOfPsukim);
                        break;
                    case 16:
                        assertEquals(10, numOfPsukim);
                        break;
                    case 17:
                        assertEquals(18, numOfPsukim);
                        break;
                    case 18:
                        assertEquals(28, numOfPsukim);
                        break;
                    case 19:
                        assertEquals(51, numOfPsukim);
                        break;
                    case 20:
                        assertEquals(9, numOfPsukim);
                        break;
                    case 21:
                        assertEquals(45, numOfPsukim);
                        break;
                    case 22:
                        assertEquals(34, numOfPsukim);
                        break;
                    case 23:
                        assertEquals(16, numOfPsukim);
                        break;
                    case 24:
                        assertEquals(33, numOfPsukim);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void testText(JsonReader jsonReader, int bookNum, int perekNum, int pasukNum) throws IOException {
        switch (bookNum){
            case 5:
                testBook5Text(jsonReader, perekNum, pasukNum);
                break;

            default:
                jsonReader.nextString();
                break;
        }
    }


    private int numOfPsukimInBook(int bookNum) {
        switch (bookNum){
            case 1:
                //בראשית
                return 1533;
            case 2:
                //שמות
                return 1210;
            case 3:
                //ויקרא
                return 859;
            case 4:
                //במדבר
                return 1288;
            case 5:
                //דברים
                return 956;
            case 6:
                //יהושע
                return 658;
            case 7:
                //שופטים
                return 618;
            case 8:
                //שמואל א
                return 811;
            case 9:
                //שמואל ב
                return 695;
            case 10:
                //מלכים א
                return 817;
            case 11:
                //מלכים ב
                return 719;
            case 12:
                //ישעיה
                return 1291;
            case 13:
                //ירמיה
                return 1364;
            case 14:
                //יחזקאל
                return 1273;
            case 15:
                //תחילת תרי עשר
                //הושע
                return 197;
            case 16:
                //יואל
                return 73;
            case 17:
                //עמוס
                return 146;
            case 18:
                //עובדיה
                return 21;
            case 19:
                //יונה
                return 48;
            case 20:
                //מיכה
                return 105;
            case 21:
                //נחום
                return 47;
            case 22:
                //חבקוק
                return 56;
            case 23:
                //צפניה
                return 53;
            case 24:
                //חגי
                return 38;
            case 25:
                //זכריה
                return 211;
            case 26:
                //מלאכי
                //סוף תרי עשר
                return 55;
            case 27:
                //תהילים
                return 2527;
            case 28:
                //משלי
                return 915;
            case 29:
                //איוב
                return 1070;
            case 30:
                //שיר השירים
                return 117;
            case 31:
                //רות
                return 85;
            case 32:
                //איכה
                return 154;
            case 33:
                //קהלת
                return 222;
            case 34:
                //אסתר
                return 167;
            case 35:
                //דניאל
                return 357;
            case 36:
                //עזרא
                return 280;
            case 37:
                //נחמיה
                return 405;
            case 38:
                //דברי הימים א
                return 943;
            case 39:
                //דברי הימים ב
                return 822;

            default:
                return -1;
        }
    }

    /**
     * assert uri is valid. return true if it's the first pasuk in the perek (for out of func update).
     * @param jsonReader
     * @param bookNum
     * @param perekNum
     * @param pasukNum
     * @return
     * @throws IOException
     */
    private boolean testUri(JsonReader jsonReader, int bookNum, int perekNum, int pasukNum) throws IOException {
        boolean retval = false;
        Line uriLine = new Line(jsonReader.nextString());
        int uriBook = Integer.valueOf(uriLine.extract("tanach-", "-"));
        int uriPerek = Integer.valueOf(uriLine.extract("tanach-" + uriBook + "-", "-"));
        int uriPasuk = Integer.valueOf(uriLine.extract("tanach-" + uriBook + "-" + uriPerek + "-", " "));
        assertEquals(uriBook, bookNum);
        if(uriPerek != perekNum){
            perekNum++;
            pasukNum=1;
            retval = true;
        }
        //System.out.println("perekNum = " + perekNum + "; pasukNum = " + pasukNum);
        assertEquals(perekNum, uriPerek);
        assertEquals(pasukNum, uriPasuk);
        return retval;
    }


    private void testBook5Text(JsonReader jsonReader, int perekNum, int pasukNum) throws IOException {
        String text = jsonReader.nextString();
        switch (perekNum){
            case 1:
                switch (pasukNum){
                    case 1:
                        assertEquals(text, "אֵלֶּה הַדְּבָרִים אֲשֶׁר דִּבֶּר מֹשֶׁה אֶל כָּל יִשְׂרָאֵל בְּעֵבֶר הַיַּרְדֵּן בַּמִּדְבָּר בָּעֲרָבָה מוֹל סוּף בֵּין פָּארָן וּבֵין תֹּפֶל וְלָבָן וַחֲצֵרֹת וְדִי זָהָב");
                        break;
                    case 36:
                        assertEquals(text, "זוּלָתִי כָּלֵב בֶּן יְפֻנֶּה הוּא יִרְאֶנָּה וְלוֹ אֶתֵּן אֶת הָאָרֶץ אֲשֶׁר דָּרַךְ בָּהּ וּלְבָנָיו יַעַן אֲשֶׁר מִלֵּא אַחֲרֵי יְהֹוָה");
                        break;
                    default:
                        break;
                }
                break;
            case 34:
                switch (pasukNum){
                    case 2:
                        assertEquals(text,"וְאֵת כָּל נַפְתָּלִי וְאֶת אֶרֶץ אֶפְרַיִם וּמְנַשֶּׁה וְאֵת כָּל אֶרֶץ יְהוּדָה עַד הַיָּם הָאַחֲרוֹן");
                        break;
                    case 10:
                        assertEquals(text,"וְלֹא קָם נָבִיא עוֹד בְּיִשְׂרָאֵל כְּמֹשֶׁה אֲשֶׁר יְדָעוֹ יְהֹוָה פָּנִים אֶל פָּנִים");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

}