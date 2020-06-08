package com.e.majorprojectfrontend;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DownloadImageTask {

    public DownloadImageTask() {
    }

    public void setImage(final ImageView imageView, String url, final ProgressBar progressBar, boolean isCenterCrop) {
        imageView.setVisibility(View.VISIBLE);
        Context context=imageView.getContext();
        if (isCenterCrop){
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            imageView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.canimage)
                    .centerCrop()
                    .into(imageView);
        }
        else {
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            imageView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.canimage)
                    .fitCenter()
                    .into(imageView);
        }

    }
    public void fitImage(final ImageView imageView, String url,final ProgressBar progressBar){
        imageView.setVisibility(View.VISIBLE);
        Context context=imageView.getContext();
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.canimage)
                .into(imageView);

    }
}
