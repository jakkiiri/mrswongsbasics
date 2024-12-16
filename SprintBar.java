// this class is responsible for drawing the sprint bar in the graphical screen

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SprintBar {


    GamePanel gp;

    // rectangle array that shows the stamina bar
    Rectangle[] gauge = new Rectangle[100];

    // rectangle to show bar
    Rectangle bar = new Rectangle ();

    public SprintBar (GamePanel gp) {
        this.gp = gp;
        gauge();
    }

    // method to initalize all of the guage rectangle
    public void gauge () {
        bar.setBounds(40, gp.screenHeight-80, 200, 30);
        for (int i = 0; i < 100; i++) {
           gauge[i] = new Rectangle(40 + (2 * i), gp.screenHeight-80, 2, 30);
        }
    }

    // draw method to draw the bar
    public void draw (Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fill(bar);
        g2.draw(bar);
        if (gp.player.stamina <= 100) {
            g2.setColor(Color.ORANGE);
        } else if (gp.player.stamina <= 175) {
            g2.setColor(Color.YELLOW);
        } else {
            g2.setColor(Color.GREEN);
        }
        for (int i = 0; i < (int) (gp.player.stamina / 5); i++) {
            g2.fill(gauge[i]);
            g2.draw(gauge[i]);
        }
        
    }


}
