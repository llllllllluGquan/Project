package com.example.template;



import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ReareaBaiduMapActivity extends Activity {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocationClient client;
	private TextView tv_curr_position;
	private ImageView iv_map_back;
	private boolean isFirstLoc=true;
	private Mylocation mylocation=new Mylocation();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidumap);
		mMapView=(MapView) findViewById(R.id.bmapView);
		tv_curr_position=(TextView) findViewById(R.id.tv_curr_position);
		iv_map_back=(ImageView) findViewById(R.id.iv_map_back);
		iv_map_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ReareaBaiduMapActivity.this,MyUserInfoActivity.class));
				ReareaBaiduMapActivity.this.finish();
			}
		});
		mBaiduMap= mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);
		
		
		
		
		client=new LocationClient(this);
		LocationClientOption option=new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(false);
		option.setScanSpan(5000000);
		client.setLocOption(option);
		client.registerLocationListener((BDLocationListener) mylocation);
		client.start();
		
		LatLng ll=new LatLng(100, 100);
		MapStatus.Builder builder=new MapStatus.Builder();
		builder.target(ll).zoom(18.0f);
		mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}
	private class Mylocation implements BDLocationListener{

		
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if(location==null||mMapView==null){
				return ;
			}
			mBaiduMap.clear();
			MyLocationData locationData=new MyLocationData.Builder()
			.accuracy(location.getRadius())
			.direction(100)
			.latitude(location.getLatitude())
			.longitude(location.getLongitude())
			.build();
			mBaiduMap.setMyLocationData(locationData);
			
			LatLng lla=new LatLng(location.getLatitude(), location.getLongitude());
			MarkerOptions ooa=new MarkerOptions().position(lla).zIndex(9).draggable(true);
			mBaiduMap.addOverlay(ooa);
			
			if(isFirstLoc){
				isFirstLoc=false;
				LatLng ll=new LatLng(location.getLatitude(), location.getLongitude());
				MapStatus.Builder builder=new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			}
			
			String country=location.getCountry();
			String province=location.getProvince();
			String city=location.getCity();
			tv_curr_position.setText("µ±«∞Œª÷√:"+country+" "+province+" "+city);
		}


	}
}
