package com.westlyf.agent;

import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.exercise.mix.VideoPracticalExercise;
import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import sample.model.Users;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 05/09/2016.
 */
public class Agent {

    private static Users loggedUser;
    private static String currentModule;
    private static String currentLesson;
    private static String currentExam;
    private static TextLesson lesson;
    private static VideoPracticalExercise exercise;
    private static ArrayList<TextLesson> lessonsInModule = new ArrayList<TextLesson>();
    private static ArrayList<TextLesson> textLessons = new ArrayList<TextLesson>();
    private static ArrayList<VideoPracticalExercise> videoPracticalExercises = new ArrayList<VideoPracticalExercise>();
    private static ArrayList<Exam> exams = new ArrayList<Exam>();

    public Agent(Users user) {
        setLoggedUser(user);
        setCurrentModule(getLoggedUser().getCurrentModuleId());
        setCurrentLesson(getLoggedUser().getCurrentLessonId());
        setCurrentExam(getLoggedUser().getCurrentExamId());
        loadAll();
    }

    public static void loadAll(){
        loadAvailableLessons();
        loadAvailableExercises();
        loadAvailableExams();
        loadAvailableGrades();
    }

    public static void loadAvailableLessons(){
        String m;
        int i = 1;
        do {
            m = "module" + i++;
            getTextLessons().addAll(LessonDatabase.getTextLessonsUsingTagsContains(m));
        }while (!getCurrentModule().equals(m));
        System.out.println("\nContents of textLessons: ");
        getTextLessons().forEach((a)->System.out.println(a));
    }

    public static void loadAvailableExercises(){
        String m;
        int i = 1;
        //do {
            m = "module" + i++;
            System.out.println(m);
            getVideoPracticalExercises().addAll(ExerciseDatabase.getVideoPracticalExercisesUsingTagsContains(m));
        //}while (!getCurrentModule().equals(m));
        System.out.println("\nContents of videoPracticalExercises: ");
        getVideoPracticalExercises().forEach((a)->System.out.println(a));
    }

    public static void loadAvailableExams(){

    }

    public static void loadAvailableGrades(){

    }

    public static void removeLoggedUser(){
        setLoggedUser(null);
        setCurrentModule(null);
        setCurrentLesson(null);
        setCurrentExam(null);
        setLesson(null);
        getTextLessons().clear();
    }

    public static void getLesson(String module, String lesson){
        TextLesson match = null;
        for (int i = 0; i < getTextLessons().size(); i++) {
            match = getTextLessons().get(i);
            if (match.getTagsString().contains(module) && match.getTagsString().contains(lesson)){
                setLesson(match);
            }
        }
    }

    public static void getExercise(String module, String lesson){
        VideoPracticalExercise match = null;
        for (int i = 0; i < getVideoPracticalExercises().size(); i++) {
            match = getVideoPracticalExercises().get(i);
            if (match.getTagsString().contains(module) && match.getTagsString().contains(lesson)){
                setExercise(match);
            }
        }
        System.out.println("\nContent of exercise:\n" + getExercise());
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

    //setters and getters
    public static Users getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Users loggedUser) {
        Agent.loggedUser = loggedUser;
    }

    public static String getCurrentModule() {
        return currentModule;
    }

    public static void setCurrentModule(String currentModule) {
        Agent.currentModule = currentModule;
    }

    public static String getCurrentLesson() {
        return currentLesson;
    }

    public static void setCurrentLesson(String currentLesson) {
        Agent.currentLesson = currentLesson;
    }

    public static String getCurrentExam() {
        return currentExam;
    }

    public static void setCurrentExam(String currentExam) {
        Agent.currentExam = currentExam;
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

    public static ArrayList<VideoPracticalExercise> getVideoPracticalExercises() {
        return videoPracticalExercises;
    }

    public static void setVideoPracticalExercises(ArrayList<VideoPracticalExercise> videoPracticalExercises) {
        Agent.videoPracticalExercises = videoPracticalExercises;
    }

}
