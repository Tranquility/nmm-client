package com.boardgames.nmm;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableState {
	
	private List<StateObserver> _observers;

	public ObservableState() {
		_observers = new ArrayList<StateObserver>();
	}
	
	public void registerObserver(StateObserver so) {
		_observers.add(so);
	}
	
	public void notifyObservers(int oldField, int newField, int delField) {
		for (StateObserver so : _observers) {
			so.notify(oldField, newField, delField);
		}
	}
	
	public abstract void pick(int x, int y);
	
	public abstract void move(int oldField, int newField, int delField);
	
	public abstract void next();
	
}
