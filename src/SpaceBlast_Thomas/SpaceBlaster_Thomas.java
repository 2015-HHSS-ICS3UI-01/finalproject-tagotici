package SpaceBlast_Thomas;

import static SpaceBlast_Thomas.SpaceBlaster_Thomas.WIDTH;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author agott2059
 */
public class SpaceBlaster_Thomas extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 1000;
    static final int HEIGHT = 950;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    ArrayList<Rectangle> obstacles = new ArrayList<>();
    ArrayList<Rectangle> bulletsP1 = new ArrayList<>();
    ArrayList<Rectangle> bulletsP2 = new ArrayList<>();
    //ship size: x and y dimensions
    int shipSizex = 100;
    int shipSizey = 150;
    //bullet size
    int bulletSize = 20;
    Rectangle shuttle1 = new Rectangle(450, 700, shipSizex, shipSizey);
    Rectangle shuttle2 = new Rectangle(450, 200, shipSizex, shipSizey);
    //player1 move
    int movex1 = 0;
    int movey1 = 0;
    //player2 move
    int movex2 = 0;
    int movey2 = 0;
    //player1 controls
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
    boolean shootP1 = false;
    //player2 controls
    boolean a = false;
    boolean s = false;
    boolean d = false;
    boolean w = false;
    boolean f = false;
    boolean shootP2 = false;
    //game start
    boolean gameStart = false;
    boolean gameOver = false;
    //velocity of ships
    int maxVel = 14;
    //velocity when going at 45 degrees (use pythagorean theorem)
    int diagVel = 9;
    //asteroid positions
    int asteroidX1 = 300;
    int asteroidY1 = 300;
    int asteroidX2 = 670;
    int asteroidY2 = 450;
    //bullet velocity
    int bulletVel = 14;
    //bullet1 position
    int bulletX1 = 50;
    int bulletY1 = -10;
    //bullet2 position
    int bulletX2 = -30;
    int bulletY2 = 1000;
    //player hitpoints
    int hpP1 = 199;
    int hpP2 = 199;
    //has a player won or not
    boolean player1Win = false;
    boolean player2Win = false;
    //boundry thickness
    int boundry = 20;
    //has player been hit
    boolean hit1 = false;
    boolean hit2 = false;
    //jet animation on ship 1
    boolean fire1onP1 = true;
    boolean fire2onP1 = false;
    //jet animation on ship 2
    boolean fire1onP2 = true;
    boolean fire2onP2 = false;
    //timers used for animations
    int timer1 = 0;
    int timer2 = 0;
    int timer3 = 0;
    //timer resets for animations
    boolean time1Set0 = false;
    boolean time2Set0 = false;
    boolean time3Set0 = false;
    //load images and assign to variables
    BufferedImage startScreen = loadImage("startScreenGame.png");
    //background
    BufferedImage background = loadImage("background.jpg");
    //spaceships
    BufferedImage spaceShipP1 = loadImage("spaceshipP1.png");
    BufferedImage spaceShipP2 = loadImage("spaceshipP2.png");
    //red tinted spaceships for when hit
    BufferedImage spaceShipP1HIT = loadImage("spaceshipP1Hit.png");
    BufferedImage spaceShipP2HIT = loadImage("spaceshipP2Hit.png");
    //asteroid and bullet images
    BufferedImage asteroid = loadImage("asteroid.png");
    BufferedImage bullet = loadImage("bulletRed.png");
    //animations for jets on both ships
    BufferedImage fireAnim1P1 = loadImage("fire1P1.png");
    BufferedImage fireAnim2P1 = loadImage("fire2P1.png");
    BufferedImage fireAnim1P2 = loadImage("fire1P2.png");
    BufferedImage fireAnim2P2 = loadImage("fire2P2.png");
    //win screens
    BufferedImage player1WinScreen = loadImage("Player1Wins.png");
    BufferedImage player2WinScreen = loadImage("Player2Wins.png");
    //load images

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading " + filename);
        }
        return img;
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 

        //put in background      
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        //if player1 wins, put up win screen for player 1
        if (player1Win == true) {
            g.drawImage(player1WinScreen, 0, 0, WIDTH, HEIGHT, null);
            //if player1 wins, put up win screen for player 1
        } else if (player2Win == true) {
            g.drawImage(player2WinScreen, 0, 0, WIDTH, HEIGHT, null);
        }

        //draw shuttles acording to variable x&y values
        g.drawImage(spaceShipP1, shuttle1.x, shuttle1.y, shipSizex, shipSizey, null);
        g.drawImage(spaceShipP2, shuttle2.x, shuttle2.y, shipSizex, shipSizey, null);
        //draw asteroids
        g.drawImage(asteroid, asteroidX1, asteroidY1, 100, 100, null);
        g.drawImage(asteroid, asteroidX2, asteroidY2, 80, 80, null);

        //health bars
        g.setColor(Color.white);
        //outlines
        g.drawRect(20, 20, 30, 200);
        g.drawRect(WIDTH - 50, HEIGHT - 220, 30, 200);
        //fill health bars, y is a variable that decreases as health decreases
        g.setColor(Color.red);
        g.fillRect(WIDTH - 49, HEIGHT - 219, 29, hpP1);
        g.setColor(Color.blue);
        g.fillRect(21, 21, 29, hpP2);


        //bullet positions according to variable x&y coordinates
        g.drawImage(bullet, bulletX1, bulletY1, bulletSize, bulletSize, null);
        g.drawImage(bullet, bulletX2, bulletY2, bulletSize, bulletSize, null);
        //if game has not started, display start screen
        if (gameStart == false) {
            g.drawImage(startScreen, 0, 0, WIDTH, HEIGHT, null);
        }
        //if P1 is it display red
        if (hit1 == true) {
            g.drawImage(spaceShipP1HIT, shuttle1.x, shuttle1.y, shipSizex, shipSizey, null);
            if (time1Set0 == false) {
                timer1 = 0;
                time1Set0 = true;
            }
            if (timer1 == 10) {
                hit1 = false;
                time1Set0 = false;
            }
        }
        //if P2 is it display red
        if (hit2 == true) {
            g.drawImage(spaceShipP2HIT, shuttle2.x, shuttle2.y, shipSizex, shipSizey, null);
            if (time1Set0 == false) {
                timer1 = 0;
                time1Set0 = true;
            }

            if (timer1 == 10) {
                hit2 = false;
                time1Set0 = false;
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //wait for game to start to display animations 
        if (gameStart == true) {
            //animation for jet player 1
            if (fire1onP1 == true) {
                g.drawImage(fireAnim1P1, shuttle1.x + 17, shuttle1.y + 150, 70, 110, null);
                if (time2Set0 == false) {
                    timer2 = 0;
                    time2Set0 = true;
                }

                if (timer2 == 10) {
                    fire1onP1 = false;
                    fire2onP1 = true;
                    time2Set0 = false;
                }
            }
            if (fire2onP1 == true) {
                g.drawImage(fireAnim2P1, shuttle1.x + 8, shuttle1.y + 150, 85, 85, null);
                if (time2Set0 == false) {
                    timer2 = 0;
                    time2Set0 = true;
                }

                if (timer2 == 10) {
                    fire1onP1 = true;
                    fire2onP1 = false;
                    time2Set0 = false;
                }
            }

            //////////////////////////////////
            //player2 jet animations
            if (fire1onP2 == true) {
                g.drawImage(fireAnim1P2, shuttle2.x + 15, shuttle2.y - 110, 70, 110, null);
                if (time3Set0 == false) {
                    timer3 = 0;
                    time3Set0 = true;
                }

                if (timer3 == 10) {
                    fire1onP2 = false;
                    fire2onP2 = true;
                    time3Set0 = false;
                }
            }


            if (fire2onP2 == true) {
                g.drawImage(fireAnim2P2, shuttle2.x + 8, shuttle2.y - 90, 85, 85, null);
                if (time3Set0 == false) {
                    timer3 = 0;
                    time3Set0 = true;
                }

                if (timer3 == 10) {
                    fire2onP2 = false;
                    fire1onP2 = true;
                    time3Set0 = false;
                }

            }

        }

        // GAME DRAWING ENDS HERE
    }
    // The main game loop
    // In here is where all the logic for my game will go

    public void run() {
        //add asteroids & other obstacles
        obstacles.add(new Rectangle(asteroidX1, asteroidY1, 100, 100));
        Rectangle asteroid1 = new Rectangle(asteroidX1, asteroidY1, 100, 100);

        obstacles.add(new Rectangle(asteroidX2, asteroidY2, 80, 80));
        Rectangle asteroid2 = new Rectangle(asteroidX2, asteroidY2, 80, 80);

        //boundries
        obstacles.add(new Rectangle(0, 0, boundry, HEIGHT));
        obstacles.add(new Rectangle(0, 0, WIDTH, boundry));
        obstacles.add(new Rectangle(WIDTH - boundry, 0, boundry, HEIGHT));
        obstacles.add(new Rectangle(0, HEIGHT - boundry, WIDTH, boundry));
        //obstacles.add(new Rectangle(200, 200, 200, 200));
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE

            //check if game has started and nobody has lost yet
            if (gameStart == true && gameOver == false) {
                //check hp player 2 (if 0 player 1 wins)
                if (hpP2 <= 0) {
                    player1Win = true;
                    //set game over true
                    gameOver = true;
                    //check hp player 1 (if 0 player 2 wins)
                } else if (hpP1 <= 0) {
                    player2Win = true;
                    //set game over true
                    gameOver = true;
                }


                //timers for animations
                timer1 = timer1 + 1;
                timer2 = timer2 + 1;
                timer3 = timer3 + 1;

                //movements player 1
                if (left == true) {
                    movex1 = -maxVel;
                } else if (right == true) {
                    movex1 = maxVel;
                } else {
                    movex1 = 0;
                }

                if (up == true) {
                    movey1 = -maxVel;
                } else if (down == true) {
                    movey1 = maxVel;
                } else {
                    movey1 = 0;
                }
                if (up == true && left == true) {
                    movex1 = -diagVel;
                    movey1 = -diagVel;
                }
                if (up == true && right == true) {
                    movex1 = diagVel;
                    movey1 = -diagVel;
                }

                if (down == true && left == true) {
                    movex1 = -diagVel;
                    movey1 = diagVel;
                }
                if (down == true && right == true) {
                    movex1 = diagVel;
                    movey1 = diagVel;
                } else {
                }
                //change coordinates of ship
                shuttle1.x = shuttle1.x + movex1;
                shuttle1.y = shuttle1.y + movey1;

                //make ship come out opposite side if it goes to far left or right
                /*if (shuttle1.x > WIDTH) {
                 shuttle1.x = 0;
                 } else if ((shuttle1.x + shipSizex) < 0) {
                 shuttle1.x = WIDTH;
                 }*/
                ///////////////////////////////////
                //movements player 2
                if (a == true) {
                    movex2 = -maxVel;
                } else if (d == true) {
                    movex2 = maxVel;
                } else {
                    movex2 = 0;
                }

                if (w == true) {
                    movey2 = -maxVel;
                } else if (s == true) {
                    movey2 = maxVel;
                } else {
                    movey2 = 0;
                }
                if (w == true && a == true) {
                    movex2 = -diagVel;
                    movey2 = -diagVel;
                }
                if (w == true && d == true) {
                    movex2 = diagVel;
                    movey2 = -diagVel;
                }

                if (s == true && a == true) {
                    movex2 = -diagVel;
                    movey2 = diagVel;
                }
                if (s == true && d == true) {
                    movex2 = diagVel;
                    movey2 = diagVel;
                } else {
                }
                //change coordinates of ship
                shuttle2.x = shuttle2.x + movex2;
                shuttle2.y = shuttle2.y + movey2;

                //*//*//*//////////////////////////
                //check if bullet is off the map yet (disable shoot if false) 
                if (bulletY1 > 0) {
                    shootP1 = false;
                }
                //check if player can shoot
                if (shootP1 == true) {
                    //bring bullet to tip of ship
                    bulletX1 = shuttle1.x + 43;
                    bulletY1 = shuttle1.y - 30;
                    //create rectangle for bullet
                    bulletsP1.add(new Rectangle(bulletX1, bulletY1, bulletSize, bulletSize));
                }
                //bullet coordiantes for aniamtion and rectangle
                bulletY1 = bulletY1 + -bulletVel;
                for (Rectangle bullet : bulletsP1) {
                    bullet.setLocation(bullet.x, bullet.y - bulletVel);
                }
                //check if bullet is off the map yet (disable shoot if false)
                if (bulletY2 < HEIGHT) {
                    shootP2 = false;
                }
                //check if player can shoot
                if (shootP2 == true) {
                    //bring bullet to tip of ship
                    bulletX2 = shuttle2.x + 43;
                    bulletY2 = shuttle2.y + 140;
                    //create rectangle for bullet
                    bulletsP2.add(new Rectangle(bulletX2, bulletY2, bulletSize, bulletSize));
                }
                //bullet coordiantes for aniamtion and rectangle
                bulletY2 = bulletY2 + bulletVel;
                for (Rectangle bullet : bulletsP2) {
                    bullet.setLocation(bullet.x, bullet.y + bulletVel);
                }

                // collisions shuttle 1
                for (Rectangle block : obstacles) {
                    // is the player hitting a block
                    if (shuttle1.intersects(block)) {
                        // get the collision rectangle
                        Rectangle intersection = shuttle1.intersection(block);
                        // fix the x movement
                        if (intersection.width < intersection.height) {
                            // player on the left
                            if (shuttle1.x < block.x) {
                                // move the player to the left of the overlap
                                shuttle1.x = shuttle1.x - intersection.width;
                            } else {
                                shuttle1.x = shuttle1.x + intersection.width;
                            }
                        } else // fix the y
                        // hit the block with my head
                        {
                            if (shuttle1.y > block.y) {
                                shuttle1.y = shuttle1.y + intersection.height;
                                movey1 = 0;
                            } else {
                                shuttle1.y = shuttle1.y - intersection.height;
                                movey1 = 0;
                            }
                        }
                    }
                }
                //collisions shuttle 2
                for (Rectangle block : obstacles) {
                    // is the player hitting a block
                    if (shuttle2.intersects(block)) {
                        // get the collision rectangle
                        Rectangle intersection = shuttle2.intersection(block);
                        // fix the x movement
                        if (intersection.width < intersection.height) {
                            // player on the left
                            if (shuttle2.x < block.x) {
                                // move the player the overlap
                                shuttle2.x = shuttle2.x - intersection.width;
                            } else {
                                shuttle2.x = shuttle2.x + intersection.width;
                            }
                        } else // fix the y
                        // hit the block with my head
                        {
                            if (shuttle2.y > block.y) {
                                shuttle2.y = shuttle2.y + intersection.height;
                                movey1 = 0;
                            } else {
                                shuttle2.y = shuttle2.y - intersection.height;
                                movey1 = 0;
                            }
                        }
                    }
                }

                //Bullet collision for Player 1
                for (Rectangle bullet : bulletsP2) {
                    if (shuttle1.intersects(bullet)) {
                        //decrease HP
                        hpP1 = hpP1 - 10;
                        hit1 = true;
                        //move bullet animation and rectangle off screen
                        bulletX2 = 2000;
                        bulletY2 = 2000;
                        bullet.setLocation(2000, 2000);
                    }
                }
                //Bullet collision for Player 2
                for (Rectangle bullet : bulletsP1) {
                    if (shuttle2.intersects(bullet)) {
                        //decrease HP
                        hpP2 = hpP2 - 10;
                        hit2 = true;
                        //move bullet animation and rectangle off screen
                        bulletX1 = 2000;
                        bulletY1 = -2000;
                        bullet.setLocation(2000, 2000);
                    }
                }

                //asteroid collision bullets player1
                for (Rectangle bullet1 : bulletsP1) {
                    if (asteroid1.intersects(bullet1) || asteroid2.intersects(bullet1)) {
                        //move bullet animation and rectangle off screen
                        bulletX1 = 2000;
                        bulletY1 = -2000;
                        bullet1.setLocation(2000, 2000);
                    }
                }

                //asteroid collision bullets player2
                for (Rectangle bullet2 : bulletsP2) {
                    if (asteroid2.intersects(bullet2) || asteroid1.intersects(bullet2)) {
                        //move bullet animation and rectangle off screen
                        bulletX2 = 2000;
                        bulletY2 = 2000;
                        bullet2.setLocation(2000, 2000);
                    }
                }

            }

            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        SpaceBlaster_Thomas game = new SpaceBlaster_Thomas();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        //keys for player one (pressed)
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (key == KeyEvent.VK_UP) {
            up = true;
        } else if (key == KeyEvent.VK_DOWN) {
            down = true;
        } else if (key == KeyEvent.VK_SPACE) {
            shootP2 = true;
        } //keys for player two (pressed)
        else if (key == KeyEvent.VK_A) {
            a = true;
        } else if (key == KeyEvent.VK_D) {
            d = true;
        } else if (key == KeyEvent.VK_W) {
            w = true;
        } else if (key == KeyEvent.VK_S) {
            s = true;
        } else if (key == KeyEvent.VK_SHIFT) {
            shootP1 = true;
        } /////
        else if (key == KeyEvent.VK_ENTER) {
            gameStart = true;
        }

    }

    @Override
    //keys for player one (released)
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (key == KeyEvent.VK_UP) {
            up = false;
        } else if (key == KeyEvent.VK_DOWN) {
            down = false;
        } else if (key == KeyEvent.VK_0) {
            shootP1 = false;
        } //keys for player one (released)
        else if (key == KeyEvent.VK_A) {
            a = false;
        } else if (key == KeyEvent.VK_D) {
            d = false;
        } else if (key == KeyEvent.VK_W) {
            w = false;
        } else if (key == KeyEvent.VK_S) {
            s = false;
        } else if (key == KeyEvent.VK_SPACE) {
            shootP2 = false;
        }
    }
}
