package dk.easv.pmc.gui.utils;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaHandler {

    private MediaPlayer lastMovie, currentMovie;

    public MediaHandler() {

    }

    private void stopPlayingMovie(MediaPlayer movie) {
        if (movie.getStatus() == MediaPlayer.Status.PLAYING)
            movie.stop();

        if (lastMovie != null && lastMovie.getStatus() == MediaPlayer.Status.PLAYING)
            lastMovie.stop();

        currentMovie = movie;
    }

    private boolean shouldPause(MediaPlayer movie) {
        if (movie == currentMovie) {
            MediaPlayer.Status status = movie.getStatus();
            if (status == MediaPlayer.Status.PLAYING)
                movie.pause();
            else if (status == MediaPlayer.Status.PAUSED)
                movie.play();

            return true;
        }
        return false;
    }

    private void movieEnd(MediaPlayer movie) {
        Duration currentTime = movie.getCurrentTime();
        Duration totalDuration = movie.getTotalDuration();

        if (Math.abs(currentTime.toMillis() - totalDuration.toMillis()) < 1.0)
            movie.seek(Duration.seconds(0));
    }

    public void playMovie(MediaPlayer movie, boolean restart) {
        movieEnd(movie);

        if (!restart && shouldPause(movie))
            return;

        stopPlayingMovie(movie);
        currentMovie.seek(Duration.seconds(0));
        currentMovie.play();

        lastMovie = movie;
    }

    public boolean isPlaying() {
        return (currentMovie.getStatus() == MediaPlayer.Status.PLAYING);
    }

    public void restartMovie() {
        if (isPlaying())
            currentMovie.seek(new Duration(0));
    }

    public String getTimeFromDouble(double val) {
        double seconds = val;

        double secs = seconds % 60;
        double minutes = (seconds / 60) % 60;

        return String.format("%d:%02d", (int)minutes, (int)secs);
    }

    public MediaPlayer getCurrentMovie() {
        return currentMovie;
    }


}
