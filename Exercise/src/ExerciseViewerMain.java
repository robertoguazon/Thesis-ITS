import com.westlyf.database.ExerciseDatabase;
import com.westlyf.domain.exercise.Exercise;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizType;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.ExerciseViewerController;

import java.io.IOException;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class ExerciseViewerMain extends Application {

    //sample quiz
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample/view/ExerciseViewer.fxml"));
            BorderPane root = loader.load();

            ExerciseViewerController controller = loader.getController();

            //sample tests load
            controller.setQuiz(ExerciseDatabase.getQuizExerciseUsingLID("lid724313411498252"));
            //controller.setQuiz(ExerciseDatabase.getQuizExerciseUsingLID("lid724133762778948"));
            //controller.setQuiz(ExerciseDatabase.getQuizExerciseUsingLID("lid632146417557684"));

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
