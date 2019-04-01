package tc;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.junit.Test;
import code.OceanMap;
import code.Ship;
import javafx.scene.layout.AnchorPane;
import code.Enemy;


public class ShipTest {

	OceanMap oceanMap = OceanMap.getGrid();	
	Ship ship1, ship2;
		
	
	//Test that addMultipleObservers registers the correct amount
	//of enemy ships
	@Test
	public void test() {
		
		LinkedList<Enemy> enemies = new LinkedList<Enemy>();
		NullEnemy e1 = new NullEnemy();
		NullEnemy e2 = new NullEnemy();
		NullEnemy e3 = new NullEnemy();
		NullEnemy e4 = new NullEnemy();
		enemies.add(e1);
		enemies.add(e2);
		enemies.add(e3);
		enemies.add(e4);

		ship1 = new Ship();
		ship1.addMultipleObservers(enemies);
		assertEquals(ship1.countObservers(),4);	
	}
	
	//test that addMultipleObservers and addObserver returns same value
	//when only 1 observer is added
	@Test
	public void test2() {
		
		LinkedList<Enemy> enemies = new LinkedList<Enemy>();
		NullEnemy e1 = new NullEnemy();
		enemies.add(e1);
		
		ship1 = new Ship();
		ship2 = new Ship();
		ship1.addObserver(e1);
		ship2.addMultipleObservers(enemies);
		
		assertTrue(ship1.countObservers() == ship2.countObservers());
		
	}
}

class NullEnemy implements Observer, Enemy {

	//a null enemy for implementing the test case
	public NullEnemy() {}
	
	@Override
	public void sail() {}

	@Override
	public void addToPane(AnchorPane root) {}

	@Override
	public void update(Observable o, Object arg) {}	
	
}


