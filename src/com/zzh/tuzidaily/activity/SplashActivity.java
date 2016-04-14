package com.zzh.tuzidaily.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.zzh.tuzidaily.R;
import com.zzh.tuzidaily.utils.HttpUtils;
import com.zzh.tuzidaily.utils.MyContants;
import com.zzh.tuzidaily.utils.ShowToast;

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
	protected static final int FINISH = 2;
	private ImageView iv_splashbg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		startAnimation();// 显示动画
		// initShow();
	}

	/**
	 * 初始化界面显示
	 */
	// private void initShow() {
	// new Thread() {
	// public void run() {
	// long startTime = System.currentTimeMillis();
	//
	// if (HttpUtils.isNetworkConnected(SplashActivity.this)) {
	// // 如果网络连接了,获取知乎日报服务器中的Splash加载图片
	// HttpUtils.get(MyContants.START,
	// new AsyncHttpResponseHandler() {
	//
	// // 连接失败
	// @Override
	// public void onFailure(int arg0,
	// org.apache.http.Header[] arg1,
	// byte[] arg2, Throwable arg3) {
	// }
	//
	// @Override
	// public void onSuccess(int arg0,
	// org.apache.http.Header[] arg1,
	// byte[] arg2) {
	// try {
	// // 服务器连接成功，就开始Json解析文件的内容
	// JSONObject jsonObject = new JSONObject(
	// new String(arg2));
	//
	// String imageUrl = (String) jsonObject
	// .get("img");
	// // 获取图片信息
	// HttpUtils
	// .getImage(
	// imageUrl,
	// new BinaryHttpResponseHandler() {
	//
	// @Override
	// public void onSuccess(
	// int arg0,
	// org.apache.http.Header[] arg1,
	// byte[] arg2) {
	// // 接收成功，保存图片
	// saveImage(
	// imageFile,
	// arg2);
	// }
	//
	// @Override
	// public void onFailure(
	// int arg0,
	// org.apache.http.Header[] arg1,
	// byte[] arg2,
	// Throwable arg3) {
	// // 接收失败，直接回到主机面
	//
	// }
	// });
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// });
	// } else {
	// // 如果没有网络
	// ShowToast.showToast(SplashActivity.this, "当前无网络连接", 1);
	// }
	//
	// // 判断处理的事件
	// long endTime = System.currentTimeMillis();
	// if (endTime - startTime < 3000) {
	// SystemClock.sleep(3000 - (endTime - startTime));
	// Message msg = Message.obtain();
	// msg.what = FINISH;
	// handler.sendMessage(msg);
	// } else {
	// Message msg = Message.obtain();
	// msg.what = FINISH;
	// handler.sendMessage(msg);
	// }
	// };
	// }.start();
	// }

	private void saveImage(File imageFile, byte[] bytes) {
		imageFile.delete();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imageFile);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// private Handler handler = new Handler() {
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case LOADING:// 去加载数据
	// break;
	// case FINISH:// 加载完毕
	// loadMian();
	// break;
	// default:
	// break;
	// }
	// };
	// };

	private File imageFile;

	/**
	 * 加载主界面
	 */
	private void loadMian() {
		finish();// 销毁当前activity
		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	/**
	 * 知乎显示动画效果
	 */
	private void startAnimation() {
		// 判断本地是否有图片
		File dir = getFilesDir();// 获取当前File目录
		imageFile = new File(dir, "start.jpg");
		if (imageFile.exists()) {
			// 如果文件存在了
			iv_splashbg.setImageBitmap(BitmapFactory.decodeFile(imageFile
					.getAbsolutePath()));
		}

		ScaleAnimation scale = new ScaleAnimation(1.0f, 1.2f, 1.1f, 1.2f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(3800);
		scale.setFillAfter(true);

		scale.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				long startTime = System.currentTimeMillis();
				// 如果网络可用
				if (HttpUtils.isNetworkConnected(SplashActivity.this)) {
					// 从知乎日报服务器中获取开始图片
					HttpUtils.get(MyContants.START,
							new AsyncHttpResponseHandler() {

								@Override
								public void onSuccess(int arg0, Header[] arg1,
										byte[] arg2) {
									// JSon解析
									try {
										JSONObject jsonObject = new JSONObject(
												new String(arg2));
										String imageUrl = jsonObject
												.getString("img");
										HttpUtils
												.getImage(
														imageUrl,
														new BinaryHttpResponseHandler() {

															@Override
															public void onSuccess(
																	int arg0,
																	Header[] arg1,
																	byte[] arg2) {
																// 保存图片
																saveImage(
																		imageFile,
																		arg2);
																loadMian();
															}

															@Override
															public void onFailure(
																	int arg0,
																	Header[] arg1,
																	byte[] arg2,
																	Throwable arg3) {
																ShowToast
																		.showToast(
																				SplashActivity.this,
																				"图片加载失败",
																				1);
																loadMian();
															}
														});
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								@Override
								public void onFailure(int arg0, Header[] arg1,
										byte[] arg2, Throwable arg3) {
									ShowToast.showToast(SplashActivity.this,
											"网络资源获取失败", 1);
								}
							});
				} else {
					// 如果网络不可用
					ShowToast.showToast(SplashActivity.this, "当前网络不可用", 1);
					//loadMian();
				}

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				loadMian();
			}
		});

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
