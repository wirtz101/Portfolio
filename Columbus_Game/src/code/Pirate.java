package code;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Pirate implements Observer, Enemy {
	
	Point shipLocation;
	Point pirateLocation;
	SailStrategy sailStrategy;
	OceanMap map;
	Random rand = new Random();
	String name = "pirate";
	Image spriteImage = new Image("pirateship.png",50,50,true, true);
	ImageView spriteImageView = new ImageView(spriteImage);
	
	public Pirate(OceanMap oceanMap) {
		
		this.map = oceanMap;
		this.shipLocation = map.shipLocation;
		this.sailStrategy = new HorizontalSail();		//default sail strategy
		boolean placedPirate = false;
		int x,y;
		while(!placedPirate) {
			x = rand.nextInt(map.dimensions);
			y = rand.nextInt(map.dimensions); 
   			if(oceanMap.isOcean(x, y)) {
   				pirateLocation = new Point(x,y);
   				placedPirate = true;
    			}
    		}
		oceanMap.enemyShips.add(this);
	}
	
	public void addToPane(AnchorPane root) {
		root.getChildren().add(spriteImageView);
	}
	
	public void draw(){
		//method to redraw the pirate as it moves
		int x = this.pirateLocation.x;
		int y = this.pirateLocation.y;
		spriteImageView.setX(x*50);
		spriteImageView.setY(y*50);	
	}
	
	public void setSailStrategy(SailStrategy sail) {
		this.sailStrategy = sail;
	}
	
	public Point getPirateLocation() {
		return pirateLocation;
	}
	
	public Point getShipLocation() {
		return shipLocation;
	}
	
	public void setX(int x) {
		pirateLocation.x = x;
	}
	
	public void setY(int y) {
		pirateLocation.y = y;
	}

	public void sail() {
		sailStrategy.sail(this);
		draw();
	}

	@Override
	public void update(Observable ship, Object obj) {
		if(ship instanceof Ship) { 
			shipLocation = ((Ship)ship).getShipLocation();
			sail();
		}
	}
	
}
