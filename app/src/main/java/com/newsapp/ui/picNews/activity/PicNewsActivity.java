package com.newsapp.ui.picNews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.focus.newsapp.R;
import com.newsapp.widgets.CustomTitle;
import com.newsapp.ui.picNews.adapter.PicNewsPagerAdapter;

/**
 *
 */
public class PicNewsActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private String[] urls = {"http://api.sina.cn/sinago/list.json?channel=hdpic_toutiao&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=&imei=867064013906290&uid=802909da86d9f5fc&p=", "http://api.sina.cn/sinago/list.json?channel=hdpic_funny&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=", "http://api.sina.cn/sinago/list.json?channel=hdpic_pretty&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=", "http://api.sina.cn/sinago/list.json?channel=hdpic_story&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p="};
    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private CustomTitle mCustomTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pic_news_laytout);
        init();
    }

    private void init() {
        mRadioGroup = (RadioGroup) findViewById(R.id.pic_news_radio_group);
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager = (ViewPager) findViewById(R.id.pic_news_detail_viewpager);
        PicNewsPagerAdapter adapter = new PicNewsPagerAdapter(getSupportFragmentManager(), urls);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mCustomTitle = (CustomTitle) findViewById(R.id.pic_news_custom_title);
        mCustomTitle.setOnClickListener(this);
        ((RadioButton) mRadioGroup.getChildAt(0)).toggle();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        ((RadioButton) mRadioGroup.getChildAt(i)).toggle();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            if (checkedId == group.getChildAt(i).getId()) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_title_left_image:
                finish();
                break;
        }
    }
}
