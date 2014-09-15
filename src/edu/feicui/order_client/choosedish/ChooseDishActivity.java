package edu.feicui.order_client.choosedish;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.feicui.order_client.R;
import edu.feicui.order_client.base.BaseActivity;
import edu.feicui.order_client.db.DBHelper;
import edu.feicui.order_client.db.MenuDBWrapper;
import edu.feicui.order_client.db.TableDBWrapper;
import edu.feicui.order_client.net.AsyncImageLoader;
import edu.feicui.order_client.net.AsyncImageLoader.ImageCallBack;
import edu.feicui.order_client.util.Constants;

/**
 * ��˽���
 * 
 * @author Sogrey
 * 
 */
public class ChooseDishActivity extends BaseActivity implements
		OnClickListener, OnChildClickListener, OnGroupClickListener,
		OnItemSelectedListener {

	/** ��ǰʹ���˻��� */
	protected String mUser;
	/** �������-���/�ӵ� */
	protected int mType;
	/** ��ǰ���� */
	protected String mNumber;
	/** ���ű������ */
	public TableDBWrapper mTableDB;
	/** �˵�������� */
	public MenuDBWrapper mMenuDB;
	/** ���ݿ��ѯ���ص��α� */
	public Cursor mCursor;
	/** �������� */
	protected String[] mMenuType;
	/** �����¾���������� */
	protected List<List<String>> mMenuSet;
	/** ��Ҫ�����������List */
	protected List<List<ItemData>> mList;
	/** ѡ�����Ű�ť */
	protected Spinner mSprTableId;
	/** ���� */
	protected TextView mTxtTableId;
	/** ѡ�����ఴť */
	protected Spinner mSprChoosedishTypes;
	/** ���ȷ����ť */
	protected Button mBtnChoosedishOk;
	/** ���۵��˵��б� */
	protected ExpandableListView mEpdTypes;
	/** ��ƷͼƬ */
	protected ImageView mImgPic;
	/** ���� */
	protected TextView mTxtName;
	/** �˼� */
	protected TextView mTxtPrice;
	/** ��Ʒ���� */
	protected TextView mTxtRemark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosedish);
		initDB();
		initDatas();
		initViews();
		initAdapter();
	}

	/** ��ʼ�����ݿ���� */
	private void initDB() {
		mTableDB = TableDBWrapper.getInstance(getApplication());
		mMenuDB = MenuDBWrapper.getInstance(getApplication());
	}

	/** ��ʼ������ */
	private void initDatas() {
		Intent intent = getIntent();
		mUser = intent.getStringExtra(Constants.USER_NAME);
		mType = intent.getIntExtra(Constants.CHOOSEDISH, 0);
		mNumber = intent.getStringExtra(Constants.NUMBER);// ������
		mMenuType = getResources().getStringArray(R.array.dish_type);
		mMenuSet = new ArrayList<List<String>>();
		mList = new ArrayList<List<ItemData>>();
		mMenuSet.add(gleanStapleFood());
		mMenuSet.add(gleanHotFood());
		mMenuSet.add(gleanCoolFood());
		mMenuSet.add(gleanSoup());
		mMenuSet.add(gleanDrink());
		mMenuSet.add(gleanTableware());
	}

	/**
	 * @return ��ʳ�б�
	 */
	private List<String> gleanStapleFood() {
		List<String> stapleFood = new ArrayList<String>();
		List<ItemData> list = new ArrayList<ItemData>();
		ItemData data;
		mCursor = mMenuDB.rawQueryRank(Constants.MENU_STAPLE_FOOD);
		while (mCursor.moveToNext()) {
			data = new ItemData();
			int nameIndex = mCursor.getColumnIndex(DBHelper.COLUMN_MENU_NAME);
			String name = mCursor.getString(nameIndex);
			stapleFood.add(name);
			data.name = name;
			data.checked = false;
			list.add(data);
		}
		mList.add(list);
		mCursor.close();
		return stapleFood;
	}

	/**
	 * @return �Ȳ��б�
	 */
	private List<String> gleanHotFood() {
		List<String> hotFood = new ArrayList<String>();
		List<ItemData> list = new ArrayList<ItemData>();
		ItemData data;
		mCursor = mMenuDB.rawQueryRank(Constants.MENU_HOT_FOOD);
		while (mCursor.moveToNext()) {
			data = new ItemData();
			int nameIndex = mCursor.getColumnIndex(DBHelper.COLUMN_MENU_NAME);
			String name = mCursor.getString(nameIndex);
			hotFood.add(name);
			data.name = name;
			data.checked = false;
			list.add(data);
		}
		mList.add(list);
		mCursor.close();
		return hotFood;
	}

	/**
	 * @return �����б�
	 */
	private List<String> gleanCoolFood() {
		List<String> coolFood = new ArrayList<String>();
		List<ItemData> list = new ArrayList<ItemData>();
		ItemData data;
		mCursor = mMenuDB.rawQueryRank(Constants.MENU_COOL_FOOD);
		while (mCursor.moveToNext()) {
			data = new ItemData();
			int nameIndex = mCursor.getColumnIndex(DBHelper.COLUMN_MENU_NAME);
			String name = mCursor.getString(nameIndex);
			coolFood.add(name);
			data.name = name;
			data.checked = false;
			list.add(data);
		}
		mList.add(list);
		mCursor.close();
		return coolFood;
	}

	/**
	 * @return �����б�
	 */
	private List<String> gleanSoup() {
		List<String> soup = new ArrayList<String>();
		List<ItemData> list = new ArrayList<ItemData>();
		ItemData data;
		mCursor = mMenuDB.rawQueryRank(Constants.MENU_SOUP_FOOD);
		while (mCursor.moveToNext()) {
			data = new ItemData();
			int nameIndex = mCursor.getColumnIndex(DBHelper.COLUMN_MENU_NAME);
			String name = mCursor.getString(nameIndex);
			soup.add(name);
			data.name = name;
			data.checked = false;
			list.add(data);
		}
		mList.add(list);
		mCursor.close();
		return soup;
	}

	/**
	 * @return �����б�
	 */
	private List<String> gleanDrink() {
		List<String> drink = new ArrayList<String>();
		List<ItemData> list = new ArrayList<ItemData>();
		ItemData data;
		mCursor = mMenuDB.rawQueryRank(Constants.MENU_DRINK_FOOD);
		while (mCursor.moveToNext()) {
			data = new ItemData();
			int nameIndex = mCursor.getColumnIndex(DBHelper.COLUMN_MENU_NAME);
			String name = mCursor.getString(nameIndex);
			drink.add(name);
			data.name = name;
			data.checked = false;
			list.add(data);
		}
		mList.add(list);
		mCursor.close();
		return drink;
	}

	/**
	 * @return �;��б�
	 */
	private List<String> gleanTableware() {
		List<String> Tableware = new ArrayList<String>();
		List<ItemData> list = new ArrayList<ItemData>();
		ItemData data;
		mCursor = mMenuDB.rawQueryRank(Constants.MENU_TABLEWARE_FOOD);
		while (mCursor.moveToNext()) {
			data = new ItemData();
			int nameIndex = mCursor.getColumnIndex(DBHelper.COLUMN_MENU_NAME);
			String name = mCursor.getString(nameIndex);
			Tableware.add(name);
			data.name = name;
			data.checked = false;
			list.add(data);
		}
		mList.add(list);
		mCursor.close();
		return Tableware;
	}

	private void initViews() {
		mImgPic = (ImageView) findViewById(R.id.img_include_choosedish_pic);
		mTxtName = (TextView) findViewById(R.id.txt_include_choosedish_info_name);
		mTxtPrice = (TextView) findViewById(R.id.txt_include_choosedish_info_price);
		mTxtRemark = (TextView) findViewById(R.id.txt_include_choosedish_info);

		mSprTableId = (Spinner) findViewById(R.id.spr_choosedish_tableid);
		mTxtTableId = (TextView) findViewById(R.id.txt_choosedish_tableid);
		mSprChoosedishTypes = (Spinner) findViewById(R.id.spr_choosedish_types);
		mBtnChoosedishOk = (Button) findViewById(R.id.btn_choosedish_ok);
		mBtnChoosedishOk.setOnClickListener(this);
		mEpdTypes = (ExpandableListView) findViewById(R.id.epd_types);

		// mSprTableId.setOnItemSelectedListener(this);
		mSprChoosedishTypes.setOnItemSelectedListener(this);

	}

	private void initAdapter() {
		{
			switch (mType) {
			case Constants.CHOOSE_ORDER:// ���
				mTxtTableId.setVisibility(View.GONE);
				mSprTableId.setVisibility(View.VISIBLE);
				mCursor = mTableDB.rawQueryRank();
				List<String> tableId = new ArrayList<String>();
				while (mCursor.moveToNext()) {
					int index = mCursor
							.getColumnIndex(DBHelper.COLUMN_TABLE_NUM);
					String num = mCursor.getInt(index) + "";
					tableId.add(num);
				}
				String[] tableStrings = tableId.toArray(new String[tableId
						.size()]);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, tableStrings);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mSprTableId.setPromptId(R.string.txt_tableid);
				mSprTableId.setAdapter(adapter);
				mCursor.close();
				break;
			case Constants.CHOOSE_ADD_ORDER:// �ӵ�
				mTxtTableId.setVisibility(View.VISIBLE);
				mSprTableId.setVisibility(View.GONE);
				String tableNum = mNumber.substring(12, 15);// �Ӷ�����ˮ�г�ȡ����
				mTxtTableId.setText(tableNum);
				break;
			default:
				break;
			}

		}
		{
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, mMenuType);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mSprChoosedishTypes.setPromptId(R.string.txt_types);
			mSprChoosedishTypes.setAdapter(adapter);
			mCursor.close();
		}
		mEpdTypes.setAdapter(new EpdAdapter());
		mEpdTypes.setOnGroupClickListener(this);
		mEpdTypes.setOnChildClickListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		mEpdTypes.expandGroup(position, true);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	/**
	 * �������¼�����
	 * 
	 * @param parent
	 *            ExpandableListView���
	 * @param view
	 *            ������ķ�����Ŀ
	 * @param groupPosition
	 *            ���������
	 * @param id
	 *            �������ID
	 * @return true:���۵���չ��������չ�����ղ�£��false������չ������£
	 * */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View view,
			int groupPosition, long id) {
		Toast.makeText(this, mMenuType[groupPosition], Toast.LENGTH_SHORT)
				.show();
		return false;
	}

	/**
	 * ��������Ŀ����¼�����
	 * 
	 * @param parent
	 *            ExpandableListView���
	 * @param view
	 *            ������ķ�������Ŀ
	 * @param groupPosition
	 *            ���������
	 * @param childPosition
	 *            �����������Ŀ���
	 * @param id
	 *            �����������ĿID
	 * @return true:���۵���չ��������չ�����ղ�£��false������չ������£
	 * */
	@Override
	public boolean onChildClick(ExpandableListView parent, View view,
			int groupPosition, int childPosition, long id) {
		Toast.makeText(this, mMenuSet.get(groupPosition).get(childPosition),
				Toast.LENGTH_SHORT).show();
		ChildHolder holder = (ChildHolder) view.getTag();
		ItemData data = mList.get(groupPosition).get(childPosition);
		holder.chbChild.setChecked(!holder.chbChild.isChecked());
		data.checked = holder.chbChild.isChecked();
		upDateMenuInfo(mMenuSet.get(groupPosition).get(childPosition));
		return false;
	}

	/**
	 * @param string
	 *            ������Ĳ�Ŀ
	 * 
	 */
	private void upDateMenuInfo(String name) {
		mCursor = mMenuDB.rawQueryRank(name);
		while (mCursor.moveToNext()) {
			String pic = mCursor.getString(mCursor
					.getColumnIndex(DBHelper.COLUMN_MENU_PIC));
			int price = mCursor.getInt(mCursor
					.getColumnIndex(DBHelper.COLUMN_MENU_PRICE));
			String remark = mCursor.getString(mCursor
					.getColumnIndex(DBHelper.COLUMN_MENU_REMARK));

			String picUrl = Constants.PATH_SERVER + "/" + pic;
			AsyncImageLoader loader = new AsyncImageLoader();
			loader.LoadImage(picUrl, new ImageCallBack() {

				@Override
				public void onImageLoaded(String url, Bitmap bmp) {
					mImgPic.setImageBitmap(bmp);
				}
			});
			mTxtName.setText(name);
			mTxtPrice.setText(getString(R.string.txt_infos_unitprice, price));
			mTxtRemark.setText(remark);
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.btn_choosedish_ok:// ���ȷ����ť
			Intent intent = new Intent();
			intent.setClass(this, ChooseInfoActivity.class);
			switch (mType) {
			case Constants.CHOOSE_ORDER:// ���
				intent.putExtra(Constants.CHOOSEDISH, Constants.CHOOSE_ORDER);
				intent.putExtra(Constants.CHOOSE_TABLE_ID, mSprTableId
						.getSelectedItem().toString());// ����
				break;
			case Constants.CHOOSE_ADD_ORDER:// �ӵ�
				intent.putExtra(Constants.NUMBER, mNumber);// �ӵ���ԭ������ˮ��
				intent.putExtra(Constants.CHOOSEDISH,
						Constants.CHOOSE_ADD_ORDER);
				break;

			default:
				break;
			}
			ArrayList<String> list = new ArrayList<String>();
			for (List<ItemData> iterable_element : mList) {
				for (ItemData data : iterable_element) {
					if (data.checked) {
						list.add(data.name);
					}
				}
			}
			intent.putExtra(Constants.USER_NAME, mUser);
			intent.putStringArrayListExtra("list", list);
			startActivity(intent);
			this.finish();
			break;

		default:
			break;
		}
	}

	class EpdAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			return mMenuType == null ? 0 : mMenuType.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mMenuSet == null || groupPosition >= mMenuSet.size()
					|| mMenuSet.get(groupPosition) == null ? 0 : mMenuSet.get(
					groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return mMenuType[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return mMenuSet.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return mMenuType[groupPosition].hashCode();
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return mMenuSet.get(groupPosition).get(childPosition).hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = convertView;
			GroupHolder holder;
			if (convertView == null) {
				holder = new GroupHolder();
				view = getLayoutInflater().inflate(
						R.layout.item_expendlist_group, null);
				holder.txtGroup = (TextView) view
						.findViewById(R.id.txt_expendlist_group_name);
				view.setTag(holder);
			} else {
				holder = (GroupHolder) view.getTag();
			}
			holder.txtGroup.setText(mMenuType[groupPosition]);
			return view;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = convertView;
			ChildHolder holder;
			if (convertView == null) {
				holder = new ChildHolder();
				view = getLayoutInflater().inflate(
						R.layout.item_expendlist_children, null);
				holder.txtChild = (TextView) view
						.findViewById(R.id.txt_expendlist_child_name);
				holder.chbChild = (CheckBox) view
						.findViewById(R.id.chb_expendlist_child);
				view.setTag(holder);
			} else {
				holder = (ChildHolder) view.getTag();
			}
			ItemData data = mList.get(groupPosition).get(childPosition);
			// holder.txtChild.setText(mMenuSet.get(groupPosition).get(
			// childPosition));
			holder.txtChild.setText(data.name);
			holder.chbChild.setChecked(data.checked);
			return view;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	class GroupHolder {
		TextView txtGroup;
	}

	class ChildHolder {
		TextView txtChild;
		CheckBox chbChild;
	}

	class ItemData {
		String name;
		boolean checked = false;
	}
}
