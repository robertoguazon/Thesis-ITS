package com.westlyf.agent;

import com.westlyf.controller.BackgroundProcess;
import com.westlyf.database.*;
import com.westlyf.domain.exercise.mix.VideoPracticalExercise;
import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.user.ExamGrade;
import com.westlyf.user.UserExercise;
import com.westlyf.user.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by robertoguazon on 05/09/2016.
 */
public class Agent {

    private static Users loggedUser;
    private static boolean isExerciseCleared;

    private static UserExercise userExercise = new UserExercise();
    private static TextLesson lesson = new TextLesson();
    private static VideoPracticalExercise exercise = new VideoPracticalExercise();
    private static Exam exam = new Exam();
    private static ExamGrade examGrade = new ExamGrade();

    private static ArrayList<UserExercise> userExercises = new ArrayList<UserExercise>();
    private static ArrayList<TextLesson> lessonsInModule = new ArrayList<TextLesson>();
    private static ArrayList<TextLesson> textLessons = new ArrayList<TextLesson>();
    private static ArrayList<PracticalExercise> challenges = new ArrayList<PracticalExercise>();
    private static ArrayList<VideoPracticalExercise> videoPracticalExercises = new ArrayList<VideoPracticalExercise>();
    private static ArrayList<Exam> exams = new ArrayList<Exam>();
    private static ArrayList<ExamGrade> examGrades = new ArrayList<ExamGrade>();

    private static BackgroundProcess background = new BackgroundProcess();
    private static final String fer = "http://localhost/emotion-detection/";

    public Agent(Users user) {
        setLoggedUser(user);
        loadAll();
    }

    public static void loadAll(){
        String s = "module";
        int i = 1;
        //extract data from db
        while (!Agent.getLoggedUser().getCurrentModuleId().equals(s)) {
            s = "module" + i++;
            load(LoadType.LESSON, s);
            load(LoadType.EXERCISE, s);
        };
        if (loggedUser.getCurrentExamId() == "challenge"){
            load(LoadType.CHALLENGE, "challenge");
        }else if (loggedUser.getCurrentExamId() != null) {
            load(LoadType.EXAM, s);
        }
        load(LoadType.USER_EXERCISE);
        load(LoadType.GRADE);
        //prints out the arraylists
        print(LoadType.LESSON);
        print(LoadType.EXERCISE);
        print(LoadType.EXAM);
        print(LoadType.CHALLENGE);
        print(LoadType.USER_EXERCISE);
        print(LoadType.GRADE);
    }

    public static void load(LoadType loadType){
        load(loadType, null);
    }

    public static void load(LoadType loadType, String s){
        switch (loadType){
            case LESSON:
                getTextLessons().addAll(LessonDatabase.getTextLessonsUsingTagsContains(s));
                break;
            case EXERCISE:
                getVideoPracticalExercises().addAll(ExerciseDatabase.getVideoPracticalExercisesUsingTagsContains(s));
                break;
            case EXAM:
                setExams(ExamDatabase.getExamsUsingTagsContains(s));
                break;
            case USER_EXERCISE:
                getUserExercises().addAll(UserDatabase.getUserExercisesUsingUserId(getLoggedUser().getUserId()));
                break;
            case GRADE:
                getExamGrades().addAll(UserDatabase.getExamGradesUsingUserId(getLoggedUser().getUserId()));
                break;
            case CHALLENGE:
                getChallenges().addAll(ExerciseDatabase.getPracticalExercisesUsingTagsContains(s));
            default:
                System.out.println("empty.");
                break;
        }
    }

    public static void print(LoadType loadType){
        System.out.println("\nContents of " + loadType);
        switch (loadType){
            case LESSON:
                getTextLessons().forEach((a)->System.out.println(a));
                break;
            case EXERCISE:
                getVideoPracticalExercises().forEach((a)->System.out.println(a));
                break;
            case EXAM:
                getExams().forEach((a)->System.out.println(a));
                break;
            case USER_EXERCISE:
                getUserExercises().forEach((a)->System.out.println(a));
                break;
            case GRADE:
                getExamGrades().forEach((a)->System.out.println(a));
                break;
            case CHALLENGE:
                getChallenges().forEach((a)->System.out.println(a));
            default:
                System.out.println("empty.");
                break;
        }
    }

    public static void removeLoggedUser(){
        if (getLoggedUser() != null) {
            if (updateUser() > 0) {
                setLoggedUser(null);
                setLesson(null);
                setExercise(null);
                setExam(null);
                getLessonsInModule().clear();
                getTextLessons().clear();
                getVideoPracticalExercises().clear();
                getExams().clear();
                getUserExercises().clear();
                getExamGrades().clear();
            }else {return;}
        }
    }

    public static TextLesson loadLesson(String tag1, String tag2){
        TextLesson match = null;
        for (int i = 0; i < getTextLessons().size(); i++) {
            match = getTextLessons().get(i);
            if (match.getTagsString().contains(tag1) && match.getTagsString().contains(tag2)){
                break;
            }else {match = null;}
        }
        setLesson(match);
        System.out.println("\nRetrieved Lesson:\n" + getLesson());
        return match;
    }

    public static VideoPracticalExercise loadExercise(String tag1, String tag2){
        VideoPracticalExercise match = null;
        for (int i = 0; i < getVideoPracticalExercises().size(); i++) {
            match = getVideoPracticalExercises().get(i);
            if (match.getTagsString().contains(tag1) && match.getTagsString().contains(tag2)){
                break;
            }else {match = null;}
        }
        setExercise(match);
        System.out.println("\nRetrieved Exercise:\n" + getExercise());
        return match;
    }

    public static Exam loadExam(){
        Exam match = null;
        if (!getLoggedUser().getCurrentExamId().contains("exam")){
            match = getExams().get((int) (Math.random() * getExams().size()));
            getLoggedUser().setCurrentExamId(match.getTagsString());
        }else {
            for (int i = 0; i < getExams().size(); i++){
                match = getExams().get(i);
                if (getLoggedUser().getCurrentExamId().contains(match.getTagsString())){
                    break;
                }else {match = null;}
            }
        }
        setExam(match);
        System.out.println("\nRetrieved Exam:\n" + getExam());
        return match;
    }

    public static PracticalExercise loadChallenge(String title){
        PracticalExercise match = null;
        for (int i = 0; i < getChallenges().size(); i++) {
            match = getChallenges().get(i);
            if (match.getTitle().contains(title)){
                break;
            }else {match = null;}
        }
        System.out.println("\nRetrieved Challenge:\n" + match);
        return match;
    }

    public static ArrayList<TextLesson> getLessonsInModule(String currentModule) {
        for (int i = 0; i < getTextLessons().size(); i++) {
            TextLesson match = getTextLessons().get(i);
            if (match.getTagsString().contains(currentModule)){
                getLessonsInModule().add(match);
            }
        }
        //System.out.println("Contents of lessons in module:");
        //getLessonsInModule().forEach((a)->System.out.println(a));
        return getLessonsInModule();
    }

    public static void clearLessonsInModule(){
        getLessonsInModule().clear();
    }

    public static void clearExams() { getExams().clear(); }

    public static boolean containsPracticalExercise(PracticalExercise practicalExercise){
        UserExercise match = null;
        for (int i = 0; i < getUserExercises().size(); i++){
            match = getUserExercises().get(i);
            if (practicalExercise.getTitle().equals(match.getExercise_title())){
                setUserExercise(match);
                return true;
            }
        }
        return false;
    }

    public static boolean containsExamGrade(Exam exam){
        ExamGrade match = null;
        for (int i = 0; i < getExamGrades().size(); i++){
            match = getExamGrades().get(i);
            if (exam.getTitle().equals(match.getExam_title())){
                setExamGrade(match);
                return true;
            }
        }
        return false;
    }

    public static void startBrowser(){
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec( "rundll32 url.dll,FileProtocolHandler " + fer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startBackground(){
        background.setDaemon(true);
        background.start();
    }

    public static void stopBackground(){
        background.stopBackground();
    }

    //database methods
    public static Users getUserUsingCredentials(String username, String password){
        return UserDatabase.getUserUsingCredentials(username, password);
    }

    public static int addUser(Users user){
        return UserDatabase.addUser(user);
    }

    public static int addUserExercise(UserExercise userExercise){
        setUserExercise(userExercise);
        getUserExercises().add(userExercise);
        return UserDatabase.addUserExercise(userExercise);
    }

    public static int addExamGrade(String exam_title, int grade, int totalItems, int percentGrade, String status){
        getExamGrade().setUserId(getLoggedUser().getUserId());
        getExamGrade().setExam_title(exam_title);
        getExamGrade().setRawGrade(grade);
        getExamGrade().setTotalItems(totalItems);
        getExamGrade().setStatus(status);
        getExamGrade().setPercentGrade(percentGrade);
        getExamGrades().add(getExamGrade());
        return UserDatabase.addExamGrade(getExamGrade());
    }

    public static int updateUser(){
        return UserDatabase.updateUser(getLoggedUser());
    }

    public static int updateUserExercise(String code){
        int i = getUserExercises().indexOf(getUserExercise());
        getUserExercises().get(i).setCode(code);
        setUserExercise(getUserExercises().get(i));
        return UserDatabase.updateUserExercise(getUserExercise());
    }

    public static int updateExamGrade(int grade, int totalItems, int percentGrade, String status){
        int i = getExamGrades().indexOf(getExamGrade());
        getExamGrades().get(i).setRawGrade(grade);
        getExamGrades().get(i).setTotalItems(totalItems);
        getExamGrades().get(i).setPercentGrade(percentGrade);
        getExamGrades().get(i).setStatus(status);
        setExamGrade(getExamGrades().get(i));
        return UserDatabase.updateExamGrade(getExamGrade());
    }

    //setters and getters
    public static boolean isCleared() {
        return isExerciseCleared;
    }

    public static void setIsExerciseCleared(boolean isExerciseCleared) {
        Agent.isExerciseCleared = isExerciseCleared;
    }

    public static Users getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Users loggedUser) {
        Agent.loggedUser = loggedUser;
    }

    public static UserExercise getUserExercise() {
        return userExercise;
    }

    public static void setUserExercise(UserExercise userExercise) {
        Agent.userExercise = userExercise;
    }

    public static TextLesson getLesson() {
        return lesson;
    }

    public static void setLesson(TextLesson lesson) {
        Agent.lesson = lesson;
    }

    public static VideoPracticalExercise getExercise() {
        return exercise;
    }

    public static void setExercise(VideoPracticalExercise exercise) {
        Agent.exercise = exercise;
    }

    public static Exam getExam() {
        return exam;
    }

    public static void setExam(Exam exam) {
        Agent.exam = exam;
    }

    public static ExamGrade getExamGrade() {
        return examGrade;
    }

    public static void setExamGrade(ExamGrade examGrade) {
        Agent.examGrade = examGrade;
    }

    public static ArrayList<UserExercise> getUserExercises() {
        return userExercises;
    }

    public static void setUserExercises(ArrayList<UserExercise> userExercises) {
        Agent.userExercises = userExercises;
    }

    public static ArrayList<TextLesson> getTextLessons() {
        return textLessons;
    }

    public static void setTextLessons(ArrayList<TextLesson> textLessons) {
        Agent.textLessons = textLessons;
    }

    public static ArrayList<TextLesson> getLessonsInModule() {
        return lessonsInModule;
    }

    public static void setLessonsInModule(ArrayList<TextLesson> lessonsInModule) {
        Agent.lessonsInModule = lessonsInModule;
    }

    public static ArrayList<PracticalExercise> getChallenges() {
        return challenges;
    }

    public static void setChallenges(ArrayList<PracticalExercise> challenges) {
        Agent.challenges = challenges;
    }

    public static ArrayList<VideoPracticalExercise> getVideoPracticalExercises() {
        return videoPracticalExercises;
    }

    public static void setVideoPracticalExercises(ArrayList<VideoPracticalExercise> videoPracticalExercises) {
        Agent.videoPracticalExercises = videoPracticalExercises;
    }

    public static ArrayList<Exam> getExams() {
        return exams;
    }

    public static void setExams(ArrayList<Exam> exams) {
        if (exams != null) {
            Agent.exams = exams;
        }
    }

    public static ArrayList<ExamGrade> getExamGrades() {
        return examGrades;
    }

    public static void setExamGrades(ArrayList<ExamGrade> examGrades) {
        Agent.examGrades = examGrades;
    }


}
