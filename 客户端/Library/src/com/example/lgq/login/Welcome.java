package com.example.lgq.login;

import com.example.template.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class Welcome extends Activity {

	private ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		mImageView=(ImageView) findViewById(R.id.img_wel);
		mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		new Thread(new welThread()).start();
	}

private class welThread implements Runnable{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(4000);
				//new Thread(new DataThread()).start();
				//System.out.println("查询线程启动");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			Intent intent=new Intent();
			intent.setClass(Welcome.this, Login.class);
			startActivity(intent);
			Welcome.this.finish();
		}
		
	}

}
