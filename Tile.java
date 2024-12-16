// the tile class
// helps to generate map with individual png tiles

import java.awt.image.BufferedImage;

public class Tile {

    public BufferedImage image;
    private boolean collision = false;

    // getters
    public BufferedImage getImage() {
        return image;
    }

    public boolean getCollision() {
        return collision;
    }

    // setters
    public void setCollision(boolean b) {
        collision = b;
    }
}
