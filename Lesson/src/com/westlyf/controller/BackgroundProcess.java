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
    private static ExamChoicesOnlyViewerController examChoicesOnlyViewerController;

    @Override
    public void run() {
        try {
            String fer;
            while( running ) {
                fer = "";
                reader = new BufferedInputStream(new FileInputStream( "C:\\Users\\Yves\\Desktop\\emotion.txt" ) );
                while ( reader.available() > 0 ) {
                    fer = fer + String.valueOf((char)reader.read());
                }
                System.out.println(fer);
                if (fer.equals("Anxious")){
                    getExamChoicesOnlyViewerController().setHintVisible(true);
                }else {
                    getExamChoicesOnlyViewerController().setHintVisible(false);
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

    /*public Node loadController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ExamChoicesOnlyViewer.fxml"));
        Node node = loader.load();
        examChoicesOnlyViewerController = loader.getController();
        examChoicesOnlyViewerController.setExam(Agent.getExam());
        return node;
    }*/

    public static void setExamChoicesOnlyViewerController(ExamChoicesOnlyViewerController examChoicesOnlyViewerController) {
        BackgroundProcess.examChoicesOnlyViewerController = examChoicesOnlyViewerController;
    }

    public static ExamChoicesOnlyViewerController getExamChoicesOnlyViewerController() {
        return examChoicesOnlyViewerController;
    }
}
