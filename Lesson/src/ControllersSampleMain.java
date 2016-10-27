import com.westlyf.controller.ControllerType;
import com.westlyf.controller.Controllers;
import com.westlyf.database.ExamDatabase;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.exercise.quiz.Exam;
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
        //testQuizExerciseViewer(primaryStage);
        //testPracticalPrintExerciseViewer(primaryStage);
        //testPracticalReturnExerciseViewer(primaryStage);
        //testVideoPracticalExerciseViewer(primaryStage);
        //testExamChoicesOnlyViewer(primaryStage);

            //reviews
        //testQuizExerciseReviewViewer();
        //testPracticalExerciseReviewViewer();

        //makers
        //testLessonMaker(primaryStage);
        //testQuizExerciseMaker(primaryStage);
        //testPracticalExerciseMaker(primaryStage);
        testVideoPracticalExerciseMaker(primaryStage);
        //testExamChoicesOnlyMaker(primaryStage);

        //Platform.exit(); //TODO comment out when testing
    }

    private void testTextLessonViewer(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.TEXT_LESSON_VIEWER, LessonDatabase.getTextLessonUsingLID("lid197678772180313"));
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

        //view exam
        //Node node = Controllers.getNode(ControllerType.QUIZ_EXERCISE_VIEWER, ExamDatabase.getExamUsingLID("lid297786924355311"));

        //view quiz
        //Node node = Controllers.getNode(ControllerType.QUIZ_EXERCISE_VIEWER, ExerciseDatabase.getQuizExerciseUsingLID("lid589434155957645"));

        /// /all types of items are included
        Node node = Controllers.getNode(ControllerType.QUIZ_EXERCISE_VIEWER, ExerciseDatabase.getQuizExerciseUsingLID("lid1710104421235870"));

        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void testPracticalPrintExerciseViewer(Stage primaryStage) {

        Node node = Controllers.getNode(ControllerType.PRACTICAL_PRINT_EXERCISE_VIEWER,
                ExerciseDatabase.getPracticalExerciseUsingTitle("Exercise 2 - Strings and Chars"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testPracticalReturnExerciseViewer(Stage primaryStage) {

        Node node = Controllers.getNode(ControllerType.PRACTICAL_RETURN_EXERCISE_VIEWER, ExerciseDatabase.getPracticalExerciseUsingTitle("Sample Multiply"));
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

    private void testQuizExerciseMaker(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.QUIZ_EXERCISE_MAKER);
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testPracticalExerciseMaker(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.PRACTICAL_EXERCISE_MAKER);
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testVideoPracticalExerciseMaker(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.VIDEO_PRACTICAL_EXERCISE_MAKER);
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testVideoPracticalExerciseViewer(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.VIDEO_PRACTICAL_EXERCISE_VIEWER,
                ExerciseDatabase.getVideoPracticalExerciseUsingTitle("Exercise 4 - Boolean"));
        //Controllers.getNode(ControllerType.VIDEO_PRACTICAL_EXERCISE_VIEWER, ExerciseDatabase.getVideoPracticalExerciseUsingLID("lid51204534921811"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testExamChoicesOnlyMaker(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.EXAM_CHOICES_ONLY_MAKER);
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testExamChoicesOnlyViewer(Stage primaryStage) {
        Node node = Controllers.getNode(ControllerType.EXAM_CHOICES_ONLY_VIEWER, ExamDatabase.getExamUsingLID("lid7824281890242"));
        Scene scene = new Scene((Parent)node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        Controllers.disposeAll();
    }

}
