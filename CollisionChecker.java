// this class helps to check collision between characters and intransversable tiles

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // check intersection between the hitbox and an intransversable tile
    // parameter character
    public void checkTile (Character character) {
        int left = character.x + character.hitBox.x;
        int right = character.x + character.hitBox.x + character.hitBox.width;
        int top = character.y + character.hitBox.y;
        int bottom = character.y + character.hitBox.y + character.hitBox.height;

        int leftCol = left/gp.tileWidth;
        int rightCol = right/gp.tileWidth;
        int topRow = top/gp.tileWidth;
        int bottomRow = bottom/gp.tileWidth;

        int tile1 = 0, tile2 = 0;

        // the switch statements computes the player's predicated position (tile) after moving for one 
        // frame in said direction.  
        switch(character.getDirection()) {
            case 0:
            topRow = (top - character.getSpeed())/gp.tileWidth;
            // get the two tiles the player is trying to collide into 
            tile1 = gp.tileM.map[topRow][leftCol];
            tile2 = gp.tileM.map[topRow][rightCol];
            // check collision boolean, turn on collision accordingly
            if (gp.tileM.getTiles().get(tile1).getCollision() || gp.tileM.getTiles().get(tile2).getCollision()) {
                character.collisionOn = true;
            }
            break;
            case 2:
            bottomRow = (bottom + character.getSpeed())/gp.tileWidth;
            // get the two tiles the player is trying to collide into 
            tile1 = gp.tileM.map[bottomRow][leftCol];
            tile2 = gp.tileM.map[bottomRow][rightCol];
            // check collision boolean, turn on collision accordingly
            if (gp.tileM.getTiles().get(tile1).getCollision() || gp.tileM.getTiles().get(tile2).getCollision()) {
                character.collisionOn = true;
            }
            break;
            case 3:
            leftCol = (left - character.getSpeed())/gp.tileWidth;
            // get the two tiles the player is trying to collide into 
            tile1 = gp.tileM.map[topRow][leftCol];
            tile2 = gp.tileM.map[bottomRow][leftCol];
            // check collision boolean, turn on collision accordingly
            if (gp.tileM.getTiles().get(tile1).getCollision() || gp.tileM.getTiles().get(tile2).getCollision()) {
                character.collisionOn = true;
            }
            break;
            case 1:
            rightCol = (right + character.getSpeed())/gp.tileWidth;
            // get the two tiles the player is trying to collide into 
            tile1 = gp.tileM.map[topRow][rightCol];
            tile2 = gp.tileM.map[bottomRow][rightCol];
            // check collision boolean, turn on collision accordingly
            if (gp.tileM.getTiles().get(tile1).getCollision() || gp.tileM.getTiles().get(tile2).getCollision()) {
                character.collisionOn = true;
            }
            break;
        }
    }
    public int checkNotebook() {
        int i = 0;
        for (i = 0; i < gp.notebooks.length; i++) {
            if(gp.notebooks[i].room.equalsIgnoreCase(gp.player.map) && (gp.player.tileX == gp.notebooks[i].x/gp.tileWidth && gp.player.tileY == gp.notebooks[i].y/gp.tileWidth)) {
                return i;
            }
        }
        return -1;
    }

    public int checkChocolate() {
        int i = 0;
        for (i = 0; i < gp.chocolates.length; i++) {
            if(gp.chocolates[i].room.equalsIgnoreCase(gp.player.map) && (gp.player.tileX == gp.chocolates[i].x/gp.tileWidth && gp.player.tileY == gp.chocolates[i].y/gp.tileWidth)) {
                return i;
            }
        }
        return -1;
    }

    
}
