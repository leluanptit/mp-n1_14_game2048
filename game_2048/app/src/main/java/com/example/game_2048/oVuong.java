package com.example.game_2048;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class oVuong extends android.support.v7.widget.AppCompatTextView {

    public oVuong(Context context) {
        super(context);
    }

    public oVuong(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public oVuong(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dai = getMeasuredHeight();
        int rong = getMeasuredWidth();
        setMeasuredDimension(rong, dai);
    }

    public void setTextt(int so) {
        if (so < 100) {
            setTextSize(40);
        } else if (so < 1000) {
            setTextSize(35);
        } else {
            setTextSize(25);
        }
        if (so >= 8) {
            setTextColor(Color.WHITE);
        } else {
            setTextColor(Color.BLACK);
        }
        GradientDrawable drawable = (GradientDrawable) this.getBackground();
        drawable.setColor(dataGame.getDataGame().colorr(so));
        setBackground(drawable);
        if(so==0){
            setText(" ");
        }else{
            setText(so+"");
        }
    }
}
