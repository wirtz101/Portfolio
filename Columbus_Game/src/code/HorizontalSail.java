package code;

public class HorizontalSail implements SailStrategy{
	
	int count = 0;
	
	public void sail(Pirate pirate) {
		
		if(count%4 < 2 && pirate.pirateLocation.x > 0) {
			if (pirate.map.isOcean(pirate.pirateLocation.x-1, pirate.pirateLocation.y))
				pirate.pirateLocation.x--;
		}
		
		else if (count%4 >= 2 && pirate.pirateLocation.x < pirate.map.getDimensions()) {
				if (pirate.map.isOcean(pirate.pirateLocation.x+1, pirate.pirateLocation.y)) 
					pirate.pirateLocation.x++;
		}	
		count++;	
	}	
}
