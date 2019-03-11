import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Enemy extends Character {
    private int speed;
    Boolean sloth= false;
    Boolean snail = false;
    public Enemy(int x, int y, int speed, int num) {
	    System.out.println(sloth);

    	if(num ==0) {
	    	FRAMES = 3;
	        this.x = x;
	        this.y = y;
	        this.speed = speed;
	        sprite = new Image[FRAMES];
        
		    for (int i = 0; i < FRAMES; i++) {
		    		sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/sloth_" + (i) + ".png")).getImage();
		    }
	        sloth = true;

        }
    	else if(num == 1) {
            FRAMES = 2;
            this.x = x;
            this.y = y;
            this.speed = speed;
            sprite = new Image[FRAMES];
            for (int i = 0; i < FRAMES; i++) {
                sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/snailWalk" + (i+1) + ".png")).getImage();
            }
            snail=true;
        }

        curFrame = 0;
    }
    

    public void updatePos() {
        x += speed;
    }
}
