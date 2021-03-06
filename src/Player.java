import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;


public class Player extends Character {
    private int dx;
    private int velocity;
    private int lives;
    private int invulnDur;
    private boolean JUMPING;
    private boolean PEAKED;
    private boolean WALKING;
    private boolean GODMODE;
    private int LAND_Y;
    private Image jumpSprite;
    private Clip clip;
    private Clip clip2;


    public Player() throws Exception {
        this(0, 0);
    }

    public Player(int x, int y) throws Exception {
        FRAMES = 11;
        lives = 3;
        this.x = x;
        this.y = y;
        sprite = new Image[FRAMES];
        for (int i = 0; i < FRAMES; i++) {
        	Image newImage = new ImageIcon(this.getClass().getResource("resources/Player/p4_walk/PNG/charwalk" + String.format("%02d", i+1) + ".png")).getImage();

            sprite[i] = newImage;
        }
        
        jumpSprite = new ImageIcon(this.getClass().getResource("resources/Player/p4_walk/PNG/charjump02.png")).getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT);
       try {
           jumpSprite = new ImageIcon(this.getClass().getResource("resources/Player/p4_walk/PNG/charjump02.png")).getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT);
           
           InputStream is = getClass().getResourceAsStream("resources/Sounds/Jump.wav");
           InputStream bufferedIn = new BufferedInputStream(is);
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
           clip = AudioSystem.getClip();
           clip.open(audioInputStream);
           
           InputStream is2 = getClass().getResourceAsStream("resources/Sounds/step.wav");
           InputStream bufferedIn2 = new BufferedInputStream(is2);
           AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(bufferedIn2);
           clip2 = AudioSystem.getClip();
           clip2.open(audioInputStream2);
       }catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Failed to load background music: " + e, "Error", JOptionPane.ERROR_MESSAGE);
       }
        this.curFrame = 0;
        JUMPING = false;
        WALKING =true;
        GODMODE = false;
        invulnDur = 0;
        velocity = 40;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setLAND_Y(int LAND_Y) {
        this.LAND_Y = LAND_Y;
    }

    public Image getSprite() {
        if (JUMPING) {
            return jumpSprite;
        } else {
            return sprite[curFrame];
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int n) {
        lives = n;
    }

    public void changeLives(int n) {
        if (!GODMODE)
            lives += (n);
    }
    public void transform() {
    	
    }

    public void updatePos() {
        x += dx;
        if(WALKING && y==LAND_Y) {
        	if(y==LAND_Y)
        	clip2.start();
        	clip2.loop(1);
           // clip2.setFramePosition(0);
        }
        if (JUMPING && !PEAKED) {
            if (y == LAND_Y) {
                clip.start();
                clip.setFramePosition(0);
            }
            if (velocity > 0) {
                y -= velocity;
                velocity *= 0.8;
            } else {
                PEAKED = true;
                velocity = 0;
            }
        } else {
            if ((y + velocity) < LAND_Y) {
                if (!PEAKED) {
                    velocity = 0;
                    PEAKED = true;
                }
                y += velocity;
                velocity += 3;
            } else {
                y = LAND_Y;
                
                PEAKED = false;
                velocity = 40;
            }
        }
    }

    public void checkInvulnerability() {
        if (invulnDur > 0)
            invulnDur --;
        else if (invulnDur == 0)
            GODMODE = false;
    }

    public boolean isGODMODE() { return GODMODE; }

    public int getInvulnDur() {
        return invulnDur;
    }

    public void setInvulnDur(int n) {
        GODMODE = true;
        invulnDur = n;
    }

    public void jump(boolean b) throws Exception {
        if (b) {
            if (!JUMPING && !PEAKED)
                //LAND_Y = y;
            	JUMPING = true;
        } else JUMPING = false;
    }
}
