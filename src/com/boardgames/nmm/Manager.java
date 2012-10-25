package com.boardgames.nmm;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class Manager {

	private BoardActivity _board;

	public Manager() {
		_board = new BoardActivity();
		registerObservers();
	}

	/**
	 * Registers one observer that sends a GET request after being notified and
	 * another one that sends a POST request after being notified.
	 */
	private void registerObservers() {
		_board.registerGetObserver(new StateObserver() {

			public void notify(JSONObject o) {
				getLatestMove();
			}
		});

		_board.registerPostObserver(new StateObserver() {

			public void notify(JSONObject o) {
				postLatestMove(o);
				_board.waitState();
			}
		});
	}

	/**
	 * This methods initiates a POST request to forward the users latest move to
	 * the server.
	 */
	private void postLatestMove(final JSONObject o) {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				String url = "http://nmm.ole-reifschneider.de/moves.json";
				return NetworkManager.postJson(url, o);
			}
		}.execute();
	}

	public void getLatestMove() {
		Timer timer;
		timer = new Timer();
		timer.schedule(new PullDaemon(), 0, 5 * 1000);
	}

	/**
	 * This methods initiates a GET request to fetch the opponents latest move
	 * from the server.
	 */
	class PullDaemon extends TimerTask {
		
		public void run() {
			
			new AsyncTask<Void, Void, JSONArray>() {

				@Override
				protected JSONArray doInBackground(Void... params) {
					String url = "http://nmm.ole-reifschneider.de/moves.json";
					return NetworkManager.getJson(url);
				}

				protected void onPostExecute(JSONArray result) {
					try {
						_board.move(result.getJSONObject(0));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}.execute();
		}
	}
}
