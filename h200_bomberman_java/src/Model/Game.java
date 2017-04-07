package Model;
import View.Window;

import java.util.ArrayList;
import java.util.Random;

public class Game implements DemisableObserver{
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	private Window window;
	private int size = 20;
	private int bombTimer = 3000;
	private int numberOfBreakableBlocks = 40;
	
	public Game(Window window){
		this.window = window;

		// Creating one Player at position (1,1)
		objects.add(new Player(10,10,3,5));
		
		// Map building 
		for(int i = 0; i < size; i++){
			objects.add(new BlockUnbreakable(i,0));
			objects.add(new BlockUnbreakable(0,i));
			objects.add(new BlockUnbreakable(i, size-1));
			objects.add(new BlockUnbreakable(size-1, i));
		}
		Random rand = new Random();
		for(int i = 0; i < numberOfBreakableBlocks; i++){
			int x = rand.nextInt(16) + 2;
			int y = rand.nextInt(16) +2;
			BlockBreakable block = new BlockBreakable(x,y);
			block.demisableAttach(this);
			objects.add(block);
		}
		
		window.setGameObjects(this.getGameObjects());
		notifyView();
	}
	
	public void dropBomb(String bombType, int playerNumber){
		Player player = ((Player) objects.get(playerNumber));
		
		BombObject bombDropped = player.dropBomb(bombType);
		if(bombDropped != null){
			bombDropped.demisableAttach(this);
			for(GameObject object : objects){
				if(object instanceof Explodable){
					((BombObject) object).explodableAttach(((ExplodableObserver) bombDropped));
				}
				if(object instanceof ExplodableObserver){
					bombDropped.explodableAttach(((ExplodableObserver) object));
				}
			}
			objects.add(bombDropped);
			notifyView();
		}
	}
	
	public void movePlayer(int x, int y, int playerNumber){
		Player player = ((Player) objects.get(playerNumber));
		int nextX = player.getPosX()+x;
		int nextY = player.getPosY()+y;
		
		boolean obstacle = false;
		for(GameObject object : objects){
			if(object.isAtPosition(nextX, nextY)){
				obstacle = object.isObstacle();
			}
			if(obstacle == true){
				break;
			}
		}
		
		if(obstacle == false){
			player.move(x,y);
			notifyView();
		}
	}
	
	private void notifyView(){
		window.update();
	}

	public ArrayList<GameObject> getGameObjects(){
		return this.objects;
	}
	
	@Override
	synchronized public void demise(Demisable ps, ArrayList<GameObject> loot) {
		objects.remove(ps);
		if(loot != null){
			objects.addAll(loot);
		}
		notifyView();
	}	
}
