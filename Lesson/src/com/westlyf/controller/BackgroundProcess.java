package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.quiz.Exam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import sample.model.FileUtil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yves on 10/16/2016.
 */
public class BackgroundProcess implements Runnable{

    private volatile boolean running = true;
    BufferedInputStream reader;
    private ExamChoicesOnlyViewerController examChoicesOnlyViewerController;

    @Override
    public void run() {
        System.out.println("Starting Background Process...");
        String ferPath = getFERPath();
        try {
            String fer;
            examChoicesOnlyViewerController =
                    (ExamChoicesOnlyViewerController) Controllers.getController(ControllerType.EXAM_CHOICES_ONLY_VIEWER);
            running = true;
            while( running ) {
                fer = "";
                reader = new BufferedInputStream(new FileInputStream(ferPath) );
                while ( reader.available() > 0 ) {
                    fer = fer + String.valueOf((char)reader.read());
                }
                System.out.println(fer);
                if (fer.equals("Anxious")){
                    examChoicesOnlyViewerController.setHintButtonDisable(false);
                }else {
                    examChoicesOnlyViewerController.setHintButtonDisable(true);
                }
                Thread.sleep( 5000 );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
        System.out.println("Stopping Background Process...");
        try {
            Thread.sleep(200);
            System.out.println("Background Process has been stopped: FER");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getFERPath(){
        FileUtil fileUtil = new FileUtil();
        return fileUtil.readFile("resources/fer/FacialExpressionRecognitionPath");
    }
}
