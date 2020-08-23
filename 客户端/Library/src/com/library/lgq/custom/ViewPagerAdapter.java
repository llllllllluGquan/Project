
package com.library.lgq.custom;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * @Filename ViewPagerAdapter.java
 * @Package cn.staray.guidetest
 * @Project Guidetest
 * @Create 2014-6-12 ����2:57:31
 * @author Staray
 * @Description ����������
 */

public class ViewPagerAdapter extends PagerAdapter {

    private final ArrayList<View> mViews;

    public ViewPagerAdapter(ArrayList<View> views) {
        mViews = views;
    }

    // ����ҳ����Ŀ
    @Override
    public int getCount() {
        if (mViews != null) {
            return mViews.size();
        }
        return 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    // ��ʼ��positionλ�õ�ҳ��
    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager)view).addView(mViews.get(position), 0);
        return mViews.get(position);
    }

    // �ж��Ƿ��ɶ������ɽ���
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    // ����positionλ�õĽ���
    @Override
    public void destroyItem(View view, int position, Object arg2) {
        ((ViewPager)view).removeView(mViews.get(position));
    }
}
