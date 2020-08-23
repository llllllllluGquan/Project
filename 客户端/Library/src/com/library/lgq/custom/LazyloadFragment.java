package com.library.lgq.custom;

import android.support.v4.app.Fragment;

public abstract class LazyloadFragment extends Fragment {

	/**
	 * Fragment当前状态是否可见
	 */
	protected boolean isVisible;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		
		if(getUserVisibleHint()){
			isVisibleToUser=true;
			onVisible();
		}else{
			isVisibleToUser=false;
			onInvisible();
		}
	}
	/**
	 * 可见
	 */
	protected void onVisible(){
		lazyLoad();
	}
	
	/**
	 * 不可见
	 */
	protected void onInvisible(){
		
	}
	
	/**
	 * 延迟加载
	 */
	protected abstract void lazyLoad();
}
