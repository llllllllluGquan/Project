package com.example.template;

import com.example.lgq.login.Login;
import com.zhy.weixin6.ui.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class SettingActivity extends Activity {

	private Button btn_logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		btn_logout=(Button) findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SettingActivity.this,Login.class));
				SettingActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
			Intent intent=new Intent();
			intent.setClass(SettingActivity.this, MainActivity.class);
			startActivity(intent);
			SettingActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
		
	}
	
}
