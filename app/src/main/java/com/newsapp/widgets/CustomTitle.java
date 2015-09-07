package com.newsapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.focus.newsapp.R;

/**
 *
 */
public class CustomTitle extends RelativeLayout implements View.OnClickListener {
    private OnClickListener l;

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_title_modle, this);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitle, 0, 0);
        try {
            int backgroundColor = a.getColor(R.styleable.CustomTitle_TitleBacground, 0);
            Drawable leftImageDraw = a.getDrawable(R.styleable.CustomTitle_LeftImageSrc);
            Drawable rightImageDraw = a.getDrawable(R.styleable.CustomTitle_RightImageSrc);
            int titleColor = a.getColor(R.styleable.CustomTitle_TitleColor, 0);
            String titleText = a.getString(R.styleable.CustomTitle_TitleText);
            boolean titleDivder = a.getBoolean(R.styleable.CustomTitle_TitleDivder, false);
            if (backgroundColor !=0) {
                view.setBackgroundColor(backgroundColor);
            }
            if (leftImageDraw != null) {
                ImageView leftImage = (ImageView) findViewById(R.id.custom_title_left_image);
                leftImage.setImageDrawable(leftImageDraw);
                //注册监听
                leftImage.setOnClickListener(this);
            }
            if (rightImageDraw != null) {
                ImageView rightImage = (ImageView) findViewById(R.id.custom_title_right_image);
                rightImage.setImageDrawable(rightImageDraw);
                rightImage.setOnClickListener(this);
            }
            TextView title = (TextView) findViewById(R.id.custom_title_text);
            if (titleColor !=0) {
                title.setTextColor(titleColor);
            }
            if (titleText != null) {
                title.setText(titleText);
            }
            View diver = findViewById(R.id.custom_divder_view);
            if (titleDivder == false) {
                diver.setVisibility(View.GONE);
            } else {
                diver.setVisibility(View.VISIBLE);
            }
        } finally {
            a.recycle();
        }

    }
    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    @Override
    public void onClick(View v) {
        l.onClick(v);
    }
}
