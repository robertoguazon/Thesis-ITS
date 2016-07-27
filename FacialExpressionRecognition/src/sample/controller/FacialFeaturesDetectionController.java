package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 27/07/2016.
 */
public class FacialFeaturesDetectionController implements Initializable {

    @FXML
    private ImageView originalImage;
    @FXML
    private ImageView detectedImage;
    @FXML
    private FlowPane featuresHolder;
    @FXML
    private Button clearButton;
    @FXML
    private Button loadImageButton;
    @FXML
    private Button detectButton;
    @FXML
    private Button getFeaturesButton;

    private FileChooser fileChooser;
    private File previousDirectory;
    private String currentPath;
    private ArrayList<Mat> detectedFaces;
    private Stage stage;

    private CascadeClassifier faceDetector;
    private CascadeClassifier eyeDetector;
    private CascadeClassifier mouthDetector;
    private CascadeClassifier noseDetector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detectedFaces = new ArrayList<>();

        detectButton.setDisable(true);
        getFeaturesButton.setDisable(true);

        faceDetector = new CascadeClassifier("resources/haarcascades/haarcascade_frontalface_alt_tree.xml");
        eyeDetector = new CascadeClassifier("resources/haarcascades/haarcascade_eye.xml");
        mouthDetector = new CascadeClassifier("resources/haarcascades/haarcascade_mcs_mouth.xml");
        noseDetector = new CascadeClassifier("resources/haarcascades/haarcascade_mcs_nose.xml");
        fileChooser = new FileChooser();
    }

    @FXML
    private void clear() {
        featuresHolder.getChildren().clear();
    }

    @FXML
    private void loadImage() {
        File file = chooseFile();
        detectedFaces.clear();

        if (file != null) {
            currentPath = file.getAbsolutePath();
            Image image = new Image("File:" + currentPath);
            originalImage.setImage(image);

            detectButton.setDisable(false);

            if (detectedFaces.size() == 0) {
                getFeaturesButton.setDisable(true);
            }

        } else {
            currentPath = null;

            detectButton.setDisable(true);
            getFeaturesButton.setDisable(true);
            originalImage.setImage(null);
            detectedImage.setImage(null);

        }

    }

    @FXML
    private void detect() {
        Mat image = Imgcodecs.imread(currentPath);
        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(image,faceDetections);
        System.out.println(String.format("Detected %d faces", faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {

            Mat imageCopy = new Mat();
            image.copyTo(imageCopy);
            detectedFaces.add(imageCopy.submat(rect));

            Imgproc.rectangle(image, rect.tl(),rect.br(),new Scalar(0,0,255),5);
        }

        if (faceDetections.toArray().length > 0) {
            detectedImage.setImage(mat2Image(image));
            getFeaturesButton.setDisable(false);
        } else {
            detectedImage.setImage(null);
            getFeaturesButton.setDisable(true);
        }
    }

    @FXML
    private void getFeatures() {
        getFaces(detectedFaces);
        getEyes(detectedFaces);
        getMouths(detectedFaces);
        getNoses(detectedFaces);
    }

    public void getFaces(ArrayList<Mat> detectedFaces) {
        for (Mat mat : detectedFaces) {
            ImageView faceImage = new ImageView(mat2Image(mat));
            featuresHolder.getChildren().add(faceImage);
        }
    }

    public void getEyes(ArrayList<Mat> detectedFaces) {
        for (Mat mat : detectedFaces) {

            MatOfRect eyeDetections = new MatOfRect();
            eyeDetector.detectMultiScale(mat,eyeDetections);

            System.out.println(String.format("Detected %d eyes", eyeDetections.toArray().length));

            for (Rect rect : eyeDetections.toArray()) {
                Mat eyes = mat.submat(rect);
                ImageView eyesImage = new ImageView(mat2Image(eyes));
                featuresHolder.getChildren().add(eyesImage);
            }
        }
    }

    public void getMouths(ArrayList<Mat> detectedFaces) {
        for (Mat mat : detectedFaces) {

            MatOfRect mouthDetections = new MatOfRect();
            mouthDetector.detectMultiScale(mat,mouthDetections);

            System.out.println(String.format("Detected %d mouths", mouthDetections.toArray().length));

            for (Rect rect : mouthDetections.toArray()) {
                Mat mouth = mat.submat(rect);
                ImageView mouthImage = new ImageView(mat2Image(mouth));
                featuresHolder.getChildren().add(mouthImage);
            }
        }
    }

    public void getNoses(ArrayList<Mat> detectedFaces) {
        for (Mat mat : detectedFaces) {

            MatOfRect noseDetections = new MatOfRect();
            noseDetector.detectMultiScale(mat,noseDetections);

            System.out.println(String.format("Detected %d noses", noseDetections.toArray().length));

            for (Rect rect :noseDetections.toArray()) {
                Mat nose = mat.submat(rect);
                ImageView noseImage = new ImageView(mat2Image(nose));
                featuresHolder.getChildren().add(noseImage);
            }
        }
    }

    public File chooseFile() {
        fileChooser.setTitle("Choose Image");

        if (previousDirectory != null) {
            fileChooser.setInitialDirectory(previousDirectory);
        }

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.bmp", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            previousDirectory = selectedFile.getParentFile();
        }

        return selectedFile;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Image mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame,buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
}
