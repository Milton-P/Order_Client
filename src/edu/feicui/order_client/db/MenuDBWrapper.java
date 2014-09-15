package edu.feicui.order_client.db;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Sogrey
 * 
 */
public class MenuDBWrapper {
	private SQLiteDatabase mDb;// SQL���ݿ����
	/** ����ģʽ ���� */
	private static MenuDBWrapper sInstance;

	/**
	 * ����ģʽ <br>
	 * һ�������ֻ����һ��ʵ�� <br>
	 * 1����һ��˽�о�̬��Ա <br>
	 * 2����һ��������̬����getInstance�õ����˽�о�̬��Ա <br>
	 * 3����һ��˽�еĹ��췽����������ʵ������ <br>
	 */

	public static MenuDBWrapper getInstance(Context context) {
		if (sInstance == null) {
			synchronized (MenuDBWrapper.class) {
				if (sInstance == null) {
					sInstance = new MenuDBWrapper(context);
				}
			}
		}
		return sInstance;
	}

	private MenuDBWrapper(Context context) {
		DBHelper helpper = new DBHelper(context, DBHelper.DB_NAME, null,
				DBHelper.DB_VERSION);
		mDb = helpper.getWritableDatabase();
	}

	/** �������� */
	public void insertRank(int id,int category, String name, int price, int units,
			String pic, String remark) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_MENU_MENUID, id);
		values.put(DBHelper.COLUMN_MENU_CATEGORY, category);
		values.put(DBHelper.COLUMN_MENU_NAME, name);
		values.put(DBHelper.COLUMN_MENU_PRICE, price);
		values.put(DBHelper.COLUMN_MENU_UNITS, units);
		values.put(DBHelper.COLUMN_MENU_PIC, pic);
		values.put(DBHelper.COLUMN_MENU_REMARK, remark);
		// mDb.insert(table, nullColumnHack, values)/*ԭ��*/
		mDb.insert(DBHelper.TABLE_MENU, "", values);
	}

	/** �������� */
	public void updateRank(/* ���� */String name, int price, int units,
			String pic, int version, String remark) {
	}
	/** ������� */
	public void deleteRank(){
		mDb.delete(DBHelper.TABLE_MENU, null, null);
	}

	/** ��ѯ��������,�����α�Cursor */
	public Cursor rawQueryRank() {
		/* SQL����ѯ */
		String sql = "select * from " + DBHelper.TABLE_MENU + " order by "
				+ DBHelper.COLUMN_MENU_CATEGORY + " ASC";
		return mDb.rawQuery(sql, null);
	}

	/** ��CATEGORY��ѯ����,�����α�Cursor */
	public Cursor rawQueryRank(int category) {
		/* SQL����ѯ */
		String sql = "select * from " + DBHelper.TABLE_MENU + " where "
				+ DBHelper.COLUMN_MENU_CATEGORY + " =" + category  ;
		return mDb.rawQuery(sql, null);
	}
	/** ��NAME��ѯ����,�����α�Cursor */
	public Cursor rawQueryRank(String name) {
		/* SQL����ѯ */
		String sql = "select * from " + DBHelper.TABLE_MENU + " where "
				+ DBHelper.COLUMN_MENU_NAME + " =\"" + name+"\""  ;
		return mDb.rawQuery(sql, null);
	}
	/** ��MENUID��ѯNAME����,�����α�Cursor */
	public Cursor queryRank(int menuId) {
		/* SQL����ѯ */
		String sql = "select * from " + DBHelper.TABLE_MENU + " where "
				+ DBHelper.COLUMN_MENU_MENUID + " =\"" + menuId+"\""  ;
		return mDb.rawQuery(sql, null);
	}
}
