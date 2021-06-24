package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class Menu {
	
	@FXML public AnchorPane Field;
	
	@FXML public Label Title;
	@FXML public Label Player1;
	@FXML public Label Player2;
	@FXML public TextField  player1name;
	@FXML public TextField player2name;
	@FXML public Button OK;
	
	public static String Playername1;
	public static String Playername2;
	
	public void startgame() throws Exception
	{
		if(getusername()) 
		{
			Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
			Scene PlayScene = new Scene (root,1200,800);
			Main.currentStage.setTitle("Due Battle");
			Main.currentStage.setScene(PlayScene);
			PlayScene.getRoot().requestFocus();
		   

		}
	}
	
	public boolean getusername ()
	{
		boolean status = false;
	if(player1name.getText() != null && player2name.getText() != null && !player1name.getText().isEmpty() && !player2name.getText().isEmpty())
	{
		Playername1 = player1name.getText();
		Playername2 = player2name.getText();
		System.out.println(Playername1 + " " + Playername2);
		status = true ;
	}
	else
	{
		Label warning = new Label ("Please enter both players' name");
		warning.setStyle("-fx-text-fill: red; -fx-background-color: #cccccc; -fx-border-radius:5px; -fx-background-radius: 5px;");
		warning.setPrefHeight(30.0);
		warning.setPrefWidth(279.0);
		this.Field.getChildren().add(warning);
		warning.setLayoutX(206.0);
		warning.setLayoutY(230.0);
		if (player1name.getText() == null || player1name.getText().isEmpty())
		{
			player1name.setStyle("-fx-border-color: red; -fx-border-radius:5px; -fx-background-radius: 5px; -fx-background-color: #cccccc;");
		}
		else
		{
			player1name.setStyle("-fx-background-radius: 5px; -fx-background-color: #cccccc;");
		}
		
		if (player2name.getText() == null || player2name.getText().isEmpty())
		{
			player2name.setStyle("-fx-border-color: red; -fx-border-radius:5px; -fx-background-radius: 5px; -fx-background-color: #cccccc;");
		}
		else
		{
			player2name.setStyle("-fx-background-radius: 5px; -fx-background-color: #cccccc;");
		}
		
		status = false ;
	}
	
	return status;
	}	
		

	

}
