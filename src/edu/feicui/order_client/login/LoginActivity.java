package edu.feicui.order_client.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.feicui.order_client.R;
import edu.feicui.order_client.base.BaseActivity;
import edu.feicui.order_client.main.MainActivity;
import edu.feicui.order_client.net.Downloader;
import edu.feicui.order_client.net.OnDownloaderListener;
import edu.feicui.order_client.util.Constants;

/**
 * ��¼����
 * 
 * @author Sogrey
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener,
		OnDownloaderListener {

	/** �޸����� */
	public static final int CHANGE_PWD = 0x01;
	/** ��ת������ */
	public static final int MAIN = 0x02;
	/** ��ת�Լ� */
	public static final int SELF = 0x03;
	/** ��½�ȴ���ʾ�Ի���ID */
	public static final int DIALOG_ID_LOGIN = 0x10;
	/** ��ǰ��¼�û��� */
	String mName;
	/** ��ǰ��¼�û����� */
	String mPassword;
	/** �û�������� */
	protected EditText mEdtUserName;
	/** ��������� */
	protected EditText mEdtPassword;
	/** ��¼��ť */
	protected Button mBtnLogin;
	/** ��½�ύ���ݶ��� */
	Map<String, String> map;
	/**��������ύ�������*/
	Downloader downloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
	}

	@Override
	protected void onStart() {
		super.onStart();
		map = new HashMap<String, String>();
		downloader = new Downloader();
		downloader.setOnDownloaderListener(this);

	}

	private void initViews() {
		mEdtUserName = (EditText) findViewById(R.id.edt_user);
		mEdtPassword = (EditText) findViewById(R.id.edt_password);
		mEdtUserName.setFocusable(true);
		mEdtUserName.setFocusableInTouchMode(true);
		mEdtUserName.requestFocus();
		mEdtUserName.requestFocusFromTouch();
		mBtnLogin = (Button) findViewById(R.id.btn_login);
		mBtnLogin.setOnClickListener(this);
		// mEdtUserName.
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.btn_login:// ��½
			mName = mEdtUserName.getText().toString();
			 mPassword = mEdtPassword.getText().toString();
			if (TextUtils.isEmpty(mName)) {
				Toast.makeText(this, getString(R.string.tst_no_name),
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (TextUtils.isEmpty(mPassword)) {
				Toast.makeText(this, getString(R.string.tst_no_pwd),
						Toast.LENGTH_SHORT).show();
				break;
			}
			map.put("name", mName);
			map.put("paw", mPassword);
			map.put("category", "user");
			downloader.execute(Constants.PATH_LOGIN, downloader.HTTP_GET, map);
			break;

		default:
			break;
		}
	}

	/**
	 * ȷ�������Ƿ���Ч
	 * 
	 * @return true:������֤��Ч��false��������֤��Ч
	 */
	private boolean isValidity() {
		// TODO ��֤����
		return true;
	}

	/**
	 * ��ת���޸��������
	 * 
	 * @param index
	 *            :CHANGE_PWD (0x1):�޸�������棻MAIN (0x2):������
	 */
	private void gotoOtherActivity(int index) {
		Intent intent = new Intent();
		switch (index) {
		case CHANGE_PWD:// �޸�����
			intent.setClass(this, ChagePwdActivity.class);
			intent.putExtra(Constants.PASSWORD, mPassword);
			break;
		case MAIN:// ������
			intent.setClass(this, MainActivity.class);
			break;
		case SELF:// ������
			intent.setClass(this, LoginActivity.class);
			break;

		default:
			break;
		}
		intent.putExtra(Constants.USER_NAME, mName);
		startActivity(intent);
//		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:// �˳�Ӧ��
			showDialog(Constants.DIALOG_EXIT);
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.DIALOG_EXIT:// �˳�Ӧ��
			return createExitDialog();
		case DIALOG_ID_LOGIN:// ��½�ȴ���ʾ�Ի���
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
		dialog.setMessage(getString(R.string.hint_dialog_login));
		return dialog;
	}

	/**
	 * @return �˳�Ӧ����ʾ�Ի���
	 */
	private Dialog createExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.hint_exit_dialog));
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(getString(R.string.txt_exit_ack));
		builder.setPositiveButton(getString(R.string.ok),
				new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		builder.setNegativeButton(getString(R.string.back), null);

		return builder.create();
	}

	@Override
	public void onBeforeDownload() {
		showDialog(DIALOG_ID_LOGIN);// ��ʾ��¼�Ի���
	}

	@Override
	public void onProgressChanged(int totleSize, int countSize) {
		
	}

	@Override
	public void onAfterDownload(File file) {
		dismissDialog(DIALOG_ID_LOGIN);// ���ص�¼�Ի���
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject json = new JSONObject(sb.toString());
			if (200 == json.optInt("rt", -1)) {
				Toast.makeText(this,
						getString(R.string.hint_dialog_login_success, mName),
						Toast.LENGTH_LONG).show();
				if (Constants.INCEPTIVE_PASSWORD.equals(mPassword)) {//�ж��ǳ�ʼ���룬����ת���޸��������
					gotoOtherActivity(CHANGE_PWD);
				} else {
					gotoOtherActivity(MAIN);
				}

			} else {
				Toast.makeText(this,
						getString(R.string.hint_dialog_login_faild),
						Toast.LENGTH_LONG).show();
				gotoOtherActivity(SELF);
			}
			finish();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
