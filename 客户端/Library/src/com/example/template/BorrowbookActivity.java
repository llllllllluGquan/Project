package com.example.template;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.example.template.MyUserInfoActivity.UpdateAddress;
import com.library.lgq.custom.SearchViewAdapter;
import com.library.lgq.listviewReflash.ReFlashListView;
import com.library.lgq.listviewReflash.ReFlashListView.IReflashListener;
import com.library.lgq.vo.Book;
import com.zhy.weixin6.ui.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BorrowbookActivity extends Activity implements IReflashListener {

	private ReFlashListView mListView;
	private ImageButton back;
	private SearchViewAdapter adapter;
	private String username;
	private List<Book> list=new ArrayList<Book>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_borrowbook);

		username=Login.preferences.getString("GLOBAL_USERNAME", "");
		new MyTaskQureyBook().execute(Info.path13);
		back=(ImageButton) findViewById(R.id.btn_borrowbook_back);
		mListView=(ReFlashListView) findViewById(R.id.lv_borrowbook);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(BorrowbookActivity.this, MainActivity.class);
				startActivity(intent);
				BorrowbookActivity.this.finish();
			}
		});

	}

	@SuppressWarnings("static-access")
	private void initData() {
		// TODO Auto-generated method stub
		if(adapter==null){
			mListView.setInterface(this);
			adapter=new SearchViewAdapter(list, BorrowbookActivity.this);
			mListView.setAdapter(adapter);
		}else{
			adapter.mList=list;
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onReflash() {
		// TODO Auto-generated method stub
		new AsyncTask<String, Void, String>(){

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

				}else{
					list=getJsonString(result);
					mListView.reflashComplete();
					initData();
				}
			}
		}.execute(Info.path13);
	}

	/**
	 * 查书
	 * @param result
	 * @return
	 */
	private List<Book> getJsonString(String result){
		List<Book> temp=new ArrayList<Book>();
		Book book;
		try {
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++){
				JSONObject object=array.getJSONObject(i);
				book=new Book(object.getString("bookname")
						,object.getString("bookauthor")
						,object.getString("bookmargin")
						,object.getString("booknum")
						,object.getString("bookstate")
						,object.getString("bookpublic"));
				temp.add(book);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	class MyTaskQureyBook extends AsyncTask<String, Void, String>{

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

			}else{
				list=getJsonString(result);
				initData();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	/**
	 * iscanBorrow
	 * @author Smart
	 *
	 */
	private void borrowbookdialog(final int position){

		LayoutInflater inflater=LayoutInflater.from(BorrowbookActivity.this);
		RelativeLayout layout=(RelativeLayout) inflater.inflate(R.layout.dialog_borrowbook, null);

		final Dialog dialog=new AlertDialog.Builder(BorrowbookActivity.this).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(layout);

		TextView re_info=(TextView) layout.findViewById(R.id.dialog_borrow_bookinfo);
		re_info.setText("您借阅的是："+list.get(position).getBook_name()+"       目前剩余"+list.get(position).getBook_margin()+" 本");

		Button ok=(Button) layout.findViewById(R.id.dialog_borrow_ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//修改借阅的数量  修改图书的数量  更新user_book表
				new updateinfo().execute(Info.path15+username+"~"+Info.path16+list.get(position).getBook_num()+"~"+Info.path17+"update&username="+username+"&booknum="+list.get(position).getBook_num()+"&bookname="+list.get(position).getBook_name());
				dialog.dismiss();
			}
		});

		Button cancle= (Button) layout.findViewById(R.id.dialog_borrow_cancle);
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				dialog.dismiss();
			}
		});

	}
	class iscanBorrowBook extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String[] info=params.toString().split(";");
			String path=info[0];
			String result=HttpUtils.sendPostMethod(path, "utf-8");
			return result+";"+info[1];
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String[] info=result.toString().split(";");
			if(info[0].equals("yes")){
				borrowbookdialog(Integer.parseInt(info[1]));
			}else{
				Toast.makeText(BorrowbookActivity.this, "不符合借书条件", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 修改书的数量 修改借阅的数量  更新user_book数据
	 * @author Smart
	 *
	 */
	class updateinfo extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String[] info=params.toString().split("~");
			String path1=info[0];
			String path2=info[1];
			String path3=info[2];
			String result1=HttpUtils.sendPostMethod(path1, "utf-8");
			String result2=HttpUtils.sendPostMethod(path2, "utf-8");
			String result3=HttpUtils.sendPostMethod(path3, "utf-8");
			if(result1.equals("success")&&result2.equals("success")&&result3.equals("success")){
				return "success";
			}else{
				return "fail";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(BorrowbookActivity.this, "借阅成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(BorrowbookActivity.this, "借阅失败", Toast.LENGTH_SHORT).show();
			}
		}

	}
}
