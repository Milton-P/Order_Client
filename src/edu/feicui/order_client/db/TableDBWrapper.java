package edu.feicui.order_client.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Sogrey
 *
 */
public class TableDBWrapper {
	private SQLiteDatabase mDb;// SQL���ݿ����
	/**����ģʽ ����*/
	private static TableDBWrapper sInstance;

	/**
	 * ����ģʽ <br>
	 * һ�������ֻ����һ��ʵ�� <br>
	 * 1����һ��˽�о�̬��Ա <br>
	 * 2����һ��������̬����getInstance�õ����˽�о�̬��Ա <br>
	 * 3����һ��˽�еĹ��췽����������ʵ������ <br>
	 */

	public static TableDBWrapper getInstance(Context context) {
		if (sInstance == null) {
			synchronized (TableDBWrapper.class) {
				if (sInstance == null) {
					sInstance = new TableDBWrapper(context);
				}
			}
		}
		return sInstance;
	}

	private TableDBWrapper(Context context) {
		DBHelper helpper = new DBHelper(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
		mDb = helpper.getWritableDatabase();
	}
	
	/** �������� */
	public void insertRank(int tableid,int num, String name, int number, 
			String description) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TABLE_TABLEID, tableid);
		values.put(DBHelper.COLUMN_TABLE_NUM, num);
		values.put(DBHelper.COLUMN_TABLE_NAME, name);
		values.put(DBHelper.COLUMN_TABLE_NUMBER, number);
		values.put(DBHelper.COLUMN_TABLE_DESCRIPTION, description);
		// mDb.insert(table, nullColumnHack, values)/*ԭ��*/
		mDb.insert(DBHelper.TABLE_TABLEID, "", values);
	}
	
	/** �������� */
	public void updateRank(/* ���� */int tableid,int num, String name, int number, 
			String description) {
		
	}
	/** ������� */
	public void deleteRank(){
		mDb.delete(DBHelper.TABLE_TABLEID, null, null);
	}
	/** ��mode,level��ѯ����,�����α�Cursor */
	public Cursor rawQueryRank() {
		/* SQL����ѯ */
		String sql = "select * from " + DBHelper.TABLE_TABLEID 
				+ " order by " + DBHelper.COLUMN_TABLE_NUM + " ASC";
		return mDb.rawQuery(sql, null);
	}
}
