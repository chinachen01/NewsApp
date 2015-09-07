package com.newsapp.ui.picNews.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class ImfoPicDataListBean implements Parcelable {
    private String id;
    private String title;
    private String long_title;
    private String pic;
    private String intro;
    private ImfoPicDataListPicsBean pics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLong_title() {
        return long_title;
    }

    public void setLong_title(String long_title) {
        this.long_title = long_title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public ImfoPicDataListPicsBean getPics() {
        return pics;
    }

    public void setPics(ImfoPicDataListPicsBean pics) {
        this.pics = pics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.long_title);
        dest.writeString(this.pic);
        dest.writeString(this.intro);
        dest.writeParcelable(this.pics, 0);
    }

    public ImfoPicDataListBean() {
    }

    protected ImfoPicDataListBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.long_title = in.readString();
        this.pic = in.readString();
        this.intro = in.readString();
        this.pics = in.readParcelable(ImfoPicDataListPicsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ImfoPicDataListBean> CREATOR = new Parcelable.Creator<ImfoPicDataListBean>() {
        public ImfoPicDataListBean createFromParcel(Parcel source) {
            return new ImfoPicDataListBean(source);
        }

        public ImfoPicDataListBean[] newArray(int size) {
            return new ImfoPicDataListBean[size];
        }
    };
}

