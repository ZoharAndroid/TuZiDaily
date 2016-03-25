package com.zzh.tuzidaily.activity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.zzh.tuzidaily.R;


public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
	}
}
