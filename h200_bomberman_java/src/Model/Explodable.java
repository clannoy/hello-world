package Model;

public interface Explodable {
	void explodableAttach(ExplodableObserver po);
	void explodableNotifyObserver();
}
