package text2json;

import com.google.gson.stream.JsonReader;
import org.junit.BeforeClass;
import org.junit.Test;
import text2json.parsers.MidrashRabaParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;
import static text2json.JbsOntology.*;
import static text2json.TestUtils.getJson;
import static text2json.TestUtils.getText;

/**
 * Created by omishali on 05/02/2017.
 */
public class MidrashRabaParserTest {
    static int bookNums[] = {1,2,3,4,5,30,31,32,33,34};
    int uriSefer = 0;
    int uriParasha = 0;
    int uriSeif = 0;
    String uriSeferName = null;
    String uriParashaName = null;
    String uriSeifLetter = null;

    @BeforeClass
    public static void setup() throws Exception {
        for (int i=0; i< bookNums.length; i++) {
            MidrashRabaParser parser = new MidrashRabaParser();
            BufferedReader reader = TestUtils.getText("/midrashraba/tanach-midrashraba-" + bookNums[i] + ".txt");
            parser.parse(reader, "json/tanach-midrashraba-" + bookNums[i] + ".json");
        }
        /*MidrashRabaParser parser = new MidrashRabaParser();
        BufferedReader reader = TestUtils.getText("/midrashraba/tanach-midrashraba-32.txt");
        parser.parse(reader, "json/tanach-midrashraba-32.json");*/
    }


    @Test
    public void test() throws IOException {
        final File folder = new File("./json");
        System.out.println(folder.getAbsoluteFile() + "*******");
        for (final File fileEntry : folder.listFiles()) {
            System.out.println(fileEntry.getAbsoluteFile() + "&&&&&&");
            String fileName = fileEntry.getName();
            if (fileName.contains("packages")){
                continue;
            }
            System.out.println("Testing:" + fileName);
            JsonReader jsonReader = new JsonReader(TestUtils.getJsonFileReader(fileName));
            //testMidrashRaba(jsonReader);
            System.out.println("SUCCESS");
        }
    }

    public void testMidrashRaba(JsonReader jsonReader) throws IOException{
        jsonReader.beginObject();

        assertEquals(jsonReader.nextName(), "subjects");
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            //======== URI ==============
            assertEquals(jsonReader.nextName(), "uri");
            testUri(jsonReader);
            //======== JBO_TEXT ==============
            assertEquals(jsonReader.nextName(), "jbo:text");
            //assertNotNull(jsonReader.nextString());
            testText(jsonReader);
            //======== JBO_TEXT_NIKUD ==============
            assertEquals(jsonReader.nextName(), "jbo:textNikud");
            assertNotNull(jsonReader.nextString());
            //======== SEFER ==============
            assertEquals(jsonReader.nextName(), "jbo:sefer");
            assertEquals(jsonReader.nextString(),"jbr:tanach-midrashraba-"+uriSefer);
            //======== PARASHA ==============
            assertEquals(jsonReader.nextName(), "jbo:parasha");
            assertNotNull(jsonReader.nextString());
            //======== POSITION ==============
            assertEquals(jsonReader.nextName(), "jbo:position");
            assertNotNull(jsonReader.nextString());
            //======== LABLE ==============
            assertEquals(jsonReader.nextName(), "rdfs:label");
            assertNotNull(jsonReader.nextString());
            jsonReader.endObject();
        }
    }


    public void testUri(JsonReader jsonReader) throws IOException {
        Line uriLine = new Line(jsonReader.nextString());
        //System.out.println(uriLine.getLine());
        String uribook = uriLine.extract("jbr:tanach-midrashraba-", "-");
        String uriparasha = uriLine.extract("jbr:tanach-midrashraba-"+uribook+"-" , "-");
        String uriseif = uriLine.extract("jbr:tanach-midrashraba-"+uribook+"-"+uriparasha+"-" , " ");
        assertNotNull(Integer.valueOf(uribook));
        assertNotNull(Integer.valueOf(uriparasha));
        assertNotNull(Integer.valueOf(uriseif));

        uriSefer = Integer.valueOf(uribook);
        uriParasha = Integer.valueOf(uriparasha);
        uriSeif = Integer.valueOf(uriseif);

        assertNotEquals(uriSefer, 0);
        assertNotEquals(uriSeif, 0);

        assertNotEquals(uriSefer, -1);
        assertNotEquals(uriParasha, -1);
        assertNotEquals(uriSeif, -1);
        return;
    }

    private void testText(JsonReader jsonReader) throws IOException {
        switch (uriSefer) {
            case 2:
                testTextBook2(jsonReader);
                break;
            case 32:
                testTextBook32(jsonReader);
                break;
            default:
                break;
        }
        return;
    }
    private void testTextBook2(JsonReader jsonReader) throws IOException {
        String text = jsonReader.nextString();
        switch (uriParasha) {
            case 1:
                switch (uriSeif){
                    case 7:
                        assertEquals(text,stripVowels("פִּילוֹסוֹפִי אֶחָד שָׁאַל אֶת רַבָּן גַּמְלִיאֵל, אֲמַר לֵיהּ צַיָּר גָּדוֹל הוּא אֱלֹהֵיכֶם, אֶלָּא שֶׁמָּצָא סַמְּמָנִים טוֹבִים שֶׁסִּיְּעוּ אוֹתוֹ, תֹּהוּ, וָבֹהוּ, וְחשֶׁךְ, וְרוּחַ, וּמַיִם, וּתְהוֹמוֹת. אֲמַר לֵיהּ תִּפַּח רוּחֵיהּ דְּהַהוּא גַּבְרָא, כּוּלְּהוֹן כְּתִיב בָּהֶן בְּרִיאָה, תֹּהוּ וָבֹהוּ, שֶׁנֶּאֱמַר (ישעיה מה, ז): עֹשֶׂה שָׁלוֹם וּבוֹרֵא רָע. חשֶׁךְ (ישעיה מה, ז): יוֹצֵר אוֹר וגו', מַיִם (תהלים קמח, ד): הַלְּלוּהוּ שְׁמֵי הַשָּׁמָיִם וְהַמַּיִם, לָמָּה, שֶׁצִּוָּה וְנִבְרָאוּ. רוּחַ (עמוס ד, יג): כִּי הִנֵּה יוֹצֵר הָרִים וּבֹרֵא רוּחַ. תְּהוֹמוֹת (משלי ח, כד): בְּאֵין תְּהֹמוֹת חוֹלָלְתִּי."));
                        break;
                    case 14:
                        assertEquals(text,stripVowels("רַבִּי יִשְׁמָעֵאל שָׁאַל אֶת רַבִּי עֲקִיבָא אָמַר לוֹ בִּשְׁבִיל שֶׁשִּׁמַּשְׁתָּ אֶת נַחוּם אִישׁ גַּם זוּ עֶשְׂרִים וּשְׁתַּיִם שָׁנָה, אַכִין וְרַקִּין מִעוּטִין, אֶתִין וְגַמִּין רִבּוּיִן, הָדֵין אֶת דִּכְתִיב הָכָא, מַה הוּא, אֲמַר לֵיהּ, אִלּוּ נֶאֱמַר בְּרֵאשִׁית בָּרָא אֱלֹהִים שָׁמַיִם וָאָרֶץ, הָיִינוּ אוֹמְרִים הַשָּׁמַיִם וְהָאָרֶץ אֱלָהוּת הֵן, אֲמַר לֵיהּ (דברים לב, מז): כִּי לֹא דָבָר רֵק הוּא מִכֶּם, וְאִם רֵק הוּא מִכֶּם, לָמָּה שֶׁאֵין אַתֶּם יוֹדְעִין לִדְרשׁ בְּשָׁעָה שֶׁאִי אַתֶּם יְגֵעִין בּוֹ, (דברים לב, מז): כִּי הוּא חַיֵּיכֶם, אֵימָתַי הוּא חַיֵּיכֶם, בְּשָׁעָה שֶׁאַתֶּם יְגֵעִין בּוֹ. אֶלָּא אֵת הַשָּׁמַיִם, לְרַבּוֹת חַמָּה וּלְבָנָה וּמַזָּלוֹת, וְאֵת הָאָרֶץ, לְרַבּוֹת אִילָנוֹת וּדְשָׁאִין וְגַן עֵדֶן. רַבִּי תַּנְחוּמָא מִשּׁוּם רַב הוּנָא אָמַר (שמות לח, כב): וּבְצַלְאֵל בֶּן אוּרִי בֶּן חוּר לְמַטֵּה יְהוּדָה עָשָׂה אֵת אֲשֶׁר צִוָּה אֹתוֹ משֶׁה, לֹא נֶאֱמַר, אֶלָּא אֵת כָּל אֲשֶׁר צִוָּה ה' אֶת משֶׁה, אֲפִלּוּ דְּבָרִים שֶׁלֹא שָׁמַע מִפִּי רַבּוֹ, הִסְכִּימָה דַעְתּוֹ לְמַה שֶּׁנֶּאֱמַר לְמשֶׁה בְּסִינַי. רַבִּי חוֹנְיָא בְּשֵׁם רַבִּי אָמַר (מלאכי כ, ו): תּוֹרַת אֱמֶת הָיְתָה בְּפִיהוּ, אֵלּוּ דְּבָרִים שֶׁשָּׁמַע מִפִּי רַבּוֹ. וְרַבָּנָן אָמְרֵי (משלי ג, כו): כִּי ה' יִהְיֶה בְכִסְלֶךָ, אֲפִלּוּ דְּבָרִים שֶׁאַתָּה כְּסִיל בָּהֶן, (משלי ג, כו): וְשָׁמַר רַגְלְךָ מִלָּכֶד, רַבִּי דוֹסָאי אָמַר, מִן הַהוֹרָיָה. רַבִּי אַבָּהוּ אָמַר, מִן הָעֲבֵרָה. רַבִּי לֵוִי אָמַר, מִן הַמַּזִּיקִין. \n" +
                                "אָמַר רַבִּי אַבְדִימוּס, אִם נָתַתָּ מִכִּסְךָ צְדָקָה, הַקָּדוֹשׁ בָּרוּךְ הוּא מְשַׁמֶּרְךָ מִן הַפִּיסִין וּמִן הַזִּמְיוֹנוֹת, מִן הַגֻּלְגְּלָאוֹת וּמִן הָאַרְנוֹנִית."));
                    default:
                        break;
                }
                break;
            case 24:
                switch (uriSeif){
                    case 6:
                        assertEquals(text, stripVowels("דָּבָר אַחֵר, זֶה סֵפֶר תּוֹלְדֹת אָדָם, אִלֵּין תּוֹלָדוֹת וְאֵין הָרִאשׁוֹנִים תּוֹלָדוֹת, וּמָה הֵן אֱלֹהוֹת. בְּעוֹן קוֹמֵי אַבָּא כֹּהֵן בַּרְדְּלָא, אָדָם שֵׁת אֱנוֹשׁ, וְשָׁתַק, אָמַר לָהֶם עַד כָּאן בְּצֶלֶם אֱלֹהִים וכו' כְּדִכְתִיב לְעֵיל. \n" +
                                "דָּבָר אַחֵר, אֵלּוּ תּוֹלָדוֹת וְאֵין הָרִאשׁוֹנִים תּוֹלָדוֹת, וּמָה הֵן רוּחוֹת, דְּאָמַר רַבִּי סִימוֹן כָּל מֵאָה וּשְׁלשִׁים שָׁנָה שֶׁפֵּרְשָׁה חַוָּה מֵאָדָם הָיוּ רוּחוֹת הַזְּכָרִים מִתְחַמְּמִים מִמֶּנָּהּ וְהָיוּ מוֹלִידִים מִמֶּנָּהּ, וְרוּחוֹת נְקֵבוֹת מִתְחַמְּמוֹת מֵאָדָם וּמוֹלִידִים מִמֶּנּוּ, הֲדָא הוּא דִכְתִיב (שמואל ב ז, יד): אֲשֶׁר בְּהַעֲוֹתוֹ וְהֹכַחְתִּיו בְּשֵׁבֶט אֲנָשִׁים וּבְנִגְעֵי בְּנֵי אָדָם, בְּנוֹי דְּאָדָם קַדְמָאָה מַאן דְּאָמַר דְּרוּחֵי דְבֵיתָא טָבִין דְּרָבוּ עִמֵּיהּ, וּמַאן דְּאָמַר דְּאִינּוּן בִּישִׁין דְּחַכְּמִין יִצְרֵיהּ, מַאן דְּאָמַר דְּרוּחֵי דְחַקְלָא בִּישִׁין דְּלָא רָבִין עִמֵּיהּ, וּמַאן דְּאָמַר דְּאִינּוּן טָבִין דְּלָא חַכְּמִין יִצְרֵיהּ. \n" +
                                "דָּבָר אַחֵר, אֵלּוּ תּוֹלָדוֹת וְאֵין הָרִאשׁוֹנִים תּוֹלָדוֹת, לָמָּה שֶׁהֵן כָּלִין בַּמַּיִם, דְּאָמַר רַבִּי יְהוֹשֻׁעַ בֶּן לֵוִי כָּל הַשֵּׁמוֹת הַלָּלוּ לָשׁוֹן מַרְדּוּת הֵן, עִירָד, עוֹרְדָן אֲנִי מִן הָעוֹלָם וכו', עַד מַה לִּי לְלֶמֶךְ וּלְתוֹלְדוֹתָיו."));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void testTextBook32(JsonReader jsonReader) throws IOException {
        String text = jsonReader.nextString();
        switch (uriParasha) {
            case 1:
                switch (uriSeif){
                    case 7:
                        assertEquals(text,stripVowels("רַבִּי אַבָּהוּ בְּשֵׁם רַבִּי יוֹסֵי בַּר חֲנִינָא פָּתַח (ישעיה ג, כו): וְאָנוּ וְאָבְלוּ פְּתָחֶיהָ, אֲנִינָה מִבִּפְנִים וַאֲבִילָה מִבַּחוּץ. פְּתָחֶיהָ, חֻרְבָּן רִאשׁוֹן וְחֻרְבָּן שֵׁנִי. וְנִקָּתָה, נְקִיָּה מִדִּבְרֵי תוֹרָה, נְקִיָּה מִדִּבְרֵי נְבוּאָה, נְקִיָּה מִצַּדִּיקִים, נְקִיָּה מִמִּצְווֹת וּמַעֲשִׂים טוֹבִים, לְפִיכָךְ לָאָרֶץ תֵּשֵׁב, (איכה ב, י): יֵשְׁבוּ לָאָרֶץ יִדְמוּ זִקְנֵי וגו'."));
                        break;
                    case 14:
                        assertEquals(text,stripVowels("רַבִּי חֲנִינָא בַּר פַּפָּא פָּתַח (משלי כט, ט): אִישׁ חָכָם נִשְׁפָּט אֶת אִישׁ אֱוִיל וְרָגַז וְשָׂחַק וְאֵין נָחַת. \n" +
                                "אָמַר רַבִּי סִימוֹן כָּל מִי שֶׁהוּא דָן אֶת טִפֵּשׁ, הוּא עַצְמוֹ נִדּוֹן, הֲדָא הוּא דִכְתִיב: חָכָם נִשְׁפָּט, שָׁפַט אֵין כְּתִיב, אֶלָּא נִשְׁפָּט. \n" +
                                "דָּבָר אַחֵר, אִישׁ חָכָם נִשְׁפָּט, זֶה הַקָּדוֹשׁ בָּרוּךְ הוּא, שֶׁנֶּאֱמַר (איוב ט, ד): חֲכַם לֵבָב וְאַמִּיץ כֹּחַ. אֶת אִישׁ אֱוִיל, אֵלּוּ יִשְׂרָאֵל, שֶׁנֶּאֱמַר (ירמיה ד, כב): כִּי אֱוִיל עַמִּי. וְרָגַז וְשָׂחַק, רָגַזְתִּי וְאֵין נָחַת, שָׂחַקְתִּי וְאֵין נָחַת. רָגַזְתִּי עֲלֵיכֶם בִּימֵי פָּקַח בֶּן רְמַלְיָהוּ, שֶׁנֶּאֱמַר (דברי הימים ב כח, ו): וַיַּהֲרֹג פֶּקַח בֶּן רְמַלְיָהוּ וגו', שָׂחַקְתִּי עֲלֵיכֶם בִּימֵי אֲמַצְיָהוּ, שֶׁנֶּאֱמַר (דברי הימים ב כה, יא): וַאֲמַצְיָהוּ הִתְחַזַּק וַיִּנְהַג אֶת עַמּוֹ וַיֵּלֶךְ גֵּיא הַמֶּלַח, מַהוּ גֵּיא הַמֶּלַח, תַּחַת כֵּפֵי הַמֶּלַח, תַּחַת כְּפוּיֵי מִלְחָמָה. וַעֲשֶׂרֶת אֲלָפִים חַיִּים שָׁבוּ בְּנֵי יְהוּדָה וַיְבִיאוּם לְרֹאשׁ הַסָּלַע וַיַּשְׁלִיכוּם וגו', בְּאוֹתָהּ שָׁעָה אָמַר הַקָּדוֹשׁ בָּרוּךְ הוּא לֹא גָזַרְתִּי מִיתָה לִבְנֵי נֹחַ אֶלָּא בַּחֶרֶב, וְאֵלּוּ: וַיְבִיאוּם לְרֹאשׁ הַסָּלַע וַיַּשְׁלִיכוּם וְכֻלָּם נִבְקָעוּ. וְאֵין נָחַת, בְּאוֹתָהּ שָׁעָה אָמַר הַקָּדוֹשׁ בָּרוּךְ הוּא מָה אֵלּוּ עוֹשִׂין כָּאן, יִגְלוּ. כֵּיוָן שֶׁחָטְאוּ גָּלוּ, וְכֵיוָן שֶׁגָּלוּ הִתְחִיל יִרְמְיָה מְקוֹנֵן עֲלֵיהֶם אֵיכָה."));
                    default:
                        break;
                }
                break;
            case 5:
                switch (uriSeif){
                    case 5:
                        assertEquals(text, stripVowels("עַל צַוָּארֵנוּ נִרְדָּפְנוּ, אַדְרִיָּאנוּס שְׁחִיק עֲצָמוֹת פַּקֵּיד וַאֲמַר אִי דַּאֲתֵינַן מַשְׁכַּחְנַן שַׂעֲרָא בִּיהוּדָאֵי אֲרִימוּן רֵאשֵׁיהּ מִינֵּיהּ, הֲדָא הוּא דִכְתִיב: עַל צַוָּארֵנוּ נִרְדָּפְנוּ. \n" +
                                "דָּבָר אַחֵר, עַל צַוָּארֵנוּ נִרְדָּפְנוּ, עַל שֶׁבָּגַדְנוּ בְּצַוָּארֵנוּ בְּיוֹם צָרָה. יָגַעְנוּ וְלֹא הוּנַּח לָנוּ, נְבוּכַדְנֶצַּר שְׁחִיק עֲצָמוֹת צִוָּה לִנְבוּזַרְאֲדָן וַאֲמַר לֵיהּ אֱלָהֲהוֹן דְּאִינוּן מְקַבֵּל בַּעֲלֵי תְּשׁוּבָה הוּא וְיָדוֹ פְּשׁוּטָה לְקַבֵּל שָׁבִים, כֵּיוָן דְּאַתְּ כָּבֵישׁ לְהוֹן לָא תִשְׁבְּקִינַן דִּיצַלּוּן דְּלָא יַעְבְּדוּן תְּיוּבְתָּא, וֵאלָהֲהוֹן יְרַחֵם עֲלֵיהוֹן, וְהַהוּא גַבְרָא נָחֵית בִּשְׁחוֹר אַפִּין, אֶלָּא לָא תְהֵי מַפְלֵּי עֲלֵיהוֹן. כֵּיוָן דִּכְבַשׁ יַתְהוֹן כַּד הֲוָה קָאֵים חַד מִנְהוֹן הֲוָה נָסֵיב לֵיהּ וּמְפַסַּל לֵיהּ אֵיבָרִים אֵיבָרִים, טְרֵיף לֵיהּ קוֹמֵיהוֹן וְאִינוּן מְהַלְּכִין בְּעַל כָּרְחֵיהוֹן שֶׁלֹא בְּטוֹבַתְהוֹן. \n" +
                                "אָמַר רַבִּי יְהוֹשֻׁעַ בֶּן לֵוִי נְבוּזַרְאֲדָן הוּא אַרְיוֹךְ, וְלָמָּה נִקְרָא שְׁמוֹ אַרְיוֹךְ שֶׁהָיָה נוֹהֵם עַל הַשִּׁבְיָה כְּאַרְיֵה, עַד שֶׁהִגִּיעוּ לִפְרָת, כֵּיוָן שֶׁהִגִּיעוּ לִפְרָת אֲמַר לְהוֹן לְחַיָּלוּתָא שַׁבְקִינוּן דְּנִיחוּן, דִּי מִן כַּדּוּן לֵית אֱלָהֲהוֹן חוֹזֵר עֲלֵיהוֹן, הֲדָא הוּא דִכְתִיב (תהלים קלז, א): עַל נַהֲרוֹת בָּבֶל שָׁם יָשַׁבְנוּ גַּם בָּכִינוּ, עַד לְשָׁם לֹא יָשַׁבְנוּ."));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }


    public String stripVowels(String rawString){
        String newString = "";
        for(int j=0; j<rawString.length(); j++) {
            if(rawString.codePointAt(j)<1425
                    || rawString.codePointAt(j)>1479)
            { newString = newString + rawString.charAt(j); }
        }
        return(newString);
    }
}
