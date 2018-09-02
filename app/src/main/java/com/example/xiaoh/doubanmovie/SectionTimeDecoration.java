package com.example.xiaoh.doubanmovie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class SectionTimeDecoration extends RecyclerView.ItemDecoration {//用于分组展示的分割线（主要粘性头的部分）
//    private int mHeader =80;
//    private GroupInfoCallback mCallBack;
//    private Paint mpaint = new Paint();
//    private Paint mtextpaint;

//    public SectionTimeDecoration(Context context, GroupInfoCallback callback) {
//        super();
//        mCallBack = callback;
//        mpaint.setAntiAlias(true);
//        mpaint.setColor(Color.parseColor("#C4C4C4"));
//        mtextpaint = new Paint();
//        mtextpaint.setAntiAlias(true);
//        mtextpaint.setColor(Color.BLACK);
//        mtextpaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,context.getResources().getDisplayMetrics()));
//    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//
//        int postion = parent.getChildAdapterPosition(view);
//
//        if (mCallBack != null){
//             GroupInfo groupInfo = mCallBack.getGroupInfo(postion);
//
//        }
//    }

//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state){
//        int childCount = parent.getChildCount();
//        for (int i = 0 ; i <childCount;i++){
//            View view = parent.getChildAt(i);
//            int toptest = view.getTop(); //测试每个view的位置
//            int bottomtest = view.getBottom();
//            int index = parent.getChildAdapterPosition(view);
//            if (mCallBack != null){
//                GroupInfo groupInfo = mCallBack.getGroupInfo(index);
//                int left = parent.getPaddingLeft();
//                int right = parent.getWidth() - parent.getPaddingRight();
//                if (i != 0) {
//                    if (groupInfo.isFirstViewInGroup()) {
//                        int top = view.getTop()-mHeader;
//                        int bottom = view.getTop();
//                        drawHeaderRect(c,groupInfo,left,top,right,bottom);
//                    }
//                }else {
//                    int top = parent.getPaddingTop();
//                    int bottom = top+mHeader;
//                    drawHeaderRect(c,groupInfo,left,top,right,bottom);
//                }
//            }
//        }
//    }

//    private void drawHeaderRect(Canvas c, GroupInfo groupinfo, int left, int top, int right, int bottom) {//绘制Header
//        c.drawRect(left,top,right,bottom,mpaint);
//        float titleX =  left ;
//        Rect targetRect = new Rect(left, top, right, bottom);
//        Paint.FontMetricsInt fontMetrics = mtextpaint.getFontMetricsInt();
//        float titleY= (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
//        c.drawText(groupinfo.getDate(),titleX,titleY,mtextpaint);
//    }
//
//    public interface GroupInfoCallback{
//        public GroupInfo getGroupInfo(int postion);
//    }


    public interface GroupInfoCallback{
        public GroupInfo getGroupInfo(int position);

    }

    private GroupInfoCallback mCallback;
    private int mHeaderHeight =80;

    public SectionTimeDecoration(GroupInfoCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if ( mCallback != null ) {
            GroupInfo groupInfo = mCallback.getGroupInfo(position);

            //如果是组内的第一个则将间距撑开为一个Header的高度，或者就是普通的分割线高度
            if ( groupInfo != null && groupInfo.isFirstViewInGroup() ) {
                outRect.top = mHeaderHeight;
            } else {
                outRect.top = 1;
            }
        }
    }




}
