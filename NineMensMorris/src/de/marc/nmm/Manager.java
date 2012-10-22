package de.marc.nmm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class Manager implements StateObserver {
	private BoardActivity _board;
	
	public Manager() {
		_board = new BoardActivity();
		_board.registerStateObserver(this);
	}
	
	/**
	 * This methods initiates a POST request to forward the users latest move to
	 * the server.
	 */
	private void postLatestMove() {
		final JSONObject object = new JSONObject();
		try {
			object.put("player_id", 12333);
			object.put("old_field", "a7");
			object.put("new_field", "d7");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				String url = "http://nmm.ole-reifschneider.de/moves.json";
				return PostRequest.postJson(url, object);
			}
		}.execute();
	}

	/**
	 * This methods initiates a GET request to fetch the opponents latest move
	 * from the server.
	 */
	private void getLatestMove() {
		new AsyncTask<Void, Void, JSONArray>() {

			@Override
			protected JSONArray doInBackground(Void... params) {
				String url = "http://nmm.ole-reifschneider.de/moves.json";
				return GetRequest.getJson(url);
			}

			protected void onPostExecute(JSONArray result) {
				System.out.println(result.toString());
			}
		}.execute();
	}

	public void notify(JSONObject o) {
		// TODO Auto-generated method stub
		
	}

}
