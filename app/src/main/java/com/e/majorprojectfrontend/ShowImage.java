package com.e.majorprojectfrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowImage extends AppCompatActivity {

    public static String mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        mainImage=getIntent().getStringExtra("mainImage");
        ImageView cancel=findViewById(R.id.cancel_show_image_activity);
        ImageView image=findViewById(R.id.imageview_in_showimageactivity);
        ProgressBar progressBar=findViewById(R.id.progress_bar_in_showimage);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImage.this.finish();
            }
        });
        new DownloadImageTask().setImage(image,mainImage,progressBar,false);
        }
}
