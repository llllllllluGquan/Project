package com.library.lgq.custom;

import java.util.ArrayList;
import java.util.List;

import com.example.template.R;
import com.library.lgq.vo.Seat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyReflashListViewAdapter extends BaseAdapter {

	private List<Seat> mList=new ArrayList<Seat>();
	private Context context;
	private LayoutInflater inflater;
	
	public MyReflashListViewAdapter(List<Seat> mList, Context context) {
		super();
		this.mList = mList;
		this.context = context;
	}
	public void onDataChange(List<Seat> list) {
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

	@SuppressWarnings("static-access")
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(v==null){
			holder=new ViewHolder();
			v=inflater.from(context).inflate(R.layout.listitem_seat, null);
			holder.tv_seatNum=(TextView) v.findViewById(R.id.tv_seatNum);
			holder.tv_principal=(TextView) v.findViewById(R.id.tv_principal);
			holder.tv_position=(TextView) v.findViewById(R.id.tv_position);
			holder.tv_state=(TextView) v.findViewById(R.id.tv_state);
			v.setTag(holder);
		}else{
			holder=(ViewHolder) v.getTag();
		}
		holder.tv_seatNum.setText("编号:"+mList.get(position).getSeatNum());
		holder.tv_principal.setText("负责人:"+mList.get(position).getPrincipal());
		holder.tv_position.setText("位置:"+mList.get(position).getPosition());
		if(mList.get(position).getState().equals("0")){
			holder.tv_state.setText("状态:"+"可訂");
			holder.tv_state.setTextColor(Color.GREEN);
		}else{
			holder.tv_state.setText("状态:"+"不可訂");
			holder.tv_state.setTextColor(Color.RED);
		}
		return v;
	}

	class ViewHolder{
		TextView tv_seatNum;
		TextView tv_principal;
		TextView tv_position;
		TextView tv_state;
		
	}

}
