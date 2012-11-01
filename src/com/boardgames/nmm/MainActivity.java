package com.boardgames.nmm;

import com.boardgames.nmm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addRegisterButtonListener();
		addStartButtonListener();
		addLoginButtonListener();
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
		Button loginButton = (Button) findViewById(R.id.button_login);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
			}
		});
	}
}