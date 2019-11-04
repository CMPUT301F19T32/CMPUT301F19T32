package com.example.myapplication;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
    private Context   mContext;
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
    // get the number of images
    public int getCount()
    {
        return mImageIds.length;
    }
    // get the position of images
    public Object getItem(int position)
    {
        return position;
    }
    // Get image's id
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null)
        {

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
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