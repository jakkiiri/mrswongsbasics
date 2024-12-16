// this class instantiates notebook objects, which are interactive 
// and collecting them is the key objective of the game

import java.io.IOException;

import javax.imageio.ImageIO;

public class Notebook extends SuperObject{

    public Notebook (String name, String file) {
        this.name = name;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method to set its position
    public void setPosition (String room, int x, int y) {
        this.room = room;
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return room + " " + x + " " + y;
    }
}
