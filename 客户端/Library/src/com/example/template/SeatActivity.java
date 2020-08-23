package com.example.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.lgq.login.Login;
import com.example.lgq.util.HttpUtils;
import com.example.lgq.util.Info;
import com.example.lgq.util.WifiName;
import com.library.lgq.custom.MyReflashListViewAdapter;
import com.library.lgq.custom.UIHelper;
import com.library.lgq.custom.UIHelper_Activity;
import com.library.lgq.listviewReflash.ReFlashListView;
import com.library.lgq.listviewReflash.ReFlashListView.IReflashListener;
import com.library.lgq.vo.Seat;
import com.zhy.weixin6.ui.MainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SeatActivity extends Activity implements IReflashListener{

	private ReFlashListView mListView;
	private List<Seat> list=new ArrayList<Seat>();
	private MyReflashListViewAdapter adapter;
	private ImageButton  back;
	private AlertDialog.Builder builder;
	private String username=null;
	private String infomation;
	private String position;

	private Timer timer=new Timer();
	private Timer time=new Timer();
	private TimerTask task;
	private TimerTask interval;
	private static int SEAT_COUNT=3;
	private boolean isWifi;
	private String Wifiname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.seat);
		mListView=(ReFlashListView) findViewById(R.id.lv_seat);
		back = (ImageButton) findViewById( R.id.btn_back);
		//����ǰ��Ĳ���
		Intent intent=getIntent();
		infomation=intent.getExtras().getString("infomation");
		if(infomation.equals("ALL")){
			new MyAsyncTask().execute(Info.path2);
		}else{
			position=intent.getExtras().getString("position");
			new MyAsyncTask().execute(Info.path7+position);
		}
		isWifi=ifWifiConneected(SeatActivity.this);
		if(isWifi){
			Wifiname=getConnectWifiname();
		}
		back.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(SeatActivity.this, MainActivity.class);
				startActivity(intent);
				SeatActivity.this.finish();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				if(list.get(position-1).getState().equals("0")){
					//����Ƿ�����ռ������Ҫ
					//����֮ǰ�ȿ���user���can_book_seat��״̬�����yes��Ȼ��bookseat�����д��user_seat ��
					username=Login.preferences.getString("GLOBAL_USERNAME", "");
					new MyAsyncTaskIsBookseat().execute(Info.path3+"?username="+username+";"+(position-1));
				}
			}
		});
	}

	//�Ƿ�����wifi
	private boolean ifWifiConneected(Activity context) {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(wifiNetworkInfo.isConnected()){
			return true;
		}else{
			return false;
		}
	}
	//���wifi����
	private String getConnectWifiname() {
		WifiManager manager=(WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo=manager.getConnectionInfo();
		System.out.println("lgq_____wifiname"+ wifiInfo.getSSID());
		return wifiInfo.getSSID();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
			Intent intent=new Intent();
			intent.setClass(SeatActivity.this, MainActivity.class);
			startActivity(intent);
			SeatActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);

	}

	/**
	 * �������� �����Ի�����ʾ,���ȷ������ж�����ȡ���򲻽����κβ���
	 * @param position
	 */
	public void dialogshow(final int position){

		builder = new Builder(SeatActivity.this);
		builder.setTitle("��ʾ")
		.setMessage("��Ԥ����λ�ı��Ϊ:"+list.get(position).getSeatNum())
		.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				/**
				 * �޸�user���can_book_seat  user_seat���info   seat��state(seatNum)
				 */
				String path1=Info.path4+"?username="+username;
				String path2=Info.path5+"?username="+username+"&position="+list.get(position).getPosition()+"&seatNum="+list.get(position).getSeatNum();
				String path3=Info.path6+"?seatNum="+list.get(position).getSeatNum();
				String path=path1+";"+path2+";"+path3;
				new MyAsyncTaskBookseat().execute(path);
			}
		}).setNegativeButton("ȡ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Toast.makeText(SeatActivity.this, "��δ����", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		}).show();

	}
	/**
	 * ʵ����
	 */
	private void init() {
		// TODO Auto-generated method stub
		if(adapter==null){
			mListView.setInterface(this);
			adapter=new MyReflashListViewAdapter(list, SeatActivity.this);
			mListView.setAdapter(adapter);
		}else{
			adapter.onDataChange(list);
		}

	}
	/**
	 * ����ˢ�����¼�������
	 */
	private void reinit() {
		// TODO Auto-generated method stub
		adapter.onDataChange(list);
		mListView.reflashComplete();
	}
	/**
	 * ����ˢ�£������첽����
	 */
	@Override
	public void onReflash() {
		// TODO Auto-generated method stub
		if(infomation.equals("ALL")){
			new AsyncTask<String, Void, String>() {


				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					list=JsonString(result);
					reinit();
				}


				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();

				}

				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					String result=HttpUtils.sendPostMethod(params[0],"utf-8");
					return result;
				}

			}.execute(Info.path2);
		}else{
			new AsyncTask<String, Void, String>() {


				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					list=JsonString(result);
					reinit();
				}


				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();

				}

				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					String result=HttpUtils.sendPostMethod(params[0],"utf-8");
					return result;
				}

			}.execute(Info.path7+position);
		}
	}

	/**
	 * ����JSON
	 * @param result
	 * @return
	 */
	public List<Seat> JsonString(String result){
		List<Seat> temp=new ArrayList<Seat>();
		Seat seat;
		try {
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++){
				JSONObject object=array.getJSONObject(i);
				seat=new Seat(object.getString("seatNum")
						,object.getString("principal")
						,object.getString("state")
						,object.getString("position"));
				temp.add(seat);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
	}
	/**
	 * �첽����
	 * @author Smart
	 *
	 */
	class MyAsyncTask extends AsyncTask<String, Void, String>{


		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			UIHelper_Activity.hideDialogForLoading();
			list=JsonString(result);
			init();
		}

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			new UIHelper_Activity().showDialogForLoading(SeatActivity.this, "Ո�Ժ�...", true);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result=HttpUtils.sendPostMethod(params[0], "utf-8");
			return result;
		}

	}

	/**
	 * ����Ƿ����ռ��
	 * @author Smart
	 *
	 */
	class MyAsyncTaskIsBookseat extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path[]=params[0].split(";");
			String result=HttpUtils.sendPostMethod(path[0], "utf-8");
			return result+";"+path[1];
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if((result.split(";"))[0].equals("��������")){
				String temp[] = result.split(";");
				dialogshow(Integer.parseInt(temp[1]));
			}else{
				Toast.makeText(SeatActivity.this, "��������ռ������", Toast.LENGTH_SHORT).show();
			}
		}

	}

	/**
	 * �ڼ����Զ���֮�󣬽���ȷ����������дseat���state״̬ ͬʱ ����user_seat �����Info user���� can_book_seat ״̬
	 * @author Smart
	 *
	 */
	private void showdialog(int msg){
		System.out.println("lgq---count"+msg);
		AlertDialog.Builder builder=new Builder(SeatActivity.this);

		builder.setTitle("������λ����"+msg+"���Ӻ��Զ�ȡ��")
		.setIcon(R.drawable.ic_launcher)
		.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.show();
	}
	@SuppressLint("HandlerLeak")
	Handler handler2=new Handler(){

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(!getConnectWifiname().equals(WifiName.wifi)){
				new debookSeat().execute(Info.path20+username);
				time.cancel();
			}
		}
		
	};
	private void jiance(){
		interval=new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message=new Message();
				message.what=1;
				handler2.sendMessage(message);
			}
		};
		//task   1000*60*10  1000*60
		time.schedule(interval, 20000,20000);
	}
	@SuppressLint("HandlerLeak")
	Handler handler =new Handler(){

		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(SEAT_COUNT>0&&Wifiname!=null&&Wifiname.equals(WifiName.wifi)){
				System.out.println("lgq____"+"success");
				timer.cancel();
				Toast.makeText(SeatActivity.this, "��ʱ�ϵ��������ɹ�", 1).show();
				jiance();
				return;
			}else if(SEAT_COUNT>0&&Wifiname!=null&&!Wifiname.equals(WifiName.wifi)){
				System.out.println("lgq-----����360wifi     "+Wifiname);
				showdialog(SEAT_COUNT--);
			}else{
				//�Զ�����
				System.out.println("lgq___------����");
				new debookSeat().execute(Info.path20+username);
				timer.cancel();
			}

		}

	};
	//����
	class debookSeat extends AsyncTask<String, Void, String>{

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
				Toast.makeText(SeatActivity.this, "�˶�ʧ�ܣ����Ժ�����...", Toast.LENGTH_SHORT).show();
			}else if(result.equals("success")){
				Toast.makeText(SeatActivity.this, "������δ��ʱ�ϵ�����λ���Զ�ȡ��...", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	private void timertask(){
		task=new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message=new Message();
				message.what=1;
				handler.sendMessage(message);
			}
		};
		//task   1000*60*10  1000*60
		timer.schedule(task, 5000,6000);
	}
	class MyAsyncTaskBookseat extends AsyncTask<String, Void, String>{


		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals("success")){
				Toast.makeText(SeatActivity.this, "�����ɹ�", Toast.LENGTH_SHORT).show();
				timertask();
			}else{
				Toast.makeText(SeatActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path[]= params[0].split(";");
			String result1=HttpUtils.sendPostMethod(path[0], "utf-8");
			String result2=HttpUtils.sendPostMethod(path[1], "utf-8");
			String result3=HttpUtils.sendPostMethod(path[2], "utf-8");
			if(result1.equals("success")&&result2.equals("success")&& result3.equals("�����ɹ�")){
				return "success";
			}
			return "fail";
		}

	}
}
