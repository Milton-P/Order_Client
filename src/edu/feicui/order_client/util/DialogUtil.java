package edu.feicui.order_client.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import edu.feicui.order_client.R;
import edu.feicui.order_client.base.BaseActivity;

/**
 * @author Sogrey
 *
 */
public class DialogUtil extends BaseActivity {
	/** DIALOG name��ͨ�ã� */
	public static final String DIALOG_NAME_STRING = "DIALOG";
//	/** �˳�Ӧ�öԻ���ID */
//	public static final int DIALOG_EXIT = 0x1;
	/** �Ի���ID �������� */
	private int mID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		mID = intent.getIntExtra(DIALOG_NAME_STRING, 0);
		showDialog(mID);
	}
	
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
//		case DIALOG_EXIT://�˳�Ӧ��
//			return createExitDialog( args);

		default:
			return super.onCreateDialog(id, args);
		}
	}
	
	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		// TODO Auto-generated method stub
		switch (id) {
//		case DIALOG_XXX:
//			
//			break;

		default:
			break;
		}
		super.onPrepareDialog(id, dialog, args);
	}
	
	/** �����˳�Ӧ�öԻ��� */
	private Dialog createExitDialog(Bundle args) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.hint_exit_dialog));
		 builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(getString(R.string.txt_exit_ack));
		builder.setPositiveButton(getString(R.string.ok), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		builder.setNegativeButton(getString(R.string.back), null);
		Dialog dialog = builder.create();
		return dialog;
	}
}
