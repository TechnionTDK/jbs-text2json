package text2json;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by omishali on 20/12/2016.
 */
public class Text2Json {
    public static final String PARSER_CONFIG_FILE = "src/main/resources/configParsers.json";

    /**
     *
     * @param args first argument is required: the path to jbs-data.
     *             A second argument (optional) is a name of a specific raw folder.
     *             If specified, only a parser with that raw folder will be executed.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Should provide a single argument to main: path of the 'jbs-data' repository.");
            exit(1);
        }

        String jbsPath = args[0];
        // the jbs repo should have subfolders raw (input) and json (for output)
        String inputPath = jbsPath + "/" + "raw";
        String outputPath = jbsPath + "/" + "json";

        Gson gson = new Gson();
        ConfigFile configFile = gson.fromJson(new BufferedReader(new FileReader(PARSER_CONFIG_FILE)), ConfigFile.class);
        List<ConfigParser> parsers = configFile.getAllParsers();

        if (args.length > 1)
            parsers = filterParserList(parsers, args[1]);

        // run each parser for all files within its input dir
        for (ConfigParser configParser : parsers){
            System.out.println("Executing parser " + configParser.getParser());
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

    /**
     * Keeps only those parsers having the specified raw folder
     * (usually it should correspond to a single parser).
     * @param parsers
     */
    private static List<ConfigParser> filterParserList(List<ConfigParser> parsers, String rawFolder) {
        List<ConfigParser> result = new ArrayList<>();
        for (ConfigParser parser : parsers) {
            if (parser.getInput().equals(rawFolder))
                result.add(parser);
        }

        return result;
    }
}
