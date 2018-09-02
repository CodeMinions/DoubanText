package com.example.xiaoh.doubanmovie;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class ListHistory extends FrameLayout {


    public ListHistory(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.lh, this);

        // FIXME: 2018/8/12 这里关于加载此布局的代码有些冗杂，尝试优化

    }
    
}
