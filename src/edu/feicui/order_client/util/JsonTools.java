package edu.feicui.order_client.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON�����������ת��ΪJson
 * 
 * @author Sogrey
 * 
 */
public class JsonTools {
	public JsonTools() {
	}

	/**
	 * @param key
	 *            ��ʾjson�ַ�����ͷ��Ϣ
	 * @param value
	 *            �ǶԽ����ļ��ϵ�����
	 * @return
	 */
	// ������ת��ΪJSONObject
	public static String createJsonString(String key, Object value) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	// ������ת��ΪJSONArray
	public static String createJsonArrayString(String menuId, String number,
			String remark) {
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray.put(0, menuId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			jsonArray.put(1, number);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			jsonArray.put(2, remark);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}

	class Dish {
		public String menuId;
		public String number;
		public String remark;

		public Dish() {
		}

		public Dish(String menuId, String number, String remark) {
			this.menuId = menuId;
			this.number = number;
			this.remark = remark;
		}

		public String getMenuId() {
			return menuId;
		}

		public void setMenuId(String menuId) {
			this.menuId = menuId;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		// ��дtoString()����
		// @Override
		// public String toString() {
		// return "[" + menuId // jsonArray [0]-menuId-��ƷID
		// + ", " + number // jsonArray [1]-number-����
		// + ", " + remark // jsonArray [2]-remark-���]
		// + "]";
		// }
	}
}
