package br.alansousa.blacklist.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.alansousa.blacklist.modelo.LogSMS;

public class LogSMSDAO extends SQLiteOpenHelper{
	
//	private Context context;

	public LogSMSDAO(Context context) {
		super(context, "br.alansousa.blacklist.database", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("Create Table logSMS (id integer primary key, numero TEXT, dataHora TEXT, mensagem TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versaoAtual, int versaoNova) {
		db.execSQL("Drop Table logSMS");
		this.onCreate(db);
	}
	
	public void adicionar(LogSMS e){
		
		ContentValues values = new ContentValues();
		
		values.put("numero",e.getNumero());
		values.put("dataHora",e.getDataHora());
		values.put("mensagem",e.getMensagem());

		getWritableDatabase().insert("logSMS", null, values);
	}
	
	public void delete(LogSMS e){
		getWritableDatabase().delete("logSMS", "id=?", new String[]{e.getId().toString()});
	}
	
	public List<LogSMS> getLista() {
		
		String[] colunas = {"id","numero","dataHora","mensagem"};
		List<LogSMS> saida = new ArrayList<LogSMS>();
		
		
		Cursor c = getWritableDatabase().query("logSMS", colunas, null, null, null, null, null);
		
		while (c.moveToNext()) {
			LogSMS e = new LogSMS();
			e.setId(c.getLong(0));
			e.setNumero(c.getString(1));
			e.setDataHora(c.getString(2));
			e.setMensagem(c.getString(3));
			saida.add(e);
		}
		c.close();
		return saida;
		
	}

}
