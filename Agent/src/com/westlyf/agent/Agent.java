package com.westlyf.agent;

import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.lesson.TextLesson;
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
    private static ArrayList<TextLesson> textLessons;
    private static ArrayList<TextLesson> lessonsInModule = new ArrayList<TextLesson>();
    private static ArrayList<String> moduleList = new ArrayList<String>();
    private static ArrayList<String> lessonList = new ArrayList<String>();

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
        String m = "module1";
        String l = "lesson1";
        int i = 1;
        int j = 0;
        while (!getCurrentModule().equals(m)) {
            getModuleList().add("module" + i++);
            m = "module" + i;
        }
        setTextLessons(LessonDatabase.getTextLessonsUsingTagsContains(moduleList));
        do {
            j++;
            getLessonList().add(l = "lesson" + j);
        }while (!getCurrentLesson().equals(l));
        getModuleList().add(m = "module" + i);
        for (int k=0; k<getLessonList().size(); k++){
            String temp = getLessonList().get(k);
            getTextLessons().addAll(LessonDatabase.getTextLessonsUsingTagsContains(temp, "module"+i));
        }
        //System.out.println("Contents of textLessons: \n");
        //getTextLessons().forEach((a)->System.out.println("\n" + a));
        //System.out.println("Contents of moduleList: " + getModuleList());
        //System.out.println("Contents of lessonList: " + getLessonList());
        //System.out.println("Contents of lessonList: " + getLesson("module1", "lesson5"));
    }

    public static void loadAvailableExercises(){

    }

    public static void loadAvailableExams(){

    }

    public static void loadAvailableGrades(){

    }

    public static void removeLoggedUser(){
        setLoggedUser(null);
        setTextLessons(null);
        setCurrentModule(null);
        setCurrentLesson(null);
        setCurrentExam(null);
        setLesson(null);
    }

    public static TextLesson getLesson(String module, String lesson){
        TextLesson match = null;
        for (int i = 0; i < getTextLessons().size(); i++) {
            match = getTextLessons().get(i);
            if (match.getTagsString().contains(module) && match.getTagsString().contains(lesson)){
                setLesson(match);
                return match;
            }
        }return null;
    }

    public static ArrayList<TextLesson> getLessonsInModule(String currentModule) {
        for (int i = 0; i < getTextLessons().size(); i++) {
            TextLesson match = getTextLessons().get(i);
            if (match.getTagsString().contains(currentModule)){
                getLessonsInModule().add(match);
            }
        }
        System.out.println("Contents of lessons in module:");
        getLessonsInModule().forEach((a)->System.out.println(a));
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

    public static ArrayList<String> getModuleList() {
        return moduleList;
    }

    public static void setModuleList(ArrayList<String> moduleList) {
        Agent.moduleList = moduleList;
    }

    public static ArrayList<String> getLessonList() {
        return lessonList;
    }

    public static void setLessonList(ArrayList<String> lessonList) {
        Agent.lessonList = lessonList;
    }
}
