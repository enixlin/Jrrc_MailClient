/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) fieldsfirst noctor radix(10) lradix(10) 
// Source File Name:   HttpGet.java

package com.gdbeim.oa.applet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

// Referenced classes of package com.gdbeim.oa.applet:
//            ProgressListener, DownloadTask, DownloadFeedback

public class HttpGet {

	public static final boolean DEBUG = false;
	private static int BUFFER_SIZE = 8192;
	private Vector vDownLoad;
	private Vector vFileList;
	private Vector vFileSize;
	private Vector vType;
	private Vector vAttachList;

	public HttpGet() {
		vDownLoad = new Vector();
		vFileList = new Vector();
		vFileSize = new Vector();
		vType = new Vector();
		vAttachList = new Vector();
	}

	public void resetList() {
		vDownLoad.clear();
		vFileList.clear();
	}

	public void addItem(String url, String filename, Long filesize, String type, List attachIds) {
		vDownLoad.add(url);
		vFileList.add(filename);
		vFileSize.add(filesize);
		vType.add(type);
		vAttachList.add(attachIds);
	}

	

	public boolean saveToFile(String destUrl, String fileName, ProgressListener listener)
			throws IOException, InterruptedException {
		boolean isSuccess = true;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte buf[] = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(fileName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
				listener.showCurrentProgress(size);
			}
			fos.close();
			bis.close();
			httpUrl.disconnect();
			Thread.sleep(10L);
		} catch (IOException e) {
			isSuccess = false;
			throw e;
		} catch (InterruptedException e) {
			isSuccess = false;
			throw e;
		}
		return isSuccess;
	}

	public boolean saveMessage(String destUrl, String fileName, ProgressListener listener, List lstAttach)
			throws IOException, InterruptedException {
		boolean isSuccess = false;
		isSuccess = saveToFile(destUrl, fileName, listener);
		if (!isSuccess)
			return false;
		for (int i = 0; i < lstAttach.size(); i++) {
			String attachUrl = (String) lstAttach.get(i);
			String aUrl[] = attachUrl.split(";");
			if (aUrl != null && aUrl.length == 2) {
				String url = aUrl[0];
				String filename = aUrl[1];
				isSuccess = saveToFile(url, filename, listener);
				if (!isSuccess)
					return false;
			}
		}

		return isSuccess;
	}

	public DownloadTask getDownloadTask(String strUrl) {
		HttpURLConnection httpUrl = null;
		URL url = null;
		DownloadTask task = null;
		try {
			url = new URL(strUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			ObjectInputStream ois = new ObjectInputStream(httpUrl.getInputStream());
			task = (DownloadTask) ois.readObject();
			ois.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			System.out.println("DownloadTask error:" + e);
		}
		return task;
	}

	public void sendFeedBack(String strUrl, DownloadFeedback feed) {
		HttpURLConnection httpConn = null;
		URL url = null;
		try {
			StringBuffer info = new StringBuffer();
			info.append("&receiveIds=");
			Long receiveIds[] = feed.getReceiveIds();
			for (int i = 0; i < receiveIds.length; i++)
				info.append(receiveIds[i]).append(",");

			if (receiveIds.length > 0)
				info.deleteCharAt(info.length() - 1);
			info.append("&sendIds=");
			Long sendIds[] = feed.getSendIds();
			for (int i = 0; i < sendIds.length; i++)
				info.append(sendIds[i]).append(",");

			if (sendIds.length > 0)
				info.deleteCharAt(info.length() - 1);
			info.append("&draftIds=");
			Long draftIds[] = feed.getDraftIds();
			for (int i = 0; i < draftIds.length; i++)
				info.append(draftIds[i]).append(",");

			if (draftIds.length > 0)
				info.deleteCharAt(info.length() - 1);
			info.append("&wasteIds=");
			Long wasteIds[] = feed.getWasteIds();
			for (int i = 0; i < wasteIds.length; i++)
				info.append(wasteIds[i]).append(",");

			if (wasteIds.length > 0)
				info.deleteCharAt(info.length() - 1);
			url = new URL(strUrl + info);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.connect();
			httpConn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null)
				System.out.println(line);
			reader.close();
			httpConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public String getSpeed(double s) {
		String size = "";
		if (s > 1024D) {
			s /= 1024D;
			size = (new DecimalFormat("#,##0.00")).format(s) + "MB";
		} else {
			size = (new DecimalFormat("#,##0.00")).format(s) + "KB";
		}
		return size;
	}



}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\enixlin\git\Jrrc_MialClient\lib\Applet.jar
	Total time: 2189 ms
	Jad reported messages/errors:
Couldn't fully decompile method run
Couldn't resolve all exception handlers in method run
	Exit status: 0
	Caught exceptions:
*/
