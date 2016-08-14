import com.westlyf.database.DatabaseConnection;
import com.westlyf.database.LessonDatabase;
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
    }

    private static void testPull() {
        System.out.println();
        System.out.println("trying to load sample text lesson using lid...");
        System.out.println(LessonDatabase.getTextLessonUsingLID("lid527210822633425"));

        System.out.println();
        System.out.println("trying to load sample video lesson using lid...");
        System.out.println(LessonDatabase.getVideoLessonUsingLID("lid527210984737022"));

        System.out.println();
        System.out.println("trying to load sample text lesson using title...");
        System.out.println(LessonDatabase.getTextLessonUsingTitle("sample test"));

        System.out.println();
        System.out.println("trying to load sample video lesson using title...");
        System.out.println(LessonDatabase.getVideoLessonUsingTitle("sample test"));
    }
}
