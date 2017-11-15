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
    private static final String BEGIN_SERIES = "begin_series";
    private static final String BEGIN_AUTHOR = "begin_author";
    private static final String BEGIN_IMAGE = "begin_image";
    private static final String BEGIN_GUIDELINES = "begin_guidelines";
    private static final String BEGIN_SINGLE_CHOICE = "begin_single_choice";
    private static final String BEGIN_MULTIPLE_CHOICE = "begin_multiple_choice";
    private static final String BEGIN_SORT = "begin_sort";
    private static final String BEGIN_MATERIALS = "begin_materials";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_NUM_OF_CORRECT = "num_of_correct";
    private static final String VALUE_GUIDELINES = "guidelines";
    private static final String VALUE_SINGLE_CHOICE = "single_choice";
    private static final String VALUE_MULTIPLE_CHOICE = "multiple_choice";
    private static final String VALUE_MATERIALS = "materials";
    private static final String VALUE_SORT = "sort";
    private static final String SEPARATOR = "@@@";

    private String title;

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
                return BEGIN_SERIES;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("סדרה:");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_TITLE;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("כותרת:");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_AUTHOR;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("מחבר:");
            }
        });

        registerMatcher(new LineMatcher() {
            @Override
            public String type() {
                return BEGIN_IMAGE;
            }

            @Override
            public boolean match(Line line) {
                return line.beginsWith("תמונה:");
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
                return line.beginsWith("חומרי לימוד") || line.beginsWith("חומרי הלימוד") || line.beginsWith("חומר הלימוד");
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
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, "title");
                String title = line.extract("כותרת:", " ");
                jsonObject().add(KEY_CONTENT, title);
                break;
            case BEGIN_SERIES:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, "series");
                String series = line.extract("סדרה:", " ");
                jsonObject().add(KEY_CONTENT, series);
                break;
            case BEGIN_AUTHOR:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, "author");
                String author = line.extract("מחבר:", " ");
                jsonObject().add(KEY_CONTENT, author);
                break;
            case BEGIN_IMAGE:
                jsonObjectFlush();
                jsonObject().add(KEY_TYPE, "image");
                String image = line.extract("תמונה:", " ");
                jsonObject().add(KEY_CONTENT, image);
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
                // do we have additional title in this line?
                String[] values = line.getLine().split(SEPARATOR);
                if (values.length > 1)
                    jsonObject().add(KEY_TITLE, values[1].trim());
                break;
            case NO_MATCH: // content line
                if(jsonObject().hasTuple(KEY_TYPE, VALUE_GUIDELINES)) {
                    jsonObject().addToArray(KEY_CONTENT, line.getLine());
                    return;
                }
                if(jsonObject().hasTuple(KEY_TYPE, VALUE_MATERIALS)) {
                    assertMaterialsContent(line.getLine());
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

    private void assertMaterialsContent(String line) {
        if (!line.contains("@@@"))
            error("No @@@ separator in material element");
    }

    private void error(String message) {
        throw new RuntimeException("Lesson " + title + ": " + message + " (Line " + getLineNumber() + ")");
    }

    @Override
    protected String getUri() {
        return null;
    }

    @Override
    protected String getBookId() {
        return null;
    }
}
