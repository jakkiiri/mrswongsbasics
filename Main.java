import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        //makes a brand new JFrame
		JFrame frame = new JFrame ("Mrs. Wongs Basics");
		//makes a new copy of your "game" that is also a JPanel
		GamePanel myPanel = new GamePanel();
		//so your JPanel to the frame so you can actually see it
		frame.add(myPanel);
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
		frame.setIconImage(myPanel.screens.get("jumpscare"));
    }	
}
