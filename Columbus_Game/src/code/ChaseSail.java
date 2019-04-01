package code;

import java.awt.Point;

public class ChaseSail implements SailStrategy {

	OceanMap oceanMap;

	public void sail(Pirate pirate) {

		oceanMap = pirate.map;
		int shipX = pirate.shipLocation.x;
		int shipY = pirate.shipLocation.y;
		int pirateX = pirate.pirateLocation.x;
		int pirateY = pirate.pirateLocation.y;

		if (pirate.shipLocation != pirate.pirateLocation) {

			// Player is east
			if (pirateX < oceanMap.getDimensions() - 1 && pirateX < shipX && pirateY == shipY
					&& oceanMap.isOcean(pirateX + 1, pirateY)) {
				pirate.pirateLocation.x++;
			}

			// Player is west
			else if (pirateX > 0 && pirateX > shipX && pirateY == shipY && oceanMap.isOcean(pirateX - 1, pirateY)) {
				pirate.pirateLocation.x--;
			}

			// Player is north
			if (pirateY > 0 && pirateY > shipY && pirateX == shipX && oceanMap.isOcean(pirateX, pirateY - 1)) {
				pirate.pirateLocation.y--;
			}

			// Player is south
			else if (pirateY < oceanMap.getDimensions() - 1 && pirateY < shipY && pirateX == shipX
					&& oceanMap.isOcean(pirateX, pirateY + 1)) {
				pirate.pirateLocation.y++;
			}

			// Player is northwest
			if (shipX < pirateX && shipY < pirateY) {
				if (pirateX - 1 > 0 && pirateY - 1 > 0 && oceanMap.isOcean(pirateX - 1, pirateY - 1)) {
					pirate.pirateLocation.x--;
					pirate.pirateLocation.y--;
				}
			}

			// Player is northeast
			else if (shipX > pirateX && shipY < pirateY) {
				if (pirateX + 1 < oceanMap.getDimensions() && pirateY - 1 > 0
						&& oceanMap.isOcean(pirateX + 1, pirateY - 1)) {
					pirate.pirateLocation.x++;
					pirate.pirateLocation.y--;
				}
			}

			// Player is southwest
			if (shipX < pirateX && shipY > pirateY) {
				if (pirateX - 1 > 0 && pirateY + 1 < oceanMap.getDimensions()
						&& oceanMap.isOcean(pirateX - 1, pirateY + 1)) {
					pirate.pirateLocation.x--;
					pirate.pirateLocation.y++;
				}
			}

			// Player is southeast
			else if (shipX > pirateX && shipY > pirateY) {
				if (pirateX + 1 < oceanMap.getDimensions() && pirateY + 1 < oceanMap.getDimensions()
						&& oceanMap.isOcean(pirateX + 1, pirateY + 1)) {
					pirate.pirateLocation.x++;
					pirate.pirateLocation.y++;
				}
			}
		}
	}

}