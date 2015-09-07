package com.newsapp.ui.picNews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.focus.newsapp.R;
import com.newsapp.ui.picNews.bean.ImfoPicsListbean;
import com.newsapp.utils.RequestManager;

import java.util.List;

/**
 *
 */
public class PicNewsDetailViewPagerAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    private List<ImfoPicsListbean> mList;
    public PicNewsDetailViewPagerAdapter(Context context, List<ImfoPicsListbean> list){
        mInflater = LayoutInflater.from(context);
        mList = list;

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View child = mInflater.inflate(R.layout.pic_news_viewpager_item, null);
        ImfoPicsListbean bean =  mList.get(position);
        String imageUrl = bean.getPic();
        String alt = bean.getAlt();
        NetworkImageView image = (NetworkImageView) child.findViewById(R.id.pic_news_detail_image);
        TextView text = (TextView) child.findViewById(R.id.pic_news_detail_title_text);
        TextView count = (TextView) child.findViewById(R.id.pic_news_detail_count_text);
        text.setText(alt);
        image.setImageUrl(imageUrl, RequestManager.getImageLoader());
        count.setText(position+1 + "/" + mList.size());
        container.addView(child);
        return child;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
