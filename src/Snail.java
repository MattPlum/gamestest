import java.awt.Image;

import javax.swing.ImageIcon;

public class Snail extends Enemy{

	public Snail(int x, int y, int speed) {
		super(x, y, speed);
		FRAMES = 2;
		this.x = x;
		this.y = y;
		this.speed = speed;
		sprite = new Image[FRAMES];
		for (int i = 0; i < FRAMES; i++) {
           sprite[i] = new ImageIcon(this.getClass().getResource("resources/Enemies/snailWalk" + (i+1) + ".png")).getImage();
       }
	}

}
