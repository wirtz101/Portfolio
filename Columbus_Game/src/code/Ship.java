package code;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Ship extends Observable {

	Point currentLocation;
	OceanMap oceanMap;
	
	public Ship() {
		oceanMap = OceanMap.getGrid();
		currentLocation = oceanMap.getShipLocation();
	}
	
	public Point getShipLocation() {
		return currentLocation;
	}
	 
	//method to register multiple enemy ships
	public void addMultipleObservers(LinkedList<Enemy> enemies) {
		for (Enemy temp : enemies) {
            this.addObserver((Observer) temp);
        }
	}
	
	public void goEast(){
	    	if(currentLocation.x<oceanMap.getDimensions()-1 && 
	    	   oceanMap.isOcean(currentLocation.x+1, currentLocation.y)){
	    		currentLocation.x++;
	    		setChanged();
	    		notifyObservers();
	    	}
	}
	
	public void goWest(){
		if(currentLocation.x >0 && oceanMap.isOcean(currentLocation.x-1, currentLocation.y)){
    			currentLocation.x--;
    			setChanged();
    			notifyObservers();
    		}
    }
    
    public void goNorth(){
    		if(currentLocation.y>0 && oceanMap.isOcean(currentLocation.x, currentLocation.y-1)){
    			currentLocation.y--;
    			setChanged();
    			notifyObservers();
    		} 
    }
    
    public void goSouth(){
    		if(currentLocation.y<oceanMap.getDimensions()-1 && 
    		   oceanMap.isOcean(currentLocation.x, currentLocation.y+1)){
    			currentLocation.y++;
    			setChanged();
    			notifyObservers();
    		}  	
    }
	
}
