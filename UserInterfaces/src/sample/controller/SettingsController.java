package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sample.model.AlertBox;
import sample.model.FileUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/17/2016.
 */
public class SettingsController extends ControllerManager implements Initializable{

    @FXML private RadioButton darkThemeRadioButton;
    @FXML private RadioButton lightThemeRadioButton;
    @FXML private ToggleGroup themeToggleGroup;
    @FXML private Button apply;

    private final String darkThemePath = "sample/style/css/dark.css";
    private final String lightThemePath = "sample/style/css/light.css";

    private FileUtil fileUtil = new FileUtil();
    private String stylePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeToToggleButton();
        stylePath = fileUtil.readFile();
        if (stylePath.contains("dark")){
            darkThemeRadioButton.setSelected(true);
        }else if (stylePath.contains("light")){
            lightThemeRadioButton.setSelected(true);
        }
/*
        themeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (themeToggleGroup.getSelectedToggle() != null){
                    scene.getStylesheets().clear();
                    if (themeToggleGroup.getSelectedToggle() == darkThemeRadioButton){
                        scene.getStylesheets().addAll(darkThemePath);
                    }else if (themeToggleGroup.getSelectedToggle() == lightThemeRadioButton){
                        scene.getStylesheets().addAll(lightThemePath);
                    }else return;
                }
            }
        });
*/
    }

    @FXML
    public void handleAction(ActionEvent event){
        if (event.getSource() == apply){
            if (themeToggleGroup.getSelectedToggle() != null){
                scene.getStylesheets().clear();
                child.getScene().getStylesheets().clear();
                if (darkThemeRadioButton.isSelected()){
                    scene.getStylesheets().addAll(darkThemePath);
                    child.getScene().getStylesheets().addAll(darkThemePath);
                    fileUtil.writeFile(darkThemePath);
                }else if (lightThemeRadioButton.isSelected()){
                    scene.getStylesheets().addAll(lightThemePath);
                    child.getScene().getStylesheets().addAll(lightThemePath);
                    fileUtil.writeFile(lightThemePath);
                }
            }
            AlertBox.display("Apply", "Changes Applied!", "Click \"ok\" to close this window");
        }
    }

    private void changeToToggleButton(){
        darkThemeRadioButton.getStyleClass().remove("radio-button");
        darkThemeRadioButton.getStyleClass().add("toggle-button");
        lightThemeRadioButton.getStyleClass().remove("radio-button");
        lightThemeRadioButton.getStyleClass().add("toggle-button");
    }
}
