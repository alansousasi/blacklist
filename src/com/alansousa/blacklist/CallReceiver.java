package com.alansousa.blacklist;

import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

public class CallReceiver extends BroadcastReceiver {

	Context context = null;
	private static final String TAG = "Phone call";
	private ITelephony telephonyService;

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.v(TAG, "Receving....");
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Class<?> c = Class.forName(telephony.getClass().getName());
			Method m = c.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			telephonyService = (ITelephony) m.invoke(telephony);
//			telephonyService.silenceRinger();
			telephonyService.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Bundle extras = intent.getExtras();
		// if (extras != null) {
		// String state = extras.getString(TelephonyManager.EXTRA_STATE);
		// Log.w("MY_DEBUG_TAG", state);
		// if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
		// String phoneNumber = extras
		// .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
		//
		// Log.w("MY_DEBUG_TAG", phoneNumber);
		// }
		// }
	}

	public boolean contato(Context context, String numero) {

		boolean existe = false;

		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(Phone.CONTENT_URI, null, null, null, null);

		while (cursor.moveToNext()) {
			String num = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			Log.i("NUMERO AGENDA", num);
			if (num.contains(numero)) {
				existe = true;
			}
		}
		cursor.close();

		return existe;

	}

}
