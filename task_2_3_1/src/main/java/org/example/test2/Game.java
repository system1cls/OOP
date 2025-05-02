package org.example.test2;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

public class Game extends Task {

    Stage stage;
    int width;
    int height;
    int dest;
    int delay = 200;
    int curPoints = 0;
    Text points;
    Text destText;
    Text winner;
    Text looser;
    StackPane gamePane;
    Level level;

    Game(Stage stage, int width, int height, int dest, Level level) {
        this.dest = dest;
        this.height = height;
        this.width = width;
        this.stage = stage;
        this.level = level;
    }

    void startGame () {
        StackPane pane = new StackPane();
        this.gamePane = pane;
        level.setPane(pane);
        Canvas canvas = new Canvas(width * 10, height * 10);
        level.addContext(canvas.getGraphicsContext2D());

        Text score = new Text("Score: 0");
        Text destText = new Text("Dest: " + Integer.toString(this.dest*100));
        Text winner = new Text("You Win");
        Text looser = new Text("You lose");
        this.points = score;
        this.winner = winner;
        this.looser = looser;
        score.setVisible(true);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);
        pane.getChildren().add(score);
        pane.getChildren().add(destText);
        pane.getChildren().add(winner);
        pane.getChildren().add(looser);
        winner.setVisible(false);
        looser.setVisible(false);
        pane.setAlignment(score, Pos.TOP_LEFT);
        pane.setAlignment(destText, Pos.TOP_RIGHT);
        pane.setAlignment(winner, Pos.TOP_CENTER);
        pane.setAlignment(looser, Pos.TOP_CENTER);



        stage.getScene().setRoot(pane);


        gameLoop(level);
    }

    void gameLoop (Level level){

            int res = 0;
            int cntCheck = 1;

            while (res >= 0) {

                long t1 = System.currentTimeMillis();

                for (int i = 0; i < cntCheck; i++) {
                    System.out.println("New Move");
                    res = level.checkColAndMove();

                    switch (res) {
                        case -1:
                            this.looser.setVisible(true);
                            break;
                        case -2:
                            this.winner.setVisible(true);
                            addPints();
                            level.show();
                            break;
                        case 1:
                            addPints();
                            level.show();
                            break;
                        default:
                            level.show();
                            break;
                    }
                }

                long t2 = System.currentTimeMillis();
                cntCheck = (int)(t2 - t1) / delay + 1;

                try {
                    if (delay - (t2 - t1) > 0)
                        Thread.sleep(delay - (t2 - t1));
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException();
                }
            }
    }

    void addPints() {
        curPoints += 100;
        this.points.setText("Score: " + curPoints);
    }

    @Override
    protected Object call() throws Exception {
        startGame();
        return null;
    }
}
