package com.boardgames.nmm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_registration);
	    
	    OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEdit   = (EditText)findViewById(R.id.register_name);
            	Toast.makeText(getApplicationContext(), usernameEdit.getText(), Toast.LENGTH_SHORT).show();
            }
        };
 
        Button btn = (Button) findViewById(R.id.button_createAcc);
        btn.setOnClickListener(listener);	
	
	}

}