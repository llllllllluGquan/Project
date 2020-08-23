package com.zhy.weixin6.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.MySQLite;
import com.example.template.R;
import com.example.template.SeatActivity;
import com.library.lgq.custom.MyHistoryAdapter;
import com.library.lgq.custom.UIHelper;
import com.library.lgq.vo.Temp_db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class Fragment_1 extends Fragment {

	private View mView;
	private ViewPager viewPager;
	private List<ImageView> imageViews;
	private String[] titles;
	private int[] imageResId;
	private List<View> dots;
	private TextView tv_title;
	private int currentItem=0;
	private ScheduledExecutorService scheduledExecutorService;
	
	private Spinner sp_flow;
	private Spinner sp_area;
	private Button btn_select;
	private TextView tv_wifi;
	private TextView tv_allseat;
	private ListView listView;
	private boolean isWificonnect;
	private String flow;
	private String area;
	private MySQLite helper;
	private SQLiteDatabase db;
	private static String TABLE_NAME="records";
	private static String COLUMN_NAME_DATE="date";
	private static String COLUMN_NAME_HISTORY="history";
	private List<Temp_db> list_history= new ArrayList<Temp_db>();
	private MyHistoryAdapter adapter;
	
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
		// TODO Auto-generated method stub
		if(mView==null){
			mView=inflater.inflate(R.layout.fragment_1, container,false);
		}
		ViewGroup group=(ViewGroup) mView.getParent();
		if(group!=null){
			group.removeView(mView);
		}
		helper=new MySQLite(getActivity());
		isWificonnect=ifWifiConneected(getActivity());
		init();
		setListen();
		return mView;
	}
	/**
	 * �ж��Ƿ�����wifi
	 * @param context
	 * @return
	 */
	private boolean ifWifiConneected(FragmentActivity context) {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(wifiNetworkInfo.isConnected()){
			return true;
		}else{
			return false;
		}
	}

	private void setListen() {
		// TODO Auto-generated method stub
		sp_flow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				flow=getActivity().getResources().getStringArray(R.array.flow)[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		sp_area.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				area=getActivity().getResources().getStringArray(R.array.area)[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btn_select.setOnClickListener(new OnClickListener() {
			Temp_db temp_db;
			@SuppressLint("SimpleDateFormat")
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String date=dateFormat.format(new Date());
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("lgq------btn");
				temp_db=new Temp_db(flow+area,date);
				saveContacct(temp_db);
				//������һ�����档
				Intent intent=new Intent();
				intent.putExtra("infomation", "condition");
				intent.putExtra("position", flow+area);
				intent.setClass(getActivity(), SeatActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		
		if(tv_wifi.getText().toString().trim().equals("�����ȡwifi")){
			tv_wifi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent =new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
					startActivity(intent);
				}
			});
		}
		tv_allseat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//������ѯ�߳�;SelectAllseathandler ,�������첽��
				Intent intent=new Intent();
				intent.putExtra("infomation", "ALL");
				intent.setClass(getActivity(),SeatActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
	}

	@SuppressLint("ResourceAsColor")
	private void init(){

		// TODO Auto-generated method stub
		sp_flow=(Spinner) mView.findViewById(R.id.sp_flow);
		sp_area=(Spinner) mView.findViewById(R.id.sp_area);
		btn_select=(Button) mView.findViewById(R.id.btn_select);
		tv_wifi=(TextView) mView.findViewById(R.id.tv_wifi);
		tv_allseat=(TextView) mView.findViewById(R.id.tv_seeallseat);
		listView=(ListView) mView.findViewById(R.id.lv_histroy);
		
		if(this.getContactList().size()>0){
			list_history=getContactList();
			adapter=new MyHistoryAdapter(list_history, getActivity());
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					// TODO Auto-generated method stub
					//��һ���Զ���Ի���
				}
				
			});
		}
		
		if(isWificonnect){
			String wifiName=getConnectWifiname();
			System.out.println("lgq---"+wifiName);
			tv_wifi.setText("wifi����:"+wifiName);
			tv_wifi.setTextColor(R.color.blue);
		}else{
			tv_wifi.setText("�����ȡwifi");
			tv_wifi.setTextColor(R.color.red);
		}
		
		
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
			
		viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������
		// ����һ������������ViewPager�е�ҳ��ı�ʱ����
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		helper=new MySQLite(getActivity());
		if(getContactList().size()>0){
			list_history=getContactList();
			adapter=new MyHistoryAdapter(list_history,getActivity());
			adapter.notifyDataSetChanged();
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
	}

	/**
	 * ��ȡ���ӵ�wifi����
	 * @return
	 */
	private String getConnectWifiname() {
		WifiManager manager=(WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo=manager.getConnectionInfo();
		System.out.println("lgq_____wifiname"+ wifiInfo.getSSID());
		return wifiInfo.getSSID();
	}
	
	/**
	 * ��������
	 * @author Smart
	 */
	public void saveContacct(Temp_db bottle){
		db=helper.getWritableDatabase();
		ContentValues values= new ContentValues();
		values.put(COLUMN_NAME_DATE, bottle.getDate());
		values.put(COLUMN_NAME_HISTORY, bottle.getHistory());
		if(db.isOpen()){
			db.replace(TABLE_NAME, null, values);
		}
	}
	/**
	 * 
	 * ��ȡ��������
	 * @author Smart
	 */
	public List<Temp_db> getContactList(){
		db=helper.getReadableDatabase();
		List<Temp_db> temp=new ArrayList<Temp_db>();
		List<Temp_db> list=new ArrayList<Temp_db>();
		Temp_db temp_db;
		if(db.isOpen()){
			System.out.println("lgq---------db.isopen()");
			Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null );
			while(cursor.moveToNext()){
				temp_db=new Temp_db(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE))
						,cursor.getString(cursor.getColumnIndex(COLUMN_NAME_HISTORY)));
				temp.add(temp_db);
			}
			for(int i=temp.size()-1;i>=0;i--){
				list.add(temp.get(i));
			}
		}
		db.close();
		return list;
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
	
	class MyAsynctask extends AsyncTask<String, Void, String>{

		//����̨��������ʱ���˷������ᱻ���ã�����������Ϊ�������ݵ��˷����У�ֱ�ӽ������ʾ��UI����ϡ�
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			UIHelper.hideDialogForLoading();
//			mTextView.setText(result);
		}

		//��execute()�����ú�����ִ�У�һ��������ִ�к�̨����ǰ��UI��һЩ���
		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			new UIHelper().showDialogForLoading(getActivity(), "������", true);
			super.onPreExecute();
		}

		//��onPreExecute()��ɺ�����ִ�У�����ִ�н�Ϊ��ʱ�Ĳ������˷�����������������ͷ��ؼ���������ִ�й����п��Ե���publishProgress���������½�����Ϣ
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			System.out.println("lgq"+result);
			return result;
		}
		
	}
	
	
}
