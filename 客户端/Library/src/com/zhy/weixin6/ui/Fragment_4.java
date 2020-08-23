package com.zhy.weixin6.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.example.template.MyUserInfoActivity;
import com.example.template.R;
import com.example.template.SettingActivity;
import com.library.lgq.custom.BookInfoAdapter;
import com.library.lgq.custom.ReturnbookAdapter;
import com.library.lgq.vo.User_book;
import com.library.lgq.vo.User_seat;
import com.library.lgq.vo.Userinfo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Smart
 *
 */
public class Fragment_4 extends Fragment {

	private View mView;
	private RelativeLayout re_myinfo;
	private RelativeLayout re_seat;
	private RelativeLayout re_book;
	private RelativeLayout re_cancleseat;
	private RelativeLayout re_lendbook;
	private ImageView iv_userphoto;
	private TextView tv_name;
	private ImageView iv_sex;
	private TextView tv_id;
	private RelativeLayout re_setting;
	private String username;
	private List<Userinfo> list=new ArrayList<Userinfo>();

	private List<User_seat> seat_list=new ArrayList<User_seat>();
	private List<User_book> book_list=new ArrayList<User_book>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(mView==null){
			mView=inflater.inflate(R.layout.fragment_4, container,false);
		}
		ViewGroup group=(ViewGroup) mView.getParent();
		if(group!=null){
			group.removeView(mView);
		}
		initView();
		username=Login.preferences.getString("GLOBAL_USERNAME", "");
		new MyAsyncTaskUserinfo().execute(Info.path8+username);
		setlistener();
		return mView;
	}

	public void setlistener(){
		re_myinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("userinfo", (Serializable)list);
				intent.setClass(getActivity(), MyUserInfoActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		re_seat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//启动线程给看是否进行了占座,若是占座了 则显示占座信息，若没有则Toast一下
				new querySeatInfo().execute(Info.path18+username);
			}
		});

		re_book.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new queryBookInfo().execute(Info.path19+username);
			}
		});

		re_cancleseat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new debookSeat().execute(Info.path20+username);
			}
		});

		re_lendbook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new returnbook().execute(Info.path19+username);
//				returnbookdialog();
				
			}
		});

		re_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),SettingActivity.class));
				getActivity().finish();
			}
		});
	}
	
	/**
	 * 退订图书的对话框
	 * @param list
	 */
	@SuppressWarnings("static-access")
	private void returnbookdialog(List<User_book> list){
		

		System.out.println("lgq----"+list.size());
		LinearLayout layout=(LinearLayout) getActivity().getLayoutInflater().from(getActivity()).inflate(R.layout.dialog_returnbook, null);
		ListView listView=(ListView) layout.findViewById(R.id.lv_returnbook);
		ReturnbookAdapter adapter=new ReturnbookAdapter(list, getActivity());
		listView.setAdapter(adapter);

		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle("退订图书")
		.setView(layout)
		.setPositiveButton("关闭", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.show();
	}
	/**
	 * 初始化控件
	 */
	public void initView(){
		re_myinfo=(RelativeLayout) mView.findViewById(R.id.re_myinfo);
		re_seat=(RelativeLayout) mView.findViewById(R.id.re_seat);
		re_book=(RelativeLayout) mView.findViewById(R.id.re_book);
		re_cancleseat=(RelativeLayout) mView.findViewById(R.id.re_cancleseat);
		re_lendbook=(RelativeLayout) mView.findViewById(R.id.re_lendbook);
		re_setting=(RelativeLayout) mView.findViewById(R.id.re_setting);
		iv_userphoto=(ImageView) mView.findViewById(R.id.iv_userphoto);
		tv_name=(TextView) mView.findViewById(R.id.tv_username);
		iv_sex=(ImageView) mView.findViewById(R.id.iv_usersex);
		tv_id=(TextView) mView.findViewById(R.id.tv_id);
	}
	public void initData(){
		tv_name.setText(list.get(0).getAnother_name());
		tv_name.setTextSize(16);
		tv_id.setText("账号："+list.get(0).getUsername());
		tv_id.setTextSize(14);
		if(list.get(0).getSex().equals("男")){
			iv_sex.setBackgroundResource(R.drawable.ic_sex_male);
			iv_sex.setVisibility(View.VISIBLE);
		}else{
			iv_sex.setBackgroundResource(R.drawable.ic_sex_female);
			iv_sex.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 显示占座的信息
	 */
	@SuppressWarnings("static-access")
	private void showSeatInfo(List<User_seat> list){
		RelativeLayout layout=(RelativeLayout) getActivity().getLayoutInflater().from(getActivity()).inflate(R.layout.dialog_seatinfo, null);
		final TextView tv_seatinfo=(TextView) layout.findViewById(R.id.dialog_seatinfo_msg);
		tv_seatinfo.setText(list.get(0).getCurr_position()+"   座位编号:"+list.get(0).getCurr_seatNum());
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle("占座信息")
		.setView(layout)
		.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.show();
	}

	/**
	 * 显示借阅的信息
	 */
	@SuppressWarnings("static-access")
	private void showborrowinfo(List<User_book> list){

		LinearLayout layout=(LinearLayout) getActivity().getLayoutInflater().from(getActivity()).inflate(R.layout.dialog_bookinfo, null);
		ListView listView=(ListView) layout.findViewById(R.id.dialog_bookinfo_lv);
		BookInfoAdapter adapter=new BookInfoAdapter(list, getActivity());
		listView.setAdapter(adapter);

		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle("借阅信息")
		.setView(layout)
		.setPositiveButton("关闭", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.show();


	}

	/**
	 * 退订座位
	 */
	private void showCancleSeat(){

	}

	/**
	 * 退订的图书
	 */
	private void showCancleBook(){

	}
	/**
	 * 个人用户信息
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
	 * 查询个人信息
	 * @author Smart
	 */
	class MyAsyncTaskUserinfo extends AsyncTask<String, Void, String>{


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

	private List<User_seat> JsonString_seat(String result){
		List<User_seat> temp=new ArrayList<User_seat>();
		User_seat seat;
		try {
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++){
				JSONObject jsonObject=array.getJSONObject(i);
				seat=new User_seat(
						jsonObject.getString("username")
						,jsonObject.getString("curr_seatNum")
						,jsonObject.getString("curr_position"));
				temp.add(seat);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	/**
	 * 个人的占座信息
	 * @author Smart
	 *
	 */
	class querySeatInfo extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result.equals("fail")){
				seat_list=JsonString_seat(result);
				if(seat_list.size()!=0){
					showSeatInfo(seat_list);
				}else{
					Toast.makeText(getActivity(), "您当前没有订座", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getActivity(), "您当前没有订座", Toast.LENGTH_SHORT).show();
			}
		}

	}

	private List<User_book> JsonString_book(String result){
		List<User_book> temp=new ArrayList<User_book>();
		User_book book;
		try {
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++){
				JSONObject jsonObject=array.getJSONObject(i);
				book=new User_book(
						jsonObject.getString("username")
						,jsonObject.getString("bookname")
						,jsonObject.getString("booknum")
						,jsonObject.getString("date")
						,jsonObject.getString("enddate"));
				temp.add(book);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return temp;
	}
	/**
	 * 查看个人借阅的书
	 * @author Smart
	 *
	 */
	class queryBookInfo extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!result.equals("fail")){
				book_list=JsonString_book(result);
				if(book_list.size()!=0){
					System.out.println("---lgq"+book_list.get(0).getBook_name());
					showborrowinfo(book_list);
				}else{
					Toast.makeText(getActivity(), "没有查到您借阅的图书", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getActivity(), "没有查到您借阅的图书", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	class debookSeat extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("fail")){
				Toast.makeText(getActivity(), "退订失败，请稍候重试...", Toast.LENGTH_SHORT).show();
			}else if(result.equals("success")){
				Toast.makeText(getActivity(), "退订成功，欢迎下次使用...", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	/**
	 * 归还图书
	 * @author Smart
	 *
	 */
	
	private List<User_book> jsonString_return(String result){
		List<User_book> temp=new ArrayList<User_book>();
		User_book book;
		try {
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++){
				JSONObject object=array.getJSONObject(i);
				book =new User_book(
						object.getString("username")
						,object.getString("bookname")
						,object.getString("booknum")
						,object.getString("date")
						,object.getString("enddate"));
				temp.add(book);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	class returnbook extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.endsWith("fail")){
				
			}else{
				book_list=jsonString_return(result);
				if(book_list.size()!=0){
					returnbookdialog(book_list);
				}else{
					
				}
			}
		}
		
	}
}
