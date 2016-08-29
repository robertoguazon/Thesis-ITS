package sample.controller;

import com.westlyf.domain.exercise.practical.DataType;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class PracticalReturnExerciseMakerController implements Initializable {

    @FXML private Button addReturnValidatorButton;
    @FXML private HBox returnTypeHBox;

    @FXML private Button addParameterButton;
    @FXML private FlowPane parametersTypesFlowPane;

    @FXML private VBox practicalReturnVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        returnTypeHBox.getChildren().add(createDataTypeComboBox());
    }

    @FXML
    private void addReturnValidator() {
        //TODO -bind everything
        HBox hBox = new HBox();
        int size = parametersTypesFlowPane.getChildren().size() / 2; // divide by 2 because of the removeButtons
        TextField[] actualParams = createTextFields(size, "param");

        TextField expectedOutput = new TextField();
        expectedOutput.promptTextProperty().set("Output");

        Button removeButton = createRemoveButton("x", hBox,practicalReturnVBox);

        hBox.getChildren().addAll(actualParams);
        hBox.getChildren().add(expectedOutput);
        hBox.getChildren().add(removeButton);
        practicalReturnVBox.getChildren().add(hBox);
    }

    @FXML
    private void addParameter() {
        //TODO -bind
        ComboBox comboBox = createDataTypeComboBox();
        Button removeButton = createRemoveButton("x", comboBox, parametersTypesFlowPane);
        parametersTypesFlowPane.getChildren().addAll(comboBox, removeButton);
    }

    private ComboBox<DataType> createDataTypeComboBox() {
        //TODO -bind
        ComboBox<DataType> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                DataType.DOUBLE,
                DataType.INT,
                DataType.FLOAT,
                DataType.STRING,
                DataType.CHAR
        );
        return comboBox;
    }

    private Button createRemoveButton(String buttonName, final Node toRemove, final Pane from) {
        final Button removeButton = new Button(buttonName);
        removeButton.setFocusTraversable(false);

        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                from.getChildren().remove(toRemove);
                from.getChildren().remove(removeButton);
            }
        });

        return removeButton;
    }

    private TextField[] createTextFields(final int size, final String prompt) {
        TextField[] textFields = new TextField[size];
        for (int i = 0; i < size; i++) {
            textFields[i] = new TextField();
            textFields[i].promptTextProperty().set(prompt);
        }

        return textFields;
    }
}
