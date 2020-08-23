package com.zhy.weixin6.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.example.lgq.util.Util;
import com.example.template.MyUserInfoActivity;
import com.example.template.R;
import com.library.lgq.custom.SearchViewAdapter;
import com.library.lgq.vo.Book;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class Fragment_2 extends Fragment implements OnQueryTextListener  {


	private View mView;
	private ListView mListView;
	private SearchView mSearchView;
	private SearchManager manager;
	private SearchViewAdapter adapter;
	private List<Book> list=new ArrayList<Book>();

	private String username;
	static String[] DATA_COLLECTION;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(mView==null){
			mView=inflater.inflate(R.layout.fragment_2, container,false);
		}
		ViewGroup group=(ViewGroup) mView.getParent();
		if(group!=null){
			group.removeView(mView);
		}
		init();
		new MyTaskQureyBook().execute(Info.path13);
		return mView;
	}




	/**
	 * 
	 */
	private void init() {
		// TODO Auto-generated method stub
		manager=(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

		mListView=(ListView) mView.findViewById(R.id.search_list_view);
		mSearchView=(SearchView) mView.findViewById(R.id.show_search_view);
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
		
	}

	private void initData(){
		DATA_COLLECTION=new String[list.size()];
		for(int i=0;i<list.size();i++){
			DATA_COLLECTION[i]=list.get(i).getBook_name();
		}
		if(adapter==null){
			adapter=new SearchViewAdapter(list, getActivity());
			mListView.setAdapter(adapter);
		}else{
			adapter.mList=list;
			adapter.notifyDataSetChanged();
		}
	}
	
	private void borrowDialog(final int position){
		LayoutInflater inflater=LayoutInflater.from(getActivity());
		RelativeLayout layout=(RelativeLayout) inflater.inflate(R.layout.dialog_borrowbook, null);
		
		final Dialog dialog=new AlertDialog.Builder(getActivity()).create();
		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(layout);
		
		EditText re_info=(EditText) layout.findViewById(R.id.dialog_borrow_bookinfo);
		re_info.setText("您要借的书："+list.get(position).getBook_name());
		
		Button ok=(Button) layout.findViewById(R.id.dialog_borrow_ok);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username=Login.preferences.getString("GLOBAL_USERNAME", "");
				new updateBorrowInfo().execute(Info.path15+username+";"+Info.path16+list.get(position).getBook_num());
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
	

	//用户输入字符时激活该方法
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		System.out.println("newtext-----------------lgq"+newText);
		if(newText.equals("")){
			new MyTaskQureyBook().execute(Info.path13);
		}
		return false;
	}


	//单击搜索按钮是激活该方法
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		System.out.println("newtext-----------------lgq"+query);
		ArrayList<Book> data=new ArrayList<Book>();
		for(int i=0;i<DATA_COLLECTION.length;i++){
			if(DATA_COLLECTION[i].contains(query)){
				data.add(list.get(i));
			}
		}
		if(adapter==null){
			adapter=new SearchViewAdapter(data,getActivity());
			mListView.setAdapter(adapter);
		}else{
			adapter.mList=data;
			list=data;
			adapter.notifyDataSetChanged();
		}
		return true;
	}
	
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
	/**
	 * 
	 * @author Smart
	 *
	 */
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
	 * 是否可以借书
	 * @author Smart
	 *
	 */
	class isCanBorrow extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path[]=params.toString().split(";");
			String path1=path[0];
			String path2=path[1];
			String result=HttpUtils.sendPostMethod(path1, "utf-8");
			return result+";"+path2;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String[] res=result.split(";");
			String info=res[0];
			if(info.equals("yes")){
				borrowDialog(Integer.parseInt(res[1]));
			}else{
				Toast.makeText(getActivity(), "不符合结束条件", 1).show();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
	}

	
	class updateBorrowInfo extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String str[]=params.toString().split(";");
			String path1=str[0];
			String path2=str[1];
			String result1=HttpUtils.sendPostMethod(path1, "utf-8");
			String result2=HttpUtils.sendPostMethod(path2, "utf-8");
			if(result1.equals("success")&&result2.equals("success")){
				return "success";
			}
			return "fail";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(getActivity(), "借阅成功", 1).show();
			}else{
				Toast.makeText(getActivity(), "借阅失败...", 1).show();
			}
		}
		
	}
}
