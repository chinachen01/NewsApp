package com.newsapp.ui.index.bean;

import java.util.List;

/**
 *
 */
public class ImfoDetailBean {
    private String body;
    private String title;
    private String source;
    private String ptime;
    private List<ImfoDetailImageBean> img;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tile) {
        this.title = tile;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<ImfoDetailImageBean> getImg() {
        return img;
    }

    public void setImg(List<ImfoDetailImageBean> img) {
        this.img = img;
    }
}
