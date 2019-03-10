import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Enemy extends Character {
    private int speed;
    

    
    public Enemy(int x, int y, int speed) {
        FRAMES = 3;
        this.x = x;
        this.y = y;
        this.speed = speed;
        sprite = new Image[FRAMES];
        for (int i = 0; i < FRAMES; i++) {
        		sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/sloth_" + (i) + ".png")).getImage();
        	}
        curFrame = 0;
    }

    public void updatePos() {
        x += speed;
    }
}
