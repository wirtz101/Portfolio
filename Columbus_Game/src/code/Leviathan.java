package code;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.collections.ObservableList;
import javafx.scene.Node;

//Composite Class

public class Leviathan implements Monster, Runnable {
	/*
	 Jobs:
	 	- create new instance of Leviathan when 2 serpents collide
	 	- increase size of Leviathan each time it collides with a serpent
	 	- Keep doing this until All serpents are devoured and only
	 	  leviathans are on the map
	 	- DON'T allow leviathan to be devoured by another leviathan (not required?)
	 	- Change color indicating that indicates difference between
	 	  serpent and leviathan
	 */
	
	OceanMap map;
	Boolean running = true;
	int radius;
	Random rand = new Random();
	int scalingFactor;
	int length = 0;
	Point location;
	Circle circle;
	int x,y;
	boolean collision;
	List<Monster> components = new ArrayList<Monster>();
	
	public Leviathan(int scale, OceanMap map, boolean collision) {
		
		this.radius = 20;
		this.scalingFactor = scale;
		this.collision = collision;
		this.map = map;
		map.getMap();
		
		boolean placedMonster = false;
		while (!placedMonster) {
			x = rand.nextInt(map.getDimensions());
			y = rand.nextInt(map.getDimensions());
			if (map.isOcean(x, y)) {
				location = new Point(x,y);
				placedMonster = true;
			}
		}
		
		circle = new Circle();
		circle.setRadius(radius);
		circle.setFill(Color.RED);
		setPositionX(x);
		setPositionY(y);
//		map.monsters.add(this);

	}	
	
	@Override
	public void addToPane(ObservableList<Node> scene) {
		Circle circle = getCircle();
		circle.setFill(Color.RED);
		scene.add(circle);
	}
	
	public Circle getCircle() {
		return circle;
	}
	
	public void setCircle(Circle circle) {
		this.circle = circle;
		circle.setFill(Color.RED);
	}
	
	@Override
	public void add(Monster monster) {
		components.add(monster);
		radius = radius +1;
	}

	@Override
	public void remove(Monster monster) {
		components.remove(monster);
		radius = radius -1;
	}
	
	public void move() {
		int xMove = getX() + rand.nextInt(3)-1;
		if (xMove >=0 && xMove<=20)
			setX(xMove);
		int yMove = getY() + rand.nextInt(3)-1;
		if (yMove >=0 && yMove <=20) 
			setY(yMove);
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			move();
		}
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int dx) {
		this.x = dx;
		setPositionX(x);
	}

	@Override
	public void setY(int dy) {
		this.y = dy;
		setPositionY(y);
	}

	@Override
	public boolean ContainsPoint(Point point) {
		return circle.contains(point.x, point.y);
	}

	@Override
	public void setPositionX(int dx) {
		circle.setCenterX(dx*scalingFactor + (scalingFactor/2));
	}

	@Override
	public void setPositionY(int dy) {
		circle.setCenterY(dy*scalingFactor + (scalingFactor/2));
	}

	@Override
	public Point getCurrentLocation() {
		return location;
	}

	@Override
	public void setLocation(Point pos) {
		this.location = pos;
	}
}
