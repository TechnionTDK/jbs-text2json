package text2json;

import com.google.gson.stream.JsonReader;
import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MishnaParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by shilonoa on 3/19/2017.
 */
public class MishnaParserTest {
    private static final int NUM_OF_FILES = 63;
    private static final int NUM_OF_FIRST_file = 1;
    int FileNum = 0;
    static int FileSederNum = 0;
    static int FileMasechetNum = 0;
    String mefaresh = null;
    String fileName = null;

    @BeforeClass
    public static void setup() throws IOException {
        for (int FileNum = NUM_OF_FIRST_file; FileNum <= NUM_OF_FILES; FileNum++) {
            System.out.println("Creating mishna json for book number " + FileNum);
            if (FileNum <= 11){
                FileSederNum = 1;
                FileMasechetNum = FileNum;
            }
            else if (FileNum <= 23){
                FileSederNum = 2;
                FileMasechetNum = FileNum - 11;
            }
            else if (FileNum <= 30){
                FileSederNum = 3;
                FileMasechetNum = FileNum - 23;
            }
            else if (FileNum <= 40){
                FileSederNum = 4;
                FileMasechetNum = FileNum - 30;
            }
            else if (FileNum <= 51){
                FileSederNum = 5;
                FileMasechetNum = FileNum - 40;
            }
            else if (FileNum <= 63){
                FileSederNum = 6;
                FileMasechetNum = FileNum - 51;
            }
            MishnaParser parser = new MishnaParser();
            BufferedReader reader = TestUtils.getText("/mishna/mishna-" + FileSederNum + "-" + FileMasechetNum + ".txt");
            parser.parse(reader, "json/mishna-" + FileSederNum + "-" + FileMasechetNum + ".json");
        }
        FileSederNum = 0;
        FileMasechetNum = 0;
        return;
    }

    @Test
    public void test() throws IOException {
        final File folder = new File("./json");
        System.out.println(folder.getAbsoluteFile() + "*******");
        for (final File fileEntry : folder.listFiles()) {
            System.out.println(fileEntry.getAbsoluteFile() + "&&&&&&");
            fileName = fileEntry.getName();
            System.out.println("Testing:" + fileName);
            FileSederNum = Integer.valueOf(extract(fileName,"mishna-","-"));
            FileMasechetNum = Integer.valueOf(extract(fileName,"mishna-"+ FileSederNum + "-","."));
            testMishna();
            System.out.println("SUCCESS");
            FileSederNum = 0;
            FileMasechetNum = 0;
        }
    }

    private void testMishna() throws IOException {
        JsonReader jsonReader = new JsonReader(TestUtils.getJsonFileReader("mishna-"+FileSederNum+"-"+FileMasechetNum+".json"));
        jsonReader.beginObject();

        assertEquals(jsonReader.nextName(), "subjects");
        jsonReader.beginArray();
        int sederNum = 1;
        int masechetNum = 1;
        int perekNum = 1;
        int mishnaNum = 1;
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            //======== URI ==============
            assertEquals(jsonReader.nextName(), "uri");
            boolean adv_perek = testUri(jsonReader, perekNum, mishnaNum);
            if (adv_perek == true) {
                perekNum++;
                mishnaNum = 1;
            }
            //======== JBO_TEXT ==============
            assertEquals(jsonReader.nextName(), "jbo:text");
            testText(jsonReader, perekNum, mishnaNum);
            //======== LABLE ==============
            assertEquals(jsonReader.nextName(), "rdfs:label");
            assertNotNull(jsonReader.nextString());
            //======== NAME ==============
            assertEquals(jsonReader.nextName(), "jbo:name");
            assertNotNull(jsonReader.nextString());
            //======== SEDER ==============
            assertEquals(jsonReader.nextName(), "jbo:seder");
            testSeder(jsonReader);
            //======== MASECHET ==============
            assertEquals(jsonReader.nextName(), "jbo:masechet");
            testMasechet(jsonReader);
            //======== PEREK ==============
            assertEquals(jsonReader.nextName(), "jbo:perek");
            assertNotNull(jsonReader.nextString());
            //======== MISHNA ==============
            assertEquals(jsonReader.nextName(), "jbo:mishna");
            assertNotNull(jsonReader.nextString());

            jsonReader.endObject();
        }
    }

    private boolean testUri(JsonReader jsonReader, int perekNum, int mishnaNum) throws IOException {
        boolean retval = false;
        Line uriLine = new Line(jsonReader.nextString());
        //System.out.println(uriLine.getLine());
        String uriMefaresh = uriLine.extract("mishna-", "-");
        mefaresh = uriMefaresh;
        assertNotNull(mefaresh);
        int uriSederNum = Integer.valueOf(uriLine.extract("mishna-"+uriMefaresh+"-","-"));
        int uriMasechetNum = Integer.valueOf(uriLine.extract("mishna-"+uriMefaresh+"-"+uriSederNum+"-","-"));
        int uriPerekNum = Integer.valueOf(uriLine.extract("mishna-"+uriMefaresh+"-"+uriSederNum+"-"+uriMasechetNum+"-","-"));
        int uriMishnaNum = Integer.valueOf(uriLine.extract("mishna-"+uriMefaresh+"-"+uriSederNum+"-"+uriMasechetNum+"-"+uriPerekNum+"-"," "));
        assertEquals(uriSederNum, FileSederNum);
        assertEquals(uriMasechetNum, FileMasechetNum);
        if (Integer.valueOf(uriPerekNum) != perekNum) {
            perekNum++;
            mishnaNum = 1;
            retval = true;
        }
        assertEquals(perekNum, uriPerekNum);
        //assertEquals(mishnaNum +1, uriMishnaNum);
        return retval;
    }

    private void testText(JsonReader jsonReader, int perekNum, int mishnaNum) throws IOException {
        switch (FileSederNum) {
            case 1:
                switch (FileMasechetNum){
                    case 5:
                        testSeder1Mesechet5Text(jsonReader, perekNum, mishnaNum);
                        break;
                    default:
                        jsonReader.nextString();
                        break;
                }
                break;
            /*case 15:
                switch (FileMasechetNum){
                    case 5:
                        testSeder1Mesechet5Text(jsonReader);
                        break;
                }
                break;*/
            default:
                jsonReader.nextString();
                break;
        }
    }

    private void testSeder1Mesechet5Text(JsonReader jsonReader, int perekNum, int mishnaNum) throws IOException {
        String text = jsonReader.nextString();
        switch (perekNum) {
            case 4:
                switch (mishnaNum) {
                    case 1:
                        switch (mefaresh) {
                            case "עיקר תוי\"ט":
                                assertEquals(text, "עיקר תוי\"ט  {א} כִּדְשָׁרִינַן בְּפִרְקִין דִּלְעֵיל מִשְׁנָה ו'. הָרַ\"שׁ: {ב} מַשְׁמַע דְּמִשֶּׁרַבּוּ עוֹבְרֵי עֲבֵרוֹת הִתְקִינוּ שֶׁיְּהֵא זֶה מְלַקֵּט מִתּוֹךְ שֶׁל זֶה כְּדֶרֶךְ כוּ', הַיְנוּ דַּוְקָא גַּסִּין. וְטַעֲמָא דְּאַף עַל גַּב דִּמְלַקְּטִין שֶׁלֹּא בְּטוֹבָה אֲפִלּוּ הָכִי הוֹאִיל וְזֶה מְלַקֵּט בְּשֶׁל זֶה וְזֶה בְּשֶׁל זֶה אִי מְלַקְּטִין הַדַּקִּין נִרְאֶה כִּמְתַקֵּן");
                                break;
                            case "ר\"ע מברטנורה":
                                assertEquals(text, "ר\"ע מברטנורה  בָּרִאשׁוֹנָה. כְּדֶרֶךְ שֶׁהוּא מְלַקֵּט מֵחֲבֵרוֹ אֶת הַגַּס הַגַּס. הָכִי מְפָרְשָׁא מַתְנִיתִין בַּיְרוּשַׁלְמִי, בָּרִאשׁוֹנָה הָיוּ אוֹמְרִים מְלַקֵּט אָדָם בְּתוֹךְ שֶׁלּוֹ אֶת הַגַּס הַגַּס כְּדֶרֶךְ שֶׁמְּלַקֵּט בְּשֶׁל חֲבֵרוֹ {א}, בֵּין גַּסִּין בֵּין דַּקִּין, וְאַף עַל גַּב דְּעֵצִים וַעֲשָׂבִים כְּשֶׁמְּלַקְּטִים אוֹתָן מִתּוֹךְ הַשָּׂדֶה מְתַקֵּן הַשָּׂדֶה לִזְרִיעָה, כְּשֶׁמְּלַקֵּט הַגַּס כְּלוֹמַר הָעֵצִים וְהָעֲשָׂבִים הַגַּסִּים וּמַנִּיחַ אֶת הַדַּקִּין, תָּלִינַן דְּצָרִיךְ לָעֵצִים הוּא וְלָאו לְתַקֵּן אֶת הַקַּרְקַע קָעָבֵיד, כְּדֶרֶךְ דְּתָלִינַן בִּמְלַקֵּט בִּשְׂדֵה חֲבֵרוֹ בֵּין דַּקִּין וּבֵין גַּסִּין, דְּלֹא עָבֵיד אִינָשׁ לְתַקֵּן שְׂדֵה חֲבֵרוֹ וּמֵידַע יְדִיעַ דְּלָעֵצִים הוּא צָרִיךְ: מִשֶּׁרַבּוּ עוֹבְרֵי עֲבֵרָה. שֶׁהָיוּ מְלַקְּטִין בִּשְׂדוֹתֵיהֶן בֵּין דַּקִּים בֵּין גַּסִּין וְהֵן אוֹמְרִים בַּגַּסִּים לִקַּטְנוּ: שֶׁלֹּא בְטוֹבָה. שֶׁאֵין חֲבֵרוֹ מַחֲזִיק לוֹ טוֹבָה, דְּהַשְׁתָּא וַדַּאי לֹא אָתֵי לְמִלְקָט הַדַּקִּין {ב} כֵּיוָן שֶׁאֵין חֲבֵרוֹ מַחֲזִיק לוֹ טוֹבָה עַל זֶה: וְאֵין צָרִיךְ לוֹמַר שֶׁיְּקַצֵּץ לָהֶם מְזוֹנוֹת. שֶׁיֹּאמַר לוֹ לְקֹט מִשָּׂדִי הַיּוֹם וְאֶתֵּן לְךָ מְזוֹנוֹתֶיךָ, דִּפְשִׁיטָא דְאָסוּר");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 9:
                switch (mishnaNum) {
                    case 4:
                        switch (mefaresh) {
                            case "עיקר תוי\"ט":
                                assertEquals(text, "עיקר תוי\"ט  {ז} הַשָּׁמוּר. וְתֵימַהּ דִּלְתַנָּא קַמָּא שָׁמוּר הַיְנוּ מַה שֶּׁבַּבָּתִּים וּמַה שֶּׁבַּגִּנּוֹת, וְאִי אָמַרְתְּ אַף עַל הַשָּׁמוּר אִם כֵּן זְמַן הַבִּעוּר אֵימָתַי כוּ'. וְנִרְאֶה לִי שֶׁזֶּה שֶׁאָמַר רַבִּי יוֹסֵי הָכִי קָאָמַר, לֹא כְדִבְרֵי תַנָּא קַמָּא שֶׁאוֹסֵר עַל כָּל שָׁמוּר, אֶלָּא מָצִינוּ שֶׁרַשַּׁאי לֶאֱכֹל אַף עַל הַשָּׁמוּר, וְתָפַסְתָּ מֻעָט כוּ', וְהַיְנוּ בִּכְגוֹן מְחֻבָּר בָּאִילָן וְהָאִילָן בְּמָקוֹם שָׁמוּר הוּא עוֹמֵד, מִכָּל מָקוֹם הוֹאִיל וְהַפֵּרוֹת עֲדַיִן בַּמְחֻבָּר אוֹכְלִים עֲלֵיהֶם. תּוֹסְפוֹת יוֹם טוֹב");
                                break;
                            case "ר\"ע מברטנורה":
                                assertEquals(text, "ר\"ע מברטנורה  וְכָל הָאֲרָצוֹת כְּאַחַת לַזֵּיתִים וְלַתְּמָרִים. כָּל הָאֲרָצוֹת שֶׁבְּאֶרֶץ יִשְׂרָאֵל אוֹכְלִים זֵיתִים וּתְמָרִים עַל יְדֵי אֶרֶץ אַחַת שֶׁלֹּא כָלוּ בָהּ הַזֵּיתִים וְהַתְּמָרִים לַחַיָּה מִן הַשָּׂדֶה, בֵּין שֶׁתִּהְיֶה הָאָרֶץ הַהִיא בִּיהוּדָה אוֹ בְעֵבֶר הַיַּרְדֵּן אוֹ בַגָּלִיל. וְאֵין הֲלָכָה כְּרַבִּי שִׁמְעוֹן: אוֹכְלִים עַל הַמֻּפְקָר. אוֹכְלִים מִמַּה שֶּׁבַּבַּיִת עַל יְדֵי שֶׁמָּצוּי מֵאוֹתוֹ הַמִּין בַּשָּׂדֶה בְּמָקוֹם מֻפְקָר. אֲבָל אִם פָּסַק וְכָלָה אוֹתוֹ הַמִּין מִמָּקוֹם הַמֻּפְקָר, אַף עַל פִּי שֶׁנִּמְצָא מִמֶּנּוּ בְּמָקוֹם הַשָּׁמוּר בַּבָּתִּים אוֹ בַגִּנּוֹת חַיָּב לְבַעֵר, כִּדְאָמְרָן כָּלָה לַחַיָּה מִן הַשָּׂדֶה כַּלֵּה לִבְהֶמְתְּךָ מִן הַבַּיִת: רַבִּי יוֹסֵי מַתִּיר אַף עַל הַשָּׁמוּר. וְאֵין הֲלָכָה כְּרַבִּי יוֹסֵי: אוֹכְלִים עַל הַטְּפִיחִים. עַל מַה שֶּׁמְּשִׂימִים הָעוֹפוֹת בַּטְּפִיחִים, שֶׁהֵן כְּלֵי חֶרֶס הַבְּנוּיִים בַּכֹּתֶל אֲשֶׁר שָׁם צִפֳּרִים יְקַנֵּנוּ. וְאוֹכְלִים מִמַּה שֶּׁבַּבַּיִת כָּל זְמַן שֶׁמָּצוּי מֵאוֹתוֹ הַמִּין בַּטְּפִיחִים: וְעַל הַדּוּפְרָא. אִילָן שֶׁעוֹשֶׂה פֵּרוֹת שְׁנֵי פְעָמִים בַּשָּׁנָה, אוֹכְלִים בָּרִאשׁוֹן עַד שֶׁיִּכְלֶה הָאַחֲרוֹן שֶׁבְּמִינָם: אֲבָל לֹא עַל הַסִּתְוָנִיּוֹת. אֵין אוֹכְלִין בָּעֲנָבִים עַל יְדֵי הָעֲנָבִים הַגְּדֵלִים בִּימוֹת הַסְּתָיו");
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

    private void testSeder(JsonReader jsonReader) throws IOException {
        boolean retval = false;
        Line uriLine = new Line(jsonReader.nextString());
        int uriSederNum = Integer.valueOf(uriLine.extract("mishna-", " "));
        assertEquals(uriSederNum, FileSederNum);
    }

    private void testMasechet(JsonReader jsonReader) throws IOException {
        boolean retval = false;
        Line uriLine = new Line(jsonReader.nextString());
        int uriMasechetNum = Integer.valueOf(uriLine.extract("mishna-"+FileSederNum+"-", " "));
        assertEquals(uriMasechetNum, FileMasechetNum);
    }

    public String extract(String src, String first, String last) {
        int firstIndex = 0;
        int lastIndex = src.length();
        if(first != " ") {
            firstIndex = src.indexOf(first) + first.length();
        }
        if(last != " ") {
            lastIndex = src.indexOf(last, firstIndex);
        }
        return src.substring(firstIndex, lastIndex).trim();
    }
}