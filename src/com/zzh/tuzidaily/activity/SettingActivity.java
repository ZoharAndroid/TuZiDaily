package com.zzh.tuzidaily.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.zzh.tuzidaily.R;

public class SettingActivity extends Activity {
	
	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initEvent();
	}

	private void initEvent() {
		//返回按钮
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//结束当前的界面
				finish();
			}
		});
	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		iv_back = (ImageView) findViewById(R.id.iv_setting_activity_back);
	}
}
