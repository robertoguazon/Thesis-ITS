package com.westlyf.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Created by robertoguazon on 06/08/2016.
 */
public class FileUtils {

    public static final String PATH_TO_VIDEOS = "resources\\videos";
    public static final CopyOption[] DEFAULT_COPY_OPTIONS = new CopyOption[] {
            StandardCopyOption.COPY_ATTRIBUTES,
            StandardCopyOption.REPLACE_EXISTING
    };


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

    public static String pathTo(String filepath, String to) {
        return pathTo(new File(filepath), new File(to));
    }

    public static String pathTo(File filepath, File to) {
        if (!filepath.isFile()) return null;

        String filename = filepath.getName();
        return to + "\\" + filename;
    }

    public static String pathToVideo(String from) {
        return pathTo(from, PATH_TO_VIDEOS);
    }

    public static String pathToVideo(File from) {
        return pathTo(from, new File(PATH_TO_VIDEOS));
    }

    public static String pathTo(String filepath, String to, String id) {
        return pathTo(new File(filepath), new File(to), id);
    }


    public static String pathTo(File filepath, File to, String id) {
        if (!filepath.isFile()) return null;

        String extension = getFileExtension(filepath);
        return to + "\\" + id + extension;
    }

    public static String pathToVideo(String from, String id) {
        return pathTo(from, PATH_TO_VIDEOS, id);
    }

    public static String pathToVideo(File from, String id) {
        return pathTo(from, new File(PATH_TO_VIDEOS), id);
    }

    public static boolean copyFileTo(Path source, Path target) {
        try {
            Files.copy(source, target, DEFAULT_COPY_OPTIONS);

        } catch (IOException e) {
            System.err.format("Unable to copy: %s: %s%n", source, e);
            return false;
        }

        return true;
    }

    public static boolean copyVideoFileTo(File source, String id) {
        String name = source.getName();
        String extension = getFileExtension(source);
        String dest = PATH_TO_VIDEOS + "\\" + id + extension;
        return copyFileTo(source.toPath(), Paths.get(dest));
    }

    public static boolean copyVideoFileTo(File source) {
        String name = source.getName();
        String dest = PATH_TO_VIDEOS + "\\" + name;
        return copyFileTo(source.toPath(), Paths.get(dest));
    }

    public static boolean copyVideoFileTo(String source, String id) {
        return copyVideoFileTo(new File(source), id);
    }

    public static boolean copyVideoFileTo(String source) {
        return copyVideoFileTo(new File(source));
    }

    public static boolean copyVideoFileTo(Path source, String id) {
        return copyVideoFileTo(source.toFile(), id);
    }

    public static boolean copyVideoFileTo(Path source) {
        return copyVideoFileTo(source.toFile());
    }

    //from - http://www.journaldev.com/842/how-to-get-file-extension-in-java
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

}
