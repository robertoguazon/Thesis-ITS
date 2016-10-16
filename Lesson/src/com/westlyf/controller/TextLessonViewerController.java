package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.lesson.TextLesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.ConfirmBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class TextLessonViewerController implements Initializable {

    Stage stage;
    @FXML private BorderPane pane;
    @FXML private Label textLessonLabel;
    @FXML private WebView textLessonWebView;
    @FXML private Button back;
    @FXML private Button exerciseButton;
    @FXML private ListView lessonsListView;

    private ArrayList<TextLesson> lessonsInModule;
    private VideoLessonViewerController vlc;
    private PracticalPrintExerciseViewerController ppec;
    private PracticalReturnExerciseViewerController prec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Agent.getLoggedUser() != null) {
            lessonsInModule = Agent.getLessonsInModule(Agent.getCurrentModule());
            for (int i = 0; i < lessonsInModule.size(); i++) {
                lessonsListView.getItems().add("Lesson " + i);
            }
            lessonsListView.setOnMouseClicked(event -> openLesson());
        }
    }

    public void setExerciseButton(String string){
        if (!string.contains("0")) {
            exerciseButton.setText("Exercise");
            exerciseButton.setOnAction(event -> {
                try {
                    handleNewWindowAction(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }else {
            exerciseButton.setText("Next Lesson");
            exerciseButton.setOnAction(event -> {
                setTextLesson(lessonsInModule.get(1));
                setExerciseButton("1");
            });
        }
    }

    public void setTextLesson(TextLesson textLesson) {
        Agent.setLesson(textLesson);
        textLessonLabel.setText(textLesson.getTitle());
        String st = textLesson.getText();
        if(st.contains("contenteditable=\"true\"")){
            st=st.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
        }
        textLessonWebView.getEngine().loadContent(st);
    }

    @FXML
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == back){
            Agent.clearLessonsInModule();
            stage = (Stage)back.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../../../sample/view/modules.fxml"));
        }
        else {return;}
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(pane.getScene().getStylesheets());
        stage.setScene(scene);
        stage.show();
    }

    private void handleNewWindowAction(ActionEvent event) throws IOException {
        stage = new Stage();
        Parent root;
        if (event.getSource() == exerciseButton){
            openExercise();
            Node vlNode = loadVideoLessonNode();
            Node peNode = loadPracticalExerciseNode();
            Node node = combine(vlNode, peNode);
            root = (Parent)node;
        }else {return;}

        stage.setOnCloseRequest(e -> {
            e.consume();
            closeExercise();
        });
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(exerciseButton.getScene().getWindow());
        stage.showAndWait();
    }

    private void openLesson(){
        String str = lessonsListView.getSelectionModel().getSelectedItem().toString();
        int i = Integer.parseInt(String.valueOf(str.charAt(str.length()-1)));
        setTextLesson(lessonsInModule.get(i));
        setExerciseButton(str);
    }

    private void openExercise() throws IOException {
        String[] lessonTags = Agent.getLesson().getTagsString().split(",");
        Agent.loadExercise(lessonTags[0], lessonTags[1]);
    }

    private Node loadVideoLessonNode() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/VideoLessonViewer.fxml"));
        Node vlNode = loader.load();
        vlc = loader.getController();
        //System.out.println(Agent.getExercise().getVideoLesson());
        vlc.setVideoLesson(Agent.getExercise().getVideoLesson());
        return vlNode;
    }

    private Node loadPracticalExerciseNode() throws IOException {
        PracticalExercise practicalExercise = Agent.getExercise().getPracticalExercise();
        FXMLLoader loader = new FXMLLoader();
        Node peNode;
        if (practicalExercise instanceof PracticalPrintExercise){
            loader.setLocation(getClass().getResource("../view/PracticalPrintExerciseViewer.fxml"));
            peNode = loader.load();
            ppec = loader.getController();
            //System.out.println(Agent.getExercise().getPracticalExercise());
            ppec.setPracticalPrintExercise((PracticalPrintExercise) Agent.getExercise().getPracticalExercise());
        }else if (practicalExercise instanceof PracticalReturnExercise){
            loader.setLocation(getClass().getResource("../view/PracticalReturnExerciseViewer.fxml"));
            peNode = loader.load();
            prec = loader.getController();
            //System.out.println(Agent.getExercise().getPracticalExercise());
            prec.setPracticalReturnExercise((PracticalReturnExercise) Agent.getExercise().getPracticalExercise());
        }else {return null;}
        return peNode;
    }

    private Node combine(Node left, Node right) {
        BorderPane borderPane = new BorderPane();
        SplitPane splitPane = new SplitPane();
        //splitPane.setPrefWidth(1120);
        //splitPane.setPrefHeight(560);
        borderPane.setCenter(splitPane);

        Pane leftPaneContainer = new Pane(left);
        leftPaneContainer.setMinWidth(560);
        leftPaneContainer.setMinHeight(480);
        Pane rightPaneContainer = new Pane(right);
        rightPaneContainer.setMinWidth(560);
        rightPaneContainer.setMinHeight(480);

        if (left instanceof Pane && right instanceof Pane) {
            Pane leftPane = (Pane) left;
            Pane rightPane = (Pane) right;

            leftPane.prefWidthProperty().bind(leftPaneContainer.widthProperty());
            leftPane.prefHeightProperty().bind(leftPaneContainer.heightProperty());
            rightPane.prefWidthProperty().bind(rightPaneContainer.widthProperty());
            rightPane.prefHeightProperty().bind(rightPaneContainer.heightProperty());
        }

        splitPane.getItems().addAll(leftPaneContainer,rightPaneContainer);
        return borderPane;
    }

    private void closeExercise(){

        Boolean answer = ConfirmBox.display("Confirm Exit",
                "Are you sure you want to close the exercise?", "All changes will not be saved.");
        if (answer){
            if (vlc instanceof Disposable){
                ((Disposable)vlc).dispose();
                System.out.println("disposed");
                stage.close();
            }else {
                System.out.println("not disposed");
            }
        }
    }
}
