package edu.feicui.order_client.main;

import edu.feicui.order_client.util.CrashHandler;
import android.app.Application;

/**
 * Application��������Ӧ��
 * 
 * @author Sogrey
 * 
 */
public class OrderApplication extends Application {

	/** Application ����������ֻ�� onCreate */
	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler.getInstance().init();// ��ʼ��
	}
}
