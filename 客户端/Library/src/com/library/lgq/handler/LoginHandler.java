package com.library.lgq.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.lgq.util.Info;
import com.example.lgq.util.Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LoginHandler implements Runnable {

	private Handler handler;
	private String username;
	private String password;
	
	
	public LoginHandler(Handler handler, String username, String password) {
		super();
		this.handler = handler;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Map<String, String> params=new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		try {
			String result=Util.sendPostRequest(Info.path1, params, "utf-8");
			System.out.println("lgq---------"+result);
			Message msg=handler.obtainMessage();
			Bundle b=new Bundle();
			b.putString("info", result);
			msg.setData(b);
			msg.sendToTarget();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
