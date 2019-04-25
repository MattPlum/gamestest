import java.awt.Image;

import javax.swing.ImageIcon;

public class SkillBuild extends Enemy{

	public SkillBuild(int x, int y, int speed) {
		super(x, y, speed);
	   	FRAMES = 30;
	    this.x = x;
	    this.y = y;
	    this.speed = speed;
	    sprite = new Image[FRAMES];
	
	    for (int i = 0; i < FRAMES; i++) {
    		sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/monkey/ethical" + "_(" + (i+1) + ").png")).getImage();

	    }
	}

}
