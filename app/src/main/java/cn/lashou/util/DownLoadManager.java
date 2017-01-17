package cn.lashou.util;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownLoadManager {

	private static boolean stop = false;
	public static final String SDPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()+"/MyLaShou";

	/**
	 * 从服务器下载apk:
	 */
	public static File getFileFromServer(String path) throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.connect();
			InputStream is = conn.getInputStream();
			File f = new File(SDPATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			File file = new File(SDPATH, "out.apatch");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			// byte[] dataAll = toByteArray(is);
			// int lenghtOfFile = dataAll.length; // 获取到文件的大小
			// L.d("lenghtOfFile", lenghtOfFile + "");
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				if (stop) {
					stop = false;
					file = null;
					break;
				}
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				// 通知handler去更新视图组件
				// Message msg = new Message();
				// msg.getData().putInt("size", (int) ((total * 100) /
				// lenghtOfFile));
				// L.d("ss", (int) ((total * 100) / lenghtOfFile) + "");
				// handler.sendMessage(msg);
				// Thread.sleep(1000);// 休息1秒后再读取下载进度
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	public static void stop() {
		stop = true;
	}

	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

	// 通过url下载字节流
	public static InputStream downStream(String urlStr) {

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(60 * 1000);
			conn.setReadTimeout(60 * 1000);
			conn.setRequestMethod("GET");
			conn.connect();
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();

				return is;
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 讲字节流写入sod卡
	public static boolean writeStream(String path, String fileName,
			boolean isApp, InputStream is) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(SDPATH + File.separator + path);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(SDPATH
						+ File.separator + path + File.separator + fileName,
						isApp);
				byte[] content = readAllStream(is);
				if (null != content) {
					fos.write(content);
					fos.flush();
					fos.close();
				} else {
					file.delete();
					fos.close();
					return false;
				}
				return true; // 写入SD卡成功返回true
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	// 将字节流转换成byte[]数组
	private static byte[] readAllStream(InputStream is) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int size;
		try {
			while ((size = is.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, size);
			}
			byte[] content = bos.toByteArray();
			bos.close();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
