package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Hello preethi");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);
        Image test = new Image("file:/resources/menubg.gif");
        BackgroundImage bgImg = new BackgroundImage(test,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        // put stuff in root as normal....
        ImagePattern pattern = new ImagePattern(test);
        scene.setFill(pattern);
        primaryStage.setScene(scene);
        primaryStage.show();


        primaryStage.show();

        //make stuff (buttons, etc.)
        //add that stuff to a scene
        //show that scene

    }


    public static void main(String[] args) {
        launch(args);
    }
}
