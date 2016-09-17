package com.westlyf.controller;

import com.westlyf.domain.exercise.practical.DataType;
import com.westlyf.domain.exercise.practical.InputParameter;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
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

    private PracticalReturnExercise practicalReturnExercise;
    private ArrayList<ComboBox> parametersTypesComboBoxes;
    private ComboBox returnTypeComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        returnTypeComboBox = createDataTypeComboBox();
        returnTypeHBox.getChildren().add(returnTypeComboBox);
        parametersTypesComboBoxes = new ArrayList<>();
    }

    @FXML
    private void addReturnValidator() {
        //TODO -bind everything
        final HBox hBox = new HBox();
        int size = parametersTypesFlowPane.getChildren().size() / 2; // divide by 2 because of the removeButtons
        TextField[] actualParams = createTextFields(size, "param");

        TextField expectedOutput = new TextField();
        expectedOutput.promptTextProperty().set("Output");

        final Button removeButton = createButton("x");

        hBox.getChildren().addAll(actualParams);
        hBox.getChildren().add(expectedOutput);
        hBox.getChildren().add(removeButton);
        practicalReturnVBox.getChildren().add(hBox);

        final PracticalReturnValidator practicalReturnValidator = new PracticalReturnValidator();
        for (int i = 0; i < parametersTypesComboBoxes.size(); i++) {

            final InputParameter inputParameter = new InputParameter(actualParams[i].textProperty(),
                    (DataType)parametersTypesComboBoxes.get(i).getValue());
            practicalReturnValidator.addInput(inputParameter);

            parametersTypesComboBoxes.get(i).valueProperty().addListener(
                    new ChangeListener<DataType>() {

                        @Override
                        public void changed(ObservableValue<? extends DataType> observable, DataType oldValue, DataType newValue) {
                            practicalReturnValidator.setInputTypeOfInputParameter(inputParameter, newValue);
                        }
                    }
            );
        }
        practicalReturnValidator.expectedReturnProperty().bind(expectedOutput.textProperty());
        practicalReturnExercise.addReturnValidator(practicalReturnValidator);

        //action listeners
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                practicalReturnVBox.getChildren().remove(hBox);
                practicalReturnExercise.removeReturnValidator(practicalReturnValidator);
            }
        });

        //set parameters
        clearParameterTypes();
        setParametersTypes();
    }

    @FXML
    private void addParameter() {
        //TODO -bind
        final ComboBox comboBox = createDataTypeComboBox();
        final Button removeButton = createButton("x");
        parametersTypesFlowPane.getChildren().addAll(comboBox, removeButton);
        parametersTypesComboBoxes.add(comboBox);

        //action listeners
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parametersTypesFlowPane.getChildren().remove(comboBox);
                parametersTypesFlowPane.getChildren().remove(removeButton);
                parametersTypesComboBoxes.remove(comboBox);

            }
        });
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

    private Button createButton(String buttonName) {
        final Button removeButton = new Button(buttonName);
        removeButton.setFocusTraversable(false);

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

    public void setPracticalReturnExercise(final PracticalReturnExercise practicalReturnExercise) {
        this.practicalReturnExercise = practicalReturnExercise;

        returnTypeComboBox.valueProperty().addListener (
                new ChangeListener<DataType>() {
                    @Override
                    public void changed(ObservableValue<? extends DataType> observable, DataType oldValue, DataType newValue) {
                        practicalReturnExercise.setReturnType(newValue);
                    }

        });
    }

    private void setParametersTypes(ArrayList<ComboBox> parametersTypes) {
        for (int i = 0; i < parametersTypes.size(); i++) {
            practicalReturnExercise.addParameterType((DataType)parametersTypes.get(i).getValue());
        }
    }

    private void setParametersTypes() {
        setParametersTypes(parametersTypesComboBoxes);
    }

    private void clearParameterTypes() {
        practicalReturnExercise.clearParametersTypes();
    }
}
