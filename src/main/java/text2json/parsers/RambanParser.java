package text2json.parsers;

/**
 * Created by orel on 26/07/18.
 */
public class RambanParser extends MefareshParser {

    @Override
    protected String getMefareshName() {
        return "ramban";
    }

    @Override
    protected String getMefareshHebrewName() {
        return "רמב\"ן";
    }
}
