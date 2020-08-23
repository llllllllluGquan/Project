package com.example.template;

import com.example.lgq.guide.GuideActivity;
import com.example.lgq.login.Welcome;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {

	private SharedPreferences preferences;
	private int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zym);
		isFistInstal();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
	}

	@SuppressLint("CommitPrefEdits")
	private void isFistInstal() {
		// TODO Auto-generated method stub
		preferences=getSharedPreferences("count", MODE_PRIVATE);
		count=preferences.getInt("count", 0);
		if(count==0){
			SharedPreferences.Editor editor=preferences.edit();
			editor.putInt("count", ++count);
			editor.commit();
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, GuideActivity.class);
			startActivity(intent);
			MainActivity.this.finish();
		}else{
			//跳到线程页面,然后到主页面.
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, Welcome.class);
			startActivity(intent);
			MainActivity.this.finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
