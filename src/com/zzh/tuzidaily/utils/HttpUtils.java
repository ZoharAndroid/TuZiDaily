package com.zzh.tuzidaily.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

public class HttpUtils {

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, ResponseHandlerInterface responseHandler) {
		client.get(MyContants.BASEURL + url, responseHandler);
	}

	public static void getImage(String url,
			ResponseHandlerInterface responseHandler) {
		client.get(url, responseHandler);
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	
	public static byte[] getImage(final String path) throws IOException {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(8000);
		conn.setReadTimeout(8000);
		InputStream inputStream = conn.getInputStream();
		byte[] data = StreamTool.readInputStream(inputStream);
		return data;
	}
}
