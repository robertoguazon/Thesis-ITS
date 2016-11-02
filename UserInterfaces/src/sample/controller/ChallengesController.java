package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.controller.*;
import com.westlyf.domain.exercise.practical.PracticalExercise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import sample.model.ConfirmBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Yves on 11/2/2016.
 */
public class ChallengesController extends ControllerManager implements Initializable {

    private ObservableList<String> challengesList;
    @FXML private ListView challengesListView;
    @FXML private Button backToMenu;
    //@FXML private Label clearedLabel;
    private ArrayList<PracticalExercise> challenges = new ArrayList<PracticalExercise>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        challengesList = FXCollections.observableArrayList();
        challenges = Agent.getChallenges();
        for (int i=0; i<challenges.size(); i++){
            challengesList.add(challenges.get(i).getTitle());
        }
        challengesListView.setItems(challengesList);
        challengesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedChallenge = challengesListView.getSelectionModel().getSelectedItem().toString();
                if (confirmTakeChallenge(selectedChallenge)) {
                    Node node = Controllers.getNode(ControllerType.PRACTICAL_RETURN_EXERCISE_VIEWER,
                            Agent.loadChallenge(selectedChallenge));
                    newChildWindow(node, ControllerType.PRACTICAL_RETURN_EXERCISE_VIEWER,  "Challenge");
                }
            }
        });
    }

    public void countCleared(){
        int count = 0;
        for (int i=0; i<challenges.size(); i++){

            if (Agent.containsPracticalExercise(challenges.get(i))){
                //challenges.get(i).setCode(Agent.getUserExercise().getCode());
                count++;
            }
        }
    }

    public void reset(){
        //challenges.clear();
        //challengesList.clear(); //TODO don't clear on reset
        //challengesListView.getItems().clear();
    }

    @FXML
    public void handleAction(ActionEvent event){
        if (event.getSource() == backToMenu){
            reset();
            changeScene("../view/user.fxml");
        }
    }

    public boolean confirmTakeChallenge(String selectedChallenge){
        return ConfirmBox.display("Take Challenge",
                "Are you sure you want to take on this challenge?",
                "Challenge: " + selectedChallenge);
    }

/*
    public void closeChildWindow(){
        if (Agent.isCleared()){
            Agent.setIsExerciseCleared(false);
            child.close();
        }else {
            Boolean answer = ConfirmBox.display("Close window",
                    "Are you sure you want to close this window?", "All changes will not be saved.");
            if (answer){

                child.close();
            }
        }
    }
*/
}
