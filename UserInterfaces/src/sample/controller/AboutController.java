package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 11/11/2016.
 */
public class AboutController implements Initializable{

    @FXML private TextArea aboutTextArea;
    private String information = "" +
            "Software Information:\n" +
            "We, the authors of the thesis entitled, " +
            "\"Free Apples: An Intelligent Tutoring System Integrating Kolb’s Experiential Learning Model " +
            "as Pedagogical Module embedded with Facial Expression Recognition\", " +
            "hereby certify and vouch that the contents of this research work is solely our own original work; " +
            "that no part of this work has been copied nor taken without due permission or proper acknowledgment " +
            "and citation of the respective authors; that we are upholding academic professionalism by integrating " +
            "intellectual property rights laws in research and projects as requirements of our program. \n" +
            "\n" +
            "If found and proven that there is an attempt or committed an infringement of copyright ownership, " +
            "we are liable for any legal course of action sanctioned by the University and the Philippine laws.\n" +
            "\n" +
            "\n" +
            "Developers:\n" +
            "Borais, Alexavier\n" +
            "Francisco, Yves Rupert\n" +
            "Guazon, John Robert\n" +
            "Mabasa, Ryan Christopher";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutTextArea.setText(information);
    }
}
