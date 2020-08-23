package com.example.template;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.library.lgq.custom.UIHelper_Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class RenameActivity extends Activity implements OnClickListener {

	private ImageView back;
	private TextView save;
	private String username;
	private String reusername;
	private String another_name;
	private EditText ed_rename_nickname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_update_nick);
		try {
			Intent intent=getIntent();
			another_name=intent.getExtras().getString("username");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		back=(ImageView) findViewById(R.id.iv_rename_back);
		save=(TextView) findViewById(R.id.tv_rename_save);
		ed_rename_nickname=(EditText) findViewById(R.id.ed_rename_nickname);
		if(another_name!=null){
			ed_rename_nickname.setText(another_name);
		}else{
			ed_rename_nickname.setText("稍后重试...");
			ed_rename_nickname.setTextColor(R.color.red);
		}
		save.setOnClickListener(this);
	}

	public void back(View v){
		startActivity(new Intent(RenameActivity.this,MyUserInfoActivity.class));
		RenameActivity.this.finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_rename_save:
			username=Login.preferences.getString("GLOBAL_USERNAME", "");
			reusername=ed_rename_nickname.getText().toString().trim();
			if(username!=null){
				new MyTask().execute(Info.path9+username+"&nickname="+reusername);
			}else{
				Toast.makeText(RenameActivity.this, "稍后重试...", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
	class MyTask extends AsyncTask<String, Void, String>{

		
		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			new UIHelper_Activity().showDialogForLoading(RenameActivity.this, "正在加载...", true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			UIHelper_Activity.hideDialogForLoading();
			if(result.equals("success")){
				Intent intent=new Intent();
				intent.setClass(RenameActivity.this, MyUserInfoActivity.class);
				startActivity(intent);
				RenameActivity.this.finish();
			}else{
				Toast.makeText(RenameActivity.this, "稍后重新操作...", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}
		
	}
}
