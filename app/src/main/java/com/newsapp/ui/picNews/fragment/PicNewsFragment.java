package com.newsapp.ui.picNews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.focus.newsapp.R;
import com.google.gson.Gson;
import com.newsapp.ui.picNews.activity.PicNewsDetailActivity;
import com.newsapp.ui.picNews.adapter.PicNewsListAdapter;
import com.newsapp.ui.picNews.bean.ImfoPicDataBean;
import com.newsapp.ui.picNews.bean.ImfoPicDataListBean;
import com.newsapp.utils.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 *
 */
public class PicNewsFragment extends Fragment implements AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    private String mUrl;
    private ListView mListView;
    private PicNewsListAdapter mAdapter;
    private SwipeRefreshLayout mSwipLayout;
    public static PicNewsFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("URL",url);
        PicNewsFragment fragment = new PicNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic_news, container, false);
        mListView = (ListView) view.findViewById(R.id.pic_news_listview);
        mListView.setEmptyView(view.findViewById(R.id.pic_news_progress_bar));
        mAdapter = new PicNewsListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        asyncSetListView(mUrl);
        mSwipLayout = (SwipeRefreshLayout) view.findViewById(R.id.pic_news_swip_layout);
        mSwipLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments() != null ? getArguments().getString("URL") : "";
    }


    private void asyncSetListView(String url) {
        StringRequest strRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<ImfoPicDataListBean> list = analyzeJsonData(response).getList();
                mAdapter.setDataChange(list);
                mSwipLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        strRequest.setShouldCache(true);
        RequestManager.getRequestQueue().add(strRequest);
    }

    private  ImfoPicDataBean analyzeJsonData(String jsonData) {
        ImfoPicDataBean dataBean = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            Gson gson = new Gson();
            dataBean= gson.fromJson(data.toString(), ImfoPicDataBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataBean;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImfoPicDataListBean bean = (ImfoPicDataListBean) parent.getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), PicNewsDetailActivity.class);
        intent.putExtra("IMFO", bean);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mSwipLayout.setRefreshing(true);
        asyncSetListView(mUrl);
    }
}
