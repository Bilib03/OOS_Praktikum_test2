package ui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.application.Application;
import java.io.IOException;

/*
import javafx.*;
import java.lang.ClassLoader;
import java.lang.Object;
import java.lang.Class;
 */


public class FxApplication extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws IOException {

            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getClassLoader()
                    .getResource("Mainview.fxml"));
            Parent root =(Parent) fxmlLoader.load();
            primaryStage.setScene(new Scene(root,600,400));
            primaryStage.setTitle("OOS P5");
            primaryStage.show();
    }
}
