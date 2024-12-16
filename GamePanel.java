import java.awt.event.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

import java.awt.image.BufferedImage;
import java.io.*;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, MouseListener {


    private static int gameMode = 0;

    // Game screens
    HashMap<String, BufferedImage> screens = new HashMap<>();

	// Game Variables & Constants
	final int originalTileWidth = 16;
	int scale = 4;
    boolean active = true;
    int difficulty = 0;

    int FPS = 60;
    int frameCount = 0;
    int prevCount = 0;
    Thread thread;
    final int tileWidth = originalTileWidth * scale;
	final int maxCol = 12;
	final int maxRow = 8;
	final int screenWidth = tileWidth * maxCol;
	final int screenHeight = tileWidth * maxRow;

	// Map Settings
	int maxMapCol = 19;
	int maxMapRow = 54;
	int mapWidth = tileWidth * maxMapCol;
	int mapHeight = tileWidth * maxMapRow;
    
    // high scores
    ArrayList<Score> highScores = new ArrayList<Score>();
    boolean scoreWritten = false;
    long startTime = 0;
    String name = "";
    boolean searching = false;
    String searchName = "";
    boolean searchNamePopupOpened = false;

	// controls
	KeyInput keyI = new KeyInput();

	// Collision checker
	CollisionChecker collision = new CollisionChecker(this);

    // object setter
    ObjectSetter oSetter = new ObjectSetter(this);

    // sound 
    Sound sound = new Sound();

	// Player
	Player player = new Player(this,keyI);

	// tile
	TileManager tileM = new TileManager(this);

	// Mrs. Wong
	MrsWong mrsWong = new MrsWong(this, tileM);

    // sprint bar
    SprintBar sB = new SprintBar(this);

    // player inventory
    Inventory inventory = new Inventory(this, keyI);

	// notebook array 7 in total
    Notebook notebooks[] = new Notebook[7];

    // chocolate bar / chatGPT array 
    Chocolate chocolates[] = new Chocolate[3];
    @SuppressWarnings("unchecked")
    ArrayList <ArrayList<Question>> questions = new ArrayList <ArrayList <Question>> (7);

    // music boolean
    boolean menuM = true;
    boolean gameM = true;
    boolean endGameM = false;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setVisible(true);

		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyI);
		this.setFocusable(true);

        // start thread
        thread = new Thread(this);
        thread.start();
    }

    public void setGame() {
        oSetter.setNotebook();
        oSetter.setChocolate();
    }

    // reset all game variables
    public void resetGame() {
        player.sprinting = false;
        player.setValues();
        mrsWong.setValues();
        mrsWong.frameCount = 0;
        for (int i = 0; i < 7; i++) {
            notebooks[i].drawn = true;
        }
        for (int i = 0; i < 3; i++) {
            chocolates[i].drawn = true;
        }
        MapIndex.nalterDown();
        MapIndex.nalterLeft();
        MapIndex.nalterRight();
        MapIndex.nalterUp();
        tileM.setMap("Hallway");
        difficulty = 0;
    }

    public void run(){
        initialize();
        while (true) {
            // System.out.println(active);
            // main game loop
            if (active) {
                update(frameCount);
                this.repaint();
                frameCount++;
            }

            try {
                Thread.sleep(1000/FPS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// cast to 2d graphics
		Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.WHITE);
        if (gameMode == 0) { // main menu
            endGameM = false;
            gameM = true;
            if (menuM) {
                playSE(0);
                menuM = false;
            }
            g2.drawImage(screens.get("menu"), 0, 0, screenWidth, screenHeight, null);
            if (frameCount-prevCount == 90) {
                if (keyI.pressed) {
                    gameMode = 1;
                    prevCount = frameCount;
                }
            }
        }
        else if (gameMode == 1) { // pregame lore
            g.drawImage (screens.get("story"), 0, 0, screenWidth, screenHeight, null); 
            if (frameCount-prevCount == 90) {
                if (keyI.pressed) {
                     gameMode = 2;
                    prevCount = frameCount;
                }
                
            }
        }
        else if (gameMode == 2) { // instructions & about
            g.drawImage (screens.get("about"), 0, 0, screenWidth, screenHeight, null);
                if (frameCount-prevCount == 90) {
                    if (keyI.pressed) {
                        gameMode = 3;
                        prevCount = frameCount;
                    }
                }
            
        }   
        else if (gameMode == 3) { // controls & items
            g.drawImage (screens.get("controls"), 0, 0, screenWidth, screenHeight, null); 
                if (frameCount-prevCount == 90) {
                    if (keyI.pressed) {
                        gameMode = 4;
                        prevCount = frameCount;
                    }
                
            }
        }
        else if (gameMode == 4) { // game 
            menuM = true;
            if (gameM) {
                playSE(1);
                gameM = false;
            }
            if (endGameM) {
                stopMusic();
                playSE(6);
                playSE(7);
                endGameM = false;
            }
            // red background at endgame
            if (player.endGame) {
                g.setColor(Color.RED);
                g.fillRect(0, 0, screenWidth, screenHeight);
            }
            scoreWritten = false;
            // draw red bg if player is at endgame
            if (player.endGame) {
            g2.setColor(Color.RED);
            g2.fillRect(0,0, screenWidth, screenHeight);
            }
            // tile
            tileM.draw(g2);
            // notebooks
            for (int i = 0; i < notebooks.length; i++) {
                if (notebooks[i] != null) {
                    notebooks[i].draw(g2, this);
                }
            }
            // chocolate bars
            for (int i = 0; i < chocolates.length; i++) {
                if (chocolates[i] != null) {
                    chocolates[i].draw(g2, this);
                }
            }
            // player
            player.draw(g2);
            // mswong
            mrsWong.draw(g2);
            // sprintbar
            sB.draw(g2);
            // inventory
            inventory.draw(g2);
        }
        else if (gameMode == 5) { // jumpscare
            g2.drawImage(screens.get("jumpscare"), 0, 0, screenWidth, screenHeight, null);
            if (frameCount-prevCount == 120) {
                gameMode = 6;
                prevCount = frameCount;
            }
        }
        else if (gameMode == 6) { // game over
            menuM = true;
            g2.drawImage(screens.get("gameover"), 0, 0, screenWidth, screenHeight, null);
            if (frameCount - prevCount >= 60) {
                if (keyI.pressed) {
                    gameMode = 0;
                    prevCount = frameCount;
                }
            }
        } else if (gameMode == 7) { // win
            menuM = true;
            g2.drawImage(screens.get("win"), 0, 0, screenWidth, screenHeight, null);
            if (frameCount - prevCount >= 60) {
                if (keyI.pressed) {
                    gameMode = 0;
                    prevCount = frameCount;
                }
            }
        } else if (gameMode == 8) { // high scores
            int initPos = 60;
            g2.setColor(Color.WHITE);
           
            
            g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 15f));

            g2.drawString("Press K to return", 10, 30);
            g2.drawString("Press W to sort by name", 10, 60);
            g2.drawString("Press S to sort by time", 10, 90);
            g2.drawString("Press A to search by name", 10, 120);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
            g2.drawString("Time to complete", 300, 30);

            // if searching high scores by a certain name
            if (searching) {
                Collections.sort(highScores, new SortByName());
                int temp = Collections.binarySearch(highScores, new Score(searchName, 13121), new SortByName());
                if (temp >= 0) {
                    g2.drawString(highScores.get(temp).getName() + ": " + (double) highScores.get(temp).getTime() / 1000 + " sec", 250, 60);
                }
            } else {
                for (int i = 0; i < highScores.size(); i++) {
                    g2.drawString(highScores.get(i).getName() + ": " + (double) highScores.get(i).getTime() / 1000 + " sec", 250, initPos + (i * 30));
                }
            }
            
        }
		g2.dispose();
	}
    // sound methods
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic() {
        sound.stop();
    }
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }


    public void initialize(){
		//setups before the game starts running

        // read in questions
        try {
            Scanner input = new Scanner(new FileReader("questions.txt"));
            for (int i = 0; i < 7; i++) {
                questions.add(new ArrayList<Question> ());
                for (int j = 0; j < 3; j++) {
                    String question = input.nextLine();
                    String answer = input.nextLine();
                    // System.out.println(question + " " + answer);
                    questions.get(i).add(new Question(question, answer));
                }
            }
            input.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
         // read in scores
         try {
            Scanner input = new Scanner(new FileReader("scores.txt"));
            while(input.hasNext()) {
                String[] temp = input.nextLine().trim().split(" ");
                highScores.add(new Score(temp[0], Long.parseLong(temp[1])));
            }
            Collections.sort(highScores);
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        try {
            screens.put("menu", ImageIO.read(getClass().getResourceAsStream("/screens/menu.png")));
            screens.put("story", ImageIO.read(getClass().getResourceAsStream("/screens/story.png")));
            screens.put("win", ImageIO.read(getClass().getResourceAsStream("/screens/win.png")));
            screens.put("gameover", ImageIO.read(getClass().getResourceAsStream("/screens/gameOver.png")));
            screens.put("jumpscare", ImageIO.read(getClass().getResourceAsStream("/screens/jumpScare.png")));
            screens.put("controls", ImageIO.read(getClass().getResourceAsStream("/screens/controls.png")));
            screens.put("about", ImageIO.read(getClass().getResourceAsStream("/screens/about.png")));
        } catch (IOException e) {
            System.out.println("Error loading images");
        }
		setGame();

	}

    public void updateScores(long newTime, String name) {
        highScores.add(new Score(name, newTime));
        Collections.sort(highScores);
        try {
            PrintWriter b = new PrintWriter(new FileWriter("scores.txt", true));
            b.print("\n" + name + " " + newTime);
            b.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
	}
	
	public void update(int frameCount) {
        if (gameMode == 0) {
            if (frameCount - prevCount >= 60) {
                if (keyI.use) { // if j is pressed
                    gameMode = 8;
                }
                else if (keyI.pressed) {
                    gameMode = 1;
                    prevCount = frameCount;
                }
            }
                
            }
        
        if (gameMode == 1) {
            if (frameCount - prevCount >= 60) {
                if (keyI.pressed) {
                 gameMode = 2;
                 prevCount = frameCount;
                }
            }
        }

        if (gameMode == 2) {
            if (frameCount - prevCount >= 60) {
                if (keyI.pressed) {
                 gameMode = 3;
                 prevCount = frameCount;
                }
            }
        }

        if (gameMode == 3) {
            if (frameCount - prevCount >= 60) {
                if (keyI.pressed) {
                    gameMode = 4;
                    startTime = System.currentTimeMillis();
                }
            }
        }

        if (gameMode == 4) {
            player.update();
            mrsWong.update();
            inventory.update();
            if (player.tileX == mrsWong.tileX && player.tileY == mrsWong.tileY && player.map.equals(mrsWong.mapName)) {
                prevCount = frameCount;
                gameMode = 5;
                resetGame();
                stopMusic();
                playSE(5);
            } 
            if (player.exits == 4) {
                prevCount = frameCount;
                gameMode = 7;
                resetGame();
                stopMusic();
                endGameM = true;
            }
        }
        
        if (gameMode == 6) {
            if (frameCount - prevCount >= 60) {
                if (keyI.pressed) {
                    gameMode = 0;
                    prevCount = frameCount;
                }
            }
        }

        if (gameMode == 7) {
            if (!scoreWritten) {
                prevCount = frameCount;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getNamePopup("You Won!", "Your name: ");
                scoreWritten = true;
            }
            if (frameCount - prevCount >= 60) {
                if (keyI.pressed) {
                    gameMode = 0;
                    prevCount = frameCount;
                }
            }
        }
        if (gameMode == 8) {
            // g2.drawString("Press K to return", 10, 30);
            // g2.drawString("Press W to sort by name", 10, 60);
            // g2.drawString("Press S to sort by time", 10, 90);
            // g2.drawString("Press A to search by name", 10, 120);
            if (keyI.useAlt) { // if k is pressed, go back
                searching = false;
                gameMode = 0;
                prevCount = frameCount;
            } else if (keyI.up) { // sort by name
                searching = false;
                Collections.sort(highScores, new SortByName());
            } else if (keyI.down) { // sort by time
                searching = false;
                Collections.sort(highScores);
            } else if (keyI.left) { // search by name
                System.out.println("pop");
                if (!searchNamePopupOpened) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    searchNamePopup("Search by name", "Enter name: ");
                    searchNamePopupOpened = true;
                }
                searching = true;
            }
        }

	}

    public void searchNamePopup(String frameName, String prompt) {
        JFrame frame = new JFrame(frameName);
        JPanel panel = new JPanel();
        JLabel question = new JLabel(prompt);
        JTextField answer= new JTextField("", 8);
        JButton submitButton = new JButton("submit");

        frame.setLocationRelativeTo(null);
        // frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        panel.add(question);
        panel.add(answer);
        panel.add(submitButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                searchName = answer.getText().trim();
                searchNamePopupOpened = false;
            }
        });
    }

    public void getNamePopup(String frameName, String prompt) {
        JFrame frame = new JFrame(frameName);
        JPanel panel = new JPanel();
        JLabel question = new JLabel(prompt);
        JTextField answer= new JTextField("", 8);
        JButton submitButton = new JButton("submit");

        frame.setLocationRelativeTo(null);
        // frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        panel.add(question);
        panel.add(answer);
        panel.add(submitButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                name = answer.getText().trim();
                updateScores(System.currentTimeMillis() - startTime, name);
            }
        });
	}

    public void showPopup(String q1, String ans1, String q2, String ans2, String q3, String ans3) {
        System.out.println(active);
        active = false;
        JFrame frame = new JFrame("Notebook");
        frame.setLocationRelativeTo(null);
        // frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        JPanel panel = new JPanel();
        frame.setPreferredSize(new Dimension(800, 400));

        JLabel question1 = new JLabel(q1);
        JTextField answer1 = new JTextField("", 8);
        JLabel question2 = new JLabel(q2);
        JTextField answer2 = new JTextField("", 8);
        JLabel question3 = new JLabel(q3);
        JTextField answer3 = new JTextField("", 8);

        question1.setBounds(0, 0, 500, 500);

        
        JButton submitButton = new JButton("SUBMIT!");

        panel.add(question1);
        panel.add(answer1);
        panel.add(question2);
        panel.add(answer2);
        panel.add(question3);
        panel.add(answer3);
        panel.add(submitButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        // play audio
        playSE(2);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("answer submitted");
                System.out.println(answer1.getText().trim());
                System.out.println(ans2);
                if (!answer1.getText().trim().equalsIgnoreCase(ans1)) {
                    difficulty += 1;
                }
                if (!answer2.getText().trim().equalsIgnoreCase(ans2)) {
                    difficulty += 1;
                }
                if (!answer3.getText().trim().equalsIgnoreCase(ans3)) {
                    difficulty += 1;
                }
                active = true;
                frame.dispose();
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

    
}