import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Pong extends Application {

    private static final int appWidth = 1200;
    private static final int appHeight = 800;
    private static final int playerWidth = 15;
    private static final double ballDiameter = 20;
    private static int playerOneHeight = 100;
    private static int playerTwoHeight = 100;
    private double ballYSpeed = 3;
    private double ballXSpeed = 3;
    private double playerOneYPos = appHeight / 2;
    private double playerTwoYPos = appHeight / 2;
    private double reaction = 700;
    private double playerTwoSpeed = 50;
    private double ballXPos;
    private double ballYPos;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted = false;


    public void start(Stage stage) {
        playerOneHeight = 100;
        playerTwoHeight = 100;
        Canvas canvas = new Canvas(appWidth, appHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            try {
                run(gc);
            } catch (Exception ex) {
            }
        }));

        tl.setCycleCount(Timeline.INDEFINITE);
        canvas.setOnMouseMoved(e -> {
                    playerOneYPos = e.getY() - ((double) playerOneHeight) / 2;
                    if (playerOneYPos > appHeight - playerOneHeight) {
                        playerOneYPos = appHeight - playerOneHeight;
                    } else if (playerOneYPos < 0) {
                        playerOneYPos = 0;
                    }
                }
        );
        canvas.setOnScroll(e -> {
            if (e.getDeltaY() > 0) {
                ballXSpeed *= 2;
                ballYSpeed *= 2;
            } else if (ballXSpeed >= 0.1 && ballYSpeed >= 0.1) {
                ballXSpeed *= 0.5;
                ballYSpeed *= 0.5;
            }
        });
        canvas.setOnMouseClicked(e -> gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, appWidth, appHeight);
        double ballXPosPre = ballXPos;
        double ballYPosPre = ballYPos;
        double playerTwoSpeed = 1;
        int playerOneXPos = 0;
        double playerTwoXPos = appWidth - playerWidth;
        if (gameStarted) {
            if (ballXSpeed > 0) {
                double ballYSpeedTemp = ballYSpeed;
                while ((ballXPosPre < playerTwoXPos - ballDiameter)) {
                    ballXPosPre += ballXSpeed;
                    ballYPosPre += ballYSpeedTemp;
                    if (ballYPosPre > appHeight - ballDiameter || ballYPosPre < 0) ballYSpeedTemp *= -1;
                }
            }
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            if (ballYPosPre != playerTwoYPos + ((double) playerTwoHeight) / 2 - ballDiameter / 2
                    && ballXSpeed > 0 && ballXPos > reaction) {
                playerTwoSpeed = (ballYPosPre - (playerTwoYPos + ((double) playerTwoHeight) / 2 - ballDiameter / 2)) / this.playerTwoSpeed;
                playerTwoYPos += playerTwoSpeed;
            } else {
                playerTwoSpeed = (ballYPos - (playerTwoYPos + ((double) playerTwoHeight) / 2 - ballDiameter / 2)) / (this.playerTwoSpeed * 2);
                playerTwoYPos += playerTwoSpeed;
            }
            if (new Random().nextInt(100) > 90) {
                playerTwoYPos += (new Random().nextBoolean() ? 1 : -1) * 5;
            }
            playerTwoYPos += playerTwoSpeed;
            if (playerTwoYPos > appHeight - playerTwoHeight) {
                playerTwoYPos = appHeight - playerTwoHeight;
            } else if (playerTwoYPos < 0) {
                playerTwoYPos = 0;
            }
        } else {
            /* starting point of the ball*/
            ballXPos = playerWidth;
            ballYPos = playerOneYPos + ((double) playerOneHeight) / 2 - ballDiameter / 2;
//            ballXPos = playerTwoXPos - playerWidth;// starting point of the ball
//            ballYPos = playerTwoYPos + ((double) playerOneHeight) / 2 - ballDiameter / 2;
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("Click to Start", appWidth / 2, appHeight / 2);
        }
        if (ballYPos > appHeight - ballDiameter || ballYPos < 0) ballYSpeed *= -1;// bouncing off the side walls
        if (((ballXPos > playerTwoXPos - ballDiameter) && ballYPos >= playerTwoYPos - ballDiameter && ballYPos <= playerTwoYPos + playerTwoHeight + ballDiameter) ||
                ((ballXPos < playerWidth) && ballYPos >= playerOneYPos - ballDiameter && ballYPos <= playerOneYPos + playerOneHeight + ballDiameter)) {
            ballXSpeed *= -1;
            ballYSpeed += new Random().nextDouble() * (new Random().nextBoolean() ? 1 : -1);

            if (ballXSpeed < 10)
                ballXSpeed += 0.25 * Math.signum(ballXSpeed);
            if (ballYSpeed < 10)
                ballYSpeed += 0.25 * Math.signum(ballYSpeed);
        } else if (ballXPos < playerWidth) {//when player two wins
            if (playerTwoHeight < appHeight * 0.4)
                playerTwoHeight += 20;
            if (playerOneHeight > 100)
                playerOneHeight -= 50;
            reaction += 50;
            this.playerTwoSpeed += 2;
            scoreP2++;
            ballXSpeed = 3;
            ballYSpeed = 3;
            gameStarted = false;
        } else if (ballXPos > playerTwoXPos - ballDiameter) {//when player one wins
            if (playerOneHeight < appHeight * 0.4)
                playerOneHeight += 20;
            if (playerTwoHeight > 100)
                playerTwoHeight -= 50;
            scoreP1++;
            if (reaction > 0)
                reaction -= 50;
            if (this.playerTwoSpeed > 1)
                this.playerTwoSpeed -= 2;
            ballXSpeed = 3;
            ballYSpeed = 3;
            gameStarted = false;
        }
        gc.setFill(Color.WHITE);
        gc.fillText(scoreP1 + "                    Scroll up/down to increase/decrease pong speed                    " + scoreP2, appWidth / 2, 50);
        gc.setFont(Font.font(25));
        gc.fillOval(ballXPos, ballYPos, ballDiameter, ballDiameter);
        gc.fillRect(playerTwoXPos, playerTwoYPos, playerWidth, playerTwoHeight);
        gc.fillRect(playerOneXPos, playerOneYPos, playerWidth, playerOneHeight);
    }

}