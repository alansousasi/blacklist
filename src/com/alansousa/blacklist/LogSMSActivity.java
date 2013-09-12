package com.alansousa.blacklist;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import br.alansousa.blacklist.dao.LogSMSDAO;
import br.alansousa.blacklist.modelo.LogSMS;

public class LogSMSActivity extends Activity {
	
	private ListView view;
	
	private List<LogSMS> lista;
	
	private int posicao;
	
	private ItemLogSMS adapter;
	
	LogSMSDAO dao = new LogSMSDAO(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_sms);
		
		view = (ListView) findViewById(R.id.lista);
		
		carregaLista(view);
		
		adapter = new ItemLogSMS(this, lista);

		view.setAdapter(adapter);
		
		view.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int posicao, long arg3) {

					setPosicao(posicao);
				
				return false;
			}
		});
		
		//registrando menu de contexto
		registerForContextMenu(view);
	}
	
	public void setPosicao(int posicao){
		this.posicao = posicao;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_sm, menu);
		return true;
	}
	
	public void carregaLista(ListView view){
		lista = dao.getLista();
		dao.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.setHeaderTitle("Selecione:");
    	final MenuItem exlcuir = menu.add(0,1,0,"EXCLUIR");
    	final MenuItem sms = menu.add(0,2,0,"RESPONDER SMS");
    	final MenuItem call = menu.add(0,3,0,"LIGAR");
    	
    	sms.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {

				Intent i = new Intent(Intent.ACTION_VIEW);
				
				i.setData(Uri.parse("sms:"+lista.get(posicao).getNumero()));
				
				startActivity(i);

				return false;
			}
		});
    	
    	exlcuir.setOnMenuItemClickListener(new OnMenuItemClickListener() {
    		
    		@Override
    		public boolean onMenuItemClick(MenuItem item) {

    			dao.delete(lista.get(posicao));
    			
    			dao.close();

    			Toast.makeText(LogSMSActivity.this, "Log excluído!", Toast.LENGTH_LONG).show();
    			
    			lista.remove(posicao);
    			
    			adapter.notifyDataSetChanged();
    			
    			return false;
    		}
    	});
    	
    	call.setOnMenuItemClickListener(new OnMenuItemClickListener() {
    		
    		@Override
    		public boolean onMenuItemClick(MenuItem item) {
    			
				Intent i = new Intent(Intent.ACTION_VIEW);
				
				i.setData(Uri.parse("tel:"+lista.get(posicao).getNumero()));
				
				startActivity(i);

				return false;
    		}
    	});
    }

}
