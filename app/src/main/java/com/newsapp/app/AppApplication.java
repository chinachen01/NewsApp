package com.newsapp.app;


import android.app.Application;

import com.newsapp.db.SQLHelper;
import com.newsapp.utils.RequestManager;

public class AppApplication extends Application {
    private static AppApplication mAppApplication;
    private SQLHelper sqlHelper;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mAppApplication = this;
//        initImageLoader();
        RequestManager.init(this);
    }

    /** 获取Application */
    public static AppApplication getApp() {
        return mAppApplication;
    }

    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(mAppApplication);
        return sqlHelper;
    }

    /** 摧毁应用进程时候调用 */
    public void onTerminate() {
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
    }


//    private void initImageLoader() {
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .memoryCache(new LruMemoryCache(5*1024*1024)) // 5 Mb (delete most not used image)
//                .build();
//        ImageLoader.getInstance().init(config);
//    }

}
