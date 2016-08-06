package com.westlyf.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by robertoguazon on 06/08/2016.
 */
public class FileUtils {

    public static File prevDirectory = null;
    public static FileChooser fileChooser = new FileChooser();

    public static File chooseFile(Stage stage) {
        fileChooser.setTitle("select a video file");


        if (prevDirectory != null) {
            fileChooser.setInitialDirectory(prevDirectory);
        }

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video files",
                "*.avi", "*.mpg", "*.mp4", "*.wmv", "*.mkv"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            prevDirectory = selectedFile.getParentFile();
        }

        return selectedFile;
    }

}
