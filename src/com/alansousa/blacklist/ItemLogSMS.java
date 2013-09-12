package com.alansousa.blacklist;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.alansousa.blacklist.modelo.LogSMS;

public class ItemLogSMS extends BaseAdapter {
	
	private List<LogSMS> lista;
	
	private Activity activity;
	
	public ItemLogSMS(Activity activity, List<LogSMS> lista) {
		this.lista = lista;
		this.activity = activity;
	}
	

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int posicao) {
		return lista.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		LogSMS log = lista.get(posicao);
		return log.hashCode();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {
		View view;
		if(convertView == null){
			LayoutInflater inflater = activity.getLayoutInflater();
			view = (View) inflater.inflate(R.layout.layout_log_sms, null);
		}else{
			view = convertView;
		}
		LogSMS log = lista.get(posicao);
		TextView data = (TextView) view.findViewById(R.id.item_data);
		TextView numero = (TextView) view.findViewById(R.id.item_numero);
		TextView message = (TextView) view.findViewById(R.id.item_message);
		data.setText(log.getDataHora());
		numero.setText(log.getNumero());
		message.setText(log.getMensagem());
		return view;
	}
	
	

}
