package com.example.mentaland_sortlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Create extends AppCompatActivity {
    private ImageView image;
    private void initComponent() {
        image = (ImageView) findViewById(R.id.imageView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initComponent();
//取得GridView物件
        final GridView gridview = (GridView) findViewById(R.id.gridview);
//新增元素給gridview
        gridview.setAdapter(new ImageAdapter(this));
// 設定Gallery的背景
        gridview.setBackgroundResource(R.drawable.ic_launcher_background);
//事件監聽
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {


                if(position==0){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img1));
                    gridview.setVisibility(View.INVISIBLE);
                    Toast.makeText(Create.this, "Feel"   +  " happy", Toast.LENGTH_SHORT).show();
                }
                if(position==1){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img2));
                    gridview.setVisibility(View.INVISIBLE);
                    Toast.makeText(Create.this, "Feel"   +  " angry", Toast.LENGTH_SHORT).show();
                }
                if(position==2){
                    image.setImageDrawable( getResources().getDrawable(R.drawable.img3));
                    gridview.setVisibility(View.INVISIBLE);
                    Toast.makeText(Create.this, "Feel"   +  " sad", Toast.LENGTH_SHORT).show();
                }


            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridview.setVisibility(View.VISIBLE);
            }
        });
    }
}
