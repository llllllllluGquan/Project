package com.library.lgq.custom;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

import com.example.template.R;
import com.example.template.SeatActivity;
import com.library.lgq.vo.Temp_db;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyHistoryAdapter extends BaseAdapter {

	private List<Temp_db> mList=new ArrayList<Temp_db>();
	private Context context;
	private LayoutInflater layoutInflater;
	
	public MyHistoryAdapter(List<Temp_db> mList, Context context) {
		super();
		this.mList = mList;
		this.context = context;
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

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if(convertView==null){
			holder= new ViewHolder();
			convertView=layoutInflater.from(context).inflate(R.layout.list_history, null);
			holder.textView=(TextView) convertView.findViewById(R.id.tv_history);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mList.get(position).getDate()+"   "+mList.get(position).getHistory());
		holder.textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String flow=holder.textView.getText().toString().substring(0,2);
				String position=holder.textView.getText().toString().substring(2,4);
				Intent intent=new Intent();
				intent.putExtra("infomation", "condition");
				intent.putExtra("position", flow+position);
				intent.setClass(context, SeatActivity.class);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder{
		TextView textView;
	}
}
