package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.*;

public class Controller implements Initializable {
    @FXML public  Pane field;

    @FXML public Rectangle player1, player2, bullet1, bullet2;
    @FXML public Rectangle player1HPF, player1HP, player2HPF, player2HP;
    @FXML public  Label player1name ;
    @FXML public  Label player2name ;
    public final Circle protect1 = new Circle (100, 100, 80);
    public final Circle protect2 = new Circle ();
       
    private Boolean[] go1 = {false, false, false, false}; // up down left right
    private Boolean[] go2 = {false, false, false, false};

    //GET user is shooted or not
    private Boolean isInsidePlayer(Rectangle player, double x, double y) {
        if(x >= player.getLayoutX() && x <= player.getLayoutX() + player.getWidth() && y >= player.getLayoutY() && y <= player.getLayoutY() + player.getHeight()) return true;
        else return false;
    }

    // series shoot
    LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private void addBullet(Bullet newBullet) {
        bullets.push(newBullet);
        field.getChildren().add(newBullet.getObj());
    }
    private void update_bullet(Bullet bullet) {
        bullet.update();
    }
   //determine is protected
   private Boolean isProtected (Boolean startprt, Rectangle player, Circle prtring)
   {
	   Boolean prt = false;
	   if (startprt)
	   {
	   if ( Math.sqrt(player.getWidth()*player.getHeight()) >= prtring.getRadius())
	   {
		   prtring.setVisible(false);
		   prtring.setRadius(0);
		   prt = false;
	   }
	   else
	   {
		   prt = true;
	   }
	   }
	   else
	   {
		   prt = false;
	   }
	   return prt;
   }

    //players move his block
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W: go1[0] = true; break;
            case S: go1[1] = true; break;
            case A: go1[2] = true; break;
            case D: go1[3] = true; break;
            default: break;
        }

        switch (keyEvent.getCode()) {
            case UP: go2[0] = true; break;
            case DOWN: go2[1] = true; break;
            case LEFT: go2[2] = true; break;
            case RIGHT: go2[3] = true; break;
            default: break;
        }
    }
    @FXML
    public void handleKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W: go1[0] = false; break;
            case S: go1[1] = false; break;
            case A: go1[2] = false; break;
            case D: go1[3] = false; break;
            default: break;
        }
        switch (keyEvent.getCode()) {
            case UP: go2[0] = false; break;
            case DOWN: go2[1] = false; break;
            case LEFT: go2[2] = false; break;
            case RIGHT: go2[3] = false; break;
            default: break;
        }
    }



    // shoot bullet functions 
    // TODO: delete out of view bullets
    public void shoot() {
        Bullet newBullet1 = new Bullet(bullet1, player1, player2, true);
        Bullet newBullet2 = new Bullet(bullet2, player1, player2, false);
        addBullet(newBullet1);
        addBullet(newBullet2);


        Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000 / 30), (e) -> {
            update_bullet(newBullet1);
            if(isInsidePlayer(player2, newBullet1.getObj().getLayoutX(), newBullet1.getObj().getLayoutY())  &&  !isProtected(true,player2,protect2)) {
            	// MINUS THE HP
                player2HP.setWidth(Math.max(0, player2HP.getWidth() - player2HPF.getWidth() * 0.05));
            }
        }));
        fps.setCycleCount(Timeline.INDEFINITE);
        fps.play();

        Timeline fps2 = new Timeline(new KeyFrame(Duration.millis(1000 / 30), (e) -> {
            update_bullet(newBullet2);
            if(isInsidePlayer(player1, newBullet2.getObj().getLayoutX(), newBullet2.getObj().getLayoutY())  && !isProtected(true,player1,protect1)) {
            	// MINUS THE HP
                player1HP.setWidth(Math.max(0, player1HP.getWidth() - player1HPF.getWidth() * 0.05));
            }
        }));
        fps2.setCycleCount(Timeline.INDEFINITE);
        fps2.play();
    }

    
    


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    	//get players border
       double [] playerborder1 = {player1.getHeight() ,player1.getWidth()};
       double [] playerborder2 = {player2.getHeight() ,player2.getWidth()};
       double [] tempposition1 = {0,0};
       double [] tempposition2 = {0,0};
       
       //Show two players' name 
       player1name.setText("Player1: "+Menu.Playername1);
       player2name.setText("Player2: "+Menu.Playername2);
       
       //add two players' protect ring
       field.getChildren().add(protect1);
       field.getChildren().add(protect2);

        // TODO: handle player out of view
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	tempposition1[0] = player1.getLayoutY();
            	tempposition1[1] = player1.getLayoutX();
            	
            	tempposition2[0] = player2.getLayoutY();
            	tempposition2[1] = player2.getLayoutX();
            	//for player01
            	
            	double [] nextposition = move_player(go1,tempposition1,playerborder1,tempposition2,playerborder2);
            	
            	player1.setLayoutY(nextposition[0]);
            	player1.setLayoutX(nextposition[1]);
            	
            	//for player02
            	nextposition = move_player(go2,tempposition2,playerborder2,tempposition1,playerborder1);
            	player2.setLayoutY(nextposition[0]);
            	player2.setLayoutX(nextposition[1]);
            	
            }
        };
        timer.start();
        
        // add players' protect ring 
        AnimationTimer circle = new AnimationTimer()
        {
        	@Override
        	public void handle(long now) {
        		//for player1 protect ring 
        		protect1.setCenterX(player1.getLayoutX()+player1.getWidth()/2);
        		protect1.setCenterY(player1.getLayoutY()+player1.getHeight()/2);
        		protect1.setVisible(true);
        		
        		//for player2 protect ring
        		protect2.setCenterX(player2.getLayoutX()+player2.getWidth()/2);
        		protect2.setCenterY(player2.getLayoutY()+player2.getHeight()/2);
        		protect2.setVisible(true);
        	}
        };
        circle.start();
        
        Timeline Circle = new Timeline(new KeyFrame(Duration.millis(2000), (e) -> { 
        	if (isProtected(true,player1,protect1))
        	{
        		protect1.setRadius(protect1.getRadius() * 0.8);
        	}
        	if (isProtected(true,player2,protect2))
        	{
        		protect2.setRadius(protect2.getRadius() * 0.8);
        	}
        	System.out.println(protect1.getRadius() );
        }));
        Circle.setCycleCount(Timeline.INDEFINITE);
        Circle.play();

        Timeline fps = new Timeline(new KeyFrame(Duration.millis(2000), (e) -> { shoot(); }));
        fps.setCycleCount(Timeline.INDEFINITE);
        fps.play();


    }
    private double [] move_player (Boolean []  go ,double [] position , double [] border,double [] otherposition ,double [] otherborder) {
    	
    	double nextpositionY = position[0];
    	double nextpositionX = position[1];
    	double [] nextposition = {0,0};
    	//moving
    	if(go[0]) 
    	{ nextpositionY = position[0] - 5;}
    	if(go[1]) 
    	{ nextpositionY = position[0] + 5;}
    	if(go[2]) 
    	{ nextpositionX = position[1] - 5;}
    	if(go[3]) 
    	{ nextpositionX = position[1] + 5;}
    	
    	//prevent out of border
    	double borderY = border[0] + nextpositionY;
    	double borderX = border[1] + nextpositionX;
    	if (borderY > Main.scenesizeY || nextpositionY  < 0)
    	{
    		nextpositionY = position[0];
    	}
    	if (borderX > Main.scenesizeX || nextpositionX  < 0)
    	{
    		nextpositionX = position[1];
    	}
    	
    	//prevent two user overlay
    	if( (borderY >= otherposition[0] && borderY <= otherborder[0]+otherposition[0])||(nextpositionY <= otherborder[0]+otherposition[0] && nextpositionY >= otherposition[0]))
    	{    	
    	if ((borderX >= otherposition[1] && borderX <= otherborder[1]+otherposition[1])||(nextpositionX <= otherborder[1]+otherposition[1] && nextpositionX >= otherposition[1]))
    	{
    		nextpositionX = position[1];
    		nextpositionY = position[0];
    	}
    	}
    	nextposition[0] = nextpositionY;
    	nextposition[1] = nextpositionX;
    	//System.out.println(nextposition[0] + " " + nextposition[1]);
    	//System.out.println(Main.scenesizeY);
    	
    return nextposition;
    }
    
}

