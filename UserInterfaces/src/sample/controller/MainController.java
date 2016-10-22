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

public class MainController implements Initializable{

    @FXML private Hyperlink loadProfile;
    @FXML private Hyperlink newProfile;
    @FXML private Hyperlink settings;
    @FXML private Hyperlink about;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        if (event.getSource() == loadProfile){
            scene = loadProfile.getScene();
            stage = (Stage)scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/loadprofile.fxml"));
        }else if (event.getSource() == newProfile){
            scene = newProfile.getScene();
            stage = (Stage)scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/newprofile.fxml"));
        }else {return;}
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleNewWindowAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = null;
        Parent root = null;
        if (event.getSource() == settings){
//            scene = settings.getScene();
//            root = FXMLLoader.load(getClass().getResource("../view/settings.fxml"));
        }else if (event.getSource() == about){
//            scene = about.getScene();
//            root = FXMLLoader.load(getClass().getResource("../view/about.fxml"));
        }else {return;}
        scene.setRoot(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settings.getScene().getWindow());
        stage.showAndWait();
    }
}
