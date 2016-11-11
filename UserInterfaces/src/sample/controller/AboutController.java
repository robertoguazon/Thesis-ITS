package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 11/11/2016.
 */
public class AboutController implements Initializable{

    @FXML private TextArea aboutTextArea;
    private String information = "" +
            "Software Information:\n" +
            "\n" +
            "\n" +
            "Features:\n" +
            "\n" +
            "\n" +
            "Developers:\n" +
            "Borais, Alexavier\n" +
            "Francisco, Yves Rupert\n" +
            "Guazon, John Robert\n" +
            "Mabasa, Ryan Christopher";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutTextArea.setText(information);
    }
}
