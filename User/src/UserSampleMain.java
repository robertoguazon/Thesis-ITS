import com.sun.org.apache.xerces.internal.dom.NamedNodeMapImpl;
import com.westlyf.database.ExamDatabase;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.lesson.Level;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import com.westlyf.user.User;

/**
 * Created by robertoguazon on 09/09/2016.
 */
public class UserSampleMain {

    public static void main(String[] args) {

        //test1(); //TextLessons //TODO -delete
        test2(); //VideoLessons, Exercises, pulling from databases //TODO - delete
    }

    private static void test1() {
        Level root  = new Level();
        root.addTag("root");

        Level _1_1 = new Level();
        _1_1.addTag("Strings");
        _1_1.setTitle("Strings and things");
        _1_1.setLevel("1.1");
        Level _1_2 = new Level();
        _1_2.addTag("Variancy of variables");
        _1_2.setLevel("1.2");

        root.addSubLevel(_1_1);
        root.addSubLevel(_1_2);

        TextLesson lessonStrings = new TextLesson();
        lessonStrings.addTag("Strings");
        lessonStrings.setID("lid911231231890");
        lessonStrings.setTitle("Strings tutorial");
        lessonStrings.setText("String string = \"Master\"");

        TextLesson lessonVariables = new TextLesson();
        lessonVariables.addTag("variables");
        lessonVariables.setID("lid123123125464");
        lessonVariables.setTitle("Variables tutorial");
        lessonVariables.setText("int is short for Integers");

        User sampleUser = new User();
        sampleUser.addHistory(_1_1, lessonStrings, 0,0);
        sampleUser.addHistory(_1_2, lessonVariables, 0,0);
        sampleUser.addHistory(_1_1, lessonStrings, 0,0);

        System.out.println(sampleUser);
    }

    private static void test2() {
        Level root = new Level();

        Level variables = new Level();
        variables.setLevel("variables-1.0");

        Level variables_1_1 = new Level();
        variables_1_1.setLevel("variables-1.1");
        Level variables_1_2 = new Level();
        variables_1_2.setLevel("variables-1.2");
        Level variables_1_3 = new Level();
        variables_1_3.setLevel("variables-1.3");
        variables.addSubLevels(variables_1_1,variables_1_2,variables_1_3);

        Level strings = new Level();
        strings.setLevel("strings-1.0");

        Level strings_1_1 = new Level();
        strings_1_1.setLevel("strings-1.1");
        Level strings_1_2 = new Level();
        strings_1_2.setLevel("strings-1.2");
        strings.addSubLevels(strings_1_1,strings_1_2);

        root.addSubLevels(strings,variables);

        User man = new User();
        man.setName("Adam");
        man.setAge(15);

        man.addHistory(variables_1_1, LessonDatabase.getTextLessonUsingLID("lid627925056887413"),0,0);
        man.addHistory(variables_1_1, LessonDatabase.getTextLessonUsingLID("lid627925056887413"),0,0);
        man.addHistory(variables_1_2, LessonDatabase.getVideoLessonUsingLID("lid1275089802453708"),0,0);
        man.addHistory(variables_1_2, LessonDatabase.getVideoLessonUsingLID("lid294207804725146"),0,0);
        man.addHistory(variables_1_3, ExamDatabase.getExamUsingLID("lid297786924355311"), 5,10);
        man.addHistory(variables_1_3, ExerciseDatabase.getQuizExerciseUsingLID("lid632146417557684"),2,3);
        man.addHistory(strings_1_1, ExerciseDatabase.getPracticalExerciseUsingLID("lid2110480049967968"), 1,1);
        man.addHistory(strings_1_2, ExerciseDatabase.getPracticalExerciseUsingLID("lid217838487380978"), 0,1);

        System.out.println(man);
        System.out.println("Level strings percentage exercise: " + man.getLevelExercisePercentage(root));
        System.out.println("Level variables percentage exam: " + man.getLevelExamPercentage(root));

    }
}
