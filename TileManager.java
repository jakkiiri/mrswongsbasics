// manages the tiles by storing them in a tile list

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class TileManager {

    GamePanel gp;
    // store tiles in a tile list
    ArrayList <Tile> tile = new ArrayList <> ();

    // mapData
	MapIndex mapIndex = new MapIndex("maps.txt");

    String room = "Hallway";
    int[][] map = MapIndex.getMaps().get(room).getMap();

    public TileManager  (GamePanel gp) {
        this.gp = gp;

        getTileImage();
    }

    // getters
    public int[][] getMap () {
        return map;
    }

    public MapIndex getMapIndex() {
        return mapIndex;
    }

    public ArrayList <Tile> getTiles() {
        return tile;
    }

    // setters
    public void setMap (String s) {
        room = s;
        map = MapIndex.getMaps().get(room).getMap();
    }


    // load the images
    public void getTileImage () {

        try {
            tile.add(new Tile());
            tile.get(0).image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor.png"));

            tile.add(new Tile());
            tile.get(1).image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftWall.png"));
            tile.get(1).setCollision(true);
            
            tile.add(new Tile());
            tile.get(2).image = ImageIO.read(getClass().getResourceAsStream("/tiles/upWall.png"));
            tile.get(2).setCollision(true);

            tile.add(new Tile());
            tile.get(3).image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightWall.png"));
            tile.get(3).setCollision(true);

            tile.add(new Tile());
            tile.get(4).image = ImageIO.read(getClass().getResourceAsStream("/tiles/downWall.png"));
            tile.get(4).setCollision(true);

            tile.add(new Tile());
            tile.get(5).image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile.get(5).setCollision(true);

            tile.add(new Tile());
            tile.get(6).image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftUpWall.png"));
            tile.get(6).setCollision(true);

            tile.add(new Tile());
            tile.get(7).image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftDownWall.png"));
            tile.get(7).setCollision(true);

            tile.add(new Tile());
            tile.get(8).image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightUpWall.png"));
            tile.get(8).setCollision(true);

            tile.add(new Tile());
            tile.get(9).image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightDownWall.png"));
            tile.get(9).setCollision(true);

            tile.add(new Tile());
            tile.get(10).image = ImageIO.read(getClass().getResourceAsStream("/tiles/upDoor.png"));

            tile.add(new Tile());
            tile.get(11).image = ImageIO.read(getClass().getResourceAsStream("/tiles/downDoor.png"));

            tile.add(new Tile());
            tile.get(12).image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftDoor.png"));

            tile.add(new Tile());
            tile.get(13).image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightDoor.png"));

            tile.add(new Tile());
            tile.get(14).image = ImageIO.read(getClass().getResourceAsStream("/tiles/carpet.png"));

            tile.add(new Tile());
            tile.get(15).image = ImageIO.read(getClass().getResourceAsStream("/tiles/deskUp.png"));
            tile.get(15).setCollision(true);

            tile.add(new Tile());
            tile.get(16).image = ImageIO.read(getClass().getResourceAsStream("/tiles/deskRight.png"));
            tile.get(16).setCollision(true);

            tile.add(new Tile());
            tile.get(17).image = ImageIO.read(getClass().getResourceAsStream("/tiles/deskDown.png"));
            tile.get(17).setCollision(true);

            tile.add(new Tile());
            tile.get(18).image = ImageIO.read(getClass().getResourceAsStream("/tiles/deskLeft.png"));
            tile.get(18).setCollision(true);

            tile.add(new Tile());
            tile.get(19).image = ImageIO.read(getClass().getResourceAsStream("/tiles/tableH.png"));
            tile.get(19).setCollision(true);

            tile.add(new Tile());
            tile.get(20).image = ImageIO.read(getClass().getResourceAsStream("/tiles/tableV.png"));
            tile.get(20).setCollision(true);

            tile.add(new Tile());
            tile.get(21).image = ImageIO.read(getClass().getResourceAsStream("/tiles/leftExit.png"));
            tile.get(21).setCollision(true);
            
            tile.add(new Tile());
            tile.get(22).image = ImageIO.read(getClass().getResourceAsStream("/tiles/downExit.png"));
            tile.get(22).setCollision(true);
            
            tile.add(new Tile());
            tile.get(23).image = ImageIO.read(getClass().getResourceAsStream("/tiles/rightExit.png"));
            tile.get(23).setCollision(true);

            tile.add(new Tile());
            tile.get(24).image = ImageIO.read(getClass().getResourceAsStream("/tiles/upExit.png"));
            tile.get(24).setCollision(true);

            tile.add(new Tile());
            tile.get(25).image = ImageIO.read(getClass().getResourceAsStream("/tiles/!leftExit.png"));
            tile.get(25).setCollision(true);

            tile.add(new Tile());
            tile.get(26).image = ImageIO.read(getClass().getResourceAsStream("/tiles/!downExit.png"));
            tile.get(26).setCollision(true);

            tile.add(new Tile());
            tile.get(27).image = ImageIO.read(getClass().getResourceAsStream("/tiles/!rightExit.png"));
            tile.get(27).setCollision(true);

            tile.add(new Tile());
            tile.get(28).image = ImageIO.read(getClass().getResourceAsStream("/tiles/!upExit.png"));
            tile.get(28).setCollision(true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // draws the tiles from the int array from mapping
    public void draw (Graphics2D g2) {
      
        int mapCol = 0;
        int mapRow = 0;
        
        while (mapRow < map.length && mapCol < map[0].length) {

            // row
            // col

            // when the col gets to 53, set col to 0 and go to the next row (row++)
            // 
            int tileNum = map[mapRow][mapCol];
            if (!room.equalsIgnoreCase("Hallway") && !room.equalsIgnoreCase("Gym")) {
                if (tileNum == 0) {
                    tileNum = 14;
                }
            }

            // initiate x y position of the player on the map as well as the camera position
            // positions of the tile on the map
            int mapX = mapCol * gp.tileWidth;
            int mapY = mapRow * gp.tileWidth;
            // calculated where the tile should be drawn relative to the player's position
            int screenX = mapX - gp.player.x + gp.player.getScreenX();
            int screenY = mapY - gp.player.y + gp.player.getScreenY();

            // only render tiles assigned greater than -1 & the tiles that are on screen
            if (tileNum >= 0 && (mapX + gp.tileWidth > gp.player.x - gp.player.getScreenX() && mapX - gp.tileWidth < gp.player.x + gp.player.getScreenX() && 
            mapY + gp.tileWidth > gp.player.y - gp.player.getScreenY() && mapY - gp.tileWidth < gp.player.y + gp.player.getScreenY())) {
                g2.drawImage(tile.get(tileNum).image, screenX, screenY, gp.tileWidth, gp.tileWidth, null);
            }
            mapCol++;
      
            if (mapCol == map[0].length-1) {
                mapCol = 0;
                mapRow++;
            }
        }
    }
}