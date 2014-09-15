package edu.feicui.order_client.util;

import android.os.Environment;

/**
 * �����б�<br>
 * ȫ���{�ã���yһ����<br>
 * 
 * @author Sogrey
 * */
public final class Constants {
	/** menu��·�� */
	public final static String PATH_menuCARD = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	/**
	 * Log�ļ�����·��<br>
	 * "/menucard/order_client/order_log.txt"
	 */
	public final static String PATH_LOG = PATH_menuCARD
			+ "/order_client/order_log.txt";
	/**
	 * ����ͼƬ�ļ��б���·��<br>
	 * "/menucard/edu.feicui.order_client/imgs"
	 */
	public static final String PATH_CACHE = PATH_menuCARD
			+ "/edu.feicui.order_client/imgs";
	/**�û���*/
	public final static String USER_NAME = "name";
	/**����*/
	public final static String PASSWORD = "password";
	/**
	 * ��ʼ����
	 */
	public final static String INCEPTIVE_PASSWORD = "888888";
	/** ��������ַ */
	public static final String PATH_SERVER = "http://192.168.2.200:8080/GourmetOrderServer";

	/** ��½��ַ */
	public static final String PATH_LOGIN = PATH_SERVER + "/loginServlet";
	/** ���²˵����ţ���ȡ�˵��б������б� */
	public static final String PATH_UPDATE = PATH_SERVER + "/updateServlet";
	/** ��ѯ���ж�����ˮ */
	public static final String PATH_QUERY_ALL_OREDER = PATH_SERVER + "/queryAllOreder";
	/** ��ѯָ������ */
	public static final String PATH_QUERY_OREDER = PATH_SERVER + "/queryOreder";
	/** ��Ӷ��� */
	public static final String PATH_OREDER = PATH_SERVER + "/orderServlet";
	/** �ӵ� */
	public static final String PATH_ADD_ORDER = PATH_SERVER + "/addOrderServlet";
	/** ��������-��̨/��̨ */
	public static final String PATH_CHANGE_TABLE = PATH_SERVER + "/changetableServlet";
	/** �ᵥ */
	public static final String PATH_PAY_MONEY = PATH_SERVER + "/payMoneyServlet";
	
	/** ��ƷͼƬ��ַ */
	public static final String PATH_IMAGES = PATH_SERVER + "/images";
	/** �˳�Ӧ�öԻ���ID */
	public static final int DIALOG_EXIT = 0x400;

	/** ��������λ���� */
	public final static int SIZE_BUFFER = 1024;

	/** ���Ÿ��������� */
	public static final String CHANGE_TABLE_NAME = "ChangeTable";
	/** ���� */
	public static final String CHOOSE_TABLE_ID = "TableId";
	
	/** Ҫ��ѯ�Ķ����� */
	public static final String NUMBER = "number";
	/** �鵥 */
	public static final String CHECK_ORDER = "check_order";
	/** �鵥=>���� */
	public static final int CHECKOUT = 0x10;
	/** �鵥=>�������� */
	public static final int ORDER_PROGRESS  = 0x11;
	/** �鵥=>�ӵ�*/
	public static final int ADD_ORDER = 0x12;
	/** �鵥=>�鵥������ */
	public static final int CHECK_ORDER_INFO = 0x13;
	
	/** ��� */
	public static final String CHOOSEDISH = "ChooseDish";
	/** ���=>��� */
	public static final int CHOOSE_ORDER = 0x20;
	/** ���=>�ӵ� */
	public static final int CHOOSE_ADD_ORDER  = 0x21;

	
	/** ����-��̨ */
	public static final int CHANGE_TABLE = 0x01;
	/** ����-��̨ */
	public static final int MERGE_TABLE = 0x02;
	
	/** �Ȳ� */
	public static final int MENU_HOT_FOOD = 1201;
	/** ���� */
	public static final int MENU_COOL_FOOD = 1202;
	/** ���� */
	public static final int MENU_SOUP_FOOD = 1203;
	/** ���� */
	public static final int MENU_DRINK_FOOD = 1204;
	/** ��ʳ */
	public static final int MENU_STAPLE_FOOD = 1205;
	/** �;� */
	public static final int MENU_TABLEWARE_FOOD = 1206;
	
	/** ��ˮ��-�����ʱ���ʽ */
	public final static String FMT_SERIAL_NUMBER = "yyyyMMddkkmm";
	/** ���ڸ�ʽ */
	public final static String FMT_DATE = "yyyy-MM-dd";
	
	/** �������ݶԻ��� */
	public static final int DIALOG_ID_UPDATA = 2000;
	
}
