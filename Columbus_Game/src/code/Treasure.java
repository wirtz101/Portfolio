package code;
import java.awt.Point;

public class Treasure {

	Point currentLocation;
    OceanMap oceanMap;
    
    public Treasure(OceanMap oceanMap){    	
    	this.oceanMap = oceanMap;
    	currentLocation = oceanMap.getTreasureLocation();
    }
    
    public Point getTreasureLocation(){
    	return currentLocation;
    }

}
