package text2json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 23-Dec-16.
 */
public class ConfigFile {
    List<ConfigParser> parsers = new ArrayList<ConfigParser>();

    public List<ConfigParser> getAllParsers(){
        return parsers;
    }
}
