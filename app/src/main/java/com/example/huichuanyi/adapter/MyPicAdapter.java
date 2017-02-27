package com.example.huichuanyi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.custom.RoundImageView;
import com.example.huichuanyi.bean.Pic;
import com.example.huichuanyi.utils.ImageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小五 on 2017/1/15.
 */
public class MyPicAdapter extends BaseAdapter {

    private List<Pic> mData;
    private Context mContext;
    private View.OnClickListener mClick;
    private String[] occ = {"休闲", "商务", "社交"};
    private String[] sort = {"上衣", "裤子", "裙子", "鞋子", "包", "配饰", "家居服"};
    private String mJsons;

    public MyPicAdapter(Context context, List<Pic> data, String jsons) {
        mContext = context;
        mData = data;
        mJsons = jsons;
    }

    public void setOnItemClick(View.OnClickListener click) {
        mClick = click;
    }

    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_label, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mStyle.removeAllViews();
        holder.mOcc.removeAllViews();
        holder.mSort.removeAllViews();
        final Pic pic = mData.get(position);
        final List<String> strings = pic.getmListStyle();
        final List<String> strings1 = pic.getmListStyleId();
        if (pic.isCheckOcc()) {
            for (int i = 0; i < this.occ.length; i++) {
                RadioButton rbOcc = new RadioButton(mContext);
                rbOcc.setBackgroundResource(R.drawable.rb_sealect);
                rbOcc.setText(this.occ[i]);
                rbOcc.setTag(i);
                rbOcc.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                rbOcc.setLayoutParams(params);
                rbOcc.setButtonDrawable(android.R.color.transparent);
                if (pic.tagOcc == i) {
                    rbOcc.setChecked(true);
                    rbOcc.setTextColor(mContext.getResources().getColor(R.color.write));
                }
                holder.mOcc.addView(rbOcc);
                rbOcc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        pic.setOcc(occ[tag]);
                        pic.setCheckOcc(true);
                        pic.setTagOcc(tag);
                        notifyDataSetChanged();
                    }
                });
            }
        } else {
            for (int i = 0; i < this.occ.length; i++) {
                RadioButton rbOcc = new RadioButton(mContext);
                rbOcc.setBackgroundResource(R.drawable.rb_sealect);
                rbOcc.setText(this.occ[i]);
                rbOcc.setTag(i);
                rbOcc.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                rbOcc.setLayoutParams(params);
                rbOcc.setButtonDrawable(android.R.color.transparent);
                holder.mOcc.addView(rbOcc);
                rbOcc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        pic.setOcc(occ[tag]);
                        pic.setCheckOcc(true);
                        pic.setTagOcc(tag);
                        notifyDataSetChanged();
                    }
                });
            }
        }

        if (pic.isCheckSort) {
            for (int i = 0; i < sort.length; i++) {
                RadioButton rbSort = new RadioButton(mContext);
                rbSort.setBackgroundResource(R.drawable.rb_sealect);
                rbSort.setText(sort[i]);
                rbSort.setTag(i);
                rbSort.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                rbSort.setLayoutParams(params);
                rbSort.setButtonDrawable(android.R.color.transparent);
                rbSort.setTag(i);
                if (pic.getTagSort() == i) {
                    rbSort.setChecked(true);
                    rbSort.setTextColor(mContext.getResources().getColor(R.color.write));
                }
                rbSort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        pic.setCheckSort(true);
                        pic.setTagSort(tag);
                        pic.setSort((tag + 1) + "");
                        holder.mStyle.removeAllViews();
                        pic.setCheckStyle(false);
                        pic.setTagStyle(-1);
                        pic.setStyle(null);
                        tag = tag + 1;
                        pic.setmListStyle(getStyle(tag + ""));
                        pic.setmListStyleId(getStyleId(tag + ""));
                        notifyDataSetChanged();
                    }
                });
                holder.mSort.addView(rbSort);
            }
        } else {
            for (int i = 0; i < sort.length; i++) {
                RadioButton rbSort = new RadioButton(mContext);
                rbSort.setBackgroundResource(R.drawable.rb_sealect);
                rbSort.setText(sort[i]);
                rbSort.setTag(i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                rbSort.setLayoutParams(params);
                rbSort.setGravity(Gravity.CENTER);
                rbSort.setButtonDrawable(android.R.color.transparent);
                rbSort.setTag(i);
                rbSort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        pic.setCheckSort(true);
                        pic.setTagSort(tag);
                        pic.setSort((tag + 1) + "");
                        tag = tag + 1;
                        holder.mStyle.removeAllViews();
                        pic.setCheckStyle(false);
                        pic.setTagStyle(-1);
                        pic.setStyle(null);
                        pic.setmListStyle(getStyle(tag + ""));
                        pic.setmListStyleId(getStyleId(tag + ""));
                        notifyDataSetChanged();
                    }
                });
                holder.mSort.addView(rbSort);
            }
        }
        if (strings != null) {
            if (pic.isCheckStyle()) {
                for (int i = 0; i < strings.size(); i++) {
                    RadioButton rbStyle = new RadioButton(mContext);
                    rbStyle.setBackgroundResource(R.drawable.rb_sealect);
                    rbStyle.setText(strings.get(i));
                    rbStyle.setTag(i);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    rbStyle.setLayoutParams(params);
                    rbStyle.setGravity(Gravity.CENTER);
                    rbStyle.setButtonDrawable(android.R.color.transparent);
                    if (pic.getTagStyle() == i) {
                        rbStyle.setChecked(true);
                        rbStyle.setTextColor(mContext.getResources().getColor(R.color.write));
                    }
                    holder.mStyle.addView(rbStyle);
                    rbStyle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag = (int) v.getTag();
                            pic.setStyle(strings.get(tag));
                            pic.setTagStyle(tag);
                            pic.setCheckStyle(true);
                            notifyDataSetChanged();
                        }
                    });
                }
            } else {
                for (int i = 0; i < strings.size(); i++) {
                    RadioButton rbStyle = new RadioButton(mContext);
                    rbStyle.setBackgroundResource(R.drawable.rb_sealect);
                    rbStyle.setText(strings.get(i));
                    rbStyle.setTag(i);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    rbStyle.setLayoutParams(params);
                    rbStyle.setGravity(Gravity.CENTER);
                    rbStyle.setButtonDrawable(android.R.color.transparent);
                    holder.mStyle.addView(rbStyle);
                    rbStyle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag = (int) v.getTag();
                            pic.setStyle(strings.get(tag));
                            pic.setTagStyle(tag);
                            pic.setClothes_styleId(strings1.get(tag));
                            pic.setCheckStyle(true);
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        }

        String picPath = pic.getPicPath();
        if (!TextUtils.isEmpty(picPath)) {
            Bitmap smallBitmap = ImageUtils.getSmallBitmap(picPath);
            holder.mImage.setImageBitmap(smallBitmap);
        }

        if (mClick != null) {
            holder.mMore.setTag(position);
            holder.mMore.setOnClickListener(mClick);
            holder.mDelete.setTag(position);
            holder.mDelete.setOnClickListener(mClick);
        }

        return convertView;
    }

    public static class ViewHolder {
        private RoundImageView mImage;
        private RadioGroup mOcc, mSort, mStyle;
        private TextView mMore;
        private ImageView mDelete;

        public ViewHolder(View view) {
            mImage = (RoundImageView) view.findViewById(R.id.rv_item_up_clothes);
            mOcc = (RadioGroup) view.findViewById(R.id.rg_item_label_occ);
            mSort = (RadioGroup) view.findViewById(R.id.rg_item_label_sort);
            mStyle = (RadioGroup) view.findViewById(R.id.rg_item_label_style);
            mMore = (TextView) view.findViewById(R.id.tv_item_label_more);
            mDelete = (ImageView) view.findViewById(R.id.iv_item_label_delete);
        }
    }

    //更新下数据
    private List<String> getStyle(String str) {
        List<String> mStyles = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(mJsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloStyle = body.getJSONArray("cloStyle");
            for (int i = 0; i < cloStyle.length(); i++) {
                JSONObject arr = cloStyle.getJSONObject(i);
                if (TextUtils.equals(str, arr.getString("cloType_id"))) {
                    mStyles.add(arr.getString("cloStyle_name"));
                }
            }
            return mStyles;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getStyleId(String str) {
        List<String> mStylesId = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(mJsons);
            JSONObject body = object.getJSONObject("body");
            JSONArray cloStyle = body.getJSONArray("cloStyle");
            for (int i = 0; i < cloStyle.length(); i++) {
                JSONObject arr = cloStyle.getJSONObject(i);
                if (TextUtils.equals(str, arr.getString("cloType_id"))) {
                    mStylesId.add(arr.getString("cloStyle_id"));
                }
            }
            return mStylesId;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
