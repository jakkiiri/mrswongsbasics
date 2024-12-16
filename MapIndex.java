// class responsible for reading map array data from a text file
// and puts each individual map object into a map data structure
import java.util.*;
import java.io.*;

public class MapIndex {

	// hashmap to store all of the maps
	private static HashMap <String, GameMap> maps = new HashMap <> ();
	// treeMap to store the maps alphabetically
	private static TreeMap <String, GameMap> maps1 = new TreeMap <> ();
	// hashSet to store the map objects
	private static HashSet <GameMap> maps2 = new HashSet <> ();

	public MapIndex (String fileName) {
		try {
			// scanner to read textfile
			Scanner input = new Scanner(new File(fileName));
			
			String name = "";
			int a;
			int b;
			// temporary line
			String temp = "";
			while (input.hasNextLine()) {
				// read in name
				name = input.nextLine().trim();
				temp = input.nextLine().trim();
				String[] temp1 = temp.split(" ");
				// read in dimensions
				a = Integer.parseInt(temp1[0]);
				b = Integer.parseInt(temp1[1]);
				// read in map elements
				int[][] map = new int[a][b];
				for (int i = 0; i < a; i++) {
					temp = input.nextLine();
					temp1 = temp.split(" ");
					for (int k = 0; k < b; k++) {
						map [i][k] = Integer.parseInt(temp1[k].trim());
					}
				}
				// add map into the data structures
				GameMap g = new GameMap (name, map);
				GameMap g2 = new GameMap(name, map);
				maps.put(name, g);
				maps1.put(name, g2);
				maps2.add(g);
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Maps Not Found");
		}
	}

	// getters
	public static HashMap <String, GameMap> getMaps() {
		return maps;
	}
	public static TreeMap <String, GameMap> getMaps1() {
		return maps1;
	}

	// setmap
	public static void setMap() {
		maps = new HashMap<>(maps1);
	}

	// special methods to alter the maps in endGame
	public static void alterLeft() {
		maps.get("Hallway").alterLeft();
	}

	public static void nalterLeft() {
		maps.get("Hallway").nalterLeft();
	}

	public static void alterDown() {
		maps.get("Hallway").alterDown();
	}

	public static void nalterDown() {
		maps.get("Hallway").nalterDown();
	}

	public static void alterRight() {
		maps.get("Hallway").alterRight();
	}

	public static void nalterRight() {
		maps.get("Hallway").nalterRight();
	}

	public static void alterUp() {
		maps.get("Gym").alterUp();
	}

	public static void nalterUp() {
		maps.get("Gym").nalterUp();
	}
}
