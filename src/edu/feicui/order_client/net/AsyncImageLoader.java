package edu.feicui.order_client.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import edu.feicui.order_client.util.Constants;

/**
 * ����ͼƬ
 * 
 * @author Sogrey
 */
public class AsyncImageLoader {

	public static final byte[] HEX_CHAR = "0123456789ABCDEF".getBytes();
	private static final int MSG_DOWNLOAD = 0x10;

	public AsyncImageLoader() {
		File cache = new File(Constants.PATH_CACHE);// ��������Ŀ¼
		cache.mkdirs();
	}

	/** �����Ѽ��ص��ڴ��е�ͼƬ��Դ */
	protected static Map<String, SoftReference<Bitmap>> sCache;

	// static {
	// sCache = new HashMap<String, SoftReference<Bitmap>>();
	// Bitmap bmp = null;
	// sCache.put("asd", new SoftReference<Bitmap>(bmp));
	// SoftReference<Bitmap> ref = sCache.get("asd");
	// bmp = ref.get();
	// if (bmp!=null) {
	// //�����ÿ��ܱ�����ʹ��ǰ���жϣ���Ȼ����ָ��
	// }
	// }
	static {
		sCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	protected Handler mHandler = new DownloadHandler();

	public void LoadImage(String url, ImageCallBack callBack) {
		// һ���ڴ滺��
		if (sCache.containsKey(url)) {// �鵽�л���
			Bitmap bmp = sCache.get(url).get();
			return;
		} else {// �鵽û�л��棬��������û�л���
			sCache.remove(url);
		}
		// �����ļ�����
		// ��url��ȡת������ļ���
		String name = generateName(url);
		// �½��ļ�������Ŀ¼��
		File file = new File(Constants.PATH_CACHE, name);

		if (file.exists()) {
			Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
			if (bmp != null) {
				sCache.put(url, new SoftReference<Bitmap>(bmp));// ���ص��ڴ�
				callBack.onImageLoaded(url, bmp);
				return;
			} else {
				file.delete();
			}
		}
		// �������绺��
		new DownloadThread(url, file, callBack).start();
	};

	/**
	 * MD5 ת��
	 * 
	 * @param rawString
	 *            ��Ҫת����ַ���
	 * */
	protected String generateName(String rawString) {
		try {
			// �õ�MD5�㷨����
			MessageDigest md5 = MessageDigest.getInstance("md5");
			// update ���ü�������Դ
			md5.update(rawString.getBytes());
			// �õ���������16������
			return toHexString(md5.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "default";
	}

	/**
	 * @param digest
	 *            ��Ҫת�����ַ���
	 * @return ת��������ַ���
	 */
	private String toHexString(byte[] digest) {
		StringBuilder sb = new StringBuilder(digest.length * 2);
		for (int i = 0; i < digest.length; i++) {
			sb.append(HEX_CHAR[(digest[i] & 0xf0) >>> 4]);
			sb.append(HEX_CHAR[digest[i] & 0x0f]);
		}
		return sb.toString();
	}

	class DownloadHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_DOWNLOAD:
				Object[] array = (Object[]) msg.obj;
				String url = (String) array[0];
				File file = (File) array[1];
				ImageCallBack callBack = (ImageCallBack) array[2];
				Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
				callBack.onImageLoaded(url, bmp);
				break;

			default:
				break;
			}
		}
	}

	/** ͼƬ������ɣ��ص��ӿ� */
	public interface ImageCallBack {
		void onImageLoaded(String url, Bitmap bmp);
	}

	class DownloadThread extends Thread {
		String mUrl;
		File mFile;
		ImageCallBack mCallBack;

		DownloadThread(String url, File file, ImageCallBack callBack) {
			super();
			this.mUrl = url;
			this.mFile = file;
			this.mCallBack = callBack;
		}

		@Override
		public void run() {
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				byte[] buffer = new byte[1024];
				int size = 0;
				URL url = new URL(mUrl);
				HttpURLConnection http = (HttpURLConnection) url
						.openConnection();
				is = http.getInputStream();
				fos = new FileOutputStream(mFile);
				while ((size = is.read(buffer, 0, Constants.SIZE_BUFFER)) > 0) {
					fos.write(buffer, 0, size);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					is.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			Message msg = Message.obtain();
			msg.what = MSG_DOWNLOAD;
			msg.obj = new Object[] { this.mUrl, this.mFile, this.mCallBack };
			mHandler.sendMessage(msg);
		}
	}
}
