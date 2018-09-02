package com.example.xiaoh.doubanmovie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class StickyItemDecoration extends RecyclerView.ItemDecoration {//粘性头 分割线（注意此代码有可能无法复用）
    private ISticky mIsticky;
    private int mRectHeight;
    private int mTextPaintSize;
    private Paint mTxtPaint;
    private Paint mRecPaint;
    private Paint mDividerPaint;

    public StickyItemDecoration(Context context,ISticky iSticky){
        mIsticky =iSticky;
        mRectHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,context.getResources().getDisplayMetrics());
        mTextPaintSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15, context.getResources().getDisplayMetrics());
        mTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTxtPaint.setColor(Color.BLACK);
        mTxtPaint.setTextSize(mTextPaintSize);
        mRecPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRecPaint.setStyle(Paint.Style.FILL);
        mRecPaint.setColor(Color.parseColor("#fbfaf6"));
        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setStyle(Paint.Style.FILL);
        mDividerPaint.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0 ; i < childCount; i++){
            View view = parent.getChildAt(i);
            int left =parent.getPaddingLeft();
            int right = parent.getWidth()-parent.getPaddingRight();
            int top = view.getTop() -1;
            int bottom = view.getTop();
            c.drawRect(left,top,right,bottom,mDividerPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();
        int itemCount = state.getItemCount();
        int left=parent.getPaddingLeft();
        int right = parent.getWidth()- parent.getPaddingRight();
        String preGroupTitle;
        String groupTitle="";
        for (int i = 0 ; i <childCount;i++){
                View view = parent.getChildAt(i);
                int pos = parent.getChildLayoutPosition(view);
                preGroupTitle=groupTitle;
                if (pos == 0 ){//处理一个bug写的代码
                    return;
                }else {
                    pos--;
                }
                groupTitle = mIsticky.getGroupTitle(pos);
                if (groupTitle.equals(preGroupTitle)){
                    continue;
                }
            int textBaseLine = Math.max(mRectHeight,view.getTop());
            String title =mIsticky.getGroupTitle(pos);
            int viewBottom = view.getBottom();
            if (pos + 1<itemCount){
                String nextGroupTitle = mIsticky.getGroupTitle(pos+1);
                if (!nextGroupTitle.equals(groupTitle) && viewBottom < textBaseLine){
                    textBaseLine = viewBottom;
                }
            }
            c.drawRect(left,textBaseLine-mRectHeight,right,textBaseLine,mRecPaint);
            int value = (int) Math.abs(mTxtPaint.getFontMetrics().descent+mTxtPaint.getFontMetrics().ascent);
            c.drawText(title,left+10,textBaseLine-(mRectHeight)/3,mTxtPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildLayoutPosition(view);
        if (pos == 0 ){
            return;
        }else {
            pos--;
        }
        if (mIsticky.isFirstPostion(pos)){
            outRect.top = mRectHeight;
            outRect.bottom = 1;
        }else {
            outRect.bottom =1;
        }
    }
}
