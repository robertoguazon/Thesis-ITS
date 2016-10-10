package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.HTMLEditor;
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
    @FXML private Label textLessonLabel;
    @FXML private WebView textLessonWebView;
    @FXML private VBox lessonsVBox;
    @FXML private VBox webviewVBox;
    @FXML private Button back;
    @FXML private Button exercise;
    @FXML private Hyperlink[] lesson;
    //@FXML private TextArea textLessonTextArea; //!got changed because of htmleditor
    VideoLessonViewerController vlc;
    PracticalPrintExerciseViewerController ppec;
    PracticalReturnExerciseViewerController prec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Agent.getLoggedUser() != null) {
            ArrayList<TextLesson> lessonsInModule = Agent.getLessonsInModule(Agent.getCurrentModule());
            lesson = new Hyperlink[lessonsInModule.size()];
            for (int i = 0; i < lesson.length; i++) {
                lesson[i] = new Hyperlink();
                lesson[i].setText(i==0?"Introduction":"Lesson" + i);
                lesson[i].setFont(Font.font("System", FontWeight.NORMAL, 16));
                final int finalI = i;
                lesson[i].setOnAction(event -> {
                    setTextLesson(lessonsInModule.get(finalI));
                    setExerciseButton("lesson" + finalI);
                });
                if (Agent.getLoggedUser().getCurrentModuleId().compareTo(Agent.getCurrentModule()) == 0) {
                    if (Agent.getLoggedUser().getCurrentLessonId().compareTo("lesson" + i) < 0) {
                        lesson[i].setDisable(true);
                    }
                }
            }
            lessonsVBox.getChildren().addAll(lesson);
        }
    }

    public void setExerciseButton(String string){
        if (!string.contains("lesson0")) {
            if (!webviewVBox.getChildren().contains(exercise)) {
                exercise = new Button("Exercise");
                exercise.setOnAction(event -> {
                    try {
                        handleNewWindowAction(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                webviewVBox.getChildren().add(exercise);
            }
        }else {
            if (webviewVBox.getChildren().contains(exercise)){
                webviewVBox.getChildren().remove(exercise);
            }
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
        //textLessonTextArea.setText(textLesson.getText()); //!got changed because of htmleditor
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
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void handleNewWindowAction(ActionEvent event) throws IOException {
        stage = new Stage();
        Parent root;
        if (event.getSource() == exercise){
            openExercise();
            Node vlNode = loadVideoLessonNode();
            Node peNode = loadPracticalExerciseNode();
            Node node = combine(vlNode, peNode);
            root = (Parent)node;
        }else {return;}

        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(exercise.getScene().getWindow());
        stage.showAndWait();
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
        FXMLLoader loader = new FXMLLoader();
        Node peNode;
        if (Agent.getExercise().getPracticalExercise() instanceof PracticalPrintExercise){
            loader.setLocation(getClass().getResource("../view/PracticalPrintExerciseViewer.fxml"));
            peNode = loader.load();
            ppec = loader.getController();
            //System.out.println(Agent.getExercise().getPracticalExercise());
            ppec.setPracticalPrintExercise((PracticalPrintExercise) Agent.getExercise().getPracticalExercise());
        }else if (Agent.getExercise().getPracticalExercise() instanceof PracticalReturnExercise){
            loader.setLocation(getClass().getResource("../view/PracticalReturnExerciseViewer.fxml"));
            peNode = loader.load();
            prec = loader.getController();
            //System.out.println(Agent.getExercise().getPracticalExercise());
            prec.setPracticalReturnExercise((PracticalReturnExercise) Agent.getExercise().getPracticalExercise());
        }else {return null;}
        return peNode;
    }

    private void openExercise() throws IOException {
        String[] lessonTags = Agent.getLesson().getTagsString().split(",");
        Agent.getExercise(lessonTags[0], lessonTags[1]);
    }

    private static Node combine(Node left, Node right) {
        BorderPane borderPane = new BorderPane();
        SplitPane splitPane = new SplitPane();
        borderPane.setCenter(splitPane);

        Pane leftPaneContainer = new Pane(left);
        Pane rightPaneContainer = new Pane(right);

        if (left instanceof Pane && right instanceof Pane) {
            Pane leftPane = (Pane) left;
            Pane rightPane = (Pane) right;

            leftPane.prefWidthProperty().bind(leftPaneContainer.widthProperty());
            leftPane.prefHeightProperty().bind(leftPaneContainer.heightProperty());
        }

        splitPane.getItems().addAll(leftPaneContainer,rightPaneContainer);
        return borderPane;
    }

    private void closeProgram(){

        Boolean answer = ConfirmBox.display("Confirm Exit", "Are you sure you want to close the exercise?");
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
