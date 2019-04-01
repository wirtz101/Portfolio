package code;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//generate grid representing the island map and randomly placing islands onto the grid.
public class OceanMap {
	boolean[][] islands;
	static int dimensions = 20;
	static int islandCount = 30;
	Random rand = new Random();
	Point shipLocation;
	Point treasureLocation;
	LinkedList<Enemy> enemyShips = new LinkedList<Enemy>();
	List<Monster> monsters = new ArrayList<Monster>();
	private static OceanMap Grid;
	
	
	// Constructor
	private OceanMap(int dimensions, int islandCount){
		createGrid();
		placeIslands();
		shipLocation = placeShip();
		treasureLocation = placeTreasure();
	}
	
	public static OceanMap getGrid() {
		if (Grid == null) {
			synchronized(OceanMap.class) {
				if (Grid == null) {
					Grid = new OceanMap(dimensions, islandCount);
				}
			}
		}
		return Grid;	
	}
	
	// Create an empty map
	private void createGrid(){
		 islands = new boolean[dimensions][dimensions];
		 for(int x = 0; x < dimensions; x++)
			 for(int y = 0; y < dimensions; y++)
				 islands[x][y] = false;
	}
	
	// Place islands onto map
	private void placeIslands(){
		int islandsToPlace = islandCount;
		while(islandsToPlace >0){
			int x = rand.nextInt(dimensions);
			int y = rand.nextInt(dimensions);
			if(islands[x][y] == false){
				islands[x][y] = true;
				islandsToPlace--;
			}
		}
	}
	
	private Point placeShip(){
		boolean placedShip = false;
		int x=0,y=0;
		while(!placedShip){
			x = rand.nextInt(dimensions);
			y = rand.nextInt(dimensions);
			if(islands[x][y] == false){
				placedShip = true;
			}
		}
		return new Point(x,y);
	}

	private Point placeTreasure(){
		boolean placedTreasure = false;
		int x=0,y=0;
		while(!placedTreasure){
			x = rand.nextInt(dimensions);
			y = rand.nextInt(dimensions);
			if(islands[x][y] == false){
				placedTreasure = true;
			}
		}
		return new Point(x,y);
	}
	
	public Point getShipLocation(){
		return shipLocation;
	}
	
	public Point getTreasureLocation(){
		return treasureLocation;
	}
	
	// Return generated map
	public boolean[][] getMap(){
		return islands;
	}
	
	public int getDimensions(){
		return dimensions;
	}
	
	public boolean isOcean(int x, int y){
		if (!islands[x][y])
			return true;
		else
			return false;
	}
	
}