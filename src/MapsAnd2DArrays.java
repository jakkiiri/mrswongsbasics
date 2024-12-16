import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial") //funky warning, just suppress it. It's not gonna do anything.
public class MapsAnd2DArrays extends JPanel implements Runnable, KeyListener, MouseListener{
	
	//self explanatory variables
	int FPS = 60;
	Thread thread;
	int screenWidth = 600;
	int screenHeight = 600;
	
	int[][] map = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 1, 0, 1, 1, 0, 0, 0, 1},	// empty space = 0, wall = 1
			{0, 0, 0, 0, 1, 1, 0, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		};
	int numRows = 10;
	int numCols = 10;
	int tileWidth = 60;
	int tileHeight = 60;
	
	public MapsAnd2DArrays() {
		//sets up JPanel
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);
		
		//starting the thread
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		initialize();
		while(true) {
			//main game loop
			update();
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initialize() {
		//setups before the game starts running
		
	}
	
	public void update() {
		//update stuff
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//white background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, screenWidth, screenHeight);
		
		//draw stuff
		
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				int x = col * tileWidth;
				int y = row * tileHeight;
				if(map[row][col] == 1) {
					g.setColor(Color.BLACK);
					g.fillRect(x, y, tileWidth, tileHeight);
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		
	}
	
	public static void main(String[] args) {
		
		//The following lines creates your window
		
		//makes a brand new JFrame
		JFrame frame = new JFrame ("Example");
		//makes a new copy of your "game" that is also a JPanel
		MapsAnd2DArrays myPanel = new MapsAnd2DArrays ();
		//so your JPanel to the frame so you can actually see it
		frame.add(myPanel);
		//so you can actually get keyboard input
		frame.addKeyListener(myPanel);
		//so you can actually get mouse input
		frame.addMouseListener(myPanel);
		//self explanatory. You want to see your frame
		frame.setVisible(true);
		//some weird method that you must run
		frame.pack();
		//place your frame in the middle of the screen
		frame.setLocationRelativeTo(null);
		//without this, your thread will keep running even when you windows is closed!
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//self explanatory. You don't want to resize your window because
		//it might mess up your graphics and collisions
		frame.setResizable(false);
	}
}
