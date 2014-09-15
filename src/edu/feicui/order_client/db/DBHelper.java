/**
 * 
 */
package edu.feicui.order_client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Sogrey
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	public final static String DB_NAME = "order.db";// ���ݿ���
	public final static int DB_VERSION = 1;// ���ݿ�汾
	public final static String TABLE_MENU = "menu";// �˵�����
	public final static String COLUMN_MENU_ID = "_id";// �˵�����ID
	public final static String COLUMN_MENU_MENUID = "menuid";// ��Ʒ����
	public final static String COLUMN_MENU_CATEGORY = "category";// �˵�����-���
	public final static String COLUMN_MENU_NAME = "name";// �˵�����-����
	public final static String COLUMN_MENU_PRICE = "price";// �˵�����-����
	public final static String COLUMN_MENU_UNITS = "units";// �˵�����-��λ
	public final static String COLUMN_MENU_PIC = "pic";// �˵�����-ͼƬ·��
	public final static String COLUMN_MENU_REMARK = "remark";// �˵�����-���
	public final static String TABLE_TABLEID = "tableid";// ���ű���
	public final static String COLUMN_TABLE_ID = "_id";// ��������ID
	public final static String COLUMN_TABLE_TABLEID = "tableid";// ��������-�������
	public final static String COLUMN_TABLE_NUM = "num";// ��������-�����
	public final static String COLUMN_TABLE_NAME = "name";// ��������-����
	public final static String COLUMN_TABLE_NUMBER = "number";// ��������-����
	public final static String COLUMN_TABLE_DESCRIPTION = "description";// ��������-����

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlMenu = "CREATE TABLE " + TABLE_MENU + " ( " // �˵���
				+ COLUMN_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // ����ID
				+ COLUMN_MENU_MENUID + " INTEGER, " // ���
				+ COLUMN_MENU_CATEGORY + " INTEGER, " // ���
				+ COLUMN_MENU_NAME + " TEXT, " // ����
				+ COLUMN_MENU_PRICE + " INTEGER, " // ����
				+ COLUMN_MENU_UNITS + " INTEGER, "// ��λ
				+ COLUMN_MENU_PIC + " TEXT," // ͼƬ·��
				+ COLUMN_MENU_REMARK + " TEXT)";// ���
		db.execSQL(sqlMenu);// ִ��SQL��䣬����������ֵΪ�գ�
		String sqlTable = "CREATE TABLE " + TABLE_TABLEID + " ( "//���ű�
				+ COLUMN_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "// ����
				+ COLUMN_TABLE_TABLEID + " INTEGER, " // �������
				+ COLUMN_TABLE_NUM + " INTEGER, " // �����
				+ COLUMN_MENU_NAME + " TEXT, "// ����
				+ COLUMN_TABLE_NUMBER + " INTEGER, "// ����
				+ COLUMN_TABLE_DESCRIPTION + " TEXT)";// �汾
		db.execSQL(sqlTable);// ִ��SQL��䣬����������ֵΪ�գ�
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
