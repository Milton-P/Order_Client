package edu.feicui.order_client.util;

import edu.feicui.order_client.BuildConfig;
import android.util.Log;

/**
 * ��LogCat���з�װ��ֻ�ڵ���ʱ�����־<br>
 * ��������־���ܶ��û�չʾ<br>
 * ����־ֻ�ڵ���ʱ��ӡ�����к����� BuildConfig.DEBUG ��Ϊ false ���������־<br>
 * 
 * @author Sogrey
 * 
 */
public class LogWrapper {
	private static boolean DEBUG = true && BuildConfig.DEBUG;

	/** info */
	public static void i(String tag, String msg) {
		if (DEBUG)
			Log.i(tag, msg);
	}

	/** debug */
	public static void d(String tag, String msg) {
		if (DEBUG)
			Log.d(tag, msg);
	}

	/** error */
	public static void e(String tag, String msg) {
		if (DEBUG)
			Log.e(tag, msg);
	}

	/** warm */
	public static void w(String tag, String msg) {
		if (DEBUG)
			Log.w(tag, msg);
	}

	/** verbose */
	public static void v(String tag, String msg) {
		if (DEBUG)
			Log.v(tag, msg);
	}
}