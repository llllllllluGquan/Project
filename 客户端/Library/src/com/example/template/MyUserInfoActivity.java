package com.example.template;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.library.lgq.vo.Userinfo;
import com.zhy.weixin6.ui.MainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyUserInfoActivity extends Activity implements OnClickListener{

	private List<Userinfo> list=new ArrayList<Userinfo>();
	private ImageView iv_back;
	private RelativeLayout re_myinfo_avatar;
	private RelativeLayout re_myinfo_name;
	private RelativeLayout re_myinfo_address;
	private RelativeLayout re_myinfo_sex;
	private RelativeLayout re_myinfo_region;
	private RelativeLayout re_myinfo_sign;
	private TextView tv_myinfo_name;
	private TextView tv_myinfo_id;
	private TextView tv_myinfo_address;
	private TextView tv_myinfo_sex;
	private TextView tv_myinfo_area;
	private TextView tv_myinfo_sign;
	private String username;
	
	private String curr_address;
	private String curr_sign;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myinfo);
		username=Login.preferences.getString("GLOBAL_USERNAME", "");
		if(!username.equals("")&&username!=null){
			new MyAsyncTask().execute(Info.path8+username);
		}else{
			Toast.makeText(MyUserInfoActivity.this, "系统错误", Toast.LENGTH_SHORT).show();
		}
		initView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
			Intent intent=new Intent();
			intent.setClass(MyUserInfoActivity.this, MainActivity.class);
			startActivity(intent);
			MyUserInfoActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
		
	}
	/**
	 * 得到组件
	 */
	public void initView(){
		iv_back=(ImageView) findViewById(R.id.iv_myinfo_back);
		
		re_myinfo_avatar=(RelativeLayout) findViewById(R.id.re_myinfo_avatar);
		re_myinfo_name=(RelativeLayout) findViewById(R.id.re_myinfo_name);
		re_myinfo_address=(RelativeLayout) findViewById(R.id.re_myinfo_address);
		re_myinfo_sex=(RelativeLayout) findViewById(R.id.re_myinfo_sex);
		re_myinfo_region=(RelativeLayout) findViewById(R.id.re_myinfo_region);
		re_myinfo_sign=(RelativeLayout) findViewById(R.id.re_myinfo_sign);
		
		tv_myinfo_name=(TextView) findViewById(R.id.tv_myinfo_name);
		tv_myinfo_id=(TextView) findViewById(R.id.tv_myinfo_id);
		tv_myinfo_address=(TextView) findViewById(R.id.tv_myinfo_address);
		tv_myinfo_sex=(TextView) findViewById(R.id.tv_myinfo_sex);
		tv_myinfo_area=(TextView) findViewById(R.id.tv_myinfo_area);
		tv_myinfo_sign=(TextView) findViewById(R.id.tv_myinfo_sign);
		
		iv_back.setOnClickListener(this);
		re_myinfo_avatar.setOnClickListener(this);
		re_myinfo_address.setOnClickListener(this);
		re_myinfo_name.setOnClickListener(this);
		re_myinfo_sex.setOnClickListener(this);
		re_myinfo_region.setOnClickListener(this);
		re_myinfo_sign.setOnClickListener(this);
	}
	/**
	 * show Data
	 */
	public void initData(){
		tv_myinfo_name.setText(list.get(0).getAnother_name());
		tv_myinfo_id.setText(list.get(0).getUsername());
		tv_myinfo_address.setText(list.get(0).getAddress());
		tv_myinfo_sex.setText(list.get(0).getSex());
		tv_myinfo_area.setText(list.get(0).getArea());
		tv_myinfo_sign.setText(list.get(0).getSignature());
	}

	/**
	 * 
	 */
	public void showsexdialog(){

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        LinearLayout ll_title = (LinearLayout) window
                .findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("性别");
        // 为确认按钮添加事件,执行退出应用操作
        final TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("男");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {
            	String str=tv_paizhao.getText().toString().trim();
            	new UpdateSex().execute(Info.path12+username+"&sex="+str);
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("女");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String str=tv_paizhao.getText().toString().trim();
            	new UpdateSex().execute(Info.path12+username+"&sex="+str);
                dlg.cancel();
            }
        });
	}
	public void showPhotodialog(){

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("拍照");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {

                @SuppressWarnings("unused")
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("相册");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                dlg.cancel();
            }
        });
	}
	
	public void showAddressdialog(final String address){
		LayoutInflater inflater=LayoutInflater.from(MyUserInfoActivity.this);
		RelativeLayout layout=(RelativeLayout) inflater.inflate(R.layout.dialog_address, null);
		
		final Dialog dialog=new AlertDialog.Builder(MyUserInfoActivity.this).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(layout);
		
		EditText re_info=(EditText) layout.findViewById(R.id.dialog_addr_msg);
		re_info.setText(address);
		
		Button ok=(Button) layout.findViewById(R.id.dialog_addr_ok);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new UpdateAddress().execute(Info.path10+username+"&address="+address);
				dialog.dismiss();
			}
		});
		
		Button cancle= (Button) layout.findViewById(R.id.dialog_addr_cancle);
		cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog.dismiss();
			}
		});
	}
	
	public void showSigndialog(final String sign){
		LayoutInflater inflater=LayoutInflater.from(MyUserInfoActivity.this);
		RelativeLayout layout=(RelativeLayout) inflater.inflate(R.layout.dialog_sign, null);
		
		final Dialog dialog=new AlertDialog.Builder(MyUserInfoActivity.this).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(layout);
		
		EditText re_info=(EditText) layout.findViewById(R.id.dialog_sign_msg);
		re_info.setText(sign);
		
		Button ok=(Button) layout.findViewById(R.id.dialog_sign_ok);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new UpdateSign().execute(Info.path11+username+"&signature="+sign);
				dialog.dismiss();
			}
		});
		
		Button cancle= (Button) layout.findViewById(R.id.dialog_sign_cancle);
		cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog.dismiss();
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.re_myinfo_avatar:
			showPhotodialog();
			break;
		case R.id.re_myinfo_name:
			System.out.println("lgq-----------click");
			Intent intent=new Intent(MyUserInfoActivity.this,RenameActivity.class);
			intent.putExtra("username", list.get(0).getAnother_name());
			startActivity(intent);
			MyUserInfoActivity.this.finish();
			break;
		case R.id.re_myinfo_address:
			curr_address=tv_myinfo_address.getText().toString().trim();
			showAddressdialog(curr_address);
			break;
		case R.id.re_myinfo_sex:
			showsexdialog();
			break;
		case R.id.re_myinfo_region:
			startActivity(new Intent(MyUserInfoActivity.this,ReareaBaiduMapActivity.class));
			MyUserInfoActivity.this.finish();
			break;
		case R.id.re_myinfo_sign:
			curr_sign=tv_myinfo_sign.getText().toString().trim();
			showSigndialog(curr_sign);
			break;
		case R.id.iv_myinfo_back:
			startActivity(new Intent(MyUserInfoActivity.this,MainActivity.class));
			MyUserInfoActivity.this.finish();
			break;

		default:
			break;
		}
		
	}
	/**
	 * 
	 * @param result
	 * @return
	 */
	public List<Userinfo> JsonString(String result){
		List<Userinfo> temp=new ArrayList<Userinfo>();
		Userinfo userinfo;
		try {
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++){
				JSONObject jsonObject=array.getJSONObject(i);
				userinfo=new Userinfo(jsonObject.getString("username")
						,jsonObject.getString("another_name")
						,jsonObject.getString("signature")
						,jsonObject.getString("area")
						,jsonObject.getString("sex")
						,jsonObject.getString("address"));
				temp.add(userinfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return temp;
	}
	/**
	 * 初始化
	 * @author Smart
	 *
	 */
	class MyAsyncTask extends AsyncTask<String, Void, String>{

		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			list=JsonString(result);
			initData();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}
		
	}
	
	/**
	 * 修改性别
	 * @author Smart
	 *
	 */
	class UpdateSex extends AsyncTask<String, Void, String>{

		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(MyUserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(MyUserInfoActivity.this, "修改失败,稍后重试.", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}
		
	}
	
	/**
	 * 修改家庭住址
	 * @author Smart
	 *
	 */
	class UpdateAddress extends AsyncTask<String, Void, String>{
		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(MyUserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(MyUserInfoActivity.this, "修改失败,稍后重试.", Toast.LENGTH_SHORT).show();
			}
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String  reslut=HttpUtils.sendPostMethod(params[0], "utf-8");
			return reslut;
		}
		
	}
	
	
	
	/**
	 * 修改个性签名
	 * @author Smart
	 *
	 */
	class UpdateSign extends AsyncTask<String, Void, String>{
		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(MyUserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(MyUserInfoActivity.this, "修改失败,稍后重试.", Toast.LENGTH_SHORT).show();
			}
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String  reslut=HttpUtils.sendPostMethod(params[0], "utf-8");
			return reslut;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
