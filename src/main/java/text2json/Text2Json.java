package text2json;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by omishali on 20/12/2016.
 */
public class Text2Json {
    public static final String PARSER_CONFIG_FILE = "src/main/resources/configParsers.json";

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        ConfigFile configFile = gson.fromJson(new BufferedReader(new FileReader(PARSER_CONFIG_FILE)), ConfigFile.class);
        List<ConfigParser> parsers = configFile.getAllParsers();

        // run each parser for all files within its input dir
        for (ConfigParser configParser : parsers){
            File inputDir = new File(configFile.getInputBaseDir() + '/' + configParser.getInput());
            File outputDir = new File(configFile.getOutputBaseDir() + '/' + configParser.getOutput());

            // we assume an input dir contains files only and not subdirectories
            for (File file : inputDir.listFiles()) {
                BufferedReader raw = new BufferedReader(new FileReader(file.getPath()));

                // get class from string
                Class parserType = Class.forName(configParser.getParser());
                // create new object of that class
                Object parser = parserType.newInstance();
                // get object's method
                Method parse = parserType.getMethod("parse", BufferedReader.class, String.class);

                // get the input file name, without the suffix
                String name = file.getName().split("\\.")[0];

                // invoke parse method, output goes to OutputDir
                parse.invoke(parser, raw, outputDir.getPath() + "/" + name + ".json");
            }


        }
    }
}
