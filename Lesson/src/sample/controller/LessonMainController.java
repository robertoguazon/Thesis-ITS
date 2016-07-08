package sample.controller;

import com.westlyf.domain.lesson.*;
import com.westlyf.domain.util.LessonUtil;
import com.westlyf.domain.util.TreeUtil;
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
    @FXML private TextArea lessonTextArea;
    @FXML private AnchorPane lessonAnchorPane;

    private ArrayList<Lesson> lessons;

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

        lessons = new ArrayList<Lesson>();
        ArrayList<String> sampleTags = new ArrayList<>();
        sampleTags.add("master");
        sampleTags.add("slave");
        lessons.add(LessonFactory.createTextLesson(
                "Each variable in Java has a specific type, which determines the size and" +
                " layout of the variable's memory; the range of values that can be stored within that memory; and the " +
                "set of operations that can be applied to the variable. You must declare all variables befor" +
                "e they can be used.  " +
                "\n\n\n" +
                "    private TextArea textArea;\n" +
                "    private ScrollPane textAreaScrollPane;\n" +
                "    private Region textAreaContent;\n" +
                "\n" +
                "    protected TextAreaListener(TextArea textArea) {\n" +
                "        this.textArea = textArea;\n" +
                "        textArea.skinProperty().addListener(this);\n" +
                "        textArea.widthProperty().addListener(this);\n" +
                "        textArea.heightProperty().addListener(this);\n" +
                "    }\n", sampleTags));

        lessonTextArea.setWrapText(true);
        lessonTextArea.setEditable(false);

        subLevel_1_1.setTags(sampleTags);

        lessonTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Level>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Level>> observable, TreeItem<Level> oldValue, TreeItem<Level> newValue) {
                TreeItem<Level> selectedLevelItem = (TreeItem<Level>) newValue;
                Level selectedLevel = selectedLevelItem.getValue();
                ArrayList<String> tags = selectedLevelItem.getValue().getTags();

                ArrayList<Lesson> selectedLessons = LessonUtil.findLessons(selectedLevel,lessons);
                if (selectedLessons != null) {
                    if (selectedLessons.get(0) instanceof TextLesson) {
                        lessonTextArea.setVisible(true);
                        lessonTextArea.setText(((TextLesson) (selectedLessons.get(0))).getText());
                    } else if (selectedLessons.get(0) instanceof VideoLesson) {
                        lessonTextArea.setVisible(false);
                        /*lessonAnchorPane.getChildren().
                            NEED TO BE FIXED *************
                         */
                    }
                } else {
                    lessonTextArea.setText("");
                }
            }
        });

    }

}
