package code;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class OceanExplorer extends Application{

	boolean[][] islandMap;
	boolean win = false;
	int count = 0;
	AnchorPane root;
	final int scale = 50;
	OceanMap oceanMap;
	Image shipImage;
	Image winImage;
	Image treasureImage;
	Image islandImage;
	ImageView shipImageView;
	ImageView treasureImageView;
	ImageView winImageView;
	Scene scene;
	Treasure treasure;
	Ship ship;
	Serpent serpent;
	Thread serpentThread;
	Leviathan levi;
	Thread leviThread;
	Label moves;
	
	@Override
	public void start(Stage mapStage) throws Exception {
		
		//generate the ocean
		oceanMap = OceanMap.getGrid();
		islandMap = oceanMap.getMap();
		
		root = new AnchorPane();
		drawMap();
		
		ship = new Ship();	
		treasure = new Treasure(oceanMap);

		EnemyFactory enemyFactory = new EnemyFactory(oceanMap);
		Enemy enemy1 = enemyFactory.createEnemy("submarine");
		Enemy enemy2 = enemyFactory.createEnemy("pirate");
		Enemy enemy3 = enemyFactory.createEnemy("submarine");
		enemy1.addToPane(root);
		enemy2.addToPane(root);
		enemy3.addToPane(root);
		spawnPirateFleet(2,2,2);
		
		// Adding monsters onto the map
		spawnSerpents(3);
		spawnLeviathans(1);
		
		ship.addMultipleObservers(oceanMap.enemyShips);
		loadShipImage();
		loadTreasureImage();
		
		//'reset' button
		Button reset = new Button("reset");
		//button resets map if pressed
		reset.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				        try {
							start(mapStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    }
				});
				reset.setLayoutX(0);
				reset.setLayoutY(0);
				root.getChildren().add(reset);
				
				//'total moves' label
				moves = new Label("Total moves: " + count);
				moves.setLayoutX(100);
				moves.setLayoutY(0);
				root.getChildren().add(moves);
		
		scene = new Scene(root,1000,1000);
		mapStage.setScene(scene);
		mapStage.setTitle("Christopher Columbus Sails the Ocean Blue");
		mapStage.show();
		startSailing();	
	}
	
	public void spawnSerpents(int i) {
		while (i > 0) {
			serpent = new Serpent(scale, oceanMap, false);
			serpent.addToPane(root.getChildren());
			serpentThread = new Thread(serpent);
			serpentThread.start();
			i--;
		}
	}
	
	public void spawnLeviathans(int j) {
		while (j>0) {
			levi = new Leviathan(scale, oceanMap, false);
			levi.addToPane(root.getChildren());
			leviThread =new Thread(levi);
			leviThread.start();
			j--;
		}
	}
	
	//draw ocean and islands
	public void drawMap(){
		for(int x = 0; x < oceanMap.getDimensions(); x++){
			for(int y = 0; y < OceanMap.getGrid().getDimensions(); y++){
				Rectangle rect = new Rectangle(x*scale,y*scale,scale,scale);
				rect.setStroke(Color.BLACK);
				if(islandMap[x][y]) {
					islandImage = new Image("island.jpg",50,50,true,true); 
					ImageView islandImageView = new ImageView(islandImage);
					islandImageView.setX(x*scale);
					islandImageView.setY(y*scale);
					root.getChildren().add(islandImageView);
				} else { 
					rect.setFill(Color.PALETURQUOISE);
					root.getChildren().add(rect);
				}
			}
		}
	}
	

	//spawn each pirate ship type
	public void spawnPirateFleet(int vertical, int horizontal, int chase) {
		
		EnemyFactory pirateFleetFactory = new EnemyFactory(oceanMap);
		SailStrategy v = new VerticalSail();
		SailStrategy h = new HorizontalSail();
		SailStrategy c = new ChaseSail();
		
		for (int i=vertical; i>0; i--) {
			pirateFleetFactory.createPirate(v).addToPane(root);}
		
		for (int i=horizontal; i>0; i--) {
			pirateFleetFactory.createPirate(h).addToPane(root);}
		
		for (int i=chase; i>0; i--) {
			pirateFleetFactory.createPirate(c).addToPane(root);}
	}
	
	private void loadShipImage(){	
		Image shipImage = new Image("ship.png",50,50, true, true);
		shipImageView = new ImageView(shipImage);
		shipImageView.setX(oceanMap.getShipLocation().x*scale);
		shipImageView.setY(oceanMap.getShipLocation().y*scale);
		root.getChildren().add(shipImageView);
	}
	
	private void loadTreasureImage(){
		Image treasureImage = new Image("Treasure.png",50,50,true,true);
		treasureImageView = new ImageView(treasureImage);
		treasureImageView.setX(oceanMap.getTreasureLocation().x*scale);
		treasureImageView.setY(oceanMap.getTreasureLocation().y*scale);
		root.getChildren().add(treasureImageView);
		
		Image winImage = new Image("images.png",50, 50, false, false);
		winImageView = new ImageView(winImage);
		winImageView.setX(oceanMap.getTreasureLocation().x*scale);
		winImageView.setY(oceanMap.getTreasureLocation().y*scale);
	}
	
	//Controls for the Ship
	private void startSailing() {
		 scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
			public void handle(KeyEvent key) {
				if (!win)
				switch(key.getCode()){
					case RIGHT:
						ship.goEast();
						break;
					case LEFT:
						ship.goWest();
						break;
					case UP:
						ship.goNorth();
						break;
					case DOWN:
						ship.goSouth();
						break;
					default:
						break;
			}
			moves.setText("Total moves: "+ ++count);
			shipImageView.setX(ship.getShipLocation().x*scale);
			shipImageView.setY(ship.getShipLocation().y*scale);
			
			if(ship.getShipLocation().equals(treasure.getTreasureLocation())){	
	    		  setFoundTargetImage();
	    		win = true;
	    	}	
			
			}
			
		});
	}
	
	protected void setFoundTargetImage(){
    	if(root.getChildren().contains(shipImageView)){
			root.getChildren().remove(shipImageView);	
			root.getChildren().add(winImageView);		
		}
    }
	
	public static void main (String[] args) {
		launch(args);
	}
}
