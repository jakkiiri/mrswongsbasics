
// player class
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Player extends Character {

    GamePanel gp;
    KeyInput keyI;
    private BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;

    public boolean sprinting;
    public double stamina = 500;

    // screen position (for camera movement)
    private final int screenX;
    private final int screenY;

    public int tileX, tileY;

    // which map the player is on
    public String map = "Hallway";

    // map array of the player
    public int[][] mapArray;

    // type of tile the player is stepping on (used for door mechanics)
    public int currentTile;

    public int notebookCount;
    public boolean[] notebooks;

    public ArrayList<Chocolate> chocolateInventory = new ArrayList<> (); 

    // endGame
    // toggle endGame
    public boolean endGame;

    // int to keep track of how many exits the player has went to (for endGame)
    public int exits;

    // boolean to check if you have closed off the exit
    public boolean lExit;
    public boolean dExit;
    public boolean rExit;
    public boolean uExit;

    // soound
    Sound sound = new Sound();

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    // constructor
    public Player(GamePanel gp, KeyInput keyI) {
        this.gp = gp;
        this.keyI = keyI;

        // instantiate screen values to center player
        screenX = gp.screenWidth / 2 - (gp.tileWidth/2);
        screenY = gp.screenHeight / 2 - (gp.tileWidth/2);

        // instantiate hit box (smaller than the actual tile size of the player)
        hitBox = new Rectangle(8, 16, 32, 32);
        hitBoxX = hitBox.x;
        hitBoxY = hitBox.y;
        // instantiates the values
        setValues();
        // initiate sprites
        getPlayerSprite();
    }

    // getters
    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    // setters
    // set player position when teleporting between different rooms
    public void setPosition(int tileX, int tileY) {
        x = gp.tileWidth * tileX;
        y = gp.tileWidth * tileY;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    // import the sprites
    public void getPlayerSprite() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player_up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/player_up3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player_down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/player_down3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player_left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/player_left3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player_right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/player_right3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // void method that initiates the values
    public void setValues() {
        x = gp.tileWidth * 25; // init 25
        y = gp.tileWidth * 38; // init 14
        tileX = 25;
        tileY = 38;
        speed = 2;
        direction = 0; // default to up
        notebookCount = 0;
        notebooks = new boolean[7];
        stamina = 500;
        map = "Hallway";
        chocolateInventory.clear();
        endGame = false;
        exits = 0;
        lExit = false;
        dExit = false;
        rExit = false;
        uExit = false;
    }

    // void method to update the current map player is on
    public void updateMap() {
         // update current tile
         mapArray = gp.tileM.getMap();
    }

    public void endGame() {
        if (!lExit) {
            // check if player hit the left exit
            if (map.equalsIgnoreCase("Hallway")) {
                if ((tileX == 9 && tileY == 18) || (tileX == 9 && tileY == 19) || (tileX == 10 && tileY == 20)) {
                    tileX = 10;
                    tileY = 19;
                    x = tileX * gp.tileWidth;
                    y = tileY * gp.tileWidth;
                    // alter the map to block the exit
                    mapArray[18][9] = 3;
                    mapArray[19][9] = 3;
                    mapArray[20][9] = 3;
                    MapIndex.alterLeft();
                    gp.tileM.setMap("Hallway");
                    lExit = true;
                    exits++;
                    // reveal player location to ms wong
                    gp.mrsWong.targetX = tileX;
                    gp.mrsWong.targetY = tileX;
                }
            }
        }
        if (!dExit) {
            // check if player hit the down exit
            if (map.equalsIgnoreCase("Hallway")) {
                if ((tileX == 24 && tileY == 39) || (tileX == 25 && tileY == 39) || (tileX == 26 && tileY == 39)) {
                  tileX = 25;
                 tileY = 38;
                  x = tileX * gp.tileWidth;
                    y = tileY * gp.tileWidth;
                    // alter the map to block the exit
                    mapArray[39][24] = 2;
                    mapArray[39][25] = 2;
                    mapArray[39][26] = 2;
                    MapIndex.alterDown();
                    gp.tileM.setMap("Hallway");
                    dExit = true;
                    exits++;
                    // reveal player location to ms wong
                    gp.mrsWong.targetX = tileX;
                    gp.mrsWong.targetY = tileX;
                }
            }
        }
        if (!rExit) {
            // check if player hit the left exit
            if (map.equalsIgnoreCase("Hallway")) {
                // check if player hit the right exit
                if ((tileX == 46 && tileY == 19) || (tileX == 46 && tileY == 20) || (tileX == 46 && tileY == 21)) {
                   tileX = 45;
                   tileY = 20;
                   x = tileX * gp.tileWidth;
                   y = tileY * gp.tileWidth;
                   // alter the map to block the exit
                   mapArray[19][46] = 1;
                   mapArray[20][46] = 1;
                   mapArray[21][46] = 1;
                   MapIndex.alterRight();
                   gp.tileM.setMap("Hallway");
                   rExit = true;
                exits++;
                  // reveal player location to ms wong
                  gp.mrsWong.targetX = tileX;
                  gp.mrsWong.targetY = tileX;
                }
            }
        }
        if (!uExit) {
            // check if player hit the left exit
            if (map.equalsIgnoreCase("Gym")) {
            // check if player hit the right exit
            if ((tileX == 12 && tileY == 4) || (tileX == 13 && tileY == 4) || (tileX == 14 && tileY == 4)) {
                tileX = 13;
                tileY = 5;
                x = tileX * gp.tileWidth;
                y = tileY * gp.tileWidth;
                // alter the map to block the exit
                mapArray[4][12] = 4;
                mapArray[4][13] = 4;
                mapArray[4][14] = 4;
                MapIndex.alterUp();
                gp.tileM.setMap("Gym");
                uExit = true;
                exits++;
                // reveal player location to ms wong
                gp.mrsWong.targetX = tileX;
                gp.mrsWong.targetY = tileX;
            }
        }
        } 
    }

    // update player position
    public void update() {

        updateMap();

        tileX = (int) ((double) (this.x+(gp.tileWidth/3)) / gp.tileWidth);
        tileY = (int) ((double) (this.y+(gp.tileWidth/3)) / gp.tileWidth);

        if (notebookCount == 7) {
            endGame();
            endGame = true;
        }
        currentTile = mapArray[tileY][tileX];
        
        // check if player is on a door tile
        if (currentTile == 10 || currentTile == 11 || currentTile == 12 || currentTile == 13) {
            playSE(4);
            // Gym left entry
            checkRoomTeleport("Hallway", "Gym", 11, 7, 3, 10, 1);
            // Gym right entry
            checkRoomTeleport( "Hallway","Gym", 29, 7, 24, 10, 3);
            // Room1 
            checkRoomTeleport("Hallway","Room1", 9, 10, 13, 5, 3);
            // Room2
            checkRoomTeleport("Hallway", "Room2", 44, 5, 18, 6, 3);
            // Room3
            checkRoomTeleport("Hallway", "Room3", 22, 15, 10, 1, 2);
            // Room4
            checkRoomTeleport("Hallway", "Room4", 31, 13, 1, 8, 1);
            // Room5
            checkRoomTeleport("Hallway", "Room5", 11, 22, 1, 6, 1);
            // Room6
            checkRoomTeleport("Hallway","Room6", 29, 22, 13, 5, 3);
            // Room7
            checkRoomTeleport("Hallway", "Room7", 44, 22, 18, 6, 3);
            // Room8 to hallway
            checkRoomTeleport("Hallway", "Room8", 11, 32, 1, 5, 1);
            // Room8 to Room9
            checkRoomTeleport("Room8", "Room9", 10, 3, 1, 10, 1);
            // Room9 to hallway
            checkRoomTeleport("Hallway", "Room9", 20, 26, 8, 1, 2);
            // Room10 to hallway
            checkRoomTeleport("Hallway", "Room10", 24, 35, 14, 6, 3);
            // Room11 to hallway
            checkRoomTeleport("Hallway", "Room11", 28, 26, 3, 1, 2);
            // Room12 to hallway
            checkRoomTeleport("Hallway", "Room12", 26, 32, 1, 3, 1);
            // Room13 to Hallway
            checkRoomTeleport("Hallway", "Room13", 44, 29, 10, 3, 3);
        }

        // System.out.println(tileX + " " + tileY);
        // update player speed
        // sprint and update stamina
        if (sprinting) {
            if (stamina < 6) {
                sprinting = false;
            }
            speed = 4;
            stamina -= 2;
        } else {
            speed = 2;
            if (stamina > 400 && stamina < 501) {
                stamina += 1.5;
            } else if (stamina > 300 && stamina < 401) {
                stamina += 1.25;
            } else if (stamina > 200 && stamina < 301) {
                stamina++;
            } else if (stamina > 100 && stamina < 201) {
                stamina += 0.75;
            } else if (stamina > 50 && stamina < 101) {
                stamina += 0.5;
            } else if (stamina > 0 && stamina < 51) {
                stamina += 0.25;
            }
        }

        if (keyI.up == true || keyI.down == true || keyI.left == true || keyI.right == true || keyI.sprintOn == true || keyI.sprintOff == true) {
            // update stuff
            if (keyI.up) {
                direction = 0;
            } else if (keyI.down) {
                direction = 2;
            } else if (keyI.left) {
                direction = 3;
            } else if (keyI.right) {
                direction = 1;
            } else if (keyI.sprintOn) {
                speed = 0;
                // only turn on sprinting when stamina is greater than 175
                if (stamina > 175) {
                    sprinting = true; 
                }
            } else if (keyI.sprintOff) {
                sprinting = false;
                speed = 0;
            }
            

            // set collision to false
            collisionOn = false;
            // check collision
            gp.collision.checkTile(this);

            // check collision with notebook
            int noteBookIndex = gp.collision.checkNotebook();
            if (noteBookIndex != -1) { 
                int count = 0;
                for (int i = 0; i < notebooks.length; i++) {
                    if (notebooks[i]) {
                        count++;
                    }
                }
                notebookCount = count;
                notebooks[noteBookIndex] = true;
                
                // if notebook is being drawn, undraw it
                // System.out.println(gp.questions.get(3).toString());
                if (gp.notebooks[noteBookIndex].drawn) {
                    gp.notebooks[noteBookIndex].drawn = false;
                    // System.out.println("notebook picked up");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gp.showPopup(gp.questions.get(notebookCount).get(0).question, gp.questions.get(notebookCount).get(0).answer, gp.questions.get(notebookCount).get(1).question, gp.questions.get(notebookCount).get(1).answer, gp.questions.get(notebookCount).get(2).question, gp.questions.get(notebookCount).get(2).answer);
                    keyI.down = false;
                    keyI.up = false;
                    keyI.left = false;
                    keyI.right = false;
                    keyI.sprintOff = false;
                    keyI.sprintOn = false;
                    keyI.pressed = false;
                    sprinting = false;
                }
                if (notebookCount == 7) {
                    gp.endGameM = true;
                }
            }

            // check collision with chocolate
            // collects if collide
            int chocolateIndex = gp.collision.checkChocolate();
            if (chocolateIndex != -1) {
                if (gp.chocolates[chocolateIndex].drawn == true) {
                    chocolateInventory.add(new Chocolate ("chocolate", "/res/chocolate.png"));
                    gp.chocolates[chocolateIndex].drawn = false;
                }
            }

            // check collision, allows player to move in said direction if collision is false
            if (!collisionOn) {
                switch(direction) {
                    case 0:
                    y -= speed;
                    break;
                    case 2:
                    y += speed;
                    break;
                    case 3:
                    x -= speed;
                    break;
                    case 1:
                    x += speed;
                    break;
                }
            }

            // update sprite
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    // check stamina 

    public void checkRoomTeleport(String entryRoom, String exitRoom, int entryX, int entryY, int exitX, int exitY, int direction) {
        // System.out.println("exit: " + exitX + " " + exitY);
        if (map.equalsIgnoreCase(entryRoom)) {
            if (tileX == entryX && tileY == entryY) {
                if (direction == 0){
                    exitY--;
                }
                else if (direction == 1){
                    exitX++;
                }
                else if (direction == 2){
                    exitY++;
                }
                else if (direction == 3){
                    exitX--;
                }
                gp.tileM.setMap(exitRoom);
                setPosition(exitX, exitY);
                // set mrs wong's target position
                // System.out.println("entered door");
                if (gp.mrsWong.mapName.equals(exitRoom)) {
                    gp.mrsWong.targetX = exitX;
                    gp.mrsWong.targetY = exitY;
                    gp.mrsWong.reachedTarget = false;
                } else if (gp.mrsWong.mapName.equals(entryRoom)) {
                    gp.mrsWong.targetX = entryX;
                    gp.mrsWong.targetY = entryY;
                    gp.mrsWong.reachedTarget = false;
                } else if (!gp.mrsWong.mapName.equals("Hallway")) {
                    gp.mrsWong.reachedTarget = false;
                }
                else { // door 8/9 link
                    gp.mrsWong.reachedTarget = true;
                }
                
                map = exitRoom;
            }
        }
        // in exit room, entering entry room
        else if (map.equalsIgnoreCase(exitRoom)) {
            if (tileX == exitX && tileY == exitY) {
                if (direction == 0){
                    entryY++;
                }
                else if (direction == 1){
                    entryX--;
                }
                else if (direction == 2){
                    entryY--;
                }
                else if (direction == 3){
                    entryX++;
                }
                gp.tileM.setMap(entryRoom);
                setPosition(entryX, entryY);
                // System.out.println("exited door");
                if (gp.mrsWong.mapName.equals(entryRoom)) {
                    gp.mrsWong.targetX = entryX;
                    gp.mrsWong.targetY = entryY;
                    gp.mrsWong.reachedTarget = false;
                } else if (gp.mrsWong.mapName.equals(exitRoom)) {
                    gp.mrsWong.targetX = exitX;
                    gp.mrsWong.targetY = exitY;
                    gp.mrsWong.reachedTarget = false;
                } else if (!gp.mrsWong.mapName.equals("Hallway")) {
                    gp.mrsWong.reachedTarget = false;
                }
                else {
                    gp.mrsWong.reachedTarget = true;
                }
                map = entryRoom;

            }

        }
    }

    // draw player
    public void draw(Graphics2D g2) {
       BufferedImage image = null;

       // sprite determination
       switch(direction) {
        case 0:
        if (spriteNum == 1) {
            image = up1;
        }
        if (spriteNum == 2) {
            image = up2;
        }
        if (spriteNum == 3) {
            image = up3;
        }
        break;
        case 2:
        if (spriteNum == 1) {
            image = down1;
        }
        if (spriteNum == 2) {
            image = down2;
        }
        if (spriteNum == 3) {
            image = down3;
        }
        break;
        case 3:
        if (spriteNum == 1) {
            image = left1;
        }
        if (spriteNum == 2) {
            image = left2;
        }
        if (spriteNum == 3) {
            image = left3;
        }
        break;
        case 1:
        if (spriteNum == 1) {
            image = right1;
        }
        if (spriteNum == 2) {
            image = right2;
        }
        if (spriteNum == 3) {
            image = right3;
        }
        break;
       }
       
       // draw player
       g2.drawImage(image, screenX, screenY, gp.tileWidth, gp.tileWidth, null);
       // draw how many notebooks are currently collected
       g2.setColor(Color.WHITE);
       g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
       g2.drawString("Notebooks " + notebookCount + "/7", 40, 70);
       if (stamina < 175) {
        g2.setColor(Color.RED);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.drawString("YOU NEED TO REST", 40, gp.screenHeight - 100);
       }
    }

    // Method to show popup with text field
    
    
}

