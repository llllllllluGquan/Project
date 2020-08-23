package com.library.lgq.custom;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.lbsapi.auth.m;
import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.example.template.BorrowbookActivity;
import com.example.template.R;
import com.library.lgq.vo.Book;
import com.library.lgq.vo.User_book;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchViewAdapter extends BaseAdapter {

	public static List<Book> mList;
	private Context context;
	private String username;

	@SuppressWarnings("static-access")
	public SearchViewAdapter(List<Book> mList, Context context) {
		super();
		this.mList = mList;
		this.context = context;
		username=Login.preferences.getString("GLOBAL_USERNAME", "");
	}

	public void onDataChange(List<Book> list) {
		// TODO Auto-generated method stub
		this.mList=list;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.book_item_layout, null);
			viewHolder.tv_title=(TextView) convertView.findViewById(R.id.tv_bookname);
			viewHolder.tv_public=(TextView) convertView.findViewById(R.id.tv_bookpublic);
			viewHolder.tv_count=(TextView) convertView.findViewById(R.id.tv_bookcount);
			viewHolder.tv_num=(TextView) convertView.findViewById(R.id.tv_booknum);
			viewHolder.btn_state=(Button) convertView.findViewById(R.id.btn_bookstate);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.tv_title.setText(mList.get(position).getBook_name());
		viewHolder.tv_public.setText(mList.get(position).getBook_public()+"  "+mList.get(position).getBook_author()+"著");
		viewHolder.tv_count.setText("剩余 "+mList.get(position).getBook_margin()+" 本");
		viewHolder.tv_num.setText("书目编号："+mList.get(position).getBook_num());
		if(mList.get(position).getBook_state().equals("yes")){
			viewHolder.btn_state.setText("可借");
			viewHolder.btn_state.setBackgroundResource(R.drawable.btn1_style_green);
			viewHolder.btn_state.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new iscanBorrowBook().execute(Info.path14+username+";"+position);
				}
			});
		}else if(mList.get(position).getBook_state().equals("no")){
			viewHolder.btn_state.setText("不可借");
		}
		return convertView;
	}

	class ViewHolder{

		ImageView img_bookphoto;
		TextView tv_title;
		TextView tv_public;
		TextView tv_count;
		Button btn_state;
		TextView tv_num;
	}

	/**
	 * iscanBorrow
	 * @author Smart
	 *
	 */
	private void borrowbookdialog(final int position){

		LayoutInflater inflater=LayoutInflater.from(context);
		RelativeLayout layout=(RelativeLayout) inflater.inflate(R.layout.dialog_borrowbook, null);

		final Dialog dialog=new AlertDialog.Builder(context).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(layout);

		TextView re_info=(TextView) layout.findViewById(R.id.dialog_borrow_bookinfo);
		re_info.setText("您借阅的是："+mList.get(position).getBook_name()+"       目前剩余"+mList.get(position).getBook_margin()+" 本");

		Button ok=(Button) layout.findViewById(R.id.dialog_borrow_ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//修改借阅的数量  修改图书的数量  更新user_book表
				new updateinfo().execute(Info.path15+username+"~"+Info.path16+mList.get(position).getBook_num()+"~"+Info.path17+"update&username="+username+"&booknum="+mList.get(position).getBook_num()+"&bookname="+mList.get(position).getBook_name());
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
			String[] info=params[0].toString().split(";");
			String path=info[0];
			System.out.println("lgq+++++++path"+path);
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
				Toast.makeText(context, "不符合借书条件", Toast.LENGTH_SHORT).show();
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
			String[] info=params[0].toString().split("~");
			String path1=info[0];
			String path2=info[1];
			String path3=info[2];
			System.out.println("lgq-------path1"+info[0]);
			System.out.println("lgq-------path2"+info[1]);
			System.out.println("lgq-------path3"+info[2]);
			String result1=HttpUtils.sendPostMethod(path1, "utf-8");
			if(result1.equals("success")){
				System.out.println("lgq----1");
				String result2=HttpUtils.sendPostMethod(path2, "utf-8");
				if(result2.equals("success")){
					System.out.println("lgq----2");
					String result3=HttpUtils.sendPostMethod(path3, "utf-8");
					if(result3.equals("success")){
						System.out.println("lgq----3");
						return "success";
					}else{
						return "fail";
					}
				}else{
					return "fail";
				}
			}else{
				return "fail";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(context, "借阅成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, "借阅失败", Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	/**
	 * 更新书目信息
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
				mList=getJsonString(result);
				onDataChange(mList);
			}
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}
}
