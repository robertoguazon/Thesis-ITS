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

        ArrayList<StringProperty> tagList = new ArrayList<>();

        TextLesson textLesson = new TextLesson();
        textLesson.setTitle("sample test");
        textLesson.setText("This is a sample test. keep in touch with the program so it is easy to find the errors");
        tagList.add(new SimpleStringProperty("sample"));
        tagList.add(new SimpleStringProperty("test"));
        textLesson.setTags(tagList);
        LessonDatabase.storeData(textLesson);

        tagList.clear();

        VideoLesson videoLesson = new VideoLesson();
        videoLesson.setTitle("sample test");
        videoLesson.setPathLocation("C:\\Users\\whatever");
        tagList.add(new SimpleStringProperty("video"));
        tagList.add(new SimpleStringProperty("sample"));
        videoLesson.setTags(tagList);
        LessonDatabase.storeData(videoLesson);

        try {
            user.close();
            lesson.close();
            exercise.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
