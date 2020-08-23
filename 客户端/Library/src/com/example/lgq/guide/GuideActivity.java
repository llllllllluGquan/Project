
package com.example.lgq.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.example.lgq.login.Login;
import com.example.template.MainActivity;
import com.example.template.R;
import com.library.lgq.custom.ViewPagerAdapter;

/**
 * @Filename GuideActivity.java
 * @Package cn.staray.guidetest
 * @Project Guidetest
 * @Create 2014-6-12 ����2:44:23
 * @author Staray
 * @Description ��������
 */

public class GuideActivity extends Activity {

    // ��ʾ����ҳ���viewpager
    private ViewPager guideViewPager;

    // ҳ��������
    private ViewPagerAdapter guideViewAdapter;

    // ҳ��ͼƬ�б�
    private ArrayList<View> mViews;

    // ͼƬ��Դ���������Ƿ�����3��ͼƬ����Ϊ������ͼƬ�������Ѿ���guide_content_view.xml�м��غ���
    // һ��ֱ���������ļ��Ϳ����ˡ�
    private final int images[] = {
            R.drawable.guide_page1, R.drawable.guide_page2, R.drawable.guide_page3
    };

    // �ײ�������С��
    private ImageView[] guideDots;

    // ��¼��ǰѡ�е�ͼƬ
    private int currentIndex;

    // ���ǵ����ǵĿ�ʼ��ť��
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide_view);

        initView();

        initDot();

        // ���ҳ����������¼��������µ���С���״̬��
        guideViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                setCurrentDot(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        // ��ʼ��ť�ĵ���¼�����
        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // ���������תһ��ҳ��
                Intent intent = new Intent(GuideActivity.this, Login.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        });
    }

    // ��ʼ��ҳ��
    private void initView() {
        guideViewPager = (ViewPager)findViewById(R.id.guide_view_pager);
        mViews = new ArrayList<View>();

        for (int i = 0; i < images.length; i++) {
            // �½�һ��ImageView�������������ǵ�ͼƬ��
            ImageView iv = new ImageView(GuideActivity.this);
            iv.setBackgroundResource(images[i]);

            // ��������ӵ�ͼƬ�б���
            mViews.add(iv);
        }

        // �������������ͼƬ�ˣ�����һ�ŷ���guide_content_view.xml�У����ǰ����ҳ��Ҳ��ӽ�����
        View view = LayoutInflater.from(GuideActivity.this).inflate(R.layout.guide_content_view,
                null);
        mViews.add(view);

        // ����Ϊ���ǵĿ�ʼ��ť�ҵ���Ӧ�Ŀؼ�
        startBtn = (Button)view.findViewById(R.id.start_btn);

        // �����õ����ǵ�ҳ����������
        guideViewAdapter = new ViewPagerAdapter(mViews);

        guideViewPager.setAdapter(guideViewAdapter);
    }

    // ��ʼ������С��
    private void initDot() {
        // �ҵ�����С��Ĳ���
        LinearLayout layout = (LinearLayout)findViewById(R.id.guide_dots);

        // ��ʼ��С������
        guideDots = new ImageView[mViews.size()];

        // ѭ��ȡ��С��ͼƬ����ÿ��С�㶼��������״̬
        for (int i = 0; i < mViews.size(); i++) {
            guideDots[i] = (ImageView)layout.getChildAt(i);
            guideDots[i].setSelected(false);
        }

        // ��ʼ����һ��С��Ϊѡ��״̬
        currentIndex = 0;
        guideDots[currentIndex].setSelected(true);
    }

    // ҳ�����ʱ������С��״̬
    private void setCurrentDot(int position) {
        if (position < 0 || position > mViews.size() - 1 || currentIndex == position) {
            return;
        }

        guideDots[position].setSelected(true);
        guideDots[currentIndex].setSelected(false);

        currentIndex = position;
    }
}
