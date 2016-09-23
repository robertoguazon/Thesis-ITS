package sample.controller;

import com.westlyf.controller.ControllerType;
import com.westlyf.controller.Controllers;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.lesson.*;
import com.westlyf.domain.util.LessonUtil;
import com.westlyf.domain.util.TreeUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/06/2016.
 */
public class LessonMainController implements Initializable {

    @FXML private TreeView<Level> lessonTreeView;
    @FXML private AnchorPane lessonAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image lockedImage = new Image("File:resources/images/locked_16.png");
        Image unlockedImage =new Image("File:resources/images/unlocked_16.png");
        Node lockedIcon = new ImageView(lockedImage);
        Node unlockedIcon = new ImageView(unlockedImage);

        Level level_1 = LessonFactory.createLevel("1","Variables and such");
        Level subLevel_1_1 = LessonFactory.createLevel("1.1", "Data types");
        Level subLevel_1_2 = LessonFactory.createLevel("1.2", "String data types");
        Level level_2 = LessonFactory.createLevel("2","Methods / functions");

        level_1.addSubLevel(subLevel_1_1);
        level_1.addSubLevel(subLevel_1_2);

        ArrayList<Level> levels = new ArrayList<>();
        levels.add(level_1);
        levels.add(level_2);

        Level rootLevel = new Level();
        rootLevel.addSubLevels(level_1,level_2);

        TreeItem<Level> root = TreeUtil.createTree(rootLevel);

        root.setExpanded(true);
        lessonTreeView.setRoot(root);
        lessonTreeView.setShowRoot(false);

        subLevel_1_1.addTag("test");
        subLevel_1_1.addTag("sample");

        subLevel_1_2.addTag("asdad");
        subLevel_1_2.addTag("asdad");


        Controllers.loadAll(); //TODO - delete and put on before start
        lessonTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Level>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Level>> observable, TreeItem<Level> oldValue, TreeItem<Level> newValue) {

                //TODO -fix this one must check user or agent
                lessonAnchorPane.getChildren().clear();
                if (true) { //TODO - put check if unlocked or not
                    TreeItem<Level> selectedLevelItem = (TreeItem<Level>) newValue;
                    Level selectedLevel = selectedLevelItem.getValue();
                    String tags = selectedLevelItem.getValue().getTagsString();

                    //TODO - replace with agent this is just a test
                    ArrayList<TextLesson> textLessons = LessonDatabase.getTextLessonsUsingTagsExactly(tags);
                    Controllers.view(ControllerType.TEXT_LESSON_VIEWER,lessonAnchorPane,textLessons.get(0));
                }
            }
        });

    }

}
