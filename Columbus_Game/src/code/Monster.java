package code;
import java.awt.Point;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface Monster {
	public void add(Monster monster);				// add monster into composite
	public void remove(Monster monster);			// remove monster from composite
	public void move();								// move monsters simultaneously
	public int getX();								// get x value
	public int getY();								// get y value
	public void setX(int dx);						// set x value
	public void setY(int dy);						// set y value
	public boolean ContainsPoint(Point point);		// checks to see if component has certain point
	public void setPositionX(int dx);
	public void setPositionY(int dy);
	public Point getCurrentLocation();				// get current location
	public void setLocation(Point pos);				// set location
	void addToPane(ObservableList<Node> scene);
	
}
