package edu.feicui.order_client.more;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import edu.feicui.order_client.R;
import edu.feicui.order_client.base.BaseActivity;
import edu.feicui.order_client.db.MenuDBWrapper;
import edu.feicui.order_client.db.TableDBWrapper;
import edu.feicui.order_client.net.Downloader;
import edu.feicui.order_client.net.OnDownloaderListener;
import edu.feicui.order_client.util.CheckData;
import edu.feicui.order_client.util.Constants;

/**
 * @author Sogrey
 * 
 */
public class UpdateActivity extends BaseActivity implements
		OnDownloaderListener, OnClickListener {

	/** ���²˵� */
	public static final int UPDATE_MENU = 0x01;
	/** �������� */
	public static final int UPDATE_TABLE = 0x02;

	/** ���ݸ��£��������ָ��²˵��������� */
	protected int mUpdateType;
	/** ��½�ύ���ݶ��� */
	Map<String, String> mMap;
	// /** ��������ύ������� */
	// Downloader mDownloader;
	/** ������԰�ť */
	protected Button mBtnNetTest;
	/** ���²˵���ť */
	protected Button mBtnUpdateMenu;
	/** �������Ű�ť */
	protected Button mBtnUpdateTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		initViews();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mMap = new HashMap<String, String>();
//		mDownloader = new Downloader();
//		mDownloader.setOnDownloaderListener(this);
	}

	private void initViews() {
		mBtnNetTest = (Button) findViewById(R.id.btn_update_net);
		mBtnUpdateMenu = (Button) findViewById(R.id.btn_update_menu);
		mBtnUpdateTable = (Button) findViewById(R.id.btn_update_table);
		mBtnNetTest.setOnClickListener(this);
		mBtnUpdateMenu.setOnClickListener(this);
		mBtnUpdateTable.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.btn_update_net:// �������

			break;
		case R.id.btn_update_menu:// ���²˵�
		{
			mUpdateType = UPDATE_MENU;
			mMap.put("num", "1");
			mMap.put("update", "menu");
			Downloader mDownloader = new Downloader();
			mDownloader.setOnDownloaderListener(this);
			mDownloader.execute(Constants.PATH_UPDATE, mDownloader.HTTP_GET,
					mMap);
		}
			break;
		case R.id.btn_update_table:// ��������
		{
			mUpdateType = UPDATE_TABLE;
			mMap.put("num", "1");
			mMap.put("update", "table");
			Downloader mDownloader = new Downloader();
			mDownloader.setOnDownloaderListener(this);
			mDownloader.execute(Constants.PATH_UPDATE, mDownloader.HTTP_GET,
					mMap);
		}
			break;

		default:
			break;
		}
	}
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.DIALOG_ID_UPDATA:// �������ݶԻ���
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(getString(R.string.hint_dialog_updating));
			return dialog;

		default:
			return super.onCreateDialog(id);
		}
	}
	@Override
	public void onBeforeDownload() {
		showDialog(Constants.DIALOG_ID_UPDATA);// ��ʾ��ȡ���ݶԻ���
	}

	@Override
	public void onProgressChanged(int totleSize, int countSize) {

	}

	@Override
	public void onAfterDownload(File file) {
		dismissDialog(Constants.DIALOG_ID_UPDATA);// ���ض�ȡ���ݶԻ���
		JSONObject json = CheckData.checkRT(file);
		if (200 == json.optInt("rt", -1)) {
			updateDB(json);
			Toast.makeText(this, getString(R.string.hint_update_ok),
					Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(this, getString(R.string.hint_update_faile),
					Toast.LENGTH_LONG).show();
	}

	/**
	 * ��������
	 * 
	 * @param json
	 */
	private void updateDB(JSONObject json) {
		switch (mUpdateType) {
		case UPDATE_MENU:// ���²˵�

			try {
				JSONArray arrays = json.getJSONArray("list");
				int id;
				int category;
				String name;
				int price;
				int units;
				String pic;
				String remark;
				MenuDBWrapper dbWrapper = MenuDBWrapper
						.getInstance(getApplication());
				dbWrapper.deleteRank();// ���ԭ����
				for (int i = 0; i < arrays.length(); i++) {
					JSONArray array = arrays.getJSONArray(i);
					id = array.getInt(0);
					category = array.getInt(1);
					name = array.getString(2);
					pic = array.getString(3);
					remark = array.getString(5);
					price = array.getInt(4);
					units = array.getInt(6);
					dbWrapper.insertRank(id, category, name, price, units, pic,
							remark);// ����������
				}
				// finish();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case UPDATE_TABLE:// ��������
			try {
				JSONArray arrays = json.getJSONArray("list");
				int tableid;
				int num;
				String name;
				int number;
				String description;
				TableDBWrapper dbWrapper = TableDBWrapper
						.getInstance(getApplication());
				dbWrapper.deleteRank();// ���ԭ����
				for (int i = 0; i < arrays.length(); i++) {
					JSONArray array = arrays.getJSONArray(i);
					tableid = i + 1;
					num = array.getInt(0);
					name = array.getString(1);
					number = array.getInt(2);
					description = array.getString(3);
					dbWrapper.insertRank(tableid, num, name, number,
							description);// ����������
				}
				// finish();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
}
