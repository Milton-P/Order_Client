package edu.feicui.order_client.login;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.feicui.order_client.R;
import edu.feicui.order_client.base.BaseActivity;
import edu.feicui.order_client.main.MainActivity;
import edu.feicui.order_client.net.Downloader;
import edu.feicui.order_client.net.OnDownloaderListener;
import edu.feicui.order_client.util.CheckData;
import edu.feicui.order_client.util.Constants;

/**
 * ��һ�ε�¼���޸��������
 * 
 * @author Sogrey
 * 
 */
public class ChagePwdActivity extends BaseActivity implements OnClickListener,
		OnDownloaderListener {

	/** ��½�ύ���ݶ��� */
	Map<String, String> map;
	/** ��������ύ������� */
	Downloader downloader;
	/** �û��� - �ӵ�¼���洫���� */
	private String mName;
	/** ���� - �ӵ�¼���洫���� */
	private String mPassword;
	/** ������ */
	private String mNewPassword;
	/** �û��� - TextView */
	protected TextView mUserName;
	/** ������ */
	protected EditText mEdtOldPwd;
	/** ������ */
	protected EditText mEdtNewPwd1;
	/** ȷ�������� */
	protected EditText mEdtNewPwd2;
	/** ȷ�ϰ�ť */
	protected Button mBtnOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		Intent intent = getIntent();
		mName = intent.getStringExtra(Constants.USER_NAME);
		mPassword = intent.getStringExtra(Constants.PASSWORD);
		map = new HashMap<String, String>();
		downloader = new Downloader();
		downloader.setOnDownloaderListener(this);
		initViews();
	}

	private void initViews() {
		mUserName = (TextView) findViewById(R.id.txt_changepwd_username);
		mUserName.setText(mName);
		mEdtOldPwd = (EditText) findViewById(R.id.edt_old_pwd);
		mEdtNewPwd1 = (EditText) findViewById(R.id.edt_new_pwd_1);
		mEdtNewPwd2 = (EditText) findViewById(R.id.edt_new_pwd_2);
		mBtnOK = (Button) findViewById(R.id.btn_changepwd_ok);
		mBtnOK.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.btn_changepwd_ok:// ȷ���޸�����-��ť
			if (isValidity()) {// ȷ��������Ч
				map.put("name", mName);
				map.put("opaw", mPassword);
				map.put("npaw", mNewPassword);
				downloader.execute(Constants.PATH_LOGIN, downloader.HTTP_POST,
						map);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ȷ�������Ƿ���Ч
	 * 
	 * @return true:�¾�������֤��Ч��false���¾�������֤��Ч
	 */
	private boolean isValidity() {
		String dolPwd = mEdtOldPwd.getText().toString();
		String newPwd1 = mEdtNewPwd1.getText().toString();
		String newPwd2 = mEdtNewPwd2.getText().toString();
		if (!dolPwd.equals(mPassword)) {// ��������֤����ȷ
			Toast.makeText(this, getString(R.string.hint_changepwd_oldpwderr),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!newPwd1.equals(newPwd2)) {// ��֤�������Ƿ�һ��
			Toast.makeText(this, getString(R.string.hint_changepwd_newpwderr),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		mNewPassword = newPwd2;
		return true;
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.DIALOG_ID_UPDATA:
			return createLoginDialog();

		default:
			return super.onCreateDialog(id);
		}
	}

	/**
	 * @return ��½�ȴ���ʾ�Ի���
	 */
	private Dialog createLoginDialog() {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setTitle(getString(R.string.title_dialog_login));
		dialog.setMessage(getString(R.string.hint_dialog_chargeped));
		return dialog;
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
		if (200 == json.optInt("rt", -1)) {
			Intent intent = new Intent(ChagePwdActivity.this,
					MainActivity.class);
			intent.putExtra(Constants.USER_NAME, mName);
			startActivity(intent);
			ChagePwdActivity.this.finish();
		} else
			downloader = new Downloader();
	}
}
