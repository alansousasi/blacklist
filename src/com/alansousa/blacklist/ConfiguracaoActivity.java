package com.alansousa.blacklist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class ConfiguracaoActivity extends Activity {
	
	private CheckBox checkForaAgenda;
	
	private CheckBox checkBlackList;
	
	private CheckBox checkSMSForaAgenda;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);
		checkForaAgenda = (CheckBox) findViewById(R.id.checkLigacaoForaAgenda);
		checkBlackList = (CheckBox) findViewById(R.id.checkBlackList);
		checkSMSForaAgenda = (CheckBox) findViewById(R.id.checkSMSForaAgenda);
		
//		SharedPreferences cfg = getSharedPreferences("configuracao", MainActivity.MODE_PRIVATE);
		SharedPreferences cfg = PreferenceManager.getDefaultSharedPreferences(ConfiguracaoActivity.this);
		
		checkForaAgenda.setChecked(cfg.getBoolean("checkForaAgenda", false));
		checkBlackList.setChecked(cfg.getBoolean("checkBlackList", false));
		checkSMSForaAgenda.setChecked(cfg.getBoolean("checkSMSForaAgenda", false));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracao, menu);
		return true;
	}
	
	public void salvar(View v){
		
		//SharedPreferences cfg = getSharedPreferences("configuracao",MainActivity.MODE_PRIVATE);
		SharedPreferences cfg = PreferenceManager.getDefaultSharedPreferences(ConfiguracaoActivity.this);
		SharedPreferences.Editor editor = cfg.edit();
		editor.putBoolean("checkBlackList", checkBlackList.isChecked());
		editor.putBoolean("checkForaAgenda", checkForaAgenda.isChecked());
		editor.putBoolean("checkSMSForaAgenda", checkSMSForaAgenda.isChecked());
		editor.commit();
		finish();
		
		Toast.makeText(ConfiguracaoActivity.this, "Configurações gravadas!", Toast.LENGTH_LONG).show();
	}
	
	public SharedPreferences getSharedPreferences(){
		return getSharedPreferences("configuracao", MainActivity.MODE_PRIVATE);
	}

}
