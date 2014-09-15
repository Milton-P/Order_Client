/**
 * 
 */
package edu.feicui.order_client.more;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import edu.feicui.order_client.R;
import edu.feicui.order_client.base.BaseActivity;
import edu.feicui.order_client.checkout.CheckoutActivity;
import edu.feicui.order_client.choosedish.ChooseDishActivity;
import edu.feicui.order_client.net.Downloader;
import edu.feicui.order_client.net.OnDownloaderListener;
import edu.feicui.order_client.progress.ProgressActivity;
import edu.feicui.order_client.util.CheckData;
import edu.feicui.order_client.util.Constants;

/**
 * ���
 * 
 * @author Sogrey
 * 
 */
public class CheckOrderListActivity extends BaseActivity implements
		OnClickListener, OnDownloaderListener {

	/** ��ǰʹ���˻��� */
	protected String mUser;
	/** �鵥Ŀ�� */
	protected int mCheckType;
	/** ��������ύ������� */
	Downloader downloader;
	/** ���ڱ���ÿ����Ŀ���� */
	ArrayList<ItemData> mData;
	/** ��ˮ���б�������� */
	LstAdapter mAdapter;
	/** ���� */
	protected TextView mTxtDate;
	/** ��̖ */
	protected TextView mTxtTableid;
	/** ��ˮ̖�б� */
	protected ListView mLstSerialNumber;
	/** ���ذ��o */
	protected Button mBtnBack;
	/** �_�����o */
	protected Button mBtnOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		initData();
		initViews();
	}

	/** ��ʼ������ */
	private void initData() {
		mData = new ArrayList<ItemData>();
		Intent intent = getIntent();
		mUser = intent.getStringExtra(Constants.USER_NAME);
		mCheckType = intent.getIntExtra(Constants.CHECK_ORDER, 0);
		downloader = new Downloader();
		downloader.execute(Constants.PATH_QUERY_ALL_OREDER,
				downloader.HTTP_GET, null);
		downloader.setOnDownloaderListener(this);
	}

	private void initViews() {
		mTxtDate = (TextView) findViewById(R.id.txt_checkout_date);
		mTxtTableid = (TextView) findViewById(R.id.txt_checkout_tableid);
		mLstSerialNumber = (ListView) findViewById(R.id.lst_checkout_serial_number);
		mBtnBack = (Button) findViewById(R.id.btn_checkout_back);
		mBtnBack.setOnClickListener(this);
		mBtnOk = (Button) findViewById(R.id.btn_checkout_ok);
		mBtnOk.setOnClickListener(this);
		mAdapter = new LstAdapter();
		mLstSerialNumber.setAdapter(mAdapter);

		/* ���ڸ�ʽ */
		String patternDate = Constants.FMT_DATE;
		/* new ��SimpleDateFormat ����sdf */
		SimpleDateFormat sdfDate = new SimpleDateFormat(patternDate);
		/* ��ʽ����ǰʱ�丳�� dateString */
		String dateString = sdfDate.format(new Date());
		mTxtDate.setText(getString(R.string.txt_checkout_date, dateString));
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.btn_checkout_back:// ���ذ��o
			finish();
			break;
		case R.id.btn_checkout_ok:// �_�����o
			checkOk();
			break;

		default:
			break;
		}
	}

	public void checkOk() {
		Intent intent = new Intent();
		switch (mCheckType) {
		case Constants.CHECKOUT:// �鵥=>����
			Toast.makeText(this, "�鵥=>���� ", Toast.LENGTH_SHORT).show();
			intent.setClass(this, CheckoutActivity.class);
			break;
		case Constants.ORDER_PROGRESS:// �鵥=>��������
			Toast.makeText(this, "�鵥=>�������� ", Toast.LENGTH_SHORT).show();
			intent.setClass(this, ProgressActivity.class);
			break;
		case Constants.ADD_ORDER:// �鵥=>�ӵ�
			Toast.makeText(this, "�鵥=>�ӵ� ", Toast.LENGTH_SHORT).show();
			intent.setClass(this, ChooseDishActivity.class);
			intent.putExtra(Constants.CHOOSEDISH, Constants.CHOOSE_ADD_ORDER);
			break;
		case Constants.CHECK_ORDER_INFO:// �鵥=>�鵥����
			intent.setClass(this, CheckOrderInfoActivity.class);
			break;

		default:
			break;
		}
		for (int i = 0; i < mData.size(); i++) {
			if (mData.get(i).isSeclect) {
				intent.putExtra(Constants.NUMBER, mData.get(i).strSerialNumber);
			}else {
				Toast.makeText(this, getString(R.string.hint_must_select_one), Toast.LENGTH_SHORT).show();
				return;
			}
		}
		intent.putExtra(Constants.USER_NAME, mUser);
		startActivity(intent);
		this.finish();
	}


	@Override
	public void onBeforeDownload() {
	}

	@Override
	public void onProgressChanged(int totleSize, int countSize) {

	}

	@Override
	public void onAfterDownload(File file) {
		JSONObject json = CheckData.checkRT(file);
		ItemData data;
		if (200 == json.optInt("rt", -1)) {
			JSONArray arrays;
			try {
				arrays = json.getJSONArray("list");
				for (int i = 0; i < arrays.length(); i++) {
					JSONArray array = arrays.optJSONArray(i);
					String serialNumber = array.optString(0);
					Log.d("list", serialNumber);
					data = new ItemData();
					data.strSerialNumber = serialNumber;
					data.isSeclect = false;
					mData.add(i, data);
					mAdapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Toast.makeText(this, json.optString("rtmsg"), Toast.LENGTH_LONG).show();
	}

	class LstAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mData.get(position).hashCode();
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			final ItemData data = new ItemData();
			if (convertView == null) {
				holder = new ViewHolder();
				view = getLayoutInflater().inflate(R.layout.item_checkout_list,
						null);
				holder.txtSerialNumber = (TextView) view
						.findViewById(R.id.txt_checkout_item_serial_number);
				holder.rdbSeclect = (RadioButton) view
						.findViewById(R.id.rdb_checkout_item_select);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.txtSerialNumber.setText(mData.get(position).strSerialNumber);
			view.setClickable(true);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (holder.rdbSeclect.isChecked()) {
						mData.get(position).isSeclect = false;
					} else {
						mData.get(position).isSeclect = true;// ѡ�б���
						for (int i = 0; i < mData.size(); i++) {// ���������Ŀ��ѡ��״̬
							if (i != position) {
								mData.get(i).isSeclect = false;
							}
						}
					}
					holder.rdbSeclect.setChecked(mData.get(position).isSeclect);
					String tableNum = holder.txtSerialNumber.getText()
							.toString().substring(12, 15);// �Ӷ�����ˮ�г�ȡ����
					mTxtTableid.setText(getString(
							R.string.txt_checkout_tableid, tableNum));

					String year = holder.txtSerialNumber.getText().toString()
							.substring(0, 4);// �Ӷ�����ˮ�г�ȡ��
					String month = holder.txtSerialNumber.getText().toString()
							.substring(4, 6);// �Ӷ�����ˮ�г�ȡ��
					String day = holder.txtSerialNumber.getText().toString()
							.substring(6, 8);// �Ӷ�����ˮ�г�ȡ��
					mTxtDate.setText(getString(R.string.txt_checkout_date, year
							+ "-" + month + "-" + day));
				}
			});
			notifyDataSetChanged();// ֪ͨ�������仯
			return view;
		}
	}

	class ViewHolder {
		TextView txtSerialNumber;
		RadioButton rdbSeclect;
	}

	class ItemData {
		String strSerialNumber;
		boolean isSeclect;
	}
}
