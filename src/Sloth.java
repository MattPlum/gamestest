import java.awt.Image;

import javax.swing.ImageIcon;

public class Sloth extends Enemy{

	public Sloth(int x, int y, int speed) {
		super(x, y, speed);
	   	FRAMES = 3;
	    this.x = x;
	    this.y = y;
	    this.speed = speed;
	    sprite = new Image[FRAMES];
	
	    for (int i = 0; i < FRAMES; i++) {
	    		sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/sloth_" + (i) + ".png")).getImage();
	    }
	}

}
