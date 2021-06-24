package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static  Stage currentStage;
	public static  Scene currentScene;
	public static double scenesizeX = 0;
    public static double scenesizeY = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{

    	currentStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        currentStage.setTitle("Due Battle-Settings");
        currentScene = new Scene(root, 1200, 800);
        currentScene.getRoot().requestFocus();
        currentStage.setScene(currentScene);
        currentStage.show();
        
        //get Scene size
        scenesizeX = Main.currentStage.getWidth();
        scenesizeY=  Main.currentStage.getHeight();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
