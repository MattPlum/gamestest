import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel{
	private static final long serialVersionUID = 1L;

    private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public int iWIDTH = WIDTH, iHEIGHT = HEIGHT, buttonWIDTH = 273, buttonHEIGHT = 108;
    private boolean isSplash;
    private JPanel screen = new JPanel();
    
    final BufferedImage bg = ImageIO.read(Menu.class.getResourceAsStream("resources/Background/UD3.png"));
    final BufferedImage yogi = ImageIO.read(Menu.class.getResourceAsStream("resources/Player/p4_walk/PNG/charjump01.png"));
    final BufferedImage startButton = ImageIO.read(Menu.class.getResourceAsStream("resources/Menu/startButton.png"));



    private int frameWidth, frameHeight;

	public Menu (boolean isSplash) throws IOException {
		this.isSplash = isSplash;
        this.frameWidth = getWidth();
        this.frameHeight = getHeight();

        if (isSplash == true) {
            screen.repaint();
        }
	}

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

        //draw BG
        g.drawImage(bg, 0, 0, iWIDTH, iHEIGHT, null);
        

        //draw Me
        int yogiWIDTH = 233, yogiHEIGHT = 290;
        g.drawImage(yogi, WIDTH / 2 - (yogiWIDTH / 2), HEIGHT - yogiHEIGHT - 70, yogiWIDTH, yogiHEIGHT, null);

        //draw start button
        g.drawImage(startButton, WIDTH / 2 - (buttonWIDTH / 2), HEIGHT / 2 - buttonHEIGHT, buttonWIDTH, buttonHEIGHT, null);

		repaint();
	}
}