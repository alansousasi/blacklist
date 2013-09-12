package com.alansousa.blacklist;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsMessage;
import android.util.Log;
import br.alansousa.blacklist.dao.LogSMSDAO;
import br.alansousa.blacklist.modelo.LogSMS;

public class SMSReceiver extends BroadcastReceiver {

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//SharedPreferences cfg = new ConfiguracaoActivity().getSharedPreferences();
		
		SharedPreferences cfg = PreferenceManager.getDefaultSharedPreferences(context);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
//		boolean checkBlackList = cfg.getBoolean("checkBlackList", false);
		boolean checkSMSForaAgenda = cfg.getBoolean("checkSMSForaAgenda", false);
		
		Object[] array = (Object[]) intent.getSerializableExtra("pdus");
		SmsMessage[] messages = new SmsMessage[array.length];
		for (int i = 0; i < messages.length; i++) {
			
			messages[i] = SmsMessage.createFromPdu((byte[]) array[i]);
			
			String telefone = messages[i].getDisplayOriginatingAddress();
			
			if(checkSMSForaAgenda){
				if(!contato(context, telefone)){
					LogSMSDAO dao = new LogSMSDAO(context);
					LogSMS l = new LogSMS();
					l.setNumero(telefone);
					l.setMensagem(messages[i].getMessageBody());
					l.setDataHora(sdf.format(new Date()));
					dao.adicionar(l);
					dao.close();
					abortBroadcast();
					
					boolean usarNotificacao = cfg.getBoolean("notificacao", true);
				         
				    if (usarNotificacao) {
				    	
				    	vibrar(context);
				    } 				
				}
			}
			
//			if(messages[i].getMessageBody().contains("amor") && !this.contato(context,telefone)){
//				abortBroadcast();
//			}
		}

	}
	
	@SuppressWarnings("deprecation")
	public void notificar(Context context){
 	   String ns = Context.NOTIFICATION_SERVICE;
 	   NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
 	 
 	   int icon = R.drawable.ic_launcher;
 	   CharSequence tickerText = "";
 	   long when = System.currentTimeMillis();

 	   	Notification notification = new Notification(icon, tickerText, when);
 	   
 	   //notificacao.vibrate = new long[] { 100, 250, 100, 500 };
 	   
 	   CharSequence contentTitle = "";
 	   CharSequence contentText = "";
 	   Intent notificationIntent = new Intent(context,LogSMSActivity.class);
 	   PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

 	   notification.flags = Notification.FLAG_AUTO_CANCEL;
 	   notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
 	   mNotificationManager.notify(0, notification);
	}
	
	public void vibrar(Context context)
	{
		NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); 
		Notification n = new Notification();

		// Now we set the vibrate member variable of our Notification
		// After a 100ms delay, vibrate for 200ms then pause for another
		//100ms and then vibrate for 500ms
		n.vibrate = new long[]{100, 200, 100, 500};
		n.defaults |= Notification.DEFAULT_LIGHTS;
		nManager.notify(0, n);
	}
	
	public boolean contato(Context context,String numero){
		
		boolean existe = false;
		
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(Phone.CONTENT_URI, null, null, null, null);
		
		while (cursor.moveToNext()) {
			String num = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			Log.i("NUMERO AGENDA", num);
			if(num.contains(numero)){
				existe = true;
			}
		}
		cursor.close();
		
		return existe;
		
	}

}
