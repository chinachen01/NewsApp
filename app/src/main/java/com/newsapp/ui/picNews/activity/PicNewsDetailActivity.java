package com.newsapp.ui.picNews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.focus.newsapp.R;
import com.newsapp.ui.picNews.adapter.PicNewsDetailViewPagerAdapter;
import com.newsapp.ui.picNews.bean.ImfoPicDataListBean;
import com.newsapp.ui.picNews.bean.ImfoPicsListbean;

import java.util.List;

/**
 *
 */
public class PicNewsDetailActivity extends Activity {
    private ViewPager mViewPager;
    private ImageView mBackImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pic_news_detail_layout);
        initView();
        ImfoPicDataListBean bean = getIntent().getParcelableExtra("IMFO");
        Log.e("detai", bean.getTitle());
        List<ImfoPicsListbean> list = bean.getPics().getList();
        PicNewsDetailViewPagerAdapter adapter = new PicNewsDetailViewPagerAdapter(this, list);
        mViewPager.setAdapter(adapter);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pic_news_detail_viewpager);
        mBackImage = (ImageView) findViewById(R.id.pic_news_detail_title_image);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
