package code;

public class EnemyFactory {
	OceanMap map;
	
	public EnemyFactory(OceanMap oceanmap) {
		this.map = oceanmap;
	}
	
	//method to create any enemy
		public Enemy createEnemy(String enemyType) {
			
			if (enemyType == null) {
				return null;
			}
			
			if(enemyType == "pirate") {
				Pirate pirate = new Pirate(map);
				pirate.draw();
				return pirate;
			}
			else if(enemyType == "submarine") {
				Submarine submarine = new Submarine(map);
				submarine.draw();
				return submarine;
			}
			
			return null;	
		}
		
		//method to create specific pirate
		//used to spawn fleet of pirates
		public Pirate createPirate(SailStrategy strategy) {
			Pirate pirate = new Pirate(map);
			pirate.draw();
			pirate.setSailStrategy(strategy);
			return pirate;
		}
}
