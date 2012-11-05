package com.boardgames.nmm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class ObservableState {
	
	private List<StateObserver> _observers;

	public ObservableState() {
		_observers = new ArrayList<StateObserver>();
	}
	
	public void registerObserver(StateObserver so) {
		_observers.add(so);
	}
	
	public void notifyObservers(JSONObject o) {
		for (StateObserver so : _observers) {
			so.notify(o);
		}
	}
	
	public abstract void pick(int x, int y);
	
	public abstract void move(int oldField, int newField, int delField);
	
	public abstract void next();
	
}
