package com.library.lgq.custom;

import android.support.v4.app.Fragment;

public abstract class LazyloadFragment extends Fragment {

	/**
	 * Fragment��ǰ״̬�Ƿ�ɼ�
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
	 * �ɼ�
	 */
	protected void onVisible(){
		lazyLoad();
	}
	
	/**
	 * ���ɼ�
	 */
	protected void onInvisible(){
		
	}
	
	/**
	 * �ӳټ���
	 */
	protected abstract void lazyLoad();
}
