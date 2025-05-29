package org.example.test2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.example.test2.Logger.EmptyLogger;
import org.example.test2.Logger.ILogger;
import org.example.test2.Logger.SimpleLogger;
import org.example.test2.levels.CustomLevel;
import org.example.test2.levels.Level;
import org.example.test2.levels.Level1;
import org.example.test2.levels.LevelPUBG;
import org.example.test2.snakes.MyPlayerSnake;


import java.io.IOException;

public class Main extends Application {
    Stage stage;
    GraphicsContext gc;

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

        if (width > 50 || width <= 0) {
            ErrorText.setVisible(true);
            ErrorText.setText("Wrong Width");
        }
        else if (height > 35 || height <= 0) {
            ErrorText.setVisible(true);
            ErrorText.setText("Wrong Height");
        }
        else if (dest > width * height / 2 || dest <= 0) {
            ErrorText.setVisible(true);
            ErrorText.setText("Wrong Destination");
        }
        else {
            System.out.println("Starting");
            SimpleLogger logger = new SimpleLogger();
            MyPlayerSnake snake = new MyPlayerSnake(width / 2, height / 2, stage.getScene());
            CustomLevel level = new CustomLevel(width, height, 3, 0, 0, snake, dest, logger);
            Game game = new Game(stage, width, height, dest, level, logger);
            Thread thread = new Thread(game);
            thread.setDaemon(true);
            thread.start();
        }

    }

    @FXML protected void startLevel1() {
        MyPlayerSnake snake = new MyPlayerSnake(3, 3, stage.getScene());
        SimpleLogger logger = new SimpleLogger();
        Level level = new Level1(0, 0, snake, logger);
        Game game = new Game(stage, 30, 30, 20, level, logger);
        Thread thread = new Thread(game);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML protected void startLevel2() {

    }

    @FXML protected void startLevel3() {
        MyPlayerSnake snake = new MyPlayerSnake(15, 15, stage.getScene());
        ILogger logger = new EmptyLogger();
        Level level = new LevelPUBG(0, 0, snake, logger);
        Game game = new Game(stage, 30, 30, 10, level, logger);
        Thread thread = new Thread(game);
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("1.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene menu = new Scene(root,  600, 400);
        stage.setScene(menu);
        stage.setResizable(false);

        this.stage = stage;

        ErrorText.setVisible(false);

        stage.setTitle("Snake");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}