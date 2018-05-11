package com.example.huichuanyi.ui.activity.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by 小五 on 2017/4/25.
 */

@SuppressLint("AppCompatCustomView")
public class MoveImageView extends ImageView {

    private static final String TAG = "MoveImageView";

    private int lastX = 0;
    private int lastY = 0;

    /*private double x = 0;
    private double y = 0;
    private double xx = 0;
    private double yy = 0;*/
    private static int screenWidth = 1080;
    //屏幕宽度
    private static int screenHeight = 1920;

    /*private OnClickListener mOnClick;*/
//屏幕高度
    /*private MoveViewClick mMoveClick;*/

    public MoveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
    }


   /* public void setonItemListener(MoveViewClick moveClick) {
        mMoveClick = moveClick;
    }*/
    /*public void setOnclickListener(OnClickListener onClickListener) {
        mOnClick = onClickListener;
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
               /* mMoveClick.onClick();*/
                /*mOnClick.onClick(this);*/
               /* x = event.getRawX();
                y = event.getRawY();
                Log.e(TAG, "onTouchEvent: " + x + "y" + y);*/
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - getHeight();
                }
                layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                /*xx = event.getRawX();
                yy = event.getRawY();
                double distance = Math.sqrt(Math.abs(x - lastX) * Math.abs(x - lastX) + Math.abs(y - lastY) * Math.abs(y - lastY));
                if (distance < 15) {
                    mOnClick.onClick(this);
                }*/
                break;
            default:
                break;
        }
        return true;
    }

    /*public interface MoveViewClick {
        void onClick();
    }*/
}
