package com.westlyf.agent;

import com.westlyf.database.ExamDatabase;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.database.UserDatabase;
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
    private static Exam exam;
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
        String s;
        int i = 1;
        do {
            s = "module" + i++;
            getTextLessons().addAll(LessonDatabase.getTextLessonsUsingTagsContains(s));
        }while (!getCurrentModule().equals(s));
        System.out.println("\nContents of textLessons: ");
        getTextLessons().forEach((a)->System.out.println(a));
    }

    public static void loadAvailableExercises(){
        String s;
        int i = 1;
        //do {
            s = "module" + i++;
            getVideoPracticalExercises().addAll(ExerciseDatabase.getVideoPracticalExercisesUsingTagsContains(s));
        //}while (!getCurrentModule().equals(m));
        System.out.println("\nContents of videoPracticalExercises: ");
        getVideoPracticalExercises().forEach((a)->System.out.println(a));
    }

    public static void loadAvailableExams(){
        getExams().addAll(ExamDatabase.getExamsUsingTagsContains(getCurrentExam()));
        System.out.println("\nContents of exams: ");
        getExams().forEach((a)->System.out.println(a));
    }

    public static void loadAvailableGrades(){

    }

    public static void removeLoggedUser(){
        if (getLoggedUser() != null) {
            UserDatabase.updateUser(getLoggedUser().getUserId(), getLoggedUser().getCurrentModuleId(),
                    getLoggedUser().getCurrentLessonId(), getLoggedUser().getCurrentExamId(),
                    getLoggedUser().getUsername(), getLoggedUser().getPassword(), getLoggedUser().getName(),
                    getLoggedUser().getAge(), getLoggedUser().getSex(), getLoggedUser().getSchool(),
                    getLoggedUser().getYearLevel(), getLoggedUser().getProfilePicturePath());
            setLoggedUser(null);
            setCurrentModule(null);
            setCurrentLesson(null);
            setCurrentExam(null);
            setLesson(null);
            setExercise(null);
            setExam(null);
            getTextLessons().clear();
            getVideoPracticalExercises().clear();
            getExams().clear();
        }
    }

    public static void loadLesson(String module, String lesson){
        TextLesson match = null;
        for (int i = 0; i < getTextLessons().size(); i++) {
            match = getTextLessons().get(i);
            if (match.getTagsString().contains(module) && match.getTagsString().contains(lesson)){
                setLesson(match);
            }
        }
        System.out.println("\nRetrieved Lesson:\n" + getLesson());
    }

    public static void loadExercise(String module, String lesson){
        VideoPracticalExercise match = null;
        for (int i = 0; i < getVideoPracticalExercises().size(); i++) {
            match = getVideoPracticalExercises().get(i);
            if (match.getTagsString().contains(module) && match.getTagsString().contains(lesson)){
                setExercise(match);
            }
        }
        System.out.println("\nRetrieved Exercise:\n" + getExercise());
    }

    public static void loadExam(){
        if (getCurrentExam().contains("module")){
            setExam(getExams().get((int) (Math.random() * getExams().size())));
            Agent.getLoggedUser().setCurrentExamId(getExam().getTagsString());
        }else {
            for (int i = 0; i < getExams().size(); i++){
                Exam match = getExams().get(i);
                if (getCurrentExam().contains(match.getTagsString())){
                    setExam(match);
                }
            }
        }

        System.out.println("\nRetrieved Exam:\n" + getExam());
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

    public static Exam getExam() {
        return exam;
    }

    public static void setExam(Exam exam) {
        Agent.exam = exam;
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

    public static ArrayList<Exam> getExams() {
        return exams;
    }

    public static void setExams(ArrayList<Exam> exams) {
        Agent.exams = exams;
    }
}
