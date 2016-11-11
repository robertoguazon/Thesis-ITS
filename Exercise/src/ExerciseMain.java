import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizType;
import com.westlyf.utils.Convert;
import com.westlyf.utils.array.ArrayUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 13/06/2016.
 */
public class ExerciseMain extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample/view/QuizMaker.fxml"));
            BorderPane root = loader.load();

            /*
            BorderPane root = new BorderPane();
            root.setPrefSize(400,400);
            ScrollPane quiz = test();
            quiz.prefWidthProperty().bind(root.widthProperty());
            root.setCenter(quiz);
            */

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    //used for testing - to be deleted
    public ScrollPane test() {
        QuizItem quizItem1 = new QuizItem();
        quizItem1.setPointsPerCorrect(5);
        quizItem1.setQuestion("Who is the girlfriend of She-Hulk");
        quizItem1.setType(QuizType.RADIOBUTTON);

        ArrayList<String> choices = new ArrayList<>();
        choices.add("Batman");
        choices.add("Superman");
        choices.add("Hulk");
        choices.add("Spiderman");
        choices.add("None");

        ArrayList<String> answers = new ArrayList<>();
        answers.add("None");
        answers.add("Superman");

        quizItem1.setChoices(Convert.convertToStringProperty(choices));
        quizItem1.setValidAnswers(Convert.convertToStringProperty(answers));

        QuizItem quizItem2 = new QuizItem();
        quizItem2 = quizItem1;

        QuizExercise quizExercise = new QuizExercise();
        quizExercise.addItem(QuizType.CHECKBOX, "Who is the girlfriend of She-Hulk", choices, answers);
        quizExercise.addItem(QuizType.RADIOBUTTON, "Who is the girlfriend of She-Hulk", choices, answers);
        quizExercise.addItem(QuizType.RADIOBUTTON, "Who is the girlfriend of She-Hulk", choices, answers);
        quizExercise.addItem("Who is the girlfriend of She-Hulk", choices, answers);

        return quizExercise.getQuizExercise();
    }
}
