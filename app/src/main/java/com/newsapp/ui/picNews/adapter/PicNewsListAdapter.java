package com.newsapp.ui.picNews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.focus.newsapp.R;
import com.newsapp.ui.picNews.bean.ImfoPicDataListBean;
import com.newsapp.utils.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PicNewsListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ImfoPicDataListBean> mList = new ArrayList<>();

    public PicNewsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setDataChange(List<ImfoPicDataListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pic_news_listview_item, null);
            holder = new ViewHolder();
            holder.setImageView((NetworkImageView) convertView.findViewById(R.id.pic_news_listview_image));
            holder.setTextView((TextView) convertView.findViewById(R.id.pic_news_listview_text));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String picUrl = mList.get(position).getPic();
        String text = mList.get(position).getTitle();
        holder.getImageView().setImageUrl(picUrl, RequestManager.getImageLoader());
        holder.getImageView().setDefaultImageResId(R.mipmap.loading_pin);
        holder.getTextView().setText(text);
        return convertView;
    }
    class ViewHolder {
        private NetworkImageView imageView;
        private TextView textView;

        public NetworkImageView getImageView() {
            return imageView;
        }

        public void setImageView(NetworkImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}
