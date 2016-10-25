package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.TouchPoint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.naming.InitialContext;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 24/10/2016.
 */
public class URLViewerController implements Initializable {

    @FXML private TextField urlTextField;
    @FXML private Button goButton;
    @FXML private WebView urlWebView;

    private WebEngine webEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loading a html file
        //url = new File(path).toURI().toString();
        //webEngine.load(url);
        webEngine = urlWebView.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener((ov,os,ns) -> {
            if (ns == Worker.State.SUCCEEDED) {
                urlTextField.setText(webEngine.getLocation());
            } else if (ns == Worker.State.FAILED) {
                load(searchGoogle(urlTextField.getText()));
            }
        });
    }

    @FXML
    private void go() {
        String text = urlTextField.getText();
        if (text.contains(" ") || !text.contains(".")) {
            text = searchGoogle(text);
        } else {
            text = goToURL(text);
        }
       load(text);
    }

    private String searchGoogle(String s) {
        String google = "http://google.com/search?q=" + s.replaceAll("\\s{2,}", "+").trim();

        return google;
    }

    private String goToURL(String url) {
        if (!(url.contains("http://") || (url.contains("http:\\\\")))) {
            url = "http://" + url;
        }

        return url;
    }

    private void load(String text) {
        System.out.println(text);
        webEngine.load(text);
    }
}
