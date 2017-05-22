# text2json
A tool for turning raw text of the Jewish bookshelf into json.

## what is this?
the text2json is a platforme for developers to easly add new parsers to the project. every developer can easly add new raw texts and creat a parser that inherits from the general "Parser" class we created,now the developer needs only to define the parsr rules and the output Json. the platform gives the developer very clear guide lins to create his new parser and requires him to fill in three functions

## Background
This tool was devleoped as part of the JBS (Jewish Book Shelf) project in the [TDK lab](http://tdk.net.technion.ac.il/).


# getting started

## Prerequisites
we used intellij (by JetBrains) and suggest you do the same. it has built in Maven and Gson that we need.

## Installation

1. Git clone jbs-text2json repository to a directory.

        git clone git@github.com:TechnionTDK/jbs-text2json.git

1. Git clone jbs-text repository to a directory from jbs-text2json.

        git clone git@github.com:TechnionTDK/jbs-text.git

1. Git clone jbs-raw repository to a directory from jbs-text2json.

        git clone git@github.com:TechnionTDK/jbs-raw.git
        
      
# Using the tool - creating a new parser

## working with Git using command line

mostly you'll need five commands

before every sync to git and during your work
        
        git pull

when syncing new data to any Git folder
      
        cd **dest folder**
        git add -A
        git commit -m "message to go with the commit"
        git push
        
 in case of a conflict the update will be aborted and the conflict will be marked in the conflicted file, you only need to fix the conflict, run "git pull" and try again
 
 
## preparation

1. first you want to add a new folder to the jbs-raw tree and add the relevant raw files

1. second you want to add a new folder to the jbs-text tree

1. in your workspace go to jbs-jext2json>src>main>java>text2json>parsers and add a new java class in the text2json.parsers package, make sure the class extends "Parser"

## writing a new parser and tests

in this readme all the eaxmples are fro the raw text:
            פרשת בראשית  
            בראשית פרק-א
            {א}  בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ:
            {ב}  וְהָאָרֶץ הָיְתָה תֹהוּ וָבֹהוּ וְחשֶׁךְ עַל פְּנֵי תְהוֹם וְרוּחַ אֱלֹהִים מְרַחֶפֶת עַל פְּנֵי הַמָּיִם:
            
### writing the parser

now you need to fill in three functions:

        protected abstract void registerMatchers();
        
this fuction contains all the "rules" the parse will work with, every line you want the parser to notice has to have a matching lineMatcher in "register matchers

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
        
        **for this raw text there is a thirs matcher for BEGIN_PASUK, you can fill it in yourself
the "BEGIN_PARASHA" is defined in the file Parser (also under text2json) and is a simple constant, feel free to add your constants
    
        protected abstract void onLineMatch(String type, Line line) throws IOException;
  
onLineMatch is the function the program will get to if one of the LineMatchers returns a true value (which means the parser detected an interesting line). the function should contain a switch case for every relevant constant (like BEGIN_SEFER from the example) and the function is where you build your Json (using functionaleties of the class "Line", that is also defined in text2json, and Gson capabilities

for example:

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

more examples are in the already written parsers like "tanachParser" and "mishnaParser"
      
      protected abstract String getUri();

this function usualy returns a String containg the relevant uri for the parser

for example:
     
     protected String getUri() {return "jbr:tanach-" + bookNum + "-" + perekNum + "-" + pasukNum;}

### writing the tests

for this part we use JUnit,  we used intelliJ and found [this](https://www.jetbrains.com/help/idea/2017.1/configuring-testing-libraries.html) guide helful

in your workspace the tests go under jbs-jext2json>src>test>java>text2json 


#### imports we used in the tests

      import com.google.gson.stream.JsonReader;
      import org.junit.BeforeClass;
      import org.junit.Test;
      import text2json.parsers.MidrashRabaParser;
      import java.io.BufferedReader;
      import java.io.File;
      import java.io.IOException;
      import static org.junit.Assert.*;

each test has two parts 
1. runnig the parser
1. going over the Jsons

#### running the parser has three phases:

1. creating a new parser
1. creating a new BufferReader with the source file
1. running the parser with the BufferReader and the destination folder

  for example:
  
        MidrashRabaParser parser = new MidrashRabaParser();
        BufferedReader reader = TestUtils.getText("/midrashraba/tanach-midrashraba-" + bookNums[i] + ".txt");
        parser.parse(reader, "json/tanach-midrashraba-" + bookNums[i] + ".json");
        
        
#### going over the Jsons

here we had two ways to test:

1. the firsd method was to create a new JsonReader with the Jasons the parser made and going over every Json Object to see if the values are correct

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
 

 2. the second methos was to put all the objects in an array and randomly test objects
 
 for example:
 
        static SubjectsJson json[] = new SubjectsJson[NUM_OF_LAST_MASECHET + 1];

filling the array:

        for (int bookNum = NUM_OF_FIRST_BOOK; bookNum <= NUM_OF_LAST_BOOK; bookNum++) {
            TanachParser parser = new TanachParser();
            BufferedReader reader = getText("/tanach/tanach-" + bookNum + ".txt");
            parser.parse(reader, "../../jbs-text/tanach/tanach-" + bookNum + ".json");
            json[bookNum] = getJson("../../jbs-text/tanach/tanach-" + bookNum + ".json");
        }
        
        
of course feel free to test your Jsons any way you feel best :)


# config file
Assuming that you have a tested parser, next step is to deploy the results into jbs-text. To do that, you have to update the file **configParsers.json** with basic details about your new parser. You have to provide the fully qualified name of the parser, as well as the input and output folders in jbs-raw and jbs-text, respectively.

The file is under jbs-text2json>src>main>resourcer>configParsers.json. An example entry:

```
{"parser":"text2json.parsers.TanachParser",
    "input": "tanach",
    "output": "tanach"}
```

After the update, execute the **main** method within class **Text2Json**. The method executes all parsers based on the config file, and produces outputs to jbs-text. Eventually you have to push changes in jbs-text.

# Tips For Champions

the tool is easy to use and makes adding new parser relatively easy, however we still encountered some difficulties along the way. below are tips we gathered from working with the tool.

1. the files are often very large so bugs are hard to find, debugging isn't always easy. add alot of "blind" content tests in your test file

1. alot of the times the raw text will be punctuated which will make handeling it harder, in the tests always prefer using the unpunctuated text (the Parser class has a stripVowels() function that can halp you)

1. since it isn't always possible to verify the correctnes of every single entry we suggest you add tests specifically for the first and last json object in the file (or at least insome of the files)

1. we found it's helpful to see the json using Json viewing tools such as [this](http://jsonviewer.stack.hu/)

1. in your tests it is very helpful to check the number of objects you are expecting, this will catch cases where the matcher misses lines

1. if a line is matched to more then one lineMatcher the behavior is undefined

1. use the Line class, it has most of what you need to analyze the text

1. notice that "registerMatchers" runs fully before "OnLineMatch" so matchers can't rely on changes made by the match part of the "OnLineMatch" function

1. print unmatched lines, this will help you find bugs
