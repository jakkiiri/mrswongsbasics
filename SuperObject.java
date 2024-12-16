// super class of objects (interactable items)
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int x, y;
    public String room;
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public int hitBoxX = 0;
    public int hitBoxY = 0;
    
    public boolean drawn = true;

    // draw method
    public void draw(Graphics2D g2, GamePanel gp) {
        // checks if the object is in the same room as the player
        if (room.equalsIgnoreCase(gp.player.map)) {
            // calculated where the object should be drawn relative to the player's position
            int screenX = x - gp.player.x + gp.player.getScreenX();
            int screenY = y - gp.player.y + gp.player.getScreenY();

            // only render object assigned greater than -1 & the objects that are on screen
            if (drawn) {
                if ((x + gp.tileWidth > gp.player.x - gp.player.getScreenX()
                    && x - gp.tileWidth < gp.player.x + gp.player.getScreenX() &&
                    y + gp.tileWidth > gp.player.y - gp.player.getScreenY()
                    && y - gp.tileWidth < gp.player.y + gp.player.getScreenY())) {
                    g2.drawImage(image, screenX, screenY, gp.tileWidth, gp.tileWidth, null);
                }
            }
        }
    }
}
