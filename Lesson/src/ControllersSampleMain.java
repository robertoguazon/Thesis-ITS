import com.westlyf.controller.ControllerType;
import com.westlyf.controller.Controllers;
import com.westlyf.database.ExamDatabase;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class ControllersSampleMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controllers.loadAll();

        //viewers
        //testTextLessonViewer(primaryStage);
        //testVideoLessonViewer(primaryStage);
        testQuizExerciseViewer(primaryStage);
        //testPracticalPrintExerciseViewer(primaryStage);
        //testReturnPrintExerciseViewer(primaryStage);

        //makers
        //testLessonMaker(primaryStage);
        //quizExerciseMaker(primaryStage);
        //practicalExerciseMaker(primaryStage);

        //Platform.exit(); //TODO comment out when testing
    }

    private void testTextLessonViewer(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.TEXT_LESSON_VIEWER, LessonDatabase.getTextLessonUsingLID("lid627925056887413"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testVideoLessonViewer(Stage primaryStage) {


        Node node = Controllers.getNode(ControllerType.VIDEO_LESSON_VIEWER, LessonDatabase.getVideoLessonUsingLID("lid1275089802453708"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testQuizExerciseViewer(Stage primaryStage) {

        Node node = Controllers.getNode(ControllerType.QUIZ_EXERCISE_VIEWER, ExamDatabase.getExamUsingLID("lid297786924355311"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void testPracticalPrintExerciseViewer(Stage primaryStage) {

        Node node = Controllers.getNode(ControllerType.PRACTICAL_PRINT_EXERCISE_VIEWER, ExerciseDatabase.getPracticalExerciseUsingLID("lid2110480049967968"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void testReturnPrintExerciseViewer(Stage primaryStage) {

        Node node = Controllers.getNode(ControllerType.PRACTICAL_RETURN_EXERCISE_VIEWER, ExerciseDatabase.getPracticalExerciseUsingLID("lid217838487380978"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void testLessonMaker(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.LESSON_MAKER);
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void quizExerciseMaker(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.QUIZ_EXERCISE_MAKER);
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void practicalExerciseMaker(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.PRACTICAL_EXERCISE_MAKER);
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
