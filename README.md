# What is text2json?
A library created in order to (easily) convert raw text files of the Jewish bookshelf into structured json files.

# Getting Started

## Get IntelliJ
This library is written in the Java programming language and therefore requires a compatible IDE for it. We strongly recommend using the intelliJ IDEA (free for students).

## Installation
After logging in to GitHub and acquiring permissions to the repositories: 
1. jbs-text2json - https://github.com/TechnionTDK/jbs-text2json
1. jbs-data      - https://github.com/TechnionTDK/jbs-data

You can now log in and clone these repositories to you workspace:

```
clone https://github.com/TechnionTDK/jbs-text2json
clone https://github.com/TechnionTDK/jbs-data
```

Remember the path to the jbs-data directory since you will need it in following steps.

# Executing the code
Basically, you will use this library to create text2json *parsers*. A parser turns a raw text into a structured json. But before creating any parser, you should understand how to execute the existing parsers. There are two execution modes as will be explained.

## Testing mode
Each parser should have an accompanied JUnit test class. The tests take their input from the directory *jbs-data/raw* (that you have cloned). So we should tell them where this directory is located:
- Create a file named *pathToRawFiles.txt* within *src/main/resources*.
- Place a single line within the file with the full path to the local *jbs-data/raw* directory. For example:
```
C:\Users\omishali.TD-CSF\GithubProjects\jbs-data\raw
```

Now you may simply run a single test or all the tests. The tests are located in *src/test*, right-click and run, or right-click the project and run all tests.

## Production mode
When you are certain that the parser you are developing indeed produces the correct results (i.e., after thoroughly testing it), you are ready to write the output to the *jbs-data* repository (to *jbs-data/json*). For that, you should execute the main method of the *Text2Json* class. This main method requires a single argument: the full path to the local *jbs-data* (edit the runtime configuration to supply this argument).

Executing *Text2Json.main* does the follows:
- All parsers defined in *main/resources/configParsers.json* are executed one after the other.
- Each parser is provided with raw input from *jbs-data/raw* as specified in the config file.
- Each parser produces its output to *jbd-data/json* as specified in the config file.

# Creating a new parser
 
## Preparation

1. Add a new folder to the *jbs/raw* folder and add the relevant raw files to it.
1. If does not exist, create a *json* folder in the jbs-text2json project. The output of the tests will go there.
1. In the project, go to jbs-jext2json>src>main>java>text2json>parsers and add a new java class in the text2json.parsers package. Make sure the class extends "Parser".


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
        switch(type) {
            case BEGIN_SEFER:
                break;

            case BEGIN_PEREK:
                jsonObjectFlush();
                chapterNum++;
                String chapterName = HEB_LETTERS_INDEX[chapterNum-1];
                addUri( getUri());
                addBook( getBookId());
                addPosition(chapterNum);
                String label = "מדבר שור " + chapterName;
                addLabel(label);
                break;

            case NO_MATCH:
                appendText( line.getLine());
                break;
        }

Here we can see we added fields to our regular json Object such ass URI and POSITION.
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

#### Testing the parser :

1. Use the setupParser method with the following inputs: 'a new parser' and a 'name' (string) of the files and folders.
The method will auto complete the parameters and bring the necessary paths and directories.


  For example:
  
    public static void beforeClass() throws Exception {
        json = setupParser(new MidbarShurParser() , "midbarshur");
    }
        
        
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
 

 2. The second method was to test the objects (as Map type):
 
 For example:
 
  public void testSpecificPackageObjects() {
        Map<String, String> object;

        object = json.getObject(0);
        
Than testing the fields in the object:

        assertTextUriProperty(object, "midbarshur-1");
        assertPositionProperty(object ,"1");
        assertLabelProperty( object ,"מדבר שור א");
        assertBookProperty(object,"midbarshur");
        
        
of course feel free to test your Jsons any way you feel best :)


# Config file
Assuming that you have a tested parser, next step is to deploy the results into *jbs-data/json*. To do that, you have to update the file **configParsers.json** with basic details about your new parser. You have to provide the fully qualified name of the parser, as well as the input and output folders in *jbs-data/raw* and *jbs-data/json*, respectively.

The file is under src>main>resource>configParsers.json. An example entry:

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
