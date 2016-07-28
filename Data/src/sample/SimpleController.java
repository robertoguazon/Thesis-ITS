package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.ActionMethod;
import org.datafx.controller.flow.action.ActionTrigger;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 27/07/2016.
 */

@FXMLController("SimpleView.fxml")
public class SimpleController {

    @FXML
    private Label resultLabel;

    @FXML
    @ActionTrigger("myAction")
    private Button actionButton;

    private int clickCount = 0;

    @PostConstruct
    public void init() {
        resultLabel.setText("Button was clicked " + clickCount + " times");
    }

    @ActionMethod("myAction")
    public void onAction() {
        clickCount++;
        resultLabel.setText("Button was clicked " + clickCount + " times");
    }
}
