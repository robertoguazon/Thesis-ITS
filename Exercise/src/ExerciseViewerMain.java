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
    private QuizExercise quiz;

    {
        quiz = new QuizExercise();
        quiz.setTitle("DOTA");
        quiz.addTag(new SimpleStringProperty("money"));
        quiz.addTag(new SimpleStringProperty("play"));

        QuizItem quizItem1 = new QuizItem();
        quizItem1.setQuestion("What is my name");
        quizItem1.setType(QuizType.RADIOBUTTON);
        quizItem1.addChoice("juggernaut");
        quizItem1.addChoice("bounty hunter");
        quizItem1.addValidAnswer("bounty hunter");
        quiz.addItem(quizItem1);

        QuizItem quizItem2 = new QuizItem();
        quizItem2.setQuestion("What is my age");
        quizItem2.setType(QuizType.CHECKBOX);
        quizItem2.addChoice("12");
        quizItem2.addChoice("1");
        quizItem2.addValidAnswer("1");
        quiz.addItem(quizItem2);
    }

    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample/view/ExerciseViewer.fxml"));
            BorderPane root = loader.load();

            ExerciseViewerController controller = loader.getController();
            controller.setQuiz(quiz);

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
