package com.zzh.tuzidaily.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.zzh.tuzidaily.R;

/**
 * 新浪/腾讯微博账号登录
 * 
 * @author zohar
 * 
 */
public class LoginActivity extends Activity {
	private static final String APP_KEY = "3755567013";
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";// 应用的回调页
	public static final String SCOPE = // 应用申请的高级权限
	"email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	private ImageView iv_back;
	private RelativeLayout rl_sina;
	private RelativeLayout rl_qq;
	private AuthInfo mAuthInfo;
	private SsoHandler mSsoHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
		initEvent();// 点击事件
	}

	private void initData() {
		mAuthInfo = new AuthInfo(this, APP_KEY, REDIRECT_URL, SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
	}

	/**
	 * 按键初始事件
	 */
	private void initEvent() {
		/**
		 * 返回按键
		 */
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		/**
		 * 点击登录新浪微博
		 */
		rl_sina.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 发送获取权限
				mSsoHandler.authorize(new AuthListener());
			}
		});
		/**
		 * 点击登录腾讯微博
		 */
		rl_qq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		iv_back = (ImageView) findViewById(R.id.iv_login_activity_back);
		rl_sina = (RelativeLayout) findViewById(R.id.rl_login_activity_sina);
		rl_qq = (RelativeLayout) findViewById(R.id.rl_login_activity_qq);
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		private Oauth2AccessToken mAccessToken;

		@Override
		public void onComplete(Bundle values) {
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// 从这里获取用户输入的 电话号码信息
			String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {// 如果有效

				// 保存 Token 到 SharedPreferences
				// AccessTokenKeeper.writeAccessToken(WBAuthActivity.this,
				// mAccessToken);
				Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT)
						.show();
				System.out
						.println("获得acccess_token：" + mAccessToken.getToken());
			} else {// 无效
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。

				Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_LONG)
						.show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(LoginActivity.this, "授权异常", Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

	}
}
