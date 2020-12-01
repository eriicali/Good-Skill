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

        StackPane root = new StackPane();
        Image test = new Image("file:../../resources/menubg.gif");
        BackgroundImage bgImg = new BackgroundImage(test,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        root.setBackground(new Background(bgImg));
        Scene scene = new Scene(root, 640, 480);
// put stuff in root as normal....

        primaryStage.setScene(scene);
        primaryStage.show();




        //make stuff (buttons, etc.)
        //add that stuff to a scene
        //show that scene

    }


    public static void main(String[] args) {
        launch(args);
    }
}
