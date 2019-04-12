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

    final BufferedImage bg = ImageIO.read (new File(getClass().getResource("resources/Background/UD3.jpg").getPath()));
    final BufferedImage yogi = ImageIO.read (new File(getClass().getResource("resources/Player/p4_walk/PNG/charjump01.png").getPath()));
    //final BufferedImage logo = ImageIO.read (new File(getClass().getResource("resources/Menu/logo.png").getPath()));
    final BufferedImage startButton = ImageIO.read (new File(getClass().getResource("resources/Menu/startButton.png").getPath()));
    
    private Font scoreFont=new Font("Calibri", Font.BOLD, 56);
    private FontMetrics metric;

    private int frameWidth, frameHeight;

	public Menu (boolean isSplash) throws IOException {
		this.isSplash = isSplash;
        this.frameWidth = getWidth();
        this.frameHeight = getHeight();
        scoreFont = new Font("Calibri", Font.BOLD, 56);

        if (isSplash == true) {
            screen.repaint();
        }
	}

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

        //draw BG
        g.drawImage(bg, 0, 0, iWIDTH, iHEIGHT, null);
        
        //draw game title
        metric = g.getFontMetrics(scoreFont);
        Font largeScoreFont = new Font("Calibri", Font.BOLD, 100);

        String message1 = "My Leadership, The Game";
        g.setColor(Color.YELLOW);
        g.fillRect(frameWidth/2-metric.stringWidth(message1)/2+718, 210, 1100, 150);

        g.setColor(Color.BLUE);
        g.setFont(largeScoreFont);
        g.drawString(message1, frameWidth/2-metric.stringWidth(message1)/2+730, 330);
        

        //draw Me
        int yogiWIDTH = 233, yogiHEIGHT = 290;
        g.drawImage(yogi, WIDTH / 2 - (yogiWIDTH / 2), HEIGHT - yogiHEIGHT - 70, yogiWIDTH, yogiHEIGHT, null);

        //draw start button
        g.drawImage(startButton, WIDTH / 2 - (buttonWIDTH / 2), HEIGHT / 2 - buttonHEIGHT, buttonWIDTH, buttonHEIGHT, null);



		repaint();
	}
}