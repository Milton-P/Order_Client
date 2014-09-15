package edu.feicui.order_client.util;

import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * ���񲢴�����Щû����try...catch������쳣
 * 
 * @author Sogrey
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {
	
	private UncaughtExceptionHandler mDefaultExceptionHandler;
	/**����ģʽ ����*/
	private static CrashHandler sInstance;

	/**
	 * ����ģʽ <br>
	 * һ�������ֻ����һ��ʵ�� <br>
	 * 1����һ��˽�о�̬��Ա <br>
	 * 2����һ��������̬����getInstance�õ����˽�о�̬��Ա <br>
	 * 3����һ��˽�еĹ��췽����������ʵ������ <br>
	 */

	public static CrashHandler getInstance() {
		if (sInstance == null) {
			synchronized (CrashHandler.class) {
				if (sInstance == null) {
					sInstance = new CrashHandler();
				}
			}
		}
		return sInstance;
	}

	private CrashHandler() {
	}

	/** ��ʼ�� */
	public void init() {
		/*��ȡĬ��δ�����쳣��Handler��default exception handler������ʼ���������*/
		mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		/*Ϊ�߳�����Ĭ��Ĭ��δ�����쳣��Handler�����ӿ�*/
		Thread.setDefaultUncaughtExceptionHandler(this);

	}

	/** �����쳣 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		outputException(thread, ex);
		if (mDefaultExceptionHandler != null) {
			mDefaultExceptionHandler.uncaughtException(thread, ex);
		}
	}

	/** ���쳣������ļ� ,�ַ��� */
	private void outputException(Thread thread, Throwable Throwed) {
		try {
			PrintWriter pWriter = new PrintWriter(Constants.PATH_LOG);
			Throwable throwable = Throwed;
			do {
				throwable.printStackTrace(pWriter);
			} while ((throwable = throwable.getCause()) != null);
			pWriter.flush();
			pWriter.close();
		} catch (Throwable ex) {
		}
	}
}
