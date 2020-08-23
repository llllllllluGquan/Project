package com.library.lgq.custom;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.example.template.R;
import com.library.lgq.vo.Seat;
import com.library.lgq.vo.User_book;
import com.zhy.weixin6.ui.Fragment_4;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReturnbookAdapter extends BaseAdapter{

	private List<User_book> list=new ArrayList<User_book>();
	private Context context;
	private String username;
	private List<User_book> books=new ArrayList<User_book>();
	
	public ReturnbookAdapter(List<User_book> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		username=Login.preferences.getString("GLOBAL_USERNAME", "");
	}

	public void onDataChange(List<User_book> list) {
		// TODO Auto-generated method stub
		this.list=list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewHolder holder;
		if(v==null){
			holder=new viewHolder();
			v=LayoutInflater.from(context).inflate(R.layout.dialog_returnbook_item_layout, null);
			holder.title=(TextView) v.findViewById(R.id.tv_dialog_returnbook_title);
			holder.num=(TextView) v.findViewById(R.id.tv_dialog_returnbook_num);
			holder.re=(Button) v.findViewById(R.id.btn_dialog_returnbook_return);
			v.setTag(holder);
		}else{
			holder=(viewHolder) v.getTag();
		}
		holder.title.setText(list.get(position).getBook_name());
		holder.num.setText(list.get(position).getBook_num());
		holder.re.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//启动退订的线程。 修改user_book 和user里面的can_borrow_book
//				new returnbook().execute(Info.path21+username+"&bookname="+list.get(position).getBook_name()+"&booknum="+list.get(position).getBook_num()+"&date="+list.get(position).getDate());
				String path1=Info.path21+username+"&bookname="+list.get(position).getBook_name()+"&booknum="+list.get(position).getBook_num()+"&date="+list.get(position).getDate();
				String path2=Info.path23+username;
				String path3=Info.path24+list.get(position).getBook_num();
				new returnbook().execute(path1+"~"+path2+"~"+path3);
//				Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
			}
		});
		return v;
	}

	class viewHolder{
		TextView title;
		TextView num;
		Button re;
	}

	/**
	 * 还书  user_book
	 * @author Smart
	 *
	 */
	class returnbook extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String[] info=params[0].split("~");
			String path1=info[0];
			String path2=info[1];
			String path3=info[2];
			String result1=HttpUtils.sendPostMethod(path1, "utf-8");
			if(result1.equals("success")){
				System.out.println("lgq"+1);
				String result2=HttpUtils.sendPostMethod(path2, "utf-8");
				if(result2.equals("success")){
					System.out.println("lgq"+2);
					String result3=HttpUtils.sendPostMethod(path3, "utf-8");
					if(result3.equals("success")){
						System.out.println("lgq"+3);
						return "success";
					}else{
						return "fail";
					}
				}else{
					return "fail";
				}
			}
			return "fail";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
				new getborrowbookinfo().execute(Info.path19+username);
			}else{
				Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
			}
//			System.out.println(result.toString());
		}
		
	} 
	
	
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
	
	class getborrowbookinfo extends AsyncTask<String, Void, String>{

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
				books=jsonString_return(result);
				if(books.size()!=0){
					onDataChange(books);
				}else{
					
				}
			}
		}
		
	}
}
