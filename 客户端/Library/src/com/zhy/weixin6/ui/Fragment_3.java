package com.zhy.weixin6.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.template.R;
import com.library.lgq.custom.MyListViewAdapter;
import com.library.lgq.vo.News;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class Fragment_3 extends Fragment {

	
	private View mView;
	private ViewPager viewPager;
	private List<ImageView> imageViews;
	private String[] titles;
	private int[] imageResId;
	private List<View> dots;
	private TextView tv_title;
	private int currentItem=0;
	private ScheduledExecutorService scheduledExecutorService;
	private ListView listView;
	
	private List<News> list=new ArrayList<News>();
	
	//切换当前显示的图片
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(currentItem);
		}
		
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		 TODO Auto-generated method stub
		if(mView==null){
			mView=inflater.inflate(R.layout.fragment_3, container,false);
		}
		ViewGroup group=(ViewGroup) mView.getParent();
		if(group!=null){
			group.removeView(mView);
		}
		init();
		return mView;
	}


	private void init() {
		// TODO Auto-generated method stub
		imageResId=new int[] {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
		titles=new String[imageResId.length];
		titles[0] = "智能图书馆";
		titles[1] = "智能图书馆";
		titles[2] = "智能图书馆";
		titles[3] = "智能图书馆";
		titles[4] = "智能图书馆";
		imageViews = new ArrayList<ImageView>();
		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}
		dots = new ArrayList<View>();
		dots.add(mView.findViewById(R.id.v_dot0));
		dots.add(mView.findViewById(R.id.v_dot1));
		dots.add(mView.findViewById(R.id.v_dot2));
		dots.add(mView.findViewById(R.id.v_dot3));
		dots.add(mView.findViewById(R.id.v_dot4));
		tv_title = (TextView) mView.findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//
		viewPager = (ViewPager)mView.findViewById(R.id.vp_fragment_3);
		listView=(ListView) mView.findViewById(R.id.lv_fragment_3);
			
		list.add(new News("CCTV7-农广天地采访智慧农业重点实验室","智能图书馆"));//http://www.sdbhlc.com/pages/News/xwbd.jsp?id=105
		list.add(new News("智慧农业重点实验室学生在全国高校物联网应用创新大赛中斩获佳绩","智能图书馆"));//http://www.sdsmartag.com/display.asp?did=1229&id=2
		list.add(new News("智慧农业重点实验室参与中组部、农业部大学生村官示范培训","智能图书馆"));//http://www.sdsmartag.com/display.asp?did=1220&id=2
		list.add(new News("热烈祝贺智慧农业重点实验室王姗姗在2015中国农学会计算机农业应用分会做大会报告","智能图书馆"));//http://www.sdsmartag.com/display.asp?did=1093&id=2
		list.add(new News("互联网+三农：山东玉米数字化育种成功破题","智能图书馆"));//http://www.sdsmartag.com/display.asp?did=1002&id=2
		
		list.add(new News("三项“渤海粮仓”技术入选“十二五”科技创新成就展","智能图书馆"));//http://bhlc.sdau.edu.cn/pages/News/news.jsp?id=86
		list.add(new News("渤海粮仓小麦亩产连续三年创省纪录","渤海粮仓"));//http://bhlc.sdau.edu.cn/pages/News/news.jsp?id=85
		list.add(new News("河北省渤海粮仓科技示范工程重点示范县建设推进会在邢台威县召开","智能图书馆"));//http://bhlc.sdau.edu.cn/pages/News/news.jsp?id=84
		listView.setAdapter((ListAdapter) new MyListViewAdapter(list,getActivity()));
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
		//当在此页面的时候，每两秒钟切换一次图片
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * 换行切换任务
	 * 
	 * @author lgq
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}
	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author lgq
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author lgq
	 * 
	 */
	private class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageResId.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView((View) object);
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(imageViews.get(position));
			return imageViews.get(position);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			// TODO Auto-generated method stub
			super.restoreState(state, loader);
		}
		
	}
	
}
