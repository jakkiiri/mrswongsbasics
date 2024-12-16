

import java.awt.*;
import java.util.*;

public abstract class Character extends Rectangle {
    
    protected int speed; // movement speed in tiles per frame
    protected int direction; // 0 for up, 1 for right, 2 for down, 3 for left
    protected boolean moving;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    // collision hitbox (different from character rectangle)
    protected Rectangle hitBox;
    protected boolean collisionOn = false;

    // hitbox x & y values
    public int hitBoxX;
    public int hitBoxY;

    // setLocation(x, y) to change location

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean getMoving() {
        return moving;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
