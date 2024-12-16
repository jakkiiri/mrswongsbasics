// class responsible for creating the map object

public class GameMap implements Comparable <GameMap> {
	
	// instance variables
	private String name;
	
	/* Map Appendix
	 * 0 = Transversable Space
	 * 1 = Default Wall
	 * 2 = Hallway Door
	 * 
	 */
	private int[][] map;
	
	// constructor
	public GameMap (String name, int[][] map) {
		this.map = map;
		this.name = name;
		this.map = map;
	}
	
	// getters
	public int[][] getMap() {
		return map;
	}
	
	public String getName() {
		return name;
	}

	// equals method
	public boolean equals(Object o) {
		GameMap m = (GameMap) o;
		return this.name.equalsIgnoreCase(m.name);
	}

	// hashCode method
	public int hashCode() {
		return this.name.hashCode();
	}

	// compareTo method
	public int compareTo(GameMap m) {
		return this.name.toLowerCase().compareTo(m.name.toLowerCase());
	}

	// alterLeft method (exclusive to hallway)
	public void alterLeft() {
		map[18][9] = 3;
        map[19][9] = 3;
        map[20][9] = 3;
	}

	// alterLeft method (exclusive to hallway)
	public void nalterLeft() {
		map[18][9] = 0;
        map[19][9] = 0;
        map[20][9] = 0;
	}

	// alterDown method (exclusive to hallway)
	public void alterDown() {
		map[39][24] = 2;
        map[39][25] = 2;
        map[39][26] = 2;
	}

	// alterDown method (exclusive to hallway)
	public void nalterDown() {
		map[39][24] = 0;
        map[39][25] = 0;
        map[39][26] = 0;
	}

	// alterRight method (exclusive to hallway)
	public void alterRight() {
		map[19][46] = 1;
        map[20][46] = 1;
        map[21][46] = 1;
	}

	// alterRight method (exclusive to hallway)
	public void nalterRight() {
		map[19][46] = 0;
        map[20][46] = 0;
        map[21][46] = 0;
	}

	// alterUp method (exclusive to gym)
	public void alterUp() {
		map[4][12] = 4;
        map[4][13] = 4;
        map[4][14] = 4;
	}

	// alterUp method (exclusive to gym)
	public void nalterUp() {
		map[4][12] = 0;
        map[4][13] = 0;
        map[4][14] = 0;
	}
}
