package com.westlyf.controller;

import com.westlyf.database.ExerciseDatabase;
import com.westlyf.domain.exercise.practical.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class PracticalExerciseMakerController implements Initializable {

    @FXML private TextField titleTextField;
    @FXML private Button addTagButton;
    @FXML private FlowPane tagFlowPane;

    @FXML private ToggleGroup practicalTypeGroup;
    @FXML private RadioButton printRadioButton;
    @FXML private RadioButton returnRadioButton;
    @FXML private Button okButton;
    @FXML private BorderPane containerBorderPane;

    @FXML private TextField classNameTextField;
    @FXML private TextField methodNameTextField;

    @FXML private TextArea instructionsTextArea;
    @FXML private Button clearInstructionsButton;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;

    @FXML private Button createButton;

    @FXML private TextArea explanationTextArea;
    @FXML private Button clearExplanationButton;

    //code checkerse
    @FXML private Button addCStringButton;
    @FXML private VBox cGroupVBox;
    private CGroup cGroup;

    private ArrayList<StringProperty> tags;
    private PracticalPrintExercise practicalPrintExercise;
    private PracticalReturnExercise practicalReturnExercise;
    private PracticalExercise practicalExercise;

    private Stage stage;
    private BorderPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addTagButton.setDisable(true);
        createButton.setDisable(true);
        printRadioButton.setSelected(true);

        tags = new ArrayList<>();
        practicalPrintExercise = new PracticalPrintExercise();
        practicalReturnExercise = new PracticalReturnExercise();

        cGroup = new CGroup();

        //binds
        practicalPrintExercise.classNameProperty().bind(classNameTextField.textProperty());
        practicalPrintExercise.methodNameProperty().bind(methodNameTextField.textProperty());
        practicalPrintExercise.instructionsProperty().bind(instructionsTextArea.textProperty());
        practicalPrintExercise.codeProperty().bind(codeTextArea.textProperty());
        practicalPrintExercise.explanationProperty().bind(explanationTextArea.textProperty());

        practicalReturnExercise.classNameProperty().bind(classNameTextField.textProperty());
        practicalReturnExercise.methodNameProperty().bind(methodNameTextField.textProperty());
        practicalReturnExercise.instructionsProperty().bind(instructionsTextArea.textProperty());
        practicalReturnExercise.codeProperty().bind(codeTextArea.textProperty());
        practicalReturnExercise.explanationProperty().bind(explanationTextArea.textProperty());
    }

    @FXML
    private void addCString() {
        VBox cStringVBox  = new VBox();
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        //to be put in cStringVBox;

        Label cStringLabel = new Label("CSTRING:");

        HBox tipHBox = new HBox();
        Label tipLabel = new Label("Tip:");
        TextArea tipTextArea = new TextArea();
        tipHBox.getChildren().addAll(tipLabel,tipTextArea);

        HBox cStringHBox = new HBox();
        TextField cStringField = new TextField();
        Button removeCStringButton = new Button("Remove cString");
        Button addEquivalentButton = new Button("Add equivalent");
        cStringHBox.getChildren().addAll(cStringField,removeCStringButton);

        StringProperty orig = new SimpleStringProperty();
        orig.bind(cStringField.textProperty());
        CString cString = new CString(orig);
        cString.tipProperty().bind(tipTextArea.textProperty()); //tipProperty

        addEquivalentButton.setOnAction(event -> {
            TextField eTextField = new TextField();
            Button xButton = new Button("x");
            StringProperty equivalent = new SimpleStringProperty();
            equivalent.bind(eTextField.textProperty());
            cString.addEquivalent(equivalent);

            HBox eHBox = new HBox();
            eHBox.getChildren().addAll(eTextField,xButton);
            cStringVBox.getChildren().add(eHBox);

            xButton.setOnAction(event2 -> {
                cString.removeEquivalents(equivalent);
                cStringVBox.requestFocus();
                cStringVBox.getChildren().removeAll(eHBox);
            });
        });


        removeCStringButton.setOnAction(event -> {
            cGroupVBox.getChildren().remove(cStringVBox);
            cGroupVBox.requestFocus();
            cGroup.removeCString(cString);
        });


        cGroup.addCString(cString);

        //practicalExercise.setCGroup(cGroup);

        cStringVBox.getChildren().addAll(separator,cStringLabel,tipHBox,cStringHBox,addEquivalentButton);
        cGroupVBox.getChildren().add(cStringVBox);

        System.out.println("CGroup size: " + cGroup.getSize());
    }

    @FXML
    private void clearExplanation() {
        this.explanationTextArea.clear();
    }

    @FXML
    private void addTag() {
        final TextField tagTextField = new TextField();
        tagTextField.setMinSize(100,20);
        tagTextField.setMaxSize(100,20);

        //add to string property then add to tag lists
        final StringProperty stringProperty = new SimpleStringProperty();
        stringProperty.bind(tagTextField.textProperty());
        tags.add(stringProperty);
        practicalPrintExercise.setTags(tags);
        practicalReturnExercise.setTags(tags);

        final Button removeButton = new Button("x");
        removeButton.setFocusTraversable(false);
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tagFlowPane.getChildren().remove(tagTextField);
                tagFlowPane.getChildren().remove(removeButton);
                tags.remove(stringProperty);
            }
        });

        tagFlowPane.getChildren().add(tagTextField);
        tagFlowPane.getChildren().add(removeButton);
    }

    @FXML
    private void ok() {
        if (addTagButton.isDisabled()) {
            addTagButton.setDisable(false);
        }

        if (createButton.isDisabled()) {
            createButton.setDisable(false);
        }

        containerBorderPane.getChildren().clear();

        if (printRadioButton.isSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PracticalPrintExerciseMaker.fxml"));
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PracticalPrintExerciseMakerController controller = loader.getController();

            //binds
            practicalPrintExercise.titleProperty().bind(titleTextField.textProperty());
            controller.bindPrintValidator(practicalPrintExercise.printValidatorProperty());
            controller.bindMustMatch(practicalPrintExercise.mustMatchProperty());

             practicalExercise = practicalPrintExercise;
             practicalExercise.setCGroup(cGroup);

            methodNameTextField.setText("main");
            if (!methodNameTextField.isDisabled()) {
                methodNameTextField.setDisable(true);
            }

        } else if (returnRadioButton.isSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PracticalReturnExerciseMaker.fxml"));

            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PracticalReturnExerciseMakerController controller = loader.getController();

            practicalReturnExercise.titleProperty().bind(titleTextField.textProperty());
            controller.setPracticalReturnExercise(practicalReturnExercise);
            //TODO controller bind

            practicalExercise = practicalReturnExercise;
            practicalExercise.setCGroup(cGroup);

            if (methodNameTextField.isDisabled()) {
                methodNameTextField.setDisable(false);
            }
        }

        try {
            containerBorderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }



        //TODO
    }

    @FXML
    private void clearInstructions() {
        instructionsTextArea.clear();
    }

    @FXML
    private void clearCode() {
        codeTextArea.clear();
    }

    @FXML
    private void create() {
        //TODO

        //test
        System.out.println(practicalExercise.getCGroup());

        if (practicalExercise instanceof PracticalPrintExercise) {
            PracticalPrintExercise practicalPrintExercise = ((PracticalPrintExercise)practicalExercise).clone();

            practicalPrintExercise.makeID();
            if (practicalPrintExercise != null && practicalPrintExercise.isValid()) {

                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, practicalPrintExercise.check());
                confirmation.setTitle("CONFIRM");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        ExerciseDatabase.storeData(practicalPrintExercise);
                        System.out.println("data was pushed to database");
                    }
                });

            } else {
                Alert error = new Alert(Alert.AlertType.ERROR, practicalPrintExercise.check());
                error.setTitle("INVALID");
                error.show();
            }

        } else if (practicalExercise instanceof PracticalReturnExercise) {
            PracticalReturnExercise practicalReturnExercise = ((PracticalReturnExercise)practicalExercise).clone();
            practicalReturnExercise.makeID();

            if (practicalReturnExercise != null && practicalReturnExercise.isValid()) {

                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, practicalReturnExercise.check());
                confirmation.setTitle("CONFIRM");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        ExerciseDatabase.storeData(practicalReturnExercise);
                        System.out.println("data was pushed to database");
                    }
                });

            } else {
                Alert error = new Alert(Alert.AlertType.ERROR, practicalReturnExercise.check());
                error.setTitle("INVALID");
                error.show();
            }

        }

        //end- test
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
