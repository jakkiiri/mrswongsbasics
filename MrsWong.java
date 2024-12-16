
import java.io.IOException;
import java.util.*;
import java.util.Queue;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/*
Mrs. Wong Class. 

Roaming:
Every few ticks, it starts to move towards a random location on the map.
    
Chase:
Every teleport, it checks in all four directions for the player. 
If it sees the player, it enters chase mode and starts moving towards the player's last position. If it sees the player while chasing, it reroutes to the player.

If player enters/exits rooms, route to the player's last position.
 
Returning to roaming:
After arriving at the player's last position returns to roaming mode.


 * 
 */

public class MrsWong extends Character{
    public int targetX, targetY;

    // render X/Y is the relative position on the screen.
    // x/y is the absolute position on the map
    private int screenX, screenY;; 
    public int tileX, tileY; // absolute tile position
    public boolean reachedTarget = true;
    public String mapName;
    private int[][] map;
    private MapIndex mapIndex;
    public int frameCount = 0;
    private boolean ascending = true;

    private int refreshRate = 25;

    public GamePanel gp;

    protected Rectangle hitBox;
    protected boolean collisionOn = false;

    private BufferedImage img1, img2, img3, img4, img5;

    // own sound
    Sound sound = new Sound();

    public MrsWong(GamePanel gp, TileManager tileM) {
        // MRS WONG HAS 0 SPEED BECAUSE SHE TELEPORTS
        mapName = "Hallway";
        mapIndex = tileM.getMapIndex();
        map = mapIndex.getMaps().get(mapName).getMap();
        this.gp = gp;
        // instantiates the values
        setValues();
        // initiate sprites
        getPlayerSprite();

        hitBox = new Rectangle(8, 16, 32, 32);
    }

    public void getPlayerSprite() {
        try {
            img1 = ImageIO.read(getClass().getResourceAsStream("/res/msWong1.png"));
            img2 = ImageIO.read(getClass().getResourceAsStream("/res/msWong2.png"));
            img3 = ImageIO.read(getClass().getResourceAsStream("/res/msWong3.png"));
            img4 = ImageIO.read(getClass().getResourceAsStream("/res/msWong4.png"));
            img5 = ImageIO.read(getClass().getResourceAsStream("/res/msWong5.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // void method that initiates the values
    public void setValues() {
        x = gp.tileWidth * 10;
        y = gp.tileWidth * -1;
        speed = 2;
        direction = 0; // default to up
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    // runs every frame.
    public void update() {
        // update sprite
        spriteCounter++;
        if (spriteCounter > 12) {
            if (ascending) {
                if (spriteNum == 1) {
                    if (gp.player.notebookCount >= 2 && gp.player.notebookCount != 7) {
                        playSE(3);
                    }
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 5;
                    ascending = false;
                }
            } else {
                if (spriteNum == 5) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                    ascending = true;
                }
            }
            spriteCounter = 0;
        }
        tileX = (int) ((double) (this.x+(gp.tileWidth/3)) / gp.tileWidth);
        tileY = (int) ((double) (this.y+(gp.tileWidth/3)) / gp.tileWidth);
        updateMap(map[tileY][tileX]);
        if (gp.player.notebookCount < 2) {
            x = gp.tileWidth * 10;
            y = gp.tileWidth * 1;
        }
        map = MapIndex.getMaps().get(mapName).getMap();
        frameCount++;
        screenX = x - gp.player.x + gp.player.getScreenX();
        screenY = y - gp.player.y + gp.player.getScreenY();

        scanForPlayer(map, (int) ((gp.player.x+gp.tileWidth/3)/gp.tileWidth), (int) ((gp.player.y+gp.tileWidth/3)/gp.tileWidth));


        // if target has been reached, pick a random point and start moving to it
        if (frameCount % refreshRate == 0) {
            if (gp.difficulty > 4) {
                refreshRate = 21;
            } else if (gp.difficulty > 2) {
                refreshRate = 25;
            } else {
                refreshRate = 30;
            }
            // System.out.print("difficulty: " + gp.difficulty);
            // System.out.print(" | target: " + targetX + ", " + targetY);
            // System.out.print(" | pos: " + tileX + ", " + tileY + " | reachedTarget: " + reachedTarget);
            // System.out.println(" | player: " + gp.player.tileX + ", " + gp.player.tileY);
            
            
            // if player and mrs wong are in a room together, mrs wong always sees the player
            if (gp.player.map.equals(mapName) && !mapName.equals("Hallway")) {
                targetX = gp.player.tileX;
                targetY = gp.player.tileY;
            }
            else if (reachedTarget) {
                // System.out.println("random point");
                chooseRandomPoint(map);
                // System.out.println("d");
                reachedTarget = false;
            } else if (gp.player.notebookCount < 2) {
                // ms wong is immobile when the player has not collected more than 2 notebooks
                targetX = tileX;
                targetY = tileY;
            }
            // targetX = 11;
            // targetY = 7;
            move(map);
        }
        
    }

    public void updateMap(int currentTile) {
        // check if wong is on a door tile
        // System.out.println("current tile: " + currentTile);
        if (currentTile == 10 || currentTile == 11 || currentTile == 12 || currentTile == 13) {
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
        }
        x = gp.tileWidth * tileX;
        y = gp.tileWidth * tileY;
    }

    public void checkRoomTeleport(String entryRoom, String exitRoom, int entryX, int entryY, int exitX, int exitY, int direction) {
        if (mapName.equalsIgnoreCase(entryRoom)) {
            if (tileX == entryX && tileY == entryY) {
                reachedTarget = true;

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
                tileX = exitX;
                tileY = exitY;
                mapName = exitRoom;
            }
        }
        else if (mapName.equalsIgnoreCase(exitRoom)) {
            if (tileX == exitX && tileY == exitY) {
                reachedTarget = true;

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
                tileX = entryX;
                tileY = entryY;
                mapName = entryRoom;

            }

        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
       // draw wong only if she is on the same map as the player
       if (mapName.equals(gp.player.map)) {
            if (spriteNum == 1) {
                image = img1;
            } else if (spriteNum == 2) {
                image = img2;
            } else if (spriteNum == 3) {
                image = img3;
            } else if (spriteNum == 4) {
                image = img4;
            } else if (spriteNum == 5) {
                image = img5;
            }
            g2.drawImage(image, screenX, screenY, gp.tileWidth, gp.tileWidth, null);
       }
      
    
    }

    // DESC: Looks in all four directions in a straight line for the player. If player found, enter chase mode.
    // PARAMETERS: 2d array map, player position
    // RETURN: none
    public void scanForPlayer(int[][] map, int playerX, int playerY) {
       // make sure wong is on the same map as the player
        if (mapName.equals(gp.player.map)) {
             // check north
            for (int i = tileY ; i >= 0; i--) {
                if (map[i][tileX] != 0 && map[i][tileX] != 14) { // wall in the way, cannot go north
                    break;
                }

                // check around corners to account for rounding error
                if ((i + 1 == playerY || i - 1 == playerY ) && (tileX + 1 == playerX || tileX - 1 == playerX) || (i == playerY && tileX == playerX)) { // found player
                    this.targetX = playerX;
                    this.targetY = playerY;
                    reachedTarget = false;
                    return;
                }
            }

            // check south
            for (int i = tileY ; i < map.length; i++) {
                if (map[i][tileX] != 0 && map[i][tileX] != 14) { // wall in the way, cannot go south
                    break;
                }
                if ( (i + 1 == playerY || i - 1 == playerY ) && (tileX + 1 == playerX || tileX - 1 == playerX) || (i == playerY && tileX == playerX)) { // found player
                    this.targetX = playerX;
                    this.targetY = playerY;
                    reachedTarget = false;

                    return;
                }
            }

            // check west
            for (int i = tileX ; i >= 0; i--) {
                if (map[tileY][i] != 0 && map[tileY][i] != 14) { // wall in the way, cannot go west
                    break;
                }


                // checks for player 1 tile around corners to account for tile/px rounding errors
                if ( (tileY + 1 == playerY || tileY - 1 == playerY ) && (i + 1 == playerX || i - 1 == playerX) || (tileY == playerY && i == playerX)) { // found player
                    this.targetX = playerX;
                    this.targetY = playerY;
                    reachedTarget = false;

                    return;
                }
            }

            // check east
            for (int i = tileX ; i < map[0].length; i++) {
                if (map[tileY][i] != 0 && map[tileY][i] != 14) { // wall in the way, cannot go east
                    break;
                }
                if ((tileY + 1 == playerY || tileY - 1 == playerY) && (i + 1 == playerX || i - 1 == playerX) || (tileY == playerY && i == playerX)) { // found player
                    // System.out.println("found!");
                    this.targetX = playerX;
                    this.targetY = playerY;
                    reachedTarget = false;
                    return;
                }
            }
        }
        // System.out.println("player not found");
        return;
    }


    // DESC: Choose a random location within the current map that is a valid path
    // PARAMETERS: 2d array map
    // RETURN: none
    public void chooseRandomPoint(int[][] map) {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(map[0].length);
            y = rand.nextInt(map.length);
        } while (map[y][x] != 0 && map[y][x] != 14);

        this.targetX = x;
        this.targetY = y; 
    }

    public void move(int[][] map) {

        if (tileX == this.targetX && tileY == this.targetY) {
            reachedTarget = true;
            return;
        }

        int[] bfsTest = bfs(map, tileX, tileY, this.targetX, this.targetY);
    
        if (bfsTest[1] == -1) {
            // do nothing if no path
        }
        else if (bfsTest[0] == 3) { // left
            // go left
            this.setLocation(this.x - gp.tileWidth, this.y);
        }
        else if (bfsTest[0] == 2) { // down
            // go up
            this.setLocation(this.x, this.y + gp.tileWidth);
        }
        else if (bfsTest[0] == 1) { // right
            this.setLocation(this.x + gp.tileWidth, this.y);
        }
        else if (bfsTest[0] == 0) { // up
            this.setLocation(this.x, this.y - gp.tileWidth);
        }
    }

    // DESC: BFS pathfinding algorithm
    // PARAMETERS: 2d array map, startx, starty, targetx, targety
    // RETURN: 2d int array. [0] = direction, [1] = num of steps

    private static final int[] rowNum = {-1, 0, 0, 1};
    private static final int[] colNum = {0, -1, 1, 0};

    public int[] bfs(int[][] map, int startX, int startY, int targetX, int targetY) {
        int rows = map.length;
        int cols = map[0].length;

        // Base condition checks that don't actually matter
        // if (map[startY][startX] == 1 || map[targetY][targetX] == 1) {
        //     // Either start or target is a wall
        //     System.out.println("Either start or target is a wall");
        //     return new int[]{-1, -1};
        // }

        boolean[][] visited = new boolean[rows][cols];
        visited[startY][startX] = true;

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY, 0, null));

        Point targetPoint = null;

        // BFS loop
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if ((current.x == targetX || current.x + 1 == targetX || current.x - 1 == targetX) && ((current.y == targetY) || (current.y + 1 == targetY || current.y - 1 == targetY))) {
                targetPoint = current;
                break;
            }

            for (int i = 0; i < 4; i++) { // left, up, right, down
                
                int newX = current.x + rowNum[i];
                int newY = current.y + colNum[i];

                
                if (isValid(newX, newY, rows, cols)) {
                    if((map[newY][newX] == 0 || map[newY][newX] == 14) && !visited[newY][newX]) {
                        visited[newY][newX] = true;
                        queue.add(new Point(newX, newY, current.distance + 1, current)); // maybe
                    }                    
                }
            }
        }

        // No path found
        if (targetPoint == null) {
            // System.out.println("No path found");
            return new int[]{-1, -1};
        }

        // Reconstruct path from targetPoint to start point
        LinkedList<Point> path = new LinkedList<>();
        for (Point p = targetPoint; p != null; p = p.parent) {
            path.addFirst(p);
        }
        // System.out.println(path.getFirst());
        // System.out.println(targetX + ", " + targetY);

        if (path.size() == 1) {
            
            // if (map[targetY][targetX] == 0) {
                        if (targetPoint.x != targetX && targetPoint.y != targetY) {
                    // impossible case, so fix it manually
                    // System.out.println("path size 1, added manually");
                    // System.out.println("targetPoint (last dfs point): " + targetPoint.x + ", " + targetPoint.y);
                    // for (int[] row : map) {
                    //     System.out.println(Arrays.toString(row));
                    // }
                    if (targetPoint.x - targetX == 1 && targetPoint.y - targetY == 1) {
                        // targetpoint is bottom right of target, so go top left
                        if (map[targetPoint.y - 1][targetPoint.x] == 0 || map[targetPoint.y - 1][targetPoint.x] == 14) {
                            // check up
                            return new int[] {0, 1};

                        }
                        else if (map[targetPoint.y][targetPoint.x - 1] == 0 || map[targetPoint.y][targetPoint.x - 1] == 14) {
                            // check left
                            return new int[] {3, 1};

                        }
                    }
                    else if (targetPoint.x - targetX == 1 && targetPoint.y - targetY == -1) {
                        // go bottom left
                        if (map[targetPoint.y + 1][targetPoint.x] == 0 || map[targetPoint.y + 1][targetPoint.x] == 14) {
                            // check down
                            return new int[] {2, 1};

                        }
                        else if (map[targetPoint.y][targetPoint.x - 1] == 0 || map[targetPoint.y][targetPoint.x - 1] == 14) {
                            // check left
                            return new int[] {3, 1};

                        }
                    }

                    else if (targetPoint.x - targetX == -1 && targetPoint.y - targetY == 1) {
                        // go top right
                        if (map[targetPoint.y - 1][targetPoint.x] == 0 || map[targetPoint.y - 1][targetPoint.x] == 14) {
                            // check up
                            return new int[] {0, 1};

                        }
                        else if (map[targetPoint.y][targetPoint.x + 1] == 0 || map[targetPoint.y][targetPoint.x + 1] == 14) {
                            // check right
                            return new int[] {1, 1};

                        }
                    }

                    else if (targetPoint.x - targetX == -1 && targetPoint.y - targetY == -1) {
                        // go bottom right
                        if (map[targetPoint.y + 1][targetPoint.x] == 0 || map[targetPoint.y + 1][targetPoint.x] == 14) {
                            // check down
                            return new int[] {2, 1};
                        }
                        else if (map[targetPoint.y][targetPoint.x + 1] == 0 || map[targetPoint.y][targetPoint.x + 1] == 14) {
                            // check right
                            return new int[] {1, 1};
                        }
                    }
                }
                else {
                    path.addLast(new Point(targetX, targetY, 1, targetPoint));
                }
            // }
            // else {
            //     // System.out.println("player is in a wall");
            //     return new int[]{-1, -1};
            // }
        }

        // System.out.println("path: "+ Arrays.toString(path.toArray()) + "target: " + targetX + ", " + targetY);
        
        // Find the final move in a straight line
        Point firstMoveStart = path.get(0); // 1st
        Point firstMoveEnd;
        if (path.size() == 1) {
            // this.setLocation(targetX, targetY);
            return new int[]{-1, -1};
        } else {
            firstMoveEnd = path.get(1); // 2nd point
        }
        
 
        int direction = -1;
        int distance = 0;

        if (firstMoveEnd.x == firstMoveStart.x) {
            // Moving horizontally
            distance = Math.abs(firstMoveEnd.y - firstMoveStart.y);
            direction = firstMoveEnd.y > firstMoveStart.y ? 2 : 0; // down / up
        } else if (firstMoveEnd.y == firstMoveStart.y) {
            // Moving vertically
            distance = Math.abs(firstMoveEnd.x - firstMoveStart.x);
            direction = firstMoveEnd.x > firstMoveStart.x ? 1 : 3; // right or left
        }

        return new int[]{direction, distance};
    }

    private static boolean isValid(int x, int y, int rows, int cols) {
        // System.out.println("rows, cols: " + rows + ", " + cols);
        return (y >= 0 && y < rows && x >= 0 && x < cols);
    }

    private static class Point {
        int x, y, distance;
        Point parent;
    
        public Point(int x, int y, int distance, Point parent) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.parent = parent;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public Rectangle getHitBox() {
        return hitBox;
    }


    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

}
