import java.awt.Image;

import javax.swing.ImageIcon;

public class DP extends Enemy{

	public DP(int x, int y, int speed) {
		super(x, y, speed);
	   	FRAMES = 5;
	    this.x = x;
	    this.y = y;
	    this.speed = speed;
	    sprite = new Image[FRAMES];
	
	    for (int i = 0; i < FRAMES; i++) {
	    		sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/DP/DP" + (i+1) + ".png")).getImage();
	    }
	}

}