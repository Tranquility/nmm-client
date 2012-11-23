package com.boardgames.nmm;

import com.boardgames.nmm.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	SharedPreferences pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
		String token = pref.getString("token", null);
		if (token != null) {
			Toast.makeText(this, "You are still logged in!", Toast.LENGTH_SHORT)
					.show();

		} else {
			Toast.makeText(this, "You need to log in or create a new account!",
					Toast.LENGTH_SHORT).show();
		}

		addRegisterButtonListener();
		addStartButtonListener();
		addLoginButtonListener();
		addJoinGameButtonListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Adds a listener to the button that opens the registration.
	 */
	private void addRegisterButtonListener() {
		Button regButton = (Button) findViewById(R.id.button_signup);
		regButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						RegistrationActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * Adds a listener to the button that starts the game.
	 */
	private void addStartButtonListener() {
		Button startButton = (Button) findViewById(R.id.button_startgame);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						BoardActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * Adds a listener to the button that opens login activity.
	 */
	private void addLoginButtonListener() {
		Button loginButton = (Button) findViewById(R.id.button_gotologin);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * Adds a listener to the button that opens games activity.
	 */
	private void addJoinGameButtonListener() {
		Button loginButton = (Button) findViewById(R.id.button_joinGame);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						gamesActivity.class);
				startActivity(intent);
			}
		});
	}
}