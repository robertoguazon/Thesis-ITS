package com.westlyf.domain.exercise.mix;

import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.exercise.Exercise;
import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.lesson.VideoLesson;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by robertoguazon on 18/09/2016.
 */
public class VideoPracticalExercise extends Exercise {

    private StringProperty videoLessonId = new SimpleStringProperty();
    private StringProperty videoLessonTitle = new SimpleStringProperty();

    private StringProperty practicalExerciseId = new SimpleStringProperty();
    private StringProperty practicalExerciseTitle = new SimpleStringProperty();

    private Using videoLessonUsing;
    private Using practicalExerciseUsing;

    public VideoPracticalExercise() {
        videoLessonUsing = Using.TITLE;
        practicalExerciseUsing = Using.TITLE;
    }

    @Override
    public String check() {
        return super.check() + "\n" +
                "videoLessonUsing: " + videoLessonUsing + "\n" +
                "videoLessonId: " + ((videoLessonId == null || videoLessonId.isEmpty().get()) ? "empty" : videoLessonId.get()) + "\n" +
                "videoLessonTitle: " + ((videoLessonTitle == null || videoLessonTitle.isEmpty().get()) ? "empty" : videoLessonTitle.get()) + "\n" +
                "practicalExerciseUsing: " + practicalExerciseUsing + "\n" +
                "practicalExerciseId: " + ((practicalExerciseId == null || practicalExerciseId.isEmpty().get()) ? "empty" : practicalExerciseId.get()) + "\n" +
                "practicalExerciseTitle: " + ((practicalExerciseTitle == null || practicalExerciseTitle.isEmpty().get()) ? "empty" : practicalExerciseTitle.get()) + "\n";
    }

    public boolean isValidMaker() {
        //check title,tags and other super properties
        if (!super.isValid()) return false;

        //Using cannot be null
        if (videoLessonUsing == null || practicalExerciseUsing == null) return false;

        //check if the title or id you will use is empty
        switch (videoLessonUsing) {
            case TITLE:
                if (videoLessonTitle == null || videoLessonTitle.isEmpty().get()) return false;
                break;
            case ID:
                if (videoLessonId == null || videoLessonId.isEmpty().get()) return false;
                break;
        }

        //check if the title or id you will use is empty
        switch (practicalExerciseUsing) {
            case TITLE:
                if (practicalExerciseTitle == null || practicalExerciseTitle.isEmpty().get()) return false;
                break;
            case ID:
                if (practicalExerciseId == null || practicalExerciseId.isEmpty().get()) return false;
                break;
        }

        return true;
    }

    public VideoLesson getVideoLesson() {
        switch (videoLessonUsing) {
            case ID:
                return LessonDatabase.getVideoLessonUsingLID(videoLessonId.get());
            case TITLE:
                return LessonDatabase.getVideoLessonUsingTitle(videoLessonTitle.get());
            default:
                return null;
        }
    }

    public PracticalExercise getPracticalExercise() {
        switch (practicalExerciseUsing) {
            case ID:
                return ExerciseDatabase.getPracticalExerciseUsingLID(practicalExerciseId.get());
            case TITLE:
                return ExerciseDatabase.getPracticalExerciseUsingTitle(practicalExerciseTitle.get());
            default:
                return null;
        }
    }

    public java.lang.String getVideoLessonId() {
        return videoLessonId.get();
    }

    public StringProperty videoLessonIdProperty() {
        return videoLessonId;
    }

    public void setVideoLessonId(java.lang.String videoLessonId) {
        this.videoLessonId.set(videoLessonId);
    }

    public java.lang.String getVideoLessonTitle() {
        return videoLessonTitle.get();
    }

    public StringProperty videoLessonTitleProperty() {
        return videoLessonTitle;
    }

    public void setVideoLessonTitle(java.lang.String videoLessonTitle) {
        this.videoLessonTitle.set(videoLessonTitle);
    }

    public java.lang.String getPracticalExerciseId() {
        return practicalExerciseId.get();
    }

    public StringProperty practicalExerciseIdProperty() {
        return practicalExerciseId;
    }

    public void setPracticalExerciseId(java.lang.String practicalExerciseId) {
        this.practicalExerciseId.set(practicalExerciseId);
    }

    public java.lang.String getPracticalExerciseTitle() {
        return practicalExerciseTitle.get();
    }

    public StringProperty practicalExerciseTitleProperty() {
        return practicalExerciseTitle;
    }

    public void setPracticalExerciseTitle(java.lang.String practicalExerciseTitle) {
        this.practicalExerciseTitle.set(practicalExerciseTitle);
    }

    public Using getVideoLessonUsing() {
        return videoLessonUsing;
    }

    public void setVideoLessonUsing(Using videoLessonUsing) {
        this.videoLessonUsing = videoLessonUsing;
    }

    public Using getPracticalExerciseUsing() {
        return practicalExerciseUsing;
    }

    public void setPracticalExerciseUsing(Using practicalExerciseUsing) {
        this.practicalExerciseUsing = practicalExerciseUsing;
    }

    public void copy(VideoPracticalExercise videoPracticalExercise) {
        super.copy(videoPracticalExercise);

        this.videoLessonId = videoPracticalExercise.videoLessonIdProperty();
        this.videoLessonTitle = videoPracticalExercise.videoLessonTitleProperty();

        this.practicalExerciseId = videoPracticalExercise.practicalExerciseIdProperty();
        this.practicalExerciseTitle = videoPracticalExercise.practicalExerciseTitleProperty();

        this.videoLessonUsing = videoPracticalExercise.getVideoLessonUsing();
        this.practicalExerciseUsing = videoPracticalExercise.getPracticalExerciseUsing();
    }

    @Override
    public VideoPracticalExercise clone() {
        VideoPracticalExercise videoPracticalExercise = new VideoPracticalExercise();
        videoPracticalExercise.copy(this);

        return videoPracticalExercise;
    }

    public String toString() {
        return super.toString() + "\n" +
                "videoLessonUsing: " + videoLessonUsing + "\n" +
                "videoLessonId: " + videoLessonId.get() + "\n" +
                "videoLessonTitle: " + videoLessonTitle.get() + "\n" +
                "practicalExerciseUsing: " + practicalExerciseUsing + "\n" +
                "practicalExerciseId: " + practicalExerciseId.get() + "\n" +
                "practicalExerciseTitle: " + practicalExerciseTitle.get() + "\n" ;
    }
}
