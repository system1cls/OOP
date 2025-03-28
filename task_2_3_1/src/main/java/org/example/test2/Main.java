package org.example.test2;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import javax.swing.Timer;
import java.io.IOException;

public class Main extends Application {
    Stage stage;


    @FXML TextField Width;
    @FXML TextField Height;
    @FXML TextField Dest;
    @FXML Button startButton;
    @FXML Text ErrorText;


    @FXML protected void handleButton(ActionEvent e) {
        int width, height, dest;


        try {
            width = Integer.parseInt(Width.getText());
            height = Integer.parseInt(Height.getText());
            dest = Integer.parseInt(Dest.getText());
        } catch (NumberFormatException ex) {
            ErrorText.setVisible(true);
            ErrorText.setText("Wrong args");
            return;
        }

        if (width > 50 || width < 0) {
            ErrorText.setVisible(true);
            ErrorText.setText("Wrong Width");
        }
        else if (height > 50 || height < 0) {
            ErrorText.setVisible(true);
            ErrorText.setText("Wrong Height");
        }
        else if (dest > 30 || dest < 0) {
            ErrorText.setVisible(true);
            ErrorText.setText("Wrong Destination");
        }
        else {
            System.out.println("Starting");
            Game game = new Game(stage, width, height, dest);
            Thread thread = new Thread(game);
            thread.start();
        }

    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("1.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene menu = new Scene(root,  600, 400);
        stage.setScene(menu);

        this.stage = stage;

        ErrorText.setVisible(false);

        stage.setTitle("Snake");

        stage.show();
    }

    class Game extends Task {
            Stage stage;
            int width;
            int height;
            int dest;
            int delay = 500;
            int curPoints = 0;
            Text points;
            Text destText;

            Game(Stage stage, int width, int height, int dest) {
                this.dest = dest;
                this.height = height;
                this.width = width;
                this.stage = stage;
            }

            void startGame () {
            StackPane pane = new StackPane();
            Canvas canvas = new Canvas(width * 10, height * 10);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            pane.getChildren().add(canvas);

            MyPlayerSnake snake = new MyPlayerSnake(width / 2, height / 2, stage.getScene());
            CustomLevel level = new CustomLevel(width, height, 3, 0, 0, gc, snake, dest);

            stage.setWidth(width * 10);
            stage.setHeight(height * 10);
            stage.getScene().setRoot(pane);


            gameLoop(level, gc);
        }

            void gameLoop (Level level, GraphicsContext gc){
            Timer timer = new Timer(delay, e -> {
                System.out.println("New Move");
                int res = level.checkColAndMove();
                switch (res) {
                    case -1:
                        System.out.println("Game Over");
                        ((Timer) e.getSource()).stop();
                        break;
                    case -2:
                        System.out.println("You win");
                        level.show();
                        ((Timer) e.getSource()).stop();
                        break;
                    case 1:
                        addPints();
                    default:
                        level.show();
                        break;
                }
            });

            timer.start();
        }

        void addPints() {
            curPoints += 100;

        }

        @Override
        protected Object call() throws Exception {
            startGame();
            return null;
        }
    }
    public static void main(String[] args) {
        launch();
    }
}