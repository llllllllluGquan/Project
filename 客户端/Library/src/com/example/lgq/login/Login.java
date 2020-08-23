package com.example.lgq.login;



import com.example.template.R;
import com.library.lgq.handler.LoginHandler;
import com.zhy.weixin6.ui.MainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	/**
	 * 用户名编辑框 etUsername
	 * 密码编辑框     etPassword
	 * 记住密码    rem_pwd
	 * 自动登录    auto_login
	 * 登录按钮    btn_login
	 * 版权超链接   tv_href
	 * 
	 */
	private EditText etUsername;
	private EditText etPassword;
	private CheckBox rem_pwd;
	private CheckBox auto_login;
	private Button btn_login;
	private TextView tv_href;
	private String username;
	private String password;
	public static SharedPreferences preferences;
	private Handler handler;
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		//获取实例对象
		init();
		
		handler=new Handler(){

			@SuppressLint("HandlerLeak")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Bundle bundle=msg.getData();
				String info=bundle.getString("info");
				if(info.equals("用户不存在")){
					Toast.makeText(Login.this,info, Toast.LENGTH_SHORT).show();
				}else if(info.equals("密码错误")){
					Toast.makeText(Login.this,info, Toast.LENGTH_SHORT).show();
				}else if(("恭喜"+username+"登陆成功").equals(info)){
					//保存用户名和密码
					Toast.makeText(Login.this,"登陆成功", Toast.LENGTH_SHORT).show();
					Editor editor=preferences.edit();
					editor.putString("GLOBAL_USERNAME", username);
					editor.putString("GLOBAL_PASSWORD", password);
					if(rem_pwd.isChecked()){
						editor.putString("USER_NAME", username);
						editor.putString("PASSWORD", password);
						editor.commit();
						Intent intent=new Intent(Login.this,MainActivity.class);
						startActivity(intent);
						Login.this.finish();
					}else{
						Intent intent=new Intent(Login.this,MainActivity.class);
						startActivity(intent);
						Login.this.finish();
					}
				}
			}
			
		};
		
		//判断记住密码多选框的状态
		if(preferences.getBoolean("ISCHECK", false)){
			//设置默认是记录迷茫状态
			rem_pwd.setChecked(true);
			etUsername.setText(preferences.getString("USER_NAME", ""));
			etPassword.setText(preferences.getString("PASSWORD", ""));
			//判断自动登录多选框状态
			if(preferences.getBoolean("AUTO_ISCHECK", false)){
				
				//设置默认是自动登录状态
				auto_login.setChecked(true);
				Intent intent=new Intent(Login.this,MainActivity.class);
				Login.this.startActivity(intent);
				Login.this.finish();
			}
		}
		
		//btn_login监听事件，在这设置默认用户名：zhny 密码：zhny
		btn_login.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("CommitPrefEdits")
			@Override
			public void onClick(View v) {
				username=etUsername.getText().toString().trim();
				password=etPassword.getText().toString().trim();
				if(!username.toString().trim().equals("")&&!password.toString().trim().equals("")){
//					Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
					new Thread(new LoginHandler(handler,username,password)).start();
				}else{
					Toast.makeText(Login.this, "用户名密码不能为空！", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		//记住密码
		rem_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(rem_pwd.isChecked()){
					
					System.out.println("记住密码选中");
					preferences.edit().putBoolean("ISCHECK", true).commit();
				}else{
					System.out.println("记住密码未被选中");
					preferences.edit().putBoolean("ISCHECK", false).commit();
				}
			}
		});
		
		//自动登录
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(auto_login.isChecked()){
					System.out.println("自动登录已选中");
					preferences.edit().putBoolean("AUTO_ISCHECK", true).commit();
				}else{
					System.out.println("自动登录未被选中");
					preferences.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});
	}
	private void init() {
		preferences=this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		etUsername=(EditText) findViewById(R.id.edit_username);
		etPassword=(EditText) findViewById(R.id.edit_pwd);
		rem_pwd=(CheckBox) findViewById(R.id.cb_remPwd);
		auto_login=(CheckBox) findViewById(R.id.cb_autoLogin);
		btn_login=(Button) findViewById(R.id.but_login);
		tv_href=(TextView) findViewById(R.id.tv_href);
		tv_href.setMovementMethod(LinkMovementMethod.getInstance());//超链接的关键
	}
}
