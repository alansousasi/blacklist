package br.alansousa.blacklist.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.alansousa.blacklist.modelo.LogSMS;

public class LogLigacaoDAO extends SQLiteOpenHelper{
	
//	private Context context;

	public LogLigacaoDAO(Context context) {
		super(context, "br.alansousa.blacklist.database", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("Create Table logLigacao (id integer primary key, numero TEXT, dataHora TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versaoAtual, int versaoNova) {
		db.execSQL("Drop Table logLigacao");
		this.onCreate(db);
	}
	
	public void adicionar(LogSMS e){
		
		ContentValues values = new ContentValues();
		
		values.put("numero",e.getNumero());
		values.put("dataHora",e.getDataHora());

		getWritableDatabase().insert("logLigacao", null, values);
	}
	
	public void delete(LogSMS e){
		getWritableDatabase().delete("logLigacao", "id=?", new String[]{e.getId().toString()});
	}
	
	public List<LogSMS> getLista() {
		
		String[] colunas = {"id","numero","dataHora"};
		List<LogSMS> saida = new ArrayList<LogSMS>();
		
		
		Cursor c = getWritableDatabase().query("logLigacao", colunas, null, null, null, null, null);
		
		while (c.moveToNext()) {
			LogSMS e = new LogSMS();
			e.setId(c.getLong(0));
			e.setNumero(c.getString(1));
			e.setDataHora(c.getString(2));
			saida.add(e);
		}
		c.close();
		return saida;
		
	}

}
