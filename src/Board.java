import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class Board extends JPanel implements ComponentListener {
    Timer timer;
    private final int SPAWN_INTERVAL = 35;
    private boolean PLAYGAME;
    private boolean PAUSEGAME = false;
    private boolean GAMEOVER = false;
    private int frameWidth, frameHeight;
    private int LAND_HEIGHT = (int) (0.8 * frameHeight);
    
    private int PLAYER_X;
    private int PLAYER_Y;
    private int SNAIL_SPEED;
    private int NUM_OF_SNAILS;
    private int i = 0;
    int count =1;
    boolean isSloth=false;
    boolean isSkillBuild=false;
    boolean isCORE=false;
    boolean isWorkOn=false;
    boolean isDP = false;
    private int score;
    private int scoreWidth;
    int enemyNumber = 0;
    private Font scoreFont;
    private FontMetrics metric;
    private Terrain ground, ground2, background;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Iterator<Enemy> iter;

    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    Story story = new Story(SCREEN_WIDTH, SCREEN_HEIGHT);
    boolean showScreen = false;
    
    Clip clip;
    Clip clip2;

    public Board() throws Exception {
        addComponentListener(this);
        setDoubleBuffered(true);
        try {
            InputStream is = getClass().getResourceAsStream("resources/Sounds/well_done.wav");
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
    		clip = AudioSystem.getClip();	//endscreen sound
            clip.open(audioInputStream);
    		
            InputStream is2 = getClass().getResourceAsStream("resources/Sounds/prompt2.wav");
            InputStream bufferedIn2 = new BufferedInputStream(is2);
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(bufferedIn2);
            clip2= AudioSystem.getClip();	//prompt sound
    		clip2.open(audioInputStream2);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load background music: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        this.frameWidth = getWidth();
        this.frameHeight = getHeight();
        score = 0;
        setLayout(null);
        scoreWidth = 0;
        PLAYER_X = (int) (0.15 * frameWidth);
        SNAIL_SPEED = -7;
        NUM_OF_SNAILS = 1;
        enemies = new ArrayList<>();
        scoreFont = new Font("Calibri", Font.BOLD, 56);
        ground = new Terrain(-5, "resources/Tiles/grassMid.png");
        ground2 = new Terrain(-5, "resources/Tiles/grassCenter.png");

        background = new Terrain(-1, "resources/Background/UD2.jpg");
        player = new Player();
        player.setX(PLAYER_X);
        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PLAYGAME) {
                    ground.nextPos();
                    ground2.nextPos();
                    background.nextPos();
                    player.nextFrame();
                    player.updatePos();
                    if (i == SPAWN_INTERVAL) {	//at 35 spawn enemies, decrease i then add i repeatedly
                        spawnEnemies(count);
                        score++;
                        i = -1;
                    }
                    iter = enemies.iterator();
                    while (iter.hasNext()) {
                        Enemy tmp = iter.next();
                        if (tmp.getX() < -150) {	//remove if off screen
                            enemies.remove(tmp);
                            break;
                        }
                        tmp.nextFrame();
                        tmp.updatePos();
                    }
                    player.checkInvulnerability();
                }
                checkCollisions();
                repaint();
                i++;
            }
        });
        timer.start();
        PLAYGAME = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (PLAYGAME) {
            drawBackground(g);
            drawLand(g);
            drawPlayer(g);
            drawEnemies(g);
        //    drawHUD(g);
        } 
        if(PAUSEGAME) {
        	pauseGame();
        	drawHelp(g);
        	clip2.start();
        	clip2.setFramePosition(0);
        	if(isSloth) {
        		analogyScreen(g);
        	}else if(isSkillBuild) {
        		skillBuildScreen(g);
        	}else if(isCORE) {
        		coreScreen(g);
        	}else if(isWorkOn) {
        		workOnScreen(g);

        	}else if(isDP) {
        		dpScreen(g);
        	}
        }if(GAMEOVER) {
        	gameOver(g);  
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        	
        }
    }
   
    public void pauseGame() {
	      if (timer.isRunning()) {
		      timer.stop();
		      //game.timer.start();
	      } 
      }



    private void drawBackground(Graphics g) {
        for (int x = background.getInitX(); x < frameWidth; x += background.getW()) {
            g.drawImage(background.getSprite(), x, 0, null);
        }
        	
    }

    private void drawLand(Graphics g) {
        for (int y = LAND_HEIGHT; y < frameHeight; y += ground.getH()) {
            if (y == LAND_HEIGHT) {
                for (int x = ground.getInitX(); x < frameWidth; x += ground.getW()) {
                    g.drawImage(ground.getSprite(), x, y, null);
                }
            } else {
                for (int x = ground.getInitX(); x < frameWidth; x += ground2.getW()) {
                    g.drawImage(ground2.getSprite(), x, y, null);
                }
            }
        }
    }

 

    private void drawPlayer(Graphics g) {
        if (player.isGODMODE() && player.getInvulnDur() % 2 == 0)
            return;
        g.drawImage(player.getSprite(), player.getX(), player.getY(), this);
    }

    private void drawEnemies(Graphics g) {
        iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy tmp = iter.next();
            g.drawImage(tmp.getSprite(), tmp.getX(), tmp.getY(), null);
        }
    }

    
    private void analogyScreen(Graphics g) {	//sloth
    	showScreen = true;
    	Image answer = new ImageIcon(this.getClass().getResource("resources/Answers/Analogy_answer.png")).getImage();
    	g.drawImage(answer, (frameWidth / 2) - (answer.getWidth(null) / 2), (frameHeight / 2)-200 - (answer.getHeight(null) / 2), null);
        
    }
    private void dpScreen(Graphics g) {			//D art
    	showScreen = true;
    	Image answer = new ImageIcon(this.getClass().getResource("resources/Answers/DP_answer.png")).getImage();
        g.drawImage(answer, (frameWidth / 2) - (answer.getWidth(null) / 2), (frameHeight / 2)-200 - (answer.getHeight(null) / 2), null);
        
    }
    private void coreScreen(Graphics g) {		//CORE
    	showScreen = true;
    	Image answer = new ImageIcon(this.getClass().getResource("resources/Answers/CORE_answer.png")).getImage();
        g.drawImage(answer, (frameWidth / 2) - (answer.getWidth(null) / 2), (frameHeight / 2)-200 - (answer.getHeight(null) / 2), null);
        
    }

    private void skillBuildScreen(Graphics g) {		//monkey
    	showScreen = true;
    	Image answer = new ImageIcon(this.getClass().getResource("resources/Answers/SkillBuild_answer.png")).getImage();
        g.drawImage(answer, (frameWidth / 2) - (answer.getWidth(null) / 2), (frameHeight / 2)-200 - (answer.getHeight(null) / 2), null);
        
    }
    private void workOnScreen(Graphics g) {		//Squidward
    	showScreen = true;
    	Image answer = new ImageIcon(this.getClass().getResource("resources/Answers/effecleader_answer.png")).getImage();
        g.drawImage(answer, (frameWidth / 2) - (answer.getWidth(null) / 2), (frameHeight / 2)-200 - (answer.getHeight(null) / 2), null);
        
    }

    private void drawHelp(Graphics g) {
        Font largeScoreFont = new Font("Calibri", Font.BOLD, 25);
        String message1 = "Double Press Space to continue";
        metric = g.getFontMetrics(largeScoreFont);

        g.setColor(Color.YELLOW);
        g.fillRect(frameWidth/2-metric.stringWidth(message1)/2+40, 610, 345, 50);
        
        g.setColor(Color.BLUE);
        g.setFont(largeScoreFont);
        g.drawString(message1, frameWidth/2-metric.stringWidth(message1)/2+50, 650);


    }
    private void gameOver(Graphics g) {
    	PAUSEGAME=true;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);
        Image gameOver = new ImageIcon(this.getClass().getResource("resources/Menu/gameOver2.png")).getImage();
        g.drawImage(gameOver, (frameWidth / 2) - (gameOver.getWidth(null) / 2), (frameHeight / 2) - (gameOver.getHeight(null) / 2), null);
        Font largeScoreFont = new Font("Calibri", Font.BOLD, 75);
        metric = g.getFontMetrics(scoreFont);
        FontMetrics metric2 = g.getFontMetrics(scoreFont);
        scoreWidth = metric2.stringWidth(String.format("%d", score));
        String message1 = "Congratulations! Thanks for playing";
        String message2 = "Press space to play again";
        g.setColor(Color.WHITE);
        g.setFont(largeScoreFont);
        g.drawString(message1, frameWidth/2-metric.stringWidth(message1)/2-200, 100);
        g.setFont(scoreFont);
        g.drawString("Time Played: "+ String.format("%d", score) + " seconds", frameWidth/2-scoreWidth-270, 200);
        g.drawString(message2, frameWidth/2-metric.stringWidth(message2)/2, frameHeight - 100);
    }
    


    @Override
    public void componentResized(ComponentEvent e) {
        timer.stop();
        frameHeight = getHeight();
        frameWidth = getWidth();
        LAND_HEIGHT = (int) (0.85 * frameHeight);
    
        PLAYER_X = (int) (0.15 * frameWidth);
        PLAYER_Y = LAND_HEIGHT - player.getSprite().getHeight(null) + 5;
        player.setX(PLAYER_X);
        player.setY(PLAYER_Y);
        player.setLAND_Y(PLAYER_Y);
     
        iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy tmp = iter.next();
            tmp.setY(LAND_HEIGHT - 400 + 5);
        }
        timer.start();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

   

    public void keyPressed(KeyEvent e) throws Exception {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && !showScreen) {
            if (PLAYGAME){
                player.jump(true);
            }
            if(PAUSEGAME) {
                    timer.start();
                    PAUSEGAME = false;
                    player.jump(false);
               
            }if(GAMEOVER) {
            	PAUSEGAME= false;
            	GAMEOVER=false;
            	clip2.stop();

            	restartGame();
            }
        
        } else if (key == KeyEvent.VK_LEFT) {
            player.setDx(-9);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setDx(9);
        }else if (key == KeyEvent.VK_R) {
        	restartGame();
            
        }
    }

    public void keyReleased(KeyEvent e) throws Exception {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE ) {
        		player.jump(false);
        		showScreen=false;
        	
        }else if (key == KeyEvent.VK_LEFT) {
            player.setDx(0);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setDx(0);
        }
    }


    private void spawnEnemies(int x) {
        if (enemies.size() < NUM_OF_SNAILS) {
        	if(x ==1) {
        		Enemy enemy = new Sloth(frameWidth + 400, LAND_HEIGHT - 400 + 5, SNAIL_SPEED);	//analogy
            	enemies.add(enemy);
            	count =2;
        	}else if(x == 2) {
          		Enemy enemy = new DP(frameWidth + 400, LAND_HEIGHT - 300 + 5, SNAIL_SPEED);	//DP
          		enemies.add(enemy);
          		count =3;
        	}else if (x ==3) {
        	    Enemy enemy = new CORE(frameWidth + 400, LAND_HEIGHT - 300 + 5, SNAIL_SPEED);		//core
                enemies.add(enemy);
                count =4;
        	}else if (x==4) {
                Enemy enemy = new SkillBuild(frameWidth + 400, LAND_HEIGHT - 400 + 5, SNAIL_SPEED);	//work on
        		enemies.add(enemy);
        		count = 5;
        	}
        	else if(x == 5){
        		Enemy enemy = new WorkOn(frameWidth + 400, LAND_HEIGHT - 300 + 5, SNAIL_SPEED);		//skill

        		enemies.add(enemy);
        		count = 6;
        		
        	}  	else if(x == 6){
        		PLAYGAME=false;
        		PAUSEGAME=true;
        		GAMEOVER = true;
        		
        	}


        }
}


    public void checkCollisions() {
        iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy tmp = iter.next();
            isSloth=false;
            isSkillBuild=false;
            isCORE=false;
            isWorkOn= false;
            isDP = false;
            if(collisionHelper(player.getBounds(), tmp.getBounds(), player.getBI(), tmp.getBI())) {
            	
            	//System.out.println(tmp.getClass().getSimpleName());
               if(tmp.getClass().getSimpleName() =="Sloth" ) {
            	   isSloth = true;
               }else if(tmp.getClass().getSimpleName() == "SkillBuild") {
            	   isSkillBuild=true;
               }else if(tmp.getClass().getSimpleName() == "CORE") {
            	   isCORE=true;
               }else if(tmp.getClass().getSimpleName() == "WorkOn") {
            	   isWorkOn=true;
               }else if(tmp.getClass().getSimpleName() == "DP") {
            	   isDP=true;
               }
            	enemies.remove(tmp);
            	PAUSEGAME=true;
            	//timer.setDelay(40);;

               break;
                   
               
            }
        }
    }

    private boolean collisionHelper(Rectangle r1, Rectangle r2, BufferedImage b1, BufferedImage b2) {
        if(r1.intersects(r2)) {
            Rectangle r = r1.intersection(r2);

            int firstI = (int) (r.getMinX() - r1.getMinX()); //firstI is the first x-pixel to iterate from
            int firstJ = (int) (r.getMinY() - r1.getMinY()); //firstJ is the first y-pixel to iterate from
            int bp1XHelper = (int) (r1.getMinX() - r2.getMinX()); //helper variables to use when referring to collision object
            int bp1YHelper = (int) (r1.getMinY() - r2.getMinY());

            for(int i = firstI; i < r.getWidth() + firstI; i++) { //
                for(int j = firstJ; j < r.getHeight() + firstJ; j++) {
                    if((b1.getRGB(i, j) & 0xFF000000) != 0x00 && (b2.getRGB(i + bp1XHelper, j + bp1YHelper) & 0xFF000000) != 0x00) {
                       // player.changeLives(-1);
                   
                        break;
                    }
                }
            }
            return true;

        }else {
        	return false;
        }

    }


    public void restartGame() {
        clip.stop();	//end clapping
        player.setX((int) (0.15 * frameWidth));
        player.setY(PLAYER_Y);
       // player.setLives(3);
        enemies.clear();
        score = 0;
        PLAYGAME = true;
        GAMEOVER = false;
        PAUSEGAME=false;
        count=1;
        timer.start();
    }
}