package com.alansousa.blacklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_settings){
			Intent i = new Intent(this, ConfiguracaoActivity.class);
			startActivity(i);
		}else if(item.getItemId() == R.id.action_logSMS){
			Intent i = new Intent(this, LogSMSActivity.class);
			startActivity(i);
		}

		return super.onOptionsItemSelected(item);
	}

}
