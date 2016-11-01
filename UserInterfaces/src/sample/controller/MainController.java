package sample.controller;

import com.sun.org.apache.bcel.internal.generic.LADD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends ControllerManager implements Initializable{

    @FXML private Hyperlink loadProfile;
    @FXML private Hyperlink newProfile;
    @FXML private Hyperlink settings;
    @FXML private Hyperlink about;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML private void handleAction(ActionEvent event){
        if (event.getSource() == loadProfile){changeScene("../view/loadprofile.fxml");}
        else if (event.getSource() == newProfile){changeScene("../view/newprofile.fxml");}
        else if (event.getSource() == settings){newChildWindow("../view/settings.fxml", "Settings");}
        else if (event.getSource() == about){newChildWindow("../view/about.fxml", "About");}
        else {return;}
    }
}
