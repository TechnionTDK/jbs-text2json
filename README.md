# Jewish bookshelf - jbs-text2json
A tool created in order to convert raw texts of the Jewish bookshelf from free raw format into json formats.

## What is text2json?
text2json is a platform for developers to easily add new parsers to the jbs project. Every developer can easily add new raw texts and create a parser that inherits from the general "Parser" class. The developer is only required to define the parser "rules" and "behavior" when addressing the rules.

# Getting Started

## Initialization:
First thing first, this library is written in Java programming language and therefore requires a compatible IDE for it. We strongly recommend using the intelliJ IDEA (free to download and use from JetBrains site) due to its simple, elegant and modularity for this project's needs.

## Installation
After logging in to GitHub and acquiring permissions to: 
1. jbs-text2json - https://github.com/TechnionTDK/jbs-text2json
1. jbs-text      - https://github.com/TechnionTDK/jbs-text
1. jbs-raw       - https://github.com/TechnionTDK/jbs-raw

You can now log in and clone these repositories to you workspace. Please notice, clone the jbs-text and jbs-raw repositories INTO the jbs-text2json folder. (If cloning is new to you just give it a quick web search, it's quite simple).

# Creating a new parser
 
## Preparation

1. Step One, add a new folder to the jbs-raw folder and add the relevant raw files to it.
1. Step Two, make sure there is a 'json' folder in the jbs-text2json folder since the output will be stored there.
1. Step Three, add a new folder to the jbs-text folder.
1. Step Four, in your workspace go to jbs-jext2json>src>main>java>text2json>parsers and add a new java class in the text2json.parsers package, make sure the class extends "Parser".


## Writing a new parser and Tests

Note: in this readme all the examples are from the raw text:

            פרשת בראשית  
            בראשית פרק-א
            {א}  בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ:
            {ב}  וְהָאָרֶץ הָיְתָה תֹהוּ וָבֹהוּ וְחשֶׁךְ עַל פְּנֵי תְהוֹם וְרוּחַ אֱלֹהִים מְרַחֶפֶת עַל פְּנֵי הַמָּיִם:
            
### Writing the parser

Your parser needs to implement three main functions: 'registerMatchers', 'onLineMatch' and last 'getUri'.
We will elaborate on each one.


        protected abstract void registerMatchers();
        
This function defines the "rules" the parser will work by. Each line in your text file will be parsed and these 'Rules' will define the action to be taken in each case you will choose for every line you want the parser to notice. 
for example:
      
      registerMatcher(new LineMatcher() {
            @Override
            public String type() { 
                    return BEGIN_PARASHA;
                }
            @Override
            public boolean match(Line line) {
                return line.beginsWith("פרשת") && line.wordCount() <= 5;
                }
        });
        
        registerMatcher(new LineMatcher() {
            @Override
            public String type() { 
                 return BEGIN_PEREK;
                 }
            @Override
            public boolean match(Line line) {
                return line.contains(" פרק-") && line.wordCount() <= 4;
                }
        });
        
        **for this raw text there is a third matcher for BEGIN_PASUK, you can fill it in yourself
The "BEGIN_PARASHA" is defined in the file Parser (also under text2json) and is a simple constant, feel free to add your constants
We can see here that we want the have a unique action for lines starting with  " פרק-" or "פרשת".


        protected abstract void onLineMatch(String type, Line line) throws IOException;
  
'onLineMatch' is the method activated when one of the LineMatchers (which were previously given) returns a true value - meaning the parser detected an interesting line. The function should contain a switch case for every relevant constant (like BEGIN_SEFER from the example) and the function is where you build your Json file (using functionalities of the class "Line” that is also defined in text2json, and Gson capabilities)
When matching a case like BEGIN_SEFER you may add any fields you wish to your Json object and when 'jsonObjectFlush()' will occur all the information written will be stored to your output json file as a "subject".

For example:

      protected void onLineMatch(String type, Line line) throws IOException {
        switch (type){
            case BEGIN_PARASHA: // we assume bookNum is initialized
                jsonObjectFlush();
                positionInParasha = 0;
                parashaNum++;
                // adding parasha triples in packages json
                packagesJsonObject().add(URI, getParashaUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine());
                packagesJsonObject().add(JBO_POSITION, getFixedParashaPosition());
                packagesJsonObjectFlush();
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                perekNum++;
                positionInPerek = 0;
                pasukNum = 0;
                perekTitle = line.extract("פרק-", " ");

                // adding perek triples in packages json
                packagesJsonObject().add(URI, getPerekUri());
                packagesJsonObject().add(RDFS_LABEL, line.getLine().replace('-', ' '));
                packagesJsonObject().add(JBO_POSITION, perekNum);
                packagesJsonObject().add(JBO_SEFER, "jbr:tanach-"+bookNum);
                packagesJsonObjectFlush();
                break;

Here we can see we added fields to our regular json Object and also to our Packages object json such ass URI and POSITION.
More examples are in the already written parsers like "tanachParser" and "mishnaParser"
      
      
      protected abstract String getUri();
This function usually returns a String contain the relevant uri for the text in hand.

For example:
     protected String getUri() {return "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum;}



### writing the tests

For this part we use JUnit, we used intelliJ and found [this](https://www.jetbrains.com/help/idea/2017.1/configuring-testing-libraries.html) guide helpful
In your workspace the tests go under jbs-jext2json>src>test>java>text2json 


#### imports we used in the tests

      import com.google.gson.stream.JsonReader;
      import org.junit.BeforeClass;
      import org.junit.Test;
      import text2json.parsers.MidrashRabaParser;
      import java.io.BufferedReader;
      import java.io.File;
      import java.io.IOException;
      import static org.junit.Assert.*;

Each test has two parts:
1. Running the parser - which will parse your file and create an output json file in json/name_of_your_file/name_of_your_file.json .
1. Going over the Jsons - and retrieving subjects and fields from it in order to test them.

#### Running the parser has three phases:

1. Creating a new parser
1. Creating a new BufferReader with the source file
1. Running the parser with the BufferReader and the destination folder

  For example:
  
        MidrashRabaParser parser = new MidrashRabaParser();
        BufferedReader reader = TestUtils.getText("/midrashraba/tanach-midrashraba-" + bookNums[i] + ".txt");
        parser.parse(reader, "json/tanach-midrashraba-" + bookNums[i] + ".json");
        
        
#### Going over the Jsons

In this case we had two ways to test our output:

1. The first method was to create a new JsonReader with the output Json files the parser created and going over every Json Object to check correctness of its values.

for example:

                  JsonReader jsonReader = new JsonReader(TestUtils.getJsonFileReader(fileName));

and then: 
            
            jsonReader.beginObject();
            assertEquals(jsonReader.nextName(), "subjects");
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                //======== URI ==============
                assertEquals(jsonReader.nextName(), "uri");
                testUri(jsonReader);
                //======== JBO_TEXT ==============
                assertEquals(jsonReader.nextName(), "jbo:text");
                testText(jsonReader);
                jsonReader.endObject();
             }
 

 2. The second method was to put all the objects in an array and randomly test objects
 
 For example:
 
        static SubjectsJson json[] = new SubjectsJson[NUM_OF_LAST_MASECHET + 1];

Filling the array:

        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            TanachParser parser = new TanachParser();
            BufferedReader reader = getText("/tanach/tanach-" + bookNum + ".txt");
            parser.parse(reader, "../../jbs-text/tanach/tanach-" + bookNum + ".json");
            json[bookNum] = getJson("../../jbs-text/tanach/tanach-" + bookNum + ".json");
        }
        
        
of course feel free to test your Jsons any way you feel best :)


# Config file
Assuming that you have a tested parser, next step is to deploy the results into jbs-text. To do that, you have to update the file **configParsers.json** with basic details about your new parser. You have to provide the fully qualified name of the parser, as well as the input and output folders in jbs-raw and jbs-text, respectively.

The file is under jbs-text2json>src>main>resource>configParsers.json. An example entry:

```
{"parser":"text2json.parsers.TanachParser",
    "input": "tanach",
    "output": "tanach"}
```

After the update, execute the **main** method within class **Text2Json**. The method executes all parsers based on the config file, and produces outputs to jbs-text. Eventually you have to push (git push) changes in jbs-text.


# Helpful Stuff
1. You can find 'shortcut' methods for adding fields in your jsonObject - look them up in jbsParser class.
1. A Number count to Hebrew letters count function and more useful arrays in jbsUtils Class.
1. Try not to reinvent and write from scratch, read a simple parser, understand it and modify a copy to your needs.


# Tips For Champions

The tool is easy to use and makes adding new parser relatively easy, however we still encountered some difficulties along the way. Below are tips we gathered from working with the tool.

1. The files are often very large so bugs are hard to find, debugging isn't always easy. Add a lot of "blind" content tests in your test file

1. a lot of the times the raw text will be punctuated which will make handling it harder, in the tests always prefer using the unpunctuated text (the Parser class has a stripVowels() function that can help you)

1. Since it isn't always possible to verify the correctness of every single entry we suggest you add tests specifically for the first and last json object in the file (or at least in some of the files)

1. We found it's helpful to see the json using Json viewing tools such as [this](http://jsonviewer.stack.hu/)

1. In your tests it is very helpful to check the number of objects you are expecting, this will catch cases where the matcher misses lines

1. If a line is matched to more than one lineMatcher the behavior is undefined

1. Use the Line class, it has most of what you need to analyze the text

1. Notice that "registerMatchers" runs fully before "OnLineMatch" so matchers can't rely on changes made by the match part of the "OnLineMatch" function

1. Print unmatched lines, this will help you find bugs
