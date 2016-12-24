package text2json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 23-Dec-16.
 */
public class ConfigFile {
    String inputBaseDir;
    String outputBaseDir;
    List<ConfigParser> Parsers = new ArrayList<ConfigParser>();

    public String getInputBaseDir(){
        return inputBaseDir;
    }

    public String getOutputBaseDir(){
        return outputBaseDir;
    }

    public List<ConfigParser> getAllParsers(){
        return Parsers;
    }
}
