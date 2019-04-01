package code;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Submarine implements Observer, Enemy {

	Point shipLocation;
	Point submarineLocation;
	OceanMap map;
	Random rand = new Random();
	String name = "submarine";
	Image spriteImage = new Image("submarine.png",50,50,true, true);
	ImageView spriteImageView = new ImageView(spriteImage);
	
	public Submarine(OceanMap oceanMap) {
		
		this.map = oceanMap;
		this.shipLocation = map.shipLocation;
		boolean placedSubmarine = false;
		int x,y;
		while(!placedSubmarine) {
			x = rand.nextInt(map.dimensions);
			y = rand.nextInt(map.dimensions); 
   			if(oceanMap.isOcean(x, y)) {
   				submarineLocation = new Point(x,y);
   				placedSubmarine = true;
    			}
    		}
		oceanMap.enemyShips.add(this);
	}
	
	public void addToPane(AnchorPane root) {
		root.getChildren().add(spriteImageView);
	}
	
	public void draw(){
		//method to redraw the pirate as it moves
		int x = this.submarineLocation.x;
		int y = this.submarineLocation.y;
		spriteImageView.setX(x*50);
		spriteImageView.setY(y*50);	
	}
	
	public void sail() {	
		
		if (submarineLocation.y < shipLocation.y && 
			map.isOcean(submarineLocation.x, submarineLocation.y+1)) 
				submarineLocation.y++;
		
		else if (submarineLocation.y > shipLocation.y && 
				map.isOcean(submarineLocation.x, submarineLocation.y-1)) 
					submarineLocation.y--;
		
		if (submarineLocation.y == shipLocation.y &&
				submarineLocation.x > shipLocation.x && 
				map.isOcean(submarineLocation.x-1, submarineLocation.y)) 
					submarineLocation.x--;
		
		else if (submarineLocation.y == shipLocation.y &&
				submarineLocation.x < shipLocation.x && 
				map.isOcean(submarineLocation.x+1, submarineLocation.y+1)) 
					submarineLocation.x++;
		
		draw();
	}
	
	@Override
	public void update(Observable ship, Object arg) {
		if(ship instanceof Ship) { 
			shipLocation = ((Ship)ship).getShipLocation();
			sail();
		}
	}

}
