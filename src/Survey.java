import java.awt.Image;

import javax.swing.ImageIcon;

public class Survey extends Enemy{

	public Survey(int x, int y, int speed) {
		super(x, y, speed);
	   	FRAMES = 7;
	    this.x = x;
	    this.y = y;
	    this.speed = speed;
	    sprite = new Image[FRAMES];
	
	    for (int i = 0; i < FRAMES; i++) {
	    		sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/survey/survey" + (i) + ".png")).getImage();
	    }
	}

}