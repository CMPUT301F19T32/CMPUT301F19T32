package com.example.mentaland_sortlist;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
    // 定義Context
    private Context   mContext;
    // 定義整型陣列 即圖片源
    private Integer[]  mImageIds  =
            {
                    R.drawable.img1,
                    R.drawable.img2,
                    R.drawable.img3,
            };
    public ImageAdapter(Context c)
    {
        mContext = c;
    }
    // 獲取圖片的個數
    public int getCount()
    {
        return mImageIds.length;
    }
    // 獲取圖片在庫中的位置
    public Object getItem(int position)
    {
        return position;
    }
    // 獲取圖片ID
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null)
        {
// 給ImageView設定資源
            imageView = new ImageView(mContext);
// 設定佈局 圖片120×120顯示
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
// 設定顯示比例型別
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mImageIds[position]);
        return imageView;
    }
}