package text2json;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by omishali on 20/12/2016.
 */
public class Text2Json {
    public static final String PARSER_CONFIG_FILE = "src/main/resources/configParsers.json";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Should provide a single argument to main: path of the 'jbs' repository.");
            exit(1);
        }

        String jbsPath = args[0];
        // the jbs repo should have subfolders raw (input) and json (for output)
        String inputPath = jbsPath + "/" + "raw";
        String outputPath = jbsPath + "/" + "json";

        Gson gson = new Gson();
        ConfigFile configFile = gson.fromJson(new BufferedReader(new FileReader(PARSER_CONFIG_FILE)), ConfigFile.class);
        List<ConfigParser> parsers = configFile.getAllParsers();

        // run each parser for all files within its input dir
        for (ConfigParser configParser : parsers){
            File inputDir = new File(inputPath + '/' + configParser.getInput());
            File outputDir = new File(outputPath + '/' + configParser.getOutput());

            // we create the output dir if it doesn't exist
            if (!outputDir.exists())
                outputDir.mkdir();

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
