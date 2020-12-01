package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.layout.Pane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello preethi");
        Button b = new Button("Click Me");
        b.setText("Click me if you dare!");
        Scene scene = new Scene(b, 400, 400);
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
