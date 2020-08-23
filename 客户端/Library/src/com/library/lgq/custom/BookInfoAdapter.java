package com.library.lgq.custom;

import java.util.List;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.example.template.R;
import com.library.lgq.vo.User_book;
import com.zhy.weixin6.ui.Fragment_4;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookInfoAdapter extends BaseAdapter {

	private List<User_book> list;
	private Context context;
	private String username;
	public BookInfoAdapter(List<User_book> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		username=Login.preferences.getString("GLOBAL_USERNAME", "");
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

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View conterview, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewHolder holder;
		if(conterview==null){
			holder=new viewHolder();
			conterview=LayoutInflater.from(context).inflate(R.layout.dialog_book_item_layout, null);
			holder.img=(ImageView) conterview.findViewById(R.id.img_dialog_bookphoto);
			holder.title=(TextView) conterview.findViewById(R.id.tv_dialog_bookname);
			holder.end=(TextView) conterview.findViewById(R.id.tv_dialog_bookenddate);
			holder.date=(TextView) conterview.findViewById(R.id.tv_dialog_date);
			holder.num=(TextView) conterview.findViewById(R.id.tv_dialog_booknum);
			holder.extra=(Button) conterview.findViewById(R.id.btn_dialog_bookstate);
			conterview.setTag(holder);
		}else{
			holder=(viewHolder) conterview.getTag();
		}
		holder.title.setText(list.get(position).getBook_name());
		holder.end.setText("归还日期:"+list.get(position).getEnd_date());
		holder.end.setTextColor(R.color.red);
		holder.date.setText("借阅日期:"+list.get(position).getDate());
		holder.num.setText("图书编号:"+list.get(position).getBook_num());
		holder.extra.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new extraBook().execute(Info.path22+username+"&booknum="+list.get(position).getBook_num());
			}
		});
		return conterview;
	}

	class viewHolder{
		ImageView img;
		TextView title;
		TextView  end;
		TextView  date;
		TextView  num;
		Button  extra;
		
	}
	
	/**
	 * 续借课本
	 * @author Smart
	 *
	 */
	class extraBook extends AsyncTask<String, Void, String>{

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
			if(result.equals("续借成功")){
				Toast.makeText(context, "续借成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, "续借失败", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}
