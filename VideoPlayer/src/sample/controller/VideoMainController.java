package sample.controller;

import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.lesson.VideoLesson;
import com.westlyf.video.VideoUtil;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 11/06/2016.
 */
public class VideoMainController implements Initializable {

    @FXML private MediaView videoLessonMediaView;
    @FXML private Slider timeSlider;
    @FXML private Label timeLabel;
    @FXML private Slider volumeSlider;
    @FXML private Button playButton;

    private Media media;
    private MediaPlayer player;

    private boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeSlider.setMinWidth(50);
        timeSlider.setPrefWidth(130);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeLabel.setMinWidth(50);
        volumeSlider.setMinWidth(30);
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);

       try {
           VideoLesson videoLesson = LessonDatabase.getVideoLessonUsingLID("lid1275089802453708");
           this.media = new Media(VideoUtil.load(videoLesson));
           this.player = new MediaPlayer(media);
           this.videoLessonMediaView.setMediaPlayer(player);
           this.player.setAutoPlay(false);

       } catch (MediaException e) {
           e.printStackTrace();
       }

        initPlayer();
        //init time and volum sliders
        initSliders();

    }

    @FXML
    private void play() {
        MediaPlayer.Status status = this.player.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            return;
        }

        if (status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.STOPPED) {

            //rewind if at the end
            if (atEndOfMedia) {
                atEndOfMedia = false;
            }

            this.player.play();

        } else {
            this.player.pause();
        }

    }

    private void updateValues() {
        if (timeLabel != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = player.getCurrentTime();
                    timeLabel.setText(VideoUtil.formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO)&& !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int)Math.round(player.getVolume() * 100));
                    }
                }
            });
        }
    }

    private void initPlayer() {
        this.player.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateValues();
            }
        });

        this.player.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                if (stopRequested) {
                    stopRequested = false;
                }

                playButton.setText("Pause");
            }
        });

        this.player.setOnPaused(new Runnable() {

            @Override
            public void run() {
                playButton.setText("Play");
            }
        });

        this.player.setOnReady(new Runnable() {

            @Override
            public void run() {
                duration = player.getMedia().getDuration();
                updateValues();
            }
        });

        this.player.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        this.player.setOnEndOfMedia(() -> {
            if (!repeat) {
                playButton.setText("Play");
                atEndOfMedia = true;
                stopRequested = true;
                this.player.seek(this.player.getStartTime());
                player.pause();
            }
        });
    }

    private void initSliders() {
        this.timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    player.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });

        this.volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (volumeSlider.isValueChanging()) {
                    player.setVolume(volumeSlider.getValue() / 100);
                }
            }
        });
    }
}
