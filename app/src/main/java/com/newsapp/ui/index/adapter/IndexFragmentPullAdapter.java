package com.newsapp.ui.index.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.focus.newsapp.R;
import com.newsapp.ui.index.bean.ImfoContentBean;
import com.newsapp.utils.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class IndexFragmentPullAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ImfoContentBean> mList = new ArrayList<>();
    private int viewTypeCount = 2;
    private com.android.volley.toolbox.ImageLoader mImageLoader;
//    private ImageLoader imageLoader;
//    private DisplayImageOptions options;
    public IndexFragmentPullAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mImageLoader = RequestManager.getImageLoader();
//        imageLoader = ImageLoader.getInstance();
//        // Initialize ImageLoader with configuration.
//        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_launcher).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .cacheInMemory(true).cacheOnDisk(true).build();
    }

    public void setDataChange(List list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addDataChange(List list) {
        mList.addAll(list);
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
    public int getItemViewType(int position) {
        if(position >=10 && position %10 == 0) {
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return viewTypeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == 0) {
            return getFullImageView(position, convertView, parent);
        }else {
            return getSimpleImageView(position, convertView, parent);
        }
    }

    private View getFullImageView(int position, View convertView, ViewGroup parent) {
        FullImageViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.index_xlistview_full_image_item, null);
            holder = new FullImageViewHolder();
            holder.setmFullImage((NetworkImageView) convertView.findViewById(R.id.xlistview_item_full_image));
            holder.setmFullText((TextView) convertView.findViewById(R.id.xlistview_item_full_text));
            convertView.setTag(holder);
        } else {
            holder = (FullImageViewHolder) convertView.getTag();
        }
        holder.getmFullText().setText(mList.get(position).getTitle());
        //加载图片
        String imageSrc = mList.get(position).getImgsrc();
        holder.getmFullImage().setImageUrl(imageSrc,mImageLoader);
        holder.getmFullImage().setDefaultImageResId(R.mipmap.loading_pin);
        holder.getmFullImage().setErrorImageResId(R.mipmap.loading_pin);
//        imageLoader.displayImage(imageSrc,holder.getmFullImage(),options,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                super.onLoadingStarted(imageUri, view);
//                //加载过程中将图片隐藏(防止显示复用的图片),显示进度条
//                finalHolder.getmFullImage().setVisibility(View.INVISIBLE);
//                finalHolder.getmFullProgressBar().setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                finalHolder.getmFullProgressBar().setVisibility(View.GONE);
//                finalHolder.getmFullImage().setVisibility(View.VISIBLE);
//            }
//        });

        return convertView;
    }

    private View getSimpleImageView(int position, View convertView, ViewGroup parent) {
        SimpleImageViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.index_xlistview_simple_image_item, null);
            holder = new SimpleImageViewHolder();
            holder.setmImage((NetworkImageView) convertView.findViewById(R.id.xlistview_item_image));
            holder.setmTtileText((TextView) convertView.findViewById(R.id.xlistview_item_title_text));
            holder.setmDigestText((TextView) convertView.findViewById(R.id.xlistview_item_digest_text));
            holder.setmTimeText((TextView) convertView.findViewById(R.id.xlistview_item_time_text));
            convertView.setTag(holder);
        } else {
            holder = (SimpleImageViewHolder) convertView.getTag();
        }
//        //加载图片
//        if(options == null) {
//
//        }

        String imageSrc = mList.get(position).getImgsrc();
        holder.getmImage().setDefaultImageResId(R.mipmap.loading_pin);
        holder.getmImage().setErrorImageResId(R.mipmap.loading_pin);
        holder.getmImage().setImageUrl(imageSrc,mImageLoader);
//        final SimpleImageViewHolder finalHolder = holder;
//        ImageLoader.getInstance().displayImage(imageSrc,holder.getmImage(),options,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                super.onLoadingStarted(imageUri, view);
//                //加载过程中将图片隐藏(防止显示复用的图片),显示进度条
//                finalHolder.getmImage().setVisibility(View.INVISIBLE);
//                finalHolder.getmProgressBar().setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                finalHolder.getmProgressBar().setVisibility(View.GONE);
//                finalHolder.getmImage().setVisibility(View.VISIBLE);
//            }
//        });
        holder.getmTtileText().setText(mList.get(position).getTitle());
        holder.getmDigestText().setText(mList.get(position).getDigest());
        holder.getmTimeText().setText(mList.get(position).getPtime());
        return convertView;
    }

    class SimpleImageViewHolder {
        private NetworkImageView mImage;
        private TextView mTtileText, mDigestText, mTimeText;
        private ProgressBar mProgressBar;

        public ProgressBar getmProgressBar() {
            return mProgressBar;
        }

        public void setmProgressBar(ProgressBar mProgressBar) {
            this.mProgressBar = mProgressBar;
        }

        public NetworkImageView getmImage() {
            return mImage;
        }

        public void setmImage(NetworkImageView mImage) {
            this.mImage = mImage;
        }

        public TextView getmTtileText() {
            return mTtileText;
        }

        public void setmTtileText(TextView mTtileText) {
            this.mTtileText = mTtileText;
        }

        public TextView getmDigestText() {
            return mDigestText;
        }

        public void setmDigestText(TextView mDigestText) {
            this.mDigestText = mDigestText;
        }

        public TextView getmTimeText() {
            return mTimeText;
        }

        public void setmTimeText(TextView mTimeText) {
            this.mTimeText = mTimeText;
        }
    }
    class FullImageViewHolder {
        private NetworkImageView mFullImage;
        private TextView mFullText;
        public NetworkImageView getmFullImage() {
            return mFullImage;
        }

        public void setmFullImage(NetworkImageView mFullImage) {
            this.mFullImage = mFullImage;
        }

        public TextView getmFullText() {
            return mFullText;
        }

        public void setmFullText(TextView mFullText) {
            this.mFullText = mFullText;
        }
    }
}
