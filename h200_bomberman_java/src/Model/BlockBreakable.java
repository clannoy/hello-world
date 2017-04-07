package Model;

import java.util.ArrayList;

public class BlockBreakable extends Block implements Demisable, ExplodableObserver{

	private ArrayList<DemisableObserver> observers = new ArrayList<DemisableObserver>();
	
	public BlockBreakable(int X, int Y) {
		super(X, Y, 1);
	}

	@Override
	public void exploded(Explodable e) {
		BombObject bomb = (BombObject) e;
		boolean distanceX = Math.abs(this.getPosX() - bomb.getPosX()) <= bomb.getRange();
		boolean distanceY = Math.abs(this.getPosY() - bomb.getPosY()) <= bomb.getRange();

		if(distanceX && distanceY){
			demisableNotifyObserver();	
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void demisableAttach(DemisableObserver po) {
		observers.add(po);		
	}

	@Override
	public void demisableNotifyObserver() {
		for (DemisableObserver o : observers) {
			o.demise(this, null);
		}	
	}

	@Override
	public boolean isObstacle() {
		return true;
	}
	
}
