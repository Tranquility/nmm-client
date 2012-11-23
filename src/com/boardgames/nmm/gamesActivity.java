package com.boardgames.nmm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class gamesActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games);
		createTables();
	}

	private void createTables() {
		// Get the TableLayout
		TableLayout tl = (TableLayout) findViewById(R.id.gametable);

		int numberOfGames = 3;
		// Go through each item in the array
		for (int current = 0; current < numberOfGames; current++) {
			TableRow tr = new TableRow(this);
			TextView gameLabel = new TextView(this);
			gameLabel.setText("Name");
			tr.addView(gameLabel);

			Button btn = new Button(this);
			btn.setText("Join");
			int gameId = current;
			btn.setId(gameId);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println(v.getId());
				}
			});
			tr.addView(btn);

			// Add the TableRow to the TableLayout
			tl.addView(tr);
		}
	}
}
