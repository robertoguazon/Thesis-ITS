package sample.controller;

import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.utils.RuntimeUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 30/08/2016.
 */
public class PracticalPrintExerciseViewerController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;
    @FXML private Button runCodeButton;

    @FXML private TextArea outputTextArea;
    @FXML private Button clearOutputButton;

    private PracticalPrintExercise currentExercise;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test(); //TODO - delete
    }

    private void test() {
        PracticalPrintExercise practicalPrintExercise = new PracticalPrintExercise();
        practicalPrintExercise.setTitle("sample practical exercise");
        practicalPrintExercise.addTag("sample");
        practicalPrintExercise.addTag("exercise");
        practicalPrintExercise.addTag("practical");

        practicalPrintExercise.setInstructions("Try output \"word\" by using System.out.println();");
        practicalPrintExercise.setClassName("Sample");
        practicalPrintExercise.setMethodName("run");
        practicalPrintExercise.setPrintValidator("word");
        practicalPrintExercise.setMustMatch(false);
        practicalPrintExercise.setCode(
                "public class Sample {\n" +
                        "   public void run() {\n" +
                        "       System.out.println(\"Hello word\");\n" +
                        "   }\n" +
                        "}"
        );
        practicalPrintExercise.setMustMatch(false);

        view(practicalPrintExercise);
    }

    @FXML
    private void clearCode() {
        this.codeTextArea.clear();
    }

    @FXML
    private void runCode() {
        clearOutput();
        currentExercise.makeID();
        if (currentExercise != null) {
            try {
                RuntimeUtil.setOutStream(RuntimeUtil.STRING_STREAM);
                RuntimeUtil.compile(currentExercise);
                outputTextArea.setText(RuntimeUtil.STRING_OUTPUT.toString());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STREAM);
            }
        }
    }

    @FXML
    private void clearOutput() {

        try {
            this.outputTextArea.clear();
            RuntimeUtil.reset(RuntimeUtil.STRING_OUTPUT);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void submit() {
        //TODO evaluate and get score and push to database
        System.out.println(RuntimeUtil.STRING_OUTPUT.toString());
        System.out.println("Correct: " + currentExercise.evaluate(RuntimeUtil.STRING_OUTPUT.toString()));
    }

    public void view(PracticalPrintExercise practicalPrintExercise) {
        currentExercise = practicalPrintExercise;

        titleLabel.setText(practicalPrintExercise.getTitle());
        instructionsTextArea.setText(practicalPrintExercise.getInstructions());

        codeTextArea.setText(practicalPrintExercise.getCode());
        practicalPrintExercise.codeProperty().bind(codeTextArea.textProperty());


    }

}
