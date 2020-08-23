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
	
	//�л���ǰ��ʾ��ͼƬ
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
		titles[0] = "����ͼ���";
		titles[1] = "����ͼ���";
		titles[2] = "����ͼ���";
		titles[3] = "����ͼ���";
		titles[4] = "����ͼ���";
		imageViews = new ArrayList<ImageView>();
		// ��ʼ��ͼƬ��Դ
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
			
		list.add(new News("CCTV7-ũ����زɷ��ǻ�ũҵ�ص�ʵ����","����ͼ���"));//http://www.sdbhlc.com/pages/News/xwbd.jsp?id=105
		list.add(new News("�ǻ�ũҵ�ص�ʵ����ѧ����ȫ����У������Ӧ�ô��´�����ն��Ѽ�","����ͼ���"));//http://www.sdsmartag.com/display.asp?did=1229&id=2
		list.add(new News("�ǻ�ũҵ�ص�ʵ���Ҳ������鲿��ũҵ����ѧ�����ʾ����ѵ","����ͼ���"));//http://www.sdsmartag.com/display.asp?did=1220&id=2
		list.add(new News("����ף���ǻ�ũҵ�ص�ʵ����������2015�й�ũѧ������ũҵӦ�÷ֻ�����ᱨ��","����ͼ���"));//http://www.sdsmartag.com/display.asp?did=1093&id=2
		list.add(new News("������+��ũ��ɽ���������ֻ����ֳɹ�����","����ͼ���"));//http://www.sdsmartag.com/display.asp?did=1002&id=2
		
		list.add(new News("����������֡�������ѡ��ʮ���塱�Ƽ����³ɾ�չ","����ͼ���"));//http://bhlc.sdau.edu.cn/pages/News/news.jsp?id=86
		list.add(new News("��������С��Ķ���������괴ʡ��¼","��������"));//http://bhlc.sdau.edu.cn/pages/News/news.jsp?id=85
		list.add(new News("�ӱ�ʡ�������ֿƼ�ʾ�������ص�ʾ���ؽ����ƽ�������̨�����ٿ�","����ͼ���"));//http://bhlc.sdau.edu.cn/pages/News/news.jsp?id=84
		listView.setAdapter((ListAdapter) new MyListViewAdapter(list,getActivity()));
		viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������
		// ����һ������������ViewPager�е�ҳ��ı�ʱ����
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
		//���ڴ�ҳ���ʱ��ÿ�������л�һ��ͼƬ
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
	 * �����л�����
	 * 
	 * @author lgq
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
			}
		}

	}
	/**
	 * ��ViewPager��ҳ���״̬�����ı�ʱ����
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
	 * ���ViewPagerҳ���������
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
