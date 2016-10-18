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

    @FXML private Button submitButton;

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
        practicalPrintExercise.setMethodName("main");
        practicalPrintExercise.setPrintValidator("word");
        practicalPrintExercise.setMustMatch(false);
        practicalPrintExercise.setCode(
                "public class Sample {\n" +
                        "   public void main(String[] args) {\n" +
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
        if (currentExercise != null) {
            compileCode();
            outputTextArea.setText(RuntimeUtil.CONSOLE_OUTPUT.toString());
        }
    }

    @FXML
    private void clearOutput() {

        try {
            this.outputTextArea.clear();
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void submit() {
        //TODO evaluate and get score and push to database
        compileCode();

        System.out.println("output: " + RuntimeUtil.CONSOLE_OUTPUT.toString());
        System.out.println("Correct: " + currentExercise.evaluate(RuntimeUtil.CONSOLE_OUTPUT.toString()));
    }

    public void view(PracticalPrintExercise practicalPrintExercise) {
        currentExercise = practicalPrintExercise;

        titleLabel.setText(practicalPrintExercise.getTitle());
        instructionsTextArea.setText(practicalPrintExercise.getInstructions());

        codeTextArea.setText(practicalPrintExercise.getCode());
        practicalPrintExercise.codeProperty().bind(codeTextArea.textProperty());


    }

    private void compileCode() {
        try {
            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STRING_STREAM);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);
            RuntimeUtil.compile(currentExercise);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STREAM);
        }
    }
}
