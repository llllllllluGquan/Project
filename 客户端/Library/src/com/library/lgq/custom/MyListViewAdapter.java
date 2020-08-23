package com.library.lgq.custom;

import java.util.List;

import com.example.template.R;
import com.library.lgq.vo.News;


import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {

	private List<News> mList;
	private Context context;
	private LayoutInflater layoutInflater;
	
	public MyListViewAdapter(List<News> mList, Context context) {
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

	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler hodler=null;
		if(convertView==null){
			hodler=new ViewHodler();
			convertView=layoutInflater.from(context).inflate(R.layout.list_news, null);
			hodler.imageView=(ImageView) convertView.findViewById(R.id.img_title_image);
			hodler.title=(TextView) convertView.findViewById(R.id.tv_titles);
			hodler.author=(TextView) convertView.findViewById(R.id.tv_public);
			convertView.setTag(hodler);
		}else {
			hodler=(ViewHodler) convertView.getTag();
		}
//		String html=mList.get(position).getNews_title()+"\n"+"http://www.sdbhlc.com/pages/News/xwbd.jsp?id=105";
//		CharSequence charSequence=Html.fromHtml(html);
		hodler.imageView.setBackgroundResource(R.drawable.title);
		hodler.title.setText(mList.get(position).getNews_title());
//		hodler.title.setMovementMethod(LinkMovementMethod.getInstance());		
		hodler.author.setText(mList.get(position).getAuthor());
		return convertView;
	}

	class ViewHodler{
		public ImageView imageView;
		public TextView title;
		public TextView author;
	}
}
