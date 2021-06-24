package sample;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.animation.*;

public class Bullet {
    private Rectangle obj;
    private double x, y;
    private Boolean type;
    private KeyFrame kf;

    //
    public Bullet(Rectangle _obj, Rectangle player1, Rectangle player2, Boolean isPlayer1) {
        this.obj = new Rectangle(_obj.getWidth(), _obj.getHeight());
        this.obj.setFill(_obj.getFill());
        this.x = player1.getLayoutX() - player2.getLayoutX();
        this.y = player1.getLayoutY() - player2.getLayoutY();
        var dis = Math.sqrt(x * x + y * y);
        this.x = this.x / dis;
        this.y = this.y / dis;
        this.type = isPlayer1;

        if(isPlayer1) {
            this.obj.setLayoutX(player1.getLayoutX() + player1.getWidth() - this.obj.getWidth());
            this.obj.setLayoutY(player1.getLayoutY() + player1.getHeight() / 2 - this.obj.getHeight() / 2);
            this.obj.setRotate(Math.toDegrees(Math.atan(y / x)));
            this.x = -this.x;
            this.y = -this.y;
        } else {
            this.obj.setLayoutX(player2.getLayoutX());
            this.obj.setLayoutY(player2.getLayoutY() + player2.getHeight() / 2 - this.obj.getHeight() / 2);
            this.obj.setRotate(Math.toDegrees(Math.atan(y / x)));
        }
    }
    // 
    public void update() {
        obj.setLayoutX(obj.getLayoutX() + x * 50);
        obj.setLayoutY(obj.getLayoutY() + y * 50);
        if(outOfView())
        {
        	obj.setVisible(false);
        }
    }
    public  Boolean outOfView() {
        if(obj.getLayoutX() > Main.scenesizeX || obj.getLayoutX() < 0) return true;
        if(obj.getLayoutY() > Main.scenesizeY || obj.getLayoutY() < 0) return true;
        return false;
    }
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public Rectangle getObj() {
        return obj;
    }
}
