package com.zzh.tuzidaily.activity;

import com.zzh.tuzidaily.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * Splash界面
 * 
 * @author zohar
 * 
 * @date 2016年3月23日
 * @version 1.0
 * 
 */
public class SplashActivity extends Activity {

	protected static final int LOADING = 1;
	private ImageView iv_splashbg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		startAnimation();// 显示动画
		initShow();
	}
	
	/**
	 * 初始化界面显示
	 */
	private void initShow(){
		new Thread(){
			public void run() {
				long startTime = System.currentTimeMillis();
				
				long endTime = System.currentTimeMillis();
				if(endTime - startTime < 3000){
					SystemClock.sleep(3000-(endTime-startTime));
					Message msg = new Message();
					msg.what= LOADING;
					handler.sendMessage(msg);
				}else{
					//不作处理
				}
			};
		}.start();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOADING:
				loadMian();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 加载主界面
	 */
	private void loadMian() {
		finish();//销毁当前activity
		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	/**
	 * 知乎显示动画效果
	 */
	private void startAnimation() {
		ScaleAnimation scale = new ScaleAnimation(1.0f, 1.2f, 1.1f, 1.2f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(3800);
		scale.setFillAfter(true);
		iv_splashbg.startAnimation(scale);
	}

	/**
	 * 初始化界面
	 * 
	 */
	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		iv_splashbg = (ImageView) findViewById(R.id.activity_splash_background);

	}
}
