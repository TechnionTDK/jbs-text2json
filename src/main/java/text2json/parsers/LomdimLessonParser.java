package text2json.parsers;

import text2json.Line;
import text2json.LineMatcher;
import text2json.Parser;

import java.io.IOException;

/**
 * Created by omishali on 17/01/2017.
 */
public class LomdimLessonParser extends Parser {
    private static final String BEGIN_COMMENT = "begin_comment";
    private static final String BEGIN_TITLE = "begin_title";
    private static final String BEGIN_GUIDELINES = "begin_guidelines";
    private static final String BEGIN_SINGLE_CHOICE = "begin_single_choice";
    private static final String BEGIN_MULTIPLE_CHOICE = "begin_multiple_choice";
    private static final String BEGIN_SORT = "begin_sort";
    private static final String BEGIN_MATERIALS = "begin_materials";
    public static final String KEY_TYPE = "type";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_NUM_OF_CORRECT = "num_of_correct";
    private static final String VALUE_GUIDELINES = "guidelines";
    private static final String VALUE_SINGLE_CHOICE = "single_choice";
    private static final String VALUE_MULTIPLE_CHOICE = "multiple_choice";
    private static final String VALUE_MATERIALS = "materials";
    private static final String VALUE_SORT = "sort";

    @Override
    protected void registerMatchers() {
        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_COMMENT;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("#");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_TITLE;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("כותרת") && line.wordCount() == 1;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_GUIDELINES;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("הנחיות") && line.wordCount() == 1;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SINGLE_CHOICE;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("בחירה יחידה") && line.wordCount() == 2;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_MULTIPLE_CHOICE;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("בחירה מרובה") && line.wordCount() == 3;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_MATERIALS;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("חומרי לימוד") && line.wordCount() == 2;
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_SORT;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מיון") && line.wordCount() == 1;
            }
        });
    }

    @Override
    protected void onLineMatch(String type, Line line) throws IOException {
        switch(type) {
            case BEGIN_COMMENT:
                break;
            case BEGIN_TITLE:
                jsonObject().add(KEY_TYPE, "title");
                break;
            case BEGIN_GUIDELINES:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, VALUE_GUIDELINES);
                break;
            case BEGIN_SINGLE_CHOICE:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, VALUE_SINGLE_CHOICE);
                break;
            case BEGIN_MULTIPLE_CHOICE:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, VALUE_MULTIPLE_CHOICE);
                jsonObject().add(KEY_NUM_OF_CORRECT, line.extract("בחירה מרובה", " "));
                break;
            case BEGIN_SORT:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, VALUE_SORT);
                break;
            case BEGIN_MATERIALS:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, VALUE_MATERIALS);
                break;
            case NO_MATCH: // content line
                if(jsonObject().hasTuple(KEY_TYPE, "title")) {
                    jsonObject().add(KEY_CONTENT, line.getLine());
                    return;
                }
                if(jsonObject().hasTuple(KEY_TYPE, VALUE_GUIDELINES)
                        || jsonObject().hasTuple(KEY_TYPE, VALUE_MATERIALS)) {
                    jsonObject().addToArray(KEY_CONTENT, line.getLine());
                    return;
                }
                // if we are here, the object must be of type question.
                if(!jsonObject().hasKey(KEY_QUESTION))
                    jsonObject().add(KEY_QUESTION, line.getLine());
                else
                    jsonObject().addToArray(KEY_CONTENT, line.getLine());
                return;
        }

    }

    @Override
    protected String getUri() {
        return null;
    }
}
