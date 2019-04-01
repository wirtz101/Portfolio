package code;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.awt.Point;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.scene.Node;

// Leaf Node: generates serpent
public class Serpent implements Monster, Runnable{

	OceanMap map;
	Boolean running = true;
	int radius;
	Random rand = new Random();
	int scalingFactor;
	Point location;
	int x,y;
	Circle circle;
	boolean collision;
	
	public Serpent(int scalingFactor, OceanMap map, boolean collision) {
		this.radius = 20;
		this.scalingFactor = scalingFactor;
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
		circle.setFill(Color.LIMEGREEN);
		setPositionX(x);
		setPositionY(y);
		map.monsters.add(this);
		
	}
	
	public Circle getCircle() {
		return circle;
	}
	
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	@Override
	public void addToPane(ObservableList<Node> scene) {
		Circle circle = getCircle();
		scene.add(circle);
	}
	
	public void move() {
		int xMove = getX() + rand.nextInt(3)-1;
		if (xMove >=0 && xMove <=20) 
			setX(xMove);
		int yMove = getY() + rand.nextInt(3)-1;
		if (yMove >=0 && yMove<=20) 
			setY(yMove);
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
	
	public boolean getCollision() {
		return collision;
	}
	
	public void setCollision(boolean collision) {
		this.collision = collision;
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
	public void add(Monster monster) {
		// doesn't apply to leaf node
	}

	@Override
	public void remove(Monster monster) {
		// doesn't apply to leaf node
	}
}
