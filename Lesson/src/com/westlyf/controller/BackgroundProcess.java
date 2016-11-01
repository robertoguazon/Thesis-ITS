package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.quiz.Exam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Yves on 10/16/2016.
 */
public class BackgroundProcess extends Thread{

    boolean running = true;
    BufferedInputStream reader;
    private ExamChoicesOnlyViewerController examChoicesOnlyViewerController;

    @Override
    public void run() {
        try {
            String fer;
            examChoicesOnlyViewerController =
                    (ExamChoicesOnlyViewerController) Controllers.getController(ControllerType.EXAM_CHOICES_ONLY_VIEWER);
            while( running ) {
                fer = "";
                reader = new BufferedInputStream(new FileInputStream( "C:\\Users\\Yves\\Desktop\\emotion.txt" ) );
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
}
