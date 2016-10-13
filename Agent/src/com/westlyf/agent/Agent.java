package com.westlyf.agent;

import com.westlyf.database.*;
import com.westlyf.domain.exercise.mix.VideoPracticalExercise;
import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.user.ExamGrade;
import com.westlyf.user.UserExercise;
import com.westlyf.user.Users;

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
    private static ArrayList<UserExercise> userExercises = new ArrayList<UserExercise>();
    private static ArrayList<TextLesson> lessonsInModule = new ArrayList<TextLesson>();
    private static ArrayList<TextLesson> textLessons = new ArrayList<TextLesson>();
    private static ArrayList<VideoPracticalExercise> videoPracticalExercises = new ArrayList<VideoPracticalExercise>();
    private static ArrayList<Exam> exams = new ArrayList<Exam>();
    private static ArrayList<ExamGrade> examGrades = new ArrayList<ExamGrade>();

    public Agent(Users user) {
        setLoggedUser(user);
        setCurrentModule(getLoggedUser().getCurrentModuleId());
        setCurrentLesson(getLoggedUser().getCurrentLessonId());
        setCurrentExam(getLoggedUser().getCurrentExamId());
        loadAll();
    }

    public static void loadAll(){
        String s = "module";
        int i = 1;
        while (!getCurrentModule().equals(s)) {
            s = "module" + i++;
            load(LoadType.LESSON, s);
        };
        load(LoadType.EXERCISE, "module1");
        load(LoadType.USER_EXERCISE);
        load(LoadType.EXAM);
        load(LoadType.GRADE);
    }

    public static void load(LoadType loadType){
        load(loadType, null);
    }

    public static void load(LoadType loadType, String s){
        System.out.println("\nContents of " + loadType);
        switch (loadType){
            case USER_EXERCISE:
                getUserExercises().addAll(UserDatabase.getUserExercisesUsingUserId(getLoggedUser().getUserId()));
                getUserExercises().forEach((a)->System.out.println(a));
                break;
            case LESSON:
                getTextLessons().addAll(LessonDatabase.getTextLessonsUsingTagsContains(s));
                getTextLessons().forEach((a)->System.out.println(a));
                break;
            case EXERCISE:
                getVideoPracticalExercises().addAll(ExerciseDatabase.getVideoPracticalExercisesUsingTagsContains(s));
                getVideoPracticalExercises().forEach((a)->System.out.println(a));
                break;
            case EXAM:
                setExams(ExamDatabase.getExamsUsingTagsContains(getCurrentExam()));
                getExams().forEach((a)->System.out.println(a));
                break;
            case GRADE:
                getExamGrades().addAll(UserDatabase.getExamGradesUsingUserId(getLoggedUser().getUserId()));
                getExamGrades().forEach((a)->System.out.println(a));
                break;
            default:
                System.out.println("empty.");
                break;
        }
    }

    public static void removeLoggedUser(){
        if (getLoggedUser() != null) {
            updateUser();
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

    //database methods
    public static Users getUserUsingCredentials(String username, String password){
        return UserDatabase.getUserUsingCredentials(username, password);
    }

    public static void addUser(Users user){
        UserDatabase.addUser(user);
    }

    public static void addUserExercise(PracticalExercise practicalExercise){
        UserDatabase.addUserExercise(getLoggedUser().getUserId(), practicalExercise.getTitle(), practicalExercise.getCode());
    }

    public static void addExamGrade(String exam_title, int grade){
        UserDatabase.addExamGrade(getLoggedUser().getUserId(), exam_title, grade);
    }

    public static void updateUser(){
        UserDatabase.updateUser(getLoggedUser());
    }

    public static void updateUserExercise(int id, String exercise_title, String tags, String code){
        UserDatabase.updateUserExercise(id, getLoggedUser().getUserId(), exercise_title, code);
    }

    public static void updateExamGrade(int id, String exam_title, int grade){
        UserDatabase.updateExamGrade(id, getLoggedUser().getUserId(), exam_title, grade);
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

    public static ArrayList<ExamGrade> getExamGrades() {
        return examGrades;
    }

    public static void setExamGrades(ArrayList<ExamGrade> examGrades) {
        Agent.examGrades = examGrades;
    }
}
