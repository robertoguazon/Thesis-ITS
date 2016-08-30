
import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.domain.exercise.practical.DataType;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.LessonMakerController;
import sample.controller.PracticalExerciseMakerController;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class PracticalExerciseMakerMain extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        //test1(); //-check
        //test2(); //-check

        launch(args);
    }

    private static void test1() {
        System.out.println("-----");
        System.out.println("test1");

        PracticalPrintExercise pe = new PracticalPrintExercise();
        pe.setTitle("PE");
        pe.addTag("practical");
        pe.addTag("exercise");

        pe.setClassName("Sample");
        pe.setMethodName("run");
        pe.setCode("public class Sample {}");
        pe.setInstructions("Make a method named: run and print anything");
        pe.setPrintValidator("ok to not match anyway");

        pe.makeID();
        System.out.println(pe);
    }

    private static void test2() {
        System.out.println("-----");
        System.out.println("test2");

        PracticalReturnExercise pr = new PracticalReturnExercise();

        pr.setTitle("practical return exercise sample");
        pr.addTag("practical");
        pr.addTag("return");
        pr.addTag("sample");

        pr.setClassName("Sample");
        pr.setMethodName("run");
        pr.setCode("public class Sample {" +
                "\n     public void run() {}" +
                "\n}");
        pr.setInstructions("be at ease and follow the instructions");
        pr.setReturnType(DataType.FLOAT);
        pr.addParameterType(DataType.CHAR);
        pr.addParameterType(DataType.CHAR);

        PracticalReturnValidator pr1 = new PracticalReturnValidator();
        pr1.setExpectedReturn("Master");
        pr1.addInput("Master", DataType.STRING);
        pr1.addInput("boot", DataType.INT);
        pr1.addInput("daym", DataType.STRING);

        PracticalReturnValidator pr2 = new PracticalReturnValidator();
        pr2.setExpectedReturn("1.0");
        pr2.addInput("2.5f", DataType.FLOAT);
        pr2.addInput("3.5f", DataType.FLOAT);
        pr2.addInput("5.0f", DataType.FLOAT);

        pr.addReturnValidator(pr1);
        pr.addReturnValidator(pr2);
        pr.makeID();
        System.out.println(pr);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            this.stage = primaryStage;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample/view/PracticalExerciseMaker.fxml"));
            BorderPane root = loader.load();

            PracticalExerciseMakerController controller = loader.getController();
            controller.setStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
