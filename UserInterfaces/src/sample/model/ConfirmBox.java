package sample.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by Yves on 9/24/2016.
 */
public class ConfirmBox {

    private static Boolean answer;

    public static Boolean display(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            answer = true;
        } else {
            // ... user chose CANCEL or closed the dialog
            answer = false;
        }

        return answer;
        /*
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        Label header = new Label();
        header.setText(title);
        header.setFont(new Font("System", 22));

        Separator separator = new Separator();

        Label label = new Label();
        label.setText(message);
        label.setFont(new Font("System", 14));
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(event -> {
            answer = true;
            stage.close();
        });
        Button noButton = new Button("No");
        noButton.setOnAction(event -> {
            answer = false;
            stage.close();
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(noButton, yesButton);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(header, separator, label, hBox);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();

        return answer;
        */
    }
}
