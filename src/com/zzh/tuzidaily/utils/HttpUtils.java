package com.zzh.tuzidaily.utils;

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

}
