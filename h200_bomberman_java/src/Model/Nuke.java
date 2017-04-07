package Model;

import java.util.ArrayList;


public class Nuke extends BombObject implements Demisable{

	public Nuke(int x, int y, int duration, int range) {
		super(x, y, duration, range, 5);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void run() {
		int count = 0;
		while(!this.detonated  && count < super.duration/10.0){
			try {
				Thread.sleep(10);
				count = count + 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.demisableNotifyObserver();
		this.explodableNotifyObserver();
	}

	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void exploded(Explodable e) {
		BombObject bomb = (BombObject) e;
		boolean distanceX = Math.abs(this.getPosX() - bomb.getPosX()) <= bomb.getRange();
		boolean distanceY = Math.abs(this.getPosY() - bomb.getPosY()) <= bomb.getRange();
		if(distanceX && distanceY){
			this.detonated = true;
			this.demisableNotifyObserver();		
		}
	}
	
	@Override
	public void demisableNotifyObserver() {
		ArrayList<GameObject> loot = new ArrayList<GameObject>();
		int range = this.getRange();
		int x = this.getPosX();
		int y = this.getPosY();
		for(int i = x-range; i <= x+range; i++){
			for(int j = y-range; j <= y+range; j++){
				Explosion explosion = new Explosion(i,j,250);
				Thread thread = new Thread(explosion);
				thread.start();
				for(DemisableObserver observer : demisableObservers){
					explosion.demisableAttach(observer);
				}
				loot.add(explosion);
			}
		}
		
		System.out.println(loot.size());
		
		
		for (DemisableObserver o : this.demisableObservers) {
			o.demise(this, loot);
		}	
	}
}
