import com.westlyf.database.DatabaseConnection;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizType;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class DatabaseMain {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Connection user = DatabaseConnection.getUserConnection();
        Connection lesson = DatabaseConnection.getLessonConn();
        Connection exercise = DatabaseConnection.getExerciseConn();

        System.out.println(user);
        System.out.println(lesson);
        System.out.println(exercise);

        testPush();
        testPull();

        try {
            user.close();
            lesson.close();
            exercise.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testPush() {
        ArrayList<StringProperty> tagList = new ArrayList<>();

        TextLesson textLesson = new TextLesson();
        textLesson.setTitle("sample test");
        textLesson.setText("This is a sample test. keep in touch with the program so it is easy to find the errors");
        tagList.add(new SimpleStringProperty("sample"));
        tagList.add(new SimpleStringProperty("test"));
        textLesson.setTags(tagList);
        textLesson.makeID();
        LessonDatabase.storeData(textLesson);

        tagList.clear();

        VideoLesson videoLesson = new VideoLesson();
        videoLesson.setTitle("sample test");
        videoLesson.setPathLocation("C:\\Users\\whatever");
        tagList.add(new SimpleStringProperty("video"));
        tagList.add(new SimpleStringProperty("sample"));
        videoLesson.setTags(tagList);
        videoLesson.makeID();
        LessonDatabase.storeData(videoLesson);

        QuizExercise quizExercise = new QuizExercise();
        quizExercise.setTitle("sample test");
        quizExercise.addTag("sample");
        quizExercise.addTag("exercise");
        quizExercise.addTag("quiz");
        ArrayList<String> choices = new ArrayList<>();
        choices.add("game");
        choices.add("sample test");
        choices.add("master");
        ArrayList<String> validAnswers = new ArrayList();
        validAnswers.add("sample test");
        quizExercise.addItem(QuizType.RADIOBUTTON, "what is this?", choices, validAnswers);
        quizExercise.makeID();
        ExerciseDatabase.storeData(quizExercise);
    }

    private static void testPull() {
        System.out.println();
        System.out.println("trying to load sample text lesson using lid...");
        System.out.println(LessonDatabase.getTextLessonUsingLID("lid627925056887413"));

        System.out.println();
        System.out.println("trying to load sample video lesson using lid...");
        System.out.println(LessonDatabase.getVideoLessonUsingLID("lid627925277121613"));

        System.out.println();
        System.out.println("trying to load sample text lesson using title...");
        System.out.println(LessonDatabase.getTextLessonUsingTitle("sample test"));

        System.out.println();
        System.out.println("trying to load sample video lesson using title...");
        System.out.println(LessonDatabase.getVideoLessonUsingTitle("sample test"));

        System.out.println();
        System.out.println("trying to load sample quiz exercise using lid...");
        System.out.println(ExerciseDatabase.getQuizExerciseUsingLID("lid632146417557684"));

        System.out.println();
        System.out.println("trying to load sample quiz exercise using title...");
        System.out.println(ExerciseDatabase.getQuizExerciseUsingTitle("sample test"));

        System.out.println();
        System.out.println("trying to load sample quiz exercise using tags...");
        System.out.println(ExerciseDatabase.getQuizExercisesUsingTagsExactly("money,play,").get(0));

        System.out.println();
        System.out.println("trying to load sample text lessons using tags...");
        System.out.println(LessonDatabase.getTextLessonsUsingTagsExactly("sample,test,").get(0));

        System.out.println();
        System.out.println("trying to load sample video lessons using tags...");
        System.out.println(LessonDatabase.getVideoLessonsUsingTagsExactly("sample,video,").get(0));
    }
}
