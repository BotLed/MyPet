package application.controllers;

import application.model.Feedback;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FeedbackController {

    private Feedback feedbackModel;
    private MediaPlayer currentMusicPlayer; // For background music
    private Map<String, AudioClip> soundEffects; // For sound effects

    public FeedbackController(Feedback feedbackModel) {
        this.feedbackModel = feedbackModel;
        this.currentMusicPlayer = null; // No music playing initially
        this.soundEffects = new HashMap<>();

        // Preload sound effects
        preloadSoundEffects();
    }

    // Preload sound effects into map
    private void preloadSoundEffects() {
        soundEffects.put("buttonSelect", loadAudioClip("src/main/resources/sound effects/button-select.mp3"));
        soundEffects.put("giftEffect", loadAudioClip("src/main/resources/sound effects/gift-effect.mp3"));
        soundEffects.put("pauseMenu", loadAudioClip("src/main/resources/sound effects/pause-menu.mp3"));
        soundEffects.put("reward1", loadAudioClip("src/main/resources/sound effects/reward.mp3"));
        soundEffects.put("reward2", loadAudioClip("src/main/resources/sound effects/reward-2.mp3"));
        soundEffects.put("reward3", loadAudioClip("src/main/resources/sound effects/reward-3.mp3"));
        soundEffects.put("warning", loadAudioClip("src/main/resources/sound effects/warning.mp3"));
    }

    // Load clip from file path
    private AudioClip loadAudioClip(String path) {
        File file = new File(path);
        if (file.exists()) {
            return new AudioClip(file.toURI().toString());
        } else {
            System.err.println("Audio file not found: " + path);
            return null;
        }
    }

    // Play background music for current view
    public void playBackgroundMusic(String viewName) {
        if (!feedbackModel.isMusicOn()) {
            stopBackgroundMusic();
            return;
        }

        String musicFile = getMusicFileForView(viewName);

        // Stop the current music if it's different.. no overlapping music
        if (currentMusicPlayer != null && !currentMusicPlayer.getMedia().getSource().endsWith(musicFile)) {
            stopBackgroundMusic();
        }

        // Start new music
        if (currentMusicPlayer == null) {
            File file = new File("src/main/resources/" + musicFile);
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                currentMusicPlayer = new MediaPlayer(media);
                currentMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
                currentMusicPlayer.play();
                System.out.println("Playing background music: " + musicFile);
            } else {
                System.err.println("Music file not found: " + musicFile);
            }
        }
    }

    // Stop background music
    public void stopBackgroundMusic() {
        if (currentMusicPlayer != null) {
            currentMusicPlayer.stop();
            currentMusicPlayer = null;
            System.out.println("Background music stopped.");
        }
    }

    // Get music file path for the given view
    private String getMusicFileForView(String viewName) {
        switch (viewName) {
            case "MainMenu":
                return "music/main-menu-theme.mp3";
            case "Gameplay":
                return "music/gameplay-music.mp3";
            default:
                return "music/gameplay-theme.mp3";
        }
    }

    // Play a sound effect for specific action
    public void playSoundEffect(String action) {
        if (!feedbackModel.isSfxOn()) {
            return; // SFX disabled, do nothing
        }

        AudioClip clip = soundEffects.get(action);
        if (clip != null) {
            clip.play();
            System.out.println("Playing sound effect: " + action);
        } else {
            System.err.println("Sound effect not found for action: " + action);
        }
    }
}
