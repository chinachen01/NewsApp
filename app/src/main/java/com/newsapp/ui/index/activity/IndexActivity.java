package com.newsapp.ui.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.focus.newsapp.R;
import com.newsapp.ui.channel.activity.ChannelActivity;
import com.newsapp.ui.index.adapter.IndexPagerAdapter;
import com.newsapp.ui.index.fragment.SlidingMenuFragment;
import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends SlidingFragmentActivity implements SlidingMenuFragment.OnClickSlidingMenuItem, View.OnClickListener,ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private ImageView mTtileIconImage;
    private RadioGroup mTitleGroup;
    private ViewPager mContentViewPage;
    public static List<String> newsTypeId = new ArrayList<>(); //用户的标题信息,后续保存在用户信息对象中
    private SlidingMenu sm;
    private ImageView mAddChannel;
    private long exitTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_index_layout);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        mTtileIconImage = (ImageView) findViewById(R.id.index_title_icon_image);
        mTtileIconImage.setOnClickListener(this);
        mTitleGroup = (RadioGroup) findViewById(R.id.index_title_radio_group);
        mContentViewPage = (ViewPager) findViewById(R.id.index_content_viewpager);
        mTitleGroup.setOnCheckedChangeListener(this);
        IndexPagerAdapter adapter = new IndexPagerAdapter(getSupportFragmentManager());
        mContentViewPage.setAdapter(adapter);
        mContentViewPage.setOnPageChangeListener(this);
        ((RadioButton) mTitleGroup.getChildAt(0)).toggle();
        //添加title信息,后续从本地文件中读取
        addIndexTtile();
        adapter.setDataChange(newsTypeId);
        //初始化侧滑控件
        setBehindContentView(R.layout.frame_layout);
        getSupportFragmentManager().beginTransaction().add(R.id.slidingmenu_framlayout, SlidingMenuFragment.newInstance()).commit();
        sm = getSlidingMenu();
        sm.setSlidingEnabled(true);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        sm.setShadowWidthRes(R.dimen.sliding_menu_with);
        sm.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        sm.setBehindScrollScale(0);
        sm.setFadeDegree(0.25f);
        //add Channel
        mAddChannel = (ImageView) findViewById(R.id.index_add_image);
        mAddChannel.setOnClickListener(this);
    }

    private void addIndexTtile() {
        newsTypeId.add("T1348647909107");
        newsTypeId.add("T1348648517839");
        newsTypeId.add("T1348649079062");
        newsTypeId.add("T1348648756099");
        newsTypeId.add("T1348649580692");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position < mTitleGroup.getChildCount())
            ((RadioButton) mTitleGroup.getChildAt(position)).toggle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //循环判断选中的单选按钮,获得对应项后,设置viewpager
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            if (checkedId == group.getChildAt(i).getId()) {
                mContentViewPage.setCurrentItem(i);
            }
        }
    }
    //============头像图标的点击事件=============
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_title_icon_image:
                sm.showMenu();
                break;
            case R.id.index_add_image:
                Intent intent = new Intent(this, ChannelActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void closeSlidingMenu() {
        sm.toggle();
    }

    @Override
    public void clickSlidingMenuItem() {
        Toast.makeText(IndexActivity.this,"当前模块正在开发中",Toast.LENGTH_SHORT).show();
    }

    /**
     * 按两次返回退出应用程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitTime = System.currentTimeMillis() - exitTime;
            if (exitTime > 2000) {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
