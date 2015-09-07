package com.newsapp.ui.picNews.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 *
 */
public class ImfoPicDataListPicsBean implements Parcelable {
    private String total;
    private List<ImfoPicsListbean> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ImfoPicsListbean> getList() {
        return list;
    }

    public void setList(List<ImfoPicsListbean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.total);
        dest.writeTypedList(list);
    }

    public ImfoPicDataListPicsBean() {
    }

    protected ImfoPicDataListPicsBean(Parcel in) {
        this.total = in.readString();
        this.list = in.createTypedArrayList(ImfoPicsListbean.CREATOR);
    }

    public static final Parcelable.Creator<ImfoPicDataListPicsBean> CREATOR = new Parcelable.Creator<ImfoPicDataListPicsBean>() {
        public ImfoPicDataListPicsBean createFromParcel(Parcel source) {
            return new ImfoPicDataListPicsBean(source);
        }

        public ImfoPicDataListPicsBean[] newArray(int size) {
            return new ImfoPicDataListPicsBean[size];
        }
    };
}
