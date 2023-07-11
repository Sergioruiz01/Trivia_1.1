/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trivia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Trivia extends Application {

    private static final String BACKGROUND_SOUND = "C:\\Users\\latin\\OneDrive\\Documentos\\NetBeansProjects\\Trivia\\Sounds\\Beijing_Bass.wav";
    private static final String CLICK_SOUND = "C:\\Users\\latin\\OneDrive\\Documentos\\NetBeansProjects\\Trivia\\Sounds\\RespuestaCorrecta.wav";

    private static Clip backgroundClip;
    private static Clip clickClip;

    private static TriviaController triviaController;
    private static Timer questionTimer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Trivia.fxml"));
        Parent root = loader.load();
        triviaController = loader.getController();

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("trivia.css").toExternalForm()); // Enlaza el archivo CSS
        primaryStage.setTitle("Trivia Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadSounds();

        // Reproduce el sonido de fondo
        playBackgroundMusic();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadSounds() {
        try {
            AudioInputStream backgroundStream = AudioSystem.getAudioInputStream(new File(BACKGROUND_SOUND));
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(backgroundStream);

            AudioInputStream clickStream = AudioSystem.getAudioInputStream(new File(CLICK_SOUND));
            clickClip = AudioSystem.getClip();
            clickClip.open(clickStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playClickSound() {
        if (clickClip != null) {
            clickClip.setFramePosition(0);
            clickClip.start();
        }
    }

    public static void playBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
        }
    }

    public static void startQuestionTimer() {
        if (questionTimer != null) {
            questionTimer.cancel();
        }

        questionTimer = new Timer();
        int tiempoLimite = 10; // Tiempo límite en segundos

        questionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Se agotó el tiempo, llama al método endGame() del controlador
                triviaController.endGame();
            }
        }, tiempoLimite * 1000);
    }

    public static void cancelQuestionTimer() {
        if (questionTimer != null) {
            questionTimer.cancel();
        }
    }
}
