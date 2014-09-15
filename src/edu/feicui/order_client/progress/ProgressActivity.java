package edu.feicui.order_client.progress;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import edu.feicui.order_client.R;
import edu.feicui.order_client.base.BaseActivity;
import edu.feicui.order_client.db.DBHelper;
import edu.feicui.order_client.db.MenuDBWrapper;
import edu.feicui.order_client.net.AsyncImageLoader;
import edu.feicui.order_client.net.AsyncImageLoader.ImageCallBack;
import edu.feicui.order_client.net.Downloader;
import edu.feicui.order_client.net.OnDownloaderListener;
import edu.feicui.order_client.util.CheckData;
import edu.feicui.order_client.util.Constants;

public class ProgressActivity extends BaseActivity implements OnClickListener,
		OnDownloaderListener {

	/** ��ǰʹ���˻��� */
	protected String mUser;
	/** ��ǰ���� */
	protected String mNumber;
	/** ��½�ύ���ݶ��� */
	Map<String, String> mMap;
	/** ��������ύ������� */
	Downloader mDownloader;
	/**��������-��������ƷͼUrl*/
	ArrayList<ArrayList<String>> mLists;
	/** ��ˮ�� */
	protected TextView mTxtSerialNumber;
	/** �ϲ˽����б� */
	protected ListView mLstProgress;
	/** ����/ȷ����ť */
	protected Button mBtnBack;

	LstAdapter mLstAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
		initData();
		initViews();
	}

	private void initData() {
		mLists=new ArrayList<ArrayList<String>>();
		Intent intent = getIntent();
		mUser = intent.getStringExtra(Constants.USER_NAME);
		mNumber = intent.getStringExtra(Constants.NUMBER);

		mMap = new HashMap<String, String>();
		mDownloader = new Downloader();
		mMap.put("orders", mNumber);
		mDownloader.setOnDownloaderListener(this);
		mDownloader.execute(Constants.PATH_QUERY_OREDER, Downloader.HTTP_GET,
				mMap);
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
		Toast.makeText(this, json.optString("rtmsg"), Toast.LENGTH_LONG).show();
		ArrayList<String> list = null;
		if (200 == json.optInt("rt", -1)) {
			JSONArray arrays = json.optJSONArray("list");
			MenuDBWrapper dbWrapper = MenuDBWrapper
					.getInstance(getApplication());
			Cursor cursor = null;
			for (int i = 0; i < arrays.length(); i++) {
				try {
					list = new ArrayList<String>();
					JSONArray array = arrays.getJSONArray(i);
					cursor = dbWrapper.queryRank(array.getInt(0));
					while (cursor.moveToNext()) {
						int nameIndex = cursor
								.getColumnIndex(DBHelper.COLUMN_MENU_NAME);
						String name = cursor.getString(nameIndex);
						list.add(name);// ���� - 0
						int picUrlIndex = cursor
								.getColumnIndex(DBHelper.COLUMN_MENU_PIC);
						String picUrl = cursor.getString(picUrlIndex);
						list.add(picUrl);// ��Ʒͼ - 1
						Log.d("����", name+"-"+picUrl);
					}
					mLists.add(list);
					mLstAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {

				}
			}
		}
	}

	private void initViews() {
		mTxtSerialNumber = (TextView) findViewById(R.id.txt_progress_serial_number);
		mLstProgress = (ListView) findViewById(R.id.lst_progress);
		mBtnBack = (Button) findViewById(R.id.btn_progress_back);
		mBtnBack.setOnClickListener(this);
		mLstAdapter = new LstAdapter();
		mLstProgress.setAdapter(mLstAdapter);
		
		mTxtSerialNumber.setText(getString(R.string.txt_progress_serial_number, mNumber));
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.btn_progress_back:// ����
			finish();
			break;

		default:
			break;
		}
	}

	class LstAdapter extends BaseAdapter {
		AsyncImageLoader loader = new AsyncImageLoader();
		@Override
		public int getCount() {
			return mLists.size();
		}

		@Override
		public Object getItem(int position) {
			return mLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mLists.get(position).hashCode();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				view = getLayoutInflater()
						.inflate(R.layout.item_progress, null);
				holder.txtName = (TextView) view
						.findViewById(R.id.txt_item_progress_name);
				holder.imgPic = (ImageView) view
						.findViewById(R.id.img_item_progress_pic);
				holder.pgbProgress = (ProgressBar) view
						.findViewById(R.id.pgb_item_progress);
				holder.txtProgress = (TextView) view
						.findViewById(R.id.txt_item_progress_info);
				view.setTag(holder);
			} else
				holder = (ViewHolder) view.getTag();
			holder.txtName.setText(mLists.get(position).get(0));//����
			holder.imgPic.setTag(Constants.PATH_SERVER+"/"+mLists.get(position).get(1));
			loader.LoadImage(Constants.PATH_SERVER+"/"+mLists.get(position).get(1), new ImageCallBack() {
				
				@Override
				public void onImageLoaded(String url, Bitmap bmp) {
					if (url.equals(holder.imgPic.getTag())) {
						holder.imgPic.setImageBitmap(bmp);
					}
				}
			});
			//TODO ��ӽ���
//			holder.pgbProgress
			holder.txtProgress.setText("0%");
			return view;
		}

	}

	class ViewHolder {
		TextView txtName, txtProgress;
		ImageView imgPic;
		ProgressBar pgbProgress;
	}
}
