package com.zzh.tuzidaily.utils;
/**
 *  解析API中返回的url地址中带有\标志的
 *  http:\/\/pic1.zhimg.com\/d77edc0caab0b042ca62cf95cf011a70.jpg
 * @author Administrator
 *
 */
public class URLAddressTool {
	public static String URLAddressMake(String path){
		StringBuffer url = new StringBuffer();
		for (int i = 0; i < path.length(); i++) {
			char[] data = new char[path.length()];
			data[i] = path.charAt(i);
			if(data[i] !='\\'){
				url.append(data[i]);
			}
		}
		return url.toString();
	}
}
