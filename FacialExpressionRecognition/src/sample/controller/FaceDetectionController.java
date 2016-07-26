package sample.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by robertoguazon on 26/07/2016.
 */
public class FaceDetectionController implements Initializable {

    @FXML
    private Button cameraButton;
    @FXML
    private ImageView originalFrame;
    @FXML
    private CheckBox haarClassifier;
    @FXML
    private CheckBox lbpClassifier;

    private ScheduledExecutorService timer;
    private VideoCapture capture;
    private boolean cameraActive;

    private CascadeClassifier faceCascade;
    private int absoluteFaceSize;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.capture = new VideoCapture();
        this.faceCascade = new CascadeClassifier();
        this.absoluteFaceSize = 0;
    }

    @FXML
    private void startCamera() {
        originalFrame.setFitWidth(600);
        originalFrame.setPreserveRatio(true);

        if (!this.cameraActive) {
            this.haarClassifier.setDisable(true);
            this.lbpClassifier.setDisable(true);
            this.capture.open(0);

            if (this.capture.isOpened()) {
                this.cameraActive = true;
                Runnable frameGrabber = new Runnable() {
                    @Override
                    public void run() {
                        Image imageToShow = grabFrame();
                        originalFrame.setImage(imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber,0,33, TimeUnit.MILLISECONDS);

                this.cameraButton.setText("Stop Camera");
            } else {
                System.err.println("Failed to open the camera connection ...");
            }

        }  else {
            stopCamera();
        }
    }

    private Image grabFrame() {
        Image imageToShow = null;
        Mat frame = new Mat();

        if (this.capture.isOpened()) {
            try {
                this.capture.read(frame);
                if (!frame.empty()) {
                    detectAndDisplay(frame);
                    imageToShow = mat2Image(frame);
                }
            } catch (Exception e) {
                System.err.println("ERROR: " + e);
            }
        }

        return imageToShow;
    }

    private void detectAndDisplay(Mat frame) {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        Imgproc.cvtColor(frame,grayFrame,Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayFrame, grayFrame);

        if (this.absoluteFaceSize == 0) {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0) {
                this.absoluteFaceSize = Math.round(height * 0.2f);
            }
        }

        this.faceCascade.detectMultiScale(grayFrame, faces, 1.1,2,0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(this.absoluteFaceSize,this.absoluteFaceSize), new Size());

        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++) {
            Imgproc.rectangle(frame, facesArray[i].tl(),facesArray[i].br(),new Scalar(0,255,0),3);
        }
    }

    @FXML
    private void haarSelected(Event event) {
        if (this.lbpClassifier.isSelected()) {
            this.lbpClassifier.setSelected(false);
        }

        checkBoxSelection("resources/haarcascades/haarcascade_frontalface_alt.xml");
    }

    @FXML
    private void lbpSelected(Event event) {

        if (this.haarClassifier.isSelected()) {
            this.haarClassifier.setSelected(false);
        }

        checkBoxSelection("resources/lbpcascades/lbpcascade_frontalface.xml");
    }

    private void checkBoxSelection(String classifierPath) {
        this.faceCascade.load(classifierPath);
        this.cameraButton.setDisable(false);
    }

    private Image mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame,buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    public void stopCamera() {
        this.cameraActive = false;
        this.cameraButton.setText("Start Camera");
        this.haarClassifier.setDisable(false);
        this.lbpClassifier.setDisable(false);

        try {
            this.timer.shutdown();
            this.timer.awaitTermination(33,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
        }

        this.capture.release();
        this.originalFrame.setImage(null);
    }

}
