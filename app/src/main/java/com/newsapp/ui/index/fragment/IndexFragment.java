package com.newsapp.ui.index.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.focus.newsapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.newsapp.ui.index.activity.IndexDetailContenActivity;
import com.newsapp.ui.index.adapter.IndexFragmentPullAdapter;
import com.newsapp.ui.index.bean.ImfoContentBean;
import com.newsapp.ui.index.inter.InterContentType;
import com.newsapp.utils.RequestManager;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.maxwin.view.XListView;

/**
 *
 */
public class IndexFragment extends Fragment implements InterContentType, XListView.IXListViewListener, SwipeRefreshLayout.OnRefreshListener, BaseSliderView.OnSliderClickListener, AdapterView.OnItemClickListener {
    private XListView mListView;
    private String mContentTypeId;
    private IndexFragmentPullAdapter mAdapter;
    private SwipeRefreshLayout mSwipLayout;
    private SliderLayout mSliderLayout;
    private View mHeadView;
    private SharedPreferences mSharedPreferences;
    private Handler mHandler = new Handler();
    private int mCurrentPage = 0;
    private RequestQueue mRequestQueue;

    public static IndexFragment newInstance(String contentTypeId) {
        Bundle args = new Bundle();
        args.putString("contentTypeId", contentTypeId);
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        mListView = (XListView) view.findViewById(R.id.fragment_index_xlistview);
        ProgressBar emptyView = (ProgressBar) view.findViewById(R.id.fragment_index_empty_progress_bar);
        mListView.setEmptyView(emptyView);
        mSwipLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_index_refresh_layout);
        mSwipLayout.setOnRefreshListener(this);
        mSwipLayout.setColorScheme(android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mAdapter = new IndexFragmentPullAdapter(getActivity());
        mCurrentPage = 0;
        //初始化headView
        mHeadView = inflater.inflate(R.layout.index_xlistview_sliding_image_item, null);
        mSliderLayout = (SliderLayout) mHeadView.findViewById(R.id.xlistview_slider_layout);
        mListView.addHeaderView(mHeadView);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(false);
        mListView.setAdapter(mAdapter);
        mSharedPreferences = getActivity().getSharedPreferences("JSON_DATA", Context.MODE_PRIVATE);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(this);


        Log.e("tag", "oncreateview");
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentTypeId = getArguments() != null ? getArguments().getString("contentTypeId") : "";
        mRequestQueue = RequestManager.getRequestQueue();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置Listview
        setListView("0", "9");
        Log.e("tag", "onActivityCreated");
    }

    /**
     * 设置XlistView的HeadView,用以显示动态滚动图片
     *
     * @param headBean
     */
    private void setHeadView(List<ImfoContentBean> headBean) {
        //刷新数据前先移除以前的数据
        if(mSliderLayout!=null)
        mSliderLayout.removeAllSliders();
        //根据Json数据,头条信息添加SlidingImage数据
        for (int j = 0; j < 5; j++) {
            ImfoContentBean headbean = headBean.get(j);
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(headbean.getTitle())
                    .image(headbean.getImgsrc())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("URL", headbean.getDocid());

            mSliderLayout.addSlider(textSliderView);
        }

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        mSliderLayout.setDuration(3000);
    }

    /**
     * 设置ListView,显示新闻信息
     *
     * @param pageStart
     * @param pageEnd
     */
    private void setListView(String pageStart, String pageEnd) {
        String url = initUrl(pageStart, pageEnd);
        asyncHttpConnect(url);
    }

    /**
     * 拼接Url以满足访问不同地址的需求
     *
     * @param pageStart 开始的页码
     * @param pageEnd   结束的页码
     * @return
     */
    private String initUrl(String pageStart, String pageEnd) {
        String url = "";
        if (mContentTypeId.equals(typeIdhead)) {
            url = newsHeadUrl + "/headline/" + mContentTypeId + "/" + pageStart + "-" + pageEnd + ".html";
        } else {
            url = newsOtherUrl + mContentTypeId + "/" + pageStart + "-" + pageEnd + ".html";
        }
        return url;
    }

    /**
     * 异步通过网络地址获取返回的数据
     *
     * @param url
     */
    private void asyncHttpConnect(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        final StringBuffer jsonData = new StringBuffer();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String str = new String(bytes);
                jsonData.append(str);
                writeJsonData2SharedPreferencs(jsonData.toString());
                setListViewData(jsonData.toString());
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                String jsonData = readJsonData4SharedPreferencs();
                if (jsonData != "") {
                    setListViewData(jsonData);
                }
                switch (i) {
                    case 0:
                        Toast.makeText(getActivity(), "网络环境连接异常", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void writeJsonData2SharedPreferencs(String jsonData) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(mContentTypeId, jsonData);
        editor.commit();
    }

    private String readJsonData4SharedPreferencs() {
        return mSharedPreferences.getString(mContentTypeId,"");
    }

    /**
     * 设置ListView数据源
     *
     * @param data
     */
    private void setListViewData(String data) {
        Gson gson = new Gson();
        ArrayList<ImfoContentBean> headBean = null;
        try {
            JSONObject jObject = new JSONObject(data);
            //==================解析值包含数组的字符串================
            Type listType = new TypeToken<ArrayList<ImfoContentBean>>() {
            }.getType();
            headBean = gson.fromJson(jObject.get(mContentTypeId).toString(), listType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(headBean, new Comparator<ImfoContentBean>() {
            @Override
            public int compare(ImfoContentBean lhs, ImfoContentBean rhs) {
                return lhs.getPriority() - rhs.getPriority();
            }
        });
        if (mCurrentPage == 0) {
            mAdapter.setDataChange(headBean);
            setHeadView(headBean);
        } else {
            mAdapter.addDataChange(headBean);
        }
        mSwipLayout.setRefreshing(false);
        onLoad();
    }


    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    //============上,下拉刷新事件==============
    @Override
    public void onRefresh() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipLayout.setRefreshing(true);
                mCurrentPage = 0;
                setListView("0", "9");
            }
        });
    }

    @Override
    public void onLoadMore() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCurrentPage++;
                String pageStart = mCurrentPage * 10 + "";
                String pageEnd = (mCurrentPage + 1) * 10 - 1 + "";
                setListView(pageStart, pageEnd);
            }
        });
    }

    //============滚动图片点击事件=============

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        Bundle imageUrl = baseSliderView.getBundle();
        String url = imageUrl.getString("URL");
        if (url != null) {
            Intent intent = new Intent(getActivity(), IndexDetailContenActivity.class);
            intent.putExtra("URL", url);
            startActivity(intent);
        }

    }

    //===========ListItem的点击事件============
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImfoContentBean bean = (ImfoContentBean) parent.getAdapter().getItem(position);
        String url = bean.getDocid();
        if (url != null) {
            Intent intent = new Intent(getActivity(), IndexDetailContenActivity.class);
            intent.putExtra("URL", url);
            startActivity(intent);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSliderLayout != null) {
            mSliderLayout.stopAutoCycle();
        }
    }
}
