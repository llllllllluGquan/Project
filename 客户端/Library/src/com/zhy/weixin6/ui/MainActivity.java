package com.zhy.weixin6.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.template.BorrowbookActivity;
import com.example.template.R;
import com.library.lgq.custom.ChangeColorIconWithTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener
{
	private long exitTime=0;
	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setOverflowShowingAlways();
		getActionBar().setDisplayShowHomeEnabled(true);//setting Is showing application icon 'false'
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
	
		mViewPager.setOffscreenPageLimit(1);
		initDatas();

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime)>2000){
				Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime=System.currentTimeMillis();
			}else{
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	private void initDatas()
	{

			Fragment_1 f1 = new Fragment_1();
			Fragment_2 f2 = new Fragment_2();
			Fragment_3 f3 = new Fragment_3();
			Fragment_4 f4 = new Fragment_4();
			mTabs.add(f1);
			mTabs.add(f2);
			mTabs.add(f3);
			mTabs.add(f4);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mTabs.get(arg0);
			}
		};

		initTabIndicator();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem menu) {
		
		super.onOptionsItemSelected(menu);
		switch (menu.getItemId()) {
		case R.id.action_group_chat:
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, BorrowbookActivity.class);
			startActivity(intent);
			MainActivity.this.finish();
			break;

		default:
			break;
		}
		return true;
	}

	private void initTabIndicator(){
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		one.setIconAlpha(1.0f);
	}

	@Override
	public void onPageSelected(int arg0){
		
	}
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels){

		if (positionOffset > 0)
		{
			ChangeColorIconWithTextView left = mTabIndicator.get(position);
			ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	@Override
	public void onClick(View v)
	{

		resetOtherTabs();

		switch (v.getId())
		{
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mTabIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;

		}

	}

	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs()
	{
		for (int i = 0; i < mTabIndicator.size(); i++)
		{
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try
				{
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e)
				{
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways()
	{
		try
		{
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
