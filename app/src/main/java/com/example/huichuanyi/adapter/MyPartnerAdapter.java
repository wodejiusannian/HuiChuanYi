package com.example.huichuanyi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.huichuanyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2017/1/18.
 */
public class MyPartnerAdapter extends PagerAdapter {

    private List<ImageView> mImages;
    private View.OnClickListener mOnClickListener;
    public void setOnItemClickListener(View.OnClickListener onClickListener){
        mOnClickListener = onClickListener;
    }

    public MyPartnerAdapter(Context mContext) {
        mImages = new ArrayList<>();
        for (int i = 0;i <4;i++){
            ImageView imageView = new ImageView(mContext);

            switch(i){
                case 0:
                    imageView.setImageResource(R.mipmap.dajiade);
                    break;
                case 1:
                    imageView.setImageResource(R.mipmap.wode);
                    break;
                case 2:
                    imageView.setImageResource(R.mipmap.nide);
                    break;
                case 3:
                    imageView.setImageResource(R.mipmap.womende);
                    break;
                default:

                    break;
            }
            mImages.add(imageView);
        }
    }


    @Override
    public int getCount() {
        return mImages.size()==0?0:mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup Container, int position, Object object) {
        ((ViewPager) Container).removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(mImages.get(position), 0);
        ImageView imageView = mImages.get(position);
        imageView.setTag(position);
        imageView.setOnClickListener(mOnClickListener);
        return imageView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
