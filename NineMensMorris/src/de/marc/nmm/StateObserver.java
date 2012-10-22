package de.marc.nmm;

import org.json.JSONObject;

public interface StateObserver {
	
	public void notify(JSONObject o);

}
