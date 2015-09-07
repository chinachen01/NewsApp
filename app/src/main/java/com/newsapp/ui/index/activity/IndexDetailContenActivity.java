package com.newsapp.ui.index.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.focus.newsapp.R;
import com.google.gson.Gson;
import com.newsapp.ui.index.bean.ImfoDetailBean;
import com.newsapp.ui.index.bean.ImfoDetailImageBean;
import com.newsapp.utils.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 *
 */
public class IndexDetailContenActivity extends Activity {
    private TextView mContentTtileText, mContentSourceText, mContentTimeText, mContentCountText;
    private NetworkImageView mContentImage;
    private String mJsonData;
    private WebView mContentWebView;
    private ImageView mBackImage;
    Animation mRightAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_layout);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        initView();//初始化控件
        syncSetJsonData(url);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mContentTtileText = (TextView) findViewById(R.id.detail_content_title_text);
        mContentSourceText = (TextView) findViewById(R.id.detail_content_source_text);
        mContentTimeText = (TextView) findViewById(R.id.detail_content_title_time_text);
        mContentCountText = (TextView) findViewById(R.id.detail_image_count_text);
        mContentImage = (NetworkImageView) findViewById(R.id.detail_content_image);
        mContentWebView = (WebView) findViewById(R.id.detail_content_doc_webview);
        mBackImage = (ImageView) findViewById(R.id.detail_title_back_image);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRightAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_small_loading_refresh);
    }

    /**
     * 初始化图片加载器
     */

    /**
     * 设置详细的新闻信息
     *
     * @param detailBean
     * @param imageBeans
     */
    private void setDetailContent(ImfoDetailBean detailBean, List<ImfoDetailImageBean> imageBeans) {

        //没有图片不加载,隐藏相关布局
        if (detailBean.getImg().size() != 0) {
            mContentCountText.setVisibility(View.VISIBLE);
            mContentCountText.setText("共" + detailBean.getImg().size() + "张");
            mContentImage.setImageUrl(imageBeans.get(0).getSrc(), RequestManager.getImageLoader());
            mContentImage.setDefaultImageResId(R.mipmap.loading_pin);
            mContentImage.setErrorImageResId(R.mipmap.loading_pin);
        } else {
            mContentCountText.setVisibility(View.GONE);
            mContentImage.setVisibility(View.GONE);
        }
         analyzeBody(detailBean.getBody());
        mContentSourceText.setText(detailBean.getSource());
        mContentTimeText.setText(detailBean.getPtime());
        mContentTtileText.setFocusable(true);
        mContentTtileText.setText(detailBean.getTitle());
//        mContentDocText.setText(doc);
    }

    private void analyzeBody(String body) {
//        String doc = body.replaceAll("<p>", "").replaceAll("</p>","\n");
        String doc = "<body>" + body + "</body>";
        mContentWebView.loadDataWithBaseURL(null,doc,"text/html","utf-8",null);//解析Html字符串
        mContentWebView.setWebViewClient(new MyWebViewClient());
    }


    private void syncSetJsonData(final String docid) {
        String url = "http://c.m.163.com/nc/article/" + docid + "/full.html";
        StringRequest strRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ImfoDetailBean detailBean = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    detailBean = gson.fromJson(jsonObject.get(docid).toString(), ImfoDetailBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<ImfoDetailImageBean> imageBeans = detailBean.getImg();
                setDetailContent(detailBean, imageBeans);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        strRequest.setShouldCache(true);
        RequestManager.getRequestQueue().add(strRequest);
    }
//
//    class MyLoadImageListener implements ImageLoadingListener {
//        @Override
//        public void onLoadingStarted(String s, View view) {
//
//        }
//
//        @Override
//        public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//        }
//
//        @Override
//        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//            mLoadingImage.setVisibility(View.GONE);
//        }
//
//        @Override
//        public void onLoadingCancelled(String s, View view) {
//
//        }
//    }


//    //改写物理按键——返回的逻辑
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if(keyCode== KeyEvent.KEYCODE_BACK)
//        {
//            if(mWebView.canGoBack())
//            {
//                mWebView.goBack();//返回上一页面
//                return true;
//            }
//            else
//            {
//                System.exit(0);//退出程序
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mContentWebView.loadUrl(url);
            return true;//返回false为第三方浏览器
        }
    }
}
