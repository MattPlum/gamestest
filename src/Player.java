import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by weijiangan on 02/12/2016.
 */

public class Player {
    private final int FRAMES = 11;
    private int x, y;
    private int dx;
    private int velocity;
    private int curFrame;
    private int lives;
    private int invulnDur;
    private boolean JUMPING;
    private boolean PEAKED;
    private boolean GODMODE;
    private int LAND_Y;
    private Image[] sprite = new Image[FRAMES];
    private Image jumpSprite;
    private Clip clip;

    public Player() throws Exception {
        this(0, 0);
    }

    public Player(int x, int y) throws Exception {
        lives = 3;
        this.x = x;
        this.y = y;
        for (int i = 0; i < FRAMES; i++) {
            sprite[i] = new ImageIcon(this.getClass().getResource("resources/Player/p3_walk/PNG/p3_walk" + String.format("%02d", i+1) + ".png")).getImage();
        }
        jumpSprite = new ImageIcon(this.getClass().getResource("resources/Player/p3_jump.png")).getImage();
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(getClass().getResource("resources/Jump.wav").getPath())));
        this.curFrame = 0;
        JUMPING = false;
        GODMODE = false;
        invulnDur = 0;
        velocity = 40;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public void updatePos() {
        x += dx;
        if (JUMPING && !PEAKED) {

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
                LAND_Y = y;

            JUMPING = true;
            clip.start();
            clip.setMicrosecondPosition(0);
        }
        else JUMPING = false;
    }

    public void nextFrame() {
        if (curFrame == (FRAMES-1))
            curFrame = 0;
        else
            curFrame++;
    }

    public Rectangle getBounds() {
        return (new Rectangle(x, y, sprite[curFrame].getWidth(null), sprite[curFrame].getHeight(null)));
    }

    public BufferedImage getBI() {
        BufferedImage bi = new BufferedImage(sprite[curFrame].getWidth(null), sprite[curFrame].getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(sprite[curFrame], 0, 0, null);
        g.dispose();
        return bi;
    }
}
