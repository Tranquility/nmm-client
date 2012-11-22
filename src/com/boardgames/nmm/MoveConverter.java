package com.boardgames.nmm;

import org.json.JSONException;
import org.json.JSONObject;

public class MoveConverter {

	private int _oldField;
	private int _newField;
	private int _delField;
	private int _playerId;
	private JSONObject _json = new JSONObject();

	public MoveConverter(JSONObject json) {
		try {
			_oldField = json.getInt("old_field");
			_newField = json.getInt("new_field");
			_delField = json.getInt("delete_field");
			_playerId = json.getInt("player_id");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public MoveConverter(int oldField, int newField, int delField, int playerId) {
		try {
			_json.put("old_field", oldField);
			_json.put("new_field", newField);
			_json.put("delete_field", delField);
			_json.put("player_id", playerId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int oldField() {
		return _oldField;
	}

	public int newField() {
		return _newField;
	}

	public int delField() {
		return _delField;
	}

	public int playerId() {
		return _playerId;
	}

	public JSONObject json() {
		return _json;
	}

}
