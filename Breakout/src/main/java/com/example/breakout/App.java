package com.example.breakout;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, 800, 600);

        gc.setFill(Color.BLUE);
        gc.fillOval(10,10,10,10);
        Label scoreLabel = new Label("Collisioni: 0");
        scoreLabel.setFont(Font.font(16));
        scoreLabel.setTextFill(Color.WHITE);
        StackPane.setAlignment(scoreLabel, Pos.TOP_LEFT);

        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(150), scoreLabel);
        zoomIn.setFromX(1.0);
        zoomIn.setFromY(1.0);
        zoomIn.setToX(1.5);
        zoomIn.setToY(1.5);

        ScaleTransition zoomOut = new ScaleTransition(Duration.millis(150), scoreLabel);
        zoomOut.setFromX(1.5);
        zoomOut.setFromY(1.5);
        zoomOut.setToX(1.0);
        zoomOut.setToY(1.0);

        SequentialTransition zoomAnim = new SequentialTransition(zoomIn, zoomOut);

        StackPane root = new StackPane(canvas, scoreLabel);
        List<Ball> balls = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            balls.add(new Ball(200, i * 40,2,2));
        }

        AnimationTimer timer = new AnimationTimer() {
            int punteggioPrecendete = 0;
            @Override
            public void handle(long now) {
                for (Ball b: balls) {
                    b.update();
                    b.checkWalldistance();
                }

                for (int i = 0; i < balls.size(); i++) {
                    for (int j = i + 1; j < balls.size(); j++) {
                        Ball.checkBalldistance(balls.get(i), balls.get(j));
                    }
                }

                if (Ball.schianti != punteggioPrecendete) {
                    punteggioPrecendete = Ball.schianti;
                    scoreLabel.setText("Collisioni: " + Ball.schianti);
                    zoomAnim.playFromStart();
                }

                gc.setFill(Color.rgb(20, 20, 35));
                gc.fillRect(0, 0, 800, 600);

                for (Ball b: balls) {
                    b.draw(gc);
                }
            }
        };        timer.start();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Canvas Demo");
        stage.show();
    }

    static void main() {
        new App();
    }
}
