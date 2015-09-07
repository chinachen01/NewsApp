package com.newsapp.ui.picNews.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class ImfoPicsListbean implements Parcelable {
    private String pic;
    private String alt;
    private String kpic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getKpic() {
        return kpic;
    }

    public void setKpic(String kpic) {
        this.kpic = kpic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pic);
        dest.writeString(this.alt);
        dest.writeString(this.kpic);
    }

    public ImfoPicsListbean() {
    }

    protected ImfoPicsListbean(Parcel in) {
        this.pic = in.readString();
        this.alt = in.readString();
        this.kpic = in.readString();
    }

    public static final Parcelable.Creator<ImfoPicsListbean> CREATOR = new Parcelable.Creator<ImfoPicsListbean>() {
        public ImfoPicsListbean createFromParcel(Parcel source) {
            return new ImfoPicsListbean(source);
        }

        public ImfoPicsListbean[] newArray(int size) {
            return new ImfoPicsListbean[size];
        }
    };
}
