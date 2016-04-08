package com.zzh.tuzidaily.utils;

public interface MyContants {
	public static final String APP_KEY = "3755567013"; // 应用的APP_KEY
	public static final String REDIRECT_URL = "http://www.sina.com";// 应用的回调页
	public static final String SCOPE = // 应用申请的高级权限
	"email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
	
	public static final String BASEURL = "http://news-at.zhihu.com/api/4/";//知乎基本地址
	public static final String START = "start-image/1080*1776";//开始图片地址
}
