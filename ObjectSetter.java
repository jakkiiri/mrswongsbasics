// loads in objects and set their positions

public class ObjectSetter {

    GamePanel gp;

    public ObjectSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNotebook() {
        gp.notebooks[0] = new Notebook("greenNo", "/notebook/green.png");
        gp.notebooks[0].setPosition("Room10", gp.tileWidth * 3, gp.tileWidth * 5);
        gp.notebooks[1] = new Notebook("blueNo", "/notebook/blue.png");
        gp.notebooks[1].setPosition("Room12", gp.tileWidth * 13, gp.tileWidth * 5);
        gp.notebooks[2] = new Notebook("yellowNo", "/notebook/yellow.png");
        gp.notebooks[2].setPosition("Room11", gp.tileWidth * 11, gp.tileWidth * 10);
        gp.notebooks[3] = new Notebook("redNo", "/notebook/red.png");
        gp.notebooks[3].setPosition("Room7", gp.tileWidth * 3, gp.tileWidth * 6);
        gp.notebooks[4] = new Notebook("cyanNo", "/notebook/cyan.png");
        gp.notebooks[4].setPosition("Room2", gp.tileWidth * 3, gp.tileWidth * 3);
        gp.notebooks[5] = new Notebook("pinkNo", "/notebook/pink.png");
        gp.notebooks[5].setPosition("Room3", gp.tileWidth * 3, gp.tileWidth * 3);
        gp.notebooks[6] = new Notebook("blackNo", "/notebook/black.png");
        gp.notebooks[6].setPosition("Room1", gp.tileWidth * 2, gp.tileWidth * 5);
    }

    public void setChocolate() {
        gp.chocolates[0] = new Chocolate("chocolate", "/res/chocolate.png");
        gp.chocolates[0].setPosition("Room8", gp.tileWidth * 8, gp.tileWidth * 10);
        gp.chocolates[1] = new Chocolate("chocolate", "/res/chocolate.png");
        gp.chocolates[1].setPosition("Gym", gp.tileWidth * 7, gp.tileWidth * 15);
        gp.chocolates[2] = new Chocolate("chocolate", "/res/chocolate.png");
        gp.chocolates[2].setPosition("Room6", gp.tileWidth * 2, gp.tileWidth * 2);
    }
}
