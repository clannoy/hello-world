package Model;

import java.util.ArrayList;

public abstract class BombObject extends GameObject implements Runnable, Demisable, Explodable, ExplodableObserver {

	protected int duration = 0;
	private int range = 0;
	boolean detonated = false;
	protected ArrayList<DemisableObserver> demisableObservers = new ArrayList<DemisableObserver>();
	private ArrayList<ExplodableObserver> explodableObservers = new ArrayList<ExplodableObserver>();
	
	public BombObject(int x, int y, int duration, int range, int color){
		super(x,y, color);
		this.duration = duration;
		this.range = range;
	}

	public int getRange(){
		return this.range;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void run() {
		int count = 0;
		while(!this.detonated  && count < this.duration/10.0){
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
	public void demisableAttach(DemisableObserver po) {
		demisableObservers.add(po);		
	}

	@Override
	public void demisableNotifyObserver() {
		for (DemisableObserver o : demisableObservers) {
			o.demise(this, null);
		}	
	}

	@Override
	public void explodableAttach(ExplodableObserver eo) {
		explodableObservers.add(eo);
	}

	@Override
	public void explodableNotifyObserver() {
		for (ExplodableObserver o : explodableObservers) {
			o.exploded(this);
		}
	}

	@Override
	public abstract void exploded(Explodable e);
	
	@Override
	public boolean isObstacle() {
		return false;
	}
	
}
