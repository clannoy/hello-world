package Model;

import java.util.ArrayList;

public class Explosion extends GameObject implements Runnable, Demisable {

	private int duration;
	
	private ArrayList<DemisableObserver> observers = new ArrayList<DemisableObserver>();
	
	public Explosion(int X, int Y, int duration) {
		super(X, Y, 3);
		this.duration = duration;
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
		return false;
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(this.duration);
				this.demisableNotifyObserver();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
