// this class stores the helping items the user has collected
// and display it to the user

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Inventory {

    GamePanel gp;
    KeyInput keyI;

    // pointer int to both the arraylist in player as well as in this class
    // initialize at 0;
    int pointer;

    int counter = 0;

    // rectangle array as object placeholders
    Rectangle[] placeholders = new Rectangle[6];
    
    public Inventory (GamePanel gp, KeyInput keyI) {
        this.gp = gp;
        this.keyI = keyI;
        // initialize placeholders
        setHolders();
        pointer = 0;
    }

    // set placeholders
    public void setHolders() {
        for (int i = 0; i < 6; i++) {
            placeholders[i] = new Rectangle(gp.screenWidth-80, 20 + (i * 60), 50, 50);
        }
    }

    // update the pointer on which rectangle
    public void update() {
        counter++;
        // change selected item
        // slows the scrollling down
        if (counter % 20 == 0) {
            if (keyI.iUp || keyI.iDown || keyI.use) {
                if (keyI.iUp) {
                    if (pointer > 0) {
                        pointer--;
                    } else {
                        pointer = 5;
                    }
                } else if (keyI.iDown) {
                    if (pointer < 5) {
                        pointer++;
                    } else {
                        pointer = 0;
                    }
                } else if (keyI.use) {
                    if (!(pointer >= gp.player.chocolateInventory.size())) {
                        if (gp.player.chocolateInventory.get(pointer).name.equalsIgnoreCase("chocolate")) {
                            gp.player.stamina = 500;
                            gp.player.chocolateInventory.remove(pointer);
                        }
                    }
                }
            }
        }
    }

    // draw
    public void draw(Graphics2D g2) {
        for (int i = 0; i < 6; i++) {
            if (pointer == i) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.fill(placeholders[i]);
            g2.draw(placeholders[i]);
        }
        // draw the arraylist of items onto each rectangle
        for (int i = 0; i < gp.player.chocolateInventory.size(); i++) {
            g2.drawImage(gp.player.chocolateInventory.get(i).image, gp.screenWidth - 80, 20 + (i * 60), 50, 50, null);
        }
    }
}
