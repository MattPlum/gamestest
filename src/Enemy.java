import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Enemy extends Character {
    public int speed;
 
    public Enemy(int x, int y, int speed) {
        curFrame = 0;
    }
    

   
    public void updatePos() {
        x += speed;
    }
}
