package edu.feicui.order_client.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import edu.feicui.order_client.R;
import edu.feicui.order_client.adapter.GalleryAdapter;
import edu.feicui.order_client.base.BaseActivity;
import edu.feicui.order_client.choosedish.ChooseDishActivity;
import edu.feicui.order_client.more.CheckOrderListActivity;
import edu.feicui.order_client.more.MoreActivity;
import edu.feicui.order_client.util.Constants;

/**
 * ������
 * 
 * @author Sogrey
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	
	/**��ǰʹ���˻���*/
	protected String mUser;
	/** ���-��ť */
	protected Button mBtnChooseCish;
	/** ����-��ť */
	protected Button mBtnCheckout;
	/** ��������-��ť */
	protected Button mBtnSchedule;
	/** �ӵ�-��ť */
	protected Button mBtnAdd;
	/** ����-��ť */
	protected Button mBtnMore;
	/** ��ҳ����ʾ�����һ�����Gallery */
	protected Gallery mGlrPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_main);
		mUser=getIntent().getStringExtra(Constants.USER_NAME);
		initViews();
	}

	private void initViews() {
		mBtnChooseCish = (Button) findViewById(R.id.btn_main_choosedish);
		mBtnChooseCish.setOnClickListener(this);
		mBtnCheckout = (Button) findViewById(R.id.btn_main_checkout);
		mBtnCheckout.setOnClickListener(this);
		mBtnSchedule = (Button) findViewById(R.id.btn_main_schedule);
		mBtnSchedule.setOnClickListener(this);
		mBtnAdd = (Button) findViewById(R.id.btn_main_add);
		mBtnAdd.setOnClickListener(this);
		mBtnMore = (Button) findViewById(R.id.btn_main_more);
		mBtnMore.setOnClickListener(this);
		mGlrPic = (Gallery) findViewById(R.id.glr_main_showpics);
		mGlrPic.setAdapter(new GalleryAdapter(this));
		
//		Timer timer = new Timer();
//		timer.schedule(task, 3000);
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		int id = view.getId();
		switch (id) {
		case R.id.btn_main_choosedish:// ���
			intent.setClass(this, ChooseDishActivity.class);
			intent.putExtra(Constants.CHOOSEDISH, Constants.CHOOSE_ORDER);
			break;
		case R.id.btn_main_checkout:// ����
			intent.setClass(this, CheckOrderListActivity.class);
			intent.putExtra(Constants.CHECK_ORDER, Constants.CHECKOUT);
			break;
		case R.id.btn_main_schedule:// ��������
			intent.setClass(this, CheckOrderListActivity.class);
			intent.putExtra(Constants.CHECK_ORDER, Constants.ORDER_PROGRESS);
			break;
		case R.id.btn_main_add:// �ӵ�
			intent.setClass(this, CheckOrderListActivity.class);
			intent.putExtra(Constants.CHECK_ORDER, Constants.ADD_ORDER);
			break;
		case R.id.btn_main_more:// ����
			intent.setClass(this, MoreActivity.class);
			break;

		default:
			break;
		}
		intent.putExtra(Constants.USER_NAME, mUser);
		startActivity(intent);
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

		default:
			return super.onCreateDialog(id);
		}
	}

		/** �����˳�Ӧ�öԻ��� */
	private Dialog createExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.hint_exit_dialog));
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(getString(R.string.txt_exit_ack));
		builder.setPositiveButton(getString(R.string.ok),
				new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
//						finish();
						System.exit(0);
					}
				});
		builder.setNegativeButton(getString(R.string.back), null);
		return builder.create();
	}

}
