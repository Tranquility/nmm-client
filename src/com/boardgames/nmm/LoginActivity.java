package com.boardgames.nmm;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	SharedPreferences pref;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		pref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
		String token = pref.getString("token", null);
		if (token != null) {
			System.out.println(token);
		} else {
			System.out.println("not logged in");
		}

		addLoginButtonListener();
	}

	/**
	 * Adds a listener to the button that sends the login data
	 */
	private void addLoginButtonListener() {
		Button btn = (Button) findViewById(R.id.button_login);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText emailEdit = (EditText) findViewById(R.id.email);
				EditText passswordEdit = (EditText) findViewById(R.id.password);
				JSONObject playerjson = new JSONObject();
				try {
					playerjson.put("password", passswordEdit.getText());
					playerjson.put("email", emailEdit.getText());
					postData(playerjson);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Executes a post request in background and evaluates the response from the
	 * server
	 * 
	 * @param JSONObject
	 */
	private void postData(final JSONObject o) {

		new AsyncTask<Void, Void, JSONObject>() {

			@Override
			protected JSONObject doInBackground(Void... params) {
				String url = "http://10.0.2.2:3000/tokens.json";
				return NetworkManager.postJson(url, o);
			}

			protected void onPostExecute(JSONObject result) {
				if (result.optJSONObject("errors") != null) {
					Toast.makeText(
							LoginActivity.this,
							"error:"
									+ result.optJSONObject("errors").toString(),
							Toast.LENGTH_SHORT).show();
				} else {
					try {
						pref.edit().putString("token",result.getString("token").toString()).commit();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.execute();
	}

}
