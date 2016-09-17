package sample.controller;

import com.westlyf.domain.exercise.practical.DataType;
import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnValidator;
import com.westlyf.utils.DataTypeUtil;
import com.westlyf.utils.RuntimeUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 30/08/2016.
 */
public class PracticalReturnExerciseViewerController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;


    @FXML private Button executeSampleInputButton;
    @FXML private FlowPane sampleInputFlowPane;

    @FXML private TextArea outputTextArea;
    @FXML private Button clearOutputButton;

    @FXML private Button submitButton;

    private PracticalReturnExercise currentExercise;
    private StringProperty[] parametersArray;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //test(); //check //TODO -delete
        test2(); //check //TODO -delete
    }

    @FXML
    private void clearCode() {
        this.codeTextArea.clear();
    }

    @FXML
    private void executeSampleInput() {
        clearOutput();
        if (currentExercise != null) {
            try {
                outputTextArea.setText(RuntimeUtil.compile(currentExercise,DataTypeUtil.toString(parametersArray)));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    private void clearOutput() {
        try {
            this.outputTextArea.clear();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void submit() {
        //TODO - fix
        if (compileCode()) {
            System.out.println("Correct: true");
        } else {
            System.out.println("Correct: false");
        }
    }

    private boolean compileCode() {
        try {
            return RuntimeUtil.compile(currentExercise);

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public void view(PracticalReturnExercise practicalReturnExercise) {
        currentExercise = practicalReturnExercise;

        titleLabel.setText(practicalReturnExercise.getTitle());
        instructionsTextArea.setText(practicalReturnExercise.getInstructions());

        codeTextArea.setText(practicalReturnExercise.getCode());
        practicalReturnExercise.codeProperty().bind(codeTextArea.textProperty());

        int parametersSize = currentExercise.getParametersSize();
        parametersArray = new StringProperty[parametersSize];
        for (int i = 0; i < parametersSize; i++) {
            TextField textField = new TextField();

            StringProperty stringProperty = new SimpleStringProperty();
            stringProperty.bind(textField.textProperty());
            parametersArray[i] = stringProperty;
            sampleInputFlowPane.getChildren().add(textField);
        }
    }

    private void test() {
        PracticalReturnExercise practicalReturnExercise = new PracticalReturnExercise();
        practicalReturnExercise.setTitle("sample practical exercise");
        practicalReturnExercise.addTag("sample");
        practicalReturnExercise.addTag("exercise");
        practicalReturnExercise.addTag("practical");

        practicalReturnExercise.setInstructions("make a method that accepts an int value doubles it and returns it as an int value");
        practicalReturnExercise.setClassName("Sample");
        practicalReturnExercise.setMethodName("run");

        practicalReturnExercise.setCode(
                "public class Sample {\n" +
                        "   public int run(int value) {\n" +
                        "       return value * 2;\n" +
                        "   }\n" +
                        "}"
        );

        practicalReturnExercise.addParameterType(DataType.INT);
        practicalReturnExercise.setReturnType(DataType.INT);

        PracticalReturnValidator p1 = new PracticalReturnValidator();
        p1.addInput("1",DataType.INT);
        p1.setExpectedReturn("2");

        PracticalReturnValidator p2 = new PracticalReturnValidator();
        p2.addInput("11",DataType.INT);
        p2.setExpectedReturn("22");

        practicalReturnExercise.addReturnValidator(p1);
        practicalReturnExercise.addReturnValidator(p2);

        view(practicalReturnExercise);
    }

    private void test2() {
        PracticalReturnExercise practicalReturnExercise = new PracticalReturnExercise();
        practicalReturnExercise.setTitle("sample practical exercise");
        practicalReturnExercise.addTag("sample");
        practicalReturnExercise.addTag("exercise");
        practicalReturnExercise.addTag("practical");

        practicalReturnExercise.setInstructions("make a method that accepts a string variable as parameter and returns the" +
                "last character");
        practicalReturnExercise.setClassName("Sample");
        practicalReturnExercise.setMethodName("run");

        practicalReturnExercise.setCode(
                "public class Sample {\n" +
                        "   public char run(String value) {\n" +
                        "       return value.charAt(value.length() - 1);\n" +
                        "   }\n" +
                        "}"
        );

        practicalReturnExercise.addParameterType(DataType.STRING);
        practicalReturnExercise.setReturnType(DataType.CHAR);

        PracticalReturnValidator p1 = new PracticalReturnValidator();
        p1.addInput("master",DataType.STRING);
        p1.setExpectedReturn("r");

        PracticalReturnValidator p2 = new PracticalReturnValidator();
        p2.addInput("books1",DataType.STRING);
        p2.setExpectedReturn("1");

        practicalReturnExercise.addReturnValidator(p1);
        practicalReturnExercise.addReturnValidator(p2);

        view(practicalReturnExercise);
    }
}
