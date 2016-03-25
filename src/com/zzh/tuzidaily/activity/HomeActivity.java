package com.zzh.tuzidaily.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zzh.tuzidaily.R;

/**
 * 主界面显示
 * 
 * @author zohar
 * @date 2016年3月24日
 * @version 1.0
 *
 */
public class HomeActivity extends Activity {
	private ImageView iv_menu;
	private TextView tv_title;
	private ImageView iv_login;
	private ImageView iv_popupwindow;
	private PopupWindow popWin;
	private LinearLayout ll_root;
	private ListView lv_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initClickEvent();
	}

	/**
	 * 主界面的点击事件
	 */
	private void initClickEvent() {
		ll_root.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		/**
		 * 图标：点击出现菜单的选项
		 */
		iv_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * 图标：点击注册
		 */
		iv_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		 
		/**
		 * 图标：点击显示popupwindow
		 */
		iv_popupwindow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popWinSet(v);
			}
		});
	}
	
	/**
	 * popupwindow显示设置
	 * 
	 * @param v
	 */
	private void popWinSet(View v) {
		
		View view = View.inflate(HomeActivity.this, R.layout.popupwindow_home, null);
		final TextView tv_nightmode = (TextView) view.findViewById(R.id.tv_popupwindow_nightmode);
		TextView tv_setting = (TextView) view.findViewById(R.id.tv_popupwindow_setting);
		
		if(popWin == null){
			popWin = new PopupWindow(view, -2, -2);
			popWin.setOutsideTouchable(true);//点击其他的地方就会消除这个popupwindow
			popWin.setFocusable(true);
			popWin.setTouchable(true);
			popWin.setBackgroundDrawable(new BitmapDrawable(getResources()));
			view.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(popWin.isShowing()){
						popWin.dismiss();
					}
					return false;
				}
			});
		}
		if(popWin.isShowing()){
			popWin.dismiss();
		}else{
			popWin.showAsDropDown(v, 1, 0);
		}
		
		/**
		 * popupwindow中的白天/夜间模式
		 */
		tv_nightmode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//首先关闭popupwindow
				closePopWin();
				tv_nightmode.setText("日间模式");
				
				
			}
		});
		/**
		 * popupwindow中的设置选项
		 */
		tv_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//首先关闭popupwindow
				closePopWin();
				
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		closePopWin();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/**
	 * 关闭popupwindow
	 */
	private void closePopWin(){
		if(popWin != null &&  popWin.isShowing()){
			popWin.dismiss();
			popWin = null;
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		iv_login = (ImageView) findViewById(R.id.iv_home_activity_login);
		tv_title = (TextView) findViewById(R.id.tv_home_activity_title);
		iv_menu = (ImageView) findViewById(R.id.iv_home_activity_menu);
		iv_popupwindow = (ImageView) findViewById(R.id.iv_home_activity_popupwindow);
		ll_root = (LinearLayout) findViewById(R.id.ll_home_root);
		lv_show = (ListView) findViewById(R.id.lv_home_activity_show);
	}
}
